package com.plugin.cobol.checks;

import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.fs.InputFile;
import java.util.List;
import java.util.regex.Pattern;

public class GobackStatementCheck extends AbstractCobolCheck {
    private static final Pattern GOBACK = Pattern.compile("\\bGOBACK\\b", Pattern.CASE_INSENSITIVE);
    @Override protected String getRuleKey() { return "GobackStatement"; }
    @Override
    public void analyze(SensorContext context, InputFile inputFile, String content) {
        List<String> lines = getLines(content);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (!isCommentLine(line) && GOBACK.matcher(line).find()) {
                reportIssue(context, inputFile, i + 1,
                        "GOBACK es extensión IBM COBOL preferida sobre EXIT PROGRAM/STOP RUN. " +
                        "Funciona como EXIT PROGRAM en subprogramas y STOP RUN en el programa principal.");
            }
        }
    }
}
