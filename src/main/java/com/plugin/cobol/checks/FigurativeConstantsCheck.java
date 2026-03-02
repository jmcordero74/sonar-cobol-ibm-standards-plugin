package com.plugin.cobol.checks;

import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.fs.InputFile;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Regla: FigurativeConstants
 * IBM COBOL extiende las constantes figurativas:
 * - Apóstrofo (') como alternativa a comillas (") para QUOTE
 * - NULL y NULLS para punteros y referencias de objeto (VALUE IS NULL)
 * - Valor de carácter nacional para SPACE, ZERO, QUOTE, HIGH-VALUES, LOW-VALUES, ALL literal
 *
 * Referencia: Tabla IBM - Constantes figurativas
 */
public class FigurativeConstantsCheck extends AbstractCobolCheck {

    // NULL/NULLS como valor para punteros - extensión IBM
    private static final Pattern VALUE_NULL = Pattern.compile(
            "\\bVALUE\\s+(?:IS\\s+)?NULLS?\\b", Pattern.CASE_INSENSITIVE);

    // Uso de NULLS en sentencia SET o comparación
    private static final Pattern SET_NULL = Pattern.compile(
            "\\bSET\\b.*\\bTO\\s+NULLS?\\b", Pattern.CASE_INSENSITIVE);

    // Comparación con NULL
    private static final Pattern IF_NULL = Pattern.compile(
            "\\bIF\\b.*\\bEQUAL\\s+(?:TO\\s+)?NULLS?\\b|\\bNULLS?\\s*=", Pattern.CASE_INSENSITIVE);

    @Override protected String getRuleKey() { return "FigurativeConstants"; }

    @Override
    public void analyze(SensorContext context, InputFile inputFile, String content) {
        List<String> lines = getLines(content);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (isCommentLine(line)) continue;

            if (VALUE_NULL.matcher(line).find()) {
                reportIssue(context, inputFile, i + 1,
                        "VALUE IS NULL / VALUE IS NULLS es extensión IBM COBOL para inicializar punteros y referencias de objeto. " +
                        "No es parte de la norma 85 COBOL estándar.");
            }
            if (SET_NULL.matcher(line).find()) {
                reportIssue(context, inputFile, i + 1,
                        "SET ... TO NULL/NULLS es extensión IBM COBOL para punteros.");
            }
        }
    }
}
