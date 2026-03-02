package com.plugin.cobol.checks;

import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.fs.InputFile;
import java.util.List;
import java.util.regex.Pattern;

public class NullTerminatedLiteralCheck extends AbstractCobolCheck {
    private static final Pattern NULL_LITERAL = Pattern.compile("\\bZ[\"']", Pattern.CASE_INSENSITIVE);
    @Override protected String getRuleKey() { return "NullTerminatedLiteral"; }
    @Override
    public void analyze(SensorContext context, InputFile inputFile, String content) {
        List<String> lines = getLines(content);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (!isCommentLine(line) && NULL_LITERAL.matcher(line).find()) {
                reportIssue(context, inputFile, i + 1,
                        "Literal terminado en nulo Z\"...\" o Z'...' es extensión IBM COBOL. " +
                        "Usado para interoperabilidad con C/C++.");
            }
        }
    }
}
