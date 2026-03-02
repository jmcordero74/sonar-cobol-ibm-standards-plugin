package com.plugin.cobol.checks;

import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.fs.InputFile;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Regla: StopStatement
 * COBOL extiende STOP:
 * - Permite especificar un literal de punto fijo no entero
 * - Permite un entero numérico con signo
 * - Permite codificar STOP como distinta de la última sentencia de una frase
 */
public class StopStatementCheck extends AbstractCobolCheck {
    private static final Pattern STOP_LITERAL = Pattern.compile(
            "\\bSTOP\\s+[+-]?\\d+\\.\\d+\\b|\\bSTOP\\s+[+-]\\d+\\b", Pattern.CASE_INSENSITIVE);
    private static final Pattern STOP_RUN = Pattern.compile("\\bSTOP\\s+RUN\\b", Pattern.CASE_INSENSITIVE);

    @Override protected String getRuleKey() { return "StopStatement"; }

    @Override
    public void analyze(SensorContext context, InputFile inputFile, String content) {
        List<String> lines = getLines(content);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (isCommentLine(line)) continue;

            if (STOP_LITERAL.matcher(line).find()) {
                reportIssue(context, inputFile, i + 1,
                        "STOP con literal de punto fijo no entero o entero con signo es extensión COBOL.");
            }

            // STOP RUN que no es la última sentencia en su frase (heurística: hay más código después en la misma línea)
            if (STOP_RUN.matcher(line).find()) {
                String trimmed = line.trim().toUpperCase();
                if (!trimmed.equals("STOP RUN.") && !trimmed.startsWith("STOP RUN") && trimmed.contains("STOP RUN")) {
                    reportIssue(context, inputFile, i + 1,
                            "STOP RUN detectado dentro de una frase más larga. COBOL permite codificar STOP " +
                            "como distinta de la última sentencia de una frase.");
                }
            }
        }
    }
}
