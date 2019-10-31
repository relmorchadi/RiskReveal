package com.scor.rr.service.batch;


import com.codahale.metrics.Timer;
import com.scor.rr.domain.PLTHeader;
import com.scor.rr.domain.RRFile;
import com.scor.rr.domain.RRFinancialPerspective;
import com.scor.rr.domain.dto.*;
import com.scor.rr.domain.enums.*;
import com.scor.rr.domain.model.AnalysisIncludedTargetRAP;
import com.scor.rr.domain.model.LossDataHeader;
import com.scor.rr.domain.model.PET;
import com.scor.rr.domain.model.TargetRAP;
import com.scor.rr.domain.reference.Contract;
import com.scor.rr.domain.reference.FinancialPerspective;
import com.scor.rr.domain.riskLink.RLAnalysis;
import com.scor.rr.domain.riskLink.RlSourceResult;
import com.scor.rr.domain.riskReveal.RRAnalysis;
import com.scor.rr.repository.*;
import com.scor.rr.service.batch.writer.AbstractWriter;
import com.scor.rr.service.calculation.CMBetaConvertFunctionFactory;
import com.scor.rr.service.calculation.ConvertFunctionFactory;
import com.scor.rr.service.state.TransformationBundle;
import com.scor.rr.service.state.TransformationPackage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.math.BigInteger;
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

import static com.scor.rr.util.PathUtils.makePLTFileName;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

@Service
@StepScope
@Slf4j
public class ELTToPLTConverter extends AbstractWriter {

    FinancialPerspective financialPerspective;
    @Autowired
    FinancialPerspectiveRepository financialPerspectiveRepository;

    @Autowired
    ContractRepository contractRepository;

    @Autowired
    RlAnalysisRepository rlAnalysisRepository;

    @Autowired
    RRAnalysisRepository rrAnalysisRepository;

    @Autowired
    AnalysisIncludedTargetRAPRepository analysisIncludedTargetRAPRepository;

    @Autowired
    TargetRAPRepository targetRAPRepository;

    @Autowired
    PETRepository petRepository;

    @Autowired
    @Qualifier("importExecutor")
    Executor executor;

    @Value("${ihub.treaty.peqt.path}")
    private String peqtPath;

    private Path ihubPath;

    @Value("${ihub.treaty.out.path}")
    private void setIhubPath(String path){
        this.ihubPath= Paths.get(path);
    }

    @Value("${convert.executor.chunk.size}")
    private int chunkSize;

    @Value("${convert.executor.buffer.size}")
    private int bufferSize;

    @Value("${convert.executor.queue.size}")
    private int queueSize;

    @Value("#{jobParameters['importSequence']}")
    private Integer importSequence;

    @Value("#{jobParameters['contractId']}")
    private String contractId;

    @Value("#{jobParameters['uwYear']}")
    private Integer uwYear;

    @Value("#{jobParameters['prefix']}")
    private String prefix;

    @Autowired
    TransformationPackage transformationPackage;

    private ConvertFunctionFactory factory = new CMBetaConvertFunctionFactory();

    public RepeatStatus convertEltToPLT() {
        batchConvert();
        return RepeatStatus.FINISHED;
    }

    public void batchConvert() {
        log.debug("Starting batchConvert");
        Date startDate = new Date();
        // @TODO Get TTFinancial persp Ref Table
        financialPerspective = ofNullable(financialPerspective).orElse(financialPerspectiveRepository.findByCode(FinancialPerspectiveCodeEnum.UP.getCode()));
        log.debug("fpUP is {}", ofNullable(financialPerspective).map(FinancialPerspective::getCode).orElse(null));
        log.debug("nbBundles is {}", transformationPackage.getTransformationBundles().size());
        PLTModelingBasis modelingBasis = getModelingBasis();
        log.debug("Modeling basis: {}", modelingBasis);

        Map<String, List<PLTHeader>> pltsByPeqt = new HashMap<>();
        Map<Long, TransformationBundle> bundleForPLT = new HashMap<>();

        for (TransformationBundle bundle : transformationPackage.getTransformationBundles()) {
            RlSourceResult sourceResult = bundle.getSourceResult();
            Map<String, Long> fpRRAnalysis = transformationPackage.getMapAnalysisRRAnalysisIds().get(String.valueOf(sourceResult.getRlSourceResultId()));
            Optional<RLAnalysis> rlAnalysisOpt = rlAnalysisRepository.findById(sourceResult.getRlAnalysis().getRlAnalysisId());
            String analysisName = rlAnalysisOpt.map(RLAnalysis::getAnalysisName).orElse(null);
            Long analysisId = rlAnalysisOpt.map(RLAnalysis::getAnalysisId).orElse(null);

            RRAnalysis rrAnalysis = ofNullable(fpRRAnalysis)
                    .map(fpAn -> fpAn.get(bundle.getFinancialPerspective()))
                    .map(id -> rrAnalysisRepository.findById(id).get())
                    .orElse(null);

            if (financialPerspective == null) {
                log.error("Error creating PLTs: no UP financial perspective found for analysis {} , id {}", analysisName, analysisId);
                if (rrAnalysis != null) {
                    rrAnalysis.setImportStatus("ERROR");
                    rrAnalysisRepository.save(rrAnalysis);
                }
                continue;
            }
            List<TargetRAP> targetRaps = analysisIncludedTargetRAPRepository.findByModelAnalysisId(rrAnalysis.getRrAnalysisId())
                    .map(AnalysisIncludedTargetRAP::getTargetRAPId)
                    .map(targetRAPRepository::findById)
                    .map(Optional::get)
                    .collect(toList());

            if (targetRaps == null || targetRaps.isEmpty()) {
                log.info("Finish tracking at the end of STEP 12 : CONVERT_ELT_TO_PLT for analysis {}, status {}", bundle.getSourceRRLT(), "Error", "stop this tracking");

                if (rrAnalysis != null) {
                    rrAnalysis.setImportStatus("ERROR");
                    rrAnalysisRepository.save(rrAnalysis);
                }

                continue;
            }

            List<PLTHeader> pltHeaders = makePurePLTHeaders(bundle, targetRaps, modelingBasis);
            if (pltHeaders == null || pltHeaders.isEmpty()) {
//                log.error("RRLT {} has no PLTs, dataset {} error", bundle.getConformedELTHeader().getId(), bundle.getConformedELTHeader().getRepresentationDataset().getId());
                log.error("RRLT {} has no PLTs, error", bundle.getConformedRRLT());
                continue;
            }

            //PLT Truncator
            String region = rlAnalysisOpt.map(RLAnalysis::getRegion).orElse(null);
            String peril = rlAnalysisOpt.map(RLAnalysis::getPeril).orElse(null);
            String currency =rlAnalysisOpt.map(RLAnalysis::getAnalysisCurrency).orElse(null);
//            double threshold = truncator.getThresholdFor(region, peril, currency, "PLT");
            double threshold = 0.0;

            for (PLTHeader pltHeader : pltHeaders) {
                PLTBundle pltBundle = new PLTBundle();
                pltBundle.setHeader(pltHeader);
                bundle.addPLTBundle(pltBundle);
                pltHeader.setTruncationCurrency(bundle.getTruncationCurrency());
                pltHeader.setTruncationThreshold(threshold);
//                pltHeader.setTruncationThresholdEur(truncator.getThresholdInEur());
                //@TODO Review With Vier
                //pltHeader.setTruncationThresholdEur(threshold);
                String filename = makePLTFileName(
                        pltHeader.getCreatedDate(),
                        String.valueOf(pltHeader.getRegionPerilId()),
                        financialPerspective.getCode(),
                        pltHeader.getCurrencyid(),
                        XLTOT.TARGET,
                        pltHeader.getTargetRAPId(),
                        pltHeader.getPltSimulationPeriods(),
                        PLTPublishStatus.PURE,
                        0, // pure PLT, no thread number
                        pltHeader.getPltHeaderId(),
                        ".bin"
                        );
                File file = makeFullFile(prefix, filename);
                BinFile binFile= new BinFile(file);
                pltHeader.setPltLossDataFilePath(binFile.getPath());
                pltHeader.setPltLossDataFileName(binFile.getFileName());

                bundleForPLT.put(pltHeader.getPltHeaderId(), bundle);

                /** @TODO  Implement later ...
                pltConverterProgressRepository.save(new PLTConverterProgress(pltHeader.getId(),
                        pltHeader.getProject().getId(),
                        sourceResult.getRmsAnalysis().getRdmId(),
                        sourceResult.getRmsAnalysis().getRdmName(),
                        sourceResult.getRmsAnalysis().getAnalysisId(),
                        pltHeader.getPeqtFile().getFileName(),
                        pltHeader.getImportSequence()));
                 */
            }

            groupImportPltByPeqt(pltsByPeqt, pltHeaders);

            // TODO finish step 12 CONVERT_ELT_TO_PLT for one analysis in loop for of many analysis ???????????????? must ask Viet
            // Date endDate = new Date();
            // projectImportAssetLogA.setEndDate(endDate);
            // projectImportAssetLogRepository.save(projectImportAssetLogA);
            log.info("Finish import progress STEP 12 : CONVERT_ELT_TO_PLT for analysis: {}", bundle.getSourceRRLT());
        }
        // end loop for of bundles in function batchConvert()

        int nbLauncher = 0;
        for (Map.Entry<String, List<PLTHeader>> entry : pltsByPeqt.entrySet()) {
            nbLauncher += entry.getValue().size() / chunkSize + 1;
        }
        CountDownLatch launcherCountDown = new CountDownLatch(nbLauncher);
        for (Map.Entry<String, List<PLTHeader>> entry : pltsByPeqt.entrySet()) {
            log.debug("Running ELT2PLT convertor for PEQT file {}", entry.getKey());
            BinFile peqtFile = new BinFile(entry.getKey(), peqtPath, null);
            int nbTrunk = entry.getValue().size() / chunkSize + 1;
//            int nbTrunk = 1;
            for (int i = 0; i < nbTrunk; i++) {
                int start = i * chunkSize;
                int end = (i + 1) * chunkSize < entry.getValue().size() ? (i + 1) * chunkSize : entry.getValue().size();
                List<PLTHeader> scorPLTHeaders = entry.getValue().subList(start, end);
//                List<ScorPLTHeader> scorPLTHeaders = entry.getValue();
                Map<Long, Map<Long, ELTLossBetaConvertFunction>> convertFunctionMapForPLT = new HashMap<>(scorPLTHeaders.size());
                for (PLTHeader scorPLTHeader : scorPLTHeaders) {
                    TransformationBundle bundle = bundleForPLT.get(scorPLTHeader.getPltHeaderId());
                    if (bundle != null) {
                        AnalysisELTnBetaFunction analysisELTnBetaFunction = bundle.getAnalysisELTnBetaFunction();
                        log.debug("analysisELTnBetaFunction.getEltLosses().size() = {}", 0);
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
                        convertFunctionMapForPLT.put(scorPLTHeader.getPltHeaderId(), convertFunctionMap);
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
    }

    private List<PLTHeader> makePurePLTHeaders(TransformationBundle bundle, List<TargetRAP> targetRaps, PLTModelingBasis modelingBasis) {
        LossDataHeader lossDataHeader= bundle.getConformedRRLT();
        RRAnalysis rrAnalysis= bundle.getRrAnalysis();

        if (!RRLossTableType.CONFORMED.getCode().equals(lossDataHeader.getOriginalTarget())) {
            throw new IllegalStateException();
        }

        List<PLTHeader> pltHeaders = new ArrayList<>();
        for (TargetRAP targetRap : targetRaps) {
            PET pet = petRepository.findById(targetRap.getPetId()).get();
            RRFile file = new RRFile(pet);
            BinFile peqtFile = new BinFile(file.getFileName(), peqtPath, null);
            PLTHeader scorPLTHeader = new PLTHeader();
            scorPLTHeader.setPltSimulationPeriods(pet.getNumberSimulationPeriods());
            scorPLTHeader.setPltType(PLTType.Pure.getCode());
            scorPLTHeader.setProjectId(rrAnalysis.getProjectId());
            scorPLTHeader.setCurrencyid(lossDataHeader.getCurrency());
            scorPLTHeader.setTargetRAPId(targetRap.getTargetRAPId());
            scorPLTHeader.setRegionPerilId(bundle.getRegionPeril().getRegionPerilId());
            scorPLTHeader.setRrAnalysisId(rrAnalysis.getRrAnalysisId());
            scorPLTHeader.setCloningSourceId(null);
            scorPLTHeader.setDefaultPltName("Pure-" + targetRaps.indexOf(targetRap)); // FIXME: 16/07/2016
            scorPLTHeader.setCreatedDate(new Date());
            scorPLTHeader.setGeneratedFromDefaultAdjustment(false);
            scorPLTHeader.setRmsSimulationSet(pet.getRmsSimulationSetId());
            scorPLTHeader.setGeoCode(rrAnalysis.getGeoCode());
            scorPLTHeader.setGeoDescription(rrAnalysis.getGeoCode());
            scorPLTHeader.setPerilCode(rrAnalysis.getPeril());
            scorPLTHeader.setImportSequence(importSequence);
            scorPLTHeader.setSourceLossModelingBasis(modelingBasis.getCode());
            String sourceFinPersp = rrAnalysis.getFinancialPerspective();
            scorPLTHeader.setUdName(rrAnalysis.getRegionPeril() + "_" + sourceFinPersp + "_LMF1.T0");
            scorPLTHeader.setDefaultPltName(rrAnalysis.getRegionPeril() + "_" + sourceFinPersp + "_LMF1");

            pltHeaders.add(scorPLTHeader);
            log.info("PLT {} has targetRap {}: source code {}, target code {}", scorPLTHeader.getPltHeaderId(), targetRap.getTargetRAPId(), targetRap.getSourceRAPCode(), targetRap.getTargetRAPCode());
        }
        return pltHeaders;
    }

    private void groupImportPltByPeqt(Map<String, List<PLTHeader>> pltsByPeqt, List<PLTHeader> pltHeaders) {
        if (pltHeaders != null && !pltHeaders.isEmpty()) {
            for (PLTHeader pltHeader : pltHeaders) {
                PET pet = targetRAPRepository.findById(pltHeader.getTargetRAPId()).map(tr -> petRepository.findById(tr.getPetId()))
                        .map(Optional::get).orElse(null);
                if (pet != null) {
                    List<PLTHeader> scorPLTHeaders = pltsByPeqt.get(pet.getPeqtFileName());
                    if (scorPLTHeaders == null) {
                        scorPLTHeaders = new ArrayList<>();
                    }
                    scorPLTHeaders.add(pltHeader);
                    pltsByPeqt.put(pet.getPeqtFileName(), scorPLTHeaders);
                }
            }
        }
    }


    private PLTModelingBasis getModelingBasis() {
        if (contractId != null && uwYear != null) {
            String sourceTypeId = contractRepository.findByTreatyIdAndUwYear(contractId,uwYear)
                    .map(Contract::getContractSourceTypeId)
                    .orElse(null);
            return "5".equals(sourceTypeId) ? PLTModelingBasis.PM : PLTModelingBasis.AM;
        }
        return PLTModelingBasis.AM;
    }

    private Integer parseUwYear(String uwYear) {
        return Integer.parseInt(StringUtils.split(uwYear, '-')[0]);
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

    private class TreatyBatchLauncher implements Runnable {
        private final int id;
        private BlockingQueue<RRPeriod> queue;
        private CountDownLatch latch;
        private BinFile peqtFile;
        private List<PLTHeader> scorPLTHeaders;
        Map<Long, Map<Long, ELTLossBetaConvertFunction>> convertFunctionMapForPLT;
        private Boolean[] finished = { Boolean.FALSE };
        private int nbWorkers;

        public TreatyBatchLauncher(CountDownLatch latch, BinFile peqtFile, List<PLTHeader> scorPLTHeaders,
                                   Map<Long, Map<Long, ELTLossBetaConvertFunction>> convertFunctionMapForPLT) {
            this.id = hashCode();
            this.queue = new ArrayBlockingQueue<>(queueSize);
            this.latch = latch;
            this.peqtFile = peqtFile;
            this.scorPLTHeaders = scorPLTHeaders;
            this.convertFunctionMapForPLT = convertFunctionMapForPLT;
            nbWorkers = 1;
        }

        private final static int PERIOD_HEADER_SIZE=8;
        private final static int PERIOD_EVENT_SIZE=21;

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
            Timer timer = new Timer();
            Map<Long, FileOutputStream> outputStreamForPLT = new HashMap<>();
            for (PLTHeader pltHeader : scorPLTHeaders) {
                File file = new File(pltHeader.getPltLossDataFilePath(), pltHeader.getPltLossDataFileName());
                try {
                    FileOutputStream  outputStream = new FileOutputStream(file);
                    outputStreamForPLT.put(pltHeader.getPltHeaderId(), outputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                    continue;
                }
            }

            Date startConvert = new Date();
            /** @TODO  ...
            for (PLTHeader scorPLTHeader : scorPLTHeaders) {
                PLTConverterProgress pltConverterProgress = pltConverterProgressRepository.findByPltId(scorPLTHeader.getId());
                pltConverterProgress.setStartConvert(startConvert);
                pltConverterProgressRepository.save(pltConverterProgress);
            }
             */
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
                        RRPeriod p = new RRPeriod();
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

            for (Map.Entry<Long, FileOutputStream> entry : outputStreamForPLT.entrySet()) {
                IOUtils.closeQuietly(entry.getValue());
            }

            Date endConvert = new Date();
            for (PLTHeader scorPLTHeader : scorPLTHeaders) {
                /** @TODO Later ...
                PLTConverterProgress pltConverterProgress = pltConverterProgressRepository.findByPltId(scorPLTHeader.getId());
                pltConverterProgress.setEndConvert(endConvert);
                pltConverterProgressRepository.save(pltConverterProgress);
                */
                Map<Long, ELTLossBetaConvertFunction> convertFunctionMap = convertFunctionMapForPLT.get(scorPLTHeader.getPltHeaderId());
                if (convertFunctionMap != null) {
                    convertFunctionMap.clear();
                    convertFunctionMap = null;
                    convertFunctionMapForPLT.put(scorPLTHeader.getPltHeaderId(), null);
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
        private final BlockingQueue<RRPeriod> queue;
        private final CountDownLatch workerLatch;
        private List<PLTHeader> scorPLTHeaders;
        private Map<Long, Map<Long, ELTLossBetaConvertFunction>> convertFunctionMapForPLT;
        private Map<Long, FileOutputStream> outputStreamForPLT;
        private Timer timer;
        private Comparator<PLTLossData> cmp;
        private Map<Long, List<PLTLossData>> pltLossDataForPLT;


        public TreatyBatchWorker(int masterId, Boolean[] finished, BlockingQueue<RRPeriod> queue, CountDownLatch workerLatch, Timer timer, List<PLTHeader> scorPLTHeaders,
                                 Map<Long, Map<Long, ELTLossBetaConvertFunction>> convertFunctionMapForPLT, Map<Long, FileOutputStream> outputStreamForPLT) {
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
            for (PLTHeader scorPLTHeader : scorPLTHeaders) {
                pltLossDataForPLT.put(scorPLTHeader.getPltHeaderId(), new ArrayList<PLTLossData>());
            }
        }

        @Override
        public void run() {
            log.info("Slave {}.{}: polling data for processing", this.masterId, this.id);
            int periodCount = 0;
            do {
                try {
                    RRPeriod p = queue.poll(250, TimeUnit.MILLISECONDS);
                    if (p == null) {
                        if (!finished[0]) {
                            Thread.yield();
                            continue;
                        } else
                            break;
                    } else {
                        long time=System.nanoTime();
                        periodCount++;
                        for (PLTHeader scorPLTHeader : scorPLTHeaders) {
                            Map<Long, ELTLossBetaConvertFunction> convertFunctionMap = convertFunctionMapForPLT.get(scorPLTHeader.getPltHeaderId());
                            List<PLTLossData> pltLossDataList = pltLossDataForPLT.get(scorPLTHeader.getPltHeaderId());
                            if (convertFunctionMap != null && pltLossDataList != null) {
                                process(p, convertFunctionMap, scorPLTHeader.getTruncationThreshold(), pltLossDataList);
                            }
                            if (periodCount % bufferSize == 0) {
                                FileOutputStream outputStream = outputStreamForPLT.get(scorPLTHeader.getPltHeaderId());
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
            for (PLTHeader scorPLTHeader : scorPLTHeaders) {
                FileOutputStream outputStream = outputStreamForPLT.get(scorPLTHeader.getPltHeaderId());
                List<PLTLossData> pltLossDataList = pltLossDataForPLT.get(scorPLTHeader.getPltHeaderId());
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

        private boolean process(RRPeriod p, Map<Long, ELTLossBetaConvertFunction> convertFunctionMap, double threshold, List<PLTLossData> pltLossDataList) throws IOException {
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
}
