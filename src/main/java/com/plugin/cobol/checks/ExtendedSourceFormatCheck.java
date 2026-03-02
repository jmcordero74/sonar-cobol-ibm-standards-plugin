package com.plugin.cobol.checks;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.fs.InputFile;
import java.util.List;
import java.util.regex.Pattern;
public class ExtendedSourceFormatCheck extends AbstractCobolCheck {
    private static final Pattern PATTERN = Pattern.compile("\\bSOURCEFORMAT\\b|\\bFREE\\b", Pattern.CASE_INSENSITIVE);
    @Override protected String getRuleKey() { return "ExtendedSourceFormat"; }
    @Override
    public void analyze(SensorContext context, InputFile inputFile, String content) {
        List<String> lines = getLines(content);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (!isCommentLine(line) && PATTERN.matcher(line).find()) {
                reportIssue(context, inputFile, i + 1, "Formato de fuente extendido (FREE/VARIABLE) es extensión IBM COBOL.");
            }
        }
    }
}
