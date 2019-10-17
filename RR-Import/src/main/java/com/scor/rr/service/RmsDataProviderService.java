package com.scor.rr.service;

import com.scor.rr.domain.entities.ihub.RmsPortfolioAnalysisRegion;
import com.scor.rr.domain.entities.meta.SourceEpCurve;
import com.scor.rr.domain.entities.meta.SourceEpHeader;
import com.scor.rr.domain.entities.meta.SourceSummaryStatistic;
import com.scor.rr.domain.entities.references.RegionPeril;
import com.scor.rr.domain.entities.references.cat.ModellingSystemInstance;
import com.scor.rr.domain.entities.rms.*;
import com.scor.rr.domain.entities.rms.exposuresummary.*;
import com.scor.rr.domain.entities.rms.exposuresummary.axis.AxisConformerDefinition;
import com.scor.rr.domain.entities.workspace.ModelingExposureDataSource;
import com.scor.rr.domain.entities.workspace.Portfolio;
import com.scor.rr.domain.entities.workspace.Project;
import com.scor.rr.domain.enums.ExposureSummaryType;
import com.scor.rr.domain.enums.LocationLevelExposure;
import com.scor.rr.domain.enums.ModelDataSourceType;
import com.scor.rr.importBatch.processing.datasources.DSCache;
import com.scor.rr.importBatch.processing.exposure.exposure.ExposureExtractor;
import com.scor.rr.importBatch.processing.workflow.io.ExposureSummaryExtractInput;
import com.scor.rr.importBatch.processing.workflow.io.ExposureSummaryExtractOutput;
import com.scor.rr.repository.cat.ModellingSystemInstanceRepository;
import com.scor.rr.service.condition.ConditionRoot;
import com.scor.rr.service.condition.ExposureSummaryConditionParser;
import com.scor.rr.service.condition.ExposureSummaryValueExtractor;
import com.scor.rr.utils.ALMFUtils;
import com.scor.rr.utils.CreateEDMSummaryStoredProcedure;
import com.scor.rr.utils.GenericDescriptor;
import com.scor.rr.utils.mapper.*;
import lombok.Data;
import org.apache.commons.collections.keyvalue.MultiKey;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.io.File;
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

/**
 * RmsDataProvider Service
 *
 * @author HADDINI Zakariyae
 */
@Data
public class RmsDataProviderService {

    private static final Logger logger = LoggerFactory.getLogger(RmsDataProviderService.class);

    private String dmSQLQuery;
    private String portfolioSQLQuery;
    private String analysisSQLQuery;
    private String portfolioSQLQueryBasic;
    private String analysisSQLQueryBasic;
    private String getAnalysisELTSqlQuery;
    private String getAllAnalysisSummaryStatsSqlQuery;
    private String getAnalysisSummaryStatsSqlQuery;
    private String getAllAnalysisEpCurvesSQLQuery;
    private String getAnalysisEpCurvesSqlQuery;
    private String analysisAnlsRegionsStatsSQLQuery;
    private String portfolioAnalysisRegionSQLQuery;
    private String portfolioAnalysisRegionSQLQueryInEDMContext;
    private String rmsDatabaseName;
    //	private String modelingOptionSQLQuery;
    private String createEDMPortfolioContextSQL;
    private String removeEDMPortfolioContextSQL;
    private String getRMSExchangeRatesSqlQuery;
    private String getAnalysisModellingOptionSettingsSQL;
    private String getExtractSchemaSqlQuery;
    private String getEdmDefailSummarySqlQuery;
    private String getAllAnalysisTreatyStructureSqlQuery;
    private String exposureSummarySchema;
    private String analysisMultiRegionPerilsQuery;

    @Autowired
    private DSCache dsCache;
    @Autowired
    private RegionPerilMappingCacheService regionPerilMappingCacheService;
    @Autowired
    private ExposureSummaryReferenceCacheService exposureSummaryReferenceCacheService;

    private ModellingSystemInstanceRepository modellingSystemInstanceRepository;

    public RmsDataProviderService() {
    }

    /**
     * extract modeling options
     *
     * @param instanceId
     * @param rdmId
     * @param rdmName
     * @param analysisId
     * @return
     */
    public String extractModelingOptions(String instanceId, long rdmId, String rdmName, String analysisId) {
        // @formatter:off
        Map<String, Object> params = new HashMap<>();
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(getTemplate(instanceId));

        params.put("rdm_id", rdmId);
        params.put("rdm_name", rdmName);
        params.put("analysis_id", analysisId);

        logger.debug("extractModelingOptions | getAnalysisModellingOptionSettingsSQL: {}, params: {}",
                getAnalysisModellingOptionSettingsSQL, params);

        List<Map<String, Object>> result = namedParameterJdbcTemplate
                .queryForList(getAnalysisModellingOptionSettingsSQL, params);

        if (ALMFUtils.isNotNull(result)) {
            StringBuilder builder = new StringBuilder();

            result.forEach(map -> {
                if (ALMFUtils.isNotNull(map) && ALMFUtils.isNotEmpty(map.values())) {
                    map.values().forEach(value -> {
                        if (ALMFUtils.isNotNull(value))
                            builder.append(value.toString());
                    });
                }
            });

            return builder.toString();
        }

        return null;
        // @formatter:on
    }

    /**
     * extract country code
     *
     * @param instanceId
     * @param rdmId
     * @param rdmName
     * @return
     */
    public List<Map<String, Object>> extractCountryCode(String instanceId, long rdmId, String rdmName) {
        // @formatter:off
        Map<String, Object> params = new HashMap<>();
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(getTemplate(instanceId));

        params.put("rdm_id", rdmId);
        params.put("rdm_name", rdmName);

        logger.debug("extractCountryCode |analysisSQLQuery: {}, params: {}", analysisSQLQuery, params);

        return namedParameterJdbcTemplate.queryForList(analysisSQLQuery, params);
        // @formatter:on
    }

    /**
     * extract rms model datasource
     *
     * @param instanceId
     * @return
     */
    public List<RmsModelDatasource> extractRmsModelDatasource(String instanceId) {
        // @formatter:off
        List<RmsModelDatasource> result = new LinkedList<>();

        logger.debug("call extractRmsModelDatasource instanceId : {}", instanceId);

        try {
            ModellingSystemInstance modellingSystemInstance = modellingSystemInstanceRepository.findById(instanceId)
                    .orElse(null);

            if (ALMFUtils.isNotNull(modellingSystemInstance)
                    && ALMFUtils.isNotNull(modellingSystemInstance.getModellingSystemVersion())) {
                logger.debug("instance name : {}, {}", modellingSystemInstance.getName(), " query : ".concat(dmSQLQuery));

                result = getTemplate(instanceId).query(dmSQLQuery,
                        new DmRowMapper(instanceId,
                                modellingSystemInstance.getModellingSystemVersion()
                                        .getModellingSystemVersion(),
                                modellingSystemInstance));
            }
        } catch (DataAccessException e) {
            logger.error(e.getMessage(), e);
        }

        return result;
        // @formatter:on
    }

    /**
     * extract portfolio
     *
     * @param instanceId
     * @param edmId
     * @param edmName
     * @param portfoliosIdList
     * @return
     */
    public List<RmsPortfolio> extractPortfolio(String instanceId, Long edmId, String edmName, String portfoliosIdList) {
        // @formatter:off
        Map<String, Object> params = new HashMap<>();
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(getTemplate(instanceId));

        params.put("edm_id", edmId);
        params.put("edm_name", edmName);
        params.put("portfolioList", portfoliosIdList);

        logger.debug("extractPortfolio | portfolioSQLQuery: {}, params: {}", portfolioSQLQuery, params);

        return namedParameterJdbcTemplate.query(portfolioSQLQuery, params, new PortfolioRowMapper());
        // @formatter:on
    }

    /**
     * extract portfolio basic
     *
     * @param instanceId
     * @param edmId
     * @param edmName
     * @return
     */
    public List<RmsPortfolioBasic> extractPortfolioBasic(String instanceId, Long edmId, String edmName) {
        // @formatter:off
        Map<String, Object> params = new HashMap<>();
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(getTemplate(instanceId));

        params.put("edm_id", edmId);
        params.put("edm_name", edmName);

        logger.debug("extractPortfolio | portfolioSQLQueryBasic: {}, params: {}", portfolioSQLQueryBasic, params);

        return namedParameterJdbcTemplate.query(portfolioSQLQueryBasic, params, new PortfolioBasicRowMapper());
        // @formatter:on
    }

    /**
     * extract rms analysis
     *
     * @param instanceId
     * @param rdmId
     * @param rdmName
     * @param analysisIdsList
     * @return
     */
    public List<RmsAnalysis> extractRmsAnalysis(String instanceId, long rdmId, String rdmName, String analysisIdsList) {
        // @formatter:off
        Map<String, Object> params = new HashMap<>();
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(getTemplate(instanceId));

        params.put("rdm_id", rdmId);
        params.put("rdm_name", rdmName);

        if (StringUtils.isNotEmpty(analysisIdsList))
            params.put("analysis_id_list", analysisIdsList);

        logger.debug("extractRmsAnalysis | analysisSQLQuery: {}, params: {}", analysisSQLQuery, params);

        return namedParameterJdbcTemplate.query(analysisSQLQuery, params, new AnalysisRowMapper());
        // @formatter:on
    }

    /**
     * extract rms analysis basic
     *
     * @param instanceId
     * @param rdmId
     * @param rdmName
     * @return
     */
    public List<RmsAnalysisBasic> extractRmsAnalysisBasic(String instanceId, long rdmId, String rdmName) {
        // @formatter:off
        Map<String, Object> params = new HashMap<>();
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(getTemplate(instanceId));

        params.put("rdm_id", rdmId);
        params.put("rdm_name", rdmName);

        logger.debug("extractRmsAnalysis | analysisSQLQueryBasic: {}, params: {}", analysisSQLQueryBasic, params);

        return namedParameterJdbcTemplate.query(analysisSQLQueryBasic, params, new AnalysisBasicRowMapper());
        // @formatter:on
    }

    /**
     * extract analysis elt
     *
     * @param instanceId
     * @param rdmId
     * @param rdmName
     * @param analysisId
     * @param financialPerspectiveCode
     * @param optionalTreatyLabelId
     * @return
     */
    public RmsAnalysisELT extractAnalysisELT(String instanceId, long rdmId, String rdmName, String analysisId,
                                             String financialPerspectiveCode, Integer optionalTreatyLabelId) {
        // @formatter:off
        Map<String, Object> params = new HashMap<>();
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(getTemplate(instanceId));

        params.put("rdm_id", rdmId);
        params.put("rdm_name", rdmName);
        params.put("analysis_id", analysisId);
        params.put("treaty_label_id", optionalTreatyLabelId);
        params.put("fin_persp_code", financialPerspectiveCode);

        logger.debug("extractAnalysisELT | getAnalysisELTSqlQuery: {}, params: {}", getAnalysisELTSqlQuery, params);

        return new RmsAnalysisELT(rdmId, rdmName, analysisId, instanceId, financialPerspectiveCode,
                namedParameterJdbcTemplate.query(getAnalysisELTSqlQuery, params, new RmsAnalysisELTMapper()));
        // @formatter:on
    }

    /**
     * extract rms exchange rates
     *
     * @param instanceId
     * @param ccyList
     * @return
     */
    public List<RmsExchangeRate> extractRMSExchangeRates(String instanceId, List<String> ccyList) {
        // @formatter:off
        Map<String, Object> params = new HashMap<>();
        List<RmsExchangeRate> rmsExchangeRates = null;
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(getTemplate(instanceId));


        params.put("ccyList", ccyList == null ? null : StringUtils.join(ccyList, ","));

        logger.debug("extractRMSExchangeRates: {}, param: {}", getRMSExchangeRatesSqlQuery, params);

        rmsExchangeRates = namedParameterJdbcTemplate.query(getRMSExchangeRatesSqlQuery, params, new RmsExchangeRatesMapper());

        logger.debug("rmsExchangeRates: {}", rmsExchangeRates);

        return rmsExchangeRates;
        // @formatter:on
    }

    /**
     * extract all analysis summary stats
     *
     * @param instanceId
     * @param rdmId
     * @param rdmName
     * @param fpCodes
     * @param analysisIdsList
     * @return
     */
    public List<Map<String, Object>> extractAllAnalysisSummaryStats(String instanceId, long rdmId, String rdmName,
                                                                    List<String> fpCodes, String analysisIdsList) {
        // @formatter:off
        Map<String, Object> params = new HashMap<>();
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(getTemplate(instanceId));

        params.put("rdm_id", rdmId);
        params.put("rdm_name", rdmName);
        params.put("fin_persp_list", fpCodes == null ? null : StringUtils.join(fpCodes, ","));

        if (StringUtils.isNotEmpty(analysisIdsList))
            params.put("analysis_id_list", analysisIdsList);

        logger.debug("extractAllAnalysisSummaryStats | getAllAnalysisSummaryStatsSqlQuery: {}, params: {}",
                getAllAnalysisSummaryStatsSqlQuery, params);

        return namedParameterJdbcTemplate.queryForList(getAllAnalysisSummaryStatsSqlQuery, params);
        // @formatter:on
    }

    /**
     * extract all analysis treaty structure
     *
     * @param instanceId
     * @param rdmId
     * @param rdmName
     * @param analysisIdsList
     * @return
     */
    public List<Map<String, Object>> extractAllAnalysisTreatyStructure(String instanceId, long rdmId, String rdmName,
                                                                       String analysisIdsList) {
        // @formatter:off
        Map<String, Object> params = new HashMap<>();
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(getTemplate(instanceId));

        params.put("rdm_id", rdmId);
        params.put("rdm_name", rdmName);

        if (StringUtils.isNotEmpty(analysisIdsList))
            params.put("analysis_id_list", analysisIdsList);

        logger.debug("extractAllAnalysisSummaryStats | getAllAnalysisTreatyStructureSqlQuery: {}, params: {}",
                getAllAnalysisTreatyStructureSqlQuery, params);

        return namedParameterJdbcTemplate.queryForList(getAllAnalysisTreatyStructureSqlQuery, params);
        // @formatter:on
    }

    /**
     * extract analysis summary stats
     *
     * @param instanceId
     * @param rdmId
     * @param rdmName
     * @param anlsId
     * @param fpCode
     * @param optionalTreatyLabelId
     * @return
     */
    public List<Map<String, Object>> extractAnalysisSummaryStats(String instanceId, long rdmId, String rdmName,
                                                                 String anlsId, String fpCode, Integer optionalTreatyLabelId) {
        // @formatter:off
        Map<String, Object> params = new HashMap<>();
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(getTemplate(instanceId));


        params.put("rdm_id", rdmId);
        params.put("rdm_name", rdmName);
        params.put("analysis_id", anlsId);
        params.put("fin_persp_code", fpCode);
        params.put("treaty_label_id", optionalTreatyLabelId);

        logger.debug("extractAnalysisSummaryStats | analysisSummaryStatsSQLQueryOneFP: {}, params: {}",
                getAnalysisSummaryStatsSqlQuery, params);

        return namedParameterJdbcTemplate.queryForList(getAnalysisSummaryStatsSqlQuery, params);
        // @formatter:on
    }

    /**
     * extract analysis ep curves
     *
     * @param instanceId
     * @param rdmId
     * @param rdmName
     * @param analysisId
     * @param finPerspective
     * @param optionalTreatyLabelId
     * @return
     */
    public List<Map<String, Object>> extractAnalysisEpCurves(String instanceId, long rdmId, String rdmName,
                                                             String analysisId, String finPerspective, Integer optionalTreatyLabelId) {
        // @formatter:off
        Map<String, Object> params = new HashMap<>();
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(getTemplate(instanceId));

        params.put("rdm_id", rdmId);
        params.put("rdm_name", rdmName);
        params.put("analysis_id", analysisId);
        params.put("fin_persp_code", finPerspective);
        params.put("treaty_label_id", optionalTreatyLabelId);

        logger.debug("extractAnalysisEpCurves | getAnalysisEpCurvesSqlQuery: {}, params: {}",
                getAnalysisEpCurvesSqlQuery, params);

        return namedParameterJdbcTemplate.queryForList(getAnalysisEpCurvesSqlQuery, params);
        // @formatter:on
    }

    /**
     * extract all analysis ep curves
     *
     * @param instanceId
     * @param rdmId
     * @param rdmName
     * @param returnPeriods
     * @param finPerspectiveList
     * @param analysisIdsList
     * @return
     */
    public List<Map<String, Object>> extractAllAnalysisEpCurves(String instanceId, long rdmId, String rdmName,
                                                                List<Double> returnPeriods, List<String> finPerspectiveList, String analysisIdsList) {
        // @formatter:off
        List<String> values = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("#0.0", DecimalFormatSymbols.getInstance(Locale.US));

        Map<String, Object> params = new HashMap<>();
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(getTemplate(instanceId));

        df.setGroupingUsed(false);
        df.setMinimumFractionDigits(10);
        df.setMinimumFractionDigits(1);
        df.setMinimumIntegerDigits(1);
        df.setDecimalSeparatorAlwaysShown(true);

        if (ALMFUtils.isNotNull(returnPeriods))
            returnPeriods.forEach(returnPeriod -> values.add(df.format(returnPeriod)));

        params.put("rdm_id", rdmId);
        params.put("rdm_name", rdmName);
        params.put("ep_points", StringUtils.join(values, ","));

        params.put("fin_persp_list", !ALMFUtils.isNotNull(finPerspectiveList) ? null : StringUtils.join(finPerspectiveList, ","));

        if (StringUtils.isNotEmpty(analysisIdsList))
            params.put("analysis_id_list", analysisIdsList);

        logger.debug("extractAllAnalysisEpCurves | getAllAnalysisEpCurvesSQLQuery: {}, params: {}",
                getAllAnalysisEpCurvesSQLQuery, params);

        return namedParameterJdbcTemplate.queryForList(getAllAnalysisEpCurvesSQLQuery, params);
        // @formatter:on
    }

    /**
     * extract analysis scan level 2
     *
     * @param rmds
     * @param srList
     * @param defaultReturnPeriods
     * @param fpCodes
     * @param analysisIdsList
     */
    public void extractAnalysisScanLevel2(RmsModelDatasource rmds, List<RmsAnalysis> srList,
                                          List<Double> defaultReturnPeriods, List<String> fpCodes, String analysisIdsList) {
        // @formatter:off
        logger.info("extracting AnalysisEpCurves for RDS {}:{}", rmds.getRmsId(), rmds.getName());

        Map<String, Map<Integer, SourceEpHeader>> key2Metric2EPHeader = new HashMap<>();
        List<Map<String, Object>> rmsEPCurvesData = this.extractAllAnalysisEpCurves(rmds.getInstanceId(),
                rmds.getRmsId(),
                rmds.getName(),
                defaultReturnPeriods,
                fpCodes,
                analysisIdsList);

        if (ALMFUtils.isNotNull(rmsEPCurvesData))
            rmsEPCurvesData.forEach(map -> {
                Long anlsId = ((Integer) map.get("analysis_id")).longValue();
                String financialPerspective = (String) map.get("fin_persp_code");
                Integer statisticsMetrics = (Integer) map.get("ep_type_code");
                Integer financialPerspectiveTYLabelId = (Integer) map.get("treaty_label_id");
                Double loss = (Double) map.get("loss");
                Object epObj = map.get("exceedance_probability");
                Double ep = null;

                if (epObj instanceof Double)
                    ep = (Double) epObj;
                else if (epObj instanceof BigDecimal && ALMFUtils.isNotNull(epObj))
                    ep = ((BigDecimal) epObj).doubleValue();

                String hashKey = ALMFUtils.makeSourceEPHeaderHashKey(anlsId,
                        financialPerspective,
                        financialPerspectiveTYLabelId);

                Map<Integer, SourceEpHeader> metric2EPHeader = key2Metric2EPHeader.get(hashKey);

                if (ALMFUtils.isNotNull(metric2EPHeader)) {
                    metric2EPHeader = new HashMap<>();

                    key2Metric2EPHeader.put(hashKey, metric2EPHeader);
                }

                SourceEpHeader epHeader = metric2EPHeader.get(statisticsMetrics);

                if (!ALMFUtils.isNotNull(epHeader)) {
                    epHeader = new SourceEpHeader(anlsId, statisticsMetrics);

                    metric2EPHeader.put(statisticsMetrics, epHeader);
                }

                epHeader.getSourceEpCurves().add(new SourceEpCurve(loss, ep));
            });

        logger.info("extracting AnalysisSummaryStats for RDS {}:{}", rmds.getRmsId(), rmds.getName());

        List<Map<String, Object>> rmsSummaryData = extractAllAnalysisSummaryStats(rmds.getInstanceId(),
                rmds.getRmsId(),
                rmds.getName(),
                fpCodes,
                analysisIdsList);

        if (ALMFUtils.isNotNull(rmsSummaryData))
            rmsSummaryData.forEach(map -> {
                Long anlsId = ((Integer) map.get("analysis_id")).longValue();
                String financialPerspective = (String) map.get("fin_persp_code");
                Integer financialPerspectiveTYLabelId = (Integer) map.get("treaty_label_id");
                String financialPerspectiveTYLabel = (String) map.get("treaty_label");
                String financialPerspectiveTYTag = (String) map.get("treaty_tag");
                Double purePremium = (Double) map.get("pure_premium");
                Double stdDev = (Double) map.get("std_dev");
                Double cov = (Double) map.get("cov");
                String treatyType = (String) map.get("treaty_type_code");
                String occurrenceBasis = (String) map.get("occurrence_basis");

                if ("TY".equals(financialPerspective) && "WORK".equals(treatyType))
                    occurrenceBasis = "PerRisk";
                else
                    occurrenceBasis = "PerEvent";

                String hashkey = ALMFUtils.makeSourceEPHeaderHashKey(anlsId,
                        financialPerspective,
                        financialPerspectiveTYLabelId);

                Map<Integer, SourceEpHeader> metric2EPCurves = key2Metric2EPHeader.get(hashkey);

                if (ALMFUtils.isNotNull(metric2EPCurves)) {
                    AnalysisFinancialPerspective afp = new AnalysisFinancialPerspective(financialPerspective,
                            financialPerspectiveTYLabel,
                            financialPerspectiveTYLabelId);

                    afp.setTreatyType(treatyType);
                    afp.setDefaultOccurrenceBasis(occurrenceBasis);

                    if ("TY".equals(financialPerspective))
                        afp.setTreatyTag(financialPerspectiveTYTag);

                    if (ALMFUtils.isNotNull(metric2EPCurves.values()))
                        metric2EPCurves.values().forEach(epHeader -> {
                            epHeader.setAnalysisFinancialPerspective(afp);
                            epHeader.setSourceSummaryStatistic(new SourceSummaryStatistic(purePremium,
                                    stdDev,
                                    cov));
                        });
                }

                return;
            });

        Map<String, RmsAnalysis> sourceResultMapByAnalysisId = ALMFUtils.mapByAnalysisId(srList);

        if (ALMFUtils.isNotNull(key2Metric2EPHeader) && ALMFUtils.isNotEmpty(key2Metric2EPHeader.values()))
            key2Metric2EPHeader.values().forEach(map -> {
                if (ALMFUtils.isNotNull(map) && ALMFUtils.isNotEmpty(map.values()))
                    map.values().forEach(epHeader -> {
                        RmsAnalysis rmsAnalysis = sourceResultMapByAnalysisId.get(epHeader.getAnalysisId());

                        if (ALMFUtils.isNotNull(rmsAnalysis) && ALMFUtils.isNotNull(rmsAnalysis.getSourceEpHeader()))
                            rmsAnalysis.getSourceEpHeader().add(epHeader);
                        else
                            logger.warn("SourceResult#{} not found for associating SourceEpHeader",
                                    epHeader.getAnalysisId());
                    });
            });

        Map<Long, List<RmsAnalysisProfileRegion>> srRegionPerilsMap = extractAnalysisAnlsRegionsStats(rmds.getInstanceId(),
                rmds.getRmsId(),
                rmds.getName(),
                analysisIdsList);

        if (ALMFUtils.isNotNull(srRegionPerilsMap))
            srRegionPerilsMap.entrySet().forEach(entry -> {
                RmsAnalysis rmsAnalysis = sourceResultMapByAnalysisId.get(entry.getKey());

                if (ALMFUtils.isNotNull(rmsAnalysis))
                    rmsAnalysis.setSourceResultProfileRegions(entry.getValue());
                else
                    logger.warn("SourceResult#{} not found for associating SourceResultRegionPeril", entry.getKey());
            });

        logger.trace("Extracted epcurves & stats: {}", key2Metric2EPHeader.keySet());

        logger.info("extracting AnalysisTreatyStructure for RDS {}:{}", rmds.getRmsId(), rmds.getName());

        List<Map<String, Object>> rmsAnalysisTreatyStructureData = extractAllAnalysisTreatyStructure(rmds.getInstanceId(),
                rmds.getRmsId(),
                rmds.getName(),
                analysisIdsList);

        if (ALMFUtils.isNotNull(rmsAnalysisTreatyStructureData))
            rmsAnalysisTreatyStructureData.forEach(map -> {
                AnalysisTreatyStructure analysisTreatyStructure = new AnalysisTreatyStructure();

                analysisTreatyStructure.setAnalysisId((Long) map.get("analysis_id"));
                analysisTreatyStructure.setTreatyId((Integer) map.get("treaty_id"));
                analysisTreatyStructure.setTreatyNum((String) map.get("treaty_num"));
                analysisTreatyStructure.setTreatyName((String) map.get("treaty_name"));
                analysisTreatyStructure.setTreatyType((String) map.get("treaty_type"));
                analysisTreatyStructure.setRiskLimit(((Double) map.get("risk_limit")).floatValue());
                analysisTreatyStructure.setOccurenceLimit(((Double) map.get("occurrence_limit")).floatValue());
                analysisTreatyStructure.setAttachmentPoint(((Double) map.get("attachment_point")).floatValue());
                analysisTreatyStructure.setLob((String) map.get("lob"));
                analysisTreatyStructure.setCedant((String) map.get("cedant"));
                analysisTreatyStructure.setPctCovered(((Double) map.get("pct_covered")).floatValue());
                analysisTreatyStructure.setPctPlaced(((Double) map.get("pct_placed")).floatValue());
                analysisTreatyStructure.setPctRiShared(((Double) map.get("pct_ri_share")).floatValue());
                analysisTreatyStructure.setPctRetention(((Double) map.get("pct_retention")).floatValue());
                analysisTreatyStructure.setNoofReinstatements((Integer) map.get("noof_reinstatements"));
                analysisTreatyStructure.setInuringPriority((Integer) map.get("inuring_priority"));
                analysisTreatyStructure.setCcyCode((String) map.get("ccy_code"));
                analysisTreatyStructure.setAttachmentBasis((String) map.get("attachment_basis"));
                analysisTreatyStructure.setExposureLevel((String) map.get("exposure_level"));

                RmsAnalysis rmsAnalysis = sourceResultMapByAnalysisId.get(analysisTreatyStructure.getAnalysisId());

                if (ALMFUtils.isNotNull(rmsAnalysis))
                    rmsAnalysis.getAnalysisTreatyStructures().add(analysisTreatyStructure);
                else
                    logger.warn("RmsAnalysis#{} not found for associating AnalysisTreatyStructureData",
                            analysisTreatyStructure.getAnalysisId());
            });
        // @formatter:on
    }

    /**
     * extract portfolio scan level 2
     *
     * @param emds
     * @param pfList
     * @param currency
     */
    public void extractPortfolioScanLevel2(RmsModelDatasource emds, List<RmsPortfolio> pfList, String currency) {
        // @formatter:off
        if (ALMFUtils.isNotNull(emds)
                && emds.getType() == ModelDataSourceType.EDM
                && ALMFUtils.isNotNull(pfList)
                && ALMFUtils.isNotEmpty(pfList)) {

            List<String> portfolioIdList = new ArrayList<>();

            pfList.forEach(rmsPortfolio -> {
                String pfId = String.valueOf(rmsPortfolio.getPortfolioId())
                        .concat("~")
                        .concat(rmsPortfolio.getType());

                portfolioIdList.add(pfId.concat("~").concat(ALMFUtils.isNotNull(currency) ? currency : "USD"));
            });

            Integer runId = createEDMPortfolioContext(emds.getInstanceId(),
                    emds.getRmsId(),
                    emds.getName(),
                    portfolioIdList,
                    new ArrayList<String>());

            Map<String, List<RmsPortfolioAnalysisRegion>> map = extractPortfolioAnalysisRegionNew(emds.getInstanceId(),
                    emds.getRmsId(),
                    emds.getName(),
                    runId);

            removeEDMPortfolioContext(emds.getInstanceId(), runId);

            pfList.forEach(rmsPortfolio -> {
                String pfKey = rmsPortfolio.getType()
                        .concat(String
                                .valueOf(rmsPortfolio.getPortfolioId()));

                List<RmsPortfolioAnalysisRegion> rmsPortfolioAnalysisRegions = map.remove(pfKey);

                if (!ALMFUtils.isNotNull(rmsPortfolioAnalysisRegions))
                    rmsPortfolioAnalysisRegions = new ArrayList<>();

                if (!ALMFUtils.isNotEmpty(rmsPortfolioAnalysisRegions))
                    logger.warn("no AnalysisRegion for Portfolio#{} in EDM#{}",
                            new Object[]{rmsPortfolio.getPortfolioId(), emds.getRmsId()});

                rmsPortfolio.setAnalysisRegions(rmsPortfolioAnalysisRegions);
            });
        }
        // @formatter:on
    }

    public ExposureSummaryExtractOutput extractExposureSummary(Map<MultiKey, String> mapMultiKeyRRIP,
                                                               RmsModelDatasource edm, List<String> portfolioIdList, List<String> portfolioExcludeRegionPeril,
                                                               ModelingExposureDataSource modelingExposureDataSource, Project project, ExposureSummaryExtractInput esei) {
        // @formatter:off
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
                dsCache.getDataSource(edm.getInstanceId()));
        List<String> errorMessages = new ArrayList<>();
        List<ExposureSummary> exposureSummaryResultList = new ArrayList<>();
        List<RmsExposureSummary> rmsExposureSummaryResultList = new ArrayList<>();
        Map<String, List<RmsExposureSummary>> rmsExposureSummaryResultMap = new HashMap<>();
        Integer runId = createEDMPortfolioContext(edm.getInstanceId(),
                edm.getRmsId(),
                edm.getName(),
                portfolioIdList,
                portfolioExcludeRegionPeril);

        List<ExposureSummaryDefinition> exposureSummaryDefinitions = exposureSummaryReferenceCacheService
                .getAllExposureSummaryDefinition();
        List<SystemExposureSummaryDefinition> sourceExposureSummaryToProcesses = new ArrayList<>();
        List<SystemExposureSummaryDefinition> derivedExposureSummaryToProcesses = new ArrayList<>();
        List<SystemExposureSummaryDefinition> conditionalExposureSummaryToProcesses = new ArrayList<>();

        exposureSummaryDefinitions.forEach(exposureSummaryDefinition -> {
            if (exposureSummaryDefinition.getEnabled() == Boolean.TRUE) {
                SystemExposureSummaryDefinition sysdef = exposureSummaryReferenceCacheService
                        .getSystemExposureSummary("RMS",
                                "RISKLINK",
                                "",
                                exposureSummaryDefinition
                                        .getExposureSummaryAlias());
                if (ALMFUtils.isNotNull(sysdef)
                        && sysdef.getExposureSummaryType() == ExposureSummaryType.SOURCE
                        && ALMFUtils.isNotNull(sysdef.getExposureSummaryDefinition()))
                    sourceExposureSummaryToProcesses.add(sysdef);

                if (ALMFUtils.isNotNull(sysdef)
                        && sysdef.getExposureSummaryType() == ExposureSummaryType.DERIVED
                        && ALMFUtils.isNotNull(sysdef.getExposureSummaryDefinition()))
                    derivedExposureSummaryToProcesses.add(sysdef);

                if (ALMFUtils.isNotNull(sysdef)
                        && sysdef.getExposureSummaryType() == ExposureSummaryType.CONDITIONNAL
                        && ALMFUtils.isNotNull(sysdef.getExposureSummaryDefinition()))
                    conditionalExposureSummaryToProcesses.add(sysdef);
            }
        });

        try {
            if (ALMFUtils.isNotNull(runId) && runId > 0) {
                sourceExposureSummaryToProcesses.forEach(sourceExposureSummaryToProcess -> {
                    try {
                        String procedure = sourceExposureSummaryToProcess.getExposureSummaryExtractionProcedure();
                        List<RmsExposureSummary> summaries = extractRmsExposureSummaryByQuery(edm,
                                runId,
                                namedParameterJdbcTemplate,
                                sourceExposureSummaryToProcess,
                                procedure);
                        if (ALMFUtils.isNotNull(summaries) && ALMFUtils.isNotEmpty(summaries)) {
                            summaries.forEach(summary -> summary.setProject(project));

                            rmsExposureSummaryResultMap.put(sourceExposureSummaryToProcess.getExposureSummaryAlias(), summaries);
                        }
                    } catch (Throwable th) {
                        logger.error("error when extracting RMS exposureSummary '{}'", sourceExposureSummaryToProcess
                                .getExposureSummaryAlias());
                        logger.error(th.getMessage(), th);

                        Map<String, Object> params = new HashMap<>();

                        params.put("edm_id", edm.getRmsId());
                        params.put("edm_name", edm.getName());
                        params.put("run_id", runId);

                        errorMessages.add(th.getMessage() + "Params: " + params);

                        throw th;
                    }
                });


                if (ALMFUtils.isNotNull(esei))
                    esei.setRunId(runId);

            }

            rmsExposureSummaryResultMap.entrySet().forEach(entry -> {
                entry.getValue().forEach(rmsExposureSummary -> {
                    try {
                        ExposureSummary es = transformSummary(rmsExposureSummary.getExposureSummaryDefinition(),
                                rmsExposureSummary);

                        MultiKey key = new MultiKey(rmsExposureSummary.getEdm().getRmsId(),
                                rmsExposureSummary.getPortfolioId(),
                                rmsExposureSummary.getPortfolioType());

                        if (ALMFUtils.isNotNull(mapMultiKeyRRIP))
                            es.setRrPortfolioId(mapMultiKeyRRIP.get(key));

                        es.setModelingExposureDataSource(modelingExposureDataSource);

                        rmsExposureSummaryResultList.add(rmsExposureSummary);
                        exposureSummaryResultList.add(es);
                    } catch (Throwable th) {
                        logger.error("error when processing source exposureSummary '{}'",
                                rmsExposureSummary.getExposureSummaryDefinition()
                                        .getExposureSummaryAlias());

                        logger.error(th.getMessage(), th);

                        errorMessages.add(th.getMessage());

                        throw th;
                    }
                });
            });

            derivedExposureSummaryToProcesses.forEach(derivedExposureSummaryToProcess -> {
                try {
                    List<RmsExposureSummary> rmsExposureSummaries = rmsExposureSummaryResultMap
                            .get(derivedExposureSummaryToProcess
                                    .getBasedOnSummaryAlias());

                    if (ALMFUtils.isNotNull(rmsExposureSummaries) && ALMFUtils.isNotEmpty(rmsExposureSummaries)) {
                        rmsExposureSummaries.forEach(rmsExposureSummary -> {
                            ExposureSummary es = transformSummary(derivedExposureSummaryToProcess,
                                    rmsExposureSummary);

                            MultiKey key = new MultiKey(rmsExposureSummary.getEdm().getRmsId(),
                                    rmsExposureSummary.getPortfolioId(),
                                    rmsExposureSummary.getPortfolioType());

                            if (ALMFUtils.isNotNull(mapMultiKeyRRIP))
                                es.setRrPortfolioId(mapMultiKeyRRIP.get(key));

                            es.setModelingExposureDataSource(modelingExposureDataSource);

                            rmsExposureSummaryResultList.add(rmsExposureSummary);
                            exposureSummaryResultList.add(es);
                        });
                    } else
                        logger.debug("derived summary '{}' cannot be processed as the source '{}' is not available",
                                new Object[]{derivedExposureSummaryToProcess.getExposureSummaryAlias(),
                                        derivedExposureSummaryToProcess.getBasedOnSummaryAlias()});
                } catch (Throwable th) {
                    logger.error("error when processing derived exposureSummary '{}'",
                            derivedExposureSummaryToProcess.getExposureSummaryAlias());

                    logger.error(th.getMessage(), th);

                    errorMessages.add(th.getMessage());

                    throw th;
                }
            });

            for (SystemExposureSummaryDefinition conditionalExposureSummaryToProcess : conditionalExposureSummaryToProcesses) {
                if (StringUtils.isBlank(conditionalExposureSummaryToProcess.getConditionExpression()))
                    logger.warn("the condition is empty for the SystemExposureSummaryDefinition '{}', skipping",
                            conditionalExposureSummaryToProcess.getExposureSummaryAlias());

                try {
                    logger.debug("the condition for the SystemExposureSummaryDefinition '{}' is '{}'",
                            conditionalExposureSummaryToProcess.getExposureSummaryAlias(),
                            conditionalExposureSummaryToProcess.getConditionExpression());

                    ExposureSummaryConditionParser parser = new ExposureSummaryConditionParser(
                            new StringReader(conditionalExposureSummaryToProcess
                                    .getConditionExpression()));
                    ConditionRoot cr = parser.parseStatement();
                    boolean decisionResult = false;

                    if (StringUtils.equalsIgnoreCase(cr.getRoot(), "Portfolio"))
                        decisionResult = true;
                    else if (StringUtils.equalsIgnoreCase(cr.getRoot(), "Exposure")) {
                        List<RmsExposureSummary> rmsExposureSummaries = rmsExposureSummaryResultMap
                                .get(conditionalExposureSummaryToProcess
                                        .getBasedOnSummaryAlias());

                        if (ALMFUtils.isNotNull(rmsExposureSummaries) && ALMFUtils.isNotEmpty(rmsExposureSummaries)) {
                            for (RmsExposureSummary rmsExposureSummary : rmsExposureSummaries) {
                                ExposureSummaryValueExtractor esve = new ExposureSummaryValueExtractor(regionPerilMappingCacheService);

                                logger.debug(
                                        "ConditionnalSummary '{}' found it's Exposure Summary to be worked on : '{}'",
                                        conditionalExposureSummaryToProcess.getExposureSummaryAlias(),
                                        conditionalExposureSummaryToProcess.getBasedOnSummaryAlias());
                                for (RmsExposureSummaryItem exposureSummary : rmsExposureSummary.getExposureSummaryList()) {
                                    esve.setObject(exposureSummary);

                                    decisionResult = cr.checkCondition(esve);

                                    if (decisionResult)
                                        break;
                                }

                                if (decisionResult)
                                    break;
                            }
                        } else
                            logger.debug(
                                    "ConditionnalSummary '{}' didn't find it's Exposure Summary to be worked on : '{}'",
                                    conditionalExposureSummaryToProcess.getExposureSummaryAlias(),
                                    conditionalExposureSummaryToProcess.getBasedOnSummaryAlias());
                    } else
                        logger.warn("unknown processing mode '{}' for conditionnal Exposure Summary '{}'",
                                new Object[]{cr.getRoot(),
                                        conditionalExposureSummaryToProcess.getExposureSummaryAlias()});

                    logger.debug("ConditionnalSummary '{}' decision : '{}'",
                            conditionalExposureSummaryToProcess.getExposureSummaryAlias(),
                            decisionResult);

                    if (decisionResult) {
                        List<RmsExposureSummary> condSummaries = extractRmsExposureSummaryByQuery(edm,
                                runId,
                                namedParameterJdbcTemplate,
                                conditionalExposureSummaryToProcess,
                                conditionalExposureSummaryToProcess
                                        .getExposureSummaryExtractionProcedure());

                        if (ALMFUtils.isNotNull(condSummaries) && ALMFUtils.isNotEmpty(condSummaries)) {
                            condSummaries.forEach(rmsExposureSummary -> {
                                rmsExposureSummary.setProject(project);

                                ExposureSummary es = transformSummary(conditionalExposureSummaryToProcess,
                                        rmsExposureSummary);
                                MultiKey key = new MultiKey(rmsExposureSummary.getEdm().getRmsId(),
                                        rmsExposureSummary.getPortfolioId(),
                                        rmsExposureSummary.getPortfolioType());

                                if (ALMFUtils.isNotNull(mapMultiKeyRRIP))
                                    es.setRrPortfolioId(mapMultiKeyRRIP.get(key));

                                es.setModelingExposureDataSource(modelingExposureDataSource);

                                exposureSummaryResultList.add(es);
                                rmsExposureSummaryResultList.add(rmsExposureSummary);
                            });
                        }
                    }
                } catch (Throwable th) {
                    logger.error("error when processing conditionnal exposureSummary '{}'",
                            conditionalExposureSummaryToProcess.getExposureSummaryAlias());

                    logger.error(th.getMessage(), th);

                    errorMessages.add(th.getMessage());

                    throw new RuntimeException(th);
                }
            }

            ExposureSummaryExtractOutput eseo = new ExposureSummaryExtractOutput();

            eseo.rmsExposureSummaries.addAll(rmsExposureSummaryResultList);
            eseo.exposureSummaries.addAll(exposureSummaryResultList);
            eseo.runId = runId;
            eseo.errorMessages = errorMessages;

            return eseo;
        } finally {
        }
        // @formatter:on
    }

    /**
     * extract exposure summary
     *
     * @param mapMultiKeyRRIP
     * @param esei
     * @return
     */
    public ExposureSummaryExtractOutput extractExposureSummary(Map<MultiKey, String> mapMultiKeyRRIP,
                                                               ExposureSummaryExtractInput esei) {
        // @formatter:off
        Project project = esei.getModelingExposureDataSource().getProject();
        RmsModelDatasource edm = esei.getModelingExposureDataSource().getRmsModelDataSource();

        List<String> portfolioIdList = new ArrayList<>();
        List<String> portfolioExcludeRegionPeril = new ArrayList<>();

        esei.getPortfolioList().forEach(portfolioExposureSummaryExtractInput -> {
            RmsPortfolio pf = portfolioExposureSummaryExtractInput.getPortfolio().getRmsPortfolio();
            String pfID = String.valueOf(pf.getPortfolioId()).concat("~").concat(pf.getType());

            portfolioIdList.add(pfID.concat("~")
                    .concat(ALMFUtils.isNotNull(portfolioExposureSummaryExtractInput.getConformedCurrency())
                            ? portfolioExposureSummaryExtractInput.getConformedCurrency()
                            : "USD"));

            if (ALMFUtils.isNotNull(portfolioExposureSummaryExtractInput.getRegionPerilToExclude())
                    && ALMFUtils.isNotEmpty(portfolioExposureSummaryExtractInput.getRegionPerilToExclude()))
                portfolioExcludeRegionPeril.add(
                        pfID.concat("~")
                                .concat(StringUtils.join(portfolioExposureSummaryExtractInput.getRegionPerilToExclude(), '~')));
        });

        return extractExposureSummary(mapMultiKeyRRIP,
                edm,
                portfolioIdList,
                portfolioExcludeRegionPeril,
                esei.getModelingExposureDataSource(),
                project,
                esei);
        // @formatter:on
    }

    /**
     * extract detailed exposure
     *
     * @param file
     * @param edm
     * @param extractName
     * @param portfolioIdList
     * @param portfolioExcludeRegionPeril
     * @param runId
     * @param portfolioId
     * @param portfolioType
     * @return
     * @throws Exception
     */
    public Map<String, List<String>> extractDetailedExposure(File file, RmsModelDatasource edm, String extractName,
                                                             List<String> portfolioIdList, List<String> portfolioExcludeRegionPeril, Integer runId, String portfolioId,
                                                             String portfolioType) throws Exception {
        // @formatter:off
        List<String> errors = new ArrayList<>();
        List<String> warnings = new ArrayList<>();
        boolean needToRemove = false;

        if (!ALMFUtils.isNotNull(runId)) {
            warnings.add(
                    "runId is null. Create a new one with parameters:"
                            .concat(" InstanceId=")
                            .concat(edm.getInstanceId())
                            .concat(" RmsId=")
                            .concat(String.valueOf(edm.getRmsId()))
                            .concat(" Name=")
                            .concat(edm.getName())
                            .concat(" portfolioIdList=")
                            .concat(portfolioIdList.toString())
                            .concat(" portfolioExcludeRegionPeril=")
                            .concat(portfolioExcludeRegionPeril.toString()));

            runId = createEDMPortfolioContext(edm.getInstanceId(),
                    edm.getRmsId(),
                    edm.getName(),
                    portfolioIdList,
                    portfolioExcludeRegionPeril);

            needToRemove = true;
        }

        if (ALMFUtils.isNotNull(runId) && runId > 0) {
            List<GenericDescriptor> descriptors = extractSchema(edm.getInstanceId(), extractName);

            if (!ALMFUtils.isNotNull(descriptors) || !ALMFUtils.isNotEmpty(descriptors))
                logger.error("Error: retrieve no descriptors");

            extractDetailedExposureToFile(edm.getInstanceId(),
                    edm.getRmsId(),
                    edm.getName(),
                    runId,
                    portfolioId,
                    portfolioType,
                    descriptors,
                    file);

            if (needToRemove)
                removeEDMPortfolioContext(edm.getInstanceId(), runId);
        } else {
            logger.error("RunID is null or equal to 0");

            errors.add("RunID is null or equal to 0");
        }

        Map<String, List<String>> res = new HashMap<>();

        res.put("Error", errors);
        res.put("Warning", warnings);

        return res;
        // @formatter:on
    }

    /**
     * release context
     *
     * @param edm
     * @param runId
     */
    public void releaseContext(RmsModelDatasource edm, Integer runId) {
        if (ALMFUtils.isNotNull(edm) && ALMFUtils.isNotNull(runId))
            removeEDMPortfolioContext(edm.getInstanceId(), runId);
    }

    /**
     * extract location level exposure details
     *
     * @param edm
     * @param portfolio
     * @param file
     * @param extractName
     * @param sqlQuery
     * @return
     */
    public boolean extractLocationLevelExposureDetails(RmsModelDatasource edm, Portfolio portfolio, File file,
                                                       String extractName, String sqlQuery) {
        if (!ModelDataSourceType.EDM.equals(edm.getType()))
            return false;

        Map<String, Object> dataQueryParams = new HashMap<>();

        dataQueryParams.put("Edm_id", edm.getRmsId());
        dataQueryParams.put("Edm_name", edm.getName());
        dataQueryParams.put("PortfolioID_RMS", portfolio.getRmsPortfolio().getPortfolioId());
        dataQueryParams.put("PortfolioID_RR", portfolio.getRmsPortfolio().getRmsPortfolioId());

        if (LocationLevelExposure.EXTRACT_PORT.getCode().equals(extractName)) {
            dataQueryParams.put("ProjectID_RR", portfolio.getProject().getProjectId())/*.replaceFirst("^P-0+(?!$)", ""))*/;
        } else if ((LocationLevelExposure.EXTRACT_PORT_ACCOUNT_POL.getCode().equals(extractName))
                || (LocationLevelExposure.EXTRACT_PORT_ACCOUNT_POL_CVG.getCode().equals(extractName))
                || (LocationLevelExposure.EXTRACT_PORT_ACCOUNT_LOC_CVG.getCode().equals(extractName)))
            dataQueryParams.put("ConformedCcy", portfolio.getTargetCurrency());

        List<GenericDescriptor> descriptors = extractSchema(edm.getInstanceId(), extractName);

        if (!ALMFUtils.isNotNull(descriptors) || !ALMFUtils.isNotEmpty(descriptors)) {
            logger.error("Error: retrieve no descriptors");

            return false;
        }

        extractExposureToFile(edm.getInstanceId(), sqlQuery, dataQueryParams, descriptors, file);

        return true;
    }

    /**
     * extract Rms Multiple Region Peril
     *
     * @param rmsAnalysis
     * @return
     */
    public List<RmsMultipleRegionPeril> extractRmsMultipleRegionPeril(RmsAnalysis rmsAnalysis) {
        // @formatter:off
        Map<String, Object> params = new HashMap<>();
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
                getTemplate(rmsAnalysis.getRmsModelDatasource()
                        .getInstanceId()));


        params.put("rdm_id", rmsAnalysis.getRdmId());
        params.put("rdm_name", rmsAnalysis.getRdmName());
        params.put("analysis_id_list", rmsAnalysis.getAnalysisId());

        logger.debug("analysisMultiRegionPerilsQuery : {}", analysisMultiRegionPerilsQuery);

        logger.debug("params : {}", params);

        logger.debug("extractRmsMultipleRegionPeril | analysisSQLQuery: {}, params: {}", analysisMultiRegionPerilsQuery,
                params);

        List<RmsMultipleRegionPeril> rmsMultipleRegionPerils = namedParameterJdbcTemplate.query(analysisMultiRegionPerilsQuery,
                params,
                new RmsMultipleRegionPerilRowMapper());

        return rmsMultipleRegionPerils;
        // @formatter:on
    }

    /**
     * get template
     *
     * @param instanceId
     * @return
     */
    private JdbcTemplate getTemplate(String instanceId) {
        // @formatter:off
        if (!ALMFUtils.isNotNull(instanceId) || StringUtils.isBlank(instanceId))
            throw new RuntimeException("RMS InstanceId cannot be null or empty");

        try {
            DataSource ds = dsCache.getDataSource(instanceId);
            JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);

            return jdbcTemplate;
        } catch (Exception e) {
            logger.error("init error : {}", e);

            throw new RuntimeException(e);
        }
        // @formatter:on
    }

    /**
     * extract analysis anlsRegionsStats
     *
     * @param instanceId
     * @param rdmId
     * @param rdmName
     * @param analysisIdsList
     * @return
     */
    private Map<Long, List<RmsAnalysisProfileRegion>> extractAnalysisAnlsRegionsStats(String instanceId, long rdmId,
                                                                                      String rdmName, String analysisIdsList) {
        // @formatter:off
        Map<String, Object> params = new HashMap<>();
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(getTemplate(instanceId));

        params.put("rdm_id", rdmId);
        params.put("rdm_name", rdmName);

        if (StringUtils.isNotEmpty(analysisIdsList))
            params.put("analysis_id_list", analysisIdsList);

        logger.debug("extractAnalysisAnlsRegionsStats | analysisAnlsRegionsStatsSQLQuery: {}, params: {}",
                analysisAnlsRegionsStatsSQLQuery, params);

        List<Map<String, Object>> rs = namedParameterJdbcTemplate.queryForList(analysisAnlsRegionsStatsSQLQuery,
                params);
        Map<Long, List<RmsAnalysisProfileRegion>> result = new HashMap<>();
        Set<MultiKey> uniqueId = new HashSet<>();

        rs.forEach(map -> {
            if (ALMFUtils.isNotNull(map.get("analysis_id"))) {
                Long analysisId = ((Integer) map.get("analysis_id")).longValue();
                String analysisRegion = StringUtils.trim((String) map.get("analysis_region"));
                String peril = StringUtils.trim((String) map.get("peril"));
                String profileRegion = StringUtils.trim((String) map.get("profile_region"));
                MultiKey key = new MultiKey(analysisId, analysisRegion, peril, profileRegion);

                if (uniqueId.contains(key))
                    return;

                uniqueId.add(key);

                List<RmsAnalysisProfileRegion> list = result.get(analysisId);

                if (!ALMFUtils.isNotNull(list)) {
                    list = new ArrayList<>();

                    result.put(analysisId, list);
                }

                list.add(new RmsAnalysisProfileRegion(
                        StringUtils.trim((String) map.get("analysis_region")),
                        StringUtils.trim((String) map.get("analysis_region_name")),
                        StringUtils.trim((String) map.get("profile_region")),
                        StringUtils.trim((String) map.get("profile_region_name")),
                        StringUtils.trim((String) map.get("peril")),
                        (BigDecimal) map.get("aal")));
            }
        });

        return result;
        // @formatter:on
    }

    /**
     * extract portfolio analysis region new
     *
     * @param instanceId
     * @param edmId
     * @param edmName
     * @param runId
     * @return
     */
    private Map<String, List<RmsPortfolioAnalysisRegion>> extractPortfolioAnalysisRegionNew(String instanceId,
                                                                                            long edmId, String edmName, Integer runId) {
        // @formatter:off
        Map<String, Object> params = new HashMap<>();
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(getTemplate(instanceId));

        params.put("edm_id", edmId);
        params.put("edm_name", edmName);
        params.put("run_id", runId);

        logger.debug("executing extractPortfolioAnalysisRegion in EDM Context with parameters [{},{},{}]",
                new Object[]{edmId, edmName, runId});

        logger.debug("extractPortfolioAnalysisRegionNew | portfolioAnalysisRegionSQLQueryInEDMContext: {}, params: {}",
                portfolioAnalysisRegionSQLQueryInEDMContext, params);

        List<Map<String, Object>> rs = namedParameterJdbcTemplate.queryForList(
                portfolioAnalysisRegionSQLQueryInEDMContext,
                params);
        Map<String, List<RmsPortfolioAnalysisRegion>> result = new HashMap<>();

        rs.forEach(map -> {
            Long portfolioId = ((Integer) map.get("port_id")).longValue();
            String portfolioType = ((String) map.get("port_type"));
            String portfolioKey = portfolioType.concat(String.valueOf(portfolioId));
            List<RmsPortfolioAnalysisRegion> list = result.get(portfolioKey);

            logger.debug("got PortfolioRegion : {}", new Object[]{map});

            if (!ALMFUtils.isNotNull(list)) {
                list = new ArrayList<>();

                result.put(portfolioKey, list);
            }

            if (StringUtils.isNotEmpty(StringUtils.trim((String) map.get("AnalysisRegionCode")))) {
                Double rateToUsd = ((BigDecimal) map.get("expo_ccy_roe_usd")).doubleValue();

                list.add(new RmsPortfolioAnalysisRegion(
                        StringUtils.trim((String) map.get("AnalysisRegionCode")),
                        StringUtils.trim((String) map.get("AnalysisRegionDesc")),
                        StringUtils.trim((String) map.get("Peril")),
                        ((BigDecimal) map.get("TotalTiv")).doubleValue() / rateToUsd,
                        StringUtils.trim((String) map.get("expo_ccy")),
                        rateToUsd,
                        (Integer) map.get("LocCount")));
            }
        });

        return result;
        // @formatter:on
    }

    /**
     * make rms exposure summary key
     *
     * @param edmId
     * @param portfolioId
     * @param portfolioTYpe
     * @param exposureSummaryName
     * @param peril
     * @return
     */
    private MultiKey makeRmsExposureSummaryKey(Long edmId, Long portfolioId, String portfolioTYpe,
                                               String exposureSummaryName, String peril) {
        return new MultiKey(new Object[]{edmId, portfolioId, portfolioTYpe, exposureSummaryName, peril});
    }

    /**
     * extract rms exposure summary by query
     *
     * @param edm
     * @param runId
     * @param template
     * @param sesd
     * @param procedure
     * @return
     */
    private List<RmsExposureSummary> extractRmsExposureSummaryByQuery(RmsModelDatasource edm, Integer runId,
                                                                      NamedParameterJdbcTemplate template, SystemExposureSummaryDefinition sesd, String procedure) {
        // @formatter:off
        logger.debug("will exec exposureSummaryProcedure '{}' with runid:{} on edm {}:'{}'",
                new Object[]{procedure, runId, edm.getRmsId(), edm.getName()});

        long time = System.currentTimeMillis();
        Map<String, Object> params = new HashMap<>();

        params.put("edm_id", edm.getRmsId());
        params.put("edm_name", edm.getName());
        params.put("run_id", runId);

        logger.debug("query: execute [{}].[{}].[{}] @Edm_id=:edm_id, @Edm_name=:edm_name, @RunID=:run_id",
                rmsDatabaseName, exposureSummarySchema, procedure);

        logger.debug("params : {}", params);

        List<RmsExposureSummaryItem> rmsExposureSummaryItemList = template.query(
                "execute [" + rmsDatabaseName + "].[" + exposureSummarySchema + "]"
                        + ".[" + procedure + "] @Edm_id=:edm_id, @Edm_name=:edm_name, "
                        + "@RunID=:run_id",
                params, new RmsExposureSummaryItemMapper(edm.getRmsId()));
        Map<MultiKey, RmsExposureSummary> summaries = new HashMap<>();

        if (ALMFUtils.isNotNull(rmsExposureSummaryItemList) && ALMFUtils.isNotEmpty(rmsExposureSummaryItemList))
            rmsExposureSummaryItemList.forEach(rmsExposureSummaryItem -> {
                RmsExposureSummary summary = summaries.get(makeRmsExposureSummaryKey(edm.getRmsId(),
                        rmsExposureSummaryItem.getPortfolioId(),
                        rmsExposureSummaryItem.getPortfolioType(),
                        sesd.getExposureSummaryAlias(),
                        rmsExposureSummaryItem.getPeril()));

                if (!ALMFUtils.isNotNull(summary)) {
                    summary = new RmsExposureSummary();

                    summary.setEdm(edm);
                    summary.setImportStatus(null);
                    summary.setExposureSummaryName(sesd.getExposureSummaryAlias());
                    summary.setExposureSummaryDefinition(sesd);
                    summary.setPortfolioId(rmsExposureSummaryItem.getPortfolioId());
                    summary.setPortfolioType(rmsExposureSummaryItem.getPortfolioType());
                    summary.setPeril(rmsExposureSummaryItem.getPeril());

                    summaries.put(makeRmsExposureSummaryKey(edm.getRmsId(),
                            rmsExposureSummaryItem.getPortfolioId(),
                            rmsExposureSummaryItem.getPortfolioType(),
                            sesd.getExposureSummaryAlias(),
                            rmsExposureSummaryItem.getPeril()), summary);
                }

                summary.getExposureSummaryList().add(rmsExposureSummaryItem);
            });

        logger.debug("exec exposureSummaryProcedure '{}' with runid:{} on edm {}:'{}' took {} ms",
                new Object[]{procedure, runId, edm.getRmsId(), edm.getName(), (System.currentTimeMillis() - time)});

        return new ArrayList<RmsExposureSummary>(summaries.values());
        // @formatter:on
    }

    /**
     * transform summary
     *
     * @param sesd
     * @param rmsExposureSummary
     * @return
     */
    private ExposureSummary transformSummary(SystemExposureSummaryDefinition sesd,
                                             RmsExposureSummary rmsExposureSummary) {
        // @formatter:off
        Map<MultiKey, ExposureSummaryItem> tempAggregate = new HashMap<>();
        ExposureSummary es = new ExposureSummary();

        es.setExposureSummaryDefinition(sesd.getExposureSummaryDefinition());
        es.setExposureSummaryAlias(sesd.getExposureSummaryAlias());
        es.setImportStatus(null);
        es.setProject(rmsExposureSummary.getProject());
        es.setPortfolioId(rmsExposureSummary.getPortfolioId());
        es.setPortfolioType(rmsExposureSummary.getPortfolioType());
        es.setPeril(rmsExposureSummary.getPeril());

        if (ALMFUtils.isNotNull(rmsExposureSummary.getExposureSummaryList())
                && ALMFUtils.isNotEmpty(rmsExposureSummary.getExposureSummaryList()))
            rmsExposureSummary.getExposureSummaryList().forEach(rmsExposureSummaryItem -> {
                ExposureSummaryItem itemOut = new ExposureSummaryItem();

                itemOut.setCountryCode(rmsExposureSummaryItem.getCountryCode());
                itemOut.setAdmin1Code(rmsExposureSummaryItem.getAdmin1Code());
                itemOut.setAnalysisRegionCode(rmsExposureSummaryItem.getAnalysisRegionCode());
                itemOut.setConformedCurrency(rmsExposureSummaryItem.getConformedCurrency());
                itemOut.setExposureCurrency(rmsExposureSummaryItem.getExposureCurrency());
                itemOut.setConformedCurrencyUSDRate(rmsExposureSummaryItem.getConformedCurrencyUSDRate());
                itemOut.setExposureCurrencyUSDRate(rmsExposureSummaryItem.getExposureCurrencyUSDRate());
                itemOut.setRateDate(new java.sql.Date(rmsExposureSummaryItem.getRateDate().getTime()));
                itemOut.setEdmId(rmsExposureSummaryItem.getEdmId());
                itemOut.setPortfolioId(rmsExposureSummaryItem.getPortfolioId());
                itemOut.setPortfolioType(rmsExposureSummaryItem.getPortfolioType());
                itemOut.setFinancialPerspective(rmsExposureSummaryItem.getFinancialPerspective());

                if (StringUtils.equalsIgnoreCase(rmsExposureSummaryItem.getPeril(), "Total")) {
                    itemOut.setPeril(rmsExposureSummaryItem.getPeril());
                    itemOut.setRegionPerilCode("Unmapped");
                    itemOut.setRegionPerilGroupCode("Unmapped");
                } else {
                    RegionPeril rp = regionPerilMappingCacheService.findRegionPerilByCountryCodeAdmin1CodePerilCode(
                            rmsExposureSummaryItem.getCountryCode(),
                            rmsExposureSummaryItem.getAdmin1Code(),
                            rmsExposureSummaryItem.getPeril());
                    if (!ALMFUtils.isNotNull(rp))
                        rp = regionPerilMappingCacheService.findRegionPerilByCountryCodeAdmin1CodePerilCode(
                                rmsExposureSummaryItem.getCountryCode().toUpperCase(),
                                "",
                                rmsExposureSummaryItem.getPeril().toUpperCase());

                    if (ALMFUtils.isNotNull(rp)) {
                        itemOut.setPeril(rp.getPerilCode());
                        itemOut.setRegionPerilCode(rp.getRegionPerilCode());
                        itemOut.setRegionPerilGroupCode(rp.getRegionPerilGroupCode());
                    } else {
                        itemOut.setPeril(rmsExposureSummaryItem.getPeril());
                        itemOut.setRegionPerilCode("Unmapped");
                        itemOut.setRegionPerilGroupCode("Unmapped");
                    }
                }

                itemOut.setLocationCount(rmsExposureSummaryItem.getLocationCount());
                itemOut.setTotalTIV(rmsExposureSummaryItem.getTotalTiv());

                if (ALMFUtils.isNotNull(sesd.getExposureSummaryAxisDefinitions())
                        && ALMFUtils.isNotEmpty(sesd.getExposureSummaryAxisDefinitions()))
                    sesd.getExposureSummaryAxisDefinitions().forEach(axisConformerDefinition -> {
                        traduceAxis(axisConformerDefinition,
                                rmsExposureSummary,
                                rmsExposureSummaryItem,
                                itemOut);
                    });

                MultiKey itemKey = makeExposureSummaryStorageKey(itemOut);
                ExposureSummaryItem itemAgg = tempAggregate.get(itemKey);

                if (ALMFUtils.isNotNull(itemAgg)) {
                    itemAgg.setLocationCount(itemAgg.getLocationCount() + itemOut.getLocationCount());
                    itemAgg.setTotalTIV(itemAgg.getTotalTIV() + itemOut.getTotalTIV());
                } else
                    tempAggregate.put(itemKey, itemOut);
            });

        es.getExposureSummaryItems().addAll(tempAggregate.values());

        return es;
        // @formatter:on
    }

    /**
     * make exposure summary storage key
     *
     * @param item
     * @return
     */
    private MultiKey makeExposureSummaryStorageKey(ExposureSummaryItem item) {
        // @formatter:off
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
                item.getEdmId(),
                item.getExposureCurrency(),
                item.getFinancialPerspective(),
                item.getPeril(),
                item.getPortfolioId(),
                item.getPortfolioType(),
                item.getRegionPerilCode(),
                item.getRegionPerilGroupCode()});
        // @formatter:on
    }

    /**
     * traduce axis
     *
     * @param acd
     * @param summary
     * @param itemIn
     * @param itemOut
     */
    private void traduceAxis(AxisConformerDefinition acd, RmsExposureSummary summary, RmsExposureSummaryItem itemIn,
                             ExposureSummaryItem itemOut) {
        // @formatter:off
        String value = null;
        Integer valueSortOrder = null;

        switch (acd.getSourceAxis()) {
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
                if (StringUtils.equalsIgnoreCase(itemIn.getPeril(), "Total"))
                    value = "Unmapped";
                else {
                    RegionPeril rp = this.regionPerilMappingCacheService.findRegionPerilByCountryCodeAdmin1CodePerilCode(
                            itemIn.getCountryCode(),
                            itemIn.getAdmin1Code(),
                            itemIn.getPeril());

                    if (!ALMFUtils.isNotNull(rp))
                        rp = regionPerilMappingCacheService.findRegionPerilByCountryCodeAdmin1CodePerilCode(
                                itemIn.getCountryCode().toUpperCase(),
                                "",
                                itemIn.getPeril().toUpperCase());

                    if (ALMFUtils.isNotNull(rp))
                        value = rp.getRegionPerilCode();
                    else
                        value = "UNKOWN : '" + itemIn.getCountryCode() + ":" + itemIn.getAdmin1Code() + ":"
                                + itemIn.getPeril() + "'";
                }

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
                if (StringUtils.equalsIgnoreCase(itemIn.getPeril(), "Total"))
                    value = "Unmapped";
                else {
                    RegionPeril rp = this.regionPerilMappingCacheService.findRegionPerilByCountryCodeAdmin1CodePerilCode(
                            itemIn.getCountryCode(),
                            itemIn.getAdmin1Code(),
                            itemIn.getPeril());

                    if (!ALMFUtils.isNotNull(rp))
                        rp = regionPerilMappingCacheService.findRegionPerilByCountryCodeAdmin1CodePerilCode(
                                itemIn.getCountryCode().toUpperCase(),
                                "",
                                itemIn.getPeril().toUpperCase());

                    if (ALMFUtils.isNotNull(rp))
                        value = rp.getRegionPerilGroupCode();
                    else
                        value = "UNKOWN : '" + itemIn.getCountryCode() + ":" + itemIn.getAdmin1Code() + ":"
                                + itemIn.getPeril() + "'";
                }

                break;
            case UNDEFINED:

                break;
            default:
                logger.warn("unknown axis name '{}'", ToStringBuilder.reflectionToString(acd));

                break;
        }

        switch (acd.getAxisConformerMode()) {
            case REFERENCE:
                ExposureSummaryConformerReference ref = exposureSummaryReferenceCacheService.getConformer(
                        "RMS",
                        "RISKLINK",
                        "",
                        acd.getAxisConformerAlias(),
                        value);
                if (ALMFUtils.isNotNull(ref)) {
                    value = ref.getOutputCode();

                    if (ALMFUtils.isNotNull(ref.getSortOrder()))
                        valueSortOrder = ref.getSortOrder();
                } else
                    logger.trace("ExposureSummaryConformerReference not found for tuple '{}':'{}':'{}':'{}':'{}'",
                            new Object[]{"RMS", "RISKLINK", "", acd.getAxisConformerAlias(), value});

                break;
            case FUNCTION:
                value = resolveFunctionnalMapping(acd.getAxisConformerAlias(), summary, itemIn);

                break;
            case COPY:

                break;
            default:
                logger.warn("unknown AxisConformerDefinition type : {}", ToStringBuilder.reflectionToString(acd));
        }

        switch (acd.getTargetAxis()) {
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
                itemOut.setPeril(value);

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
                logger.warn("unknown axis name '{}'", acd);

                break;
        }
        // @formatter:on
    }

    /**
     * resolve functionnal mapping
     *
     * @param functionAlias
     * @param summary
     * @param itemIn
     * @return
     */
    private String resolveFunctionnalMapping(String functionAlias, RmsExposureSummary summary,
                                             RmsExposureSummaryItem itemIn) {
        if (StringUtils.equalsIgnoreCase("PortfolioFunction", functionAlias))
            return "EDM " + summary.getEdm().getRmsId() + " : " + summary.getEdm().getName() + " | "
                    + itemIn.getPortfolioId() + ":" + itemIn.getPortfolioType();

        logger.warn("functionAlias '{}' is not implemented", functionAlias);

        return null;
    }

    /**
     * create EDM portfolio context
     *
     * @param dataSource
     * @param edmId
     * @param edmName
     * @param portfolioList
     * @param portfolioExcludedRegionPeril
     * @return
     */
    private Integer createEDMPortfolioContext(String dataSource, Long edmId, String edmName, List<String> portfolioList,
                                              List<String> portfolioExcludedRegionPeril) {
        // @formatter:off
        DataSource ds = dsCache.getDataSource(dataSource);
        CreateEDMSummaryStoredProcedure proc = new CreateEDMSummaryStoredProcedure(new JdbcTemplate(ds, true),
                createEDMPortfolioContextSQL);
        String portfolioIdParam = "";
        String portfolioExcludedRegionPerilParam = "";

        if (ALMFUtils.isNotNull(portfolioList) && ALMFUtils.isNotEmpty(portfolioList))
            portfolioIdParam = StringUtils.join(portfolioList, ',');

        if (ALMFUtils.isNotNull(portfolioExcludedRegionPeril) && ALMFUtils.isNotEmpty(portfolioExcludedRegionPeril))
            portfolioExcludedRegionPerilParam = StringUtils.join(portfolioExcludedRegionPeril, ',');

        long time = System.currentTimeMillis();
        Map<String, Object> result = proc.execute(edmId, edmName, portfolioIdParam, portfolioExcludedRegionPerilParam);
        Integer runId = (Integer) result.get("RunID");

        logger.debug(
                "created EDM Portfolio context #{} for EDM {}:{} and Portfolio ID(s) '{}' with portfolioExcludedRegionPeril(s) '{}'",
                new Object[]{runId, edmId, edmName, portfolioIdParam, portfolioExcludedRegionPerilParam});

        logger.debug("created EDM Portfolio context #{} for EDM {}:{} took {} ms",
                new Object[]{runId, edmId, edmName, (System.currentTimeMillis() - time)});

        return runId;
        // @formatter:on
    }

    /**
     * remove EDM portfolio context
     *
     * @param dataSource
     * @param runId
     */
    private void removeEDMPortfolioContext(String dataSource, Integer runId) {
        // @formatter:off
        DataSource ds = dsCache.getDataSource(dataSource);
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(ds);
        Map<String, Object> params = new HashMap<>();

        params.put("RunID", runId);

        logger.debug("removeEDMPortfolioContextSQL: {}, params: {}, runId: {}", removeEDMPortfolioContextSQL, params,
                runId);

        jdbcTemplate.update(removeEDMPortfolioContextSQL, params);
        // @formatter:on
    }

    /**
     * extract schema
     *
     * @param instanceId
     * @param extractName
     * @return
     */
    private List<GenericDescriptor> extractSchema(String instanceId, String extractName) {
        // @formatter:off
        logger.debug("extractSchema");

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(getTemplate(instanceId));
        Map<String, Object> schemaQueryParams = new HashMap<>();

        schemaQueryParams.put("extract_name", extractName);

        logger.debug("extractSchema | schemaSqlQuery: {}, params: {}", getExtractSchemaSqlQuery, schemaQueryParams);

        List<GenericDescriptor> descriptors = namedParameterJdbcTemplate.query(getExtractSchemaSqlQuery,
                schemaQueryParams,
                new GenericDescriptorMapper());

        return descriptors;
        // @formatter:on
    }

    /**
     * extract detailed exposure to file
     *
     * @param instanceId
     * @param edmId
     * @param edmName
     * @param runId
     * @param portfolioId
     * @param portfolioType
     * @param descriptors
     * @param file
     */
    private void extractDetailedExposureToFile(String instanceId, Long edmId, String edmName, Integer runId,
                                               String portfolioId, String portfolioType, List<GenericDescriptor> descriptors, File file) {
        // @formatter:off
        logger.debug("extractDetailedExposureToFile");

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(getTemplate(instanceId));
        Map<String, Object> dataQueryParams = new HashMap<>();

        dataQueryParams.put("edm_id", edmId);
        dataQueryParams.put("edm_name", edmName);
        dataQueryParams.put("runid", runId);

        if (ALMFUtils.isNotNull(portfolioId) && ALMFUtils.isNotNull(portfolioType)) {
            dataQueryParams.put("Portfolio_Id", portfolioId);
            dataQueryParams.put("Portfolio_Type", portfolioType);
        }

        ExposureExtractor extractor = new ExposureExtractor(descriptors, file);

        logger.debug("run query {} with params {}", getEdmDefailSummarySqlQuery, dataQueryParams);

        namedParameterJdbcTemplate.query(getEdmDefailSummarySqlQuery, dataQueryParams, extractor);
        // @formatter:on
    }

    /**
     * extract exposure to file
     *
     * @param instanceId
     * @param sqlQuery
     * @param dataQueryParams
     * @param descriptors
     * @param file
     */
    private void extractExposureToFile(String instanceId, String sqlQuery, Map<String, Object> dataQueryParams,
                                       List<GenericDescriptor> descriptors, File file) {
        // @formatter:off
        logger.debug("run query {} with params {}", sqlQuery, dataQueryParams);

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(getTemplate(instanceId));
        ExposureExtractor extractor = new ExposureExtractor(descriptors, file);

        namedParameterJdbcTemplate.query(sqlQuery, dataQueryParams, extractor);
        // @formatter:on
    }

}
