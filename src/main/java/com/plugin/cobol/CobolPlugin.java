package com.plugin.cobol;

import com.plugin.cobol.checks.*;
import com.plugin.cobol.cpd.CobolCpdSensor;
import com.plugin.cobol.language.CobolLanguage;
import com.plugin.cobol.language.CobolQualityProfile;
import com.plugin.cobol.rules.CobolRulesDefinition;
import org.sonar.api.Plugin;

/**
 * Plugin principal de SonarQube para verificar el cumplimiento de las normas
 * y extensiones COBOL para Linux on x86 (v1.2.0).
 *
 */
public class CobolPlugin implements Plugin {

    @Override
    public void define(Context context) {
        // Registrar el lenguaje COBOL
        context.addExtension(CobolLanguage.class);

        // Registrar perfil de calidad
        context.addExtension(CobolQualityProfile.class);

        // Registrar definición de reglas
        context.addExtension(CobolRulesDefinition.class);

        // Registrar sensor principal
        context.addExtension(CobolSensor.class);

        // Registrar sensor CPD (detección de duplicados)
        context.addExtension(CobolCpdSensor.class);

        // === REGLAS DE VERIFICACIÓN ===

        // División de Identificación
        context.addExtension(ProgramIdCheck.class);
        context.addExtension(RecursiveClauseCheck.class);

        // Soporte de caracteres y literales
        context.addExtension(NationalCharacterSupportCheck.class);
        context.addExtension(HexLiteralCheck.class);
        context.addExtension(NullTerminatedLiteralCheck.class);
        context.addExtension(FloatingPointLiteralCheck.class);
        context.addExtension(FixedPointDigitsCheck.class);

        // Data Division
        context.addExtension(LocalStorageSectionCheck.class);
        context.addExtension(PictureClauseCheck.class);
        context.addExtension(UsageClauseCheck.class);
        context.addExtension(OccursClauseCheck.class);
        context.addExtension(RedefinesClauseCheck.class);
        context.addExtension(DateFormatClauseCheck.class);
        context.addExtension(LevelNumberCheck.class);

        // Procedure Division
        context.addExtension(GobackStatementCheck.class);
        context.addExtension(EntryStatementCheck.class);
        context.addExtension(XmlParseStatementCheck.class);
        context.addExtension(XmlGenerateStatementCheck.class);
        context.addExtension(CallStatementCheck.class);
        context.addExtension(PerformStatementCheck.class);
        context.addExtension(ExitProgramCheck.class);
        context.addExtension(GoToStatementCheck.class);
        context.addExtension(SearchStatementCheck.class);
        context.addExtension(SetStatementCheck.class);
        context.addExtension(DisplayStatementCheck.class);
        context.addExtension(AcceptStatementCheck.class);
        context.addExtension(WriteStatementCheck.class);
        context.addExtension(ReadStatementCheck.class);
        context.addExtension(StartStatementCheck.class);
        context.addExtension(ComputeStatementCheck.class);
        context.addExtension(InitializeStatementCheck.class);
        context.addExtension(StringUnstringCheck.class);

        // File Section
        context.addExtension(FileControlCheck.class);
        context.addExtension(FileStatusCheck.class);
        context.addExtension(LinageClauseCheck.class);
        context.addExtension(OrganizationLineSequentialCheck.class);

        // Special Names y entorno
        context.addExtension(SpecialNamesCheck.class);
        context.addExtension(CurrencySignCheck.class);

        // Comentarios
        context.addExtension(InlineCommentCheck.class);
        context.addExtension(CommentBeforeIdDivisionCheck.class);

        // Formato de referencia
        context.addExtension(ExtendedSourceFormatCheck.class);

        // Directivas de compilador
        context.addExtension(CompilerDirectiveCheck.class);
        context.addExtension(CopyStatementCheck.class);

        // Funciones intrínsecas
        context.addExtension(IntrinsicFunctionCheck.class);

        // Manejo de fechas Y2K
        context.addExtension(DateHandlingCheck.class);

        // Registros especiales
        context.addExtension(SpecialRegisterCheck.class);

        // Indexación y suscripción
        context.addExtension(IndexingSubscriptingCheck.class);

        // === COBERTURA COMPLETA - CHECKS ADICIONALES ===
        context.addExtension(CobolUserDefinedWordsCheck.class);
        context.addExtension(FigurativeConstantsCheck.class);
        context.addExtension(MixedLiteralCheck.class);
        context.addExtension(IOControlCheck.class);
        context.addExtension(FileControlExtendedCheck.class);
        context.addExtension(FileSectionExtendedCheck.class);
        context.addExtension(DataDivisionExtendedCheck.class);
        context.addExtension(ProcedureDivisionHeaderCheck.class);
        context.addExtension(DeclarativeProceduresCheck.class);
        context.addExtension(ConditionalExpressionsCheck.class);
        context.addExtension(ArithmeticStatementsCheck.class);
        context.addExtension(CancelStatementCheck.class);
        context.addExtension(CloseStatementCheck.class);
        context.addExtension(ExitStatementCheck.class);
        context.addExtension(IfStatementCheck.class);
        context.addExtension(MergeStatementCheck.class);
        context.addExtension(OpenStatementCheck.class);
        context.addExtension(ReturnStatementCheck.class);
        context.addExtension(RewriteStatementCheck.class);
        context.addExtension(SortStatementCheck.class);
        context.addExtension(StopStatementCheck.class);
        context.addExtension(InvalidKeyCheck.class);
        context.addExtension(LengthFunctionCheck.class);
        context.addExtension(SymbolicCharactersCheck.class);
    }
}
