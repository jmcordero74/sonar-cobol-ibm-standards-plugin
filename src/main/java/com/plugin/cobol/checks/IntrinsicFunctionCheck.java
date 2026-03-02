package com.plugin.cobol.checks;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.fs.InputFile;
import java.util.List;
import java.util.regex.Pattern;
public class IntrinsicFunctionCheck extends AbstractCobolCheck {
    private static final Pattern PATTERN = Pattern.compile("\\bFUNCTION\\s+(ADD-DURATION|SUBTRACT-DURATION|CONVERT-DATE-TIME|EXTRACT-DATE-TIME|FIND-DURATION|TEST-DATE-TIME|TRIML|TRIMR|DATEVAL|UNDATE|YEARWINDOW|DATE-TO-YYYYMMDD|DAY-TO-YYYYDDD|YEAR-TO-YYYY)\\b", Pattern.CASE_INSENSITIVE);
    @Override protected String getRuleKey() { return "IntrinsicFunction"; }
    @Override
    public void analyze(SensorContext context, InputFile inputFile, String content) {
        List<String> lines = getLines(content);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (!isCommentLine(line) && PATTERN.matcher(line).find()) {
                reportIssue(context, inputFile, i + 1, "Función intrínseca IBM COBOL detectada. No es parte de la norma 85 COBOL estándar.");
            }
        }
    }
}
