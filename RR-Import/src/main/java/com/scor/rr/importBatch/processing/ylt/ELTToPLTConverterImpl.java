package com.scor.rr.importBatch.processing.ylt;

import com.scor.rr.domain.entities.ihub.SourceResult;
import com.scor.rr.domain.entities.omega.Contract;
import com.scor.rr.domain.entities.plt.PET;
import com.scor.rr.domain.entities.plt.PLTConverterProgress;
import com.scor.rr.domain.entities.plt.RRAnalysis;
import com.scor.rr.domain.entities.plt.ScorPLTHeader;
import com.scor.rr.domain.entities.plt.meta.PLTModelingBasis;
import com.scor.rr.domain.entities.rap.SourceRapMapping;
import com.scor.rr.domain.entities.references.TargetRAP;
import com.scor.rr.domain.entities.references.omega.BinFile;
import com.scor.rr.domain.entities.references.omega.Currency;
import com.scor.rr.domain.entities.references.plt.TTFinancialPerspective;
import com.scor.rr.domain.entities.rms.ProjectImportAssetLog;
import com.scor.rr.domain.entities.rms.RmsAnalysis;
import com.scor.rr.domain.entities.tracking.ProjectImportLog;
import com.scor.rr.domain.entities.workspace.Project;
import com.scor.rr.domain.enums.PLTType;
import com.scor.rr.domain.enums.RRLossTableType;
import com.scor.rr.domain.enums.TrackingStatus;
import com.scor.rr.importBatch.processing.batch.BaseBatchBeanImpl;
import com.scor.rr.importBatch.processing.batch.BaseTruncator;
import com.scor.rr.importBatch.processing.domain.Period;
import com.scor.rr.importBatch.processing.domain.loss.CMBetaConvertFunctionFactory;
import com.scor.rr.importBatch.processing.domain.loss.ConvertFunctionFactory;
import com.scor.rr.importBatch.processing.treaty.PLTBundle;
import com.scor.rr.importBatch.processing.treaty.TransformationBundle;
import com.scor.rr.importBatch.processing.treaty.TransformationPackage;
import com.scor.rr.importBatch.processing.treaty.loss.*;
import com.scor.rr.importBatch.processing.ylt.meta.PLTPublishStatus;
import com.scor.rr.importBatch.processing.ylt.meta.XLTOT;
import com.scor.rr.repository.ihub.SourceResultRepository;
import com.scor.rr.repository.omega.ContractRepository;
import com.scor.rr.repository.omega.CurrencyRepository;
import com.scor.rr.repository.plt.PLTConverterProgressRepository;
import com.scor.rr.repository.plt.RRAnalysisRepository;
import com.scor.rr.repository.plt.TTFinancialPerspectiveRepository;
import com.scor.rr.repository.rap.SourceRapMappingRepository;
import com.scor.rr.repository.rap.TargetRapRepository;
import com.scor.rr.repository.rms.ProjectImportAssetLogRepository;
import com.scor.rr.repository.rms.RmsAnalysisRepository;
import com.scor.rr.repository.stat.RRStatisticHeaderRepository;
import com.scor.rr.repository.tracking.ProjectImportLogRepository;
import com.scor.rr.repository.workspace.ProjectRepository;
import com.scor.rr.service.omega.DAOService;
import com.scor.rr.utils.Step;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.concurrent.*;

//import java.io.File;

/**
 * Created by u004602 on 18/10/2017.
 */
public class ELTToPLTConverterImpl extends BaseBatchBeanImpl implements ELTToPLTConverter {
    private static final Logger log= LoggerFactory.getLogger(ELTToPLTConverterImpl.class);

    private final int queueSize;

    private final int consumers;

    private final int peqtSize;

    private final int chunkSize;

    private final int bufferSize;

    private final Executor executor;

    private PEQTSelector selector;

    private String peqtPath;

    private TTFinancialPerspective fpUP;

    private BaseTruncator truncator;

    private Path ihubPath;

    private ConvertFunctionFactory factory = new CMBetaConvertFunctionFactory();

    public String getPeqtPath() {
        return peqtPath;
    }

    public void setPeqtPath(String peqtPath) {
        this.peqtPath = peqtPath;
    }

    public PEQTSelector getSelector() {
        return selector;
    }

    public void setSelector(PEQTSelector selector) {
        this.selector = selector;
    }

    public BaseTruncator getTruncator() {
        return truncator;
    }

    public void setTruncator(BaseTruncator truncator) {
        this.truncator = truncator;
    }

    @Autowired
    private TransformationPackage transformationPackage;

//    @Autowired
//    private MongoDBSequence mongoDBSequence;

    @Autowired
    private TTFinancialPerspectiveRepository ttFinancialPerspectiveRepository;

    @Autowired
    private RmsAnalysisRepository rmsAnalysisRepository;

    @Autowired
    private DAOService daoService;

    @Autowired
    private TargetRapRepository targetRapRepository;

    @Autowired
    private SourceRapMappingRepository sourceRapMappingRepository;

    @Autowired
    private PLTConverterProgressRepository pltConverterProgressRepository;

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private ProjectImportAssetLogRepository projectImportAssetLogRepository;

    @Autowired
    private ProjectImportLogRepository projectImportLogRepository;

    @Autowired
    private RRAnalysisRepository rrAnalysisRepository;

    @Autowired
    private SourceResultRepository sourceResultRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private RRStatisticHeaderRepository rrStatisticHeaderRepository;

    public ELTToPLTConverterImpl(int queueSize, int consumers, Executor executor, String filePath, int chunkSize, int bufferSize) {
        this.queueSize = queueSize;
        this.consumers = consumers;
        this.peqtSize = -1;
        this.executor = executor;
        this.ihubPath = Paths.get(filePath);
        this.chunkSize = chunkSize;
        this.bufferSize = bufferSize;
    }

    private void groupImportPltByPeqt(Map<String, List<ScorPLTHeader>> pltsByPeqt, List<ScorPLTHeader> pltHeaders) {
        if (pltHeaders != null && !pltHeaders.isEmpty()) {
            for (ScorPLTHeader pltHeader : pltHeaders) {
                PET pet = daoService.findPETBy(pltHeader.getTargetRAP());
                if (pet != null) {
                    List<ScorPLTHeader> scorPLTHeaders = pltsByPeqt.get(pet.getPeqtFile().getFileName());
                    if (scorPLTHeaders == null) {
                        scorPLTHeaders = new ArrayList<>();
                    }
                    scorPLTHeaders.add(pltHeader);
                    pltsByPeqt.put(pet.getPeqtFile().getFileName(), scorPLTHeaders);
                }
            }
        }
    }

    @Override
    public Boolean batchConvert() {
        log.debug("Starting batchConvert");

        Date startDate = new Date();
        if (fpUP == null) {
            fpUP = ttFinancialPerspectiveRepository.findByCode("UP");
        }
        log.debug("fpUP is {}", fpUP == null ? null : fpUP.getCode());
        log.debug("nbBundles is {}", transformationPackage.getBundles() == null ? null : transformationPackage.getBundles().size());

        PLTModelingBasis modelingBasis = PLTModelingBasis.AM;
        if (getContractId() != null && getUwYear() != null) {
            String[] tokens = StringUtils.split(getUwYear(), '-');
            Contract contract = contractRepository.findByTreatyIdAndUwYear(getContractId(), Integer.parseInt(tokens[0]));
            if (contract != null && contract.getContractSourceType() != null && "5".equals(contract.getContractSourceType().getContractSourceTypeId())) {
                modelingBasis = PLTModelingBasis.PM;
            }
        }

        log.debug("Modeling basis: {}", modelingBasis);

        Map<String, List<ScorPLTHeader>> pltsByPeqt = new HashMap<>();
        Map<String, TransformationBundle> bundleForPLT = new HashMap<>();

        for (TransformationBundle bundle : transformationPackage.getBundles()) {

            // start new step (import progress) : step 12 CONVERT_ELT_TO_PLT for this analysis in loop of many analysis :
            ProjectImportAssetLog projectImportAssetLogA = new ProjectImportAssetLog();
            //mongoDBSequence.nextSequenceId(projectImportAssetLogA);
            projectImportAssetLogA.setProjectId(transformationPackage.getProjectId());
            projectImportAssetLogA.setProjectId(getProjectId());
            projectImportAssetLogA.setStepId(12);
            projectImportAssetLogA.setStepName(Step.getStepNameFromStepIdForAnalysis(projectImportAssetLogA.getStepId()));
            projectImportAssetLogA.setStartDate(startDate);
            projectImportAssetLogA.setProjectImportLogId(bundle.getProjectImportLogAnalysisId());
            // --------------------------------------------------------------------

            SourceResult sourceResult = bundle.getSourceResult();
//            ImportDecision srImport = importDecisionRepository.findOne(sourceResult.getImportDecision().getId());
            RmsAnalysis analysis = sourceResult.getRmsAnalysis() == null ? null : rmsAnalysisRepository.findById(sourceResult.getRmsAnalysis().getRmsAnalysisId()).get();
            String analysisName = analysis == null ? null : analysis.getAnalysisName();
            String analysisId = analysis == null ? null : analysis.getAnalysisId().toString();
            RRAnalysis rrAnalysis = null;
            Map<String, String> fpRRAnalysis = transformationPackage.getMapAnalysisRRAnalysisIds().get(bundle.getSourceResult().getSourceResultId());
            if (fpRRAnalysis != null && fpRRAnalysis.get(bundle.getFinancialPerspective().getDisplayCode()) != null) {
                rrAnalysis  = rrAnalysisRepository.findById(fpRRAnalysis.get(bundle.getFinancialPerspective().getDisplayCode())).get();
            }

            if (fpUP == null) {
//                TransformationUtils.setImportDecisionError(importDecisionRepository, srImport, String.format("Error: no UP financial perspective found for analysis %s , id %s", analysisName, analysisId));
                log.error("Error creating PLTs: no UP financial perspective found for analysis {} , id {}", analysisName, analysisId);

                ProjectImportLog projectImportLogA = projectImportLogRepository.findById(bundle.getProjectImportLogAnalysisId()).get();
                projectImportAssetLogA.getErrorMessages().add(String.format("Error: no UP financial perspective found for analysis %s , id %s", analysisName, analysisId));
                Date endDate = new Date();
                projectImportAssetLogA.setEndDate(endDate);
                projectImportAssetLogRepository.save(projectImportAssetLogA);
                log.info("Finish import progress STEP 12 : CONVERT_ELT_TO_PLT for analysis: {}", bundle.getSourceResult().getSourceResultId());
                projectImportLogA.setEndDate(endDate);
                projectImportLogA.setStatus(TrackingStatus.ERROR.toString());
                projectImportLogRepository.save(projectImportLogA);
                log.info("Finish tracking at the end of STEP 12 : CONVERT_ELT_TO_PLT for analysis {}, status {}", bundle.getSourceResult().getSourceResultId(), "Error", "stop this tracking");

                if (rrAnalysis != null) {
                    rrAnalysis.setImportStatus(TrackingStatus.ERROR.toString());
                    sourceResult.setImportStatus(rrAnalysis.getImportStatus());
                    sourceResultRepository.save(sourceResult);
                    rrAnalysisRepository.save(rrAnalysis);
                }
                continue;
            }
            SourceRapMapping sourceRapMapping = sourceRapMappingRepository.findById(bundle.getSourceRapMapping().getSourceRapMappingId()).get();
            List<TargetRAP> targetRaps = targetRapRepository.findByModellingVendorAndModellingSystemAndModellingSystemVersionAndSourceRapCode(sourceRapMapping.getModellingVendor(),
                    sourceRapMapping.getModellingSystem(),
                    sourceRapMapping.getModellingSystemVersion(),
                    sourceRapMapping.getSourceRapCode());
            if (targetRaps == null || targetRaps.isEmpty()) {
                String errMsg = String.format("Error: no targetRap found for sourceRapMapping (ModellingVendor %s ModellingSystem %s ModellingSystemVersion %s SourceRapCode %s) for analysis %s , id %s",
                        sourceRapMapping.getModellingVendor(), sourceRapMapping.getModellingSystem(), sourceRapMapping.getModellingSystemVersion(), sourceRapMapping.getSourceRapCode(), analysisName, analysisId);
//                TransformationUtils.setImportDecisionError(importDecisionRepository, srImport, errMsg);
                log.error(errMsg);

                // tracking this analysis
                ProjectImportLog projectImportLogA = projectImportLogRepository.findById(bundle.getProjectImportLogAnalysisId()).get();
                projectImportAssetLogA.getErrorMessages().add(errMsg);
                Date endDate = new Date();
                projectImportAssetLogA.setEndDate(endDate);
                projectImportAssetLogRepository.save(projectImportAssetLogA);
                log.info("Finish import progress STEP 12 : CONVERT_ELT_TO_PLT for analysis: {}", bundle.getSourceResult().getSourceResultId());
                projectImportLogA.setEndDate(endDate);
                projectImportLogA.setStatus(TrackingStatus.ERROR.toString());
                projectImportLogRepository.save(projectImportLogA);
                log.info("Finish tracking at the end of STEP 12 : CONVERT_ELT_TO_PLT for analysis {}, status {}", bundle.getSourceResult().getSourceResultId(), "Error", "stop this tracking");

                if (rrAnalysis != null) {
                    rrAnalysis.setImportStatus(TrackingStatus.ERROR.toString());
                    sourceResult.setImportStatus(rrAnalysis.getImportStatus());
                    sourceResultRepository.save(sourceResult);
                    rrAnalysisRepository.save(rrAnalysis);
                }

                continue;
            }

            if (sourceResult.getIncludedTargetRapIds() != null)
            {
                for (int i = 0; i < targetRaps.size(); i++)
                {
                    if (!sourceResult.getIncludedTargetRapIds().contains(String.valueOf(targetRaps.get(i).getTargetRAPId())))
                    {
                        targetRaps.remove(i);
                        i--;
                    }
                }
            }

            List<ScorPLTHeader> pltHeaders = makePurePLTHeaders(bundle, targetRaps, modelingBasis);
            if (pltHeaders == null || pltHeaders.isEmpty()) {
//                log.error("RRLT {} has no PLTs, dataset {} error", bundle.getConformedELTHeader().getId(), bundle.getConformedELTHeader().getRepresentationDataset().getId());
                log.error("RRLT {} has no PLTs, error", bundle.getConformedRRLT().getRrLossTableId());
                String errorMessage = String.format("Error: no TargetRap found for SourceRap %s (id %s), analysis %s (id %s)", bundle.getSourceRapMapping().getSourceRapCode(), bundle.getSourceRapMapping().getSourceRapMappingId(), bundle.getRmsAnalysis().getAnalysisName(), bundle.getRmsAnalysis().getRmsAnalysisId());
                // TODO - to be continued
                bundle.setErrorMessage(errorMessage);
                // TODO - Add REST NOTIFIER TO BROADCAST ERRORS - this case, PLTBundle null

                // end this step but not end tracking
                projectImportAssetLogA.getErrorMessages().add(errorMessage);
                Date endDate = new Date();
                projectImportAssetLogA.setEndDate(endDate);
                projectImportAssetLogRepository.save(projectImportAssetLogA);

                continue;
            }

            //PLT Truncator
            String region = bundle.getRmsAnalysis().getRegion();
            String peril = bundle.getRmsAnalysis().getPeril();
            String currency = bundle.getRmsAnalysis().getAnalysisCurrency();
//            double threshold = truncator.getThresholdFor(region, peril, currency, "PLT");
            double threshold = 0.0;

            for (ScorPLTHeader pltHeader : pltHeaders) {
                PLTBundle pltBundle = new PLTBundle();
                pltBundle.setHeader(pltHeader);
                bundle.addPLTBundle(pltBundle);

                //Truncator
                pltHeader.setTruncationCurrency(bundle.getTruncationCurrency());
                pltHeader.setTruncationThreshold(threshold);
//                pltHeader.setTruncationThresholdEur(truncator.getThresholdInEur());
                pltHeader.setTruncationThresholdEur(threshold);

                //Loss data fle
                String filename = makePLTFileName(
                        pltHeader.getCreationDate(),
                        pltHeader.getRegionPeril().getRegionPerilCode(),
                        pltHeader.getFinancialPerspective().getCode(),
                        pltHeader.getCurrency().getCode(),
                        XLTOT.TARGET,
                        pltHeader.getTargetRAP().getTargetRAPId(),
                        pltHeader.getPltSimulationPeriods(),
                        PLTPublishStatus.PURE,
                        0, // pure PLT, no thread number
                        pltHeader.getScorPLTHeaderId(),
                        getFileExtension());
                File file = makeFullFile(getPrefixDirectory(), filename);
                pltHeader.setPltLossDataFile(new BinFile(file));

                bundleForPLT.put(pltHeader.getScorPLTHeaderId(), bundle);

                pltConverterProgressRepository.save(new PLTConverterProgress(pltHeader.getScorPLTHeaderId(),
                        pltHeader.getProject().getProjectId(),
                        sourceResult.getRmsAnalysis().getRdmId(),
                        sourceResult.getRmsAnalysis().getRdmName(),
                        sourceResult.getRmsAnalysis().getAnalysisId(),
                        pltHeader.getPeqtFile().getFileName(),
                        pltHeader.getImportSequence()));
            }

            groupImportPltByPeqt(pltsByPeqt, pltHeaders);

            // TODO finish step 12 CONVERT_ELT_TO_PLT for one analysis in loop for of many analysis ???????????????? must ask Viet
            Date endDate = new Date();
            projectImportAssetLogA.setEndDate(endDate);
            projectImportAssetLogRepository.save(projectImportAssetLogA);
            log.info("Finish import progress STEP 12 : CONVERT_ELT_TO_PLT for analysis: {}", bundle.getSourceResult().getSourceResultId());
        }
        // end loop for of bundles in function batchConvert()

        int nbLauncher = 0;
        for (Map.Entry<String, List<ScorPLTHeader>> entry : pltsByPeqt.entrySet()) {
            nbLauncher += entry.getValue().size() / chunkSize + 1;
        }
        CountDownLatch launcherCountDown = new CountDownLatch(nbLauncher);
        for (Map.Entry<String, List<ScorPLTHeader>> entry : pltsByPeqt.entrySet()) {
            log.debug("Running ELT2PLT convertor for PEQT file {}", entry.getKey());
            BinFile peqtFile = new BinFile(entry.getKey(), peqtPath, null);
            int nbTrunk = entry.getValue().size() / chunkSize + 1;
//            int nbTrunk = 1;
            for (int i = 0; i < nbTrunk; i++) {
                int start = i * chunkSize;
                int end = (i + 1) * chunkSize < entry.getValue().size() ? (i + 1) * chunkSize : entry.getValue().size();
                List<ScorPLTHeader> scorPLTHeaders = entry.getValue().subList(start, end);
//                List<ScorPLTHeader> scorPLTHeaders = entry.getValue();
                Map<String, Map<Long, ELTLossBetaConvertFunction>> convertFunctionMapForPLT = new HashMap<>(scorPLTHeaders.size());
                for (ScorPLTHeader scorPLTHeader : scorPLTHeaders) {
                    TransformationBundle bundle = bundleForPLT.get(scorPLTHeader.getScorPLTHeaderId());
                    if (bundle != null) {
                        AnalysisELTnBetaFunction analysisELTnBetaFunction = bundle.getAnalysisELTnBetaFunction();
                        log.debug("analysisELTnBetaFunction.getEltLosses().size() = {}", analysisELTnBetaFunction.getEltLosses().size());
                        Map<Long, ELTLossBetaConvertFunction> convertFunctionMap = new HashMap<>(analysisELTnBetaFunction.getEltLosses().size());
                        for (int j = 0; j < analysisELTnBetaFunction.getEltLosses().size(); j++) {
                            ELTLossnBetaFunction eltLossnBetaFunction = analysisELTnBetaFunction.getEltLosses().get(j);
                            ELTLossBetaConvertFunction convertFunction = new ELTLossBetaConvertFunction(eltLossnBetaFunction.getLoss(),
                                    eltLossnBetaFunction.getStdDevI(),
                                    eltLossnBetaFunction.getStdDevC(),
                                    eltLossnBetaFunction.getStdDevUSq(),
                                    eltLossnBetaFunction.getExposureValue(),
                                    eltLossnBetaFunction.getMinLayerAtt());
                            convertFunctionMap.put(eltLossnBetaFunction.getEventId(), convertFunction);
//                            eltLossnBetaFunction = null;
                        }
//                        analysisELTnBetaFunction.setEltLosses(null);
//                        bundle.setAnalysisELTnBetaFunction(null);
                        convertFunctionMapForPLT.put(scorPLTHeader.getScorPLTHeaderId(), convertFunctionMap);
                    }
                }
                executor.execute(new TreatyBatchLauncher(launcherCountDown, peqtFile, scorPLTHeaders, convertFunctionMapForPLT));
            }
        }

        try {
            launcherCountDown.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.debug("batchConvert completed");
        return true;
    }
    // end function batchConvert()


    private class TreatyBatchLauncher implements Runnable {
        private final int id;
        private BlockingQueue<Period> queue;
        private CountDownLatch latch;
        private BinFile peqtFile;
        private List<ScorPLTHeader> scorPLTHeaders;
        Map<String, Map<Long, ELTLossBetaConvertFunction>> convertFunctionMapForPLT;
        private Boolean[] finished = { Boolean.FALSE };
        private int nbWorkers;

        public TreatyBatchLauncher(CountDownLatch latch, BinFile peqtFile, List<ScorPLTHeader> scorPLTHeaders,
                                   Map<String, Map<Long, ELTLossBetaConvertFunction>> convertFunctionMapForPLT) {
            this.id = hashCode();
            this.queue = new ArrayBlockingQueue<>(queueSize);
            this.latch = latch;
            this.peqtFile = peqtFile;
            this.scorPLTHeaders = scorPLTHeaders;
            this.convertFunctionMapForPLT = convertFunctionMapForPLT;
            nbWorkers = 1;
        }

        private final  static int PERIOD_HEADER_SIZE=8;
        private final  static int PERIOD_EVENT_SIZE=21;

        private void closeDirectBuffer(ByteBuffer cb)
        {
            if(cb == null || !cb.isDirect()) return;
            try
            {
                Method cleaner = cb.getClass().getMethod("cleaner");
                cleaner.setAccessible(true);
                Method clean = Class.forName("sun.misc.Cleaner").getMethod("clean");
                clean.setAccessible(true);
                clean.invoke(cleaner.invoke(cb));
                log.trace("cleaned buffer");
            }
            catch(Exception ex)
            {
                log.debug("not able to clean buffer, let the GC do it's work", ex);
            }
            cb = null;
        }

        @Override
        public void run() {
            CountDownLatch workerLatch = new CountDownLatch(nbWorkers);
            ExecutorService pool = Executors.newFixedThreadPool(nbWorkers);
            com.codahale.metrics.Timer timer = new com.codahale.metrics.Timer();
            Map<String, FileOutputStream > outputStreamForPLT = new HashMap<>();
            for (ScorPLTHeader pltHeader : scorPLTHeaders) {
                File file = new File(pltHeader.getPltLossDataFile().getPath(), pltHeader.getPltLossDataFile().getFileName());
                try {
                    FileOutputStream  outputStream = new FileOutputStream(file);
                    outputStreamForPLT.put(pltHeader.getScorPLTHeaderId(), outputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                    continue;
                }
            }

            Date startConvert = new Date();
            for (ScorPLTHeader scorPLTHeader : scorPLTHeaders) {
                PLTConverterProgress pltConverterProgress = pltConverterProgressRepository.findByPltId(scorPLTHeader.getScorPLTHeaderId());
                pltConverterProgress.setStartConvert(startConvert);
                pltConverterProgressRepository.save(pltConverterProgress);
            }
            pool.execute(new TreatyBatchWorker(id, finished, queue, workerLatch, timer, scorPLTHeaders, convertFunctionMapForPLT, outputStreamForPLT));

            FileChannel fc = null;
            log.info("Master {}: reading PEQT {}", this.id, peqtFile.getFileName());
            int periodCount = 0;
            try {
                File f = new File(peqtFile.getPath(), peqtFile.getFileName());
                fc = FileChannel.open(f.toPath(), StandardOpenOption.READ);
                long fileSize=fc.size();
                long bufferSize=1024*1024*16;

                if(fileSize<bufferSize)
                {
                    bufferSize=(int)fileSize;
                }

                MappedByteBuffer ib=fc.map(FileChannel.MapMode.READ_ONLY, 0, bufferSize);
                ib.order(ByteOrder.LITTLE_ENDIAN);
                long time=System.currentTimeMillis();
                long filePosition=0;
                int remainingBuffer=0;

                while(filePosition+ib.position()<fileSize) {
                    remainingBuffer = ib.remaining();
                    //
                    if (remainingBuffer < PERIOD_HEADER_SIZE) {

                        filePosition += ib.position();
                        closeDirectBuffer(ib);
                        ib = fc.map(FileChannel.MapMode.READ_ONLY, filePosition, Math.min(bufferSize, (fileSize - filePosition)));
                        ib.order(ByteOrder.LITTLE_ENDIAN);
                    }
                    final int period = ib.getInt();
                    int nbEvents = ib.getInt();

                    if(nbEvents>0)
                    {
                        if((remainingBuffer - PERIOD_HEADER_SIZE) <(PERIOD_EVENT_SIZE*nbEvents))
                        {
                            if((PERIOD_EVENT_SIZE*nbEvents)+PERIOD_HEADER_SIZE>bufferSize)
                            {
                                bufferSize=(PERIOD_EVENT_SIZE*nbEvents)+PERIOD_HEADER_SIZE;
                            }
                            filePosition+=ib.position();
                            closeDirectBuffer(ib);
                            ib=fc.map(FileChannel.MapMode.READ_ONLY, filePosition, Math.min(bufferSize, (fileSize-filePosition)));
                            ib.order(ByteOrder.LITTLE_ENDIAN);
                        }

                        periodCount++;
                        Period p = new Period();
                        p.setNb(period);
                        p.setEvents(new int[nbEvents]);
                        p.setSeqs(new int[nbEvents]);
                        p.setQuantiles(new double[nbEvents]);
                        p.setEventDates(new long[nbEvents]);
                        int i = 0;
                        while (nbEvents > 0) {
                            nbEvents--;
                            p.getEvents()[i] = ib.getInt();
                            p.getSeqs()[i] = ib.get();
                            p.getEventDates()[i] = ib.getLong();
                            p.getQuantiles()[i] = ib.getDouble();
                            i++;
                        }

                        if(periodCount%10000==0) log.info("reading PEQT '{}' period count {}",peqtFile.getFileName(), periodCount);

                        while(!queue.offer(p))
                        {
                            Thread.yield();
                        }

                    }
                }

                log.info("total time to read PEQT : {}", System.currentTimeMillis()-time);
            } catch (IOException e) {
                log.error("Master {}: reading PEQT {}, IOException", this.id, peqtFile.getFileName(), e);
            }finally {
                log.info("Master {}: finish reading PEQT {}, total period count {}", this.id, peqtFile.getFileName(), periodCount);
                IOUtils.closeQuietly(fc);
                pool.shutdown();
            }

            finished[0] = true;
            try {
                workerLatch.await();
            } catch (InterruptedException e) {
                log.error("Exception: {}, {}", peqtFile.getFileName(), e);
            }

            for (Map.Entry<String, FileOutputStream> entry : outputStreamForPLT.entrySet()) {
                IOUtils.closeQuietly(entry.getValue());
            }

            Date endConvert = new Date();
            for (ScorPLTHeader scorPLTHeader : scorPLTHeaders) {
                PLTConverterProgress pltConverterProgress = pltConverterProgressRepository.findByPltId(scorPLTHeader.getScorPLTHeaderId());
                pltConverterProgress.setEndConvert(endConvert);
                pltConverterProgressRepository.save(pltConverterProgress);

                Map<Long, ELTLossBetaConvertFunction> convertFunctionMap = convertFunctionMapForPLT.get(scorPLTHeader.getScorPLTHeaderId());
                if (convertFunctionMap != null) {
                    convertFunctionMap.clear();
                    convertFunctionMap = null;
                    convertFunctionMapForPLT.put(scorPLTHeader.getScorPLTHeaderId(), null);
                }
            }

            latch.countDown();
            log.info("Master {}: finish processing PEQT {}", this.id, peqtFile.getFileName());
            log.info("ELT2PLT {} nano 75%",timer.getSnapshot().get75thPercentile());
            log.info("ELT2PLT {} nano 95%",timer.getSnapshot().get95thPercentile());
            log.info("ELT2PLT {} nano min",timer.getSnapshot().getMin());
            log.info("ELT2PLT {} nano max",timer.getSnapshot().getMax());
        }
    }

    private class TreatyBatchWorker implements Runnable {
        private final int masterId;
        private final int id;
        private Boolean[] finished;
        private final BlockingQueue<Period> queue;
        private final CountDownLatch workerLatch;
        private List<ScorPLTHeader> scorPLTHeaders;
        private Map<String, Map<Long, ELTLossBetaConvertFunction>> convertFunctionMapForPLT;
        private Map<String, FileOutputStream> outputStreamForPLT;
        private com.codahale.metrics.Timer timer;
        private Comparator<PLTLossData> cmp;
        private Map<String, List<PLTLossData>> pltLossDataForPLT;


        public TreatyBatchWorker(int masterId, Boolean[] finished, BlockingQueue<Period> queue, CountDownLatch workerLatch, com.codahale.metrics.Timer timer, List<ScorPLTHeader> scorPLTHeaders,
                                 Map<String, Map<Long, ELTLossBetaConvertFunction>> convertFunctionMapForPLT, Map<String, FileOutputStream> outputStreamForPLT) {
            id = hashCode();
            this.masterId = masterId;
            this.finished = finished;
            this.queue = queue;
            this.workerLatch = workerLatch;
            this.timer = timer;
            this.scorPLTHeaders = scorPLTHeaders;
            this.convertFunctionMapForPLT = convertFunctionMapForPLT;
            this.outputStreamForPLT = outputStreamForPLT;
            this.cmp = new Comparator<PLTLossData>() {
                @Override
                public int compare(PLTLossData o1, PLTLossData o2) {
                    return new CompareToBuilder()
                            .append(o1.getSimPeriod(), o2.getSimPeriod())
                            .append(o1.getEventDate(), o2.getEventDate())
                            .append(o1.getEventId(), o2.getEventId())
                            .append(o1.getSeq(), o2.getSeq())
                            .toComparison();
                }
            };
            pltLossDataForPLT = new HashMap<>(scorPLTHeaders.size());
            for (ScorPLTHeader scorPLTHeader : scorPLTHeaders) {
                pltLossDataForPLT.put(scorPLTHeader.getScorPLTHeaderId(), new ArrayList<PLTLossData>());
            }
        }

        @Override
        public void run() {
            log.info("Slave {}.{}: polling data for processing", this.masterId, this.id);
            int periodCount = 0;
            do {
                try {
                    Period p = queue.poll(250, TimeUnit.MILLISECONDS);
                    if (p == null) {
                        if (!finished[0]) {
                            Thread.yield();
                            continue;
                        } else
                            break;
                    } else {
                        long time=System.nanoTime();
                        periodCount++;
                        for (ScorPLTHeader scorPLTHeader : scorPLTHeaders) {
                            Map<Long, ELTLossBetaConvertFunction> convertFunctionMap = convertFunctionMapForPLT.get(scorPLTHeader.getScorPLTHeaderId());
                            List<PLTLossData> pltLossDataList = pltLossDataForPLT.get(scorPLTHeader.getScorPLTHeaderId());
                            if (convertFunctionMap != null && pltLossDataList != null) {
                                process(p, convertFunctionMap, scorPLTHeader.getTruncationThreshold(), pltLossDataList);
                            }
                            if (periodCount % bufferSize == 0) {
                                FileOutputStream outputStream = outputStreamForPLT.get(scorPLTHeader.getScorPLTHeaderId());
                                if (outputStreamForPLT != null && pltLossDataList.size() > 0) {
                                    writeBufferToOutputStream(outputStream, pltLossDataList);
                                    pltLossDataList.clear();
                                }
                            }
                        }
                        timer.update(System.nanoTime()-time,TimeUnit.NANOSECONDS);
                    }
                } catch (Throwable e) {
                    log.error("Slave {}.{}: error {}", this.masterId, this.id, e);
                }
            } while (true);
            for (ScorPLTHeader scorPLTHeader : scorPLTHeaders) {
                FileOutputStream outputStream = outputStreamForPLT.get(scorPLTHeader.getScorPLTHeaderId());
                List<PLTLossData> pltLossDataList = pltLossDataForPLT.get(scorPLTHeader.getScorPLTHeaderId());
                if (outputStream != null && pltLossDataList.size() > 0) {
                    try {
                        writeBufferToOutputStream(outputStream, pltLossDataList);
                    } catch (IOException e) {
                        log.error("Slave {}.{}: error {}", this.masterId, this.id, e);
                    }
                    pltLossDataList.clear();
                }
            }
            log.info("Slave {}.{}: finished}", this.masterId, this.id);
            workerLatch.countDown();
        }

        private void writeBufferToOutputStream(FileOutputStream outputStream, List<PLTLossData> pltLossDataList) throws  IOException{
            byte[] out = new byte[26*pltLossDataList.size()];
            ByteBuffer buffer = ByteBuffer.wrap(out);
            buffer.order(ByteOrder.LITTLE_ENDIAN);
            Collections.sort(pltLossDataList, cmp);
            for (PLTLossData lossData : pltLossDataList) {
                buffer.putInt(lossData.getSimPeriod());
                buffer.putInt(lossData.getEventId());
                buffer.putLong(lossData.getEventDate());
                buffer.putShort((short) lossData.getSeq());
                buffer.putFloat((float) lossData.getMaxExposure());
                buffer.putFloat((float) lossData.getLoss());
            }
            outputStream.write(out);
        }

        private boolean process(Period p, Map<Long, ELTLossBetaConvertFunction> convertFunctionMap, double threshold, List<PLTLossData> pltLossDataList) throws IOException {
            int simPeriod = p.getNb();
            PLTLossPeriod lossPeriod = new PLTLossPeriod(simPeriod);

            for (int i = 0; i < p.getEvents().length; i++) {
                int eventID = p.getEvents()[i];
                int seq = p.getSeqs()[i];
//                            float quantile = p.getQuantiles()[i];
                double quantile = p.getQuantiles()[i];
                long eventDate = p.getEventDates()[i];

                ELTLossBetaConvertFunction convertFunction = convertFunctionMap.get(Long.valueOf(eventID));

                double eventLoss = 0.0d;
                if (convertFunction == null) { // event not exists in the ELT, loss = zero
                    //eventLoss = 0.0d;
                } else if (quantile == 0.0f) { // no quantile, loss = meanLoss
                    eventLoss = convertFunction.getLoss();
                } else {
                    log.trace("eventId|quantile|{}|{}",eventID,quantile);
                    if (convertFunction.getConvertFunction() == null) {
                        convertFunction.setConvertFunction(factory.buildFunction(
                                convertFunction.getLoss(),
                                convertFunction.getStdDevC(),
                                convertFunction.getStdDevI(),
                                convertFunction.getStdDevUSq(),
                                convertFunction.getExposureValue(),
                                convertFunction.getMinLayerAtt()));
                        convertFunctionMap.put(Long.valueOf(eventID), convertFunction);
                    }
                    eventLoss = convertFunction.getLossOverQuantile(quantile);
                    if (eventID == 15067999 || eventID == 15068006 || eventID == 15068543 || eventID == 15067929 || eventID == 15049996 || eventID == 15038124 || eventID == 15218210 || eventID == 15031551 || eventID == 15038069) {
                        System.out.println(simPeriod + ";" +
                                eventID + ";" +
                                convertFunction.getLoss() + ";" +
                                convertFunction.getStdDevC() + ";" +
                                convertFunction.getStdDevI() + ";" +
                                convertFunction.getExposureValue() + ";" +
                                convertFunction.getConvertFunction().getMu() + ";" +
                                convertFunction.getConvertFunction().getSigma() + ";" +
                                convertFunction.getConvertFunction().getAlpha() + ";" +
                                convertFunction.getConvertFunction().getBeta() + ";" +
                                quantile + ";" +
                                eventLoss);
                    }
                }
                if ((float) eventLoss > threshold) {
                    double maxExposure = convertFunction.getExposureValue();
                    PLTLossData lossData = new PLTLossData(eventID, eventDate, simPeriod, seq, maxExposure, eventLoss);
                    lossPeriod.addPLTLossData(lossData);
                }
            }

            if (lossPeriod.getPltLossDataByPeriods().size() > 0) {
                pltLossDataList.addAll(lossPeriod.getPltLossDataByPeriods());
                return true;
            }

            return false;
        }
    }

    private List<ScorPLTHeader> makePurePLTHeaders(TransformationBundle bundle, List<TargetRAP> targetRaps, PLTModelingBasis modelingBasis) {
        if (!RRLossTableType.CONFORMED.toString().equals(bundle.getConformedRRLT().getOriginalTarget())) {
            throw new IllegalStateException();
        }
        int i = 0;
        List<ScorPLTHeader> pltHeaders = new ArrayList<>();
        for (TargetRAP targetRap : targetRaps) {
            i++;

            PET pet = daoService.findPETBy(targetRap);
            com.scor.rr.domain.entities.cat.File file = pet.getPeqtFile();
            //read now only PEQT 100k
//            String peqt_100k = file.getFileName().replaceAll("(.+_)800K(.+)", "$1" + "100K" + "$2");
//            BinFile peqtFile = new BinFile(peqt_100k, peqtPath, null);
            BinFile peqtFile = new BinFile(file.getFileName(), peqtPath, null);


            ScorPLTHeader scorPLTHeader = new ScorPLTHeader();

            scorPLTHeader.setPeqtFile(peqtFile);
            scorPLTHeader.setPltLossDataFile(null);
//            scorPLTHeader.setPLTEPHeaders(null); // fill later TODO ????????????????????????????????????????????????????????????
            scorPLTHeader.setPltStatisticList(null);

//            scorPLTHeader.setPltGrouping(PLTGrouping.UnGrouped);
//            scorPLTHeader.setPltInuring(PLTInuring.None);
//            scorPLTHeader.setPltStatus(PLTStatus.Pending);
//            scorPLTHeader.setInuringPackageDefinition(null);
            scorPLTHeader.setPltSimulationPeriods(pet.getNumberSimulationPeriods());
            scorPLTHeader.setPltType(PLTType.Pure);

//            scorPLTHeader.setProject(eltHeader.getProject());
//            scorPLTHeader.setEltHeader(eltHeader);
//            scorPLTHeader.setCurrency(eltHeader.getCurrency());
//            scorPLTHeader.setRepresentationDataset(eltHeader.getRepresentationDataset());
//            scorPLTHeader.setTargetRap(targetRap);
//            scorPLTHeader.setRegionPeril(eltHeader.getRegionPeril());
//            scorPLTHeader.setSourceResult(eltHeader.getSourceResult());
//            scorPLTHeader.setFinancialPerspective(fpUP);

            Project project = projectRepository.findById(getProjectId()).get();
            scorPLTHeader.setProject(project);
            scorPLTHeader.setRrRepresentationDatasetId(transformationPackage.getRrRepresentationDatasetId());
            scorPLTHeader.setRrAnalysisId(bundle.getRrAnalysis().getRrAnalysisId());
            scorPLTHeader.setRrLossTableId(bundle.getConformedRRLT().getRrLossTableId());

            // TODO faux code
//            List<RRStatisticHeader> rrStatisticHeaderList = rrStatisticHeaderRepository.findByLossTableId(bundle.getConformedRRLT().getId());
//            scorPLTHeader.setPltStatisticList(rrStatisticHeaderList);

            Currency currency = currencyRepository.findByCode(bundle.getConformedRRLT().getCurrency());
            scorPLTHeader.setCurrency(currency);
//            scorPLTHeader.setRepresentationDataset(eltHeader.getRepresentationDataset());
            scorPLTHeader.setTargetRAP(targetRap);

            scorPLTHeader.setRegionPeril(bundle.getRegionPeril());
//            scorPLTHeader.setSourceResult(eltHeader.getSourceResult());
            scorPLTHeader.setFinancialPerspective(fpUP);

            //scorPLTHeader.setAdjustmentStructure(null);
            scorPLTHeader.setCatAnalysisDefinition(null);

            scorPLTHeader.setSourceScorPLTHeader(null);
            scorPLTHeader.setSystemShortName("Pure-" + i); // FIXME: 16/07/2016
            scorPLTHeader.setUserShortName("Pure-u-" + i); // FIXME: 16/07/2016
            scorPLTHeader.setTags(null);

            scorPLTHeader.setCreationDate(new Date());
            scorPLTHeader.setXActPublicationDate(null);
            scorPLTHeader.setXActUsed(false);
            scorPLTHeader.setXActAvailable(false);
            scorPLTHeader.setXActModelVersion(makeXActModelVersion(pet.getRmsModelVersion(), pet.getModelVersionSuffixForxAct()));

            scorPLTHeader.setGeneratedFromDefaultAdjustement(false);
            scorPLTHeader.setUserSelectedGrain(bundle.getRrAnalysis().getGrain());
            scorPLTHeader.setExportedDPM(false);
            scorPLTHeader.setRmsSimulationSet(pet.getRmsSimulationSetId());

            scorPLTHeader.setGeoCode(bundle.getRrAnalysis().getGeoCode());
            scorPLTHeader.setGeoDescription(bundle.getRrAnalysis().getGeoCode());
            scorPLTHeader.setPerilCode(bundle.getRrAnalysis().getPeril());

            scorPLTHeader.setEngineType(bundle.getRmsAnalysis().getEngineType());
            scorPLTHeader.setInstanceId(bundle.getInstanceId());
            scorPLTHeader.setImportSequence(getImportSequence());

            scorPLTHeader.setSourceLossModelingBasis(modelingBasis);

            String sourceFinPersp = bundle.getRrAnalysis().getFpDisplayCode();

            scorPLTHeader.setUdName(bundle.getRegionPeril().getRegionPerilCode() + "_" + sourceFinPersp + "_LMF1.T0");
            scorPLTHeader.setPltName(bundle.getRegionPeril().getRegionPerilCode() + "_" + sourceFinPersp + "_LMF1");

            pltHeaders.add(scorPLTHeader);

            //daoService.getMongoDBSequence().nextSequenceId(scorPLTHeader);
            log.info("PLT {} has targetRap {}: source code {}, target code {}", scorPLTHeader.getScorPLTHeaderId(), targetRap.getTargetRAPId(), targetRap.getSourceRapCode(), targetRap.getTargetRapCode());
        }
        return pltHeaders;
    }

    private String makeXActModelVersion(String engineVersionMajor, String modelVersionSuffixForxAct) {
        StringBuilder builder = new StringBuilder();
        if (engineVersionMajor != null) {
            builder.append(engineVersionMajor);
        }
        if (!StringUtils.isEmpty(modelVersionSuffixForxAct)) {
            builder.append(" ").append(modelVersionSuffixForxAct);
        }
        return builder.toString();
    }

    private File makeFullFile(String prefixDirectory, String filename) {
        final Path fullPath = ihubPath.resolve(prefixDirectory);
        try {
            Files.createDirectories(fullPath);
        } catch (IOException e) {
            log.error("Exception: ", e);
            throw new RuntimeException("error creating paths "+fullPath, e);
        }
        final File parent = fullPath.toFile();

        File file = new File(parent, filename);
        return file;
    }
}
