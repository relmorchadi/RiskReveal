package com.scor.rr.importBatch.processing.elt;

import com.scor.rr.domain.entities.ihub.*;
import com.scor.rr.domain.entities.plt.ProjectImportRun;
import com.scor.rr.domain.entities.plt.RRAnalysis;
import com.scor.rr.domain.entities.rap.SourceRapMapping;
import com.scor.rr.domain.entities.references.RegionPeril;
import com.scor.rr.domain.entities.references.cat.ModellingSystemInstance;
import com.scor.rr.domain.entities.references.omega.Currency;
import com.scor.rr.domain.entities.rms.*;
import com.scor.rr.domain.entities.tracking.ProjectImportLog;
import com.scor.rr.domain.entities.workspace.Portfolio;
import com.scor.rr.domain.entities.workspace.Project;
import com.scor.rr.domain.enums.AssetType;
import com.scor.rr.domain.enums.FileDATAFormatType;
import com.scor.rr.domain.enums.RRLossTableType;
import com.scor.rr.domain.enums.TrackingStatus;
import com.scor.rr.importBatch.processing.batch.rms.BaseRMSBeanImpl;
import com.scor.rr.importBatch.processing.domain.ELTData;
import com.scor.rr.importBatch.processing.treaty.TransformationBundle;
import com.scor.rr.importBatch.processing.treaty.TransformationPackage;
import com.scor.rr.repository.TTRegionPerilRepository;
import com.scor.rr.repository.cat.ModellingSystemInstanceRepository;
import com.scor.rr.repository.ihub.*;
import com.scor.rr.repository.omega.CurrencyRepository;
import com.scor.rr.repository.plt.ProjectImportRunRepository;
import com.scor.rr.repository.plt.RRAnalysisRepository;
import com.scor.rr.repository.plt.TTFinancialPerspectiveRepository;
import com.scor.rr.repository.rap.SourceRapMappingRepository;
import com.scor.rr.repository.references.ExchangeRateRepository;
import com.scor.rr.repository.references.UserRepository;
import com.scor.rr.repository.rms.ProjectImportAssetLogRepository;
import com.scor.rr.repository.rms.ProjectImportSourceConfigRepository;
import com.scor.rr.repository.rms.RmsAnalysisRepository;
import com.scor.rr.repository.rms.RmsProjectImportConfigRepository;
import com.scor.rr.repository.tracking.ProjectImportLogRepository;
import com.scor.rr.repository.workspace.PortfolioRepository;
import com.scor.rr.repository.workspace.ProjectRepository;
import com.scor.rr.utils.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by U002629 on 09/06/2015.
 */
public class RMSELTPartitionerImpl extends BaseRMSBeanImpl implements RMSELTPartitioner {
    private static final Logger log = LoggerFactory.getLogger(RMSELTPartitionerImpl.class);

    @Autowired
    TTFinancialPerspectiveRepository ttFinancialPerspectiveRepository;
    @Autowired
    TTRegionPerilRepository ttRegionPerilRepository;
    @Autowired
    private RmsAnalysisRepository rmsAnalysisRepository;
    @Autowired
    private CurrencyRepository currencyRepository;
    @Autowired
    private ExchangeRateRepository exchangeRateRepository;
    @Autowired
    private ProjectImportRunRepository projectImportRunRepository;
    @Autowired
    private ProjectImportSourceConfigRepository projectImportSourceConfigRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RepresentationDatasetRepository representationDatasetRepository;
//        @Autowired
//    private ImportDecisionRepository importDecisionRepository;
    @Autowired
    private SourceResultRepository sourceResultRepository;
    @Autowired
    private PortfolioRepository portfolioRepository;
    @Autowired
    private TransformationPackage transformationPackage;
    @Autowired
    private ProjectImportLogRepository projectImportLogRepository;
    @Autowired
    private ProjectImportAssetLogRepository projectImportAssetLogRepository;
    @Autowired
    private RmsProjectImportConfigRepository rmsProjectImportConfigRepository;
    @Autowired
    private RRLinkedDataSetRepository rrLinkedDataSetRepository;
    @Autowired
    private RRRepresentationDatasetRepository rrRepresentationDatasetRepository;
    @Autowired
    private ModellingSystemInstanceRepository modellingSystemInstanceRepository;
    @Autowired
    private RRAnalysisRepository rrAnalysisRepository;
    @Autowired
    private RRPortfolioRepository rrPortfolioRepository;
    @Autowired
    private SourceRapMappingRepository sourceRapMappingRepository;
    private RmsProjectImportConfig rmsProjectImportConfig;
    private Project project;
    private ELTData eltData;
    private String rpListQuery;

    private Project getProject() {
        return projectRepository.findById(getProjectId()).get();
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public Boolean loadRegionPerils() {
        log.debug("Starting loadRegionPerils");
        Date startDate = new Date();
        java.sql.Date startDateSql = new java.sql.Date(startDate.getTime());
        project = projectRepository.findById(getProjectId()).orElse(null);

        // new code ri
        rmsProjectImportConfig = rmsProjectImportConfigRepository.findById(getRmspicId()).orElse(null);
        if (rmsProjectImportConfig == null || rmsProjectImportConfig.getSelectedAssociationBags() == null) {
            return false;
        }

        // TODO : old code ri : chuyen ve front end
        ProjectImportSourceConfig projectImportSourceConfig = projectImportSourceConfigRepository.findByProjectIdAndSourceConfigVendor(getProjectId(), getSourceConfigVendor());
        if (projectImportSourceConfig == null) {
            return false;
        }

        // build ProjectImportRun ----------------------------------------------------------------------------------
        ProjectImportRun projectImportRun = new ProjectImportRun();
        //mongoDBSequence.nextSequenceId(projectImportRun);
        projectImportRun.setProject(project);
        List<ProjectImportRun> projectImportRunList = projectImportRunRepository.findByProjectProjectId(projectImportRun.getProject().getProjectId());
        projectImportRun.setRunId(projectImportRunList == null ? 1 : projectImportRunList.size() + 1);
        projectImportRun.setProjectImportSourceConfigId(projectImportSourceConfig.getProjectImportSourceConfigId());
        projectImportRun.setStatus(TrackingStatus.INPROGRESS.toString());
        projectImportRun.setStartDate(startDateSql);
        projectImportRun.setImportedBy(project.getAssignedTo());
        projectImportRun.setSourceConfigVendor(projectImportSourceConfig.getSourceConfigVendor());
        projectImportRun = projectImportRunRepository.save(projectImportRun);

        // TODO : do when migration
        rmsProjectImportConfig.setLastProjectImportRunId(projectImportRun.getProjectImportRunId()); // each time replace old one
        rmsProjectImportConfigRepository.save(rmsProjectImportConfig);

        RRRepresentationDataset rrRepresentationDataset = rrRepresentationDatasetRepository.findByProjectId(getProjectId());
        if (rrRepresentationDataset == null) {
            rrRepresentationDataset = new RRRepresentationDataset();
            //mongoDBSequence.nextSequenceId(rrRepresentationDataset);
            rrRepresentationDataset.setProjectId(getProjectId());
            rrRepresentationDataset.setName("Unspecified Representation");
            rrRepresentationDataset.setIncrementName(1);
            rrRepresentationDatasetRepository.save(rrRepresentationDataset);
        }

        // tracking project
        ProjectImportLog projectImportLogPR = new ProjectImportLog();
        //mongoDBSequence.nextSequenceId(projectImportLogPR);
        projectImportLogPR.setProjectId(getProjectId());
        projectImportLogPR.setProjectImportRunId(projectImportRun.getProjectImportRunId());
        projectImportLogPR.setAssetType(AssetType.PROJECT.toString());
        projectImportLogPR.setAssetId(projectImportRun.getProject().getProjectId());
        projectImportLogPR.setStartDate(new Date());
        projectImportLogPR.setImportedBy(projectImportRun.getImportedBy().getCode());
        log.info("Start tracking at STEP 1 : LOAD_REGION_PERIL for project: {}, status: {}", getProjectId(), projectImportLogPR.getStatus());
        // endDate, status later

        // build RRImportingPortfolio ------------------------------------------------------------
        Map<String, String> mapPortfolioRRPortfolioIds = new HashMap<>();

        if (getPortfolioIdsInput() != null) {
            for (String portfolioId : getPortfolioIdsInput()) { // sure that getSourceResultIdsInput() not null
                //TODO : change later
                Portfolio portfolio = portfolioRepository.findById(portfolioId).get();
                RRPortfolio rrPortfolio = new RRPortfolio();
                //mongoDBSequence.nextSequenceId(rrPortfolio);
                rrPortfolio.setProject(project);
                rrPortfolio.setCreationDate(startDateSql);
//                portfolioRepository.save(portfolio);
                rrPortfolio.setProjectImportRun(projectImportRun);
                rrPortfolio.setSourceModellingSystemInstance(portfolio.getModelingExposureDataSource().getRmsModelDataSource().getInstanceName());
                ModellingSystemInstance modellingSystemInstance = modellingSystemInstanceRepository.findById(portfolio.getModelingExposureDataSource().getRmsModelDataSource().getInstanceId()).orElse(null);
                rrPortfolio.setSourceModellingVendor(modellingSystemInstance.getModellingSystemVersion().getModellingSystem().getVendor().getName());
                rrPortfolio.setSourceModellingSystem(modellingSystemInstance.getModellingSystemVersion().getModellingSystem().getName());
                rrPortfolio.setSourceModellingSystemVersion(modellingSystemInstance.getModellingSystemVersion().getModellingSystemVersion());
                rrPortfolio.setDataSourceId(portfolio.getRmsPortfolio().getEdmId());
                rrPortfolio.setDataSourceName(portfolio.getRmsPortfolio().getEdmName());
                rrPortfolio.setPortfolioId(portfolio.getRmsPortfolio().getPortfolioId());
                rrPortfolio.setPortfolioName(portfolio.getRmsPortfolio().getName());
                rrPortfolio.setExposedLocationPerils(portfolio.getSelectedRmsPortfolioAnalysisRegions().size() == 0 ? "All" :
                        (portfolio.getSelectedRmsPortfolioAnalysisRegions().size() == portfolio.getRmsPortfolio().getAnalysisRegions().size() ? "All" : "Partial"));
//                rrPortfolio.setCurrency(portfolio.getRmsPortfolio().getAgCurrency());
                //rrPortfolio.setExchangeRate(1d); // TODO change it
                rrPortfolio.setProportion(portfolio.getImportProportion() != null ? portfolio.getImportProportion().doubleValue() : 100.0);
                rrPortfolio.setUnitMultiplier(portfolio.getUnitMultiplier() != null ? portfolio.getUnitMultiplier().doubleValue() : 1.0);
                rrPortfolio.setDescription(portfolio.getRmsPortfolio().getDescription());
                rrPortfolio.setExposureLevel(portfolio.getRmsPortfolio().getType());
                rrPortfolio.setTivAnalysisRegions(portfolio.getSelectedRmsPortfolioAnalysisRegions());
                rrPortfolio.setTags(portfolio.getTags());
                //rrPortfolio.setAllTags(portfolio.getAllTags());
                rrPortfolio.setUserNotes(portfolio.getNotes());
                //rrPortfolio.setCurrency(portfolio.getTargetCurrency());
                rrPortfolio.setTargetCurrencyBasis(portfolio.getTargetCurrencyBasis());
                rrPortfolioRepository.save(rrPortfolio);

                mapPortfolioRRPortfolioIds.put(portfolio.getPortfolioId(), rrPortfolio.getRrPortfolioId());
            }
        }

        // build RRImportingAnalysis ------------------------------------------------------------
        Map<String, Map<String, String>> mapAnalysisRRAnalysisIds = new HashMap<>();
        Map<String, String> mapAnalysisRRTrackingImportingAnalysisIds = new HashMap<>();

        // each step one new importProgress
        // create bundle here
        if (getSourceResultIdsInput() != null) {
            for (String sourceResultId : getSourceResultIdsInput()) { // sure that getSourceResultIdsInput() not null ?
                SourceResult sourceResult = sourceResultRepository.findById(sourceResultId).get();
                Map<String, String> fpRRAnalysis = new HashMap<>();
                for (AnalysisFinancialPerspective analysisFinancialPerspective : sourceResult.getAnalysisFinancialPerspectives()) {
                    RRAnalysis rrAnalysis = new RRAnalysis();
                    rrAnalysis.setRegion(sourceResult.getRmsAnalysis().getRegion());
                    rrAnalysis.setSourceResultsReference(sourceResult.getModelingResultDataSource().getModelingResultDataSourceId());
                    rrAnalysis.setTargetCcyBasis(sourceResult.getTargetCurrencyBasis());
                    rrAnalysis.setSubPeril(sourceResult.getRmsAnalysis().getSubPeril());
                    rrAnalysis.setProfileName(sourceResult.getRmsAnalysis().getProfileName());
                    rrAnalysis.setProject(project);
                    // TODO : BIG WTF ?? sourceResult.getIncludedTargetRapIds()
                    rrAnalysis.setIncludedTargetRapIds(sourceResult.getIncludedTargetRapIds());
                    //
                    rrAnalysis.setCreationDate(startDateSql);
                    rrAnalysis.setRunDate(sourceResult.getRmsAnalysis().getRunDate());
                    rrAnalysis.setImportStatus(TrackingStatus.INPROGRESS.toString());
                    sourceResult.setImportStatus(rrAnalysis.getImportStatus());
                    sourceResultRepository.save(sourceResult);
                    rrAnalysis.setProjectImportRun(projectImportRun);
                    rrAnalysis.setSourceModellingSystemInstance(sourceResult.getModelingResultDataSource().getRmsModelDataSource().getInstanceName());
                    ModellingSystemInstance modellingSystemInstance = modellingSystemInstanceRepository.findById(sourceResult.getModelingResultDataSource().getRmsModelDataSource().getInstanceId()).get();
                    rrAnalysis.setSourceModellingVendor(modellingSystemInstance.getModellingSystemVersion().getModellingSystem().getVendor().getName());
                    rrAnalysis.setSourceModellingSystem(modellingSystemInstance.getModellingSystemVersion().getModellingSystem().getName());
                    rrAnalysis.setSourceModellingSystemVersion(modellingSystemInstance.getModellingSystemVersion().getModellingSystemVersion().toString());
                    rrAnalysis.setDataSourceId(sourceResult.getRmsAnalysis().getRdmId());
                    rrAnalysis.setDataSourceName(sourceResult.getRmsAnalysis().getRdmName());
                    rrAnalysis.setAnalysisId(sourceResult.getRmsAnalysis().getAnalysisId());
                    rrAnalysis.setAnalysisName(sourceResult.getRmsAnalysis().getAnalysisName());
                    rrAnalysis.setGrain(sourceResult.getUserSelectedGrain() != null ? sourceResult.getUserSelectedGrain() : sourceResult.getRmsAnalysis().getDefaultGrain());
                    rrAnalysis.setFinancialPerspective(analysisFinancialPerspective.getCode());
                    if ("TY".equals(rrAnalysis.getFinancialPerspective())) {
                        rrAnalysis.setTreatyLabel(analysisFinancialPerspective.getTreatyLabel());
                        rrAnalysis.setTreatyTag(analysisFinancialPerspective.getTreatyTag());
                    }
                    rrAnalysis.setPeril(sourceResult.getRmsAnalysis().getPeril());
                    rrAnalysis.setGeoCode(sourceResult.getRmsAnalysis().getGeoCode());
                    rrAnalysis.setRegionPeril(getRegionPeril(sourceResult).getRegionPerilCode());
                    //TODO : GET CURRENCIES
//                    rrAnalysis.setSourceCcy(sourceResult.getRmsAnalysis().getAnalysisCurrency());
//                    rrAnalysis.setTargetCcy(sourceResult.getTargetCurrency());
                    rrAnalysis.setExchangeRate(sourceResult.getExchangeRate() != null ? sourceResult.getExchangeRate().doubleValue() : 1.0);
                    rrAnalysis.setUserOccurrenceBasis(analysisFinancialPerspective.getUserOccurrenceBasis());
                    rrAnalysis.setDefaultOccurrenceBasis(analysisFinancialPerspective.getDefaultOccurrenceBasis());
                    rrAnalysis.setProportion(sourceResult.getProportion() != null ? sourceResult.getProportion().doubleValue() : 100.0);
                    rrAnalysis.setProxyScalingBasis(sourceResult.getProxyScalingBasis());
                    rrAnalysis.setProxyScalingNarrative(sourceResult.getProxyScalingNarrative());
                    rrAnalysis.setUnitMultiplier(sourceResult.getUnitMultiplier() != null ? sourceResult.getUnitMultiplier().doubleValue() : 1.0);
                    rrAnalysis.setMultiplierBasis(sourceResult.getMultiplierBasis());
                    rrAnalysis.setMultiplierNarrative(sourceResult.getMultiplierNarrative());
                    rrAnalysis.setProfileKey(sourceResult.getProfileKey());
                    rrAnalysis.setDescription(sourceResult.getRmsAnalysis().getDescription());
                    rrAnalysis.setAnalysisLevel(sourceResult.getRmsAnalysis().getAnalysisType());
                    rrAnalysis.setLossAmplification(sourceResult.getRmsAnalysis().getLossAmplification());
                    rrAnalysis.setModel(sourceResult.getRmsAnalysis().getEngineType());
                    rrAnalysis.setTags(sourceResult.getTags());
                    rrAnalysis.setUserNotes(sourceResult.getNotes());
                    rrAnalysis.setOverrideReasonText(sourceResult.getOverrideReasonText());
//                    rrAnalysis.setGrouped(sourceResult.getRmsAnalysis().getMultipleRegionPerils());
//                    if (BooleanUtils.isTrue(rrAnalysis.isGrouped()) && sourceResult.getMultipleRegionPerils() != null) {
//                        rrAnalysis.setRmsMultipleRegionPerils(sourceResult.getMultipleRegionPerils());
//                    }
                    rrAnalysisRepository.save(rrAnalysis);

                    fpRRAnalysis.put(analysisFinancialPerspective.getDisplayCode(), rrAnalysis.getAnalysisId());

                    // tracking analysis
                    ProjectImportLog projectImportLogA = new ProjectImportLog();
                    projectImportLogA.setProjectId(getProjectId());
                    projectImportLogA.setProjectImportRunId(projectImportRun.getProjectImportRunId());
                    projectImportLogA.setAssetType(AssetType.ANALYSIS.toString());
                    projectImportLogA.setAssetId(rrAnalysis.getAnalysisId()); // tracking rrAnalysis
                    projectImportLogA.setStartDate(startDate);
                    projectImportLogA.setImportedBy(projectImportRun.getImportedBy().getFirstName() + " " + projectImportRun.getImportedBy().getFirstName());
                    // endDate, status do more later
                    mapAnalysisRRTrackingImportingAnalysisIds.put(sourceResultId, projectImportLogA.getProjectImportLogId());

                    ProjectImportAssetLog projectImportAssetLogA = new ProjectImportAssetLog();
                    projectImportAssetLogA.setProjectId(getProjectId());
                    projectImportAssetLogA.setProjectImportLogId(projectImportLogA.getProjectImportLogId());
                    projectImportAssetLogA.setStepId(1);
                    projectImportAssetLogA.setStepName(Step.getStepNameFromStepIdForAnalysis(projectImportAssetLogA.getStepId()));
                    projectImportAssetLogA.setStartDate(startDate);
                    projectImportAssetLogRepository.save(projectImportAssetLogA);

                    if (sourceResult == null || sourceResult.getRmsAnalysis() == null) {
                        log.error("Error creating source ELT. Error: source result not found or not contain a RMS analysis for id {}", sourceResult.getSourceResultId());
                        projectImportAssetLogA.getErrorMessages().add("Error creating source ELT. Error: source result not found or not contain a RMS analysis for id " + sourceResult.getSourceResultId());
                        Date endDate = new Date();
                        projectImportAssetLogA.setEndDate(endDate);
                        projectImportAssetLogRepository.save(projectImportAssetLogA);
                        log.info("Finish import progress STEP 1 : LOAD_REGION_PERIL for analysis: {}", sourceResultId);
                        projectImportLogA.setEndDate(endDate);
                        projectImportLogA.setStatus(TrackingStatus.ERROR.toString());
                        projectImportLogRepository.save(projectImportLogA);
                        log.info("Finish tracking at the end of STEP 1 : LOAD_REGION_PERIL for analysis {}, status {}", sourceResultId, "Error", "stop this tracking");
                        rrAnalysis.setImportStatus(TrackingStatus.ERROR.toString());
                        sourceResult.setImportStatus(rrAnalysis.getImportStatus());
                        sourceResultRepository.save(sourceResult);
                        // if error no imported date for rrImportingAnalysis
                        rrAnalysisRepository.save(rrAnalysis);
                        // if Error, continue means that this analysis is not added to bundles, this tracking is terminated, not appear any more on next step
                        continue;
                    }

                    RmsAnalysis rmsAnalysis = rmsAnalysisRepository.findById(sourceResult.getRmsAnalysis().getRmsAnalysisId()).get();
                    if (rmsAnalysis == null) {
                        log.error("Error creating source ELT. Error: rmsAnalysis not found for id {}", sourceResult.getRmsAnalysis().getRmsAnalysisId());
                        projectImportAssetLogA.getErrorMessages().add("Error creating source ELT. Error: rmsAnalysis not found for id " + sourceResult.getRmsAnalysis().getRmsAnalysisId());
                        Date endDate = new Date();
                        projectImportAssetLogA.setEndDate(endDate);
                        projectImportAssetLogRepository.save(projectImportAssetLogA);
                        log.info("Finish import progress STEP 1 : LOAD_REGION_PERIL for analysis: {}", sourceResultId);
                        projectImportLogA.setEndDate(endDate);
                        projectImportLogA.setStatus(TrackingStatus.ERROR.toString());
                        projectImportLogRepository.save(projectImportLogA);
                        log.info("Finish tracking at the end of STEP 1 : LOAD_REGION_PERIL for analysis {}, status {}", sourceResultId, "Error", "stop this tracking");
                        rrAnalysis.setImportStatus(TrackingStatus.ERROR.toString());
                        sourceResult.setImportStatus(rrAnalysis.getImportStatus());
                        sourceResultRepository.save(sourceResult);
                        rrAnalysisRepository.save(rrAnalysis);
                        // if Error, continue means that this analysis is not added to bundles the tracking is terminated, not appear any more on next step
                        continue;
                    }

                    String analysisName = rmsAnalysis == null ? null : rmsAnalysis.getAnalysisName();
                    String analysisId = rmsAnalysis == null ? null : rmsAnalysis.getAnalysisId().toString();


                    if (rrAnalysis.getProfileKey() == null) {
                        log.error("Error creating source ELT. Error: no profile key found for analysis {} , id {}", analysisName, analysisId);
                        projectImportAssetLogA.getErrorMessages().add(String.format("Error: no profile key found for analysis %s , id %s", analysisName, analysisId));
                        Date endDate = new Date();
                        projectImportAssetLogA.setEndDate(endDate);
                        projectImportAssetLogRepository.save(projectImportAssetLogA);
                        log.info("Finish import progress STEP 1 : LOAD_REGION_PERIL for analysis: {}", sourceResultId);
                        projectImportLogA.setEndDate(endDate);
                        projectImportLogA.setStatus(TrackingStatus.ERROR.toString());
                        projectImportLogRepository.save(projectImportLogA);
                        log.info("Finish tracking at the end of STEP 1 : LOAD_REGION_PERIL for analysis {}, status {}", sourceResultId, "Error", "stop this tracking");
                        rrAnalysis.setImportStatus(TrackingStatus.ERROR.toString());
                        sourceResult.setImportStatus(rrAnalysis.getImportStatus());
                        sourceResultRepository.save(sourceResult);
                        rrAnalysisRepository.save(rrAnalysis);
                        // if Error, continue means that this analysis is not added to bundles the tracking is terminated, not appear any more on next step
                        continue;
                    }

                    List<SourceRapMapping> sourceRapMappings = sourceRapMappingRepository.findByProfileKey(rrAnalysis.getProfileKey());
                    if (sourceRapMappings == null || sourceRapMappings.isEmpty()) {
                        log.error("Error creating source ELT. Error: no sourceRAP found for profile key {}, analysis {} , id {}", rrAnalysis.getProfileKey(), analysisName, analysisId);
                        projectImportAssetLogA.getErrorMessages().add(String.format("Error: no sourceRAP found for profile key %s, analysis %s , id %s", rrAnalysis.getProfileKey(), analysisName, analysisId));
                        Date endDate = new Date();
                        projectImportAssetLogA.setEndDate(endDate);
                        projectImportAssetLogRepository.save(projectImportAssetLogA);
                        log.info("Finish import progress STEP 1 : LOAD_REGION_PERIL for analysis: {}", sourceResultId);
                        projectImportLogA.setEndDate(endDate);
                        projectImportLogA.setStatus(TrackingStatus.ERROR.toString());
                        projectImportLogRepository.save(projectImportLogA);
                        log.info("Finish tracking at the end of STEP 1 : LOAD_REGION_PERIL for analysis {}, status {}", sourceResultId, "Error", "stop this tracking");
                        rrAnalysis.setImportStatus(TrackingStatus.ERROR.toString());
                        sourceResult.setImportStatus(rrAnalysis.getImportStatus());
                        sourceResultRepository.save(sourceResult);
                        rrAnalysisRepository.save(rrAnalysis);
                        // if Error, continue means that this analysis is not added to bundles the tracking is terminated, not appear any more on next step
                        continue;
                    }

                    RegionPeril regionPeril = getRegionPeril(sourceResult);
                    if (regionPeril == null) {
                        log.error("Error creating source ELT. Error: no region peril found for analysis {} , id {}", analysisName, analysisId);
                        projectImportAssetLogA.getErrorMessages().add(String.format("Error: no region peril found for analysis %s , id %s", analysisName, analysisId));
                        Date endDate = new Date();
                        projectImportAssetLogA.setEndDate(endDate);
                        projectImportAssetLogRepository.save(projectImportAssetLogA);
                        log.info("Finish import progress STEP 1 : LOAD_REGION_PERIL for analysis: {}", sourceResultId);
                        projectImportLogA.setEndDate(endDate);
                        projectImportLogA.setStatus(TrackingStatus.ERROR.toString());
                        projectImportLogRepository.save(projectImportLogA);
                        log.info("Finish tracking at the end of STEP 1 : LOAD_REGION_PERIL for analysis {}, status {}", sourceResultId, "Error", "stop this tracking");
                        rrAnalysis.setImportStatus(TrackingStatus.ERROR.toString());
                        sourceResult.setImportStatus(rrAnalysis.getImportStatus());
                        sourceResultRepository.save(sourceResult);
                        rrAnalysisRepository.save(rrAnalysis);
                        // if Error, continue means that this analysis is not added to bundles the tracking is terminated, not appear any more on next step
                        continue;
                    }

                    SourceRapMapping sourceRapMapping = sourceRapMappings.get(0);
                    log.info("make source ELTHeader from rpCode {}, profileKey {} (found {} SourceRapMappings, first SourceRapCode is {}, currency {})", regionPeril.getRegionPerilCode(), rrAnalysis.getProfileKey(), sourceRapMappings.size(), sourceRapMapping.getSourceRapCode(), sourceResult.getTargetCurrency());

                    Currency analysisCurrency = null;
                    if (sourceResult.getRmsAnalysis().getAnalysisCurrency() != null) {
                        analysisCurrency = currencyRepository.findByCode(sourceResult.getRmsAnalysis().getAnalysisCurrency());
                    } else {
                        log.debug("Source currency is null, use USD as source currency");
                        analysisCurrency = currencyRepository.findByCode("USD");
                    }

                    String instanceId;
                    if (rmsAnalysis.getRmsModelDatasource() != null && rmsAnalysis.getRmsModelDatasource().getInstanceId() != null) {
                        instanceId = rmsAnalysis.getRmsModelDatasource().getInstanceId();
                    } else {
                        log.warn("RmsModelDatasource is null for rmsAnalysis {} - use instanceId from batch", rmsAnalysis.getRmsAnalysisId());
                        instanceId = getInstanceId();
                    }

                    // begin for dataset of old code
                    RepresentationDataset repdataset = null;
                    // not keep precedent config so each time by finding, we have the datasets of the new run
                    List<RepresentationDataset> repDatasets = representationDatasetRepository.findByProjectProjectId(getProject().getProjectId());
                    if (repDatasets != null) {
                        for (RepresentationDataset repDts : repDatasets) {
                            if (repdataset == null) {
                                for (SourceResult sr : repDts.getRepresentedSourceResults()) {
                                    if (sr.getSourceResultId().equals(sourceResultId)) {
                                        repdataset = repDts;
                                        break;
                                    }
                                }
                            } else {
                                break;
                            }
                        }
                    }

                    RRLossTable sourceRRLT = makeSourceRRLT(rrAnalysis, sourceResult, getProject(),
                            rmsAnalysis, regionPeril, analysisFinancialPerspective, analysisCurrency, rrRepresentationDataset);

                    Currency targetCurrency = null;
                    if (sourceResult.getTargetCurrency() != null) {
                        targetCurrency = currencyRepository.findByCode(sourceResult.getTargetCurrency());
                    } else {
                        log.debug("Target currency is null, use USD as target currency");
                        targetCurrency = currencyRepository.findByCode("USD");
                    }

                    RRLossTable conformedRRLT = makeConformedRRLT(rrAnalysis, sourceRRLT, targetCurrency);
                    // end new code

                    TransformationBundle bundle = new TransformationBundle();
                    bundle.setFinancialPerspective(analysisFinancialPerspective);
                    bundle.setSourceResult(sourceResult);
                    bundle.setRmsAnalysis(rmsAnalysis);
                    bundle.setSourceRapMapping(sourceRapMapping);
                    bundle.setRegionPeril(getRegionPeril(sourceResult));
                    bundle.setRrAnalysis(rrAnalysis);
                    bundle.setSourceRRLT(sourceRRLT);
                    bundle.setInstanceId(instanceId);
                    bundle.setConformedRRLT(conformedRRLT);
                    bundle.setProjectImportLogAnalysisId(projectImportLogA.getProjectImportLogId());
                    transformationPackage.addTransformationBundle(bundle);

                    // finis step 1 LOAD_REGION_PERIL for one analysis in loop for of many analysis
                    Date endDate = new Date();
                    projectImportAssetLogA.setEndDate(endDate);
                    projectImportAssetLogRepository.save(projectImportAssetLogA);
                    log.info("Finish import progress STEP 1 : LOAD_REGION_PERIL for analysis: {}", sourceResultId);

                    // if can come here means InProgress
                    projectImportLogA.setStatus(TrackingStatus.INPROGRESS.toString());
                    projectImportLogRepository.save(projectImportLogA);
                    log.info("Tracking at the end of STEP 1 : LOAD_REGION_PERIL for analysis {}, status {}", sourceResult.getSourceResultId(), projectImportLogA.getStatus(), "continue this tracking");

                }
                mapAnalysisRRAnalysisIds.put(sourceResultId, fpRRAnalysis);
            }
        }


        // TODO out of bundle ????????????????????? map id : sr <--> tracking rrImportingAnalysis
        transformationPackage.setProjectId(project.getProjectId());
        transformationPackage.setRrRepresentationDatasetId(rrRepresentationDataset.getRrRepresentationDatasetId());
        transformationPackage.setProjectImportLogPRId(projectImportLogPR.getProjectImportLogId());
        transformationPackage.setMapAnalysisRRAnalysisIds(mapAnalysisRRAnalysisIds);
        transformationPackage.setMapPortfolioRRPortfolioIds(mapPortfolioRRPortfolioIds);

        // tracking P later
        transformationPackage.setMapAnalysisTrackingRRAnalysisIds(mapAnalysisRRTrackingImportingAnalysisIds);

        projectImportLogPR.setStatus(TrackingStatus.INPROGRESS.toString()); // tracking project must come to the last step
        projectImportLogRepository.save(projectImportLogPR);
        log.info("Tracking at the end of STEP 1 : LOAD_REGION_PERIL for project: {}, status: {}", getProjectId(), "InProgress", "continue this tracking");
        log.debug("loadRegionPerils completed");
        return true;
    }

    ///// FAC //////////

    private RegionPeril getRegionPeril(SourceResult sourceResult) {
        String rpCode = sourceResult.getAnalysisUserRegionPeril();
        if (rpCode == null || rpCode.isEmpty()) {
            rpCode = sourceResult.getAnalysisSystemRegionPeril();
        }
        if (rpCode == null || rpCode.isEmpty()) {
            rpCode = sourceResult.getAnalysisRegionPeril();
        }
        if (rpCode == null) {
            log.debug("rpCode is null");
            return null;
        }
        RegionPeril regionPeril = ttRegionPerilRepository.findByRegionPerilCode(rpCode);
        return regionPeril;
    }

    // new function to replace makeSourceELTHeader
    private RRLossTable makeSourceRRLT(RRAnalysis rrAnalysis, SourceResult sourceResult, Project project,
                                       RmsAnalysis rmsAnalysis, RegionPeril regionPeril, AnalysisFinancialPerspective afp,
                                       Currency currency, RRRepresentationDataset rrRepresentationDataset) {

        if (afp == null) {
            log.debug("no analysis financial perspective found for source result {}", sourceResult.getSourceResultId());
        }

        RRLossTable rrLossTable = new RRLossTable();
        rrLossTable.setProjectId(project.getProjectId());
        rrLossTable.setRrRepresentationDatasetId(rrRepresentationDataset.getRrRepresentationDatasetId());
        rrLossTable.setRrAnalysisId(rrAnalysis.getAnalysisId());
        rrLossTable.setLossTableType("ELT");
//        rrLossTable.setFileType(RRLossTable.FILE_TYPE_BIN); // TODO non c'est RMS
        rrLossTable.setFileDataFormat(FileDATAFormatType.FILE_DATA_FORMAT_TREATY.toString());
        rrLossTable.setOriginalTarget(RRLossTableType.SOURCE.toString());
        rrLossTable.setFinancialPerspective(afp != null ? new RRFinancialPerspective(afp) : null);
        rrLossTable.setCurrency(currency.getCode()); //  source currency
        rrLossTable.setRegionPeril(rrAnalysis.getRegionPeril());
        rrLossTable.setExchangeRate(rrAnalysis.getExchangeRate());
        rrLossTable.setProportion(rrAnalysis.getProportion());
        rrLossTable.setUnitsMultiplier(rrAnalysis.getUnitMultiplier());
        rrLossTable.setUserSelectedGrain(rrAnalysis.getGrain());

        log.info("created RRLossTable {}, Project {}, rmsAnalysis {}, sourceResult {}, afp {}, regionPeril {})", rrLossTable.getRrLossTableId(), project.getProjectId(), rmsAnalysis.getAnalysisId(), sourceResult.getSourceResultId(), afp != null ? afp.getFullCode() : null, regionPeril);
        log.info("sourceResult.getExchangeRate() = {}, rmsAnalysis.getRmsExchangeRate() = {}, sourceResult.getTargetCurrencyBasis() = {}, sourceResult.getTargetCurrency() = {}, sourceResult.getUserSelectedGrain() = {}", sourceResult.getExchangeRate(), rmsAnalysis.getRmsExchangeRate(), sourceResult.getTargetCurrencyBasis(), sourceResult.getTargetCurrency(), sourceResult.getUserSelectedGrain());
        log.info("Final currency = {}", currency.getCode());
        return rrLossTable;
    }

    // function to replace old one
    private RRLossTable makeConformedRRLT(RRAnalysis rrAnalysis, RRLossTable sourceRRLT, Currency currency) {

        RRLossTable conformedRRLT = new RRLossTable();
        //mongoDBSequence.nextSequenceId(conformedRRLT);
        conformedRRLT.setProjectId(project.getProjectId());
        conformedRRLT.setRrRepresentationDatasetId(sourceRRLT.getRrRepresentationDatasetId());
        conformedRRLT.setRrAnalysisId(rrAnalysis.getAnalysisId());
        conformedRRLT.setLossTableType("ELT");
//        conformedRRLT.setFileType(RRLossTable.FILE_TYPE_BIN); // TODO non c'est RMS
        conformedRRLT.setFileDataFormat(FileDATAFormatType.FILE_DATA_FORMAT_TREATY.toString());
        conformedRRLT.setOriginalTarget(RRLossTableType.CONFORMED.toString());
        conformedRRLT.setFinancialPerspective(sourceRRLT.getFinancialPerspective());
        conformedRRLT.setCurrency(currency.getCode()); //  target currency
        conformedRRLT.setRegionPeril(rrAnalysis.getRegionPeril());
        conformedRRLT.setExchangeRate(sourceRRLT.getExchangeRate());
        conformedRRLT.setProportion(sourceRRLT.getProportion());
        conformedRRLT.setUnitsMultiplier(sourceRRLT.getUnitsMultiplier());
        conformedRRLT.setUserSelectedGrain(rrAnalysis.getGrain());
        // TODO fileName, filePath later

        log.info("Conformed ELT Header {}", conformedRRLT.getRrLossTableId());
        return conformedRRLT;
    }

    public ELTData getEltData() {
        return eltData;
    }

    public void setEltData(ELTData eltData) {
        this.eltData = eltData;
    }

    public String getRpListQuery() {
        return rpListQuery;
    }

//    private boolean loadRegionPerilsFAC() {
//        log.info("loading region perils for "+catReqId+" and "+division+","+periodBasis);
//
//        CATObjectGroup catObjectGroup = createCatObjectGroup();
//
//        final ModelResultsDataSource modelResultsDataSource = new ModelResultsDataSource();
//        modelResultsDataSource.setName(ModelResultsDataSourceType.DEFAULT);
//        modelResultsDataSource.setExposureDatabaseName(edm);
//        modelResultsDataSource.setResultsDatabaseName(rdm);
//        modelResultsDataSource.setFacPortfolioNumber(portfolio);
//        final ModellingSystemInstance modellingSystemInstance = new ModellingSystemInstance();
//        modellingSystemInstance.setModellingSystemInstanceId(getInstanceId());
//        modelResultsDataSource.setModellingSystemInstance(modellingSystemInstance);
//        modelResultsDataSource.setFacPortfolioNumberOverride(false);
//        modelResultsDataSource.setFacPortfolioNumberOverrideReason(null);
//        modelResultsDataSource.setFacModellingVersion(0);
//        catObjectGroup.setModelDatasource(modelResultsDataSource);
//
//        List<Map<String, Object>> elts = jdbcTemplate.queryForList(rpListQuery.replaceAll(":rdm:",rdm), portfolio); // TODO - manually choice
//        for (Map<String, Object> elt : elts) {
//            ELTLoss data = new ELTLoss();
//            data.setRegion((String) elt.get("REGION"));
//            data.setPeril((String) elt.get("PERIL"));
//            data.setCurrency((String) elt.get("CURRENCY"));
//            data.setDlmProfileName(((String) elt.get("DLM_PROFILE_NAME")));
//            data.setFinancialPerspective(fpELT);
//            data.setAnalysisId((Integer) elt.get("ANALYSIS_ID"));
//            final String rp = mappingHandler.getRegionPerilForDLMProfile((String) elt.get("DLM_PROFILE_NAME")); // TODO - neglectable, to add anothe banr
//
//            eltData.addLosData(rp, data);
//
//            final ModellingResult result = new ModellingResult();
//            result.setModelRAPSource(mappingHandler.getModelRAPSourceForDLMProfile((String) elt.get("DLM_PROFILE_NAME")));
//            result.setModelRAP(mappingHandler.getModelRAPForDLMProfile((String) elt.get("DLM_PROFILE_NAME")));
//            result.setFinancialPerspectiveStats(catObjectGroup.getFinancialPerspectiveStats());
//            result.setFinancialPerspectiveELT(catObjectGroup.getFinancialPerspectiveELT());
//            result.setIsPartial(false);
//            result.setRegionPeril(result.getModelRAPSource().getRegionPeril());
//            result.setAnalysisCurrency(mappingHandler.getCurrencyForCode((String) elt.get("CURRENCY")));
//            final ELT eltObject = new ELT();
//            eltObject.setModelRAPSource(result.getModelRAPSource());
//            eltObject.setModelRAP(result.getModelRAP());
//            result.setElt(eltObject);
//            result.setDlmFileName(getFileName("ELT", rp, fpELT, (String) elt.get("CURRENCY"), "MODEL", "APS", ".xml"));
//            catObjectGroup.getModellingResultsByRegionPeril().put(rp, result);
//
//            ExecutionContext value = new ExecutionContext();
//            value.putString("name", "Thread_"+rp);
//            value.putString("regionPeril", rp);
//
//        }
//
//        final Map<String, Object> kpInfo = getKPInfo();
//        kpInfo.put("regionPerils", eltData.getRegionPerils());
//        businessKpiService.saveHit(KPIConstants.RR_17_Number_RegionPerils_Extraction, eltData.getRegionPerils().size(), kpInfo);
//        addMessage("ELT EXTRACT", catObjectGroup.getModellingResultsByRegionPeril().size()+" region perils loaded OK");
//
//        return true;
//    }

    public void setRpListQuery(String rpListQuery) {
        this.rpListQuery = rpListQuery;
    }

    public RmsProjectImportConfig getRmsProjectImportConfig() {
        return rmsProjectImportConfig;
    }

    public void setRmsProjectImportConfig(RmsProjectImportConfig rmsProjectImportConfig) {
        this.rmsProjectImportConfig = rmsProjectImportConfig;
    }
}
