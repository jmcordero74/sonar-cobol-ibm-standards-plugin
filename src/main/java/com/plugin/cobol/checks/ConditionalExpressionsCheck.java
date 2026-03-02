package com.plugin.cobol.checks;

import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.fs.InputFile;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Regla: ConditionalExpressions
 * COBOL extiende las expresiones condicionales con:
 * - Condiciones de clase DBCS y KANJI (IF ... IS DBCS / IF ... IS KANJI)
 * - Elementos de uso COMPUTATIONAL-3 o PACKED-DECIMAL en prueba de clase NUMERIC
 * - Literales alfanuméricos, DBCS o nacionales entre paréntesis en condición de relación
 * - Puntero de datos, procedimiento y función en condición de relación
 * - Comparación de nombre de índice con expresión aritmética
 * - Uso de paréntesis en condiciones de relación combinadas abreviadas
 * - Frase CORRESPONDING con identificador subordinado a elemento FILLER
 *
 * Referencia: Tabla - Expresiones condicionales / Condición de relación / Frase CORRESPONDING
 */
public class ConditionalExpressionsCheck extends AbstractCobolCheck {

    private static final Pattern CLASS_DBCS = Pattern.compile(
            "\\bIS\\s+DBCS\\b|\\bIS\\s+KANJI\\b", Pattern.CASE_INSENSITIVE);
    private static final Pattern NUMERIC_COMP3 = Pattern.compile(
            "\\bIS\\s+NUMERIC\\b", Pattern.CASE_INSENSITIVE);
    private static final Pattern CORRESPONDING = Pattern.compile(
            "\\b(?:MOVE|ADD|SUBTRACT)\\s+CORRESPONDING\\b|\\bCORR\\b", Pattern.CASE_INSENSITIVE);

    @Override protected String getRuleKey() { return "ConditionalExpressions"; }

    @Override
    public void analyze(SensorContext context, InputFile inputFile, String content) {
        List<String> lines = getLines(content);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (isCommentLine(line)) continue;

            // Clase DBCS/KANJI - extensión
            if (CLASS_DBCS.matcher(line).find()) {
                reportIssue(context, inputFile, i + 1,
                        "Las condiciones de clase DBCS y KANJI son extensiones COBOL. " +
                        "No son parte de la norma 85 COBOL estándar.");
            }

            // IS NUMERIC con COMP-3/PACKED-DECIMAL - extensión
            if (NUMERIC_COMP3.matcher(line).find()) {
                reportIssue(context, inputFile, i + 1,
                        "Prueba de clase IS NUMERIC: COBOL permite elementos COMP-3/PACKED-DECIMAL " +
                        "(extensión a la norma 85 COBOL).");
            }

            // CORRESPONDING/CORR con FILLER - extensión
            if (CORRESPONDING.matcher(line).find()) {
                reportIssue(context, inputFile, i + 1,
                        "Frase CORRESPONDING/CORR detectada. COBOL permite especificar un identificador " +
                        "subordinado a un elemento FILLER (extensión a la norma 85 COBOL).");
            }
        }
    }
}
