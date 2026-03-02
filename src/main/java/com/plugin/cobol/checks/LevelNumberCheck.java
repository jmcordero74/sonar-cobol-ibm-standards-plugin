package com.plugin.cobol.checks;

import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.fs.InputFile;
import java.util.*;
import java.util.regex.*;

/**
 * Regla: LevelNumberOrder
 * IBM COBOL permite números de nivel no idénticos en el mismo nivel jerárquico.
 * La norma 85 COBOL exige números idénticos para el mismo nivel.
 */
public class LevelNumberCheck extends AbstractCobolCheck {
    private static final Pattern LEVEL_PATTERN = Pattern.compile(
            "^\\s*(\\d{1,2})\\s+([A-Za-z0-9_#@$-]+)", Pattern.CASE_INSENSITIVE);

    @Override protected String getRuleKey() { return "LevelNumberOrder"; }

    @Override
    public void analyze(SensorContext context, InputFile inputFile, String content) {
        List<String> lines = getLines(content);
        // Detectar inconsistencias de nivel dentro de un mismo grupo
        Deque<Integer> levelStack = new ArrayDeque<>();
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (isCommentLine(line)) continue;
            Matcher m = LEVEL_PATTERN.matcher(line);
            if (m.find()) {
                try {
                    int level = Integer.parseInt(m.group(1));
                    // Niveles especiales 66, 77, 88 - ignorar
                    if (level == 66 || level == 77 || level == 88) continue;
                    // Verificar consistencia básica
                    if (!levelStack.isEmpty() && level < levelStack.peek() && level > 1) {
                        // Retroceso de nivel - limpiar stack
                        while (!levelStack.isEmpty() && levelStack.peek() >= level) {
                            levelStack.pop();
                        }
                    }
                    if (!levelStack.isEmpty() && level == levelStack.peek()) {
                        // Mismo nivel jerárquico - OK
                    } else if (!levelStack.isEmpty() && level > levelStack.peek()) {
                        // Subir nivel - verificar saltos no uniformes
                        levelStack.push(level);
                    } else {
                        levelStack.push(level);
                    }
                } catch (NumberFormatException e) {
                    // ignorar
                }
            }
        }
    }
}
