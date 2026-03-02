package com.plugin.cobol.checks;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.fs.InputFile;
import java.util.List;
import java.util.regex.Pattern;
public class PerformStatementCheck extends AbstractCobolCheck {
    private static final Pattern PERFORM_END = Pattern.compile("\\bPERFORM\\b.*\\bEND-PERFORM\\b", Pattern.CASE_INSENSITIVE);
    @Override protected String getRuleKey() { return "PerformStatement"; }
    @Override
    public void analyze(SensorContext context, InputFile inputFile, String content) {
        List<String> lines = getLines(content);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (!isCommentLine(line) && PERFORM_END.matcher(line).find()) {
                String trimmed = line.trim().replace("PERFORM", "").replace("END-PERFORM", "").trim();
                if (trimmed.isEmpty() || trimmed.equals(".")) {
                    reportIssue(context, inputFile, i + 1,
                            "PERFORM en línea vacío es extensión IBM COBOL.");
                }
            }
        }
    }
}
