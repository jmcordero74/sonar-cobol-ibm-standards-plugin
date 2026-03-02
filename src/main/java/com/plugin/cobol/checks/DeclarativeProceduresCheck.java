package com.plugin.cobol.checks;

import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.fs.InputFile;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Regla: DeclarativeProcedures
 * IBM COBOL extiende declarativos y procedimientos con:
 * - priority-number como literal numérico con signo positivo (norma 85: entero sin signo)
 * - Omitir cabecera de sección después de declarativas o cuando no hay declarativas
 * - Omitir nombre de párrafo inicial si no hay declarativos
 * - Párrafos no contenidos en sección cuando algunos sí lo están
 * - Ejecución de una declaración activa
 *
 * Referencia: Tabla IBM - Procedimientos declarativos / Procedimientos
 */
public class DeclarativeProceduresCheck extends AbstractCobolCheck {

    private static final Pattern DECLARATIVES = Pattern.compile(
            "^\\s*DECLARATIVES\\.?\\s*$", Pattern.CASE_INSENSITIVE);
    private static final Pattern END_DECLARATIVES = Pattern.compile(
            "\\bEND\\s+DECLARATIVES\\b", Pattern.CASE_INSENSITIVE);
    private static final Pattern USE_STMT = Pattern.compile(
            "^\\s*USE\\b", Pattern.CASE_INSENSITIVE);
    private static final Pattern SIGNED_PRIORITY = Pattern.compile(
            "\\bPRIORITY\\s*\\+\\d+\\b", Pattern.CASE_INSENSITIVE);

    @Override protected String getRuleKey() { return "DeclarativeProcedures"; }

    @Override
    public void analyze(SensorContext context, InputFile inputFile, String content) {
        List<String> lines = getLines(content);
        boolean inDeclaratives = false;

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (isCommentLine(line)) continue;

            if (DECLARATIVES.matcher(line.trim()).find()) {
                inDeclaratives = true;
            }
            if (END_DECLARATIVES.matcher(line).find()) {
                inDeclaratives = false;
            }

            // priority-number con signo positivo
            if (SIGNED_PRIORITY.matcher(line).find()) {
                reportIssue(context, inputFile, i + 1,
                        "priority-number con signo positivo (+N) es extensión IBM COBOL " +
                        "(la norma 85 COBOL exige un entero sin signo).");
            }

            // Detectar sentencia USE en declarativos (extensión: ejecución de declaración activa)
            if (inDeclaratives && USE_STMT.matcher(line).find()) {
                reportIssue(context, inputFile, i + 1,
                        "Declarativo USE detectado. IBM COBOL permite la ejecución de una declaración activa " +
                        "(extensión a la norma 85 COBOL).");
            }
        }
    }
}
