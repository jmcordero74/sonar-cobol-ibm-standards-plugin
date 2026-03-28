@echo off
REM Script to generate JSON rule definition files

set RULES_DIR=src\main\resources\org\sonar\l10n\cobol\rules\cobol-ibm-standards

REM Create basic JSON definitions for all rules
call :create_rule "RecursiveClause" "INFO" "RECURSIVE clause must be documented"
call :create_rule "NationalCharacterSupport" "MAJOR" "Use of USAGE NATIONAL must be valid"
call :create_rule "HexLiteral" "MAJOR" "Hexadecimal literals must use correct delimiters"
call :create_rule "NullTerminatedLiteral" "MINOR" "Null-terminated literals must use Z\" or Z'"
call :create_rule "FloatingPointLiteral" "MINOR" "Floating-point literals are an extension"
call :create_rule "FixedPointDigitsLimit" "MAJOR" "Numeric literals must not exceed 31 digits"
call :create_rule "LocalStorageSection" "INFO" "LOCAL-STORAGE SECTION is an IBM extension"
call :create_rule "PictureClauseLength" "MAJOR" "PICTURE clause must not exceed 50 characters"
call :create_rule "UsageClause" "MAJOR" "IBM USAGE clauses must be used correctly"
call :create_rule "OccursClause" "MAJOR" "OCCURS clause must comply with IBM rules"
call :create_rule "RedefinesClause" "MAJOR" "REDEFINES must not redefine to a larger size"
call :create_rule "DateFormatClause" "MINOR" "DATE-FORMAT clause must be used for dates"
call :create_rule "LevelNumberOrder" "MINOR" "Level numbers must be consistent"
call :create_rule "GobackStatement" "INFO" "GOBACK is an IBM extension"
call :create_rule "EntryStatement" "MAJOR" "ENTRY statement is an IBM extension"
call :create_rule "XmlParseStatement" "MAJOR" "XML PARSE must be used correctly"
call :create_rule "XmlGenerateStatement" "MAJOR" "XML GENERATE must be used correctly"
call :create_rule "CallStatement" "MAJOR" "CALL must comply with IBM extensions"
call :create_rule "PerformStatement" "MINOR" "Empty inline PERFORM is an IBM extension"
call :create_rule "ExitProgram" "MAJOR" "EXIT PROGRAM must be the last statement"
call :create_rule "GoToStatement" "MAJOR" "Unconditional GO TO must be the last statement"
call :create_rule "SearchStatement" "MINOR" "SEARCH must comply with IBM rules"
call :create_rule "SetStatement" "MINOR" "SET with pointers is an IBM extension"
call :create_rule "DisplayStatement" "MINOR" "DISPLAY must comply with IBM extensions"
call :create_rule "AcceptStatement" "MINOR" "ACCEPT must use IBM extensions"
call :create_rule "WriteStatement" "MAJOR" "WRITE must comply with IBM extensions"
call :create_rule "ReadStatement" "MAJOR" "READ must comply with IBM extensions"
call :create_rule "StartStatement" "MINOR" "START must use IBM operators"
call :create_rule "ComputeStatement" "MINOR" "COMPUTE may use EQUAL"
call :create_rule "InitializeStatement" "MINOR" "INITIALIZE must comply with IBM extensions"
call :create_rule "StringUnstring" "MINOR" "STRING/UNSTRING allows modification"
call :create_rule "FileControl" "MINOR" "FILE-CONTROL must comply with IBM rules"
call :create_rule "FileStatus" "MINOR" "FILE STATUS may be numeric"
call :create_rule "LinageClause" "MINOR" "LINAGE may be specified in EXTEND"
call :create_rule "OrganizationLineSequential" "INFO" "ORGANIZATION LINE SEQUENTIAL is an extension"
call :create_rule "SpecialNames" "MINOR" "SPECIAL-NAMES must comply with extensions"
call :create_rule "CurrencySign" "MINOR" "Multiple CURRENCY SIGN is an extension"
call :create_rule "InlineComment" "INFO" "Inline comments are an extension"
call :create_rule "CommentBeforeIdDivision" "INFO" "Comments before ID DIVISION"
call :create_rule "ExtendedSourceFormat" "MINOR" "Extended source format"
call :create_rule "CompilerDirective" "MINOR" "IBM compiler directives"
call :create_rule "CopyStatement" "MINOR" "IBM COPY allows literals"
call :create_rule "IntrinsicFunction" "MAJOR" "IBM intrinsic functions"
call :create_rule "DateHandling" "MAJOR" "Y2K date handling"
call :create_rule "SpecialRegister" "MINOR" "IBM special registers"
call :create_rule "IndexingSubscripting" "MINOR" "Indexing with name from another table"
call :create_rule "CobolUserDefinedWords" "MINOR" "Words with leading underscore"
call :create_rule "FigurativeConstants" "MAJOR" "Figurative constants NULL/NULLS"
call :create_rule "MixedLiterals" "MINOR" "Literals with apostrophe"
call :create_rule "IOControl" "MINOR" "I-O-CONTROL with IBM extensions"
call :create_rule "FileControlExtended" "MINOR" "Extensions in FILE-CONTROL"
call :create_rule "FileSectionExtended" "MINOR" "Extensions in FILE SECTION"
call :create_rule "DataDivisionExtended" "MINOR" "Extensions in DATA DIVISION"
call :create_rule "ProcedureDivisionHeader" "MAJOR" "PROCEDURE DIVISION header"
call :create_rule "DeclarativeProcedures" "MINOR" "IBM declaratives and procedures"
call :create_rule "ConditionalExpressions" "MAJOR" "DBCS/KANJI class conditions"
call :create_rule "ArithmeticStatements" "MAJOR" "Operands with >18 digits"
call :create_rule "CancelStatement" "MINOR" "CANCEL with alphabetic name"
call :create_rule "CloseStatement" "MINOR" "CLOSE WITH NO REWIND"
call :create_rule "ExitStatement" "MAJOR" "EXIT in phrase with other statements"
call :create_rule "IfStatement" "MAJOR" "END-IF with NEXT SENTENCE"
call :create_rule "MergeStatement" "MINOR" "MERGE with names in SAME"
call :create_rule "OpenStatement" "MINOR" "OPEN EXTEND with LINAGE"
call :create_rule "ReturnStatement" "MINOR" "RETURN to non-alphanumeric element"
call :create_rule "RewriteStatement" "MAJOR" "REWRITE without INVALID KEY"
call :create_rule "SortStatement" "MINOR" "SORT with names in SAME"
call :create_rule "StopStatement" "MINOR" "STOP with non-integer literal"
call :create_rule "InvalidKeyOmission" "MAJOR" "Omission of INVALID KEY"
call :create_rule "LengthFunction" "MINOR" "FUNCTION LENGTH with pointer"
call :create_rule "SymbolicCharacters" "MAJOR" "SYMBOLIC CHARACTERS with MBCS"

echo All JSON definitions created
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
