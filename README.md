# SonarQube COBOL IBM Standards Plugin (Community)

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![SonarQube](https://img.shields.io/badge/SonarQube-9.9%2B-blue)](https://www.sonarqube.org/)
[![Java](https://img.shields.io/badge/Java-11%2B-orange)](https://www.oracle.com/java/)

Plugin **comunitario** para SonarQube que detecta el uso de extensiones propietarias de IBM COBOL
y verifica el cumplimiento de la norma ANSI/ISO 85 COBOL estándar.

> **⚠️ Disclaimer:** Este plugin es un proyecto de la comunidad, independiente y no oficial.
> No está afiliado, patrocinado ni aprobado por **IBM** ni por **Sonarsource**.
> "IBM" e "IBM COBOL" son marcas registradas de International Business Machines Corporation.
> "SonarQube" es una marca registrada de Sonarsource SA.
> Las reglas se basan en la documentación pública de IBM disponible en:
> https://www.ibm.com/docs/en/cobol-linux-x86/1.2.0

---

## Qué hace

Analiza código fuente COBOL e identifica extensiones IBM que no forman parte de la norma 85 COBOL
estándar (ANSI X3.23-1985 / ISO 1989:1985). Útil para:

- Auditar portabilidad del código COBOL
- Documentar dependencias de plataforma IBM
- Detectar bloques de código duplicados (CPD)
- Establecer una línea base de cumplimiento de estándares

## Requisitos

- SonarQube 9.9 LTS o superior
- Java 11 o superior
- Maven 3.6+ (para compilar)

## Instalación

### Opción A: Desde el JAR precompilado

```bash
cp sonar-cobol-ibm-standards-plugin-1.0.0.jar $SONARQUBE_HOME/extensions/plugins/
# Reiniciar SonarQube
$SONARQUBE_HOME/bin/linux-x86-64/sonar.sh restart
```

### Opción B: Compilar desde fuentes

```bash
git clone https://github.com/jmcordero74/sonar-cobol-ibm-standards-plugin.git
cd sonar-cobol-ibm-standards-plugin
mvn clean package
cp target/sonar-cobol-ibm-standards-plugin-1.0.0.jar $SONARQUBE_HOME/extensions/plugins/
```

## Configuración del proyecto

Crea un archivo `sonar-project.properties` en la raíz de tu proyecto COBOL:

```properties
sonar.projectKey=mi-proyecto-cobol
sonar.projectName=Mi Proyecto COBOL
sonar.projectVersion=1.0

sonar.sources=.
sonar.language=cobol
sonar.cobol.file.suffixes=.cbl,.cob,.cobol,.cpy,.CBL,.COB

# Encoding: ISO-8859-1 para código de mainframe, UTF-8 para desarrollo moderno
sonar.sourceEncoding=ISO-8859-1

sonar.host.url=http://localhost:9000
sonar.token=TU_TOKEN
```

Luego ejecuta:

```bash
cd /ruta/a/tu/proyecto
sonar-scanner
```

## Reglas incluidas (71 reglas)

| Área | Ejemplos de reglas |
|------|-------------------|
| IDENTIFICATION DIVISION | RECURSIVE, nombre hasta 160 chars, ID como abreviatura |
| Caracteres y Unicode | USAGE NATIONAL, GROUP-USAGE NATIONAL, literales N"..." |
| Literales | X"..." hex, Z"..." nulos, flotantes, apóstrofo como delimitador |
| DATA DIVISION | LOCAL-STORAGE, GLOBAL en LINKAGE, SYNCHRONIZED nivel 01 |
| PROCEDURE DIVISION | GOBACK, ENTRY, XML PARSE/GENERATE, BY VALUE en cabecera |
| FILE / I-O | ORGANIZATION LINE SEQUENTIAL, PASSWORD, APPLY WRITE-ONLY |
| Sentencias | CLOSE NO REWIND, END-IF con NEXT SENTENCE, STOP con literal |
| Funciones intrínsecas | ADD-DURATION, TRIML, TRIMR, DATE-TO-YYYYMMDD, YEARWINDOW |
| Directivas | BASIS, *CBL, *CONTROL, EJECT, SKIP1/2/3, CALLINTERFACE |
| Detección de duplicados | CPD tokenizer para bloques de código COBOL duplicados |

## Perfil de calidad

El plugin registra automáticamente el perfil **"Cobol Analyzer"** con las 71 reglas activadas.

Para aplicarlo a tu proyecto: `Quality Profiles → COBOL → Cobol Analyzer → Set as Default`

## Autor

**jmcordero74** - [GitHub](https://github.com/jmcordero74)

## Licencia

Apache License 2.0 — ver [LICENSE](LICENSE)

Este plugin puede usarse, modificarse y distribuirse libremente.
Las marcas registradas de IBM y Sonarsource pertenecen a sus respectivos propietarios.

## Contribuciones

Pull requests bienvenidos. Por favor abre un issue antes de hacer cambios grandes.

## Soporte

Si encuentras algún problema o tienes sugerencias, por favor abre un [issue](https://github.com/jmcordero74/sonar-cobol-ibm-standards-plugin/issues).
