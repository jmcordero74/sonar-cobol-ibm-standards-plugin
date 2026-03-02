# Changelog

Todos los cambios notables en este proyecto serán documentados en este archivo.

El formato está basado en [Keep a Changelog](https://keepachangelog.com/es-ES/1.0.0/),
y este proyecto adhiere a [Semantic Versioning](https://semver.org/lang/es/).

## [1.0.0] - 2024-03-02

### Añadido
- 71 reglas para detectar extensiones IBM COBOL
- Detección de código duplicado (CPD)
- Perfil de calidad "Cobol Analyzer"
- Soporte para SonarQube 9.9+
- Documentación completa en README
- Tests unitarios con JUnit 5
- Licencia Apache 2.0

### Reglas Implementadas

#### IDENTIFICATION DIVISION
- PROGRAM-ID con nombres hasta 160 caracteres
- Cláusula RECURSIVE
- Abreviatura ID para IDENTIFICATION

#### DATA DIVISION
- LOCAL-STORAGE SECTION
- USAGE NATIONAL y extensiones
- PICTURE con hasta 50 caracteres
- OCCURS, REDEFINES extendidos
- DATE-FORMAT para Y2K

#### PROCEDURE DIVISION
- GOBACK, ENTRY
- XML PARSE/GENERATE
- CALL con BY VALUE/RETURNING
- Extensiones en READ, WRITE, etc.

#### Funciones Intrínsecas
- ADD-DURATION, SUBTRACT-DURATION
- TRIML, TRIMR
- DATE-TO-YYYYMMDD, YEARWINDOW
- Y más...

#### Directivas de Compilador
- BASIS, *CBL, *CONTROL
- EJECT, SKIP1/2/3
- CALLINTERFACE

### Características
- Análisis de sintaxis COBOL
- Detección de extensiones IBM vs ANSI 85
- CPD tokenizer para duplicados
- Compatible con formato fijo y libre
- Soporte para archivos .cbl, .cob, .cobol, .cpy

---

## [Unreleased]

### Planeado
- Más tests unitarios
- Definiciones HTML para todas las reglas
- Ejemplos de código en documentación
- Integración continua (GitHub Actions)

---

[1.0.0]: https://github.com/jmcordero74/sonar-cobol-ibm-standards-plugin/releases/tag/v1.0.0
