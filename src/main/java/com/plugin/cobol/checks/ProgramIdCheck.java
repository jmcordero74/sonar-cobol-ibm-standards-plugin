package com.plugin.cobol.checks;

import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.fs.InputFile;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Regla: ProgramIdNaming
 *
 * Verifica que el párrafo PROGRAM-ID cumpla con las extensiones COBOL:
 * - El nombre puede ser un literal alfanumérico (entre comillas)
 * - El nombre puede comenzar con subrayado
 * - El nombre puede tener hasta 160 caracteres
 * - ID es abreviatura válida para IDENTIFICATION
 * - El punto después del nombre es opcional (IBM relaja la norma 85)
 *
 * Referencia: IBM COBOL for Linux on x86 1.2.0 - IDENTIFICATION DIVISION
 */
public class ProgramIdCheck extends AbstractCobolCheck {

    private static final Pattern PROGRAM_ID_PATTERN =
            Pattern.compile("(?:PROGRAM-ID|PROGRAM\\s+ID)\\s*\\.?\\s*(\\S+)", Pattern.CASE_INSENSITIVE);

    private static final int MAX_PROGRAM_NAME_LENGTH = 160;

    @Override
    protected String getRuleKey() {
        return "ProgramIdNaming";
    }

    @Override
    public void analyze(SensorContext context, InputFile inputFile, String content) {
        List<String> lines = getLines(content);

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (isCommentLine(line)) continue;

            Matcher matcher = PROGRAM_ID_PATTERN.matcher(line);
            if (matcher.find()) {
                String programName = matcher.group(1).replaceAll("[\"'.]", "").trim();

                // Verificar longitud máxima
                if (programName.length() > MAX_PROGRAM_NAME_LENGTH) {
                    reportIssue(context, inputFile, i + 1,
                            String.format("El nombre del programa '%s' excede los 160 caracteres permitidos por IBM COBOL (%d caracteres).",
                                    programName, programName.length()));
                }

                // El nombre puede comenzar con subrayado (extensión IBM)
                // pero no puede ser solo un subrayado
                if (programName.equals("_")) {
                    reportIssue(context, inputFile, i + 1,
                            "El nombre del programa no puede ser únicamente un subrayado.");
                }

                // Verificar caracteres inválidos en el nombre
                if (!programName.matches("[A-Za-z0-9_#@$-]+")) {
                    reportIssue(context, inputFile, i + 1,
                            String.format("El nombre del programa '%s' contiene caracteres no permitidos por IBM COBOL.", programName));
                }
            }
        }
    }
}
