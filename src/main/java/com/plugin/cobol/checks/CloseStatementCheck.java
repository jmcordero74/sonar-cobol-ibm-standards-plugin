package com.plugin.cobol.checks;

import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.fs.InputFile;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Regla: CloseStatement
 * IBM COBOL extiende CLOSE:
 * - Frase WITH NO REWIND (extensión IBM)
 * - Formato secuencial de línea
 */
public class CloseStatementCheck extends AbstractCobolCheck {
    private static final Pattern CLOSE_NO_REWIND = Pattern.compile(
            "\\bCLOSE\\b.*\\bWITH\\s+NO\\s+REWIND\\b", Pattern.CASE_INSENSITIVE);
    @Override protected String getRuleKey() { return "CloseStatement"; }
    @Override
    public void analyze(SensorContext context, InputFile inputFile, String content) {
        List<String> lines = getLines(content);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (!isCommentLine(line) && CLOSE_NO_REWIND.matcher(line).find()) {
                reportIssue(context, inputFile, i + 1,
                        "CLOSE WITH NO REWIND es extensión IBM COBOL.");
            }
        }
    }
}
