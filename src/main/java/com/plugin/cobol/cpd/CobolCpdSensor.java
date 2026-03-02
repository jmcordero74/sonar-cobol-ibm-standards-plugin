package com.plugin.cobol.cpd;

import com.plugin.cobol.language.CobolLanguage;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.SensorDescriptor;
import org.sonar.api.batch.sensor.cpd.NewCpdTokens;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Sensor CPD para COBOL.
 * Usa ÚNICAMENTE la API de SonarQube (sonar-plugin-api provided).
 * Sin dependencias de PMD ni librerías externas.
 */
public class CobolCpdSensor implements Sensor {

    private static final Logger LOG = LoggerFactory.getLogger(CobolCpdSensor.class);

    private static final Set<String> KEYWORDS = new HashSet<>(Arrays.asList(
            "IDENTIFICATION","ENVIRONMENT","DATA","PROCEDURE","DIVISION","SECTION",
            "WORKING-STORAGE","LOCAL-STORAGE","LINKAGE","FILE",
            "PIC","PICTURE","USAGE","VALUE","OCCURS","REDEFINES",
            "COMP","COMP-1","COMP-2","COMP-3","COMP-4","COMP-5",
            "COMPUTATIONAL","BINARY","PACKED-DECIMAL","DISPLAY","NATIONAL",
            "POINTER","INDEXED","BY","DEPENDING","ON","GLOBAL","EXTERNAL",
            "SYNCHRONIZED","BLANK","WHEN","ZERO",
            "MOVE","ADD","SUBTRACT","MULTIPLY","DIVIDE","COMPUTE",
            "IF","ELSE","END-IF","EVALUATE","OTHER","END-EVALUATE",
            "PERFORM","UNTIL","VARYING","TIMES","END-PERFORM",
            "CALL","USING","RETURNING","END-CALL",
            "GO","TO","GOBACK","STOP","RUN","EXIT","CONTINUE",
            "OPEN","CLOSE","READ","WRITE","REWRITE","DELETE","START",
            "INPUT","OUTPUT","I-O","EXTEND",
            "STRING","UNSTRING","INITIALIZE","INSPECT","SEARCH","ALL",
            "SET","SORT","MERGE","RELEASE","RETURN","ACCEPT",
            "XML","PARSE","GENERATE",
            "AT","END","NOT","OVERFLOW","SIZE","ERROR",
            "INVALID","KEY","INTO","FROM","CORRESPONDING","CORR",
            "GIVING","REMAINDER","THROUGH","THRU","IN","OF",
            "SPACES","SPACE","ZEROS","ZEROES","HIGH-VALUES","HIGH-VALUE",
            "LOW-VALUES","LOW-VALUE","QUOTES","QUOTE","NULL","NULLS",
            "EQUAL","GREATER","LESS","THAN","OR","AND",
            "NUMERIC","ALPHABETIC","POSITIVE","NEGATIVE",
            "END-ADD","END-SUBTRACT","END-MULTIPLY","END-DIVIDE",
            "END-COMPUTE","END-STRING","END-UNSTRING",
            "END-READ","END-WRITE","END-REWRITE","END-DELETE",
            "END-START","END-RETURN","END-SEARCH","ENTRY"
    ));

    @Override
    public void describe(SensorDescriptor descriptor) {
        descriptor.name("COBOL CPD Sensor").onlyOnLanguage(CobolLanguage.KEY);
    }

    @Override
    public void execute(SensorContext context) {
        FileSystem fs = context.fileSystem();
        Iterable<InputFile> files = fs.inputFiles(fs.predicates().hasLanguage(CobolLanguage.KEY));
        int ok = 0, err = 0;
        for (InputFile f : files) {
            try { tokenizeFile(context, f); ok++; }
            catch (Exception e) { LOG.warn("CPD error {}: {}", f.filename(), e.getMessage()); err++; }
        }
        LOG.info("CPD COBOL: {} archivos tokenizados, {} errores", ok, err);
    }

    private void tokenizeFile(SensorContext context, InputFile inputFile) throws IOException {
        String[] lines = inputFile.contents().split("\\r?\\n", -1);
        NewCpdTokens cpd = context.newCpdTokens().onFile(inputFile);

        for (int i = 0; i < lines.length; i++) {
            int lineNum = i + 1;
            String line = lines[i];
            if (line.trim().isEmpty() || isCommentLine(line)) continue;

            String area = contentArea(line);
            if (area.trim().isEmpty() || area.trim().startsWith("*>")) continue;
            int ci = area.indexOf("*>");
            if (ci > 0) area = area.substring(0, ci);

            addTokensFromLine(area.toUpperCase(), lineNum, inputFile, cpd);
        }
        cpd.save();
    }

    private void addTokensFromLine(String area, int lineNum, InputFile file, NewCpdTokens cpd) {
        int pos = 0, len = area.length();
        while (pos < len) {
            while (pos < len && isSep(area.charAt(pos))) pos++;
            if (pos >= len) break;

            int start = pos;
            char c = area.charAt(pos);
            int end;

            if (c == '"' || c == '\'') {
                end = pos + 1;
                while (end < len && area.charAt(end) != c) end++;
                if (end < len) end++;
            } else {
                end = pos;
                while (end < len && !isSep(area.charAt(end))) end++;
            }

            String raw = area.substring(start, end).trim();
            pos = end;
            if (raw.isEmpty() || raw.equals(".")) continue;

            String token = normalize(raw);
            if (token == null) continue;

            // columna = offset área (6) + posición en área
            int colStart = 6 + start;
            int colEnd   = Math.max(colStart + 1, 6 + end);
            try {
                cpd.addToken(file.newRange(lineNum, colStart, lineNum, colEnd), token);
            } catch (Exception ignored) {}
        }
    }

    private String normalize(String raw) {
        String t = (raw.endsWith(".") && raw.length() > 1) ? raw.substring(0, raw.length() - 1) : raw;
        if (t.isEmpty()) return null;
        if (KEYWORDS.contains(t)) return t;
        if (isLevelNumber(t)) return "LVL";
        if (t.matches("[+-]?\\d+(\\.\\d+)?([Ee][+-]?\\d+)?")) return "NUM";
        if (t.matches("[XZNxzn][\"'][^\"']*[\"']?")) return "STR";
        if (t.startsWith("\"") || t.startsWith("'")) return "STR";
        if (t.matches("[A-Z#@$][A-Z0-9#@$_-]*")) return "ID";
        if (t.matches("[=<>+\\-*/()]+")) return t;
        return null;
    }

    private boolean isSep(char c) { return c == ' ' || c == ',' || c == ';'; }

    private boolean isCommentLine(String line) {
        if (line.length() >= 7) { char c = line.charAt(6); return c == '*' || c == '/'; }
        return line.trim().startsWith("*");
    }

    private String contentArea(String line) {
        if (line.length() <= 6) return "";
        return line.length() > 72 ? line.substring(6, 72) : line.substring(6);
    }

    private boolean isLevelNumber(String t) {
        try { int n = Integer.parseInt(t); return (n >= 1 && n <= 49) || n == 66 || n == 77 || n == 88; }
        catch (NumberFormatException e) { return false; }
    }
}
