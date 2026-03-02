package com.plugin.cobol.checks;

import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.fs.InputFile;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Regla: CancelStatement
 * COBOL extiende CANCEL:
 * - Nombre de programa en elemento de datos alfabético o decimal con zona
 * - Efecto de la opción de compilador PGMNAME en el nombre del programa a cancelar
 */
public class CancelStatementCheck extends AbstractCobolCheck {
    private static final Pattern CANCEL = Pattern.compile("^\\s*CANCEL\\b", Pattern.CASE_INSENSITIVE);
    @Override protected String getRuleKey() { return "CancelStatement"; }
    @Override
    public void analyze(SensorContext context, InputFile inputFile, String content) {
        List<String> lines = getLines(content);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (!isCommentLine(line) && CANCEL.matcher(line).find()) {
                reportIssue(context, inputFile, i + 1,
                        "CANCEL: COBOL permite especificar el nombre del programa en un elemento " +
                        "de datos alfabético o decimal con zona (extensión a la norma 85 COBOL). " +
                        "También depende de la opción de compilador PGMNAME.");
            }
        }
    }
}
