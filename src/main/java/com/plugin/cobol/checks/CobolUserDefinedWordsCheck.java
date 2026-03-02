package com.plugin.cobol.checks;

import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.fs.InputFile;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Regla: CobolUserDefinedWords
 * COBOL permite palabras definidas por el usuario con caracteres multibyte
 * y nombres del sistema en caracteres multibyte. El subrayado puede incluirse
 * en palabras definidas por el usuario (pero no como primer carácter en estándar).
 * En : el subrayado SÍ puede ser primer carácter.
 */
public class CobolUserDefinedWordsCheck extends AbstractCobolCheck {
    // Detectar identificadores que empiezan con subrayado (permitido en IBM, no en norma 85)
    private static final Pattern UNDERSCORE_FIRST = Pattern.compile(
            "^\\s*\\d{1,2}\\s+_[A-Za-z0-9_#@$-]+", Pattern.CASE_INSENSITIVE);

    @Override protected String getRuleKey() { return "CobolUserDefinedWords"; }

    @Override
    public void analyze(SensorContext context, InputFile inputFile, String content) {
        List<String> lines = getLines(content);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (!isCommentLine(line) && UNDERSCORE_FIRST.matcher(line).find()) {
                reportIssue(context, inputFile, i + 1,
                        "Nombre de dato que comienza con subrayado detectado. " +
                        "Es extensión IBM COBOL (la norma 85 no permite subrayado como primer carácter en palabras definidas por usuario).");
            }
        }
    }
}
