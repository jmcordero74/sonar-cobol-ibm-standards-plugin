package com.plugin.cobol.checks;

import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.fs.InputFile;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Regla: MergeStatement
 * IBM COBOL extiende MERGE: permite especificar nombres de archivo en una cláusula SAME.
 */
public class MergeStatementCheck extends AbstractCobolCheck {
    private static final Pattern MERGE = Pattern.compile("^\\s*MERGE\\b", Pattern.CASE_INSENSITIVE);
    @Override protected String getRuleKey() { return "MergeStatement"; }
    @Override
    public void analyze(SensorContext context, InputFile inputFile, String content) {
        List<String> lines = getLines(content);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (!isCommentLine(line) && MERGE.matcher(line).find()) {
                reportIssue(context, inputFile, i + 1,
                        "MERGE: IBM COBOL permite especificar nombres de archivo en la cláusula SAME " +
                        "(extensión a la norma 85 COBOL).");
            }
        }
    }
}
