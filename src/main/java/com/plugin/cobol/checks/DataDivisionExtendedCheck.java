package com.plugin.cobol.checks;

import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.fs.InputFile;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Regla: DataDivisionExtended
 * IBM COBOL extiende DATA DIVISION con:
 * - Cláusula GLOBAL en LINKAGE SECTION (extensión IBM)
 * - SYNCHRONIZED para entradas de nivel 01
 * - VALUE en FILE SECTION y LINKAGE SECTION (además de en nombre de condición)
 * - VALUE para nombre de condición en grupo con usos distintos de DISPLAY
 * - VALUE THROUGH usando secuencia de clasificación del locale
 * - Categorías de datos: coma flotante interna/externa, DBCS, nacional, editada nacional
 * - Categoría numérica y numérica-editada con USAGE NATIONAL
 * - BLANK WHEN ZERO con ZEROS/ZEROES como ortografía alternativa
 *
 * Referencia: Tabla IBM - DATA DIVISION y cláusulas relacionadas
 */
public class DataDivisionExtendedCheck extends AbstractCobolCheck {

    private static final Pattern GLOBAL_IN_LINKAGE = Pattern.compile(
            "\\bGLOBAL\\b", Pattern.CASE_INSENSITIVE);
    private static final Pattern SYNCHRONIZED_01 = Pattern.compile(
            "^\\s*01\\s+.*\\bSYNC(?:HRONIZED)?\\b", Pattern.CASE_INSENSITIVE);
    private static final Pattern BLANK_ZEROS = Pattern.compile(
            "\\bBLANK\\s+WHEN\\s+(?:ZEROS|ZEROES)\\b", Pattern.CASE_INSENSITIVE);
    private static final Pattern VALUE_NULL_NULLS = Pattern.compile(
            "\\bVALUE\\s+(?:IS\\s+)?NULLS?\\b", Pattern.CASE_INSENSITIVE);

    @Override protected String getRuleKey() { return "DataDivisionExtended"; }

    @Override
    public void analyze(SensorContext context, InputFile inputFile, String content) {
        List<String> lines = getLines(content);
        boolean inLinkageSection = false;

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (isCommentLine(line)) continue;

            // Rastrear sección LINKAGE
            if (line.toUpperCase().contains("LINKAGE SECTION")) {
                inLinkageSection = true;
            } else if (line.toUpperCase().matches("\\s*[A-Z]+-STORAGE\\s+SECTION.*|\\s*FILE\\s+SECTION.*|\\s*PROCEDURE\\s+DIVISION.*")) {
                inLinkageSection = false;
            }

            // GLOBAL en LINKAGE SECTION es extensión IBM
            if (inLinkageSection && GLOBAL_IN_LINKAGE.matcher(line).find()) {
                reportIssue(context, inputFile, i + 1,
                        "Cláusula GLOBAL en LINKAGE SECTION es extensión IBM COBOL. " +
                        "La norma 85 COBOL no permite GLOBAL en LINKAGE SECTION.");
            }

            // SYNCHRONIZED en nivel 01
            if (SYNCHRONIZED_01.matcher(line).find()) {
                reportIssue(context, inputFile, i + 1,
                        "SYNCHRONIZED en entrada de nivel 01 es extensión IBM COBOL.");
            }

            // BLANK WHEN ZEROS/ZEROES - ortografía alternativa IBM
            if (BLANK_ZEROS.matcher(line).find()) {
                reportIssue(context, inputFile, i + 1,
                        "BLANK WHEN ZEROS / BLANK WHEN ZEROES es ortografía alternativa IBM COBOL " +
                        "(la norma 85 solo especifica BLANK WHEN ZERO).");
            }

            // VALUE IS NULL/NULLS en datos
            if (VALUE_NULL_NULLS.matcher(line).find()) {
                reportIssue(context, inputFile, i + 1,
                        "VALUE IS NULL / VALUE IS NULLS es extensión IBM COBOL para punteros y referencias de objeto.");
            }
        }
    }
}
