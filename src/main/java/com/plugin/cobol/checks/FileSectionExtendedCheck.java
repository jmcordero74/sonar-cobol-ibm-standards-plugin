package com.plugin.cobol.checks;

import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.fs.InputFile;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Regla: FileSectionExtended
 * IBM COBOL extiende FILE SECTION con:
 * - data-name en LABEL RECORDS (para etiquetas de usuario)
 * - Cláusula RECORD MODE para archivos secuenciales
 * - Entrada de descripción de archivo de formato secuencial de línea
 * - En SD: BLOCK CONTAINS, LABEL RECORDS, VALUE OF, LINAGE, CODE-SET, WITH FOOTING, LINES AT
 * - VALUE OF bajo SD sin efecto en ejecución
 * - DATA RECORDS: opcionalidad de registro 01 para el nombre-datos
 *
 * Referencia: Tabla IBM - SECCIÓN FILE y Entrada de descripción SD
 */
public class FileSectionExtendedCheck extends AbstractCobolCheck {

    private static final Pattern LABEL_RECORDS_DATA = Pattern.compile(
            "\\bLABEL\\s+RECORDS\\b.*\\b(?!STANDARD|OMITTED)[A-Z][A-Z0-9-]*\\b", Pattern.CASE_INSENSITIVE);
    private static final Pattern RECORD_MODE = Pattern.compile(
            "\\bRECORD\\s+MODE\\b", Pattern.CASE_INSENSITIVE);
    private static final Pattern SD_BLOCK = Pattern.compile(
            "^\\s*SD\\b", Pattern.CASE_INSENSITIVE);
    private static final Pattern CODE_SET = Pattern.compile(
            "\\bCODE-SET\\b", Pattern.CASE_INSENSITIVE);
    private static final Pattern WITH_FOOTING = Pattern.compile(
            "\\bWITH\\s+FOOTING\\b", Pattern.CASE_INSENSITIVE);

    @Override protected String getRuleKey() { return "FileSectionExtended"; }

    @Override
    public void analyze(SensorContext context, InputFile inputFile, String content) {
        List<String> lines = getLines(content);
        boolean inSD = false;

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (isCommentLine(line)) continue;

            if (SD_BLOCK.matcher(line).find()) {
                inSD = true;
                continue;
            }
            // Salir de contexto SD cuando encontramos otra FD/SD o división
            if (line.toUpperCase().matches("\\s*FD\\s+.*|\\s*[A-Z]+ DIVISION.*|\\s*[A-Z]+ SECTION.*")) {
                inSD = false;
            }

            if (RECORD_MODE.matcher(line).find()) {
                reportIssue(context, inputFile, i + 1,
                        "La cláusula RECORD MODE es extensión IBM COBOL para archivos secuenciales de registro.");
            }
            if (CODE_SET.matcher(line).find()) {
                reportIssue(context, inputFile, i + 1,
                        "La cláusula CODE-SET en SD es extensión IBM COBOL " +
                        "(solo se comprueba la sintaxis, sin efecto en ejecución).");
            }
            if (inSD && WITH_FOOTING.matcher(line).find()) {
                reportIssue(context, inputFile, i + 1,
                        "WITH FOOTING en SD es extensión IBM COBOL.");
            }
        }
    }
}
