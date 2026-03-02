package com.plugin.cobol.checks;

import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.fs.InputFile;

import java.util.List;
import java.util.regex.Pattern;

/** Regla: HexLiteral - Verifica literales hexadecimales IBM COBOL */
public class HexLiteralCheck extends AbstractCobolCheck {
    private static final Pattern HEX_LITERAL = Pattern.compile("\\bX[\"'][0-9A-Fa-f]+[\"']", Pattern.CASE_INSENSITIVE);
    private static final Pattern DBCS_LITERAL = Pattern.compile("\\b[GN][\"']", Pattern.CASE_INSENSITIVE);

    @Override protected String getRuleKey() { return "HexLiteral"; }

    @Override
    public void analyze(SensorContext context, InputFile inputFile, String content) {
        List<String> lines = getLines(content);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (isCommentLine(line)) continue;
            if (HEX_LITERAL.matcher(line).find()) {
                reportIssue(context, inputFile, i + 1,
                        "Literal hexadecimal alfanumérico X\"...\" detectado. Es extensión IBM COBOL. " +
                        "Asegúrese de que el valor hex es válido.");
            }
            if (DBCS_LITERAL.matcher(line).find()) {
                reportIssue(context, inputFile, i + 1,
                        "Literal DBCS G\"...\" o N\"...\" detectado. El tipo (DBCS/Nacional) depende de la " +
                        "opción de compilador NSYMBOL.");
            }
        }
    }
}
