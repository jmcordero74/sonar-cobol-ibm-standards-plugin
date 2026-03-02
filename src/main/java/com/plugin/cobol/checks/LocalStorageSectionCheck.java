package com.plugin.cobol.checks;

import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.fs.InputFile;
import java.util.List;
import java.util.regex.Pattern;

public class LocalStorageSectionCheck extends AbstractCobolCheck {
    private static final Pattern LOCAL_STORAGE = Pattern.compile(
            "\\bLOCAL-STORAGE\\s+SECTION\\b", Pattern.CASE_INSENSITIVE);

    @Override protected String getRuleKey() { return "LocalStorageSection"; }

    @Override
    public void analyze(SensorContext context, InputFile inputFile, String content) {
        List<String> lines = getLines(content);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (!isCommentLine(line) && LOCAL_STORAGE.matcher(line).find()) {
                reportIssue(context, inputFile, i + 1,
                        "LOCAL-STORAGE SECTION es extensión IBM COBOL. " +
                        "Asigna y libera almacenamiento por invocación (memoria de pila). " +
                        "No es parte de la norma 85 COBOL estándar.");
            }
        }
    }
}
