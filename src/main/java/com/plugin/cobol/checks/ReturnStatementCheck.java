package com.plugin.cobol.checks;

import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.fs.InputFile;
import java.util.List;
import java.util.regex.Pattern;

/** Regla: ReturnStatement - RETURN a elemento no alfanumérico (IBM extensión) */
public class ReturnStatementCheck extends AbstractCobolCheck {
    private static final Pattern RETURN_STMT = Pattern.compile("^\\s*RETURN\\b", Pattern.CASE_INSENSITIVE);
    @Override protected String getRuleKey() { return "ReturnStatement"; }
    @Override
    public void analyze(SensorContext context, InputFile inputFile, String content) {
        List<String> lines = getLines(content);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (!isCommentLine(line) && RETURN_STMT.matcher(line).find()) {
                reportIssue(context, inputFile, i + 1,
                        "RETURN: COBOL permite devolver a un elemento que no sea alfanumérico " +
                        "(extensión a la norma 85 COBOL). Verifique el tipo del elemento receptor.");
            }
        }
    }
}
