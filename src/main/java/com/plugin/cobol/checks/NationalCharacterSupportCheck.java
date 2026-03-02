package com.plugin.cobol.checks;

import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.fs.InputFile;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Regla: NationalCharacterSupport
 *
 * Verifica el uso correcto de USAGE NATIONAL, GROUP-USAGE NATIONAL
 * y literales nacionales como extensiones IBM COBOL.
 *
 * Extensiones verificadas:
 * - USAGE NATIONAL para UTF-16
 * - USAGE DISPLAY para UTF-8
 * - GROUP-USAGE NATIONAL
 * - Literales nacionales N"...", NX"..."
 * - Funciones DISPLAY-OF y NATIONAL-OF
 */
public class NationalCharacterSupportCheck extends AbstractCobolCheck {

    private static final Pattern USAGE_NATIONAL =
            Pattern.compile("\\bUSAGE\\s+NATIONAL\\b", Pattern.CASE_INSENSITIVE);
    private static final Pattern GROUP_USAGE_NATIONAL =
            Pattern.compile("\\bGROUP-USAGE\\s+NATIONAL\\b", Pattern.CASE_INSENSITIVE);
    private static final Pattern NATIONAL_LITERAL =
            Pattern.compile("\\bN[X]?[\"']", Pattern.CASE_INSENSITIVE);
    private static final Pattern NATIONAL_FUNCTION =
            Pattern.compile("\\bFUNCTION\\s+(?:NATIONAL-OF|DISPLAY-OF)\\b", Pattern.CASE_INSENSITIVE);

    @Override
    protected String getRuleKey() { return "NationalCharacterSupport"; }

    @Override
    public void analyze(SensorContext context, InputFile inputFile, String content) {
        List<String> lines = getLines(content);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (isCommentLine(line)) continue;

            // USAGE NATIONAL es extensión IBM - informar como informativo
            if (USAGE_NATIONAL.matcher(line).find()) {
                reportIssue(context, inputFile, i + 1,
                        "USAGE NATIONAL es una extensión IBM COBOL para soporte UTF-16. " +
                        "No es parte de la norma 85 COBOL estándar.");
            }

            // GROUP-USAGE NATIONAL es extensión IBM
            if (GROUP_USAGE_NATIONAL.matcher(line).find()) {
                reportIssue(context, inputFile, i + 1,
                        "GROUP-USAGE NATIONAL es una extensión IBM COBOL para grupos nacionales.");
            }

            // Verificar literales nacionales (N" o NX")
            if (NATIONAL_LITERAL.matcher(line).find()) {
                reportIssue(context, inputFile, i + 1,
                        "Los literales nacionales N\"...\", N'...', NX\"...\", NX'...' son extensiones IBM COBOL. " +
                        "El comportamiento depende de la opción de compilador NSYMBOL.");
            }

            // Funciones de conversión IBM
            if (NATIONAL_FUNCTION.matcher(line).find()) {
                reportIssue(context, inputFile, i + 1,
                        "Las funciones NATIONAL-OF y DISPLAY-OF son extensiones intrínsecas IBM COBOL " +
                        "para conversión de datos Unicode.");
            }
        }
    }
}
