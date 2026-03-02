package com.plugin.cobol.checks;

import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.fs.InputFile;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Regla: IfStatement
 * IBM COBOL extiende IF:
 * - Permite uso de END-IF con la frase NEXT SENTENCE
 * La norma 85 COBOL prohíbe el uso de END-IF con NEXT SENTENCE.
 */
public class IfStatementCheck extends AbstractCobolCheck {
    // Buscar NEXT SENTENCE y END-IF en el mismo bloque IF (simplificado: en líneas consecutivas cercanas)
    private static final Pattern NEXT_SENTENCE = Pattern.compile(
            "\\bNEXT\\s+SENTENCE\\b", Pattern.CASE_INSENSITIVE);
    private static final Pattern END_IF = Pattern.compile(
            "\\bEND-IF\\b", Pattern.CASE_INSENSITIVE);

    @Override protected String getRuleKey() { return "IfStatement"; }

    @Override
    public void analyze(SensorContext context, InputFile inputFile, String content) {
        List<String> lines = getLines(content);
        boolean sawNextSentence = false;
        int nextSentenceLine = -1;

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (isCommentLine(line)) continue;

            if (NEXT_SENTENCE.matcher(line).find()) {
                sawNextSentence = true;
                nextSentenceLine = i + 1;
            }

            // Si encontramos END-IF cerca de un NEXT SENTENCE anterior
            if (sawNextSentence && END_IF.matcher(line).find()) {
                // Solo reportar si están dentro de un rango razonable (10 líneas)
                if ((i + 1) - nextSentenceLine <= 10) {
                    reportIssue(context, inputFile, i + 1,
                            "END-IF con NEXT SENTENCE es extensión IBM COBOL. " +
                            "La norma 85 COBOL prohíbe el uso de END-IF junto con NEXT SENTENCE.");
                }
                sawNextSentence = false;
            }

            // Resetear si encontramos un punto separador (fin de sentencia)
            if (line.trim().endsWith(".")) {
                sawNextSentence = false;
            }
        }
    }
}
