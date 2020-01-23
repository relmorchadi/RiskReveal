package com.scor.rr.service.fileBasedImport.batch;

import com.scor.rr.domain.*;
import com.scor.rr.domain.enums.RRLossTableType;
import com.scor.rr.domain.enums.TrackingStatus;
import com.scor.rr.domain.importfile.*;
import com.scor.rr.domain.riskLink.RLImportSelection;
import com.scor.rr.domain.riskLink.RLModelDataSource;
import com.scor.rr.domain.riskLink.RLPortfolioSelection;
import com.scor.rr.domain.views.RLAnalysisToTargetRAP;
import com.scor.rr.repository.*;
import com.scor.rr.service.RmsService;
import com.scor.rr.service.fileBasedImport.ImportFileService;
import com.scor.rr.service.state.TransformationBundle;
import com.scor.rr.service.state.TransformationPackage;
import com.scor.rr.util.EmbeddedQueries;
import com.scor.rr.util.PathUtils;
import org.apache.commons.lang3.StringUtils;
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
    private RLModelDataSourceRepository rlModelDataSourceRepository;

    @Autowired
    private ModellingSystemInstanceRepository modellingSystemInstanceRepository;

    @Autowired
    private ModelAnalysisEntityRepository rrAnalysisRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private TransformationPackage transformationPackage;

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






                // todo ben RL
//                modelAnalysisEntity.setRegion(sourceResult.getre());
//                modelAnalysisEntity.setSubPeril(sourceResult.getRlAnalysis().getSubPeril());
//                modelAnalysisEntity.setProfileName(sourceResult.getRlAnalysis().getProfileName());
//                modelAnalysisEntity.setGrain(sourceResult.getUserSelectedGrain() != null ? sourceResult.getUserSelectedGrain() : sourceResult.getRlAnalysis().getDefaultGrain());
//                modelAnalysisEntity.setGeoCode(sourceResult.getRlAnalysis().getGeoCode());
//                modelAnalysisEntity.setProxyScalingBasis(sourceResult.getProxyScalingBasis());
//                modelAnalysisEntity.setProxyScalingNarrative(sourceResult.getProxyScalingNarrative());
//                modelAnalysisEntity.setMultiplierBasis(sourceResult.getMultiplierBasis());
//                modelAnalysisEntity.setMultiplierNarrative(sourceResult.getMultiplierNarrative());
//                modelAnalysisEntity.setDescription(sourceResult.getRlAnalysis().getDescription());
//                modelAnalysisEntity.setUserOccurrenceBasis(sourceResult.getOccurrenceBasis());
//                modelAnalysisEntity.setOccurrenceBasisOverrideReason(sourceResult.getOccurrenceBasisOverrideReason());
//
//                // TODO : Not sure ?
//                modelAnalysisEntity.setOverrideReasonText(sourceResult.getOverrideRegionPerilBasis());
//                modelAnalysisEntity.setDivision(sourceResult.getDivision());
//                modelAnalysisEntity.setDefaultOccurrenceBasis(sourceResult.getRlAnalysis().getDefaultOccurrenceBasis());
//                modelAnalysisEntity.setTargetCurrency(sourceResult.getTargetCurrency());
//                modelAnalysisEntity.setProjectId(projectEntity.getProjectId());
//                modelAnalysisEntity.setCreationDate(new Date());
//                modelAnalysisEntity.setRunDate(sourceResult.getRlAnalysis().getRunDate());
//                modelAnalysisEntity.setImportStatus(TrackingStatus.INPROGRESS.toString());
//                modelAnalysisEntity.setProjectImportRunId(projectImportRunEntity.getProjectImportRunId());
//
//                TransformationBundle bundle = new TransformationBundle();
//                bundle.setInstanceId(instanceId);
//
//                Optional<RLModelDataSource> rlModelDataSource = rlModelDataSourceRepository.findById(sourceResult.getRlAnalysis().getRlModelDataSourceId());
//                rlModelDataSource.ifPresent(rlModelDataSourceItem -> {
//                    bundle.setInstanceId(rlModelDataSourceItem.getInstanceId());
//                    modelAnalysisEntity.setSourceModellingSystemInstance(rlModelDataSourceItem.getInstanceName());
//                    modellingSystemInstanceRepository.findById(rlModelDataSourceItem.getInstanceId()).ifPresent(modellingSystemInstance -> {
//                        modelAnalysisEntity.setSourceModellingVendor(modellingSystemInstance.getModellingSystemVersion().getModellingSystem().getVendor().getName());
//                        modelAnalysisEntity.setSourceModellingSystem(modellingSystemInstance.getModellingSystemVersion().getModellingSystem().getName());
//                        modelAnalysisEntity.setSourceModellingSystemVersion(modellingSystemInstance.getModellingSystemVersion().getModellingSystemVersion().toString());
//                    });
//                });
//
//                modelAnalysisEntity.setDataSourceId(sourceResult.getRlAnalysis().getRdmId());
//                modelAnalysisEntity.setDataSourceName(sourceResult.getRlAnalysis().getRdmName());
//                modelAnalysisEntity.setAnalysisId(sourceResult.getRlAnalysis().getRlId());
//                modelAnalysisEntity.setAnalysisName(sourceResult.getRlAnalysis().getAnalysisName());
//                modelAnalysisEntity.setFinancialPerspective(sourceResult.getFinancialPerspective());
//
//                // TODO : get from SourceEpHeader
////                if ("TY".equals(rrAnalysis.getFinancialPerspective())) {
////                    rrAnalysis.setTreatyLabel(analysisFinancialPerspective.getTreatyLabel());
////                    rrAnalysis.setTreatyTag(analysisFinancialPerspective.getTreatyTag());
////                }
//                RegionPerilEntity regionPeril = getRegionPeril(sourceResult);
//                modelAnalysisEntity.setPeril(sourceResult.getRlAnalysis().getPeril());
//                modelAnalysisEntity.setRegionPeril(regionPeril != null ? regionPeril.getRegionPerilCode() : null);
//                modelAnalysisEntity.setSourceCurrency(sourceResult.getRlAnalysis().getAnalysisCurrency());
//                modelAnalysisEntity.setTargetCurrency(sourceResult.getTargetCurrency());
//                modelAnalysisEntity.setExchangeRate(sourceResult.getRlAnalysis().getRlExchangeRate() != null ? sourceResult.getRlAnalysis().getRlExchangeRate().doubleValue() : 1.0);
//                modelAnalysisEntity.setUserOccurrenceBasis(sourceResult.getOccurrenceBasis());
//                modelAnalysisEntity.setProportion(sourceResult.getProportion() != null ? sourceResult.getProportion().doubleValue() : 100.0);
//                modelAnalysisEntity.setUnitMultiplier(sourceResult.getUnitMultiplier() != null ? sourceResult.getUnitMultiplier().doubleValue() : 1.0);
//                modelAnalysisEntity.setProfileKey(sourceResult.getRlAnalysis().getProfileKey());
//                modelAnalysisEntity.setAnalysisLevel(sourceResult.getRlAnalysis().getAnalysisType());
//                modelAnalysisEntity.setLossAmplification(sourceResult.getRlAnalysis().getLossAmplification());
//                modelAnalysisEntity.setModel(sourceResult.getRlAnalysis().getEngineType());

                ModelAnalysisEntity modelAnalysisEntityLambda = rrAnalysisRepository.saveAndFlush(modelAnalysisEntity);





                // create RRAnalysis
//                RRAnalysis rrAnalysis = new RRAnalysis();
//                mongoDBSequence.nextSequenceId(rrAnalysis);
                modelAnalysisEntity.setProjectId(projectId);

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

                // todo no SourceFileImport anymore
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
                File file = new File(importFileService.getRootFilePath() + sourceResult.getFilePath(), sourceResult.getFileName());
                if (file == null || !file.exists()) {
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

//                if (file.lastModified() > sourceResult.getImportFileHeader().getLastScanDate().getTime()) {
//                    log.warn("File {} has been modified after scanning. Need to validate before import", file.getName());
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
//
//                ImportFileLossDataHeader importFileLossDataHeader = sourceResult.getImportFileHeader();
//                modelAnalysisEntity.setSourceModellingVendor(importFileLossDataHeader.getMetadata().get("ModelProvider"));
//                modelAnalysisEntity.setSourceModellingSystem(importFileLossDataHeader.getMetadata().get("ModelSystem"));
//                modelAnalysisEntity.setSourceModellingSystemInstance(importFileLossDataHeader.getMetadata().get("ModelSystemInstance"));
//                modelAnalysisEntity.setSourceLossModellingBasis(importFileLossDataHeader.getMetadata().get("LossTableBasis"));
//                modelAnalysisEntity.setSourceLossTableType(importFileLossDataHeader.getMetadata().get("LossTableType"));
//                modelAnalysisEntity.setEventSet(importFileLossDataHeader.getMetadata().get("EventSet"));
//                modelAnalysisEntity.setModelModule(importFileLossDataHeader.getMetadata().get("ModelModule"));
//                modelAnalysisEntity.setModel(importFileLossDataHeader.getMetadata().get("ModelModule"));
//                modelAnalysisEntity.setSourceResultsReference(importFileLossDataHeader.getMetadata().get("SourceResultsReference"));
//                modelAnalysisEntity.setSourceModellingSystemVersion(importFileLossDataHeader.getMetadata().get("ModelSystemVersion"));
                // todo after, comment now
//                modelAnalysisEntity.setMetadata(importFileLossDataHeader.getMetadata());

                rrAnalysisRepository.save(modelAnalysisEntity);

                long pic = System.currentTimeMillis();
                List<ImportFilePLTData> datas = importFileService.parsePLTLossDataFile(file);
                if (datas != null) {
                    log.debug("File {}: read {} data lines took {} ms", file.getName(), datas.size(), System.currentTimeMillis() - pic);
                } else {
                    log.debug("File {}: parsing data section return null -- something wrong", file.getName());
                }

                // write text file to ihub
                File fileIhub = makeFullFile(getPrefixDirectory(), file.getName());
                copyFileUsingChannel(file, fileIhub);

                // create RRLossTable
//                createLossTableForOriginalFile(rrAnalysis, fileIhub, importFileLossDataHeader);
//
//                // create bundles here
//                TransformationBundleNonRMS bundle = new TransformationBundleNonRMS();
//                bundle.setHeader(importFileLossDataHeader);
//                bundle.setFile(file);
//                bundle.setDatas(datas);
//                bundle.setRrAnalysis(rrAnalysis);
//                bundle.setSourceResult(sourceResult);
//                bundle.setProjectImportLogAnalysisId(projectImportLogA.getId());
//
//                transformationPackage.addTransformationBundle(bundle);

                // finish step 1 LOAD_LOSS_DATA_FILE for one analysis in loop for of many analysis
//                Date endDate = new Date();
//                projectImportAssetLogA.setEndDate(endDate);
//                projectImportAssetLogRepository.save(projectImportAssetLogA);
//                log.info("Finish import progress STEP 1 : LOAD_LOSS_DATA_FILE for file: {}", file.getName());

                // if can come here means InProgress
//                projectImportLogA.setStatus(ProjectImportLog.TrackingStatus.InProgress.toString());
//                projectImportLogRepository.save(projectImportLogA);
                log.info("Tracking at the end of STEP 1 : LOAD_LOSS_DATA_FILE for file {}, status {}", file.getName(), "continue this tracking");

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

    private LossDataHeaderEntity makeSourceRRLT(ModelAnalysisEntity modelAnalysisEntity, RLImportSelection sourceResult, String financialPerspective, CurrencyEntity analysisCurrencyEntity) {

        if (financialPerspective == null) {
            log.debug("no analysis financial perspective found for source result {}", sourceResult.getRlImportSelectionId());
        }

        LossDataHeaderEntity rrLossTable = new LossDataHeaderEntity();
//        rrLossTable.setRrRepresentationDatasetId(rrRepresentationDataset.getId());
//        rrLossTable.setFileType("bin");
        rrLossTable.setModelAnalysisId(modelAnalysisEntity.getRrAnalysisId());
        rrLossTable.setCreatedDate(new Date());
        rrLossTable.setLossTableType("ELT");
        rrLossTable.setFileDataFormat("TreatyView");
        rrLossTable.setOriginalTarget(RRLossTableType.SOURCE.getCode());
        rrLossTable.setCurrency(analysisCurrencyEntity.getCode()); // Source Currency

        log.info("created source RRLossTable");
        return rrLossTable;
    }

    private LossDataHeaderEntity makeConformedRRLT(ModelAnalysisEntity modelAnalysisEntity, LossDataHeaderEntity sourceRRLT, CurrencyEntity currencyEntity) {

        LossDataHeaderEntity conformedRRLT = new LossDataHeaderEntity();
//        conformedRRLT.setRrRepresentationDatasetId(sourceRRLT.getRrRepresentationDatasetId());
//        conformedRRLT.setFileType(RRLossTable.FILE_TYPE_BIN); // TODO non c'est RMS
        conformedRRLT.setModelAnalysisId(modelAnalysisEntity.getRrAnalysisId());
        conformedRRLT.setLossTableType("ELT");
        conformedRRLT.setFileDataFormat("TreatyView");
        conformedRRLT.setOriginalTarget(RRLossTableType.CONFORMED.getCode());
        conformedRRLT.setCurrency(currencyEntity.getCode()); //  target currency
        // TODO fileName, filePath later

        log.info("Conformed ELT Header {}", conformedRRLT.getLossDataHeaderId());
        return conformedRRLT;
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
}
