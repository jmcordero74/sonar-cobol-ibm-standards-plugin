package com.plugin.cobol.checks;

import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.fs.InputFile;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Regla: LengthFunction
 * IBM COBOL extiende la función LENGTH:
 * - Permite especificar un puntero como argumento
 * - Permite el registro especial ADDRESS OF como argumento
 * - Permite el registro especial LENGTH OF como argumento
 * La norma 85 COBOL solo acepta elementos de datos simples.
 *
 * Referencia: Tabla IBM - Función LENGTH
 */
public class LengthFunctionCheck extends AbstractCobolCheck {

    private static final Pattern LENGTH_FUNC_PTR = Pattern.compile(
            "\\bFUNCTION\\s+LENGTH\\s*\\(\\s*(?:ADDRESS\\s+OF|LENGTH\\s+OF)", Pattern.CASE_INSENSITIVE);
    private static final Pattern LENGTH_FUNC_USAGE = Pattern.compile(
            "\\bFUNCTION\\s+LENGTH\\b", Pattern.CASE_INSENSITIVE);

    @Override protected String getRuleKey() { return "LengthFunction"; }

    @Override
    public void analyze(SensorContext context, InputFile inputFile, String content) {
        List<String> lines = getLines(content);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (isCommentLine(line)) continue;

            if (LENGTH_FUNC_PTR.matcher(line).find()) {
                reportIssue(context, inputFile, i + 1,
                        "FUNCTION LENGTH con ADDRESS OF o LENGTH OF como argumento es extensión IBM COBOL.");
            } else if (LENGTH_FUNC_USAGE.matcher(line).find() &&
                    (line.toUpperCase().contains("POINTER") || line.toUpperCase().contains("ADDRESS OF"))) {
                reportIssue(context, inputFile, i + 1,
                        "FUNCTION LENGTH con puntero como argumento es extensión IBM COBOL.");
            }
        }
    }
}
