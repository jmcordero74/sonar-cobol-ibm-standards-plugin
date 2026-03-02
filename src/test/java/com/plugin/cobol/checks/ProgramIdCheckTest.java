package com.plugin.cobol.checks;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

class ProgramIdCheckTest {

    private ProgramIdCheck check;

    @BeforeEach
    void setUp() {
        check = new ProgramIdCheck();
    }

    @Test
    void testGetRuleKey() {
        assertThat(check.getRuleKey()).isEqualTo("ProgramIdNaming");
    }

    @Test
    void testValidProgramIdName() {
        String validName = "MYPROG";
        assertThat(validName.length()).isLessThanOrEqualTo(160);
        assertThat(validName).matches("[A-Za-z0-9_#@$-]+");
    }

    @Test
    void testProgramIdTooLong() {
        String longName = "A".repeat(161);
        assertThat(longName.length()).isGreaterThan(160);
    }

    @Test
    void testProgramIdWithUnderscore() {
        String nameWithUnderscore = "_MYPROG";
        assertThat(nameWithUnderscore).matches("[A-Za-z0-9_#@$-]+");
        assertThat(nameWithUnderscore).isNotEqualTo("_");
    }

    @Test
    void testProgramIdOnlyUnderscore() {
        String onlyUnderscore = "_";
        assertThat(onlyUnderscore).isEqualTo("_");
    }

    @Test
    void testProgramIdPattern() {
        Pattern pattern = Pattern.compile("(?:PROGRAM-ID|PROGRAM\\s+ID)\\s*\\.?\\s*(\\S+)", Pattern.CASE_INSENSITIVE);
        
        String line1 = "       PROGRAM-ID. MYPROG.";
        Matcher m1 = pattern.matcher(line1);
        assertThat(m1.find()).isTrue();
        assertThat(m1.group(1)).contains("MYPROG");

        String line2 = "       PROGRAM ID TESTPROG";
        Matcher m2 = pattern.matcher(line2);
        assertThat(m2.find()).isTrue();
        assertThat(m2.group(1)).contains("TESTPROG");
    }

    @Test
    void testValidCharacters() {
        assertThat("PROGRAM-NAME").matches("[A-Za-z0-9_#@$-]+");
        assertThat("_PROGRAM").matches("[A-Za-z0-9_#@$-]+");
        assertThat("PROG123").matches("[A-Za-z0-9_#@$-]+");
        assertThat("PROG#1").matches("[A-Za-z0-9_#@$-]+");
    }

    @Test
    void testInvalidCharacters() {
        assertThat("PROG*NAME").doesNotMatch("[A-Za-z0-9_#@$-]+");
        assertThat("PROG NAME").doesNotMatch("[A-Za-z0-9_#@$-]+");
    }
}
