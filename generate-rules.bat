@echo off
REM Script para generar archivos JSON de definición de reglas

set RULES_DIR=src\main\resources\org\sonar\l10n\cobol\rules\cobol-ibm-standards

REM Crear definiciones JSON básicas para todas las reglas
call :create_rule "RecursiveClause" "INFO" "Cláusula RECURSIVE debe documentarse"
call :create_rule "NationalCharacterSupport" "MAJOR" "Uso de USAGE NATIONAL debe ser válido"
call :create_rule "HexLiteral" "MAJOR" "Literales hexadecimales deben usar delimitadores correctos"
call :create_rule "NullTerminatedLiteral" "MINOR" "Literales terminados en nulo deben usar Z\" o Z'"
call :create_rule "FloatingPointLiteral" "MINOR" "Literales de coma flotante son extensión"
call :create_rule "FixedPointDigitsLimit" "MAJOR" "Literales numéricos no deben exceder 31 dígitos"
call :create_rule "LocalStorageSection" "INFO" "LOCAL-STORAGE SECTION es extensión IBM"
call :create_rule "PictureClauseLength" "MAJOR" "Cláusula PICTURE no debe exceder 50 caracteres"
call :create_rule "UsageClause" "MAJOR" "Cláusulas USAGE IBM deben usarse correctamente"
call :create_rule "OccursClause" "MAJOR" "Cláusula OCCURS debe cumplir reglas IBM"
call :create_rule "RedefinesClause" "MAJOR" "REDEFINES no debe redefinir a tamaño mayor"
call :create_rule "DateFormatClause" "MINOR" "Cláusula DATE-FORMAT debe usarse para fechas"
call :create_rule "LevelNumberOrder" "MINOR" "Números de nivel deben ser consistentes"
call :create_rule "GobackStatement" "INFO" "GOBACK es extensión IBM"
call :create_rule "EntryStatement" "MAJOR" "Sentencia ENTRY es extensión IBM"
call :create_rule "XmlParseStatement" "MAJOR" "XML PARSE debe usarse correctamente"
call :create_rule "XmlGenerateStatement" "MAJOR" "XML GENERATE debe usarse correctamente"
call :create_rule "CallStatement" "MAJOR" "CALL debe cumplir extensiones IBM"
call :create_rule "PerformStatement" "MINOR" "PERFORM en línea vacío es extensión IBM"
call :create_rule "ExitProgram" "MAJOR" "EXIT PROGRAM debe ser la última sentencia"
call :create_rule "GoToStatement" "MAJOR" "GO TO incondicional debe ser la última sentencia"
call :create_rule "SearchStatement" "MINOR" "SEARCH debe cumplir reglas IBM"
call :create_rule "SetStatement" "MINOR" "SET con punteros es extensión IBM"
call :create_rule "DisplayStatement" "MINOR" "DISPLAY debe cumplir extensiones IBM"
call :create_rule "AcceptStatement" "MINOR" "ACCEPT debe usar extensiones IBM"
call :create_rule "WriteStatement" "MAJOR" "WRITE debe cumplir extensiones IBM"
call :create_rule "ReadStatement" "MAJOR" "READ debe cumplir extensiones IBM"
call :create_rule "StartStatement" "MINOR" "START debe usar operadores IBM"
call :create_rule "ComputeStatement" "MINOR" "COMPUTE puede usar EQUAL"
call :create_rule "InitializeStatement" "MINOR" "INITIALIZE debe cumplir extensiones IBM"
call :create_rule "StringUnstring" "MINOR" "STRING/UNSTRING permite modificación"
call :create_rule "FileControl" "MINOR" "FILE-CONTROL debe cumplir reglas IBM"
call :create_rule "FileStatus" "MINOR" "FILE STATUS puede ser numérico"
call :create_rule "LinageClause" "MINOR" "LINAGE puede especificarse en EXTEND"
call :create_rule "OrganizationLineSequential" "INFO" "ORGANIZATION LINE SEQUENTIAL es extensión"
call :create_rule "SpecialNames" "MINOR" "SPECIAL-NAMES debe cumplir extensiones"
call :create_rule "CurrencySign" "MINOR" "Múltiples CURRENCY SIGN son extensión"
call :create_rule "InlineComment" "INFO" "Comentarios en línea son extensión"
call :create_rule "CommentBeforeIdDivision" "INFO" "Comentarios antes de ID DIVISION"
call :create_rule "ExtendedSourceFormat" "MINOR" "Formato de fuente extendido"
call :create_rule "CompilerDirective" "MINOR" "Directivas de compilador IBM"
call :create_rule "CopyStatement" "MINOR" "COPY IBM permite literales"
call :create_rule "IntrinsicFunction" "MAJOR" "Funciones intrínsecas IBM"
call :create_rule "DateHandling" "MAJOR" "Manejo de fechas Y2K"
call :create_rule "SpecialRegister" "MINOR" "Registros especiales IBM"
call :create_rule "IndexingSubscripting" "MINOR" "Indexación con nombre de otra tabla"
call :create_rule "CobolUserDefinedWords" "MINOR" "Palabras con subrayado inicial"
call :create_rule "FigurativeConstants" "MAJOR" "Constantes figurativas NULL/NULLS"
call :create_rule "MixedLiterals" "MINOR" "Literales con apóstrofo"
call :create_rule "IOControl" "MINOR" "I-O-CONTROL con extensiones IBM"
call :create_rule "FileControlExtended" "MINOR" "Extensiones en FILE-CONTROL"
call :create_rule "FileSectionExtended" "MINOR" "Extensiones en FILE SECTION"
call :create_rule "DataDivisionExtended" "MINOR" "Extensiones en DATA DIVISION"
call :create_rule "ProcedureDivisionHeader" "MAJOR" "Cabecera PROCEDURE DIVISION"
call :create_rule "DeclarativeProcedures" "MINOR" "Declarativos y procedimientos IBM"
call :create_rule "ConditionalExpressions" "MAJOR" "Condiciones de clase DBCS/KANJI"
call :create_rule "ArithmeticStatements" "MAJOR" "Operandos >18 dígitos"
call :create_rule "CancelStatement" "MINOR" "CANCEL con nombre alfabético"
call :create_rule "CloseStatement" "MINOR" "CLOSE WITH NO REWIND"
call :create_rule "ExitStatement" "MAJOR" "EXIT en frase con otras sentencias"
call :create_rule "IfStatement" "MAJOR" "END-IF con NEXT SENTENCE"
call :create_rule "MergeStatement" "MINOR" "MERGE con nombres en SAME"
call :create_rule "OpenStatement" "MINOR" "OPEN EXTEND con LINAGE"
call :create_rule "ReturnStatement" "MINOR" "RETURN a elemento no alfanumérico"
call :create_rule "RewriteStatement" "MAJOR" "REWRITE sin INVALID KEY"
call :create_rule "SortStatement" "MINOR" "SORT con nombres en SAME"
call :create_rule "StopStatement" "MINOR" "STOP con literal no entero"
call :create_rule "InvalidKeyOmission" "MAJOR" "Omisión de INVALID KEY"
call :create_rule "LengthFunction" "MINOR" "FUNCTION LENGTH con puntero"
call :create_rule "SymbolicCharacters" "MAJOR" "SYMBOLIC CHARACTERS con MBCS"

echo Todas las definiciones JSON creadas
goto :eof

:create_rule
set RULE_KEY=%~1
set SEVERITY=%~2
set TITLE=%~3

(
echo {
echo   "title": "%TITLE%",
echo   "type": "CODE_SMELL",
echo   "status": "ready",
echo   "remediation": {
echo     "func": "Constant/Issue",
echo     "constantCost": "5min"
echo   },
echo   "tags": [
echo     "cobol",
echo     "ibm-extension"
echo   ],
echo   "defaultSeverity": "%SEVERITY%"
echo }
) > "%RULES_DIR%\%RULE_KEY%.json"

goto :eof
