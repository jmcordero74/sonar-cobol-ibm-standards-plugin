package com.plugin.cobol.checks;

import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.issue.NewIssue;
import org.sonar.api.batch.sensor.issue.NewIssueLocation;
import org.sonar.api.rule.RuleKey;
import org.sonar.api.batch.fs.InputFile;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clase base para todas las reglas de verificación COBOL IBM Standards.
 * Proporciona utilidades para análisis de texto COBOL línea por línea.
 */
public abstract class AbstractCobolCheck {

    /**
     * Clave del repositorio de reglas.
     */
    protected static final String REPOSITORY_KEY = "cobol-ibm-standards";

    /**
     * Analiza el contenido de un archivo COBOL y reporta issues.
     *
     * @param context  contexto del sensor
     * @param inputFile archivo de entrada
     * @param content  contenido del archivo
     */
    public abstract void analyze(SensorContext context, InputFile inputFile, String content);

    /**
     * Retorna la clave de la regla que implementa este check.
     */
    protected abstract String getRuleKey();

    /**
     * Reporta un issue en una línea específica.
     */
    protected void reportIssue(SensorContext context, InputFile inputFile, int line, String message) {
        RuleKey ruleKey = RuleKey.of(REPOSITORY_KEY, getRuleKey());
        NewIssue issue = context.newIssue().forRule(ruleKey);
        NewIssueLocation location = issue.newLocation()
                .on(inputFile)
                .at(inputFile.selectLine(line))
                .message(message);
        issue.at(location).save();
    }

    /**
     * Reporta un issue a nivel de archivo (sin línea específica).
     */
    protected void reportFileIssue(SensorContext context, InputFile inputFile, String message) {
        RuleKey ruleKey = RuleKey.of(REPOSITORY_KEY, getRuleKey());
        NewIssue issue = context.newIssue().forRule(ruleKey);
        NewIssueLocation location = issue.newLocation()
                .on(inputFile)
                .message(message);
        issue.at(location).save();
    }

    /**
     * Divide el contenido en líneas.
     */
    protected List<String> getLines(String content) {
        List<String> lines = new ArrayList<>();
        for (String line : content.split("\\r?\\n")) {
            lines.add(line);
        }
        return lines;
    }

    /**
     * Verifica si una línea es un comentario COBOL (columna 7 = '*' o '/').
     */
    protected boolean isCommentLine(String line) {
        if (line.length() >= 7) {
            char indicator = line.charAt(6);
            return indicator == '*' || indicator == '/';
        }
        return line.trim().startsWith("*") || line.trim().startsWith("*>");
    }

    /**
     * Verifica si una línea es un comentario en línea IBM (*>).
     */
    protected boolean isInlineComment(String line) {
        return line.contains("*>");
    }

    /**
     * Extrae el área B de una línea en formato fijo (columnas 12-72).
     */
    protected String getAreaB(String line) {
        if (line.length() > 11) {
            return line.length() > 72 ? line.substring(11, 72) : line.substring(11);
        }
        return "";
    }

    /**
     * Extrae el área A de una línea en formato fijo (columnas 8-11).
     */
    protected String getAreaA(String line) {
        if (line.length() > 7) {
            return line.length() > 11 ? line.substring(7, 11) : line.substring(7);
        }
        return "";
    }

    /**
     * Busca un patrón en el contenido y retorna las líneas coincidentes.
     */
    protected List<Integer> findMatchingLines(List<String> lines, Pattern pattern) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            if (!isCommentLine(lines.get(i)) && pattern.matcher(lines.get(i).toUpperCase()).find()) {
                result.add(i + 1); // líneas son 1-based
            }
        }
        return result;
    }

    /**
     * Convierte el contenido a mayúsculas ignorando literales.
     */
    protected String normalizeCobol(String line) {
        return line.toUpperCase().trim();
    }
}
