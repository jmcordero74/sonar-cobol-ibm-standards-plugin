package com.plugin.cobol.checks;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.fs.InputFile;
import java.util.List;
import java.util.regex.Pattern;
public class CompilerDirectiveCheck extends AbstractCobolCheck {
    private static final Pattern PATTERN = Pattern.compile("\\b(BASIS|EJECT|TITLE|SKIP[123]|\\*CBL|\\*CONTROL|SERVICE\\s+LABEL|SERVICE\\s+RELOAD|CALLINTERFACE)\\b", Pattern.CASE_INSENSITIVE);
    @Override protected String getRuleKey() { return "CompilerDirective"; }
    @Override
    public void analyze(SensorContext context, InputFile inputFile, String content) {
        List<String> lines = getLines(content);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (!isCommentLine(line) && PATTERN.matcher(line).find()) {
                reportIssue(context, inputFile, i + 1, "Directiva de compilador detectada. Estas son extensiones COBOL no estándar.");
            }
        }
    }
}
