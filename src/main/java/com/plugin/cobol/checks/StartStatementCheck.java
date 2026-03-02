package com.plugin.cobol.checks;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.fs.InputFile;
import java.util.List;
import java.util.regex.Pattern;
public class StartStatementCheck extends AbstractCobolCheck {
    private static final Pattern PATTERN = Pattern.compile("\\bSTART\\b", Pattern.CASE_INSENSITIVE);
    @Override protected String getRuleKey() { return "StartStatement"; }
    @Override
    public void analyze(SensorContext context, InputFile inputFile, String content) {
        List<String> lines = getLines(content);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (!isCommentLine(line) && PATTERN.matcher(line).find()) {
                reportIssue(context, inputFile, i + 1, "START con operadores LESS THAN, NOT GREATER THAN, LESS THAN OR EQUAL TO son extensiones COBOL.");
            }
        }
    }
}
