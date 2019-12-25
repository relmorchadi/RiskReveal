package com.scor.rr.service.batch;

import com.scor.rr.domain.*;
import com.scor.rr.domain.ModelPortfolioEntity;
import com.scor.rr.domain.enums.RRLossTableType;
import com.scor.rr.domain.enums.TrackingStatus;
import com.scor.rr.domain.riskLink.RLPortfolioSelection;
import com.scor.rr.domain.riskLink.RLImportSelection;
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
    private ProjectEntityRepository projectEntityRepository;

    @Autowired
    private ProjectImportRunRepository projectImportRunRepository;

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
        Optional<ProjectEntity> projectOptional = projectEntityRepository.findById(projectId);
        ProjectEntity projectEntity;

        if (projectOptional.isPresent())
            projectEntity = projectOptional.get();
        else {
            log.debug("project not found");
            throw new IllegalArgumentException("invalid project id");
        }

        // build ProjectImportRun ----------------------------------------------------------------------------------
        List<ProjectImportRunEntity> projectImportRunEntityList = projectImportRunRepository.findByProjectId(projectEntity.getProjectId());
        ProjectImportRunEntity projectImportRunEntity = new ProjectImportRunEntity();
        projectImportRunEntity.setProjectId(projectEntity.getProjectId());
        projectImportRunEntity.setRunId(projectImportRunEntityList == null ? 1 : projectImportRunEntityList.size() + 1);
        projectImportRunEntity.setStatus(TrackingStatus.INPROGRESS.toString());
        projectImportRunEntity.setStartDate(new Date());
        projectImportRunEntity.setImportedBy(projectEntity.getAssignedTo());
        projectImportRunEntity.setSourceConfigVendor("RL");
        projectImportRunEntity = projectImportRunRepository.save(projectImportRunEntity);


        // build RRAnalysis ------------------------------------------------------------
        Map<String, Map<String, Long>> mapAnalysisRRAnalysisIds = new HashMap<>();
        Map<String, Long> fpRRAnalysis = new HashMap<>();
        List<ModelPortfolioEntity> modelPortfolios = new ArrayList<>();

        if (sourceResultIdsInput != null) {
            String[] sourceResultIds = sourceResultIdsInput.split(";");

            log.debug(">>>> Creation of RRAnalysis");
            for (String sourceResultId : sourceResultIds) {

                RLImportSelection sourceResult;

                if (StringUtils.isNumeric(sourceResultId)) {
                    Optional<RLImportSelection> sourceResultOptional = rlSourceResultRepository.findById(Long.valueOf(sourceResultId));
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

                ModelAnalysisEntity modelAnalysisEntity = new ModelAnalysisEntity();

                modelAnalysisEntity.setRegion(sourceResult.getRlAnalysis().getRegion());
                modelAnalysisEntity.setSubPeril(sourceResult.getRlAnalysis().getSubPeril());
                modelAnalysisEntity.setProfileName(sourceResult.getRlAnalysis().getProfileName());
                modelAnalysisEntity.setGrain(sourceResult.getUserSelectedGrain() != null ? sourceResult.getUserSelectedGrain() : sourceResult.getRlAnalysis().getDefaultGrain());
                modelAnalysisEntity.setGeoCode(sourceResult.getRlAnalysis().getGeoCode());
                modelAnalysisEntity.setProxyScalingBasis(sourceResult.getProxyScalingBasis());
                modelAnalysisEntity.setProxyScalingNarrative(sourceResult.getProxyScalingNarrative());
                modelAnalysisEntity.setMultiplierBasis(sourceResult.getMultiplierBasis());
                modelAnalysisEntity.setMultiplierNarrative(sourceResult.getMultiplierNarrative());
                modelAnalysisEntity.setDescription(sourceResult.getRlAnalysis().getDescription());

                // TODO : Not sure ?
                modelAnalysisEntity.setOverrideReasonText(sourceResult.getOverrideRegionPerilBasis());

                modelAnalysisEntity.setDefaultOccurrenceBasis(sourceResult.getRlAnalysis().getDefaultOccurrenceBasis());
                modelAnalysisEntity.setTargetCurrency(sourceResult.getTargetCurrency());
                modelAnalysisEntity.setProjectId(projectEntity.getProjectId());
                modelAnalysisEntity.setCreationDate(new Date());
                modelAnalysisEntity.setRunDate(sourceResult.getRlAnalysis().getRunDate());
                modelAnalysisEntity.setImportStatus(TrackingStatus.INPROGRESS.toString());
                modelAnalysisEntity.setProjectImportRunId(projectImportRunEntity.getProjectImportRunId());

                TransformationBundle bundle = new TransformationBundle();
                bundle.setInstanceId(instanceId);

                rlModelDataSourceRepository.findById(sourceResult.getRlAnalysis().getRlModelDataSourceId()).ifPresent(rlModelDataSourceItem -> {
                    bundle.setInstanceId(rlModelDataSourceItem.getInstanceId());
                    modelAnalysisEntity.setSourceModellingSystemInstance(rlModelDataSourceItem.getInstanceName());
                    modellingSystemInstanceRepository.findById(rlModelDataSourceItem.getInstanceId()).ifPresent(modellingSystemInstance -> {
                        modelAnalysisEntity.setSourceModellingVendor(modellingSystemInstance.getModellingSystemVersion().getModellingSystem().getVendor().getName());
                        modelAnalysisEntity.setSourceModellingSystem(modellingSystemInstance.getModellingSystemVersion().getModellingSystem().getName());
                        modelAnalysisEntity.setSourceModellingSystemVersion(modellingSystemInstance.getModellingSystemVersion().getModellingSystemVersion().toString());
                    });
                });

                modelAnalysisEntity.setDataSourceId(sourceResult.getRlAnalysis().getRdmId());
                modelAnalysisEntity.setDataSourceName(sourceResult.getRlAnalysis().getRdmName());
                modelAnalysisEntity.setAnalysisId(sourceResult.getRlAnalysis().getRlId());
                modelAnalysisEntity.setAnalysisName(sourceResult.getRlAnalysis().getAnalysisName());
                modelAnalysisEntity.setFinancialPerspective(sourceResult.getFinancialPerspective());

                // TODO : get from SourceEpHeader
//                if ("TY".equals(rrAnalysis.getFinancialPerspective())) {
//                    rrAnalysis.setTreatyLabel(analysisFinancialPerspective.getTreatyLabel());
//                    rrAnalysis.setTreatyTag(analysisFinancialPerspective.getTreatyTag());
//                }
                RegionPerilEntity regionPeril = getRegionPeril(sourceResult);
                modelAnalysisEntity.setPeril(sourceResult.getRlAnalysis().getPeril());
                modelAnalysisEntity.setRegionPeril(regionPeril != null ? regionPeril.getRegionPerilCode() : null);
                modelAnalysisEntity.setSourceCurrency(sourceResult.getRlAnalysis().getAnalysisCurrency());
                modelAnalysisEntity.setTargetCurrency(sourceResult.getTargetCurrency());
                modelAnalysisEntity.setExchangeRate(sourceResult.getRlAnalysis().getRlExchangeRate() != null ? sourceResult.getRlAnalysis().getRlExchangeRate().doubleValue() : 1.0);
                modelAnalysisEntity.setUserOccurrenceBasis(sourceResult.getOccurrenceBasis());
                modelAnalysisEntity.setProportion(sourceResult.getProportion() != null ? sourceResult.getProportion().doubleValue() : 100.0);
                modelAnalysisEntity.setUnitMultiplier(sourceResult.getUnitMultiplier() != null ? sourceResult.getUnitMultiplier().doubleValue() : 1.0);
                modelAnalysisEntity.setProfileKey(sourceResult.getRlAnalysis().getProfileKey());
                modelAnalysisEntity.setAnalysisLevel(sourceResult.getRlAnalysis().getAnalysisType());
                modelAnalysisEntity.setLossAmplification(sourceResult.getRlAnalysis().getLossAmplification());
                modelAnalysisEntity.setModel(sourceResult.getRlAnalysis().getEngineType());

                ModelAnalysisEntity modelAnalysisEntityLambda = rrAnalysisRepository.saveAndFlush(modelAnalysisEntity);

                sourceResult.getTargetRaps().forEach(targetRAPSelection -> {
                    targetRAPRepository.findByTargetRAPCode(targetRAPSelection.getTargetRAPCode()).ifPresent(targetRAP -> {
                        AnalysisIncludedTargetRAPEntity analysisIncludedTargetRAPEntity = new AnalysisIncludedTargetRAPEntity();
                        analysisIncludedTargetRAPEntity.setTargetRAPId(targetRAP.getTargetRAPId());
                        analysisIncludedTargetRAPEntity.setModelAnalysisId(modelAnalysisEntityLambda.getRrAnalysisId());
                        analysisIncludedTargetRAPRepository.save(analysisIncludedTargetRAPEntity);
                    });
                });

                fpRRAnalysis.put(sourceResult.getFinancialPerspective(), modelAnalysisEntity.getRrAnalysisId());

                CurrencyEntity analysisCurrencyEntity;
                if (sourceResult.getRlAnalysis().getAnalysisCurrency() != null) {
                    analysisCurrencyEntity = currencyRepository.findByCode(sourceResult.getRlAnalysis().getAnalysisCurrency());
                } else {
                    log.debug("Source currency is null, use USD as source currency");
                    analysisCurrencyEntity = currencyRepository.findByCode("USD");
                }

                LossDataHeaderEntity sourceRRLT = makeSourceRRLT(modelAnalysisEntity, sourceResult, sourceResult.getFinancialPerspective(), analysisCurrencyEntity);

                CurrencyEntity targetCurrencyEntity;
                if (sourceResult.getTargetCurrency() != null) {
                    targetCurrencyEntity = currencyRepository.findByCode(sourceResult.getTargetCurrency());
                } else {
                    log.debug("Target currency is null, use USD as target currency");
                    targetCurrencyEntity = currencyRepository.findByCode("USD");
                }

                LossDataHeaderEntity conformedRRLT = makeConformedRRLT(modelAnalysisEntity, sourceRRLT, targetCurrencyEntity);


                // TODO :  Review Later with viet
//                bundle.setSourceRapMapping(sourceRapMapping);
                bundle.setFinancialPerspective(sourceResult.getFinancialPerspective());
                bundle.setSourceResult(sourceResult);
                bundle.setRlAnalysis(sourceResult.getRlAnalysis());
                bundle.setRegionPeril(getRegionPeril(sourceResult));
                bundle.setModelAnalysis(modelAnalysisEntity);
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
                RLPortfolioSelection rlPortfolioSelection;

                if (StringUtils.isNumeric(portfolioSelectionId)) {
                    Optional<RLPortfolioSelection> rlPortfolioSelectionOptional = rlPortfolioSelectionRepository.findById(Long.valueOf(portfolioSelectionId));
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


                ModellingSystemInstanceEntity modellingSystemInstance =
                        modellingSystemInstanceRepository.findById(rlPortfolioSelection.getRlPortfolio().getRlModelDataSource().getInstanceId()).get();


                ModelPortfolioEntity modelPortfolio = new ModelPortfolioEntity(null,1, rlPortfolioSelection.getProjectId(),
                        new Date(), new Date(), TrackingStatus.INPROGRESS.toString(), projectImportRunEntity.getProjectImportRunId(),
                        rlPortfolioSelection.getRlPortfolio().getRlModelDataSource().getInstanceName(),
                        modellingSystemInstance.getModellingSystemVersion().getModellingSystem().getVendor().getName(),
                        modellingSystemInstance.getModellingSystemVersion().getModellingSystem().getName(),
                        modellingSystemInstance.getModellingSystemVersion().getModellingSystemVersion(),
                        rlPortfolioSelection.getRlPortfolio().getEdmId(),
                        rlPortfolioSelection.getRlPortfolio().getEdmName(),
                        rlPortfolioSelection.getRlPortfolio().getRlId(),
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

    private RegionPerilEntity getRegionPeril(RLImportSelection sourceResult) {
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
}
