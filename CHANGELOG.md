# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/).

## [1.0.0] - 2024-03-02

### Added
- 71 rules to detect IBM COBOL extensions
- Duplicate code detection (CPD)
- "Cobol Analyzer" quality profile
- Support for SonarQube 9.9+
- Full documentation in README
- Unit tests with JUnit 5
- Apache 2.0 license

### Implemented Rules

#### IDENTIFICATION DIVISION
- PROGRAM-ID with names up to 160 characters
- RECURSIVE clause
- ID abbreviation for IDENTIFICATION

#### DATA DIVISION
- LOCAL-STORAGE SECTION
- USAGE NATIONAL and extensions
- PICTURE with up to 50 characters
- Extended OCCURS, REDEFINES
- DATE-FORMAT for Y2K

#### PROCEDURE DIVISION
- GOBACK, ENTRY
- XML PARSE/GENERATE
- CALL with BY VALUE/RETURNING
- Extensions in READ, WRITE, etc.

#### Intrinsic Functions
- ADD-DURATION, SUBTRACT-DURATION
- TRIML, TRIMR
- DATE-TO-YYYYMMDD, YEARWINDOW
- And more...

#### Compiler Directives
- BASIS, *CBL, *CONTROL
- EJECT, SKIP1/2/3
- CALLINTERFACE

### Features
- COBOL syntax analysis
- Detection of IBM vs ANSI 85 extensions
- CPD tokenizer for duplicates
- Compatible with fixed and free format
- Support for .cbl, .cob, .cobol, .cpy files

---

## [Unreleased]

### Planned
- More unit tests
- HTML definitions for all rules
- Code examples in documentation
- Continuous integration (GitHub Actions)

---

[1.0.0]: https://github.com/jmcordero74/sonar-cobol-ibm-standards-plugin/releases/tag/v1.0.0
