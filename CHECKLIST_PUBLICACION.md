# ✅ Checklist para Publicación en GitHub

## 🔴 CRÍTICO - Debe hacerse antes de publicar

- [ ] **Cambiar KEY del lenguaje de "cobol" a "cobol-ibm"** para evitar conflictos
  - Archivos a modificar:
    - `src/main/java/com/plugin/cobol/language/CobolLanguage.java`
    - `src/main/java/com/plugin/cobol/rules/CobolRulesDefinition.java`
    - `src/main/java/com/plugin/cobol/language/CobolQualityProfile.java`
    - `src/main/java/com/plugin/cobol/CobolSensor.java`
    - `src/main/java/com/plugin/cobol/cpd/CobolCpdSensor.java` (si existe)

- [ ] **Unificar nombres del artefacto**
  - Cambiar `artifactId` en pom.xml a: `sonar-cobol-ibm-standards-plugin`
  - Cambiar `pluginKey` a: `cobol-ibm-standards`
  - Actualizar README.md con el nombre correcto del JAR

- [ ] **Actualizar URLs en pom.xml**
  - Descomentar y actualizar `<url>` con tu repositorio GitHub
  - Descomentar y actualizar `<pluginUrl>` en la configuración del plugin

## ⚠️ IMPORTANTE - Recomendado antes de publicar

- [ ] **Añadir información de desarrollador en pom.xml**
  ```xml
  <developers>
      <developer>
          <name>Tu Nombre</name>
          <email>tu@email.com</email>
      </developer>
  </developers>
  ```

- [ ] **Actualizar README.md**
  - Cambiar `TU_USUARIO` por tu usuario real de GitHub
  - Añadir badges (license, build status)
  - Verificar que todos los comandos sean correctos

- [ ] **Crear CHANGELOG.md**
  - Documentar versión 1.0.0 inicial

- [ ] **Verificar LICENSE**
  - Actualizar año si es necesario
  - Añadir tu nombre/organización en Copyright

## ℹ️ OPCIONAL - Mejoras futuras

- [ ] Añadir tests unitarios
- [ ] Crear CONTRIBUTING.md
- [ ] Añadir ejemplos de código en las descripciones de reglas
- [ ] Configurar GitHub Actions para CI/CD
- [ ] Crear releases automáticas
- [ ] Añadir documentación extendida en Wiki

## 📝 Verificación final

- [ ] Compilar: `mvn clean package`
- [ ] Verificar que el JAR se genera correctamente
- [ ] Probar instalación en SonarQube local
- [ ] Verificar que las 71 reglas aparecen en SonarQube
- [ ] Analizar un proyecto COBOL de prueba
- [ ] Verificar que no hay conflictos con otros plugins

## 🚀 Publicación

- [ ] Crear repositorio en GitHub
- [ ] Push del código
- [ ] Crear release v1.0.0
- [ ] Adjuntar JAR al release
- [ ] Anunciar en comunidades COBOL/SonarQube
