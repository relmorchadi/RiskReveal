package com.scor.rr.service.batch;

import com.scor.rr.domain.*;
import com.scor.rr.domain.dto.BinFile;
import com.scor.rr.domain.model.ExposureSummaryExtractFile;
import com.scor.rr.domain.reference.ExposureSummaryConformerReference;
import com.scor.rr.domain.reference.RegionPeril;
import com.scor.rr.domain.riskLink.RLExposureSummaryItem;
import com.scor.rr.domain.riskLink.RlModelDataSource;
import com.scor.rr.mapper.RLExposureSummaryItemRowMapper;
import com.scor.rr.repository.*;
import com.scor.rr.service.RegionPerilService;
import com.scor.rr.service.RmsService;
import com.scor.rr.service.batch.writer.ExposureWriter;
import com.scor.rr.service.state.TransformationPackage;
import org.apache.commons.collections.keyvalue.MultiKey;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
@StepScope
public class ExposureSummaryExtractor {

    private static final Logger log = LoggerFactory.getLogger(ExposureSummaryExtractor.class);

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
    private RlModelDataSourceRepository rlModelDataSourceRepository;

    @Autowired
    private ExposureSummaryDataRepository exposureSummaryDataRepository;

    @Autowired
    private RLExposureSummaryItemRepository exposureSummaryItemRepository;

    @Autowired
    private RmsService rmsService;

    @Autowired
    private ExposureWriter exposureWriter;

    @Value("#{jobParameters['projectId']}")
    protected String projectId;

    @Value("#{jobParameters['division']}")
    protected String division;

    @Value("#{jobParameters['periodBasis']}")
    protected String periodBasis;

    @Value("#{jobParameters['importSequence']}")
    protected Long importSequence;

    private MultiKey createEdmKey(String instance, Long edmId, String edmName) {
        return new MultiKey(instance, edmId, edmName);
    }

    public RepeatStatus extract() {

        try {
            List<ProjectImportRun> projectImportRunList = projectImportRunRepository.findByProjectProjectId(Long.valueOf(projectId));
            ProjectImportRun projectImportRun = projectImportRunRepository.findByProjectProjectIdAndRunId(Long.valueOf(projectId), projectImportRunList.size());

            List<ModelPortfolio> modelPortfolios = transformationPackage.getModelPortfolios();

            if (modelPortfolios != null && !modelPortfolios.isEmpty()) {
                Map<MultiKey, List<ModelPortfolio>> portfoliosPerEdm = new HashMap<>();

                modelPortfolios.forEach(modelPortfolio -> {
                    MultiKey edmKey = createEdmKey(modelPortfolio.getSourceModellingSystemInstance(), modelPortfolio.getDataSourceId(), modelPortfolio.getDataSourceName());
                    Long portfolioId = modelPortfolio.getPortfolioId();
                    String conformedCcy = modelPortfolio.getCurrency();
                    if (portfoliosPerEdm.get(edmKey) != null)
                        portfoliosPerEdm.get(edmKey).add(modelPortfolio);
                    else
                        portfoliosPerEdm.put(edmKey, new ArrayList<>(Collections.singleton(modelPortfolio)));
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

                    for (Map.Entry<MultiKey, List<ModelPortfolio>> entry : portfoliosPerEdm.entrySet()) {

                        MultiKey edm = entry.getKey();
                        String instance = (String) edm.getKey(0);
                        Long edmId = (Long) edm.getKey(1);
                        String edmName = (String) edm.getKey(2);
                        List<String> portfolioAndConformedCurrencyList =
                                entry.getValue().stream().map(modelPortfolio -> modelPortfolio.getPortfolioId() + "~" + modelPortfolio.getCurrency())
                                .collect(Collectors.toList());

                        Integer runId = rmsService.createEDMPortfolioContext(instance,
                                edmId,
                                edmName,
                                portfolioAndConformedCurrencyList,
                                null);

                        if (runId != null) {

                            List<ExposureSummaryData> allRRExposureSummaries = new ArrayList<>();
                            List<RLExposureSummaryItem> allRLExposureSummaries = new ArrayList<>();
                            Map<String, Object> params = new HashMap<>();

                            params.put("edm_id", edmId);
                            params.put("edm_name", edmName);
                            params.put("run_id", runId);

                            exposureViewDefinitions.forEach(exposureViewDefinition -> {
                                ExposureViewVersion exposureViewVersion = exposureViewVersionRepository.findByExposureViewDefinitionAndCurrent(exposureViewDefinition, true);
                                if (exposureViewVersion != null) {
                                    ExposureViewQuery exposureViewQuery = exposureViewQueryRepository.findByExposureViewVersion(exposureViewVersion);

                                    GlobalViewSummary globalViewSummary = new GlobalViewSummary();
                                    globalViewSummary.setInstanceId(instance);
                                    globalViewSummary.setEdmId(edmId);
                                    globalViewSummary.setEdmName(edmName);
                                    globalViewSummary.setSummaryTitle(exposureViewDefinition.getName());
                                    globalViewSummary.setSummaryOrder(exposureViewDefinition.getOrder());
                                    globalViewSummary = globalViewSummaryRepository.save(globalViewSummary);

                                    NamedParameterJdbcTemplate template = rmsService.createTemplate(instance);
                                    List<RLExposureSummaryItem> rlExposureSummaryItems = template.query(
                                            exposureViewQuery.getQuery(),
                                            params,
                                            new RLExposureSummaryItemRowMapper(globalViewSummary));

                                    allRRExposureSummaries.addAll(this.transformSummary(exposureViewDefinition, rlExposureSummaryItems));
                                    allRLExposureSummaries.addAll(rlExposureSummaryItems);
                                }
                            });
                            //rmsService.removeEDMPortfolioContext(instance, runId);

                            log.info("Info: Start saving risk link exposure summaries");
                            exposureSummaryItemRepository.saveAll(allRLExposureSummaries);
                            log.info("Info: Saving risk link exposure summaries has ended");

                            log.info("Info: Start saving risk reveal exposure summaries");
                            exposureSummaryDataRepository.saveAll(allRRExposureSummaries);
                            log.info("Info: Saving risk reveal exposure summaries has ended");


                            log.debug("Starting extract EDM Detail Summary: {}", modelPortfolios.size());
                            String extractName = "RR_RL_GetEdmDetailSummary";

                            //RmsPortfolio pf = pesei.portfolio.getRmsPortfolio();
                            File f = exposureWriter.makeDetailExposureFile(edmName, /*pf.getPortfolioId()*/null);
                            if (f == null) {
                                log.error("Error while creating detail exposure file !");
                            } else {
                                log.debug("Export to file: {}}", f.getAbsolutePath());
                                //rmsService.extractDetailedExposure(f, edm, extractName, entry.getValue(), null, runId, pf.getPortfolioId(), pf.getType());

                                // dh modified
                                byte[] buffer = new byte[1024];
                                String zipPath = f.getParent();
                                String zipfile = f.getName().replace("txt", "zip");

                                FileOutputStream fos = new FileOutputStream((zipPath + "/" + zipfile));
                                ZipOutputStream zos = new ZipOutputStream(fos);
                                ZipEntry ze = new ZipEntry(f.getName());
                                zos.putNextEntry(ze);
                                FileInputStream in = new FileInputStream(f.getAbsolutePath());

                                int len;
                                while ((len = in.read(buffer)) > 0) {
                                    zos.write(buffer, 0, len);
                                }
                                in.close();
                                zos.closeEntry();
                                f.delete();
                                zos.close();

                                log.debug("zip file name {}", zipfile);
                                log.debug("zip file path {}", zipPath);

                                List<ExposureSummaryExtractFile> extractFiles = new ArrayList<>();
                                extractFiles.add(new ExposureSummaryExtractFile(new BinFile(zipfile, zipPath, null), "Detailed"));
                                //String rrPortfolioId = mapPortfolioRRPortfolioIds.get(pesei.portfolio.getId());
                                //RRPortfolio rrPortfolio = rrPortfolioRepository.findOne(rrPortfolioId);
                                //exposureWriter.writeExposureSummaryHeader(project, edm, pesei.portfolio, rrPortfolio, ExposureSummaryExtractType.DETAILED_EXPOSURE_SUMMARY, extractFiles);
                            }

                            rmsService.removeEDMPortfolioContext(instance, runId);
                            log.debug("Extract EDM Detail Summary completed");


                        } else {
                            log.error("Error: runId is null");
                        }
                    }

                } else {
                    log.warn("No default view was found");
                }
            } else {
                log.info("No model portfolio selected");
            }
            return RepeatStatus.FINISHED;
        } catch (Exception ex) {
            ex.printStackTrace();
            return RepeatStatus.CONTINUABLE;
        }

    }


    private List<ExposureSummaryData> transformSummary(ExposureViewDefinition exposureViewDefinition, List<RLExposureSummaryItem> rlExposureSummaryItems) {

        Map<MultiKey, ExposureSummaryData> tempAggregate = new HashMap<>();

        rlExposureSummaryItems.forEach(rlExposureSummaryItem -> {

            ExposureSummaryData exposureSummaryData = new ExposureSummaryData();

            exposureSummaryData.setCountryCode(rlExposureSummaryItem.getCountryCode());
            exposureSummaryData.setAdmin1Code(rlExposureSummaryItem.getAdmin1Code());
            exposureSummaryData.setGlobalViewSummary(rlExposureSummaryItem.getGlobalViewSummary());
            exposureSummaryData.setAnalysisRegionCode(rlExposureSummaryItem.getAnalysisRegionCode());
            exposureSummaryData.setConformedCurrency(rlExposureSummaryItem.getConformedCurrency());
            exposureSummaryData.setExposureCurrency(rlExposureSummaryItem.getExposureCurrency());
            exposureSummaryData.setConformedCurrencyUSDRate(rlExposureSummaryItem.getConformedCurrencyUSDRate());
            exposureSummaryData.setExposureCurrencyUSDRate(rlExposureSummaryItem.getExposureCurrencyUSDRate());
            exposureSummaryData.setRateDate(rlExposureSummaryItem.getRateDate());
            exposureSummaryData.setPortfolioId(rlExposureSummaryItem.getPortfolioId());
            exposureSummaryData.setPortfolioType(rlExposureSummaryItem.getPortfolioType());
            exposureSummaryData.setFinancialPerspective(rlExposureSummaryItem.getFinancialPerspective());
            //
            if (StringUtils.equalsIgnoreCase(rlExposureSummaryItem.getPeril(), "Total")) {
                exposureSummaryData.setPerilCode(rlExposureSummaryItem.getPeril());
                exposureSummaryData.setRegionPerilCode("Unmapped");
                //exposureSummaryData.setRegionPerilGroupCode("Unmapped");
            } else {
                RegionPeril rp = regionPerilService.findRegionPerilByCountryCodeAdmin1CodePerilCode(rlExposureSummaryItem.getCountryCode(), rlExposureSummaryItem.getAdmin1Code(), rlExposureSummaryItem.getPeril());
                if (rp == null) {
                    rp = regionPerilService.findRegionPerilByCountryCodeAdmin1CodePerilCode(rlExposureSummaryItem.getCountryCode().toUpperCase(), "", rlExposureSummaryItem.getPeril().toUpperCase());
                }
                if (rp != null) {
                    exposureSummaryData.setPerilCode(rp.getPerilCode());
                    exposureSummaryData.setRegionPerilCode(rp.getRegionPerilCode());
                    //exposureSummaryData.setRegionPerilGroupCode(rp.getRegionPerilGroupCode());
                } else {
                    exposureSummaryData.setPerilCode(rlExposureSummaryItem.getPeril());
                    exposureSummaryData.setRegionPerilCode("Unmapped");
                    //exposureSummaryData.setRegionPerilGroupCode("Unmapped");
                }
            }
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
                item.getPortfolioId(),
                item.getPortfolioType(),
                item.getRegionPerilCode(),
                item.getRegionPerilGroupCode()});
    }

    private void traduceAxis(AxisConformerDefinition axisConformerDefinition, RLExposureSummaryItem itemIn, ExposureSummaryData itemOut) {
        String value = null;
        Integer valueSortOrder = null;

        switch (axisConformerDefinition.getSourceAxis()) {
            case DIMENSION1:
                value = itemIn.getDimension1();
                valueSortOrder = itemIn.getDimensionSort1();
                break;
            case DIMENSION2:
                value = itemIn.getDimension2();
                valueSortOrder = itemIn.getDimensionSort2();
                break;
            case DIMENSION3:
                value = itemIn.getDimension3();
                valueSortOrder = itemIn.getDimensionSort3();
                break;
            case DIMENSION4:
                value = itemIn.getDimension4();
                valueSortOrder = itemIn.getDimensionSort4();
                break;
            case COUNTRY:
                value = itemIn.getCountryCode();
                break;
            case ADMIN1CODE:
                value = itemIn.getAdmin1Code();
                break;
            case EXPOSURECURRENCY:
                value = itemIn.getExposureCurrency();
                break;
            case CONFORMEDCURRENCY:
                value = itemIn.getConformedCurrency();
                break;
            case REGIONPERIL:
                // necessary for TreeMap...
                if (StringUtils.equalsIgnoreCase(itemIn.getPeril(), "Total")) {
                    value = "Unmapped";
                } else {
                    RegionPeril rp = this.regionPerilService.findRegionPerilByCountryCodeAdmin1CodePerilCode(itemIn.getCountryCode(), itemIn.getAdmin1Code(), itemIn.getPeril());
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
            case PERIL:
                value = itemIn.getPeril();
                break;
            case FINANCIALPERSPECTIVE:
                value = itemIn.getFinancialPerspective();
                break;
            case ANALYSISREGION:
                value = itemIn.getAnalysisRegionCode();
                break;
            case REGIONPERILGROUP:
                // necessary for TreeMap...
                if (StringUtils.equalsIgnoreCase(itemIn.getPeril(), "Total")) {
                    value = "Unmapped";
                } else {
                    RegionPeril rp = this.regionPerilService.findRegionPerilByCountryCodeAdmin1CodePerilCode(itemIn.getCountryCode(), itemIn.getAdmin1Code(), itemIn.getPeril());
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
            case UNDEFINED:
                break;
            default:
                log.warn("unknown axis name '{}'", ToStringBuilder.reflectionToString(axisConformerDefinition));
                break;
        }

        switch (axisConformerDefinition.getAxisConformerMode()) {
            case REFERENCE:
                /*
                 * TODO : ponder a way to propagate the sourceSystem/vendor/version from
                 * the up layer
                 */
                ExposureSummaryConformerReference ref = exposureSummaryConformerReferenceRepository.
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
            case FUNCTION:
                RlModelDataSource rlModelDataSource = rlModelDataSourceRepository.findById(itemIn.getGlobalViewSummary().getEdmId()).orElse(null);
                if (rlModelDataSource != null)
                    value = resolveFunctionalMapping(axisConformerDefinition.getAxisConformerAlias(), Long.valueOf(rlModelDataSource.getRlId()), itemIn);
                break;
            case COPY:
                // do nothing to alter buffer value, this is a copy after all :) (y) ;)
                break;
            default:
                log.warn("unknown AxisConformerDefinition type : {}", ToStringBuilder.reflectionToString(axisConformerDefinition));
        }

        switch (axisConformerDefinition.getTargetAxis()) {
            case DIMENSION1:
                itemOut.setDimension1(value);
                itemOut.setDimensionSort1(valueSortOrder);
                break;
            case DIMENSION2:
                itemOut.setDimension2(value);
                itemOut.setDimensionSort2(valueSortOrder);
                break;
            case DIMENSION3:
                itemOut.setDimension3(value);
                itemOut.setDimensionSort3(valueSortOrder);
                break;
            case DIMENSION4:
                itemOut.setDimension4(value);
                itemOut.setDimensionSort4(valueSortOrder);
                break;
            case COUNTRY:
                itemOut.setCountryCode(value);
                break;
            case ADMIN1CODE:
                itemOut.setAdmin1Code(value);
                break;
            case EXPOSURECURRENCY:
                itemOut.setExposureCurrency(value);
                break;
            case CONFORMEDCURRENCY:
                itemOut.setConformedCurrency(value);
                break;
            case REGIONPERIL:
                itemOut.setRegionPerilCode(value);
                break;
            case PERIL:
                itemOut.setPerilCode(value);
                break;
            case FINANCIALPERSPECTIVE:
                itemOut.setFinancialPerspective(value);
                break;
            case ANALYSISREGION:
                itemOut.setAnalysisRegionCode(value);
                break;
            case REGIONPERILGROUP:
                itemOut.setRegionPerilGroupCode(value);
                break;
            case UNDEFINED:
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
