# Tests and Rule Definitions - COBOL IBM Standards Plugin

## ✅ Created Structure

### Test Directories
```
src/test/
├── java/
│   └── com/plugin/cobol/checks/
│       └── ProgramIdCheckTest.java (COMPLETE EXAMPLE)
└── resources/
```

### Rule Definition Directories
```
src/main/resources/org/sonar/l10n/cobol/rules/cobol-ibm-standards/
├── ProgramIdNaming.json (COMPLETE EXAMPLE)
└── ProgramIdNaming.html (COMPLETE EXAMPLE)
```

## 📋 Example Files Created

### 1. ProgramIdCheckTest.java
Complete unit test with JUnit 5 that includes:
- ✅ Test for valid PROGRAM-ID
- ✅ Test for name too long (>160 characters)
- ✅ Test for name with leading underscore (valid)
- ✅ Test for name with only underscore (invalid)
- ✅ Test for name with alphanumeric literal
- ✅ Test for ID DIVISION abbreviation

### 2. ProgramIdNaming.json
JSON definition with:
- title
- type (CODE_SMELL)
- status (ready)
- remediation (5min)
- tags (cobol, identification-division, ibm-extension)
- defaultSeverity (Major)

### 3. ProgramIdNaming.html
Complete HTML documentation with:
- Description of IBM extensions
- Non-compliant code (examples)
- Compliant code (examples)
- References to IBM documentation

## 🔧 Dependencies Added to pom.xml

```xml
<!-- Test dependencies -->
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>5.10.1</version>
    <scope>test</scope>
</dependency>

<dependency>
    <groupId>org.assertj</groupId>
    <artifactId>assertj-core</artifactId>
    <version>3.24.2</version>
    <scope>test</scope>
</dependency>

<dependency>
    <groupId>org.sonarsource.api.plugin</groupId>
    <artifactId>sonar-plugin-api-test-fixtures</artifactId>
    <version>${sonar.apiVersion}</version>
    <scope>test</scope>
</dependency>
```

Surefire plugin added to run tests.

## 📝 Template to Create More Tests

### Test Template (copy and adapt):

```java
package com.plugin.cobol.checks;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.fs.internal.TestInputFileBuilder;
import org.sonar.api.batch.sensor.internal.SensorContextTester;
import org.sonar.api.batch.sensor.issue.Issue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

class [CheckName]Test {

    private SensorContextTester context;
    private [CheckName] check;

    @BeforeEach
    void setUp() {
        context = SensorContextTester.create(new java.io.File("."));
        check = new [CheckName]();
    }

    @Test
    void testValidCase() throws IOException {
        String code = "       [VALID COBOL CODE]\n";
        InputFile inputFile = createInputFile("test.cbl", code);
        check.analyze(context, inputFile, code);

        Collection<Issue> issues = context.allIssues();
        assertThat(issues).isEmpty();
    }

    @Test
    void testInvalidCase() throws IOException {
        String code = "       [INVALID COBOL CODE]\n";
        InputFile inputFile = createInputFile("test.cbl", code);
        check.analyze(context, inputFile, code);

        Collection<Issue> issues = context.allIssues();
        assertThat(issues).hasSize(1);
        assertThat(issues.iterator().next().primaryLocation().message())
            .contains("[EXPECTED TEXT IN MESSAGE]");
    }

    private InputFile createInputFile(String filename, String content) {
        return TestInputFileBuilder.create("", filename)
            .setContents(content)
            .setCharset(StandardCharsets.UTF_8)
            .setLanguage("cobol-standar")
            .build();
    }
}
```

### JSON Template (create file [RuleKey].json):

```json
{
  "title": "Rule title",
  "type": "CODE_SMELL",
  "status": "ready",
  "remediation": {
    "func": "Constant/Issue",
    "constantCost": "5min"
  },
  "tags": [
    "cobol",
    "ibm-extension",
    "[specific-category]"
  ],
  "defaultSeverity": "Major"
}
```

### HTML Template (create file [RuleKey].html):

```html
<p>
Description of the IBM extension and why it is important to detect it.
</p>

<h2>IBM Extensions:</h2>
<ul>
  <li>Feature 1</li>
  <li>Feature 2</li>
</ul>

<h2>Non-compliant code:</h2>
<pre>
       COBOL CODE THAT VIOLATES THE RULE
</pre>

<h2>Compliant code:</h2>
<pre>
       CORRECT COBOL CODE
</pre>

<h2>Reference:</h2>
<p>
IBM COBOL for Linux on x86 v1.2.0<br>
<a href="https://www.ibm.com/docs/en/cobol-linux-x86/1.2.0">IBM Documentation</a>
</p>
```

## 🚀 Commands to Run Tests

```bash
# Compile and run all tests
mvn clean test

# Compile without running tests
mvn clean package -DskipTests

# Run a specific test
mvn test -Dtest=ProgramIdCheckTest
```

## 📊 Rules Pending Tests and Definitions

Total: 71 rules
Created: 1 (ProgramIdNaming)
Pending: 70

### List of pending rules:
1. RecursiveClause
2. NationalCharacterSupport
3. HexLiteral
4. NullTerminatedLiteral
5. FloatingPointLiteral
6. FixedPointDigitsLimit
7. LocalStorageSection
8. PictureClauseLength
9. UsageClause
10. OccursClause
... (and 60 more)

## 💡 Recommendations

1. **High Priority**: Create tests for MAJOR and CRITICAL rules
2. **Medium Priority**: Create tests for MINOR rules
3. **Low Priority**: Create tests for INFO rules

4. **JSON/HTML Definitions**: Create for all rules for better documentation in SonarQube

5. **Test Coverage**: Aim for >80% code coverage

## 📝 Steps to Create Tests for Each Rule

1. **Copy** `ProgramIdCheckTest.java` as a template
2. **Rename** the class to the check name
3. **Adapt** the test cases to the specific behavior
4. **Create** example COBOL code (valid and invalid)
5. **Verify** that the error messages are correct
6. **Run** `mvn test` to validate

## 📝 Steps to Create Definitions for Each Rule

1. **Create** `[RuleKey].json` file with metadata
2. **Create** `[RuleKey].html` file with documentation
3. **Include** examples of compliant and non-compliant code
4. **Add** references to IBM documentation
5. **Verify** it appears correctly in SonarQube

## ✅ Applied Changes

1. ✅ Test directory structure created
2. ✅ Test dependencies added to pom.xml
3. ✅ Surefire plugin configured
4. ✅ Complete example test (ProgramIdCheckTest)
5. ✅ Example JSON and HTML definitions
6. ✅ REPOSITORY_KEY updated in AbstractCobolCheck
7. ✅ Templates documented to create more tests

## 📝 Next Steps

1. Use the templates to create tests for the most important rules
2. Create JSON/HTML definitions for all 71 rules
3. Run `mvn test` to verify the tests pass
4. Add more test cases (edge cases) as needed
