package com.plugin.cobol.checks;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.fs.InputFile;
import java.util.List;
import java.util.regex.Pattern;
public class SpecialRegisterCheck extends AbstractCobolCheck {
    private static final Pattern PATTERN = Pattern.compile("\\b(ADDRESS\\s+OF|LENGTH\\s+OF|RETURN-CODE|SORT-CONTROL|SORT-CORE-SIZE|SORT-FILE-SIZE|SORT-RETURN|WHEN-COMPILED|XML-CODE|XML-EVENT|XML-NTEXT|XML-TEXT|TALLY)\\b", Pattern.CASE_INSENSITIVE);
    @Override protected String getRuleKey() { return "SpecialRegister"; }
    @Override
    public void analyze(SensorContext context, InputFile inputFile, String content) {
        List<String> lines = getLines(content);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (!isCommentLine(line) && PATTERN.matcher(line).find()) {
                reportIssue(context, inputFile, i + 1, "Registro especial COBOL detectado. Verifique su uso correcto según la documentación IBM.");
            }
        }
    }
}
