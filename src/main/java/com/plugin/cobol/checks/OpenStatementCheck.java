package com.plugin.cobol.checks;

import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.fs.InputFile;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Regla: OpenStatement
 * COBOL extiende OPEN:
 * - Formato secuencial de línea
 * - Frase EXTEND para archivos que tienen cláusula LINAGE
 */
public class OpenStatementCheck extends AbstractCobolCheck {
    private static final Pattern OPEN_EXTEND = Pattern.compile(
            "\\bOPEN\\b.*\\bEXTEND\\b", Pattern.CASE_INSENSITIVE);
    @Override protected String getRuleKey() { return "OpenStatement"; }
    @Override
    public void analyze(SensorContext context, InputFile inputFile, String content) {
        List<String> lines = getLines(content);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (!isCommentLine(line) && OPEN_EXTEND.matcher(line).find()) {
                reportIssue(context, inputFile, i + 1,
                        "OPEN EXTEND: COBOL permite la frase EXTEND para archivos con cláusula LINAGE " +
                        "(extensión a la norma 85 COBOL).");
            }
        }
    }
}
