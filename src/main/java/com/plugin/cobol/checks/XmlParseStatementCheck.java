package com.plugin.cobol.checks;

import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.fs.InputFile;
import java.util.List;
import java.util.regex.Pattern;

public class XmlParseStatementCheck extends AbstractCobolCheck {
    private static final Pattern XML_PARSE = Pattern.compile("\\bXML\\s+PARSE\\b", Pattern.CASE_INSENSITIVE);
    @Override protected String getRuleKey() { return "XmlParseStatement"; }
    @Override
    public void analyze(SensorContext context, InputFile inputFile, String content) {
        List<String> lines = getLines(content);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (!isCommentLine(line) && XML_PARSE.matcher(line).find()) {
                reportIssue(context, inputFile, i + 1,
                        "XML PARSE es extensión COBOL. Utiliza registros especiales: " +
                        "XML-CODE, XML-EVENT, XML-NTEXT, XML-TEXT. Verifique el procedimiento de proceso XML.");
            }
        }
    }
}
