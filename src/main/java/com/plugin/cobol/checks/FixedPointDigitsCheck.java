package com.plugin.cobol.checks;

import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.fs.InputFile;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FixedPointDigitsCheck extends AbstractCobolCheck {
    // Números con más de 18 dígitos (extensión IBM hasta 31)
    private static final Pattern LONG_NUMERIC = Pattern.compile("\\b(\\d{19,31})\\b");
    private static final Pattern TOO_LONG_NUMERIC = Pattern.compile("\\b(\\d{32,})\\b");

    @Override protected String getRuleKey() { return "FixedPointDigitsLimit"; }

    @Override
    public void analyze(SensorContext context, InputFile inputFile, String content) {
        List<String> lines = getLines(content);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (isCommentLine(line)) continue;

            Matcher tooLong = TOO_LONG_NUMERIC.matcher(line);
            if (tooLong.find()) {
                reportIssue(context, inputFile, i + 1,
                        String.format("Literal numérico de %d dígitos excede el máximo IBM COBOL de 31 dígitos.",
                                tooLong.group(1).length()));
                continue;
            }

            Matcher longNum = LONG_NUMERIC.matcher(line);
            if (longNum.find()) {
                reportIssue(context, inputFile, i + 1,
                        String.format("Literal numérico de %d dígitos excede los 18 dígitos de la norma 85 COBOL. " +
                                "Es extensión IBM COBOL (máximo 31 dígitos).",
                                longNum.group(1).length()));
            }
        }
    }
}
