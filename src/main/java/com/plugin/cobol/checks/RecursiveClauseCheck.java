package com.plugin.cobol.checks;

import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.fs.InputFile;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Regla: RecursiveClause
 * Detecta y documenta el uso de la cláusula RECURSIVE en PROGRAM-ID (extensión IBM).
 */
public class RecursiveClauseCheck extends AbstractCobolCheck {

    private static final Pattern RECURSIVE_PATTERN =
            Pattern.compile("\\bRECURSIVE\\b", Pattern.CASE_INSENSITIVE);

    @Override
    protected String getRuleKey() { return "RecursiveClause"; }

    @Override
    public void analyze(SensorContext context, InputFile inputFile, String content) {
        List<String> lines = getLines(content);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (!isCommentLine(line) && RECURSIVE_PATTERN.matcher(line).find()) {
                reportIssue(context, inputFile, i + 1,
                        "La cláusula RECURSIVE es una extensión IBM COBOL. " +
                        "Asegúrese de que el programa está diseñado para ser reentrante y seguro para hilos.");
            }
        }
    }
}
