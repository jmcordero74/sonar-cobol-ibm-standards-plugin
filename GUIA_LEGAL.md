# ⚠️ GUÍA LEGAL - Qué Evitar en el Plugin

## ❌ LO QUE NUNCA DEBES DECIR O HACER

### 1. **NO Impliques Afiliación Oficial**

❌ **NUNCA digas:**
- "Plugin oficial de IBM"
- "Aprobado por IBM"
- "Certificado por IBM"
- "Desarrollado por IBM"
- "En colaboración con IBM"
- "Patrocinado por Sonarsource"
- "Plugin oficial de Sonarsource"

✅ **SÍ puedes decir (ya lo haces):**
- "Plugin comunitario"
- "Proyecto independiente"
- "No afiliado con IBM ni Sonarsource"
- "Basado en documentación pública de IBM"

### 2. **NO Uses Logos o Marcas Registradas**

❌ **NUNCA incluyas:**
- Logo de IBM
- Logo de Sonarsource
- Logo de SonarQube
- Cualquier imagen corporativa de estas empresas

✅ **SÍ puedes:**
- Mencionar los nombres en texto (uso descriptivo)
- Incluir disclaimers de marcas registradas (ya lo haces)

### 3. **NO Copies Código Propietario**

❌ **NUNCA incluyas:**
- Código fuente de compiladores IBM
- Bibliotecas propietarias de IBM
- Runtime de IBM COBOL
- Código de plugins comerciales de Sonarsource

✅ **SÍ puedes:**
- Analizar sintaxis pública de COBOL
- Referenciar documentación pública
- Detectar patrones sintácticos

### 4. **NO Hagas Afirmaciones de Garantía**

❌ **NUNCA digas:**
- "Garantiza compatibilidad 100% con IBM"
- "Certificado para producción"
- "Aprobado para uso empresarial"
- "Sin errores garantizado"

✅ **SÍ puedes decir:**
- "Proporcionado 'AS IS' sin garantías" (ya está en LICENSE)
- "Plugin comunitario para análisis"
- "Úsalo bajo tu propio riesgo"

### 5. **NO Ofrezcas Soporte Oficial**

❌ **NUNCA digas:**
- "Soporte oficial de IBM"
- "Contacta a IBM para soporte"
- "Garantía de respuesta en 24h"

✅ **SÍ puedes:**
- "Soporte comunitario vía GitHub Issues"
- "Pull requests bienvenidos"
- "Mejor esfuerzo, sin garantías"

### 6. **NO Modifiques el Disclaimer**

❌ **NUNCA elimines o cambies:**
```
Este plugin es un proyecto de la comunidad, independiente y no oficial.
No está afiliado, patrocinado ni aprobado por IBM ni por Sonarsource.
"IBM" e "IBM COBOL" son marcas registradas de International Business Machines Corporation.
"SonarQube" es una marca registrada de Sonarsource SA.
```

✅ **Mantén siempre visible en:**
- README.md
- pom.xml (description)
- LICENSE

### 7. **NO Uses Nombres Engañosos**

❌ **NUNCA nombres el repo:**
- `ibm-official-cobol-plugin`
- `sonarsource-cobol-plugin`
- `ibm-certified-sonar-plugin`

✅ **SÍ puedes usar (ya lo haces):**
- `sonar-cobol-ibm-standards-plugin` ✅
- Indica claramente que detecta "IBM standards"
- Uso descriptivo, no implica afiliación

## ✅ LO QUE SÍ PUEDES HACER (Ya lo haces)

### 1. **Uso Descriptivo de Marcas**
✅ "Detecta extensiones IBM COBOL"
✅ "Basado en documentación de IBM"
✅ "Plugin para SonarQube"

### 2. **Referencias a Documentación Pública**
✅ Citar: https://www.ibm.com/docs/en/cobol-linux-x86/1.2.0
✅ Mencionar normas públicas (ANSI X3.23-1985)

### 3. **Análisis de Sintaxis Pública**
✅ Detectar palabras clave COBOL
✅ Analizar estructura de código
✅ Identificar patrones sintácticos

### 4. **Licencia Open Source**
✅ Apache License 2.0
✅ Código abierto en GitHub
✅ Libre uso, modificación y distribución

## 📋 CHECKLIST ANTES DE PUBLICAR

Verifica que tu proyecto tenga:

- ✅ Disclaimer visible en README
- ✅ Disclaimer en pom.xml
- ✅ LICENSE con Apache 2.0
- ✅ Reconocimiento de marcas registradas
- ✅ Sin logos de IBM o Sonarsource
- ✅ Sin código propietario
- ✅ Nombre descriptivo (no engañoso)
- ✅ "Community" o "Independent" en descripción

## 🛡️ PROTECCIÓN LEGAL

Tu plugin está protegido por:

1. **Fair Use**: Uso educativo/informativo de documentación pública
2. **Nominative Use**: Uso descriptivo de marcas para identificar tecnología
3. **Apache License 2.0**: Licencia permisiva y reconocida
4. **Disclaimers**: Claros y visibles

## 📞 SI RECIBES UNA NOTIFICACIÓN LEGAL

**Pasos a seguir:**

1. **No entres en pánico** - Es muy poco probable
2. **Lee cuidadosamente** la notificación
3. **Verifica que sea legítima** (no spam/phishing)
4. **Responde educadamente** indicando:
   - Es un proyecto comunitario
   - Uso descriptivo de marcas (legal)
   - Basado en documentación pública
   - Dispuesto a hacer cambios si es necesario
5. **Consulta a un abogado** si es necesario

## 🎯 RESUMEN

**Tu plugin está legalmente correcto porque:**

✅ Tiene disclaimers claros
✅ No implica afiliación
✅ Usa marcas descriptivamente
✅ Basado en documentación pública
✅ Sin código propietario
✅ Licencia compatible

**Riesgo legal: MÍNIMO**

Sigue estas guías y estarás protegido. ¡Publica con confianza! 🚀

---

**Última actualización**: 2024
**Autor**: jmcordero74
**Licencia**: Apache 2.0
