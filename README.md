# SonarQube COBOL IBM Standards Plugin (Community)

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![SonarQube](https://img.shields.io/badge/SonarQube-9.9%2B-blue)](https://www.sonarqube.org/)
[![Java](https://img.shields.io/badge/Java-11%2B-orange)](https://www.oracle.com/java/)

**Community** plugin for SonarQube that detects the use of IBM COBOL proprietary extensions
and verifies compliance with the ANSI/ISO 85 standard COBOL.

> **⚠️ Disclaimer:** This plugin is a community project, independent and unofficial.
> It is not affiliated with, sponsored by, or approved by **IBM** or **Sonarsource**.
> "IBM" and "IBM COBOL" are trademarks of International Business Machines Corporation.
> "SonarQube" is a trademark of Sonarsource SA.
> The rules are based on IBM public documentation available at:
> https://www.ibm.com/docs/en/cobol-linux-x86/1.2.0

---

## What it does

Analyzes COBOL source code and identifies IBM extensions that are not part of the standard COBOL 85
specification (ANSI X3.23-1985 / ISO 1989:1985). Useful for:

- Auditing COBOL code portability
- Documenting IBM platform dependencies
- Detecting duplicate code blocks (CPD)
- Establishing a standard compliance baseline

## Requirements

- SonarQube 9.9 LTS or higher
- Java 11 or higher
- Maven 3.6+ (to compile)

## Installation

### Option A: From the precompiled JAR

```bash
cp sonar-cobol-ibm-standards-plugin-1.0.0.jar $SONARQUBE_HOME/extensions/plugins/
# Restart SonarQube
$SONARQUBE_HOME/bin/linux-x86-64/sonar.sh restart
```

### Option B: Compile from source

```bash
git clone https://github.com/jmcordero74/sonar-cobol-ibm-standards-plugin.git
cd sonar-cobol-ibm-standards-plugin
mvn clean package
cp target/sonar-cobol-ibm-standards-plugin-1.0.0.jar $SONARQUBE_HOME/extensions/plugins/
```

## Project configuration

Create a `sonar-project.properties` file at the root of your COBOL project:

```properties
sonar.projectKey=my-cobol-project
sonar.projectName=My COBOL Project
sonar.projectVersion=1.0

sonar.sources=.
sonar.language=cobol
sonar.cobol.file.suffixes=.cbl,.cob,.cobol,.cpy,.CBL,.COB

# Encoding: ISO-8859-1 for mainframe code, UTF-8 for modern development
sonar.sourceEncoding=ISO-8859-1

sonar.host.url=http://localhost:9000
sonar.token=YOUR_TOKEN
```

Then run:

```bash
cd /path/to/your/project
sonar-scanner
```

## Included rules (71 rules)

| Area | Example rules |
|------|--------------|
| IDENTIFICATION DIVISION | RECURSIVE, name up to 160 chars, ID as abbreviation |
| Characters and Unicode | USAGE NATIONAL, GROUP-USAGE NATIONAL, N"..." literals |
| Literals | X"..." hex, Z"..." null-terminated, floating point, apostrophe as delimiter |
| DATA DIVISION | LOCAL-STORAGE, GLOBAL in LINKAGE, SYNCHRONIZED at level 01 |
| PROCEDURE DIVISION | GOBACK, ENTRY, XML PARSE/GENERATE, BY VALUE in header |
| FILE / I-O | ORGANIZATION LINE SEQUENTIAL, PASSWORD, APPLY WRITE-ONLY |
| Statements | CLOSE NO REWIND, END-IF with NEXT SENTENCE, STOP with literal |
| Intrinsic functions | ADD-DURATION, TRIML, TRIMR, DATE-TO-YYYYMMDD, YEARWINDOW |
| Directives | BASIS, *CBL, *CONTROL, EJECT, SKIP1/2/3, CALLINTERFACE |
| Duplicate detection | CPD tokenizer for duplicate COBOL code blocks |

## Quality profile

The plugin automatically registers the **"Cobol Analyzer"** profile with all 71 rules enabled.

To apply it to your project: `Quality Profiles → COBOL → Cobol Analyzer → Set as Default`

## Author

**jmcordero74** - [GitHub](https://github.com/jmcordero74)

## License

Apache License 2.0 — see [LICENSE](LICENSE)

This plugin may be freely used, modified, and distributed.
IBM and Sonarsource trademarks belong to their respective owners.

## Contributions

Pull requests welcome. Please open an issue before making large changes.

## Support

If you find any issues or have suggestions, please open an [issue](https://github.com/jmcordero74/sonar-cobol-ibm-standards-plugin/issues).
