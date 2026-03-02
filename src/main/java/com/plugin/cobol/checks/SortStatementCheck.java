package com.plugin.cobol.checks;

import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.fs.InputFile;
import java.util.List;
import java.util.regex.Pattern;

/** Regla: SortStatement - SORT con nombres de archivo en cláusula SAME (IBM extensión) */
public class SortStatementCheck extends AbstractCobolCheck {
    private static final Pattern SORT_SAME = Pattern.compile(
            "^\\s*SORT\\b.*\\bSAME\\b|\\bSAME\\b.*\\bSORT\\b", Pattern.CASE_INSENSITIVE);
    private static final Pattern SORT_STMT = Pattern.compile("^\\s*SORT\\b", Pattern.CASE_INSENSITIVE);
    @Override protected String getRuleKey() { return "SortStatement"; }
    @Override
    public void analyze(SensorContext context, InputFile inputFile, String content) {
        List<String> lines = getLines(content);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (!isCommentLine(line) && SORT_STMT.matcher(line).find()) {
                reportIssue(context, inputFile, i + 1,
                        "SORT: IBM COBOL permite especificar nombres de archivo GIVING en la cláusula SAME " +
                        "(extensión a la norma 85 COBOL). Verifique el uso de cláusulas SAME.");
            }
        }
    }
}
