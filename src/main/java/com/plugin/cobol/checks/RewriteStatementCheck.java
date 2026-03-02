package com.plugin.cobol.checks;

import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.fs.InputFile;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Regla: RewriteStatement
 * IBM COBOL extiende REWRITE:
 * - Omisión de INVALID KEY y procedimiento declarativo aplicable
 * - Reescritura de registro con número de posiciones diferente al original
 */
public class RewriteStatementCheck extends AbstractCobolCheck {
    private static final Pattern REWRITE = Pattern.compile("^\\s*REWRITE\\b", Pattern.CASE_INSENSITIVE);
    private static final Pattern INVALID_KEY_IN_REWRITE = Pattern.compile(
            "\\bINVALID\\s+KEY\\b", Pattern.CASE_INSENSITIVE);

    @Override protected String getRuleKey() { return "RewriteStatement"; }

    @Override
    public void analyze(SensorContext context, InputFile inputFile, String content) {
        List<String> lines = getLines(content);
        boolean inRewrite = false;
        boolean hasInvalidKey = false;
        int rewriteLine = -1;

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (isCommentLine(line)) continue;

            if (REWRITE.matcher(line).find()) {
                inRewrite = true;
                hasInvalidKey = false;
                rewriteLine = i + 1;
            }

            if (inRewrite && INVALID_KEY_IN_REWRITE.matcher(line).find()) {
                hasInvalidKey = true;
            }

            // END-REWRITE o punto finaliza el contexto
            if (inRewrite && (line.toUpperCase().contains("END-REWRITE") || line.trim().endsWith("."))) {
                if (!hasInvalidKey && rewriteLine > 0) {
                    reportIssue(context, inputFile, rewriteLine,
                            "REWRITE sin INVALID KEY: IBM COBOL permite omitir la frase INVALID KEY y el " +
                            "procedimiento declarativo (extensión a la norma 85 COBOL que exige al menos uno de ellos). " +
                            "También permite reescribir registros de tamaño diferente.");
                }
                inRewrite = false;
            }
        }
    }
}
