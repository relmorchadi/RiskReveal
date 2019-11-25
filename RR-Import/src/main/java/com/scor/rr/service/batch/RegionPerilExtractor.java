package com.scor.rr.service.batch;

import com.scor.rr.domain.CurrencyEntity;
import com.scor.rr.domain.ModelPortfolio;
import com.scor.rr.domain.ProjectImportRunEntity;
import com.scor.rr.domain.enums.RRLossTableType;
import com.scor.rr.domain.enums.TrackingStatus;
import com.scor.rr.domain.model.AnalysisIncludedTargetRAP;
import com.scor.rr.domain.model.LossDataHeader;
import com.scor.rr.domain.reference.RegionPeril;
import com.scor.rr.domain.riskLink.ModellingSystemInstance;
import com.scor.rr.domain.riskLink.RlPortfolioSelection;
import com.scor.rr.domain.riskLink.RlSourceResult;
import com.scor.rr.domain.riskReveal.Project;
import com.scor.rr.domain.riskReveal.RRAnalysis;
import com.scor.rr.repository.*;
import com.scor.rr.service.state.TransformationBundle;
import com.scor.rr.service.state.TransformationPackage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@StepScope
public class RegionPerilExtractor {

    private static final Logger log = LoggerFactory.getLogger(RegionPerilExtractor.class);

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectImportRunRepository projectImportRunRepository;

    @Autowired
    private RlSourceResultRepository rlSourceResultRepository;

    @Autowired
    private RegionPerilRepository regionPerilRepository;

    @Autowired
    private RlModelDataSourceRepository rlModelDataSourceRepository;

    @Autowired
    private ModellingSystemInstanceRepository modellingSystemInstanceRepository;

    @Autowired
    private RranalysisRepository rrAnalysisRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private TransformationPackage transformationPackage;

    @Autowired
    private TargetrapRepository targetRAPRepository;

    @Autowired
    private AnalysisIncludedTargetRAPRepository analysisIncludedTargetRAPRepository;

    @Autowired
    private RLPortfolioSelectionRepository rlPortfolioSelectionRepository;

    @Autowired
    private ModelPortfolioRepository modelPortfolioRepository;

    @Value("#{jobParameters['projectId']}")
    private Long projectId;

    @Value("#{jobParameters['sourceResultIdsInput']}")
    private String sourceResultIdsInput;

    @Value("#{jobParameters['rlPortfolioSelectionIds']}")
    private String rlPortfolioSelectionIds;

    @Value("#{jobParameters['instanceId']}")
    private String instanceId;

    public void loadRegionPerilAndCreateRRAnalysisAndRRLossTableHeader() {
        log.debug("Start loading region perils");
        Optional<Project> projectOptional = projectRepository.findById(projectId);
        Project project;

        if (projectOptional.isPresent())
            project = projectOptional.get();
        else {
            log.debug("project not found");
            throw new IllegalArgumentException("invalid project id");
        }

        // build ProjectImportRun ----------------------------------------------------------------------------------
        List<ProjectImportRunEntity> projectImportRunEntityList = projectImportRunRepository.findByProjectId(project.getProjectId());
        ProjectImportRunEntity projectImportRunEntity = new ProjectImportRunEntity();
        projectImportRunEntity.setProjectId(project.getProjectId());
        projectImportRunEntity.setRunId(projectImportRunEntityList == null ? 1 : projectImportRunEntityList.size() + 1);
        projectImportRunEntity.setStatus(TrackingStatus.INPROGRESS.toString());
        projectImportRunEntity.setStartDate(new Date());
        projectImportRunEntity.setImportedBy(project.getAssignedTo());
        projectImportRunEntity.setSourceConfigVendor("RL");
        projectImportRunEntity = projectImportRunRepository.save(projectImportRunEntity);


        // build RRAnalysis ------------------------------------------------------------
        Map<String, Map<String, Long>> mapAnalysisRRAnalysisIds = new HashMap<>();
        Map<String, Long> fpRRAnalysis = new HashMap<>();
        List<ModelPortfolio> modelPortfolios = new ArrayList<>();

        if (sourceResultIdsInput != null) {
            String[] sourceResultIds = sourceResultIdsInput.split(";");

            log.debug(">>>> Creation of RRAnalysis");
            for (String sourceResultId : sourceResultIds) {

                RlSourceResult sourceResult;

                if (StringUtils.isNumeric(sourceResultId)) {
                    Optional<RlSourceResult> sourceResultOptional = rlSourceResultRepository.findById(Long.valueOf(sourceResultId));
                    if (sourceResultOptional.isPresent()) {
                        sourceResult = sourceResultOptional.get();
                    } else {
                        log.debug(">>>> Source Result was not found");
                        continue;
                    }
                } else {
                    log.debug(">>>> Source Result id is not numeric");
                    continue;
                }

                RRAnalysis rrAnalysis = new RRAnalysis();

                rrAnalysis.setRegion(sourceResult.getRlAnalysis().getRegion());
                rrAnalysis.setSubPeril(sourceResult.getRlAnalysis().getSubPeril());
                rrAnalysis.setProfileName(sourceResult.getRlAnalysis().getProfileName());
                rrAnalysis.setGrain(sourceResult.getUserSelectedGrain() != null ? sourceResult.getUserSelectedGrain() : sourceResult.getRlAnalysis().getDefaultGrain());
                rrAnalysis.setGeoCode(sourceResult.getRlAnalysis().getGeoCode());
                rrAnalysis.setProxyScalingBasis(sourceResult.getProxyScalingBasis());
                rrAnalysis.setProxyScalingNarrative(sourceResult.getProxyScalingNarrative());
                rrAnalysis.setMultiplierBasis(sourceResult.getMultiplierBasis());
                rrAnalysis.setMultiplierNarrative(sourceResult.getMultiplierNarrative());
                rrAnalysis.setDescription(sourceResult.getRlAnalysis().getDescription());

                // TODO : Not sure ?
                rrAnalysis.setOverrideReasonText(sourceResult.getOverrideRegionPerilBasis());

                rrAnalysis.setDefaultOccurrenceBasis(sourceResult.getRlAnalysis().getDefaultOccurrenceBasis());
                rrAnalysis.setTargetCurrency(sourceResult.getTargetCurrency());
                rrAnalysis.setProjectId(project.getProjectId());
                rrAnalysis.setCreationDate(new Date());
                rrAnalysis.setRunDate(sourceResult.getRlAnalysis().getRunDate());
                rrAnalysis.setImportStatus(TrackingStatus.INPROGRESS.toString());
                rrAnalysis.setProjectImportRunId(projectImportRunEntity.getProjectImportRunId());

                TransformationBundle bundle = new TransformationBundle();
                bundle.setInstanceId(instanceId);

                rlModelDataSourceRepository.findById(sourceResult.getRlAnalysis().getRlModelDataSourceId()).ifPresent(rlModelDataSourceItem -> {
                    bundle.setInstanceId(rlModelDataSourceItem.getInstanceId());
                    rrAnalysis.setSourceModellingSystemInstance(rlModelDataSourceItem.getInstanceName());
                    modellingSystemInstanceRepository.findById(rlModelDataSourceItem.getInstanceId()).ifPresent(modellingSystemInstance -> {
                        rrAnalysis.setSourceModellingVendor(modellingSystemInstance.getModellingSystemVersion().getModellingSystem().getVendor().getName());
                        rrAnalysis.setSourceModellingSystem(modellingSystemInstance.getModellingSystemVersion().getModellingSystem().getName());
                        rrAnalysis.setSourceModellingSystemVersion(modellingSystemInstance.getModellingSystemVersion().getModellingSystemVersion().toString());
                    });
                });

                rrAnalysis.setDataSourceId(sourceResult.getRlAnalysis().getRdmId());
                rrAnalysis.setDataSourceName(sourceResult.getRlAnalysis().getRdmName());
                rrAnalysis.setAnalysisId(sourceResult.getRlAnalysis().getAnalysisId());
                rrAnalysis.setAnalysisName(sourceResult.getRlAnalysis().getAnalysisName());
                rrAnalysis.setFinancialPerspective(sourceResult.getFinancialPerspective());

                // TODO : get from SourceEpHeader
//                if ("TY".equals(rrAnalysis.getFinancialPerspective())) {
//                    rrAnalysis.setTreatyLabel(analysisFinancialPerspective.getTreatyLabel());
//                    rrAnalysis.setTreatyTag(analysisFinancialPerspective.getTreatyTag());
//                }
                RegionPeril regionPeril = getRegionPeril(sourceResult);
                rrAnalysis.setPeril(sourceResult.getRlAnalysis().getPeril());
                rrAnalysis.setRegionPeril(regionPeril != null ? regionPeril.getRegionPerilCode() : null);
                rrAnalysis.setSourceCurrency(sourceResult.getRlAnalysis().getAnalysisCurrency());
                rrAnalysis.setTargetCurrency(sourceResult.getTargetCurrency());
                rrAnalysis.setExchangeRate(sourceResult.getRlAnalysis().getRlExchangeRate() != null ? sourceResult.getRlAnalysis().getRlExchangeRate().doubleValue() : 1.0);
                rrAnalysis.setUserOccurrenceBasis(sourceResult.getOccurrenceBasis());
                rrAnalysis.setProportion(sourceResult.getProportion() != null ? sourceResult.getProportion().doubleValue() : 100.0);
                rrAnalysis.setUnitMultiplier(sourceResult.getUnitMultiplier() != null ? sourceResult.getUnitMultiplier().doubleValue() : 1.0);
                rrAnalysis.setProfileKey(sourceResult.getRlAnalysis().getProfileKey());
                rrAnalysis.setAnalysisLevel(sourceResult.getRlAnalysis().getAnalysisType());
                rrAnalysis.setLossAmplification(sourceResult.getRlAnalysis().getLossAmplification());
                rrAnalysis.setModel(sourceResult.getRlAnalysis().getEngineType());

                RRAnalysis rrAnalysisLambda = rrAnalysisRepository.saveAndFlush(rrAnalysis);

                targetRAPRepository.findByTargetRAPCode(sourceResult.getTargetRAPCode()).ifPresent(targetRAP -> {
                    AnalysisIncludedTargetRAP analysisIncludedTargetRAP = new AnalysisIncludedTargetRAP();
                    analysisIncludedTargetRAP.setTargetRAPId(targetRAP.getTargetRAPId());
                    analysisIncludedTargetRAP.setModelAnalysisId(rrAnalysisLambda.getRrAnalysisId());
                    analysisIncludedTargetRAPRepository.save(analysisIncludedTargetRAP);
                });

                fpRRAnalysis.put(sourceResult.getFinancialPerspective(), rrAnalysis.getRrAnalysisId());

                CurrencyEntity analysisCurrencyEntity;
                if (sourceResult.getRlAnalysis().getAnalysisCurrency() != null) {
                    analysisCurrencyEntity = currencyRepository.findByCode(sourceResult.getRlAnalysis().getAnalysisCurrency());
                } else {
                    log.debug("Source currency is null, use USD as source currency");
                    analysisCurrencyEntity = currencyRepository.findByCode("USD");
                }

                LossDataHeader sourceRRLT = makeSourceRRLT(rrAnalysis, sourceResult, sourceResult.getFinancialPerspective(), analysisCurrencyEntity);

                CurrencyEntity targetCurrencyEntity;
                if (sourceResult.getTargetCurrency() != null) {
                    targetCurrencyEntity = currencyRepository.findByCode(sourceResult.getTargetCurrency());
                } else {
                    log.debug("Target currency is null, use USD as target currency");
                    targetCurrencyEntity = currencyRepository.findByCode("USD");
                }

                LossDataHeader conformedRRLT = makeConformedRRLT(rrAnalysis, sourceRRLT, targetCurrencyEntity);


                // TODO :  Review Later with viet
//                bundle.setSourceRapMapping(sourceRapMapping);
                bundle.setFinancialPerspective(sourceResult.getFinancialPerspective());
                bundle.setSourceResult(sourceResult);
                bundle.setRlAnalysis(sourceResult.getRlAnalysis());
                bundle.setRegionPeril(getRegionPeril(sourceResult));
                bundle.setRrAnalysis(rrAnalysis);
                bundle.setSourceRRLT(sourceRRLT);
                bundle.setConformedRRLT(conformedRRLT);
                transformationPackage.addTransformationBundle(bundle);

                log.info("Finish import progress STEP 1 : LOAD_REGION_PERIL for analysis: {}", sourceResultId);

                mapAnalysisRRAnalysisIds.put(sourceResultId, fpRRAnalysis);
            }

            transformationPackage.setMapAnalysisRRAnalysisIds(mapAnalysisRRAnalysisIds);

            log.info(">>>> Step 1 : load region peril completed");
        } else {
            log.warn(">>>> no source result ids were received");
        }

        if (rlPortfolioSelectionIds != null) {
            String[] portfolioSelectionIds = rlPortfolioSelectionIds.split(";");

            for (String portfolioSelectionId : portfolioSelectionIds) {
                RlPortfolioSelection rlPortfolioSelection;

                if (StringUtils.isNumeric(portfolioSelectionId)) {
                    Optional<RlPortfolioSelection> rlPortfolioSelectionOptional = rlPortfolioSelectionRepository.findById(Long.valueOf(portfolioSelectionId));
                    if (rlPortfolioSelectionOptional.isPresent()) {
                        rlPortfolioSelection = rlPortfolioSelectionOptional.get();
                    } else {
                        log.debug(">>>> Portfolio Selection was not found");
                        continue;
                    }
                } else {
                    log.debug(">>>> Portfolio Selection id is not numeric");
                    continue;
                }


                ModellingSystemInstance modellingSystemInstance =
                        modellingSystemInstanceRepository.findById(rlPortfolioSelection.getRlPortfolio().getRlModelDataSource().getInstanceId()).get();


                ModelPortfolio modelPortfolio = new ModelPortfolio(null, rlPortfolioSelection.getProjectId(),
                        new Date(), new Date(), TrackingStatus.INPROGRESS.toString(), projectImportRunEntity.getProjectImportRunId(),
                        rlPortfolioSelection.getRlPortfolio().getRlModelDataSource().getInstanceName(),
                        modellingSystemInstance.getModellingSystemVersion().getModellingSystem().getVendor().getName(),
                        modellingSystemInstance.getModellingSystemVersion().getModellingSystem().getName(),
                        modellingSystemInstance.getModellingSystemVersion().getModellingSystemVersion(),
                        rlPortfolioSelection.getRlPortfolio().getEdmId(),
                        rlPortfolioSelection.getRlPortfolio().getEdmName(),
                        rlPortfolioSelection.getRlPortfolio().getPortfolioId(),
                        rlPortfolioSelection.getRlPortfolio().getName(),
                        rlPortfolioSelection.getRlPortfolio().getType(),
                        "ALL",
                        rlPortfolioSelection.getTargetCurrency() != null ? rlPortfolioSelection.getTargetCurrency() : rlPortfolioSelection.getRlPortfolio().getAgCurrency(),
                        1.0d,
                        rlPortfolioSelection.getProportion() != null ? rlPortfolioSelection.getProportion() : 1.0d,
                        rlPortfolioSelection.getUnitMultiplier() != null ? rlPortfolioSelection.getUnitMultiplier() : 1.0d,
                        rlPortfolioSelection.getRlPortfolio().getDescription(),
                        rlPortfolioSelection.getRlPortfolio().getType(),
                        rlPortfolioSelection.isImportLocationLevel()
                );

                modelPortfolios.add(modelPortfolioRepository.save(modelPortfolio));
            }
            transformationPackage.setModelPortfolios(modelPortfolios);
        }
    }

    private RegionPeril getRegionPeril(RlSourceResult sourceResult) {
        String rpCode = sourceResult.getTargetRegionPeril();
        if (rpCode == null || rpCode.isEmpty()) {
            rpCode = sourceResult.getRlAnalysis().getRpCode();
        }
        if (rpCode == null) {
            log.debug("rpCode is null");
            return null;
        }
        return regionPerilRepository.findByRegionPerilCode(rpCode);
    }

    private LossDataHeader makeSourceRRLT(RRAnalysis rrAnalysis, RlSourceResult sourceResult, String financialPerspective, CurrencyEntity analysisCurrencyEntity) {

        if (financialPerspective == null) {
            log.debug("no analysis financial perspective found for source result {}", sourceResult.getRlSourceResultId());
        }

        LossDataHeader rrLossTable = new LossDataHeader();
//        rrLossTable.setRrRepresentationDatasetId(rrRepresentationDataset.getId());
//        rrLossTable.setFileType("bin");
        rrLossTable.setModelAnalysisId(rrAnalysis.getRrAnalysisId());
        rrLossTable.setCreatedDate(new Date());
        rrLossTable.setLossTableType("ELT");
        rrLossTable.setFileDataFormat("Treaty");
        rrLossTable.setOriginalTarget(RRLossTableType.SOURCE.getCode());
        rrLossTable.setCurrency(analysisCurrencyEntity.getCode()); // Source Currency

        log.info("created source RRLossTable");
        return rrLossTable;
    }

    private LossDataHeader makeConformedRRLT(RRAnalysis rrAnalysis, LossDataHeader sourceRRLT, CurrencyEntity currencyEntity) {

        LossDataHeader conformedRRLT = new LossDataHeader();
//        conformedRRLT.setRrRepresentationDatasetId(sourceRRLT.getRrRepresentationDatasetId());
//        conformedRRLT.setFileType(RRLossTable.FILE_TYPE_BIN); // TODO non c'est RMS
        conformedRRLT.setModelAnalysisId(rrAnalysis.getRrAnalysisId());
        conformedRRLT.setLossTableType("ELT");
        conformedRRLT.setFileDataFormat("Treaty");
        conformedRRLT.setOriginalTarget(RRLossTableType.CONFORMED.getCode());
        conformedRRLT.setCurrency(currencyEntity.getCode()); //  target currency
        // TODO fileName, filePath later

        log.info("Conformed ELT Header {}", conformedRRLT.getLossDataHeaderId());
        return conformedRRLT;
    }
}
