package com.scor.rr.service.batch;

import com.scor.rr.domain.*;
import com.scor.rr.domain.enums.StepStatus;
import com.scor.rr.domain.riskLink.RLExposureSummaryItem;
import com.scor.rr.domain.riskLink.RLModelDataSource;
import com.scor.rr.mapper.RLExposureSummaryItemRowMapper;
import com.scor.rr.repository.*;
import com.scor.rr.service.LocationLevelExposure;
import com.scor.rr.service.RegionPerilService;
import com.scor.rr.service.RmsService;
import com.scor.rr.service.abstraction.JobManager;
import com.scor.rr.service.batch.writer.ExposureWriter;
import com.scor.rr.service.state.TransformationPackage;
import org.apache.commons.collections.keyvalue.MultiKey;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@StepScope
public class ExposureSummaryExtractor {

    private static final Logger log = LoggerFactory.getLogger(ExposureSummaryExtractor.class);
    @Value("${rms.ds.dbname}")
    protected String database;
    @Value("#{jobParameters['projectId']}")
    protected String projectId;
    @Value("#{jobParameters['division']}")
    protected String division;
    @Value("#{jobParameters['periodBasis']}")
    protected String periodBasis;
    @Value("#{jobParameters['importSequence']}")
    protected Long importSequence;
    @Autowired
    private ExposureViewDefinitionRepository exposureViewDefinitionRepository;
    @Autowired
    private ExposureViewVersionRepository exposureViewVersionRepository;
    @Autowired
    private ExposureViewQueryRepository exposureViewQueryRepository;
    @Autowired
    private GlobalExposureViewRepository globalExposureViewRepository;
    @Autowired
    private ProjectImportRunRepository projectImportRunRepository;
    @Autowired
    private ExposureViewRepository exposureViewRepository;
    @Autowired
    private GlobalViewSummaryRepository globalViewSummaryRepository;
    @Autowired
    private TransformationPackage transformationPackage;
    @Autowired
    private RegionPerilService regionPerilService;
    @Autowired
    private ExposureSummaryConformerReferenceRepository exposureSummaryConformerReferenceRepository;
    @Autowired
    private RLModelDataSourceRepository rlModelDataSourceRepository;
    @Autowired
    private ExposureSummaryDataRepository exposureSummaryDataRepository;
    @Autowired
    private RLExposureSummaryItemRepository exposureSummaryItemRepository;
    @Autowired
    private ModellingSystemInstanceRepository modellingSystemInstanceRepository;
    @Autowired
    private RmsService rmsService;
    @Autowired
    private ExposureWriter exposureWriter;
    @Autowired
    private LocationLevelExposure locationLevelExposure;
    @Autowired
    private RLPortfolioRepository rlPortfolioRepository;
    @Autowired
    @Qualifier("jobManagerImpl")
    private JobManager jobManager;

    @Value("#{jobParameters['taskId']}")
    private String taskId;

    private MultiKey createEdmKey(String instance, Long edmId, String edmName) {
        return new MultiKey(instance, edmId, edmName);
    }

    public RepeatStatus extract() {

        StepEntity step = jobManager.createStep(Long.valueOf(taskId), "ExtractExposureSummaries", 13);
        try {
            //NOTE: I think you could find ProjectImportRun by projectId and importSequence (in jobParameters) ?
            List<ProjectImportRunEntity> projectImportRunList = projectImportRunRepository.findByProjectId(Long.valueOf(projectId));
            ProjectImportRunEntity projectImportRun = projectImportRunRepository.findByProjectIdAndRunId(Long.valueOf(projectId), projectImportRunList.size());

            List<ModelPortfolioEntity> modelPortfolios = transformationPackage.getModelPortfolios();

            if (modelPortfolios != null && !modelPortfolios.isEmpty()) {
                Map<MultiKey, List<ModelPortfolioEntity>> portfoliosPerEdm = new HashMap<>();

                modelPortfolios.forEach(modelPortfolio -> {
                    ModellingSystemInstanceEntity instanceEntity = modellingSystemInstanceRepository.findByName(modelPortfolio.getSourceModellingSystemInstance());
                    if (instanceEntity != null) {
                        MultiKey edmKey = createEdmKey(instanceEntity.getModellingSystemInstanceId(),
                                modelPortfolio.getDataSourceId(), modelPortfolio.getDataSourceName());
                        if (portfoliosPerEdm.get(edmKey) != null)
                            portfoliosPerEdm.get(edmKey).add(modelPortfolio);
                        else
                            portfoliosPerEdm.put(edmKey, new ArrayList<>(Collections.singleton(modelPortfolio)));
                    }
                });

                ExposureView defaultExposureView = exposureViewRepository.findByDefaultView(true);
                if (defaultExposureView != null) {
                    GlobalExposureView globalExposureView = new GlobalExposureView();
                    globalExposureView.setProjectId(Long.valueOf(projectId));
                    globalExposureView.setName(defaultExposureView.getName());

                    if (division != null)
                        globalExposureView.setDivisionNumber(Integer.valueOf(division));
                    //TODO : FIX ME LATER
                    globalExposureView.setPeriodBasisId(null);
                    if (importSequence != null)
                        globalExposureView.setVersion(importSequence.intValue());

                    globalExposureViewRepository.save(globalExposureView);
                    List<ExposureViewDefinition> exposureViewDefinitions = exposureViewDefinitionRepository.findByExposureView(defaultExposureView);

                    for (Map.Entry<MultiKey, List<ModelPortfolioEntity>> entry : portfoliosPerEdm.entrySet()) {

                        MultiKey edm = entry.getKey();
                        String instance = (String) edm.getKey(0);
                        Long edmId = (Long) edm.getKey(1);
                        String edmName = (String) edm.getKey(2);

                        List<String> portfolioAndConformedCurrencyList =
                                entry.getValue().stream().map(modelPortfolio -> modelPortfolio.getPortfolioId() + "~" + modelPortfolio.getPortfolioType() + "~" + (modelPortfolio.getCurrency() != null ? modelPortfolio.getCurrency() : "USD"))
                                        .collect(Collectors.toList());

                        //Note: add excludeRegionPerils (type varchar) into ModelPortfolio and RlPortfolioSelection.
                        // List<String> portfolioExcludedRegionPeril will be built in a similar way as portfolioAndConformedCurrencyList (as describe in documentation) or by default null
                        // It contains a list of excluded region perils, separated by comma.
                        // How we can populate this field could be discussed later based on the screen design.

                        log.info("preparing context for {}", portfolioAndConformedCurrencyList);
                        Integer runId = rmsService.createEDMPortfolioContext(instance,
                                edmId,
                                edmName,
                                portfolioAndConformedCurrencyList,
                                null);

                        if (runId != null) {
                            Map<String, Object> params = new HashMap<>();

                            params.put("Edm_id", edmId);
                            params.put("Edm_name", edmName);
                            params.put("RunID", runId);

                            //NOTE: we could process all ExposureViewDefinitions with type Source first, then reuse the RLExposureSummaryItem for conforming
                            // Avoid invoking SummaryByCountry multiple times
                            exposureViewDefinitions.stream()
                                    .filter(exposureViewDefinition -> exposureViewDefinition.getExposureSummaryType().equalsIgnoreCase("SOURCE"))
                                    .forEach(exposureViewDefinition -> {

                                        ExposureViewVersion exposureViewVersion = exposureViewVersionRepository.findByExposureViewDefinitionAndCurrent(exposureViewDefinition, true);
                                        if (exposureViewVersion != null) {
                                            ExposureViewQuery exposureViewQuery = exposureViewQueryRepository.findByExposureViewVersion(exposureViewVersion);

                                            GlobalViewSummary globalViewSummary = new GlobalViewSummary();
                                            globalViewSummary.setInstanceId(instance);
                                            globalViewSummary.setEdmId(edmId);
                                            globalViewSummary.setEdmName(edmName);
                                            globalViewSummary.setSummaryTitle(exposureViewDefinition.getName());
                                            globalViewSummary.setSummaryOrder(exposureViewDefinition.getOrder());
                                            // TODO: review this
                                            globalViewSummary.setGlobalExposureView(globalExposureView);
                                            //
                                            globalViewSummary = globalViewSummaryRepository.save(globalViewSummary);

                                            JdbcTemplate template = rmsService.getJdbcTemplate(instance);

                                            String query = "EXEC " + database + ".dbo." + exposureViewQuery.getQuery() + " @Edm_Id=" + edmId + ", @Edm_name=" + edmName + ", @RunID=" + runId;

                                            log.info("calling summary procedure {} for runId {}", exposureViewQuery.getQuery(), runId);
                                            List<RLExposureSummaryItem> rlExposureSummaryItems = template.query(
                                                    query,
                                                    new RLExposureSummaryItemRowMapper(globalViewSummary));

                                            log.info("Info: Start saving risk reveal exposure summaries");
                                            exposureSummaryDataRepository.saveAll(this.transformSummary(exposureViewDefinition, rlExposureSummaryItems, entry.getValue()));
                                            log.info("Info: Saving risk reveal exposure summaries has ended");

                                            log.info("Info: Start saving risk link exposure summaries");
                                            exposureSummaryItemRepository.saveAll(rlExposureSummaryItems);
                                            log.info("Info: Saving risk link exposure summaries has ended");

                                            exposureViewDefinitions.stream()
                                                    .filter(derivedDefinition -> derivedDefinition.getBasedOnSummaryAlias().equalsIgnoreCase(exposureViewDefinition.getExposureSummaryAlias()))
                                                    .forEach(derivedDefinition -> exposureSummaryDataRepository.saveAll(this.transformSummary(derivedDefinition, rlExposureSummaryItems, entry.getValue())));
                                        }
                                    });


//                            log.debug("Starting extract EDM Detail Summary: {}", modelPortfolios.size());
//                            String extractName = "RR_RL_GetEdmDetailSummary";
//                            entry.getValue().forEach(modelPortfolio -> {
//
//                                RLPortfolio rLPortfolio = rlPortfolioRepository.findByRlId(modelPortfolio.getPortfolioId());
//                                File f = exposureWriter.makeDetailExposureFile(edmName, rLPortfolio.getRlId(), modelPortfolio.getDivision());
//                                if (f == null) {
//                                    log.error("Error while creating detail exposure file !");
//                                } else {
//                                    log.debug("Export to file: {}}", f.getAbsolutePath());
//                                    rmsService.extractDetailedExposure(f, edmId, edmName, instance, extractName, portfolioAndConformedCurrencyList, null, runId, rLPortfolio.getRlId(), rLPortfolio.getType());
//
//                                    // dh modified
//                                    byte[] buffer = new byte[1024];
//                                    String zipPath = f.getParent();
//                                    String zipfile = f.getName().replace("txt", "zip");
//
//                                    try {
//                                        FileOutputStream fos = new FileOutputStream((zipPath + "/" + zipfile));
//                                        ZipOutputStream zos = new ZipOutputStream(fos);
//                                        ZipEntry ze = new ZipEntry(f.getName());
//                                        zos.putNextEntry(ze);
//                                        FileInputStream in = new FileInputStream(f.getAbsolutePath());
//
//                                        int len;
//                                        while ((len = in.read(buffer)) > 0) {
//                                            zos.write(buffer, 0, len);
//                                        }
//                                        f.delete();
//                                        in.close();
//                                        zos.closeEntry();
//                                        zos.close();
//                                    } catch (FileNotFoundException ex) {
//                                        log.error("file was not found");
//                                    } catch (IOException ex) {
//                                        log.error("an io error has occurred");
//                                    }
//                                    log.debug("zip file name {}", zipfile);
//                                    log.debug("zip file path {}", zipPath);
//
//                                    List<ExposureSummaryExtractFile> extractFiles = new ArrayList<>();
//                                    extractFiles.add(new ExposureSummaryExtractFile(new BinFile(zipfile, zipPath, null), "Detailed"));
//                                    exposureWriter.writeExposureSummaryHeader(edmId, edmName, rLPortfolio, modelPortfolio, ExposureSummaryExtractType.DETAILED_EXPOSURE_SUMMARY, extractFiles);
//                                }
//
//                                log.debug("Extract EDM Detail Summary completed");
//
//                                log.debug("Extract Location level Summary started");
//
//                                if (modelPortfolio.isImportLocationLevel()) {
//
//                                    List<ExposureSummaryExtractFile> extractFiles = new ArrayList<>();
//                                    for (String schema : locationLevelExposure.getAllExtractNames()) {
//                                        String extractFileType = locationLevelExposure.getExtractFileType(schema);
//                                        if (extractFileType == null) {
//                                            log.error("Extract file type for {} not found !", schema);
//                                            continue;
//                                        }
//                                        File file = null;
//                                        if (rLPortfolio != null)
//                                            file = exposureWriter.makeLocLevelExposureFile(edmName, modelPortfolio.getPortfolioId(), extractFileType, rLPortfolio.getRlPortfolioAnalysisRegions(), modelPortfolio.getDivision());
//                                        else
//                                            file = exposureWriter.makeLocLevelExposureFile(edmName, modelPortfolio.getPortfolioId(), extractFileType, null, modelPortfolio.getDivision());
//
//                                        if (file == null) {
//                                            log.error("Error while creating location level exposure details file for {}!", schema);
//                                        } else {
//                                            String query = locationLevelExposure.getQuery(schema);
//                                            if (query == null) {
//                                                log.error("Query for {} not found !", schema);
//                                                continue;
//                                            }
//                                            log.debug("Export to file: {}}", file.getAbsolutePath());
//                                            //Note: it's wrong. You must not query to RLExposureSummaryItem (configuration part)
//                                            // As you see, extractLocationLevelExposureDetails needs oly EDM ID, EDM name, Instance (that we are having)
//                                            // Pass them as parameters for this method.
//                                            if (rmsService.extractLocationLevelExposureDetails(edmId, edmName, instance, modelPortfolio.getProjectId(), rLPortfolio, modelPortfolio, file, schema, query)) {
//                                                log.debug("==> success");
//                                                extractFiles.add(new ExposureSummaryExtractFile(new BinFile(file), extractFileType));
//                                            } else {
//                                                log.debug("==> failed");
//                                            }
//                                        }
//                                    }
//                                    if (!extractFiles.isEmpty()) {
//                                        exposureWriter.writeExposureSummaryHeader(edmId, edmName, rLPortfolio, modelPortfolio, ExposureSummaryExtractType.DETAILED_EXPOSURE_SUMMARY, extractFiles);
//                                    }
//                                }
//                            });

                            rmsService.removeEDMPortfolioContext(instance, runId);
                        } else {
                            log.error("Error: runId is null");
                            jobManager.onTaskError(Long.valueOf(taskId));
                            jobManager.logStep(step.getStepId(), StepStatus.FAILED);
                        }
                    }

                } else {
                    log.warn("No default view was found");
                    jobManager.onTaskError(Long.valueOf(taskId));
                    jobManager.logStep(step.getStepId(), StepStatus.FAILED);
                }
            } else {
                log.info("No model portfolio selected");
                jobManager.onTaskError(Long.valueOf(taskId));
                jobManager.logStep(step.getStepId(), StepStatus.FAILED);
            }
            jobManager.logStep(step.getStepId(), StepStatus.SUCCEEDED);
            return RepeatStatus.FINISHED;
        } catch (Exception ex) {
            jobManager.onTaskError(Long.valueOf(taskId));
            jobManager.logStep(step.getStepId(), StepStatus.FAILED);
            ex.printStackTrace();
            return RepeatStatus.valueOf("FAILED");
        }

    }

    private List<ExposureSummaryData> transformSummary(ExposureViewDefinition exposureViewDefinition, List<RLExposureSummaryItem> rlExposureSummaryItems, List<ModelPortfolioEntity> modelPortfolios) {

        Map<MultiKey, ExposureSummaryData> tempAggregate = new HashMap<>();

        rlExposureSummaryItems.forEach(rlExposureSummaryItem -> {

            ExposureSummaryData exposureSummaryData = new ExposureSummaryData();

            exposureSummaryData.setCountryCode(rlExposureSummaryItem.getCountryCode());
            exposureSummaryData.setAdmin1Code(rlExposureSummaryItem.getAdmin1Code() != null ?
                    rlExposureSummaryItem.getAdmin1Code().equals("") ? null : rlExposureSummaryItem.getAdmin1Code() : null);
            exposureSummaryData.setGlobalViewSummary(rlExposureSummaryItem.getGlobalViewSummary());
            exposureSummaryData.setAnalysisRegionCode(rlExposureSummaryItem.getAnalysisRegionCode());
            exposureSummaryData.setConformedCurrency(rlExposureSummaryItem.getConformedCurrency());
            exposureSummaryData.setExposureCurrency(rlExposureSummaryItem.getExposureCurrency());
            exposureSummaryData.setConformedCurrencyUSDRate(rlExposureSummaryItem.getConformedCurrencyUSDRate());
            exposureSummaryData.setExposureCurrencyUSDRate(rlExposureSummaryItem.getExposureCurrencyUSDRate());
            exposureSummaryData.setRateDate(rlExposureSummaryItem.getRateDate());
            exposureSummaryData.setSourcePortfolioId(rlExposureSummaryItem.getPortfolioId());
            exposureSummaryData.setPortfolioType(rlExposureSummaryItem.getPortfolioType());
            exposureSummaryData.setEntity(1);
            exposureSummaryData.setFinancialPerspective(rlExposureSummaryItem.getFinancialPerspective());
            //
            if (StringUtils.equalsIgnoreCase(rlExposureSummaryItem.getPeril(), "Total")) {
                exposureSummaryData.setPerilCode(rlExposureSummaryItem.getPeril());
                exposureSummaryData.setRegionPerilCode("Unmapped");
                exposureSummaryData.setRegionPerilGroupCode("Unmapped");
            } else {
                RegionPerilEntity rp = regionPerilService.findRegionPerilByCountryCodeAdmin1CodePerilCode(rlExposureSummaryItem.getCountryCode(), rlExposureSummaryItem.getAdmin1Code(), rlExposureSummaryItem.getPeril());
//                if (rp == null) {
//                    rp = regionPerilService.findRegionPerilByCountryCodeAdmin1CodePerilCode(rlExposureSummaryItem.getCountryCode().toUpperCase(), "", rlExposureSummaryItem.getPeril().toUpperCase());
//                }
                if (rp != null) {
                    exposureSummaryData.setPerilCode(rp.getPerilCode());
                    exposureSummaryData.setRegionPerilCode(rp.getRegionPerilCode());
                    exposureSummaryData.setRegionPerilGroupCode(rp.getRegionPerilGroupCode());
                } else {
                    exposureSummaryData.setPerilCode(rlExposureSummaryItem.getPeril());
                    exposureSummaryData.setRegionPerilCode("Unmapped");
                    exposureSummaryData.setRegionPerilGroupCode("Unmapped");
                }
            }

            modelPortfolios.stream().filter(element -> element.getPortfolioId().equals(rlExposureSummaryItem.getPortfolioId()))
                    .findFirst()
                    .ifPresent(modelPortfolio -> exposureSummaryData.setModelPortfolioId(modelPortfolio.getModelPortfolioId()));

            exposureSummaryData.setLocationCount(rlExposureSummaryItem.getLocationCount());
            exposureSummaryData.setTiv(rlExposureSummaryItem.getTotalTiv());
            //
            for (AxisConformerDefinition axisConformer : exposureViewDefinition.getAxisConformerDefinitions()) {
                traduceAxis(axisConformer, rlExposureSummaryItem, exposureSummaryData);
            }
            // re-aggregate on the fly when the axis have been converted
            MultiKey itemKey = makeExposureSummaryStorageKey(exposureSummaryData);
            ExposureSummaryData itemAgg = tempAggregate.get(itemKey);

            if (itemAgg != null) {
                itemAgg.setLocationCount(itemAgg.getLocationCount() + exposureSummaryData.getLocationCount());
                itemAgg.setTiv(itemAgg.getTiv() + exposureSummaryData.getTiv());
            } else {
                tempAggregate.put(itemKey, exposureSummaryData);
            }


        });

        return new ArrayList<>(tempAggregate.values());
    }

    private MultiKey makeExposureSummaryStorageKey(ExposureSummaryData item) {
        return new MultiKey(new Object[]{
                item.getAdmin1Code(),
                item.getAnalysisRegionCode(),
                item.getConformedCurrency(),
                item.getCountryCode(),
                item.getDimension1(),
                item.getDimension2(),
                item.getDimension3(),
                item.getDimension4(),
                item.getDimensionSort1(),
                item.getDimensionSort2(),
                item.getDimensionSort3(),
                item.getDimensionSort4(),
                item.getGlobalViewSummary().getEdmId(),
                item.getExposureCurrency(),
                item.getFinancialPerspective(),
                item.getPerilCode(),
                item.getSourcePortfolioId(),
                item.getPortfolioType(),
                item.getRegionPerilCode(),
                item.getRegionPerilGroupCode()});
    }

    private void traduceAxis(AxisConformerDefinition axisConformerDefinition, RLExposureSummaryItem itemIn, ExposureSummaryData itemOut) {
        String value = null;
        Integer valueSortOrder = null;

        switch (axisConformerDefinition.getSourceAxis()) {
            case Dimension1:
                value = itemIn.getDimension1();
                valueSortOrder = itemIn.getDimensionSort1();
                break;
            case Dimension2:
                value = itemIn.getDimension2();
                valueSortOrder = itemIn.getDimensionSort2();
                break;
            case Dimension3:
                value = itemIn.getDimension3();
                valueSortOrder = itemIn.getDimensionSort3();
                break;
            case Dimension4:
                value = itemIn.getDimension4();
                valueSortOrder = itemIn.getDimensionSort4();
                break;
            case Country:
                value = itemIn.getCountryCode();
                break;
            case Admin1Code:
                value = itemIn.getAdmin1Code();
                break;
            case ExposureCurrency:
                value = itemIn.getExposureCurrency();
                break;
            case ConformedCurrency:
                value = itemIn.getConformedCurrency();
                break;
            case RegionPeril:
                // necessary for TreeMap...
                if (StringUtils.equalsIgnoreCase(itemIn.getPeril(), "Total")) {
                    value = "Unmapped";
                } else {
                    RegionPerilEntity rp = this.regionPerilService.findRegionPerilByCountryCodeAdmin1CodePerilCode(itemIn.getCountryCode(), itemIn.getAdmin1Code(), itemIn.getPeril());
                    if (rp == null) {
                        rp = regionPerilService.findRegionPerilByCountryCodeAdmin1CodePerilCode(itemIn.getCountryCode().toUpperCase(), "", itemIn.getPeril().toUpperCase());
                    }
                    if (rp != null) {
                        value = rp.getRegionPerilCode();
                    } else {
                        value = "UNKOWN : '" + itemIn.getCountryCode() + ":" + itemIn.getAdmin1Code() + ":" + itemIn.getPeril() + "'";
                    }
                }
                //log.warn("RegionPeril axis cannot be used as a source", acd);
                break;
            case Peril:
                value = itemIn.getPeril();
                break;
            case FinancialPerspective:
                value = itemIn.getFinancialPerspective();
                break;
            case AnalysisRegion:
                value = itemIn.getAnalysisRegionCode();
                break;
            case RegionPerilGroup:
                // necessary for TreeMap...
                if (StringUtils.equalsIgnoreCase(itemIn.getPeril(), "Total")) {
                    value = "Unmapped";
                } else {
                    RegionPerilEntity rp = this.regionPerilService.findRegionPerilByCountryCodeAdmin1CodePerilCode(itemIn.getCountryCode(), itemIn.getAdmin1Code(), itemIn.getPeril());
                    if (rp == null) {
                        rp = regionPerilService.findRegionPerilByCountryCodeAdmin1CodePerilCode(itemIn.getCountryCode().toUpperCase(), "", itemIn.getPeril().toUpperCase());
                    }
                    if (rp != null) {
                        value = rp.getRegionPerilGroupCode();
                    } else {
                        value = "UNKOWN : '" + itemIn.getCountryCode() + ":" + itemIn.getAdmin1Code() + ":" + itemIn.getPeril() + "'";
                    }
                }
                //log.warn("RegionPeril axis cannot be used as a source", acd);
                break;
            case Undefined:
                break;
            default:
                log.warn("unknown axis name '{}'", ToStringBuilder.reflectionToString(axisConformerDefinition));
                break;
        }

        switch (axisConformerDefinition.getAxisConformerMode()) {
            case Reference:
                /*
                 * TODO : ponder a way to propagate the sourceSystem/vendor/version from
                 * the up layer
                 */
                ExposureSummaryConformerReferenceEntity ref = exposureSummaryConformerReferenceRepository.
                        findBySourceVendorAndSourceSystemAndVersionAndAxisConformerAliasAndInputCode("RMS", "RISKLINK", "", axisConformerDefinition.getAxisConformerAlias(), value);
                if (ref != null) {
                    value = ref.getOutputCode();
                    if (ref.getSortOrder() != null) {
                        valueSortOrder = ref.getSortOrder();
                    }
                } else {
                    log.trace("ExposureSummaryConformerReference not found for tuple '{}':'{}':'{}':'{}':'{}'", "RMS", "RISKLINK", "", axisConformerDefinition.getAxisConformerAlias(), value);
                }
                break;
            case Function:
                RLModelDataSource rlModelDataSource = rlModelDataSourceRepository.findById(itemIn.getGlobalViewSummary().getEdmId()).orElse(null);
                if (rlModelDataSource != null)
                    value = resolveFunctionalMapping(axisConformerDefinition.getAxisConformerAlias(), Long.valueOf(rlModelDataSource.getRlId()), itemIn);
                break;
            case Copy:
                // do nothing to alter buffer value, this is a copy after all :) (y) ;)
                break;
            default:
                log.warn("unknown AxisConformerDefinition type : {}", ToStringBuilder.reflectionToString(axisConformerDefinition));
        }

        switch (axisConformerDefinition.getTargetAxis()) {
            case Dimension1:
                itemOut.setDimension1(value);
                itemOut.setDimensionSort1(valueSortOrder);
                break;
            case Dimension2:
                itemOut.setDimension2(value);
                itemOut.setDimensionSort2(valueSortOrder);
                break;
            case Dimension3:
                itemOut.setDimension3(value);
                itemOut.setDimensionSort3(valueSortOrder);
                break;
            case Dimension4:
                itemOut.setDimension4(value);
                itemOut.setDimensionSort4(valueSortOrder);
                break;
            case Country:
                itemOut.setCountryCode(value);
                break;
            case Admin1Code:
                itemOut.setAdmin1Code(value);
                break;
            case ExposureCurrency:
                itemOut.setExposureCurrency(value);
                break;
            case ConformedCurrency:
                itemOut.setConformedCurrency(value);
                break;
            case RegionPeril:
                itemOut.setRegionPerilCode(value);
                break;
            case Peril:
                itemOut.setPerilCode(value);
                break;
            case FinancialPerspective:
                itemOut.setFinancialPerspective(value);
                break;
            case AnalysisRegion:
                itemOut.setAnalysisRegionCode(value);
                break;
            case RegionPerilGroup:
                itemOut.setRegionPerilGroupCode(value);
                break;
            case Undefined:
                break;
            default:
                log.warn("unknown axis name '{}'", axisConformerDefinition);
                break;
        }
    }

    private String resolveFunctionalMapping(String functionAlias, Long edmRLId, RLExposureSummaryItem itemIn) {

        if (StringUtils.equalsIgnoreCase("PortfolioFunction", functionAlias)) {
            return "EDM " + edmRLId + " : " + edmRLId + " | " + itemIn.getPortfolioId() + ":" + itemIn.getPortfolioType();
        } else {
            log.warn("functionAlias '{}' is not implemented", functionAlias);
            return null;
        }
    }
}
