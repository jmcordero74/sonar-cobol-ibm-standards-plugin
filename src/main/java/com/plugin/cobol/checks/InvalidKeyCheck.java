package com.plugin.cobol.checks;

import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.fs.InputFile;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Regla: InvalidKeyOmission
 * IBM COBOL permite la omisión TANTO de INVALID KEY como del procedimiento de EXCEPCIÓN/ERROR.
 * La norma 85 COBOL exige al menos uno de ellos en operaciones de E/S con acceso indexado/relativo.
 *
 * Referencia: Tabla IBM - Frase INVALID KEY
 */
public class InvalidKeyCheck extends AbstractCobolCheck {

    // Sentencias I/O que requieren INVALID KEY en norma 85
    private static final Pattern IO_STMT = Pattern.compile(
            "^\\s*(READ|WRITE|REWRITE|START|DELETE)\\b", Pattern.CASE_INSENSITIVE);
    private static final Pattern INVALID_KEY = Pattern.compile(
            "\\bINVALID\\s+(?:KEY|KEY\\b)", Pattern.CASE_INSENSITIVE);
    private static final Pattern END_IO = Pattern.compile(
            "\\bEND-(?:READ|WRITE|REWRITE|START|DELETE)\\b", Pattern.CASE_INSENSITIVE);

    @Override protected String getRuleKey() { return "InvalidKeyOmission"; }

    @Override
    public void analyze(SensorContext context, InputFile inputFile, String content) {
        List<String> lines = getLines(content);
        boolean inIOStmt = false;
        boolean hasInvalidKey = false;
        int ioStartLine = -1;
        String stmtName = "";

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (isCommentLine(line)) continue;

            java.util.regex.Matcher m = IO_STMT.matcher(line);
            if (m.find()) {
                inIOStmt = true;
                hasInvalidKey = false;
                ioStartLine = i + 1;
                stmtName = m.group(1).toUpperCase();
            }

            if (inIOStmt && INVALID_KEY.matcher(line).find()) {
                hasInvalidKey = true;
            }

            if (inIOStmt && (END_IO.matcher(line).find() || (line.trim().endsWith(".") && !IO_STMT.matcher(line).find()))) {
                if (!hasInvalidKey && (stmtName.equals("WRITE") || stmtName.equals("REWRITE")
                        || stmtName.equals("START") || stmtName.equals("DELETE"))) {
                    reportIssue(context, inputFile, ioStartLine,
                            String.format("Sentencia %s sin INVALID KEY: IBM COBOL permite omitir INVALID KEY " +
                                    "y el procedimiento de excepción/error (extensión a la norma 85 COBOL). " +
                                    "Asegúrese de tener manejo de errores adecuado.", stmtName));
                }
                inIOStmt = false;
            }
        }
    }
}
