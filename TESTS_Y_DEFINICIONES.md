# Tests y Definiciones de Reglas - COBOL IBM Standards Plugin

## ✅ Estructura Creada

### Directorios de Tests
```
src/test/
├── java/
│   └── com/plugin/cobol/checks/
│       └── ProgramIdCheckTest.java (EJEMPLO COMPLETO)
└── resources/
```

### Directorios de Definiciones de Reglas
```
src/main/resources/org/sonar/l10n/cobol/rules/cobol-ibm-standards/
├── ProgramIdNaming.json (EJEMPLO COMPLETO)
└── ProgramIdNaming.html (EJEMPLO COMPLETO)
```

## 📋 Archivos de Ejemplo Creados

### 1. ProgramIdCheckTest.java
Test unitario completo con JUnit 5 que incluye:
- ✅ Test para PROGRAM-ID válido
- ✅ Test para nombre demasiado largo (>160 caracteres)
- ✅ Test para nombre con subrayado inicial (válido)
- ✅ Test para nombre solo con subrayado (inválido)
- ✅ Test para nombre con literal alfanumérico
- ✅ Test para abreviatura ID DIVISION

### 2. ProgramIdNaming.json
Definición JSON con:
- title
- type (CODE_SMELL)
- status (ready)
- remediation (5min)
- tags (cobol, identification-division, ibm-extension)
- defaultSeverity (Major)

### 3. ProgramIdNaming.html
Documentación HTML completa con:
- Descripción de extensiones IBM
- Código no conforme (ejemplos)
- Código conforme (ejemplos)
- Referencias a documentación IBM

## 🔧 Dependencias Añadidas al pom.xml

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

Plugin Surefire añadido para ejecutar tests.

## 📝 Plantilla para Crear Más Tests

### Plantilla de Test (copiar y adaptar):

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

class [NombreCheck]Test {

    private SensorContextTester context;
    private [NombreCheck] check;

    @BeforeEach
    void setUp() {
        context = SensorContextTester.create(new java.io.File("."));
        check = new [NombreCheck]();
    }

    @Test
    void testValidCase() throws IOException {
        String code = "       [CÓDIGO COBOL VÁLIDO]\n";
        InputFile inputFile = createInputFile("test.cbl", code);
        check.analyze(context, inputFile, code);
        
        Collection<Issue> issues = context.allIssues();
        assertThat(issues).isEmpty();
    }

    @Test
    void testInvalidCase() throws IOException {
        String code = "       [CÓDIGO COBOL INVÁLIDO]\n";
        InputFile inputFile = createInputFile("test.cbl", code);
        check.analyze(context, inputFile, code);
        
        Collection<Issue> issues = context.allIssues();
        assertThat(issues).hasSize(1);
        assertThat(issues.iterator().next().primaryLocation().message())
            .contains("[TEXTO ESPERADO EN MENSAJE]");
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

### Plantilla JSON (crear archivo [RuleKey].json):

```json
{
  "title": "Título de la regla",
  "type": "CODE_SMELL",
  "status": "ready",
  "remediation": {
    "func": "Constant/Issue",
    "constantCost": "5min"
  },
  "tags": [
    "cobol",
    "ibm-extension",
    "[categoria-especifica]"
  ],
  "defaultSeverity": "Major"
}
```

### Plantilla HTML (crear archivo [RuleKey].html):

```html
<p>
Descripción de la extensión IBM y por qué es importante detectarla.
</p>

<h2>Extensiones IBM:</h2>
<ul>
  <li>Característica 1</li>
  <li>Característica 2</li>
</ul>

<h2>Código no conforme:</h2>
<pre>
       CÓDIGO COBOL QUE VIOLA LA REGLA
</pre>

<h2>Código conforme:</h2>
<pre>
       CÓDIGO COBOL CORRECTO
</pre>

<h2>Referencia:</h2>
<p>
IBM COBOL for Linux on x86 v1.2.0<br>
<a href="https://www.ibm.com/docs/en/cobol-linux-x86/1.2.0">Documentación IBM</a>
</p>
```

## 🚀 Comandos para Ejecutar Tests

```bash
# Compilar y ejecutar todos los tests
mvn clean test

# Compilar sin ejecutar tests
mvn clean package -DskipTests

# Ejecutar solo un test específico
mvn test -Dtest=ProgramIdCheckTest
```

## 📊 Reglas Pendientes de Crear Tests y Definiciones

Total: 71 reglas
Creadas: 1 (ProgramIdNaming)
Pendientes: 70

### Lista de reglas pendientes:
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
... (y 60 más)

## 💡 Recomendaciones

1. **Prioridad Alta**: Crear tests para reglas MAJOR y CRITICAL
2. **Prioridad Media**: Crear tests para reglas MINOR
3. **Prioridad Baja**: Crear tests para reglas INFO

4. **Definiciones JSON/HTML**: Crear para todas las reglas para mejor documentación en SonarQube

5. **Cobertura de Tests**: Apuntar a >80% de cobertura de código

## ✅ Cambios Aplicados

1. ✅ Estructura de directorios de tests creada
2. ✅ Dependencias de test añadidas al pom.xml
3. ✅ Plugin Surefire configurado
4. ✅ Test de ejemplo completo (ProgramIdCheckTest)
5. ✅ Definiciones JSON y HTML de ejemplo
6. ✅ REPOSITORY_KEY actualizado en AbstractCobolCheck
7. ✅ Plantillas documentadas para crear más tests

## 📝 Próximos Pasos

1. Usar las plantillas para crear tests de las reglas más importantes
2. Crear definiciones JSON/HTML para todas las 71 reglas
3. Ejecutar `mvn test` para verificar que los tests pasan
4. Añadir más casos de test (edge cases) según sea necesario
