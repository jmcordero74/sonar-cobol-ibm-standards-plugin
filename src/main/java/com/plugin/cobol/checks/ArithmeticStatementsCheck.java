package com.plugin.cobol.checks;

import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.fs.InputFile;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Regla: ArithmeticStatements
 * COBOL extiende las sentencias aritméticas:
 * - ADD: compuesto de operandos de más de 18 dígitos
 * - DIVIDE: compuesto de operandos de más de 18 dígitos
 * - MULTIPLY: compuesto de operandos de más de 18 dígitos
 * - SUBTRACT: compuesto de operandos de más de 18 dígitos
 *
 * La norma 85 COBOL limita a 18 dígitos. permite hasta 31 dígitos.
 *
 * Referencia: Tabla - ADD, DIVIDE, MULTIPLY, SUBTRACT
 */
public class ArithmeticStatementsCheck extends AbstractCobolCheck {

    private static final Pattern ARITH_STMT = Pattern.compile(
            "^\\s*(?:ADD|DIVIDE|MULTIPLY|SUBTRACT)\\b", Pattern.CASE_INSENSITIVE);
    // Número con más de 18 dígitos
    private static final Pattern LONG_NUMBER = Pattern.compile("\\b(\\d{19,31})\\b");
    private static final Pattern TOO_LONG_NUMBER = Pattern.compile("\\b(\\d{32,})\\b");

    @Override protected String getRuleKey() { return "ArithmeticStatements"; }

    @Override
    public void analyze(SensorContext context, InputFile inputFile, String content) {
        List<String> lines = getLines(content);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (isCommentLine(line)) continue;

            if (!ARITH_STMT.matcher(line).find()) continue;

            Matcher tooLong = TOO_LONG_NUMBER.matcher(line);
            if (tooLong.find()) {
                reportIssue(context, inputFile, i + 1,
                        String.format("Operando aritmético de %d dígitos excede el máximo COBOL de 31 dígitos.",
                                tooLong.group(1).length()));
                continue;
            }

            Matcher longNum = LONG_NUMBER.matcher(line);
            if (longNum.find()) {
                String stmt = line.trim().toUpperCase().split("\\s+")[0];
                reportIssue(context, inputFile, i + 1,
                        String.format("Sentencia %s con operando de %d dígitos (>18). " +
                                "Es extensión COBOL (norma 85 limita a 18 dígitos, permite hasta 31).",
                                stmt, longNum.group(1).length()));
            }
        }
    }
}
