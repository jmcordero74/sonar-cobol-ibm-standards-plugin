package com.plugin.cobol.checks;

import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.fs.InputFile;
import java.util.List;
import java.util.regex.Pattern;

public class RedefinesClauseCheck extends AbstractCobolCheck {
    private static final Pattern REDEFINES = Pattern.compile("\\bREDEFINES\\b", Pattern.CASE_INSENSITIVE);
    @Override protected String getRuleKey() { return "RedefinesClause"; }
    @Override
    public void analyze(SensorContext context, InputFile inputFile, String content) {
        List<String> lines = getLines(content);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (!isCommentLine(line) && REDEFINES.matcher(line).find()) {
                reportIssue(context, inputFile, i + 1,
                        "REDEFINES detectado. COBOL permite redefinir a nivel subordinado y " +
                        "con tamaño mayor. Verifique compatibilidad con la norma 85 COBOL si se requiere portabilidad.");
            }
        }
    }
}
