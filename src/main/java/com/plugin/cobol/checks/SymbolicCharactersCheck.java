package com.plugin.cobol.checks;

import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.fs.InputFile;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Regla: SymbolicCharacters
 * COBOL: La cláusula SYMBOLIC CHARACTERS no está permitida cuando una página de códigos
 * multibyte está indicada por la configuración regional (locale con MBCS).
 * Referencia: Tabla - Párrafo SPECIAL-NAMES
 */
public class SymbolicCharactersCheck extends AbstractCobolCheck {
    private static final Pattern SYMBOLIC_CHARS = Pattern.compile(
            "\\bSYMBOLIC\\s+CHARACTERS\\b", Pattern.CASE_INSENSITIVE);
    @Override protected String getRuleKey() { return "SymbolicCharacters"; }
    @Override
    public void analyze(SensorContext context, InputFile inputFile, String content) {
        List<String> lines = getLines(content);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (!isCommentLine(line) && SYMBOLIC_CHARS.matcher(line).find()) {
                reportIssue(context, inputFile, i + 1,
                        "SYMBOLIC CHARACTERS: COBOL no permite esta cláusula cuando se utiliza " +
                        "una página de códigos multibyte (configuración regional MBCS). " +
                        "Verifique la compatibilidad con su entorno.");
            }
        }
    }
}
