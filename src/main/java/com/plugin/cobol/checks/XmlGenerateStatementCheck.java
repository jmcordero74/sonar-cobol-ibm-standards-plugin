package com.plugin.cobol.checks;

import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.fs.InputFile;
import java.util.List;
import java.util.regex.Pattern;

public class XmlGenerateStatementCheck extends AbstractCobolCheck {
    private static final Pattern XML_GENERATE = Pattern.compile("\\bXML\\s+GENERATE\\b", Pattern.CASE_INSENSITIVE);
    @Override protected String getRuleKey() { return "XmlGenerateStatement"; }
    @Override
    public void analyze(SensorContext context, InputFile inputFile, String content) {
        List<String> lines = getLines(content);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (!isCommentLine(line) && XML_GENERATE.matcher(line).find()) {
                reportIssue(context, inputFile, i + 1,
                        "XML GENERATE es extensión COBOL para convertir datos al formato XML.");
            }
        }
    }
}
