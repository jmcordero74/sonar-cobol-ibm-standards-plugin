package com.plugin.cobol.checks;

import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.fs.InputFile;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FloatingPointLiteralCheck extends AbstractCobolCheck {
    private static final Pattern FLOAT_LITERAL = Pattern.compile(
            "\\b\\d+\\.\\d+[Ee][+-]?\\d+\\b", Pattern.CASE_INSENSITIVE);
    @Override protected String getRuleKey() { return "FloatingPointLiteral"; }
    @Override
    public void analyze(SensorContext context, InputFile inputFile, String content) {
        List<String> lines = getLines(content);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (!isCommentLine(line) && FLOAT_LITERAL.matcher(line).find()) {
                reportIssue(context, inputFile, i + 1,
                        "Literal numérico de coma flotante detectado. Es extensión IBM COBOL " +
                        "(no contemplado en la norma 85 COBOL estándar).");
            }
        }
    }
}
