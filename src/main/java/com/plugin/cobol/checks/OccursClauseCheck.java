package com.plugin.cobol.checks;

import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.fs.InputFile;
import java.util.List;
import java.util.regex.Pattern;

public class OccursClauseCheck extends AbstractCobolCheck {
    private static final Pattern OCCURS_DEPENDING = Pattern.compile(
            "\\bOCCURS\\b.*\\bDEPENDING\\b", Pattern.CASE_INSENSITIVE);
    private static final Pattern OCCURS_NO_RANGE = Pattern.compile(
            "\\bOCCURS\\s+\\d+\\s+TIMES\\b", Pattern.CASE_INSENSITIVE);

    @Override protected String getRuleKey() { return "OccursClause"; }

    @Override
    public void analyze(SensorContext context, InputFile inputFile, String content) {
        List<String> lines = getLines(content);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (isCommentLine(line)) continue;
            if (OCCURS_DEPENDING.matcher(line).find()) {
                reportIssue(context, inputFile, i + 1,
                        "OCCURS DEPENDING ON detectado. IBM COBOL permite OCCURS DEPENDING ON complejo anidado " +
                        "y omisión de 'integer-1 TO', que son extensiones a la norma 85 COBOL.");
            }
        }
    }
}
