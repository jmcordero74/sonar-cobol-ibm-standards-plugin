package com.plugin.cobol.checks;

import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.fs.InputFile;
import java.util.List;
import java.util.regex.Pattern;

public class EntryStatementCheck extends AbstractCobolCheck {
    private static final Pattern ENTRY = Pattern.compile("^\\s*ENTRY\\s+", Pattern.CASE_INSENSITIVE);
    @Override protected String getRuleKey() { return "EntryStatement"; }
    @Override
    public void analyze(SensorContext context, InputFile inputFile, String content) {
        List<String> lines = getLines(content);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (!isCommentLine(line) && ENTRY.matcher(line).find()) {
                reportIssue(context, inputFile, i + 1,
                        "La sentencia ENTRY es extensión IBM COBOL para puntos de entrada alternativos. " +
                        "No es parte de la norma 85 COBOL estándar.");
            }
        }
    }
}
