package com.plugin.cobol;

import com.plugin.cobol.checks.*;
import com.plugin.cobol.language.CobolLanguage;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.SensorDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.measures.CoreMetrics;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Sensor principal que analiza archivos COBOL y aplica todas las reglas
 * de cumplimiento con las normas y extensiones COBOL.
 */
public class CobolSensor implements Sensor {

    private static final Logger LOG = LoggerFactory.getLogger(CobolSensor.class);

    private final List<AbstractCobolCheck> checks;

    public CobolSensor() {
        this.checks = Arrays.asList(
                new ProgramIdCheck(),
                new RecursiveClauseCheck(),
                new NationalCharacterSupportCheck(),
                new HexLiteralCheck(),
                new NullTerminatedLiteralCheck(),
                new FloatingPointLiteralCheck(),
                new FixedPointDigitsCheck(),
                new LocalStorageSectionCheck(),
                new PictureClauseCheck(),
                new UsageClauseCheck(),
                new OccursClauseCheck(),
                new RedefinesClauseCheck(),
                new DateFormatClauseCheck(),
                new LevelNumberCheck(),
                new GobackStatementCheck(),
                new EntryStatementCheck(),
                new XmlParseStatementCheck(),
                new XmlGenerateStatementCheck(),
                new CallStatementCheck(),
                new PerformStatementCheck(),
                new ExitProgramCheck(),
                new GoToStatementCheck(),
                new SearchStatementCheck(),
                new SetStatementCheck(),
                new DisplayStatementCheck(),
                new AcceptStatementCheck(),
                new WriteStatementCheck(),
                new ReadStatementCheck(),
                new StartStatementCheck(),
                new ComputeStatementCheck(),
                new InitializeStatementCheck(),
                new StringUnstringCheck(),
                new FileControlCheck(),
                new FileStatusCheck(),
                new LinageClauseCheck(),
                new OrganizationLineSequentialCheck(),
                new SpecialNamesCheck(),
                new CurrencySignCheck(),
                new InlineCommentCheck(),
                new CommentBeforeIdDivisionCheck(),
                new ExtendedSourceFormatCheck(),
                new CompilerDirectiveCheck(),
                new CopyStatementCheck(),
                new IntrinsicFunctionCheck(),
                new DateHandlingCheck(),
                new SpecialRegisterCheck(),
                new IndexingSubscriptingCheck(),
                // Cobertura completa
                new CobolUserDefinedWordsCheck(),
                new FigurativeConstantsCheck(),
                new MixedLiteralCheck(),
                new IOControlCheck(),
                new FileControlExtendedCheck(),
                new FileSectionExtendedCheck(),
                new DataDivisionExtendedCheck(),
                new ProcedureDivisionHeaderCheck(),
                new DeclarativeProceduresCheck(),
                new ConditionalExpressionsCheck(),
                new ArithmeticStatementsCheck(),
                new CancelStatementCheck(),
                new CloseStatementCheck(),
                new ExitStatementCheck(),
                new IfStatementCheck(),
                new MergeStatementCheck(),
                new OpenStatementCheck(),
                new ReturnStatementCheck(),
                new RewriteStatementCheck(),
                new SortStatementCheck(),
                new StopStatementCheck(),
                new InvalidKeyCheck(),
                new LengthFunctionCheck(),
                new SymbolicCharactersCheck()
        );
    }

    @Override
    public void describe(SensorDescriptor descriptor) {
        descriptor
                .name("COBOL Standards Sensor")
                .onlyOnLanguage(CobolLanguage.KEY)
                .createIssuesForRuleRepositories("cobol-ibm-standards");
    }

    @Override
    public void execute(SensorContext context) {
        FileSystem fs = context.fileSystem();
        Iterable<InputFile> inputFiles = fs.inputFiles(
                fs.predicates().hasLanguage(CobolLanguage.KEY));

        for (InputFile inputFile : inputFiles) {
            analyzeFile(context, inputFile);
        }
    }

    private void analyzeFile(SensorContext context, InputFile inputFile) {
        try {
            String content = inputFile.contents();
            LOG.debug("Analizando archivo COBOL: {}", inputFile.filename());

            int totalLines = inputFile.lines();
            int ncloc = calculateNcloc(content);

            context.<Integer>newMeasure()
                    .forMetric(CoreMetrics.LINES)
                    .on(inputFile)
                    .withValue(totalLines)
                    .save();

            context.<Integer>newMeasure()
                    .forMetric(CoreMetrics.NCLOC)
                    .on(inputFile)
                    .withValue(ncloc)
                    .save();

            for (AbstractCobolCheck check : checks) {
                try {
                    check.analyze(context, inputFile, content);
                } catch (Exception e) {
                    LOG.warn("Error al aplicar regla {} en {}: {}",
                            check.getClass().getSimpleName(), inputFile.filename(), e.getMessage());
                }
            }

        } catch (IOException e) {
            LOG.error("Error al leer el archivo {}: {}", inputFile.filename(), e.getMessage());
        }
    }

    private int calculateNcloc(String content) {
        int count = 0;
        String[] lines = content.split("\\r?\\n");

        for (String line : lines) {
            String trimmed = line.trim();
            if (!trimmed.isEmpty() && !trimmed.startsWith("*")) {
                count++;
            }
        }
        return count;
    }
}
