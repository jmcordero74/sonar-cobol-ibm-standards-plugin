package com.plugin.cobol.checks;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.fs.InputFile;
import java.util.List;
import java.util.regex.Pattern;
public class InlineCommentCheck extends AbstractCobolCheck {
    private static final Pattern PATTERN = Pattern.compile("\\*>", Pattern.CASE_INSENSITIVE);
    @Override protected String getRuleKey() { return "InlineComment"; }
    @Override
    public void analyze(SensorContext context, InputFile inputFile, String content) {
        List<String> lines = getLines(content);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (!isCommentLine(line) && PATTERN.matcher(line).find()) {
                reportIssue(context, inputFile, i + 1, "Comentario en línea (*>) es extensión IBM COBOL.");
            }
        }
    }
}
