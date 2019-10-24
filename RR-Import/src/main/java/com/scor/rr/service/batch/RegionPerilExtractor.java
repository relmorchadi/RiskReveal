package com.scor.rr.service.batch;

import com.scor.rr.domain.ProjectImportRun;
import com.scor.rr.domain.enums.RRLossTableType;
import com.scor.rr.domain.enums.TrackingStatus;
import com.scor.rr.domain.reference.Currency;
import com.scor.rr.domain.reference.RegionPeril;
import com.scor.rr.domain.riskLink.RlSourceResult;
import com.scor.rr.domain.riskReveal.Project;
import com.scor.rr.domain.riskReveal.RRAnalysis;
import com.scor.rr.domain.riskReveal.RRLossTableHeader;
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
    private RRAnalysisRepository rrAnalysisRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private TransformationPackage transformationPackage;

    @Value("${jobParameters[projectId]}")
    private Long projectId;

    @Value("${jobParameters[sourceResultIdsInput]}")
    private String sourceResultIdsInput;

    @Value("${jobParameters[instanceId]}")
    private String instanceId;

    public void loadRegionPerilAndCreateRRAnalysisAndRRLoassTableHeader() {
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
        List<ProjectImportRun> projectImportRunList = projectImportRunRepository.findByProject(project);
        ProjectImportRun projectImportRun = new ProjectImportRun();
        projectImportRun.setProject(project);
        projectImportRun.setRunId(projectImportRunList == null ? 1 : projectImportRunList.size() + 1);
        projectImportRun.setStatus(TrackingStatus.INPROGRESS);
        projectImportRun.setStartDate(new Date());
        projectImportRun.setImportedBy(project.getAssignedTo());
        projectImportRun.setSourceConfigVendor("RL");
        projectImportRun = projectImportRunRepository.save(projectImportRun);


        // build RRAnalysis ------------------------------------------------------------
        Map<String, Map<String, Long>> mapAnalysisRRAnalysisIds = new HashMap<>();
        Map<String, Long> fpRRAnalysis = new HashMap<>();

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
                //rrAnalysis.setRegion(sourceResult.getRlAnalysis().getRegion());
                //rrAnalysis.setSourceResultsReference(sourceResult.getModelingResultDataSource().getModelingResultDataSourceId());
                //rrAnalysis.setSubPeril(sourceResult.getRlAnalysis().getSubPeril());
                //rrAnalysis.setProfileName(sourceResult.getRlAnalysis().getProfileName());
                //rrAnalysis.setIncludedTargetRapIds(sourceResult.getIncludedTargetRapIds());/
                //sourceResult.setImportStatus(rrAnalysis.getImportStatus());
                //rrAnalysis.setGrain(sourceResult.getUserSelectedGrain() != null ? sourceResult.getUserSelectedGrain() : sourceResult.getRlAnalysis().getDefaultGrain());
                //rrAnalysis.setGeoCode(sourceResult.getRlAnalysis().getGeoCode());
                //rrAnalysis.setProxyScalingBasis(sourceResult.getProxyScalingBasis());
                //rrAnalysis.setProxyScalingNarrative(sourceResult.getProxyScalingNarrative());
                //rrAnalysis.setDescription(sourceResult.getRlAnalysis().getDescription());
                //rrAnalysis.setMultiplierBasis(sourceResult.getMultiplierBasis());
                //rrAnalysis.setMultiplierNarrative(sourceResult.getMultiplierNarrative());
                //rrAnalysis.setTags(sourceResult.getTags());
                //rrAnalysis.setUserNotes(sourceResult.getNotes());
                //rrAnalysis.setOverrideReasonText(sourceResult.getOverrideReasonText());
                //rrAnalysis.setDefaultOccurrenceBasis(analysisFinancialPerspective.getDefaultOccurrenceBasis());
                rrAnalysis.setTargetCurrency(sourceResult.getTargetCurrency());
                rrAnalysis.setProjectId(project.getProjectId());
                rrAnalysis.setCreationDate(new Date());
                rrAnalysis.setRunDate(sourceResult.getRlAnalysis().getRunDate());
                rrAnalysis.setImportStatus(TrackingStatus.INPROGRESS.toString());
                rrAnalysis.setProjectImportRunId(projectImportRun.getProjectImportRunId());

                rlModelDataSourceRepository.findById(sourceResult.getRlAnalysis().getRlModelDataSourceId()).ifPresent(rlModelDataSource -> {
                    rrAnalysis.setSourceModellingSystemInstance(rlModelDataSource.getInstanceName());
//                    ModellingSystemInstance modellingSystemInstance = modellingSystemInstanceRepository.findById(rlModelDataSource.getInstanceId()).get();
//                    rrAnalysis.setSourceModellingVendor(modellingSystemInstance.getModellingSystemVersion().getModellingSystem().getVendor().getName());
//                    rrAnalysis.setSourceModellingSystem(modellingSystemInstance.getModellingSystemVersion().getModellingSystem().getName());
//                    rrAnalysis.setSourceModellingSystemVersion(modellingSystemInstance.getModellingSystemVersion().getModellingSystemVersion().toString());
                });

                rrAnalysis.setDataSourceId(sourceResult.getRlAnalysis().getRdmId());
                rrAnalysis.setDataSourceName(sourceResult.getRlAnalysis().getRdmName());
                rrAnalysis.setAnalysisId(sourceResult.getRlAnalysis().getAnalysisId());
                rrAnalysis.setAnalysisName(sourceResult.getRlAnalysis().getAnalysisName());
                rrAnalysis.setFinancialPerspective(sourceResult.getFinancialPerspective());
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

                rrAnalysisRepository.save(rrAnalysis);
                fpRRAnalysis.put(sourceResult.getFinancialPerspective(), rrAnalysis.getRrAnalysisId());

                Currency analysisCurrency;
                if (sourceResult.getRlAnalysis().getAnalysisCurrency() != null) {
                    analysisCurrency = currencyRepository.findByCode(sourceResult.getRlAnalysis().getAnalysisCurrency());
                } else {
                    log.debug("Source currency is null, use USD as source currency");
                    analysisCurrency = currencyRepository.findByCode("USD");
                }

                RRLossTableHeader sourceRRLT = makeSourceRRLT(rrAnalysis, sourceResult, sourceResult.getFinancialPerspective(), analysisCurrency);

                Currency targetCurrency;
                if (sourceResult.getTargetCurrency() != null) {
                    targetCurrency = currencyRepository.findByCode(sourceResult.getTargetCurrency());
                } else {
                    log.debug("Target currency is null, use USD as target currency");
                    targetCurrency = currencyRepository.findByCode("USD");
                }

                RRLossTableHeader conformedRRLT = makeConformedRRLT(rrAnalysis, sourceRRLT, targetCurrency);

                TransformationBundle bundle = new TransformationBundle();
                // TODO :  Review Later with viet
//                bundle.setSourceRapMapping(sourceRapMapping);
                bundle.setFinancialPerspective(sourceResult.getFinancialPerspective());
                bundle.setSourceResult(sourceResult);
                bundle.setRmsAnalysis(sourceResult.getRlAnalysis());
                bundle.setRegionPeril(getRegionPeril(sourceResult));
                bundle.setRrAnalysis(rrAnalysis);
                bundle.setSourceRRLT(sourceRRLT);
                bundle.setInstanceId(instanceId);
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

    private RRLossTableHeader makeSourceRRLT(RRAnalysis rrAnalysis, RlSourceResult sourceResult, String financialPerspective, Currency analysisCurrency) {

        if (financialPerspective == null) {
            log.debug("no analysis financial perspective found for source result {}", sourceResult.getRlSourceResultId());
        }

        RRLossTableHeader rrLossTable = new RRLossTableHeader();
//        rrLossTable.setProjectId(getProjectId());
//        rrLossTable.setRrRepresentationDatasetId(rrRepresentationDataset.getId());
//        rrLossTable.setFinancialPerspective(afp != null ? new RRFinancialPerspective(afp) : null);
//        rrLossTable.setUserSelectedGrain(rrAnalysis.getGrain());
//        rrLossTable.setFileType("bin");
        rrLossTable.setRrAnalysis(rrAnalysis);
        rrLossTable.setCreatedDate(new Date());
        rrLossTable.setLossTableType("ELT");
        rrLossTable.setFileDataFormat("Treaty");
        rrLossTable.setOriginalTarget(RRLossTableType.SOURCE.getCode());
        rrLossTable.setCurrency(analysisCurrency.getCode()); // Source Currency

        log.info("created source RRLossTable");
        return rrLossTable;
    }

    private RRLossTableHeader makeConformedRRLT(RRAnalysis rrAnalysis, RRLossTableHeader sourceRRLT, Currency currency) {

        RRLossTableHeader conformedRRLT = new RRLossTableHeader();
//        conformedRRLT.setRrRepresentationDatasetId(sourceRRLT.getRrRepresentationDatasetId());
//        conformedRRLT.setFinancialPerspective(sourceRRLT.getFinancialPerspective());
//        conformedRRLT.setFileType(RRLossTable.FILE_TYPE_BIN); // TODO non c'est RMS
//        conformedRRLT.setUserSelectedGrain(rrAnalysis.getGrain());
        conformedRRLT.setRrAnalysis(rrAnalysis);
        conformedRRLT.setLossTableType("ELT");
        conformedRRLT.setFileDataFormat("Treaty");
        conformedRRLT.setOriginalTarget(RRLossTableType.CONFORMED.getCode());
        conformedRRLT.setCurrency(currency.getCode()); //  target currency
        // TODO fileName, filePath later

        log.info("Conformed ELT Header {}", conformedRRLT.getRrLossTableHeaderId());
        return conformedRRLT;
    }
}
