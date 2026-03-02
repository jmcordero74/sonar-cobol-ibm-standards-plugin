package com.plugin.cobol.checks;

import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.fs.InputFile;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Regla: PictureClauseLength
 * Verifica que la cláusula PICTURE cumpla las extensiones IBM COBOL:
 * - Máximo 50 caracteres de imagen (IBM), 30 en norma 85
 * - Símbolos G y N (DBCS/Nacional)
 * - Símbolo E (coma flotante externa)
 * - Máximo 31 dígitos para elementos numéricos DISPLAY/PACKED-DECIMAL
 */
public class PictureClauseCheck extends AbstractCobolCheck {

    private static final Pattern PIC_PATTERN = Pattern.compile(
            "\\bPIC(?:TURE)?\\s+(?:IS\\s+)?(\\S+)", Pattern.CASE_INSENSITIVE);

    private static final int MAX_IBM_PIC_LENGTH = 50;
    private static final int MAX_STANDARD_PIC_LENGTH = 30;

    @Override protected String getRuleKey() { return "PictureClauseLength"; }

    @Override
    public void analyze(SensorContext context, InputFile inputFile, String content) {
        List<String> lines = getLines(content);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (isCommentLine(line)) continue;

            Matcher matcher = PIC_PATTERN.matcher(line);
            while (matcher.find()) {
                String picString = matcher.group(1).replaceAll("[.,;]$", "");

                // Verificar longitud de imagen
                int picLength = calculatePicLength(picString);

                if (picLength > MAX_IBM_PIC_LENGTH) {
                    reportIssue(context, inputFile, i + 1,
                            String.format("La cláusula PICTURE '%s' tiene %d caracteres, excediendo el máximo IBM de %d.",
                                    picString, picLength, MAX_IBM_PIC_LENGTH));
                } else if (picLength > MAX_STANDARD_PIC_LENGTH) {
                    reportIssue(context, inputFile, i + 1,
                            String.format("La cláusula PICTURE '%s' tiene %d caracteres de imagen, " +
                                    "excediendo los %d de la norma 85 COBOL. Es extensión IBM (máximo %d).",
                                    picString, picLength, MAX_STANDARD_PIC_LENGTH, MAX_IBM_PIC_LENGTH));
                }

                // Verificar símbolos IBM extendidos G, N, E
                if (picString.toUpperCase().contains("G")) {
                    reportIssue(context, inputFile, i + 1,
                            "El símbolo G en PICTURE es extensión IBM COBOL para datos DBCS.");
                }
                if (picString.toUpperCase().contains("E") && !picString.toUpperCase().contains("9")) {
                    reportIssue(context, inputFile, i + 1,
                            "El símbolo E en PICTURE es extensión IBM COBOL para coma flotante externa.");
                }
            }
        }
    }

    /**
     * Calcula la longitud expandida de un string PICTURE.
     * Por ejemplo: 9(5) = 5, X(10) = 10, 999 = 3
     */
    private int calculatePicLength(String pic) {
        // Eliminar repeticiones como 9(5) -> 5 veces
        /*String expanded = pic.replaceAll("([A-Z9X*+\\-/,.$])\\((\\d+)\\)", (match) -> {
            // Para conteo simplificado, retornar el número entre paréntesis
            return match;
        });*/

        // Contar caracteres significativos
        int count = 0;
        int i = 0;
        while (i < pic.length()) {
            char c = pic.charAt(i);
            if (c == '(') {
                // Encontrar el número entre paréntesis
                int end = pic.indexOf(')', i);
                if (end > 0) {
                    try {
                        int repetition = Integer.parseInt(pic.substring(i + 1, end));
                        count += repetition - 1; // El símbolo ya fue contado
                        i = end + 1;
                        continue;
                    } catch (NumberFormatException e) {
                        // ignorar
                    }
                }
            } else if (Character.isLetterOrDigit(c) || "9X*+\\-/,.$".indexOf(c) >= 0) {
                count++;
            }
            i++;
        }
        return Math.max(count, pic.replaceAll("[()]", "").replaceAll("\\d+", "").length());
    }
}
