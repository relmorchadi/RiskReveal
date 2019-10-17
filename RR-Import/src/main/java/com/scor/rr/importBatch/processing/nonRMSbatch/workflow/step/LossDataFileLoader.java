/*
package com.scor.rr.importBatch.processing.nonRMSbatch.workflow.step;

import com.scor.almf.cdm.domain.reference.ExchangeRate;
import com.scor.almf.cdm.repository.reference.CurrencyRepository;
import com.scor.almf.cdm.repository.reference.ExchangeRateRepository;
import com.scor.almf.cdm.repository.reference.UserRepository;
import com.scor.almf.ihub.treaty.processing.nonRMSbatch.bean.BaseNonRMSBean;
import com.scor.almf.ihub.treaty.processing.nonRMSbatch.workflow.bundle.TransformationBundleNonRMS;
import com.scor.almf.ihub.treaty.processing.nonRMSbatch.workflow.bundle.TransformationPackageNonRMS;
import com.scor.almf.treaty.cdm.domain.dss.agnostic.ProjectImportRun;
import com.scor.almf.treaty.cdm.domain.dss.agnostic.ProjectImportSourceConfig;
import com.scor.almf.treaty.cdm.domain.dss.agnostic.RRRepresentationDataset;
import com.scor.almf.treaty.cdm.domain.dss.agnostic.ihub.*;
import com.scor.almf.treaty.cdm.domain.dss.agnostic.meta.RRAnalysis;
import com.scor.almf.treaty.cdm.domain.dss.agnostic.stat.RREPCurve;
import com.scor.almf.treaty.cdm.domain.dss.agnostic.stat.RRStatisticHeader;
import com.scor.almf.treaty.cdm.domain.dss.agnostic.stat.RRSummaryStatistic;
import com.scor.almf.treaty.cdm.domain.dss.agnostic.stat.StatisticData;
import com.scor.almf.treaty.cdm.domain.dss.agnostic.tracking.AssetType;
import com.scor.almf.treaty.cdm.domain.dss.agnostic.tracking.ProjectImportAssetLog;
import com.scor.almf.treaty.cdm.domain.dss.agnostic.tracking.ProjectImportLog;
import com.scor.almf.treaty.cdm.domain.dss.nonRMS.FileImportSourceResult;
import com.scor.almf.treaty.cdm.domain.dss.nonRMS.SourceFileImport;
import com.scor.almf.treaty.cdm.domain.dss.rms.NonRmsProjectImportConfig;
import com.scor.almf.treaty.cdm.domain.importfiles.ImportFileLossDataHeader;
import com.scor.almf.treaty.cdm.domain.importfiles.ImportFilePLTData;
import com.scor.almf.treaty.cdm.domain.plt.meta.StatisticMetric;
import com.scor.almf.treaty.cdm.domain.rap.TargetRap;
import com.scor.almf.treaty.cdm.domain.workspace.Project;
import com.scor.almf.treaty.cdm.repository.dss.*;
import com.scor.almf.treaty.cdm.repository.dss.agnostic.*;
import com.scor.almf.treaty.cdm.repository.rap.SourceRapMappingRepository;
import com.scor.almf.treaty.cdm.repository.rap.TargetRapRepository;
import com.scor.almf.treaty.cdm.repository.region.TTRegionPerilRepository;
import com.scor.almf.treaty.cdm.repository.workspace.ProjectRepository;
import com.scor.almf.treaty.cdm.tools.sequence.MongoDBSequence;
import com.scor.almf.treaty.dao.DAOService;
import com.scor.almf.treaty.service.importfile.ImportFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.*;

*/
/**
 * Created by U005342 on 14/07/2018.
 *//*

public class LossDataFileLoader extends BaseNonRMSBean {
    private static final Logger log = LoggerFactory.getLogger(LossDataFileLoader.class);

    @Autowired
    TTFinancialPerspectiveRepository ttFinancialPerspectiveRepository;

    @Autowired
    TTRegionPerilRepository ttRegionPerilRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    @Autowired
    private ProjectImportRunRepository projectImportRunRepository;

    @Autowired
    private ProjectImportSourceConfigRepository projectImportSourceConfigRepository;

    @Autowired
    private SourceRapMappingRepository sourceRapMappingRepository;

    private Project getProject() {
        return projectRepository.findOne(getProjectId());
    }

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RepresentationDatasetRepository representationDatasetRepository;

    @Autowired
    private MongoDBSequence mongoDBSequence;

    @Autowired
    private ProjectImportLogRepository projectImportLogRepository;

    @Autowired
    private ProjectImportAssetLogRepository projectImportAssetLogRepository;

    @Autowired
    private NonRmsProjectImportConfigRepository nonRmsProjectImportConfigRepository;

    @Autowired
    private RRRepresentationDatasetRepository rrRepresentationDatasetRepository;

    @Autowired
    private RRAnalysisRepository rrAnalysisRepository;

    @Autowired
    private TargetRapRepository targetRapRepository;

    @Autowired
    private FileImportSourceResultRepository fileImportSourceResultRepository;

    @Autowired
    private SourceFileImportRepository sourceFileImportRepository;

    @Autowired
    private RRLossTableRepository rrLossTableRepository;

    private NonRmsProjectImportConfig nonRmsProjectImportConfig;

    @Autowired
    private ImportFileService importFileService;

    @Autowired
    private RRStatisticHeaderRepository rrStatisticHeaderRepository;

    @Autowired
    DAOService daoService;

    private Project project;

    private TransformationPackageNonRMS transformationPackage;

    public Boolean loadLossDataFile() throws Exception {
        log.debug("Starting loadLossDataFile");
        Date startDate = new Date();
        project = projectRepository.findOne(getProjectId());

        nonRmsProjectImportConfig = nonRmsProjectImportConfigRepository.findOne(getNonrmspicId());
        if (nonRmsProjectImportConfig == null) {
            return false;
        }

        ProjectImportSourceConfig projectImportSourceConfig = projectImportSourceConfigRepository.findByProjectIdAndSourceConfigVendor(getProjectId(), "Non RMS");
        if (projectImportSourceConfig == null) {
            return false;
        }

        // build ProjectImportRun ----------------------------------------------------------------------------------
        ProjectImportRun projectImportRun = new ProjectImportRun();
        mongoDBSequence.nextSequenceId(projectImportRun);
        projectImportRun.setProjectId(getProjectId());
        List<ProjectImportRun> projectImportRunList = projectImportRunRepository.findByProjectProjectId(projectImportRun.getProjectId());
        projectImportRun.setRunId(projectImportRunList == null ? 1 : projectImportRunList.size() + 1);
        projectImportRun.setProjectImportSourceConfigId(projectImportSourceConfig.getId());
        projectImportRun.setStatus(ProjectImportLog.TrackingStatus.InProgress.toString());
        projectImportRun.setStartDate(startDate);
        projectImportRun.setImportedBy(project.getAssignedTo().getFirstNameLastName());
        projectImportRun.setSourceConfigVendor("Non RMS");
        projectImportRunRepository.save(projectImportRun);

        nonRmsProjectImportConfig.setLastProjectImportRunId(projectImportRun.getId()); // each time replace old one
        nonRmsProjectImportConfigRepository.save(nonRmsProjectImportConfig);

        RRRepresentationDataset rrRepresentationDataset = rrRepresentationDatasetRepository.findByProjectProjectId(getProjectId());
        if (rrRepresentationDataset == null) {
            rrRepresentationDataset = new RRRepresentationDataset();
            mongoDBSequence.nextSequenceId(rrRepresentationDataset);
            rrRepresentationDataset.setProjectId(getProjectId());
            rrRepresentationDataset.setName("Unspecified Representation");
            rrRepresentationDataset.setIncrementName(1);
            rrRepresentationDatasetRepository.save(rrRepresentationDataset);
        }

        // tracking project
        ProjectImportLog projectImportLogPR = new ProjectImportLog();
        mongoDBSequence.nextSequenceId(projectImportLogPR);
        projectImportLogPR.setProjectId(getProjectId());
        projectImportLogPR.setProjectImportRunId(projectImportRun.getId());
        projectImportLogPR.setAssetType(AssetType.PROJECT);
        projectImportLogPR.setAssetId(projectImportRun.getProjectId());
        projectImportLogPR.setStartDate(new Date());
        projectImportLogPR.setImportedBy(projectImportRun.getImportedBy());
        log.info("Start tracking at STEP 1 : LOAD_LOSS_DATA_FILE for project: {}, status: {}", getProjectId(), projectImportLogPR.getStatus());
        // endDate, status later

        //--------------------------------------------------------------------------------------------------------------
        // begin loop for getSourceResultIdsInput
        for (String sourceResultId : getFileImportSourceResultIdsInput()) {
            FileImportSourceResult sourceResult = fileImportSourceResultRepository.findOne(sourceResultId);

            if (sourceResult == null) {
                log.error("FileImportSourceResult not found for id {}", sourceResultId);
                continue;
            }

            // create RRAnalysis
            RRAnalysis rrAnalysis = new RRAnalysis();
            mongoDBSequence.nextSequenceId(rrAnalysis);
            rrAnalysis.setProjectId(getProjectId());

            TargetRap targetRap = targetRapRepository.findByTargetRapCode(sourceResult.getTargetRapCode());
            if (targetRap != null && targetRap.getTargetRapId() != null) {
                rrAnalysis.getIncludedTargetRapIds().add(targetRap.getTargetRapId().toString());
            }

            rrAnalysis.setCreationDate(startDate);
            rrAnalysis.setImportStatus(ProjectImportLog.TrackingStatus.InProgress.toString());
            sourceResult.setImportStatus(rrAnalysis.getImportStatus());
            fileImportSourceResultRepository.save(sourceResult);
            rrAnalysis.setProjectImportRunId(projectImportRun.getId());
            rrAnalysis.setFinancialPerspective(sourceResult.getFinancialPerspective());
            rrAnalysis.setRegionPeril(sourceResult.getSelectedRP());
            rrAnalysis.setSourceCurrency(sourceResult.getCurrency());
            rrAnalysis.setTargetCurrencyBasis(sourceResult.getTargetCurrencyBasis());
            rrAnalysis.setTargetCurrency(sourceResult.getTargetCurrency());
            double exchangeRate = 1.0d;
            if (sourceResult.getExchangeRate() != null) {
                exchangeRate = sourceResult.getExchangeRate().doubleValue();
            } else {
                if (! sourceResult.getCurrency().equals(sourceResult.getTargetCurrency())) {

                    ExchangeRate exSource = exchangeRateRepository.findFirstByTypeAndDomesticCurrencyIdAndEffectiveDateBeforeOrderByEffectiveDateDesc(
                            "YEARLY", sourceResult.getCurrency(), rrAnalysis.getCreationDate());
                    ExchangeRate exTarget = exchangeRateRepository.findFirstByTypeAndDomesticCurrencyIdAndEffectiveDateBeforeOrderByEffectiveDateDesc(
                            "YEARLY", sourceResult.getTargetCurrency(), rrAnalysis.getCreationDate());

                    if (exSource != null && exTarget != null && exSource.getRates().get("EUR") != null && exTarget.getRates().get("EUR") != null) {
                        exchangeRate = exSource.getRates().get("EUR") / exTarget.getRates().get("EUR");
                    }
                }
                log.debug("source ELT currency {} conformed ELT currency {} exchange rate {}",sourceResult.getCurrency(), sourceResult.getTargetCurrency(), exchangeRate) ;
            }

            rrAnalysis.setExchangeRate(exchangeRate);
            rrAnalysis.setProportion(sourceResult.getProportion() != null ? sourceResult.getProportion().doubleValue() : 100.0);
            rrAnalysis.setUnitMultiplier(sourceResult.getUnitMultiplier() != null ? sourceResult.getUnitMultiplier().doubleValue() : 1.0);
            rrAnalysis.setResultName(sourceResult.getResultName());
            rrAnalysis.setDataSourceName(sourceResult.getDataSource());
            rrAnalysis.setFileName(sourceResult.getFileName());
            rrAnalysis.setGrain(sourceResult.getGrain());

            rrAnalysisRepository.save(rrAnalysis);


            // TODO tracking file need ???
            ProjectImportLog projectImportLogA = new ProjectImportLog();
            mongoDBSequence.nextSequenceId(projectImportLogA);
            projectImportLogA.setProjectId(getProjectId());
            projectImportLogA.setProjectImportRunId(projectImportRun.getId());
            projectImportLogA.setAssetType(AssetType.ANALYSIS);
            projectImportLogA.setAssetId(rrAnalysis.getId()); // tracking rrAnalysis
            projectImportLogA.setStartDate(startDate);
            projectImportLogA.setImportedBy(projectImportRun.getImportedBy());
            // endDate, status do more later

            ProjectImportAssetLog projectImportAssetLogA = new ProjectImportAssetLog();
            mongoDBSequence.nextSequenceId(projectImportAssetLogA);
            projectImportAssetLogA.setProjectId(getProjectId());
            projectImportAssetLogA.setProjectImportLogId(projectImportLogA.getId());
            projectImportAssetLogA.setStepId(1);
            projectImportAssetLogA.setStepName(StepNonRMS.getStepNameFromStepIdForAnalysis(projectImportAssetLogA.getStepId()).toString());
            projectImportAssetLogA.setStartDate(startDate);
            projectImportAssetLogRepository.save(projectImportAssetLogA);

            String fileId = sourceResult.getSourceFileImportId();
            SourceFileImport sourceFile = sourceFileImportRepository.findOne(fileId);
            if (sourceFile == null) {
                log.error("SourceFileImport not found for id {}", fileId);
                projectImportAssetLogA.getErrorMessages().add("SourceFileImport not found for id " + fileId);
                Date endDate = new Date();
                projectImportAssetLogA.setEndDate(endDate);
                projectImportAssetLogRepository.save(projectImportAssetLogA);
                log.info("Finish import progress STEP 1 : LOAD_LOSS_DATA_FILE for analysis: {}", sourceResultId);
                projectImportLogA.setEndDate(endDate);
                projectImportLogA.setStatus(ProjectImportLog.TrackingStatus.Error.toString());
                projectImportLogRepository.save(projectImportLogA);
                log.info("Finish tracking at the end of STEP 1 : LOAD_LOSS_DATA_FILE for analysis {}, status {}", sourceResultId, "Error", "stop this tracking");
                rrAnalysis.setImportStatus(ProjectImportLog.TrackingStatus.Error.toString());
                sourceResult.setImportStatus(rrAnalysis.getImportStatus());
                fileImportSourceResultRepository.save(sourceResult);
                rrAnalysisRepository.save(rrAnalysis);
                continue;
            }
            File file = new File(importFileService.getRootFilePath() + sourceFile.getFilePath(), sourceFile.getFileName());
            if (file == null || !file.exists()) {
                log.error("File not found: {}", importFileService.getRootFilePath() + sourceFile.getFilePath() + sourceFile.getFileName());
                projectImportAssetLogA.getErrorMessages().add("File not found: " + importFileService.getRootFilePath() + sourceFile.getFilePath() + sourceFile.getFileName());
                Date endDate = new Date();
                projectImportAssetLogA.setEndDate(endDate);
                projectImportAssetLogRepository.save(projectImportAssetLogA);
                log.info("Finish import progress STEP 1 : LOAD_LOSS_DATA_FILE for analysis: {}", sourceResultId);
                projectImportLogA.setEndDate(endDate);
                projectImportLogA.setStatus(ProjectImportLog.TrackingStatus.Error.toString());
                projectImportLogRepository.save(projectImportLogA);
                log.info("Finish tracking at the end of STEP 1 : LOAD_LOSS_DATA_FILE for analysis {}, status {}", sourceResultId, "Error", "stop this tracking");
                rrAnalysis.setImportStatus(ProjectImportLog.TrackingStatus.Error.toString());
                sourceResult.setImportStatus(rrAnalysis.getImportStatus());
                fileImportSourceResultRepository.save(sourceResult);
                rrAnalysisRepository.save(rrAnalysis);
                continue;
            }

            if (file.lastModified() > sourceFile.getImportFileHeader().getLastScanDate().getTime()) {
                log.warn("File {} has been modified after scanning. Need to validate before import", file.getName());
                importFileService.validate(sourceFile);
                if (! sourceFile.getErrorMessages().isEmpty()) {
                    log.error("Error: {}", sourceFile.getErrorMessages());
                    projectImportAssetLogA.getErrorMessages().add("Error: " + sourceFile.getErrorMessages());
                    Date endDate = new Date();
                    projectImportAssetLogA.setEndDate(endDate);
                    projectImportAssetLogRepository.save(projectImportAssetLogA);
                    log.info("Finish import progress STEP 1 : LOAD_LOSS_DATA_FILE for analysis: {}", sourceResultId);
                    projectImportLogA.setEndDate(endDate);
                    projectImportLogA.setStatus(ProjectImportLog.TrackingStatus.Error.toString());
                    projectImportLogRepository.save(projectImportLogA);
                    log.info("Finish tracking at the end of STEP 1 : LOAD_LOSS_DATA_FILE for analysis {}, status {}", sourceResultId, "Error", "stop this tracking");
                    rrAnalysis.setImportStatus(ProjectImportLog.TrackingStatus.Error.toString());
                    sourceResult.setImportStatus(rrAnalysis.getImportStatus());
                    fileImportSourceResultRepository.save(sourceResult);
                    rrAnalysisRepository.save(rrAnalysis);
                    continue;
                }
            }

            ImportFileLossDataHeader importFileLossDataHeader = sourceFile.getImportFileHeader();
            rrAnalysis.setSourceModellingVendor(importFileLossDataHeader.getMetadata().get("ModelProvider"));
            rrAnalysis.setSourceModellingSystem(importFileLossDataHeader.getMetadata().get("ModelSystem"));
            rrAnalysis.setSourceModellingSystemInstance(importFileLossDataHeader.getMetadata().get("ModelSystemInstance"));
            rrAnalysis.setSourceLossModellingBasis(importFileLossDataHeader.getMetadata().get("LossTableBasis"));
            rrAnalysis.setSourceLossTableType(importFileLossDataHeader.getMetadata().get("LossTableType"));
            rrAnalysis.setEventSet(importFileLossDataHeader.getMetadata().get("EventSet"));
            rrAnalysis.setModelModule(importFileLossDataHeader.getMetadata().get("ModelModule"));
            rrAnalysis.setModel(importFileLossDataHeader.getMetadata().get("ModelModule"));
            rrAnalysis.setSourceResultsReference(importFileLossDataHeader.getMetadata().get("SourceResultsReference"));
            rrAnalysis.setSourceModellingSystemVersion(importFileLossDataHeader.getMetadata().get("ModelSystemVersion"));
            rrAnalysis.setMetadata(importFileLossDataHeader.getMetadata());

            rrAnalysisRepository.save(rrAnalysis);

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
            createLossTableForOriginalFile(rrAnalysis, fileIhub, importFileLossDataHeader);

            // create bundles here
            TransformationBundleNonRMS bundle = new TransformationBundleNonRMS();
            bundle.setHeader(importFileLossDataHeader);
            bundle.setFile(file);
            bundle.setDatas(datas);
            bundle.setRrAnalysis(rrAnalysis);
            bundle.setSourceResult(sourceResult);
            bundle.setProjectImportLogAnalysisId(projectImportLogA.getId());

            transformationPackage.addTransformationBundle(bundle);

            // finish step 1 LOAD_LOSS_DATA_FILE for one analysis in loop for of many analysis
            Date endDate = new Date();
            projectImportAssetLogA.setEndDate(endDate);
            projectImportAssetLogRepository.save(projectImportAssetLogA);
            log.info("Finish import progress STEP 1 : LOAD_LOSS_DATA_FILE for file: {}", file.getName());

            // if can come here means InProgress
            projectImportLogA.setStatus(ProjectImportLog.TrackingStatus.InProgress.toString());
            projectImportLogRepository.save(projectImportLogA);
            log.info("Tracking at the end of STEP 1 : LOAD_LOSS_DATA_FILE for file {}, status {}", file.getName(), "continue this tracking");

            // if file not still exists : not create RRAnalysis
        }
        // end loop for sourceResults ------------------------------------------------------------------------------------------

        transformationPackage.setRrRepresentationDatasetId(rrRepresentationDataset.getId());
        transformationPackage.setProjectImportLogPRId(projectImportLogPR.getId());

        projectImportLogPR.setStatus(ProjectImportLog.TrackingStatus.InProgress.toString()); // tracking project must come to the last step
        projectImportLogRepository.save(projectImportLogPR);
        log.info("Tracking at the end of STEP 1 : LOAD_LOSS_DATA_FILE for project: {}, status: {}", getProjectId(), "InProgress", "continue this tracking");
        log.debug("LOAD_LOSS_DATA_FILE  completed");
        return true;
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

    private void createLossTableForOriginalFile(RRAnalysis rrAnalysis, File fileIhub, ImportFileLossDataHeader importFileLossDataHeader) {
        RRLossTable rrLossTable = new RRLossTable();
        mongoDBSequence.nextSequenceId(rrLossTable);

        RRFinancialPerspective rrFinancialPerspective = null;
        if (rrAnalysis.getFinancialPerspective() != null) {
            rrFinancialPerspective = new RRFinancialPerspective(rrAnalysis.getSourceModellingVendor(),
                    rrAnalysis.getSourceModellingSystem(),
                    rrAnalysis.getSourceModellingSystemVersion() != null ? rrAnalysis.getSourceModellingSystemVersion().toString() : null,
                    rrAnalysis.getFinancialPerspective());
        }

        StatFile statFile = new StatFile();
        statFile.setFinancialPerspective(rrFinancialPerspective);

        RRStatisticHeader oep = new RRStatisticHeader();
        mongoDBSequence.nextSequenceId(oep);
        oep.setProjectId(rrAnalysis.getProjectId());
        oep.setLossDataType(RRStatisticHeader.STAT_DATA_TYPE_PLT);
        oep.setLossTableId(rrLossTable.getId());
        oep.setFinancialPerspective(rrFinancialPerspective);
        oep.setStatisticData(createStatisticDataFromMetadata(importFileLossDataHeader, StatisticMetric.OEP));
        rrStatisticHeaderRepository.save(oep);

        RRStatisticHeader aep = new RRStatisticHeader();
        mongoDBSequence.nextSequenceId(aep);
        aep.setProjectId(rrAnalysis.getProjectId());
        aep.setLossDataType(RRStatisticHeader.STAT_DATA_TYPE_PLT);
        aep.setLossTableId(rrLossTable.getId());
        aep.setFinancialPerspective(rrFinancialPerspective);
        aep.setStatisticData(createStatisticDataFromMetadata(importFileLossDataHeader, StatisticMetric.AEP));
        rrStatisticHeaderRepository.save(aep);

        statFile.setStatisticHeaders(Arrays.asList(oep.getId(), aep.getId()));

        rrLossTable.setProjectId(rrAnalysis.getProjectId());
        rrLossTable.setRrAnalysisId(rrAnalysis.getId());
        rrLossTable.setLossTableType(RRLossTable.DATA_TYPE_PLT);
        rrLossTable.setFileDataFormat(RRLossTable.FILE_DATA_FORMAT_MAT_R);
        rrLossTable.setOriginalTarget(RRLossTableType.SOURCE.toString());
        rrLossTable.setFinancialPerspective(statFile.getFinancialPerspective());
        rrLossTable.setCurrency(rrAnalysis.getSourceCurrency());
        rrLossTable.setExchangeRate(1.0);
        rrLossTable.setProportion(rrAnalysis.getProportion());
        rrLossTable.setUnitsMultiplier(rrAnalysis.getUnitMultiplier());
        rrLossTable.setRegionPeril(rrAnalysis.getRegionPeril());

        rrLossTable.setLossDataFile(new LossDataFile(fileIhub.getName(), fileIhub.getParent()));
        rrLossTable.setStatFiles(Arrays.asList(statFile));

        rrLossTableRepository.save(rrLossTable);

        createConformedRrLossTable(rrLossTable, rrAnalysis);
    }

    private void createConformedRrLossTable(RRLossTable origRrLossTable, RRAnalysis rrAnalysis) {
        RRLossTable conformedRRrLossTable = new RRLossTable();
        mongoDBSequence.nextSequenceId(conformedRRrLossTable);

        conformedRRrLossTable.setProjectId(origRrLossTable.getProjectId());
        conformedRRrLossTable.setRrAnalysisId(origRrLossTable.getRrAnalysisId());
        conformedRRrLossTable.setLossTableType(RRLossTable.DATA_TYPE_PLT);
        conformedRRrLossTable.setFileDataFormat(RRLossTable.FILE_DATA_FORMAT_TREATY);
        conformedRRrLossTable.setOriginalTarget(RRLossTableType.CONFORMED.toString());
        conformedRRrLossTable.setFinancialPerspective(origRrLossTable.getFinancialPerspective());
        conformedRRrLossTable.setProportion(origRrLossTable.getProportion());
        conformedRRrLossTable.setUnitsMultiplier(origRrLossTable.getUnitsMultiplier());
        conformedRRrLossTable.setRegionPeril(origRrLossTable.getRegionPeril());
        conformedRRrLossTable.setCurrency(rrAnalysis.getTargetCurrency());
        conformedRRrLossTable.setExchangeRate(rrAnalysis.getExchangeRate());
//        conformedRRrLossTable.setLossDataFile(origRrLossTable.getLossDataFile());

        List<StatFile> conformedStatFiles = new ArrayList<>();
        for (StatFile origStat : origRrLossTable.getStatFiles()) {
            StatFile statFile = new StatFile();
            statFile.setFinancialPerspective(origStat.getFinancialPerspective());
            List<String> conformedStatisticHeaders = new ArrayList<>();
            for (String id : origStat.getStatisticHeaders()) {
                RRStatisticHeader origRrStatisticHeader = rrStatisticHeaderRepository.findOne(id);
                if (origRrStatisticHeader != null) {
                    RRStatisticHeader conformedRrStatisticHeader = new RRStatisticHeader();
                    mongoDBSequence.nextSequenceId(conformedRrStatisticHeader);
                    conformedRrStatisticHeader.setProjectId(rrAnalysis.getProjectId());
                    conformedRrStatisticHeader.setLossDataType(RRStatisticHeader.STAT_DATA_TYPE_PLT);
                    conformedRrStatisticHeader.setLossTableId(conformedRRrLossTable.getId());
                    conformedRrStatisticHeader.setFinancialPerspective(origRrStatisticHeader.getFinancialPerspective());
                    double factor = conformedRRrLossTable.getExchangeRate() * conformedRRrLossTable.getUnitsMultiplier() * (conformedRRrLossTable.getProportion() / 100.0);
                    StatisticData conformedStatisticData = new StatisticData();
                    conformedStatisticData.setStatisticMetric(origRrStatisticHeader.getStatisticData().getStatisticMetric());
                    Double aal = origRrStatisticHeader.getStatisticData().getSummaryStatistic().getPurePremium() != null ?
                            origRrStatisticHeader.getStatisticData().getSummaryStatistic().getPurePremium() * factor : null;
                    Double std = origRrStatisticHeader.getStatisticData().getSummaryStatistic().getStandardDeviation() != null ?
                            origRrStatisticHeader.getStatisticData().getSummaryStatistic().getStandardDeviation() * factor : null;
                    Double cov = origRrStatisticHeader.getStatisticData().getSummaryStatistic().getCov() != null ?
                            origRrStatisticHeader.getStatisticData().getSummaryStatistic().getCov() * factor : null;
                    conformedStatisticData.setSummaryStatistic(new RRSummaryStatistic(aal, std, cov));
                    List<RREPCurve> epCurves = new ArrayList<>();
                    for (RREPCurve epCurve : origRrStatisticHeader.getStatisticData().getEpCurves()) {
                        epCurves.add(new RREPCurve(epCurve.getReturnPeriod(), epCurve.getExceedanceProbability(), epCurve.getLossAmount()*factor));
                    }
                    conformedStatisticData.setEpCurves(epCurves);
                    conformedRrStatisticHeader.setStatisticData(conformedStatisticData);
                    rrStatisticHeaderRepository.save(conformedRrStatisticHeader);
                    conformedStatisticHeaders.add(conformedRrStatisticHeader.getId());
                }
            }
            statFile.setStatisticHeaders(conformedStatisticHeaders);
            conformedStatFiles.add(statFile);
        }

        conformedRRrLossTable.setStatFiles(conformedStatFiles);

        rrLossTableRepository.save(conformedRRrLossTable);

    }

    private StatisticData createStatisticDataFromMetadata(ImportFileLossDataHeader importFileLossDataHeader, StatisticMetric metric) {
        Map<String, String> metadata = importFileLossDataHeader.getMetadata();
        StatisticData statisticData = new StatisticData();
        statisticData.setStatisticMetric(metric);
        Double aal = metadata.get("AAL") != null ? Double.valueOf(metadata.get("AAL")) : null;
        Double std = metadata.get("STD") != null ? Double.valueOf(metadata.get("STD")) : null;
        Double cov = metadata.get("COV") != null ? Double.valueOf(metadata.get("COV")) : null;
        statisticData.setSummaryStatistic(new RRSummaryStatistic(aal, std, cov));
        List<RREPCurve> epCurves = new ArrayList<>();
        for (Map.Entry<String, String> entry : metadata.entrySet()) {
            if (entry.getKey().contains(metric.getValue()) && !entry.getKey().contains("[")) {
                int rp = Integer.valueOf(entry.getKey().replace(metric.getValue(), ""));
                double ep = 1.0 / rp;
                epCurves.add(new RREPCurve(rp, ep, Double.valueOf(entry.getValue())));
            }
        }
        statisticData.setEpCurves(epCurves);
        return statisticData;
    }

    public TransformationPackageNonRMS getTransformationPackage() {
        return transformationPackage;
    }

    public void setTransformationPackage(TransformationPackageNonRMS transformationPackage) {
        this.transformationPackage = transformationPackage;
    }
}
*/
