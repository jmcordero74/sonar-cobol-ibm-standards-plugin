package com.plugin.cobol.checks;

import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.fs.InputFile;
import java.util.List;
import java.util.regex.Pattern;

public class DateFormatClauseCheck extends AbstractCobolCheck {
    private static final Pattern DATE_FORMAT = Pattern.compile("\\bDATE\\s+FORMAT\\b", Pattern.CASE_INSENSITIVE);
    @Override protected String getRuleKey() { return "DateFormatClause"; }
    @Override
    public void analyze(SensorContext context, InputFile inputFile, String content) {
        List<String> lines = getLines(content);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (!isCommentLine(line) && DATE_FORMAT.matcher(line).find()) {
                reportIssue(context, inputFile, i + 1,
                        "La cláusula DATE FORMAT es una extensión IBM COBOL para manejo de fechas Y2K. " +
                        "Permite especificar campos de fecha con ventana o expandidos.");
            }
        }
    }
}
