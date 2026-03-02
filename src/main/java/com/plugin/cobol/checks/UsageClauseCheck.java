package com.plugin.cobol.checks;

import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.fs.InputFile;
import java.util.*;
import java.util.regex.*;

/**
 * Regla: UsageClause
 * Verifica el uso correcto de las cláusulas USAGE extendidas de IBM COBOL:
 * NATIVE, COMP-1/2/3/4/5, COMPUTATIONAL-1..5, DISPLAY-1, NATIONAL,
 * POINTER, PROCEDURE-POINTER, FUNCTION-POINTER
 */
public class UsageClauseCheck extends AbstractCobolCheck {

    private static final Set<String> IBM_USAGE_EXTENSIONS = new HashSet<>(Arrays.asList(
            "NATIVE", "COMP-1", "COMP-2", "COMP-3", "COMP-4", "COMP-5",
            "COMPUTATIONAL-1", "COMPUTATIONAL-2", "COMPUTATIONAL-3",
            "COMPUTATIONAL-4", "COMPUTATIONAL-5",
            "DISPLAY-1", "NATIONAL", "POINTER", "PROCEDURE-POINTER", "FUNCTION-POINTER"
    ));

    private static final Pattern USAGE_PATTERN = Pattern.compile(
            "\\bUSAGE\\s+(?:IS\\s+)?(\\S+(?:-\\S+)?)", Pattern.CASE_INSENSITIVE);

    @Override protected String getRuleKey() { return "UsageClause"; }

    @Override
    public void analyze(SensorContext context, InputFile inputFile, String content) {
        List<String> lines = getLines(content);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (isCommentLine(line)) continue;

            Matcher matcher = USAGE_PATTERN.matcher(line);
            while (matcher.find()) {
                String usage = matcher.group(1).toUpperCase();
                if (IBM_USAGE_EXTENSIONS.contains(usage)) {
                    reportIssue(context, inputFile, i + 1,
                            String.format("USAGE %s es una extensión IBM COBOL no contemplada en la norma 85 COBOL estándar.", usage));
                }
            }
        }
    }
}
