package com.plugin.cobol.checks;

import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.fs.InputFile;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Regla: IOControl
 * IBM COBOL extiende el párrafo I-O-CONTROL con:
 * - Cláusula APPLY WRITE-ONLY (sintaxis comprobada, sin efecto en ejecución)
 * - SAME con solo un nombre de archivo (norma 85 exige al menos dos)
 * - Opcionalidad de ON en la cláusula RERUN
 * - Entrada de control I-O de formato secuencial de línea
 * - Cláusula RERUN en fusión de clasificación
 *
 * Referencia: Tabla IBM - SECCIÓN DE ENTRADA-SALIDA, párrafo CONTROL DE E/S
 */
public class IOControlCheck extends AbstractCobolCheck {

    private static final Pattern IO_CONTROL = Pattern.compile(
            "\\bI-O-CONTROL\\b", Pattern.CASE_INSENSITIVE);
    private static final Pattern APPLY_WRITE_ONLY = Pattern.compile(
            "\\bAPPLY\\s+WRITE-ONLY\\b", Pattern.CASE_INSENSITIVE);
    private static final Pattern RERUN_CLAUSE = Pattern.compile(
            "\\bRERUN\\b", Pattern.CASE_INSENSITIVE);
    private static final Pattern SAME_CLAUSE = Pattern.compile(
            "\\bSAME\\b.*\\bAREA\\b", Pattern.CASE_INSENSITIVE);

    @Override protected String getRuleKey() { return "IOControl"; }

    @Override
    public void analyze(SensorContext context, InputFile inputFile, String content) {
        List<String> lines = getLines(content);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (isCommentLine(line)) continue;

            if (APPLY_WRITE_ONLY.matcher(line).find()) {
                reportIssue(context, inputFile, i + 1,
                        "APPLY WRITE-ONLY es extensión IBM COBOL (solo se comprueba sintaxis, sin efecto en ejecución).");
            }
            if (RERUN_CLAUSE.matcher(line).find()) {
                reportIssue(context, inputFile, i + 1,
                        "Cláusula RERUN detectada. IBM COBOL hace opcional la palabra ON y permite RERUN en fusión de clasificación.");
            }
            if (SAME_CLAUSE.matcher(line).find()) {
                reportIssue(context, inputFile, i + 1,
                        "Cláusula SAME AREA: IBM COBOL permite especificar solo un nombre de archivo " +
                        "(la norma 85 COBOL exige al menos dos).");
            }
        }
    }
}
