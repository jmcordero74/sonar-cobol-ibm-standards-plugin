package com.plugin.cobol.language;

import org.sonar.api.server.profile.BuiltInQualityProfilesDefinition;

/**
 * Perfil de calidad integrado: "Cobol Analyzer".
 * Activa automáticamente las 71 reglas IBM COBOL Standards.
 */
public class CobolQualityProfile implements BuiltInQualityProfilesDefinition {

    public static final String PROFILE_NAME = "Cobol Analyzer";
    public static final String REPO_KEY = "cobol-ibm-standards";

    // Todas las reglas del repositorio - deben coincidir exactamente con CobolRulesDefinition
    private static final String[] ALL_RULE_KEYS = {
        // IDENTIFICATION DIVISION
        "ProgramIdNaming",
        "RecursiveClause",
        // CARACTERES Y UNICODE
        "NationalCharacterSupport",
        "HexLiteral",
        "NullTerminatedLiteral",
        "FloatingPointLiteral",
        "FixedPointDigitsLimit",
        // LITERALES
        "MixedLiterals",
        "FigurativeConstants",
        // COMENTARIOS Y FORMATO
        "InlineComment",
        "CommentBeforeIdDivision",
        "ExtendedSourceFormat",
        // PALABRAS DEFINIDAS POR USUARIO
        "CobolUserDefinedWords",
        // DATA DIVISION
        "LocalStorageSection",
        "PictureClauseLength",
        "UsageClause",
        "OccursClause",
        "RedefinesClause",
        "DateFormatClause",
        "LevelNumberOrder",
        "DataDivisionExtended",
        // SPECIAL-NAMES
        "SpecialNames",
        "CurrencySign",
        "SymbolicCharacters",
        // FILE SECTION / I-O
        "FileControl",
        "FileControlExtended",
        "FileStatus",
        "LinageClause",
        "OrganizationLineSequential",
        "FileSectionExtended",
        // I-O-CONTROL
        "IOControl",
        // PROCEDURE DIVISION
        "GobackStatement",
        "EntryStatement",
        "XmlParseStatement",
        "XmlGenerateStatement",
        "CallStatement",
        "PerformStatement",
        "ExitProgram",
        "ExitStatement",
        "GoToStatement",
        "SearchStatement",
        "SetStatement",
        "DisplayStatement",
        "AcceptStatement",
        "WriteStatement",
        "ReadStatement",
        "StartStatement",
        "ComputeStatement",
        "InitializeStatement",
        "StringUnstring",
        "CancelStatement",
        "CloseStatement",
        "MergeStatement",
        "OpenStatement",
        "ReturnStatement",
        "RewriteStatement",
        "SortStatement",
        "StopStatement",
        "IfStatement",
        "InvalidKeyOmission",
        // CABECERA PROCEDURE DIVISION
        "ProcedureDivisionHeader",
        // DECLARATIVOS Y PROCEDIMIENTOS
        "DeclarativeProcedures",
        // EXPRESIONES CONDICIONALES
        "ConditionalExpressions",
        // SENTENCIAS ARITMÉTICAS
        "ArithmeticStatements",
        // INDEXACIÓN Y SUSCRIPCIÓN
        "IndexingSubscripting",
        // FUNCIONES Y REGISTROS ESPECIALES
        "IntrinsicFunction",
        "DateHandling",
        "SpecialRegister",
        "LengthFunction",
        // DIRECTIVAS Y COPY
        "CompilerDirective",
        "CopyStatement"
    };

    @Override
    public void define(Context context) {
        NewBuiltInQualityProfile profile = context.createBuiltInQualityProfile(
                PROFILE_NAME, CobolLanguage.KEY);
        profile.setDefault(true);

        for (String ruleKey : ALL_RULE_KEYS) {
            profile.activateRule(REPO_KEY, ruleKey);
        }

        profile.done();
    }
}
