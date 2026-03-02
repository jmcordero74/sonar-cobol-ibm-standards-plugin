package com.plugin.cobol.checks;

import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.fs.InputFile;
import java.util.List;
import java.util.regex.Pattern;

public class CallStatementCheck extends AbstractCobolCheck {
    private static final Pattern CALL_BY_VALUE = Pattern.compile("\\bBY\\s+VALUE\\b", Pattern.CASE_INSENSITIVE);
    private static final Pattern CALL_OMITTED = Pattern.compile("\\bOMITTED\\b", Pattern.CASE_INSENSITIVE);
    private static final Pattern CALL_RETURNING = Pattern.compile("\\bRETURNING\\b", Pattern.CASE_INSENSITIVE);
    private static final Pattern PROC_POINTER = Pattern.compile("\\bPROCEDURE-POINTER\\b|\\bFUNCTION-POINTER\\b", Pattern.CASE_INSENSITIVE);

    @Override protected String getRuleKey() { return "CallStatement"; }

    @Override
    public void analyze(SensorContext context, InputFile inputFile, String content) {
        List<String> lines = getLines(content);
        boolean inCallStatement = false;
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (isCommentLine(line)) continue;

            if (line.toUpperCase().contains("CALL ")) inCallStatement = true;

            if (inCallStatement) {
                if (CALL_BY_VALUE.matcher(line).find()) {
                    reportIssue(context, inputFile, i + 1,
                            "BY VALUE en CALL es extensión IBM COBOL para pasar argumentos por valor.");
                }
                if (CALL_OMITTED.matcher(line).find()) {
                    reportIssue(context, inputFile, i + 1,
                            "OMITTED en CALL es extensión IBM COBOL para omitir argumentos opcionales.");
                }
                if (CALL_RETURNING.matcher(line).find()) {
                    reportIssue(context, inputFile, i + 1,
                            "RETURNING en CALL es extensión IBM COBOL para recibir valor de retorno.");
                }
                if (PROC_POINTER.matcher(line).find()) {
                    reportIssue(context, inputFile, i + 1,
                            "PROCEDURE-POINTER o FUNCTION-POINTER en CALL es extensión IBM COBOL.");
                }
                if (line.trim().endsWith(".") || line.toUpperCase().contains("END-CALL")) {
                    inCallStatement = false;
                }
            }
        }
    }
}
