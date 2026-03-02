package com.plugin.cobol.checks;

import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.fs.InputFile;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Regla: ProcedureDivisionHeader
 * IBM COBOL extiende la cabecera de PROCEDURE DIVISION con:
 * - Frase BY VALUE en la cabecera
 * - Frase RETURNING en la cabecera
 * - Especificar en USING un elemento con cláusula REDEFINES en su descripción
 * - Especificar varias instancias del mismo elemento en USING
 *
 * Referencia: Tabla IBM - Cabecera PROCEDURE DIVISION
 */
public class ProcedureDivisionHeaderCheck extends AbstractCobolCheck {

    private static final Pattern PROC_DIV_HEADER = Pattern.compile(
            "\\bPROCEDURE\\s+DIVISION\\b", Pattern.CASE_INSENSITIVE);
    private static final Pattern BY_VALUE_HEADER = Pattern.compile(
            "\\bPROCEDURE\\s+DIVISION\\b.*\\bBY\\s+VALUE\\b", Pattern.CASE_INSENSITIVE);
    private static final Pattern RETURNING_HEADER = Pattern.compile(
            "\\bPROCEDURE\\s+DIVISION\\b.*\\bRETURNING\\b", Pattern.CASE_INSENSITIVE);

    @Override protected String getRuleKey() { return "ProcedureDivisionHeader"; }

    @Override
    public void analyze(SensorContext context, InputFile inputFile, String content) {
        List<String> lines = getLines(content);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (isCommentLine(line)) continue;

            if (BY_VALUE_HEADER.matcher(line).find()) {
                reportIssue(context, inputFile, i + 1,
                        "La frase BY VALUE en la cabecera PROCEDURE DIVISION es extensión IBM COBOL.");
            }
            if (RETURNING_HEADER.matcher(line).find()) {
                reportIssue(context, inputFile, i + 1,
                        "La frase RETURNING en la cabecera PROCEDURE DIVISION es extensión IBM COBOL.");
            }
        }
    }
}
