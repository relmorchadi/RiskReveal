package com.scor.rr.service.fileBasedImport.batch;

import com.scor.rr.domain.*;
import com.scor.rr.domain.dto.ImportFilePLTData;
import com.scor.rr.domain.enums.RRLossTableType;
import com.scor.rr.domain.enums.StatisticMetric;
import com.scor.rr.domain.enums.TrackingStatus;
import com.scor.rr.domain.importfile.*;
import com.scor.rr.domain.riskLink.RLImportSelection;
import com.scor.rr.repository.*;
import com.scor.rr.service.RmsService;
import com.scor.rr.service.fileBasedImport.ImportFileService;
import com.scor.rr.util.PathUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@StepScope
public class LoadLossDataFileService {
    private static final Logger log = LoggerFactory.getLogger(LoadLossDataFileService.class);

    @Autowired
    private ProjectEntityRepository projectEntityRepository;

    @Autowired
    private FileBasedImportConfigRepository fileBasedImportConfigRepository;

    @Autowired
    private ProjectImportRunRepository projectImportRunRepository;

    @Autowired
    private FileImportSourceResultRepository fileImportSourceResultRepository;

    @Autowired
    private RLImportSelectionRepository rlSourceResultRepository;

    @Autowired
    private RegionPerilRepository regionPerilRepository;

    @Autowired
    private LossDataHeaderEntityRepository lossDataHeaderEntityRepository;

    @Autowired
    private SummaryStatisticHeaderRepository summaryStatisticHeaderRepository;

    @Autowired
    private SummaryStatisticsDetailRepository summaryStatisticsDetailRepository;

    @Autowired
    private RLModelDataSourceRepository rlModelDataSourceRepository;

    @Autowired
    private ModellingSystemInstanceRepository modellingSystemInstanceRepository;

    @Autowired
    private ModelAnalysisEntityRepository rrAnalysisRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private TransformationPackageNonRMS transformationPackage;

    @Autowired
    private TargetRapRepository targetRAPRepository;

    @Autowired
    private AnalysisIncludedTargetRAPRepository analysisIncludedTargetRAPRepository;

    @Autowired
    private RLPortfolioSelectionRepository rlPortfolioSelectionRepository;

    @Autowired
    private ModelPortfolioRepository modelPortfolioRepository;

    @Autowired
    private RmsService rmsService;

    @Autowired
    private ImportFileService importFileService;

    @Autowired
    private RLAnalysisToTargetRAPRepository rlAnalysisToTargetRAPRepository;


    @Value("#{jobParameters['projectId']}")
    private Long projectId;

    @Value("#{jobParameters['fileBasedImportConfigId']}")
    private Long fileBasedImportConfigId;

    @Value("#{jobParameters['fileImportSourceResultIds']}")
    private String fileImportSourceResultIds;

//    @Value("#{jobParameters['rlPortfolioSelectionIds']}")
//    private String rlPortfolioSelectionIds;

    @Value("#{jobParameters['instanceId']}")
    private String instanceId;

    @Value("#{jobParameters['marketChannel']}")
    private String marketChannel;

    // TODO
    public RepeatStatus loadLossDataFile() throws Exception {
        log.debug("Starting loadLossDataFile");
        Date startDate = new Date();
        Optional<ProjectEntity> projectOptional = projectEntityRepository.findById(projectId);
        ProjectEntity project;

        if (projectOptional.isPresent()) {
            project = projectOptional.get();
        } else {
            log.debug("project not found");
            throw new IllegalArgumentException("invalid project id");
        }

        FileBasedImportConfig fileBasedImportConfig;
        Optional<FileBasedImportConfig> fileBasedImportConfigOptional = fileBasedImportConfigRepository.findById(fileBasedImportConfigId);
        if (fileBasedImportConfigOptional.isPresent()) {
            fileBasedImportConfig = fileBasedImportConfigOptional.get();
        } else {
            log.debug("fileBasedImportConfig not found");
            throw new IllegalArgumentException("invalid fileBasedImportConfig id");
        }

        // todo : not exist any more ?
//        ProjectImportSourceConfig projectImportSourceConfig = projectImportSourceConfigRepository.findByProjectIdAndSourceConfigVendor(getProjectId(), "Non RMS");
//        if (projectImportSourceConfig == null) {
//            return false;
//        }

        // build ProjectImportRun ----------------------------------------------------------------------------------
        ProjectImportRunEntity projectImportRun = new ProjectImportRunEntity();
//        mongoDBSequence.nextSequenceId(projectImportRun);
        projectImportRun.setProjectId(projectId);
        List<ProjectImportRunEntity> projectImportRunList = projectImportRunRepository.findByProjectId(projectImportRun.getProjectId());
        projectImportRun.setRunId(projectImportRunList == null ? 1 : projectImportRunList.size() + 1);
//        projectImportRun.setProjectImportSourceConfigId(projectImportSourceConfig.getId());
//        projectImportRun.setStatus(ProjectImportLog.TrackingStatus.InProgress.toString());
        projectImportRun.setStartDate(startDate);
        projectImportRun.setImportedBy(project.getAssignedTo());
        projectImportRun.setSourceConfigVendor("Non RMS");
        projectImportRunRepository.save(projectImportRun);

        fileBasedImportConfig.setLastProjectImportRunId(projectImportRun.getProjectImportRunId()); // each time replace old one
        fileBasedImportConfigRepository.save(fileBasedImportConfig);

//        RRRepresentationDataset rrRepresentationDataset = rrRepresentationDatasetRepository.findByProjectId(getProjectId());
//        if (rrRepresentationDataset == null) {
//            rrRepresentationDataset = new RRRepresentationDataset();
//            mongoDBSequence.nextSequenceId(rrRepresentationDataset);
//            rrRepresentationDataset.setProjectId(getProjectId());
//            rrRepresentationDataset.setName("Unspecified Representation");
//            rrRepresentationDataset.setIncrementName(1);
//            rrRepresentationDatasetRepository.save(rrRepresentationDataset);
//        }

        // tracking project
//        ProjectImportLog projectImportLogPR = new ProjectImportLog();
//        mongoDBSequence.nextSequenceId(projectImportLogPR);
//        projectImportLogPR.setProjectId(getProjectId());
//        projectImportLogPR.setProjectImportRunId(projectImportRun.getId());
//        projectImportLogPR.setAssetType(AssetType.PROJECT);
//        projectImportLogPR.setAssetId(projectImportRun.getProjectId());
//        projectImportLogPR.setStartDate(new Date());
//        projectImportLogPR.setImportedBy(projectImportRun.getImportedBy());
//        log.info("Start tracking at STEP 1 : LOAD_LOSS_DATA_FILE for project: {}, status: {}", getProjectId(), projectImportLogPR.getStatus());
        // endDate, status later

        //--------------------------------------------------------------------------------------------------------------
        // begin loop for getSourceResultIdsInput

        if (fileImportSourceResultIds != null) {
            String[] sourceResultIds = fileImportSourceResultIds.split(";");
            for (String sourceResultId : sourceResultIds) {
                Optional<FileImportSourceResult> sourceResultOptional = fileImportSourceResultRepository.findById(Integer.valueOf(sourceResultId));

                if (sourceResultOptional == null) {
                    log.error("FileImportSourceResult not found for id {}", sourceResultId);
                    continue;
                }
                FileImportSourceResult sourceResult = sourceResultOptional.get();

                ModelAnalysisEntity modelAnalysisEntity = new ModelAnalysisEntity();
                modelAnalysisEntity.setProjectId(projectId);

                // todo set target rap
//                TargetRapEntity targetRap = targetRAPRepository.findByTargetRapCode(sourceResult.getTargetRAPCode());
//                if (targetRap != null && targetRap.getTargetRAPId() != null) {
//                    modelAnalysisEntity.getIncludedTargetRapIds().add(targetRap.getTargetRapId().toString());
//                }

                modelAnalysisEntity.setCreationDate(startDate);
                modelAnalysisEntity.setImportStatus(TrackingStatus.INPROGRESS.toString());
                sourceResult.setImportStatus(TrackingStatus.INPROGRESS.toString());
                fileImportSourceResultRepository.save(sourceResult);
                modelAnalysisEntity.setProjectImportRunId(projectImportRun.getProjectImportRunId());
                modelAnalysisEntity.setFinancialPerspective(sourceResult.getFinancialPerspective());
                modelAnalysisEntity.setRegionPeril(sourceResult.getSelectedRegionPerilCode());
                modelAnalysisEntity.setSourceCurrency(sourceResult.getSourceCurrency());
                modelAnalysisEntity.setTargetCurrencyBasis(sourceResult.getTargetCurrencyBasis());
                modelAnalysisEntity.setTargetCurrency(sourceResult.getTargetCurrency());
//                double exchangeRate = 1.0d;
//                if (sourceResult != null) {
//                    exchangeRate = sourceResult.getExchangeRate().doubleValue();
//                } else {
//                    if (! sourceResult.getCurrency().equals(sourceResult.getTargetCurrency())) {
//                        ExchangeRateEntity exSource = exchangeRateRepository.findFirstByTypeAndDomesticCurrencyIdAndEffectiveDateBeforeOrderByEffectiveDateDesc(
//                                "YEARLY", sourceResult.getCurrency(), rrAnalysis.getCreationDate());
//                        ExchangeRateEntity exTarget = exchangeRateRepository.findFirstByTypeAndDomesticCurrencyIdAndEffectiveDateBeforeOrderByEffectiveDateDesc(
//                                "YEARLY", sourceResult.getTargetCurrency(), rrAnalysis.getCreationDate());
//                        if (exSource != null && exTarget != null && exSource.getRates().get("EUR") != null && exTarget.getRates().get("EUR") != null) {
//                            exchangeRate = exSource.getRates().get("EUR") / exTarget.getRates().get("EUR");
//                        }
//                    }
//                    log.debug("source ELT currency {} conformed ELT currency {} exchange rate {}",sourceResult.getCurrency(), sourceResult.getTargetCurrency(), exchangeRate) ;
//                }

//                modelAnalysisEntity.setExchangeRate(exchangeRate);
                modelAnalysisEntity.setProportion(sourceResult.getProportion() != null ? sourceResult.getProportion().doubleValue() : 100.0);
                modelAnalysisEntity.setUnitMultiplier(sourceResult.getUnitMultiplier() != null ? sourceResult.getUnitMultiplier().doubleValue() : 1.0);
                modelAnalysisEntity.setResultName(sourceResult.getResultName());
                modelAnalysisEntity.setDataSourceName(sourceResult.getDataSource());
                modelAnalysisEntity.setFileName(sourceResult.getFileName());
                modelAnalysisEntity.setGrain(sourceResult.getGrain());

                rrAnalysisRepository.save(modelAnalysisEntity);

                // TODO tracking file need ???
//                ProjectImportLog projectImportLogA = new ProjectImportLog();
//                mongoDBSequence.nextSequenceId(projectImportLogA);
//                projectImportLogA.setProjectId(getProjectId());
//                projectImportLogA.setProjectImportRunId(projectImportRun.getId());
//                projectImportLogA.setAssetType(AssetType.ANALYSIS);
//                projectImportLogA.setAssetId(rrAnalysis.getId()); // tracking rrAnalysis
//                projectImportLogA.setStartDate(startDate);
//                projectImportLogA.setImportedBy(projectImportRun.getImportedBy());
                // endDate, status do more later

//                ProjectImportAssetLog projectImportAssetLogA = new ProjectImportAssetLog();
//                mongoDBSequence.nextSequenceId(projectImportAssetLogA);
//                projectImportAssetLogA.setProjectId(getProjectId());
//                projectImportAssetLogA.setProjectImportLogId(projectImportLogA.getId());
//                projectImportAssetLogA.setStepId(1);
//                projectImportAssetLogA.setStepName(StepNonRMS.getStepNameFromStepIdForAnalysis(projectImportAssetLogA.getStepId()).toString());
//                projectImportAssetLogA.setStartDate(startDate);
//                projectImportAssetLogRepository.save(projectImportAssetLogA);

                // todo no SourceFileImport anymore : redesign
//                String fileId = sourceResult.getSourceFileImportId();
//                SourceFileImport sourceFile = sourceFileImportRepository.findOne(fileId);
//                File file = new File(importFileService.getRootFilePath() + sourceResult.getFilePath(), sourceResult.getFileName());

//                if (file == null) {
//                    log.error("File corresponding to source result not found for sourceResult id {}", sourceResult.getFileImportSourceResultId());
//                    projectImportAssetLogA.getErrorMessages().add("SourceFileImport not found for id " + fileId);
//                    Date endDate = new Date();
//                    projectImportAssetLogA.setEndDate(endDate);
//                    projectImportAssetLogRepository.save(projectImportAssetLogA);
//                    log.info("Finish import progress STEP 1 : LOAD_LOSS_DATA_FILE for analysis: {}", sourceResultId);
//                    projectImportLogA.setEndDate(endDate);
//                    projectImportLogA.setStatus(ProjectImportLog.TrackingStatus.Error.toString());
//                    projectImportLogRepository.save(projectImportLogA);
//                    log.info("Finish tracking at the end of STEP 1 : LOAD_LOSS_DATA_FILE for analysis {}, status {}", sourceResultId, "Error", "stop this tracking");
//                    modelAnalysisEntity.setImportStatus(TrackingStatus.ERROR.toString());
//                    sourceResult.setImportStatus(modelAnalysisEntity.getImportStatus());
//                    fileImportSourceResultRepository.save(sourceResult);
//                    rrAnalysisRepository.save(modelAnalysisEntity);
//                    continue;
//                }

                File sourceFile = new File(importFileService.getRootFilePath() + sourceResult.getFilePath(), sourceResult.getFileName());
                if (sourceFile == null || !sourceFile.exists()) {
//                    log.error("File not found: {}", importFileService.getRootFilePath() + sourceFile.getFilePath() + sourceFile.getFileName());
//                    projectImportAssetLogA.getErrorMessages().add("File not found: " + importFileService.getRootFilePath() + sourceFile.getFilePath() + sourceFile.getFileName());
//                    Date endDate = new Date();
//                    projectImportAssetLogA.setEndDate(endDate);
//                    projectImportAssetLogRepository.save(projectImportAssetLogA);
//                    log.info("Finish import progress STEP 1 : LOAD_LOSS_DATA_FILE for analysis: {}", sourceResultId);
//                    projectImportLogA.setEndDate(endDate);
//                    projectImportLogA.setStatus(ProjectImportLog.TrackingStatus.Error.toString());
//                    projectImportLogRepository.save(projectImportLogA);
                    log.info("Finish tracking at the end of STEP 1 : LOAD_LOSS_DATA_FILE for analysis {}, status {}", sourceResultId, "Error", "stop this tracking");
                    modelAnalysisEntity.setImportStatus(TrackingStatus.ERROR.toString());
                    sourceResult.setImportStatus(modelAnalysisEntity.getImportStatus());
                    fileImportSourceResultRepository.save(sourceResult);
                    rrAnalysisRepository.save(modelAnalysisEntity);
                    continue;
                }


//                if (sourceFile.lastModified() > sourceResult.getImportFileHeader().getLastScanDate().getTime()) {
//                    log.warn("File {} has been modified after scanning. Need to validate before import", sourceFile.getName());
//                    importFileService.validate(sourceFile);
//                    if (! sourceFile.getErrorMessages().isEmpty()) {
//                        log.error("Error: {}", sourceFile.getErrorMessages());
////                        projectImportAssetLogA.getErrorMessages().add("Error: " + sourceFile.getErrorMessages());
////                        Date endDate = new Date();
////                        projectImportAssetLogA.setEndDate(endDate);
////                        projectImportAssetLogRepository.save(projectImportAssetLogA);
//                        log.info("Finish import progress STEP 1 : LOAD_LOSS_DATA_FILE for analysis: {}", sourceResultId);
////                        projectImportLogA.setEndDate(endDate);
////                        projectImportLogA.setStatus(ProjectImportLog.TrackingStatus.Error.toString());
////                        projectImportLogRepository.save(projectImportLogA);
//                        log.info("Finish tracking at the end of STEP 1 : LOAD_LOSS_DATA_FILE for analysis {}, status {}", sourceResultId, "Error", "stop this tracking");
//                        modelAnalysisEntity.setImportStatus(TrackingStatus.ERROR.toString());
//                        sourceResult.setImportStatus(modelAnalysisEntity.getImportStatus());
//                        fileImportSourceResultRepository.save(sourceResult);
//                        rrAnalysisRepository.save(modelAnalysisEntity);
//                        continue;
//                    }
//                }

                // todo not save ImportFileLossDataHeader, because not save SourceFileImport
                ImportFileLossDataHeader importFileLossDataHeader = importFileService.parseLossDataTableHeader(sourceFile);
                modelAnalysisEntity.setSourceModellingVendor(importFileLossDataHeader.getMetadata().get("ModelProvider"));
                modelAnalysisEntity.setSourceModellingSystem(importFileLossDataHeader.getMetadata().get("ModelSystem"));
                modelAnalysisEntity.setSourceModellingSystemInstance(importFileLossDataHeader.getMetadata().get("ModelSystemInstance"));
                modelAnalysisEntity.setSourceLossModellingBasis(importFileLossDataHeader.getMetadata().get("LossTableBasis"));
                modelAnalysisEntity.setSourceLossTableType(importFileLossDataHeader.getMetadata().get("LossTableType"));
                modelAnalysisEntity.setEventSet(importFileLossDataHeader.getMetadata().get("EventSet"));
                modelAnalysisEntity.setModelModule(importFileLossDataHeader.getMetadata().get("ModelModule"));
                modelAnalysisEntity.setModel(importFileLossDataHeader.getMetadata().get("ModelModule"));
                modelAnalysisEntity.setSourceResultsReference(importFileLossDataHeader.getMetadata().get("SourceResultsReference"));
                modelAnalysisEntity.setSourceModellingSystemVersion(importFileLossDataHeader.getMetadata().get("ModelSystemVersion"));
//                 todo after, comment now
//                modelAnalysisEntity.setMetadata(importFileLossDataHeader.getMetadata());

                rrAnalysisRepository.save(modelAnalysisEntity);

                long pic = System.currentTimeMillis();
                List<ImportFilePLTData> datas = importFileService.parsePLTLossDataFile(sourceFile);
                if (datas != null) {
                    log.debug("File {}: read {} data lines took {} ms", sourceFile.getName(), datas.size(), System.currentTimeMillis() - pic);
                } else {
                    log.debug("File {}: parsing data section return null -- something wrong", sourceFile.getName());
                }

                // write text file to ihub
                File fileIhub = makeFullFile(getPrefixDirectory(), sourceFile.getName());
                copyFileUsingChannel(sourceFile, fileIhub);

                // create RRLossTable
                createLossTableForOriginalFile(modelAnalysisEntity, fileIhub, importFileLossDataHeader);
//
//                // create bundles here
                TransformationBundleNonRMS bundle = new TransformationBundleNonRMS();
                bundle.setHeader(importFileLossDataHeader);
                bundle.setFile(sourceFile);
                bundle.setDatas(datas);
                bundle.setRrAnalysis(modelAnalysisEntity);
                bundle.setSourceResult(sourceResult);
//                bundle.setProjectImportLogAnalysisId(projectImportLogA.getId());

                transformationPackage.addTransformationBundle(bundle);

                // finish step 1 LOAD_LOSS_DATA_FILE for one analysis in loop for of many analysis
//                Date endDate = new Date();
//                projectImportAssetLogA.setEndDate(endDate);
//                projectImportAssetLogRepository.save(projectImportAssetLogA);
//                log.info("Finish import progress STEP 1 : LOAD_LOSS_DATA_FILE for file: {}", file.getName());

                // if can come here means InProgress
//                projectImportLogA.setStatus(ProjectImportLog.TrackingStatus.InProgress.toString());
//                projectImportLogRepository.save(projectImportLogA);
                log.info("Tracking at the end of STEP 1 : LOAD_LOSS_DATA_FILE for file {}, status {}", sourceFile.getName(), "continue this tracking");

                // if file not still exists : not create RRAnalysis
            }
            // end loop for sourceResults ------------------------------------------------------------------------------------------
        }  else {
            log.warn("loadLossDataFile, no source result ids were received");
        }

//        transformationPackage.setRrRepresentationDatasetId(rrRepresentationDataset.getId());
//        transformationPackage.setProjectImportLogPRId(projectImportLogPR.getId());

//        projectImportLogPR.setStatus(ProjectImportLog.TrackingStatus.InProgress.toString()); // tracking project must come to the last step
//        projectImportLogRepository.save(projectImportLogPR);
//        log.info("Tracking at the end of STEP 1 : LOAD_LOSS_DATA_FILE for project: {}, status: {}", getProjectId(), "InProgress", "continue this tracking");
        log.debug("LOAD_LOSS_DATA_FILE  completed");
        return RepeatStatus.FINISHED;
    }

    @Value("#{jobParameters['clientName']}")
    private String clientName;

    @Value("#{jobParameters['clientId']}")
    private Long clientId;

    @Value("#{jobParameters['contractId']}")
    private Long contractId;

    @Value("#{jobParameters['uwYear']}")
    private Integer uwYear;

    public String getPrefixDirectory() {
        return PathUtils.getPrefixDirectory(clientName, clientId, contractId.toString(), uwYear, projectId);
    }

    private static void copyFileUsingChannel(File source, File dest) throws IOException {
        FileChannel sourceChannel = null;
        FileChannel destChannel = null;
        try {
            sourceChannel = new FileInputStream(source).getChannel();
            destChannel = new FileOutputStream(dest).getChannel();
            destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
        } finally {
            sourceChannel.close();
            destChannel.close();
        }
    }

    private RegionPerilEntity getRegionPeril(RLImportSelection sourceResult) {
        String rpCode = sourceResult.getTargetRegionPeril();
        if (rpCode == null || rpCode.isEmpty()) {
            rpCode = sourceResult.getRlAnalysis().getSystemRegionPeril();
        }
        if (rpCode == null || rpCode.isEmpty()) {
            rpCode = sourceResult.getRlAnalysis().getRpCode();
        }
        if (rpCode == null) {
            log.debug("rpCode is null");
            return null;
        }
        return regionPerilRepository.findByRegionPerilCode(rpCode);
    }

    public File makeFullFile(String prefixDirectory, String filename) {
        final Path fullPath = getIhubPath().resolve(prefixDirectory);
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

    private String filePath;

    public Path getIhubPath() {
        return Paths.get(filePath);
    }

    private void createLossTableForOriginalFile(ModelAnalysisEntity rrAnalysis, File fileIhub, ImportFileLossDataHeader importFileLossDataHeader) {
        // LossDataHeader
        LossDataHeaderEntity rrLossTable = new LossDataHeaderEntity();
//        mongoDBSequence.nextSequenceId(rrLossTable);

        RRFinancialPerspective rrFinancialPerspective = null;
        if (rrAnalysis.getFinancialPerspective() != null) {
            rrFinancialPerspective = new RRFinancialPerspective(rrAnalysis.getSourceModellingVendor(),
                    rrAnalysis.getSourceModellingSystem(),
                    rrAnalysis.getSourceModellingSystemVersion() != null ? rrAnalysis.getSourceModellingSystemVersion().toString() : null,
                    rrAnalysis.getFinancialPerspective());
        }

        // create SummaryStatisticHeader
        SummaryStatisticHeaderEntity summaryStatisticHeader = new SummaryStatisticHeaderEntity();
        summaryStatisticHeader.setLossDataId(rrLossTable.getLossDataHeaderId());
        summaryStatisticHeader.setLossDataType("PLT");

        // createStatisticDataFromMetadata
        Map<String, String> metadata = importFileLossDataHeader.getMetadata();
        Double aal = metadata.get("AAL") != null ? Double.valueOf(metadata.get("AAL")) : null;
        Double std = metadata.get("STD") != null ? Double.valueOf(metadata.get("STD")) : null;
        Double cov = metadata.get("COV") != null ? Double.valueOf(metadata.get("COV")) : null;
        summaryStatisticHeader.setPurePremium(aal);
        summaryStatisticHeader.setStandardDeviation(std);
        summaryStatisticHeader.setCov(cov);
        summaryStatisticHeaderRepository.save(summaryStatisticHeader);

        // from SummaryStatisticHeader create 4 SummaryStatisticsDetail
        SummaryStatisticsDetail oep = new SummaryStatisticsDetail();
////        mongoDBSequence.nextSequenceId(oep);
//        oep.setProjectId(rrAnalysis.getProjectId());
//        oep.setLossDataType(RRStatisticHeader.STAT_DATA_TYPE_PLT);
//        oep.setLossTableId(rrLossTable.getId());
//        oep.setFinancialPerspective(rrFinancialPerspective);
//        oep.setStatisticData(createStatisticDataFromMetadata(importFileLossDataHeader, StatisticMetric.OEP));
        oep.setSummaryStatisticHeaderId(summaryStatisticHeader.getSummaryStatisticHeaderId());
        summaryStatisticsDetailRepository.save(oep);

        SummaryStatisticsDetail aep = new SummaryStatisticsDetail();
//        mongoDBSequence.nextSequenceId(aep);
//        aep.setProjectId(rrAnalysis.getProjectId());
//        aep.setLossDataType(RRStatisticHeader.STAT_DATA_TYPE_PLT);
//        aep.setLossTableId(rrLossTable.getId());
//        aep.setFinancialPerspective(rrFinancialPerspective);
//        aep.setStatisticData(createStatisticDataFromMetadata(importFileLossDataHeader, StatisticMetric.AEP));
        oep.setSummaryStatisticHeaderId(summaryStatisticHeader.getSummaryStatisticHeaderId());
        summaryStatisticsDetailRepository.save(aep);

//        statFile.setStatisticHeaders(Arrays.asList(oep.getId(), aep.getId()));

//        rrLossTable.setProjectId(rrAnalysis.getProjectId());
        rrLossTable.setModelAnalysisId(rrAnalysis.getRrAnalysisId());
        rrLossTable.setLossTableType("PLT");
        rrLossTable.setFileDataFormat("MAT_R");
        rrLossTable.setOriginalTarget(RRLossTableType.SOURCE.toString());
//        rrLossTable.setFinancialPerspective(statFile.getFinancialPerspective());
        rrLossTable.setCurrency(rrAnalysis.getSourceCurrency());
//        rrLossTable.setExchangeRate(1.0);
//        rrLossTable.setProportion(rrAnalysis.getProportion());
//        rrLossTable.setUnitsMultiplier(rrAnalysis.getUnitMultiplier());
//        rrLossTable.setRegionPeril(rrAnalysis.getRegionPeril());
//
        rrLossTable.setLossDataFileName(fileIhub.getName());
        rrLossTable.setLossDataFilePath(fileIhub.getParent()); // todo ok ?

//        rrLossTable.setStatFiles(Arrays.asList(statFile));

        lossDataHeaderEntityRepository.save(rrLossTable);

        createConformedRrLossTable(summaryStatisticHeader, rrLossTable, rrAnalysis);
    }

    private void createConformedRrLossTable(SummaryStatisticHeaderEntity originalSummaryStatisticHeader, LossDataHeaderEntity origRrLossTable, ModelAnalysisEntity rrAnalysis) {
        LossDataHeaderEntity conformedRRrLossTable = new LossDataHeaderEntity();
//        mongoDBSequence.nextSequenceId(conformedRRrLossTable);

//        conformedRRrLossTable.setProjectId(origRrLossTable.getProjectId());
        conformedRRrLossTable.setModelAnalysisId(origRrLossTable.getModelAnalysisId());
        conformedRRrLossTable.setLossTableType("PLT");
//        FILE_DATA_FORMAT_FACULTATIVE("Facultative"), FILE_DATA_FORMAT_TREATY("TreatyView"), FILE_DATA_FORMAT_MAT_R("Mat-R");
        conformedRRrLossTable.setFileDataFormat("TreatyView");
        conformedRRrLossTable.setOriginalTarget(RRLossTableType.CONFORMED.toString());
//        conformedRRrLossTable.setFinancialPerspective(origRrLossTable.getFinancialPerspective());
//        conformedRRrLossTable.setProportion(origRrLossTable.getProportion());
//        conformedRRrLossTable.setUnitsMultiplier(origRrLossTable.getUnitsMultiplier());
//        conformedRRrLossTable.setRegionPeril(origRrLossTable.getRegionPeril());
        conformedRRrLossTable.setCurrency(rrAnalysis.getTargetCurrency());
//        conformedRRrLossTable.setExchangeRate(rrAnalysis.getExchangeRate());
//        conformedRRrLossTable.setLossDataFile(origRrLossTable.getLossDataFile());

        // conform LossDataHeaderEntity
        SummaryStatisticHeaderEntity conformedSummaryStatisticHeader = new SummaryStatisticHeaderEntity();
        // ELT has 4 FP --> list, PLT has 1 FP --> 1
//        SummaryStatisticHeaderEntity originalSummaryStatisticHeader = summaryStatisticHeaderRepository.findByLossDataIdAndLossDataType(origRrLossTable.getLossDataHeaderId(), "PLT").get(0);
//        double factor = conformedRRrLossTable.getExchangeRate() * conformedRRrLossTable.getUnitsMultiplier() * (conformedRRrLossTable.getProportion() / 100.0);
        double factor = 1.0;
        Double aal = originalSummaryStatisticHeader.getPurePremium() != null ?
                originalSummaryStatisticHeader.getPurePremium() * factor : null;
        Double std = originalSummaryStatisticHeader.getStandardDeviation() != null ?
                originalSummaryStatisticHeader.getStandardDeviation() * factor : null;
        Double cov = originalSummaryStatisticHeader.getCov() != null ?
                originalSummaryStatisticHeader.getCov() * factor : null;
        conformedSummaryStatisticHeader.setPurePremium(aal);
        conformedSummaryStatisticHeader.setStandardDeviation(std);
        conformedSummaryStatisticHeader.setCov(cov);
        summaryStatisticHeaderRepository.save(conformedSummaryStatisticHeader);

        // todo conform SummaryStatisticsDetail
        // todo calculate EPCurveHeader

//        List<StatFile> conformedStatFiles = new ArrayList<>();
//        for (StatFile origStat : origRrLossTable.getStatFiles()) {
//            StatFile statFile = new StatFile();
//            statFile.setFinancialPerspective(origStat.getFinancialPerspective());
//            List<String> conformedStatisticHeaders = new ArrayList<>();
//            for (String id : origStat.getStatisticHeaders()) {
//                RRStatisticHeader origRrStatisticHeader = rrStatisticHeaderRepository.findOne(id);
//                if (origRrStatisticHeader != null) {
//                    RRStatisticHeader conformedRrStatisticHeader = new RRStatisticHeader();
//                    mongoDBSeque
//                      nce.nextSequenceId(conformedRrStatisticHeader);
//                    conformedRrStatisticHeader.setProjectId(rrAnalysis.getProjectId());
//                    conformedRrStatisticHeader.setLossDataType(RRStatisticHeader.STAT_DATA_TYPE_PLT);
//                    conformedRrStatisticHeader.setLossTableId(conformedRRrLossTable.getId());
//                    conformedRrStatisticHeader.setFinancialPerspective(origRrStatisticHeader.getFinancialPerspective());
//                    double factor = conformedRRrLossTable.getExchangeRate() * conformedRRrLossTable.getUnitsMultiplier() * (conformedRRrLossTable.getProportion() / 100.0);
//                    StatisticData conformedStatisticData = new StatisticData();
//                    conformedStatisticData.setStatisticMetric(origRrStatisticHeader.getStatisticData().getStatisticMetric());
//                    Double aal = origRrStatisticHeader.getStatisticData().getSummaryStatistic().getPurePremium() != null ?
//                            origRrStatisticHeader.getStatisticData().getSummaryStatistic().getPurePremium() * factor : null;
//                    Double std = origRrStatisticHeader.getStatisticData().getSummaryStatistic().getStandardDeviation() != null ?
//                            origRrStatisticHeader.getStatisticData().getSummaryStatistic().getStandardDeviation() * factor : null;
//                    Double cov = origRrStatisticHeader.getStatisticData().getSummaryStatistic().getCov() != null ?
//                            origRrStatisticHeader.getStatisticData().getSummaryStatistic().getCov() * factor : null;
//                    conformedStatisticData.setSummaryStatistic(new RRSummaryStatistic(aal, std, cov));
//                    List<RREPCurve> epCurves = new ArrayList<>();
//                    for (RREPCurve epCurve : origRrStatisticHeader.getStatisticData().getEpCurves()) {
//                        epCurves.add(new RREPCurve(epCurve.getReturnPeriod(), epCurve.getExceedanceProbability(), epCurve.getLossAmount()*factor));
//                    }
//                    conformedStatisticData.setEpCurves(epCurves);
//                    conformedRrStatisticHeader.setStatisticData(conformedStatisticData);
//                    rrStatisticHeaderRepository.save(conformedRrStatisticHeader);
//                    conformedStatisticHeaders.add(conformedRrStatisticHeader.getId());
//                }
//            }
////            statFile.setStatisticHeaders(conformedStatisticHeaders);
////            conformedStatFiles.add(statFile);
//        }

//        conformedRRrLossTable.setStatFiles(conformedStatFiles);
        lossDataHeaderEntityRepository.save(conformedRRrLossTable);
    }

    // todo need if must calculate EPCurve
//    private StatisticData createStatisticDataFromMetadata(ImportFileLossDataHeader importFileLossDataHeader, StatisticMetric metric) {
//        Map<String, String> metadata = importFileLossDataHeader.getMetadata();
//        StatisticData statisticData = new StatisticData();
//        statisticData.setStatisticMetric(metric);
//        Double aal = metadata.get("AAL") != null ? Double.valueOf(metadata.get("AAL")) : null;
//        Double std = metadata.get("STD") != null ? Double.valueOf(metadata.get("STD")) : null;
//        Double cov = metadata.get("COV") != null ? Double.valueOf(metadata.get("COV")) : null;
//        statisticData.setSummaryStatistic(new RRSummaryStatistic(aal, std, cov));
//        List<RREPCurve> epCurves = new ArrayList<>();
//        for (Map.Entry<String, String> entry : metadata.entrySet()) {
//            if (entry.getKey().contains(metric.getValue()) && !entry.getKey().contains("[")) {
//                int rp = Integer.valueOf(entry.getKey().replace(metric.getValue(), ""));
//                double ep = 1.0 / rp;
//                epCurves.add(new RREPCurve(rp, ep, Double.valueOf(entry.getValue())));
//            }
//        }
//        statisticData.setEpCurves(epCurves);
//        return statisticData;
//    }
}
