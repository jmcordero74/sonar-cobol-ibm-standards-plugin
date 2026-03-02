package com.plugin.cobol.checks;

import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.fs.InputFile;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Regla: ExitStatement
 * IBM COBOL extiende la sentencia EXIT:
 * - Permite EXIT en una frase que tiene sentencias antes o después
 * - Permite EXIT en un párrafo que tiene otras frases
 * La norma 85 COBOL exige EXIT en una frase por sí sola y única del párrafo.
 */
public class ExitStatementCheck extends AbstractCobolCheck {
    // EXIT solo (no EXIT PROGRAM que es otra sentencia)
    private static final Pattern EXIT_ONLY = Pattern.compile(
            "^\\s*EXIT\\.\\s*$", Pattern.CASE_INSENSITIVE);
    private static final Pattern EXIT_WITH_MORE = Pattern.compile(
            "\\bEXIT\\b(?!\\s+PROGRAM|\\s+SECTION|\\s+PARAGRAPH|\\s+PERFORM)", Pattern.CASE_INSENSITIVE);

    @Override protected String getRuleKey() { return "ExitStatement"; }

    @Override
    public void analyze(SensorContext context, InputFile inputFile, String content) {
        List<String> lines = getLines(content);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (isCommentLine(line)) continue;

            // Detectar EXIT que no está solo en su párrafo (heurística: EXIT en línea con otras sentencias)
            if (EXIT_WITH_MORE.matcher(line).find()) {
                String trimmed = line.trim().toUpperCase();
                // Si la línea tiene EXIT y más contenido (no es EXIT solo)
                if (!trimmed.equals("EXIT.") && !trimmed.equals("EXIT")
                        && !trimmed.startsWith("EXIT PROGRAM")
                        && !trimmed.startsWith("EXIT SECTION")
                        && !trimmed.startsWith("EXIT PARAGRAPH")
                        && !trimmed.startsWith("EXIT PERFORM")) {
                    reportIssue(context, inputFile, i + 1,
                            "Sentencia EXIT: IBM COBOL permite EXIT en una frase con otras sentencias o en un párrafo " +
                            "con otras frases (extensión a la norma 85 COBOL que exige EXIT sola en su párrafo).");
                }
            }
        }
    }
}
