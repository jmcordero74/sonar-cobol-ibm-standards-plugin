# Contribuir al Proyecto

¡Gracias por tu interés en contribuir! 🎉

## Cómo Contribuir

### 1. Reportar Bugs

Abre un [issue](https://github.com/jmcordero74/sonar-cobol-ibm-standards-plugin/issues) con:
- Descripción del problema
- Pasos para reproducirlo
- Versión de SonarQube
- Ejemplo de código COBOL (si aplica)

### 2. Sugerir Mejoras

Abre un [issue](https://github.com/jmcordero74/sonar-cobol-ibm-standards-plugin/issues) con:
- Descripción de la mejora
- Caso de uso
- Beneficio esperado

### 3. Enviar Pull Requests

1. **Fork** el repositorio
2. **Crea una rama**: `git checkout -b feature/nueva-regla`
3. **Haz tus cambios**
4. **Añade tests** si es posible
5. **Commit**: `git commit -m "Añadir regla para detectar X"`
6. **Push**: `git push origin feature/nueva-regla`
7. **Abre un Pull Request**

### Requisitos para PRs

- ✅ Código compila: `mvn clean package`
- ✅ Tests pasan: `mvn test`
- ✅ Descripción clara del cambio
- ✅ Referencia a issue (si existe)

## Desarrollo Local

```bash
# Clonar
git clone https://github.com/jmcordero74/sonar-cobol-ibm-standards-plugin.git
cd sonar-cobol-ibm-standards-plugin

# Compilar
mvn clean package

# Ejecutar tests
mvn test

# Instalar en SonarQube local
cp target/sonar-cobol-ibm-standards-plugin-1.0.0.jar $SONARQUBE_HOME/extensions/plugins/
```

## Código de Conducta

- Sé respetuoso
- Acepta críticas constructivas
- Enfócate en lo mejor para el proyecto

## Licencia

Al contribuir, aceptas que tus contribuciones se licencien bajo Apache License 2.0.
