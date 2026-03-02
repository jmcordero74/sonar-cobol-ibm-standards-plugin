package com.plugin.cobol.rules;

import com.plugin.cobol.language.CobolLanguage;
import org.sonar.api.server.rule.RulesDefinition;

/**
 * Define todas las reglas COBOL Standards en el repositorio de SonarQube.
 * Basado en: https://www.ibm.com/docs/es/cobol-linux-x86/1.2.0?topic=appendixes-extensions-cobol-standards
 */
public class CobolRulesDefinition implements RulesDefinition {

    public static final String REPOSITORY_KEY = "cobol-ibm-standards";
    public static final String REPOSITORY_NAME = "COBOL IBM Standards";

    @Override
    public void define(Context context) {
        NewRepository repository = context.createRepository(REPOSITORY_KEY, CobolLanguage.KEY)
                .setName(REPOSITORY_NAME);

        // ==========================================
        // IDENTIFICATION DIVISION
        // ==========================================
        repository.createRule("ProgramIdNaming")
                .setName("PROGRAM-ID debe ser válido según COBOL")
                .setHtmlDescription("<p>El párrafo PROGRAM-ID permite nombres de hasta 160 caracteres, " +
                        "puede comenzar con subrayado y puede ser un literal alfanumérico. " +
                        "extiende la norma 85 COBOL permitiendo estas variantes.</p>" +
                        "<p>Referencia: COBOL for Linux on x86 v1.2.0 - IDENTIFICATION DIVISION</p>")
                .setSeverity("MAJOR")
                .setType(org.sonar.api.rules.RuleType.CODE_SMELL)
                .addTags("cobol", "identification-division");

        repository.createRule("RecursiveClause")
                .setName("Cláusula RECURSIVE debe documentarse")
                .setHtmlDescription("<p>La cláusula RECURSIVE en PROGRAM-ID es una extensión IBM. " +
                        "Indica que el programa puede llamarse recursivamente.</p>" +
                        "<p>Referencia: COBOL for Linux - RECURSIVE clause</p>")
                .setSeverity("INFO")
                .setType(org.sonar.api.rules.RuleType.CODE_SMELL)
                .addTags("cobol", "identification-division");

        // ==========================================
        // CARACTERES NACIONALES Y LITERALES
        // ==========================================
        repository.createRule("NationalCharacterSupport")
                .setName("Uso de USAGE NATIONAL debe ser válido")
                .setHtmlDescription("<p>COBOL extiende el soporte Unicode con USAGE NATIONAL para UTF-16 " +
                        "y USAGE DISPLAY para UTF-8. La cláusula GROUP-USAGE NATIONAL y literales nacionales " +
                        "(N\"...\", NX\"...\") son extensiones IBM.</p>")
                .setSeverity("MAJOR")
                .setType(org.sonar.api.rules.RuleType.BUG)
                .addTags("cobol", "unicode", "national-characters");

        repository.createRule("HexLiteral")
                .setName("Literales hexadecimales deben usar delimitadores correctos")
                .setHtmlDescription("<p>Los literales hexadecimales alfanuméricos  COBOL deben usar " +
                        "delimitadores X\" o X'. Los literales DBCS usan G\", G', N\" o N'.</p>")
                .setSeverity("MAJOR")
                .setType(org.sonar.api.rules.RuleType.BUG)
                .addTags("cobol", "literals");

        repository.createRule("NullTerminatedLiteral")
                .setName("Literales terminados en nulo deben usar Z\" o Z'")
                .setHtmlDescription("<p>Los literales alfanuméricos terminados en nulo son una extensión  " +
                        "definidos con delimitadores Z\" o Z'.</p>")
                .setSeverity("MINOR")
                .setType(org.sonar.api.rules.RuleType.CODE_SMELL)
                .addTags("cobol", "literals");

        repository.createRule("FloatingPointLiteral")
                .setName("Literales de coma flotante son extensión ")
                .setHtmlDescription("<p>Los literales numéricos de coma flotante son una extensión  " +
                        "no contemplada en la norma 85 COBOL estándar.</p>")
                .setSeverity("MINOR")
                .setType(org.sonar.api.rules.RuleType.CODE_SMELL)
                .addTags("cobol", "literals", "floating-point");

        repository.createRule("FixedPointDigitsLimit")
                .setName("Literales numéricos no deben exceder 31 dígitos")
                .setHtmlDescription("<p>COBOL permite literales numéricos de punto fijo de hasta 31 dígitos. " +
                        "La norma 85 COBOL permite un máximo de 18 dígitos. " +
                        "El uso de más de 18 dígitos es una extensión IBM.</p>")
                .setSeverity("MAJOR")
                .setType(org.sonar.api.rules.RuleType.BUG)
                .addTags("cobol", "literals", "numeric");

        // ==========================================
        // DATA DIVISION
        // ==========================================
        repository.createRule("LocalStorageSection")
                .setName("LOCAL-STORAGE SECTION es extensión IBM")
                .setHtmlDescription("<p>La LOCAL-STORAGE SECTION es una extensión IBM COBOL. " +
                        "Define almacenamiento asignado y liberado en cada invocación (usa memoria de pila). " +
                        "No está en la norma 85 COBOL estándar.</p>")
                .setSeverity("INFO")
                .setType(org.sonar.api.rules.RuleType.CODE_SMELL)
                .addTags("cobol", "data-division", "local-storage");

        repository.createRule("PictureClauseLength")
                .setName("Cláusula PICTURE no debe exceder 50 caracteres de imagen")
                .setHtmlDescription("<p>COBOL permite series de caracteres de imagen de hasta 50 caracteres. " +
                        "La norma 85 COBOL permite un máximo de 30. El uso de 31-50 es una extensión IBM. " +
                        "También se verifican los símbolos G, N y E (coma flotante externa).</p>")
                .setSeverity("MAJOR")
                .setType(org.sonar.api.rules.RuleType.BUG)
                .addTags("cobol", "data-division", "picture");

        repository.createRule("UsageClause")
                .setName("Cláusulas USAGE IBM deben usarse correctamente")
                .setHtmlDescription("<p>COBOL extiende la cláusula USAGE con: NATIVE, COMP-1/2/3/4/5, " +
                        "COMPUTATIONAL-1 al 5, DISPLAY-1, NATIONAL, POINTER, PROCEDURE-POINTER, FUNCTION-POINTER. " +
                        "Estas son extensiones a la norma 85 COBOL.</p>")
                .setSeverity("MAJOR")
                .setType(org.sonar.api.rules.RuleType.CODE_SMELL)
                .addTags("cobol", "data-division", "usage");

        repository.createRule("OccursClause")
                .setName("Cláusula OCCURS debe cumplir reglas IBM")
                .setHtmlDescription("<p>COBOL permite omitir 'integer-1 TO' en tablas de longitud variable, " +
                        "OCCURS DEPENDING ON complejo anidado, e indexación sin INDEXED BY.</p>")
                .setSeverity("MAJOR")
                .setType(org.sonar.api.rules.RuleType.BUG)
                .addTags("cobol", "data-division", "occurs");

        repository.createRule("RedefinesClause")
                .setName("REDEFINES no debe redefinir a tamaño mayor sin justificación")
                .setHtmlDescription("<p>COBOL permite que un elemento de redefinición tenga tamaño mayor " +
                        "que el elemento redefinido, a diferencia de la norma 85 COBOL estándar.</p>")
                .setSeverity("MAJOR")
                .setType(org.sonar.api.rules.RuleType.CODE_SMELL)
                .addTags("cobol", "data-division", "redefines");

        repository.createRule("DateFormatClause")
                .setName("Cláusula DATE-FORMAT debe usarse para campos de fecha")
                .setHtmlDescription("<p>La cláusula DATE-FORMAT es una extensión IBM para el manejo de fechas Y2K. " +
                        "Especifica campos de fecha con ventana o expandidos.</p>")
                .setSeverity("MINOR")
                .setType(org.sonar.api.rules.RuleType.CODE_SMELL)
                .addTags("cobol", "data-division", "date", "y2k");

        repository.createRule("LevelNumberOrder")
                .setName("Números de nivel deben ser consistentes por jerarquía")
                .setHtmlDescription("<p>COBOL permite especificar números de nivel menores que otros " +
                        "números de nivel en el mismo nivel jerárquico. La norma 85 COBOL exige " +
                        "números de nivel idénticos para el mismo nivel.</p>")
                .setSeverity("MINOR")
                .setType(org.sonar.api.rules.RuleType.CODE_SMELL)
                .addTags("cobol", "data-division", "level-numbers");

        // ==========================================
        // PROCEDURE DIVISION - SENTENCIAS
        // ==========================================
        repository.createRule("GobackStatement")
                .setName("GOBACK es extensión IBM preferida a STOP RUN/EXIT PROGRAM")
                .setHtmlDescription("<p>La sentencia GOBACK es una extensión IBM. Funciona como EXIT PROGRAM " +
                        "en programas llamados y como STOP RUN en el programa principal. " +
                        "Es más flexible que las sentencias estándar.</p>")
                .setSeverity("INFO")
                .setType(org.sonar.api.rules.RuleType.CODE_SMELL)
                .addTags("cobol", "procedure-division", "goback");

        repository.createRule("EntryStatement")
                .setName("Sentencia ENTRY es extensión IBM")
                .setHtmlDescription("<p>La sentencia ENTRY establece un punto de entrada alternativo " +
                        "a un subprograma COBOL. Es una extensión IBM no contemplada en la norma estándar.</p>")
                .setSeverity("MAJOR")
                .setType(org.sonar.api.rules.RuleType.CODE_SMELL)
                .addTags("cobol", "procedure-division", "entry");

        repository.createRule("XmlParseStatement")
                .setName("XML PARSE debe usarse correctamente (extensión IBM)")
                .setHtmlDescription("<p>La sentencia XML PARSE es la interfaz COBOL con el analizador XML " +
                        "de alto rendimiento IBM. Es una extensión IBM con registros especiales XML-CODE, " +
                        "XML-EVENT, XML-NTEXT y XML-TEXT.</p>")
                .setSeverity("MAJOR")
                .setType(org.sonar.api.rules.RuleType.BUG)
                .addTags("cobol", "procedure-division", "xml");

        repository.createRule("XmlGenerateStatement")
                .setName("XML GENERATE debe usarse correctamente (extensión IBM)")
                .setHtmlDescription("<p>La sentencia XML GENERATE convierte datos a formato XML. " +
                        "Es una extensión IBM COBOL.</p>")
                .setSeverity("MAJOR")
                .setType(org.sonar.api.rules.RuleType.BUG)
                .addTags("cobol", "procedure-division", "xml");

        repository.createRule("CallStatement")
                .setName("Sentencia CALL debe cumplir extensiones IBM")
                .setHtmlDescription("<p>COBOL extiende CALL con: PROCEDURE-POINTER, FUNCTION-POINTER, " +
                        "ADDRESS OF, LENGTH OF, OMITTED, BY VALUE y RETURNING. " +
                        "El nombre del programa puede ser un literal alfanumérico o decimal con zona.</p>")
                .setSeverity("MAJOR")
                .setType(org.sonar.api.rules.RuleType.BUG)
                .addTags("cobol", "procedure-division", "call");

        repository.createRule("PerformStatement")
                .setName("PERFORM en línea vacío es extensión IBM")
                .setHtmlDescription("<p>COBOL permite una sentencia PERFORM en línea vacía y " +
                        "una salida común para dos o más PERFORM activos.</p>")
                .setSeverity("MINOR")
                .setType(org.sonar.api.rules.RuleType.CODE_SMELL)
                .addTags("cobol", "procedure-division", "perform");

        repository.createRule("ExitProgram")
                .setName("EXIT PROGRAM debe ser la última sentencia (norma 85 COBOL)")
                .setHtmlDescription("<p>La norma 85 COBOL exige que EXIT PROGRAM sea la última sentencia " +
                        "de una secuencia imperativa. IBM COBOL permite especificarla antes.")
                .setSeverity("MAJOR")
                .setType(org.sonar.api.rules.RuleType.BUG)
                .addTags("cobol", "procedure-division", "exit-program");

        repository.createRule("GoToStatement")
                .setName("GO TO incondicional debe ser la última sentencia del párrafo")
                .setHtmlDescription("<p>La norma 85 COBOL exige que GO TO incondicional sea la última " +
                        "sentencia de una frase. IBM COBOL permite codificarlo antes en ciertas situaciones.</p>")
                .setSeverity("MAJOR")
                .setType(org.sonar.api.rules.RuleType.BUG)
                .addTags("cobol", "procedure-division", "goto");

        repository.createRule("SearchStatement")
                .setName("SEARCH debe cumplir reglas de extensión IBM")
                .setHtmlDescription("<p>COBOL permite especificar END SEARCH con NEXT SENTENCE " +
                        "y omitir NEXT SENTENCE y sentencias imperativas en búsqueda binaria.</p>")
                .setSeverity("MINOR")
                .setType(org.sonar.api.rules.RuleType.CODE_SMELL)
                .addTags("cobol", "procedure-division", "search");

        repository.createRule("SetStatement")
                .setName("SET con punteros de datos/procedimiento es extensión IBM")
                .setHtmlDescription("<p>COBOL extiende SET para soportar DATA POINTER, " +
                        "PROCEDURE-POINTER y FUNCTION-POINTER.</p>")
                .setSeverity("MINOR")
                .setType(org.sonar.api.rules.RuleType.CODE_SMELL)
                .addTags("cobol", "procedure-division", "set");

        repository.createRule("DisplayStatement")
                .setName("DISPLAY debe cumplir extensiones IBM")
                .setHtmlDescription("<p>COBOL permite DISPLAY con environment-name en la frase UPON " +
                        "y visualización de literales numéricos con signo y no enteros.</p>")
                .setSeverity("MINOR")
                .setType(org.sonar.api.rules.RuleType.CODE_SMELL)
                .addTags("cobol", "procedure-division", "display");

        repository.createRule("AcceptStatement")
                .setName("ACCEPT debe usar extensiones IBM correctamente")
                .setHtmlDescription("<p>COBOL extiende ACCEPT con: environment-name en FROM, " +
                        "DATE YYYYMMDD y DAY YYYYDDD para fechas del sistema.</p>")
                .setSeverity("MINOR")
                .setType(org.sonar.api.rules.RuleType.CODE_SMELL)
                .addTags("cobol", "procedure-division", "accept", "date");

        repository.createRule("WriteStatement")
                .setName("WRITE debe cumplir extensiones IBM")
                .setHtmlDescription("<p>COBOL extiende WRITE con: INVALID KEY y NOT ON INVALID KEY, " +
                        "formato secuencial de línea, y permite escribir registros de tamaño diferente " +
                        "en archivos relativos.</p>")
                .setSeverity("MAJOR")
                .setType(org.sonar.api.rules.RuleType.BUG)
                .addTags("cobol", "procedure-division", "write", "io");

        repository.createRule("ReadStatement")
                .setName("READ debe cumplir extensiones IBM")
                .setHtmlDescription("<p>COBOL extiende READ con: frase PREVIOUS, y permite omitir " +
                        "AT END e INVALID KEY cuando hay procedimiento declarativo aplicable.</p>")
                .setSeverity("MAJOR")
                .setType(org.sonar.api.rules.RuleType.BUG)
                .addTags("cobol", "procedure-division", "read", "io");

        repository.createRule("StartStatement")
                .setName("START debe usar operadores relacionales IBM extendidos")
                .setHtmlDescription("<p>COBOL extiende START con operadores: LESS THAN, <, " +
                        "NOT GREATER THAN, NOT >, LESS THAN OR EQUAL TO, <=.</p>")
                .setSeverity("MINOR")
                .setType(org.sonar.api.rules.RuleType.CODE_SMELL)
                .addTags("cobol", "procedure-division", "start", "io");

        repository.createRule("ComputeStatement")
                .setName("COMPUTE puede usar EQUAL como extensión IBM")
                .setHtmlDescription("<p>COBOL permite usar la palabra EQUAL en lugar del signo = " +
                        "en la sentencia COMPUTE.</p>")
                .setSeverity("MINOR")
                .setType(org.sonar.api.rules.RuleType.CODE_SMELL)
                .addTags("cobol", "procedure-division", "compute");

        repository.createRule("InitializeStatement")
                .setName("INITIALIZE debe cumplir extensiones IBM")
                .setHtmlDescription("<p>COBOL permite DBCS, EGCS, NATIONAL y NATIONAL-EDITED " +
                        "en la frase REPLACING de INITIALIZE.</p>")
                .setSeverity("MINOR")
                .setType(org.sonar.api.rules.RuleType.CODE_SMELL)
                .addTags("cobol", "procedure-division", "initialize");

        repository.createRule("StringUnstring")
                .setName("STRING/UNSTRING permite modificación de referencia (IBM)")
                .setHtmlDescription("<p>COBOL permite modificación de referencia del elemento " +
                        "en la frase INTO de STRING y del campo de envío en UNSTRING.</p>")
                .setSeverity("MINOR")
                .setType(org.sonar.api.rules.RuleType.CODE_SMELL)
                .addTags("cobol", "procedure-division", "string");

        // ==========================================
        // FILE SECTION / INPUT-OUTPUT
        // ==========================================
        repository.createRule("FileControl")
                .setName("FILE-CONTROL debe cumplir reglas IBM COBOL")
                .setHtmlDescription("<p>COBOL hace opcional FILE-CONTROL cuando INPUT-OUTPUT SECTION " +
                        "está especificada pero no hay archivos definidos. También permite la frase USING " +
                        "en la cláusula ASSIGN.</p>")
                .setSeverity("MINOR")
                .setType(org.sonar.api.rules.RuleType.CODE_SMELL)
                .addTags("cobol", "file-section", "file-control");

        repository.createRule("FileStatus")
                .setName("FILE STATUS puede ser elemento numérico DISPLAY (IBM)")
                .setHtmlDescription("<p>COBOL permite un elemento numérico de uso DISPLAY o NATIONAL " +
                        "en la cláusula FILE STATUS. La norma 85 COBOL exige un dato alfanumérico.</p>")
                .setSeverity("MINOR")
                .setType(org.sonar.api.rules.RuleType.CODE_SMELL)
                .addTags("cobol", "file-section", "file-status");

        repository.createRule("LinageClause")
                .setName("LINAGE puede especificarse en archivos EXTEND (IBM)")
                .setHtmlDescription("<p>COBOL permite especificar LINAGE para archivos abiertos " +
                        "en modalidad EXTEND, extendiendo la norma estándar.</p>")
                .setSeverity("MINOR")
                .setType(org.sonar.api.rules.RuleType.CODE_SMELL)
                .addTags("cobol", "file-section", "linage");

        repository.createRule("OrganizationLineSequential")
                .setName("ORGANIZATION IS LINE SEQUENTIAL es extensión IBM")
                .setHtmlDescription("<p>La cláusula ORGANIZATION IS LINE SEQUENTIAL y el formato de " +
                        "control de archivo secuencial de línea son extensiones IBM COBOL.</p>")
                .setSeverity("INFO")
                .setType(org.sonar.api.rules.RuleType.CODE_SMELL)
                .addTags("cobol", "file-section", "line-sequential");

        // ==========================================
        // SPECIAL-NAMES / ENVIRONMENT
        // ==========================================
        repository.createRule("SpecialNames")
                .setName("SPECIAL-NAMES debe cumplir extensiones IBM")
                .setHtmlDescription("<p>COBOL hace opcional el orden de cláusulas y el punto final " +
                        "en SPECIAL-NAMES. La norma 85 COBOL exige orden específico y punto obligatorio.</p>")
                .setSeverity("MINOR")
                .setType(org.sonar.api.rules.RuleType.CODE_SMELL)
                .addTags("cobol", "environment-division", "special-names");

        repository.createRule("CurrencySign")
                .setName("Múltiples CURRENCY SIGN son extensión IBM")
                .setHtmlDescription("<p>COBOL permite múltiples cláusulas CURRENCY SIGN, signos de moneda " +
                        "de varios caracteres, y la frase WITH PICTURE SYMBOL. La norma 85 COBOL " +
                        "solo permite una cláusula CURRENCY SIGN de un carácter.</p>")
                .setSeverity("MINOR")
                .setType(org.sonar.api.rules.RuleType.CODE_SMELL)
                .addTags("cobol", "environment-division", "currency");

        // ==========================================
        // COMENTARIOS Y FORMATO
        // ==========================================
        repository.createRule("InlineComment")
                .setName("Comentarios en línea son extensión IBM")
                .setHtmlDescription("<p>Los comentarios en línea (con *>) son una extensión IBM COBOL. " +
                        "También se permiten líneas de comentario y entradas con caracteres multibyte.</p>")
                .setSeverity("INFO")
                .setType(org.sonar.api.rules.RuleType.CODE_SMELL)
                .addTags("cobol", "comments");

        repository.createRule("CommentBeforeIdDivision")
                .setName("Líneas de comentario antes de IDENTIFICATION DIVISION son IBM")
                .setHtmlDescription("<p>COBOL permite líneas de comentario antes de la cabecera " +
                        "IDENTIFICATION DIVISION. La norma 85 COBOL no lo permite.</p>")
                .setSeverity("INFO")
                .setType(org.sonar.api.rules.RuleType.CODE_SMELL)
                .addTags("cobol", "comments", "identification-division");

        repository.createRule("ExtendedSourceFormat")
                .setName("Formato de fuente extendido es extensión IBM")
                .setHtmlDescription("<p>El formato de fuente extendido (SOURCEFORMAT FREE o VARIABLE) " +
                        "es una extensión IBM COBOL al formato de referencia estándar.</p>")
                .setSeverity("MINOR")
                .setType(org.sonar.api.rules.RuleType.CODE_SMELL)
                .addTags("cobol", "source-format");

        // ==========================================
        // DIRECTIVAS Y COPY
        // ==========================================
        repository.createRule("CompilerDirective")
                .setName("Directivas de compilador IBM deben usarse correctamente")
                .setHtmlDescription("<p>COBOL incluye directivas de compilador extendidas: " +
                        "BASIS, PROCESS/CBL, *CONTROL/*CBL, DELETE, EJECT, INSERT, READY/RESET TRACE, " +
                        "SERVICE LABEL, SERVICE RELOAD, SKIP1/2/3, TITLE y CALLINTERFACE.</p>")
                .setSeverity("MINOR")
                .setType(org.sonar.api.rules.RuleType.CODE_SMELL)
                .addTags("cobol", "compiler-directives");

        repository.createRule("CopyStatement")
                .setName("COPY IBM permite literales y sentencias anidadas")
                .setHtmlDescription("<p>COBOL extiende COPY permitiendo: opcionalidad de 'OF library-name', " +
                        "literales para nombre-texto y nombre-biblioteca, frase SUPPRESS, COPY anidadas, " +
                        "guión como primer/último carácter en operandos REPLACING.</p>")
                .setSeverity("MINOR")
                .setType(org.sonar.api.rules.RuleType.CODE_SMELL)
                .addTags("cobol", "copy");

        // ==========================================
        // FUNCIONES INTRÍNSECAS IBM
        // ==========================================
        repository.createRule("IntrinsicFunction")
                .setName("Funciones intrínsecas IBM deben usarse con argumentos válidos")
                .setHtmlDescription("<p>COBOL incluye funciones intrínsecas extendidas: " +
                        "ADD-DURATION, CONVERT-DATE-TIME, DISPLAY-OF, EXTRACT-DATE-TIME, FIND-DURATION, " +
                        "NATIONAL-OF, SUBTRACT-DURATION, TEST-DATE-TIME, TRIML, TRIMR, DATEVAL, UNDATE, " +
                        "DATE-TO-YYYYMMDD, DAY-TO-YYYYDDD, YEAR-TO-YYYY, YEARWINDOW. " +
                        "LENGTH puede aceptar punteros y registros especiales.</p>")
                .setSeverity("MAJOR")
                .setType(org.sonar.api.rules.RuleType.BUG)
                .addTags("cobol", "intrinsic-functions");

        // ==========================================
        // MANEJO DE FECHAS Y2K
        // ==========================================
        repository.createRule("DateHandling")
                .setName("Manejo de fechas debe usar extensiones Y2K de IBM")
                .setHtmlDescription("<p>COBOL incluye soporte Y2K con: DATE-FORMAT, " +
                        "campos de fecha con ventana, campos expandidos, funciones DATEVAL, UNDATE, " +
                        "YEARWINDOW, DATE-TO-YYYYMMDD, DAY-TO-YYYYDDD, YEAR-TO-YYYY. " +
                        "Se recomienda usar ACCEPT DATE YYYYMMDD en lugar de ACCEPT DATE.</p>")
                .setSeverity("MAJOR")
                .setType(org.sonar.api.rules.RuleType.BUG)
                .addTags("cobol", "date", "y2k");

        // ==========================================
        // REGISTROS ESPECIALES IBM
        // ==========================================
        repository.createRule("SpecialRegister")
                .setName("Registros especiales IBM deben usarse correctamente")
                .setHtmlDescription("<p>COBOL incluye registros especiales extendidos: " +
                        "ADDRESS OF, LENGTH OF, RETURN-CODE, SHIFT-IN, SHIFT-OUT, " +
                        "SORT-CONTROL, SORT-CORE-SIZE, SORT-FILE-SIZE, SORT-MESSAGE, " +
                        "SORT-MODE-SIZE, SORT-RETURN, TALLY, WHEN-COMPILED, " +
                        "XML-CODE, XML-EVENT, XML-NTEXT, XML-TEXT.</p>")
                .setSeverity("MINOR")
                .setType(org.sonar.api.rules.RuleType.CODE_SMELL)
                .addTags("cobol", "special-registers");

        // ==========================================
        // INDEXACIÓN Y SUSCRIPCIÓN
        // ==========================================
        repository.createRule("IndexingSubscripting")
                .setName("Indexación con nombre de índice de otra tabla es extensión IBM")
                .setHtmlDescription("<p>COBOL permite referenciar una tabla con un nombre de índice " +
                        "definido para una tabla diferente, y especificar un literal entero con signo " +
                        "positivo en la suscripción relativa.</p>")
                .setSeverity("MINOR")
                .setType(org.sonar.api.rules.RuleType.CODE_SMELL)
                .addTags("cobol", "indexing", "subscripting");


        // ==========================================
        // NUEVAS REGLAS - COBERTURA COMPLETA
        // ==========================================
        repository.createRule("CobolUserDefinedWords")
                .setName("Palabras definidas por usuario con subrayado inicial son extensión IBM")
                .setHtmlDescription("<p>COBOL permite que las palabras definidas por el usuario incluyan " +
                        "un subrayado, incluso como primer carácter. La norma 85 COBOL no permite " +
                        "el subrayado como primer carácter.</p>")
                .setSeverity("MINOR").setType(org.sonar.api.rules.RuleType.CODE_SMELL)
                .addTags("cobol", "identifiers");

        repository.createRule("FigurativeConstants")
                .setName("Constantes figurativas NULL/NULLS son extensión IBM")
                .setHtmlDescription("<p>COBOL extiende las constantes figurativas con NULL y NULLS " +
                        "para punteros y referencias de objeto. VALUE IS NULL/NULLS no está en norma 85 COBOL.</p>")
                .setSeverity("MAJOR").setType(org.sonar.api.rules.RuleType.CODE_SMELL)
                .addTags("cobol", "figurative-constants", "pointers");

        repository.createRule("MixedLiterals")
                .setName("Literales con apóstrofo o literales mixtos son extensión IBM")
                .setHtmlDescription("<p>COBOL permite el apóstrofo (') como alternativa a comillas (\") " +
                        "y literales mixtos de un solo byte/multibyte. No son parte de la norma 85 COBOL estándar.</p>")
                .setSeverity("MINOR").setType(org.sonar.api.rules.RuleType.CODE_SMELL)
                .addTags("cobol", "literals");

        repository.createRule("IOControl")
                .setName("I-O-CONTROL con APPLY WRITE-ONLY, RERUN o SAME de un archivo son extensiones IBM")
                .setHtmlDescription("<p>COBOL extiende I-O-CONTROL con: APPLY WRITE-ONLY, SAME con un solo " +
                        "archivo, RERUN sin ON, y formato secuencial de línea.</p>")
                .setSeverity("MINOR").setType(org.sonar.api.rules.RuleType.CODE_SMELL)
                .addTags("cobol", "io-control");

        repository.createRule("FileControlExtended")
                .setName("Extensiones IBM en FILE-CONTROL: PASSWORD, ASSIGN USING, ALTERNATE KEY")
                .setHtmlDescription("<p>COBOL extiende FILE-CONTROL con: cláusula PASSWORD, " +
                        "frase USING en ASSIGN, ALTERNATE KEY sin palabra RECORD, claves no alfanuméricas, " +
                        "acceso descendente, literal nacional en PADDING CHARACTER.</p>")
                .setSeverity("MINOR").setType(org.sonar.api.rules.RuleType.CODE_SMELL)
                .addTags("cobol", "file-control");

        repository.createRule("FileSectionExtended")
                .setName("Extensiones IBM en FILE SECTION: RECORD MODE, CODE-SET en SD")
                .setHtmlDescription("<p>COBOL extiende FILE SECTION con: RECORD MODE, " +
                        "entradas SD con BLOCK CONTAINS/LABEL RECORDS/VALUE OF/LINAGE/CODE-SET/WITH FOOTING.</p>")
                .setSeverity("MINOR").setType(org.sonar.api.rules.RuleType.CODE_SMELL)
                .addTags("cobol", "file-section");

        repository.createRule("DataDivisionExtended")
                .setName("Extensiones IBM en DATA DIVISION: GLOBAL en LINKAGE, SYNCHRONIZED nivel 01, VALUE NULL")
                .setHtmlDescription("<p>COBOL extiende DATA DIVISION con: GLOBAL en LINKAGE SECTION, " +
                        "SYNCHRONIZED en nivel 01, BLANK WHEN ZEROS/ZEROES, VALUE IS NULL/NULLS.</p>")
                .setSeverity("MINOR").setType(org.sonar.api.rules.RuleType.CODE_SMELL)
                .addTags("cobol", "data-division");

        repository.createRule("ProcedureDivisionHeader")
                .setName("Cabecera PROCEDURE DIVISION con BY VALUE/RETURNING son extensiones IBM")
                .setHtmlDescription("<p>COBOL extiende la cabecera PROCEDURE DIVISION con: BY VALUE, RETURNING, " +
                        "USING con elemento que tiene REDEFINES, múltiples instancias del mismo elemento en USING.</p>")
                .setSeverity("MAJOR").setType(org.sonar.api.rules.RuleType.CODE_SMELL)
                .addTags("cobol", "procedure-division-header");

        repository.createRule("DeclarativeProcedures")
                .setName("Declarativos y procedimientos IBM: priority-number con signo, USE activo")
                .setHtmlDescription("<p>COBOL permite: priority-number con signo positivo, omitir cabecera " +
                        "de sección tras declarativas, omitir nombre de párrafo, párrafos sin sección.</p>")
                .setSeverity("MINOR").setType(org.sonar.api.rules.RuleType.CODE_SMELL)
                .addTags("cobol", "declaratives");

        repository.createRule("ConditionalExpressions")
                .setName("Condiciones de clase DBCS/KANJI y CORRESPONDING con FILLER son extensiones IBM")
                .setHtmlDescription("<p>COBOL extiende las condiciones con: clase DBCS/KANJI, " +
                        "COMP-3/PACKED-DECIMAL en prueba NUMERIC, CORRESPONDING con elemento subordinado a FILLER.</p>")
                .setSeverity("MAJOR").setType(org.sonar.api.rules.RuleType.CODE_SMELL)
                .addTags("cobol", "conditionals");

        repository.createRule("ArithmeticStatements")
                .setName("ADD/DIVIDE/MULTIPLY/SUBTRACT con operandos >18 dígitos son extensión IBM")
                .setHtmlDescription("<p>COBOL permite operandos de hasta 31 dígitos en sentencias " +
                        "aritméticas (ADD, DIVIDE, MULTIPLY, SUBTRACT). La norma 85 COBOL limita a 18 dígitos.</p>")
                .setSeverity("MAJOR").setType(org.sonar.api.rules.RuleType.BUG)
                .addTags("cobol", "arithmetic");

        repository.createRule("CancelStatement")
                .setName("CANCEL con nombre en elemento alfabético es extensión IBM")
                .setHtmlDescription("<p>COBOL permite especificar el nombre del programa a cancelar " +
                        "en un elemento de datos alfabético o decimal con zona.</p>")
                .setSeverity("MINOR").setType(org.sonar.api.rules.RuleType.CODE_SMELL)
                .addTags("cobol", "cancel");

        repository.createRule("CloseStatement")
                .setName("CLOSE WITH NO REWIND es extensión IBM")
                .setHtmlDescription("<p>La frase WITH NO REWIND en la sentencia CLOSE es una extensión IBM COBOL.</p>")
                .setSeverity("MINOR").setType(org.sonar.api.rules.RuleType.CODE_SMELL)
                .addTags("cobol", "close");

        repository.createRule("ExitStatement")
                .setName("EXIT en frase con otras sentencias es extensión IBM")
                .setHtmlDescription("<p>COBOL permite la sentencia EXIT en una frase con otras sentencias " +
                        "o en un párrafo con otras frases. La norma 85 COBOL exige EXIT sola en su párrafo.</p>")
                .setSeverity("MAJOR").setType(org.sonar.api.rules.RuleType.CODE_SMELL)
                .addTags("cobol", "exit");

        repository.createRule("IfStatement")
                .setName("END-IF con NEXT SENTENCE es extensión IBM")
                .setHtmlDescription("<p>COBOL permite END-IF junto con NEXT SENTENCE. " +
                        "La norma 85 COBOL prohíbe esta combinación.</p>")
                .setSeverity("MAJOR").setType(org.sonar.api.rules.RuleType.BUG)
                .addTags("cobol", "if");

        repository.createRule("MergeStatement")
                .setName("MERGE con nombres de archivo en SAME es extensión IBM")
                .setHtmlDescription("<p>COBOL permite especificar nombres de archivo en una cláusula SAME " +
                        "dentro de MERGE (extensión a la norma 85 COBOL).</p>")
                .setSeverity("MINOR").setType(org.sonar.api.rules.RuleType.CODE_SMELL)
                .addTags("cobol", "merge");

        repository.createRule("OpenStatement")
                .setName("OPEN EXTEND con LINAGE es extensión IBM")
                .setHtmlDescription("<p>COBOL permite la frase EXTEND para archivos que tienen LINAGE " +
                        "(extensión a la norma 85 COBOL estándar).</p>")
                .setSeverity("MINOR").setType(org.sonar.api.rules.RuleType.CODE_SMELL)
                .addTags("cobol", "open");

        repository.createRule("ReturnStatement")
                .setName("RETURN a elemento no alfanumérico es extensión IBM")
                .setHtmlDescription("<p>COBOL permite RETURN a un elemento que no sea alfanumérico ni grupo " +
                        "alfanumérico. Verifique el tipo del elemento receptor.</p>")
                .setSeverity("MINOR").setType(org.sonar.api.rules.RuleType.CODE_SMELL)
                .addTags("cobol", "return");

        repository.createRule("RewriteStatement")
                .setName("REWRITE sin INVALID KEY o con tamaño diferente es extensión IBM")
                .setHtmlDescription("<p>COBOL permite omitir INVALID KEY en REWRITE y reescribir " +
                        "registros de tamaño diferente al original (extensión a norma 85 COBOL).</p>")
                .setSeverity("MAJOR").setType(org.sonar.api.rules.RuleType.BUG)
                .addTags("cobol", "rewrite");

        repository.createRule("SortStatement")
                .setName("SORT con nombres de archivo en SAME es extensión IBM")
                .setHtmlDescription("<p>COBOL permite especificar nombres de archivo GIVING " +
                        "en la cláusula SAME de SORT.</p>")
                .setSeverity("MINOR").setType(org.sonar.api.rules.RuleType.CODE_SMELL)
                .addTags("cobol", "sort");

        repository.createRule("StopStatement")
                .setName("STOP con literal no entero o con signo es extensión IBM")
                .setHtmlDescription("<p>COBOL permite en STOP: literal de punto fijo no entero, " +
                        "entero con signo, y STOP como sentencia distinta de la última en una frase.</p>")
                .setSeverity("MINOR").setType(org.sonar.api.rules.RuleType.CODE_SMELL)
                .addTags("cobol", "stop");

        repository.createRule("InvalidKeyOmission")
                .setName("Omisión de INVALID KEY y procedimiento de excepción es extensión IBM")
                .setHtmlDescription("<p>COBOL permite omitir TANTO la frase INVALID KEY COMO el " +
                        "procedimiento de excepción/error. La norma 85 COBOL exige al menos uno de ellos.</p>")
                .setSeverity("MAJOR").setType(org.sonar.api.rules.RuleType.BUG)
                .addTags("cobol", "invalid-key", "error-handling");

        repository.createRule("LengthFunction")
                .setName("FUNCTION LENGTH con puntero o ADDRESS OF es extensión IBM")
                .setHtmlDescription("<p>COBOL permite especificar un puntero, ADDRESS OF o LENGTH OF " +
                        "como argumento de FUNCTION LENGTH.</p>")
                .setSeverity("MINOR").setType(org.sonar.api.rules.RuleType.CODE_SMELL)
                .addTags("cobol", "intrinsic-functions", "length");

        repository.createRule("SymbolicCharacters")
                .setName("SYMBOLIC CHARACTERS no permitida con página de códigos multibyte (IBM)")
                .setHtmlDescription("<p>COBOL: La cláusula SYMBOLIC CHARACTERS no está permitida " +
                        "cuando la configuración regional indica una página de códigos multibyte (MBCS).</p>")
                .setSeverity("MAJOR").setType(org.sonar.api.rules.RuleType.BUG)
                .addTags("cobol", "special-names", "mbcs");

        repository.done();
    }
}
