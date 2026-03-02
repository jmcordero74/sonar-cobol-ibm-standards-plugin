package com.plugin.cobol.checks;

import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.fs.InputFile;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Regla: MixedLiterals
 * IBM COBOL extiende los literales alfanuméricos con:
 * - Uso del apóstrofo (') como alternativa a comillas (") en delimitadores
 * - Caracteres de un solo byte y multibyte mezclados en literales alfanuméricos (literales mixtos)
 * - Literales alfanuméricos consecutivos (continuación en columna 72)
 *
 * Referencia: Tabla IBM - Literales
 */
public class MixedLiteralCheck extends AbstractCobolCheck {

    // Detectar literales entre apóstrofos (alternativa IBM a comillas)
    private static final Pattern APOSTROPHE_LITERAL = Pattern.compile(
            "'[^']*'", Pattern.CASE_INSENSITIVE);
    // Detectar continuación de literal alfanumérico en línea de continuación
    private static final Pattern LITERAL_CONTINUATION = Pattern.compile(
            "^.{6}-\\s*[\"']", Pattern.CASE_INSENSITIVE);

    @Override protected String getRuleKey() { return "MixedLiterals"; }

    @Override
    public void analyze(SensorContext context, InputFile inputFile, String content) {
        List<String> lines = getLines(content);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (isCommentLine(line)) continue;

            // Verificar literales con apóstrofo (extensión IBM)
            if (APOSTROPHE_LITERAL.matcher(line).find()) {
                reportIssue(context, inputFile, i + 1,
                        "Literal alfanumérico con apóstrofo (') detectado. " +
                        "IBM COBOL permite el apóstrofo como alternativa a las comillas (\") para delimitar literales. " +
                        "Es extensión a la norma 85 COBOL estándar.");
            }

            // Línea de continuación de literal
            if (LITERAL_CONTINUATION.matcher(line).find()) {
                reportIssue(context, inputFile, i + 1,
                        "Línea de continuación de literal alfanumérico detectada. " +
                        "IBM COBOL permite literales alfanuméricos consecutivos " +
                        "(terminando en columna 72 y continuando en la siguiente línea).");
            }
        }
    }
}
