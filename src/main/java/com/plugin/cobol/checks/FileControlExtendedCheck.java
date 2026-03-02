package com.plugin.cobol.checks;

import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.fs.InputFile;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Regla: FileControlExtended
 * IBM COBOL extiende el párrafo FILE-CONTROL con:
 * - Cláusula PASSWORD (comprobación sintáctica, sin efecto en ejecución)
 * - Segundo nombre de datos en FILE STATUS
 * - Opcionalidad de RECORD en ALTERNATE RECORD KEY
 * - Claves de registro de tipo no alfanumérico (numérico, editado, nacional, DBCS...)
 * - Claves fuera del tamaño mínimo de registro en archivos indexados de longitud variable
 * - Acceso secuencial en orden descendente
 * - Literal nacional en PADDING CHARACTER
 * - Frase USING en la cláusula ASSIGN
 *
 * Referencia: Tabla IBM - SECCIÓN DE ENTRADA-SALIDA, párrafo CONTROL DE ARCHIVOS
 */
public class FileControlExtendedCheck extends AbstractCobolCheck {

    private static final Pattern PASSWORD_CLAUSE = Pattern.compile(
            "\\bPASSWORD\\b", Pattern.CASE_INSENSITIVE);
    private static final Pattern ALT_RECORD_KEY = Pattern.compile(
            "\\bALTERNATE\\s+(?:RECORD\\s+)?KEY\\b", Pattern.CASE_INSENSITIVE);
    private static final Pattern ASSIGN_USING = Pattern.compile(
            "\\bASSIGN\\b.*\\bUSING\\b", Pattern.CASE_INSENSITIVE);
    private static final Pattern PADDING_NATIONAL = Pattern.compile(
            "\\bPADDING\\s+CHARACTER\\b.*N[\"']", Pattern.CASE_INSENSITIVE);
    private static final Pattern DESCENDING_ACCESS = Pattern.compile(
            "\\bACCESS\\s+MODE\\s+SEQUENTIAL\\b", Pattern.CASE_INSENSITIVE);

    @Override protected String getRuleKey() { return "FileControlExtended"; }

    @Override
    public void analyze(SensorContext context, InputFile inputFile, String content) {
        List<String> lines = getLines(content);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (isCommentLine(line)) continue;

            if (PASSWORD_CLAUSE.matcher(line).find()) {
                reportIssue(context, inputFile, i + 1,
                        "La cláusula PASSWORD es extensión IBM COBOL " +
                        "(solo se comprueba la sintaxis, no tiene efecto en la ejecución).");
            }
            if (ALT_RECORD_KEY.matcher(line).find() && !line.toUpperCase().contains("RECORD KEY")) {
                reportIssue(context, inputFile, i + 1,
                        "ALTERNATE KEY sin la palabra RECORD es extensión IBM COBOL " +
                        "(la norma 85 exige la palabra RECORD en ALTERNATE RECORD KEY).");
            }
            if (ASSIGN_USING.matcher(line).find()) {
                reportIssue(context, inputFile, i + 1,
                        "La frase USING en la cláusula ASSIGN es extensión IBM COBOL.");
            }
        }
    }
}
