package com.scor.rr.service;

import com.scor.rr.domain.*;
import com.scor.rr.mapper.*;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.*;


@Component
@Slf4j
@Transactional
public class RmsService {

    @Autowired
    @Qualifier("jdbcRms")
    JdbcTemplate rmsJdbcTemplate;

    @Value("${rms.database}")
    private String DATABASE;

    private final Logger logger = LoggerFactory.getLogger(RmsService.class);

    public List<DataSource> listAvailableDataSources() {
        String sql = "execute " + DATABASE + ".dbo.RR_RL_ListAvailableDataSources";
        this.logger.debug("Service starts executing the query ...");
        List<DataSource> dataSources = rmsJdbcTemplate.query(
                sql,
                new DataSourceRowMapper());
        this.logger.debug("the data returned ", dataSources);
        return dataSources;
    }

//
//    public void addEdmRdms(List<DataSource> dataSources, Integer projectId, String instanceId, String instanceName) {
//        Set<MultiKey> selectedDataSources = new HashSet<>();
//        for (DataSource dataSource : dataSources) {
//            selectedDataSources.add(new MultiKey(dataSource.getType(), instanceId, dataSource.getRmsId().toString()));
//        }
//        List<RlModelDataSource> existingRlModelDataSources = rlModelDataSourcesRepository.findByProjectId(projectId);
//        for (RlModelDataSource existingRlModelDataSource : existingRlModelDataSources) {
//            if (!selectedDataSources.contains(new MultiKey(existingRlModelDataSource.getType(), existingRlModelDataSource.getInstanceId(), existingRlModelDataSource.getRlId()))) {
//                rlModelDataSourcesRepository.delete(existingRlModelDataSource);
//            }
//        }
//
//        for (DataSource dataSource : dataSources) {
//            RlModelDataSource rlModelDataSource = rlModelDataSourcesRepository.findByProjectIdAndTypeAndInstanceIdAndRlId
//                    (projectId, dataSource.getType(), instanceId, dataSource.getRmsId().toString());
//            if (rlModelDataSource == null) {
//                rlModelDataSource = new RlModelDataSource(dataSource, projectId, instanceId, instanceName);
//                rlModelDataSourcesRepository.save(rlModelDataSource);
//                if ("RDM".equals(rlModelDataSource.getType())) {
//                    scanAnalysisBasicForRdm(rlModelDataSource);
//                }
//            }
//        }
//
//    }
//
//    public void scanAnalysisBasicForRdm(RlModelDataSource rdm) {
//        List<RdmAnalysisBasic> rdmAnalysisBasics = listRdmAnalysisBasic(Long.parseLong(rdm.getRlId()), rdm.getName());
//        for (RdmAnalysisBasic rdmAnalysisBasic : rdmAnalysisBasics) {
//            RLAnalysis rlAnalysis = this.rlAnalysisRepository.save(
//                    new RLAnalysis(rdmAnalysisBasic, rdm)
//            );
//            RlAnalysisScanStatus rlAnalysisScanStatus = new RlAnalysisScanStatus(rlAnalysis.getRlAnalysisId(), 0);
//            rlAnalysisScanStatusRepository.save(rlAnalysisScanStatus);
//        }
//    }
//
//    public void scanAnalysisDetail(List<AnalysisHeader> rlAnalysisList, Integer projectId) {
//        Map<MultiKey, List<Long>> analysisByRdms = new HashMap<>();
//
//        rlAnalysisList.stream().map(item -> new MultiKey(item.getRdmId(), item.getRdmName())).distinct()
//                .forEach(key -> analysisByRdms.put(key, this.getAnalysisIdByRdm((BigInteger) key.getKey(0), (String) key.getKey(1), rlAnalysisList)));
//
//        for (Map.Entry<MultiKey, List<Long>> multiKeyListEntry : analysisByRdms.entrySet()) {
//            Long rdmId = ((BigInteger) multiKeyListEntry.getKey().getKey(0)).longValue();
//            String rdmName = (String) multiKeyListEntry.getKey().getKey(1);
//            this.listRdmAnalysis(rdmId, rdmName, multiKeyListEntry.getValue()).forEach(rdmAnalysis -> {
//                this.rlAnalysisRepository.updateAnalysiById(projectId, rdmAnalysis );
//            });
//        }
//
//    }
//
//
//    private List<Long> getAnalysisIdByRdm(BigInteger rdmId, String rdmName, List<AnalysisHeader> rlAnalysisList) {
//        return rlAnalysisList.stream().filter(item -> item.getRdmId().equals(rdmId) && item.getRdmName().equals(rdmName))
//                .map(AnalysisHeader::getAnalysisId).map(Integer::longValue).collect(toList());
//    }

    public List<RdmAnalysisBasic> listRdmAnalysisBasic(Long id, String name) {
        String sql = "execute " + DATABASE + ".dbo.RR_RL_ListRdmAnalysisBasic @rdm_id=" + id + " ,@rdm_name=" + name;
        this.logger.debug("Service starts executing the query ...");
        List<RdmAnalysisBasic> rdmAnalysisBasic = rmsJdbcTemplate.query(
                sql, new RdmAnalysisBasicRowMapper()
        );
        this.logger.debug("the data returned ", rdmAnalysisBasic);
        return rdmAnalysisBasic;
    }

    public List<EdmPortfolioBasic> listEdmPortfolioBasic(Long id, String name) {
        String sql = "execute " + DATABASE + ".dbo.RR_RL_ListEdmPortfolioBasic @edm_id=" + id + ",@edm_name=" + name;
        this.logger.debug("Service starts executing the query ...");
        List<EdmPortfolioBasic> edmPortfolioBasic = rmsJdbcTemplate.query(
                sql, new EdmPortfolioBasicRowMapper()
        );
        this.logger.debug("the data returned ", edmPortfolioBasic);
        return edmPortfolioBasic;
    }


    public List<RdmAnalysis> listRdmAnalysis(Long id, String name, List<Long> analysisIdList) {
        String query = "execute " + DATABASE + ".dbo.RR_RL_ListRdmAnalysis @rdm_id=" + id + ", @rdm_name=" + name;
        List<RdmAnalysis> rdmAnalysis = new ArrayList<>();
        this.logger.debug("Service starts executing the query ...");
        if (analysisIdList != null) {
            String sql = query + ",@analysis_id_list=" + analysisIdList;
            rdmAnalysis = rmsJdbcTemplate.query(
                    sql, new RdmAnalysisRowMapper()
            );
        } else {
            rdmAnalysis = rmsJdbcTemplate.query(
                    query, new RdmAnalysisRowMapper()
            );
        }
        this.logger.debug("the data returned ", rdmAnalysis);
        return rdmAnalysis;
    }

    public List<EdmPortfolio> listEdmPortfolio(Long id, String name, List<String> portfolioList) {
        String query = "execute " + DATABASE + ".dbo.RR_RL_ListEdmPortfolio @edm_id=" + id + ",@edm_name=" + name;
        List<EdmPortfolio> edmPortfolios = new ArrayList<>();
        this.logger.debug("Service starts executing the query ...");

        if (portfolioList != null) {
            String sql = query + ",@portfolio_id_list=" + portfolioList;
            edmPortfolios = rmsJdbcTemplate.query(
                    sql, new EdmPortfolioRowMapper()
            );
        } else {
            edmPortfolios = rmsJdbcTemplate.query(
                    query, new EdmPortfolioRowMapper()
            );
        }
        this.logger.debug("the data returned ", edmPortfolios);
        return edmPortfolios;
    }

    public List<RdmAnalysisEpCurves> listRdmAllAnalysisEpCurves(Long id, String name, int epPoints, List<Long> analysisIdList, List<String> finPerspList) {

        String LIST = "";
        if (finPerspList != null) {
            for (String s : finPerspList) {

                LIST += s + ",";
            }
            LIST = LIST.substring(0, LIST.length() - 1);
        }
        List<RdmAnalysisEpCurves> rdmAnalysisEpCurves = new ArrayList<>();

        String query = "execute " + DATABASE + ".dbo.RR_RL_GetRdmAllAnalysisEpCurves @rdm_id=" + id + ", @rdm_name=" + name + ",@ep_points=" + epPoints;
        this.logger.debug("Service starts executing the query ...");
        if (analysisIdList != null && !LIST.isEmpty()) {
            String sql = query + ", @fin_persp_list=" + "'" + LIST + "'" + ", @analysis_id_list=" + analysisIdList;
            rdmAnalysisEpCurves = rmsJdbcTemplate.query(
                    sql,
                    new RdmAnalysisEpCurvesRowMapper()
            );
        }
        if (analysisIdList != null && LIST.isEmpty()) {
            String sql = query + ", @analysis_id_list=" + analysisIdList;
            rdmAnalysisEpCurves = rmsJdbcTemplate.query(
                    sql,
                    new RdmAnalysisEpCurvesRowMapper()
            );
        }
        if (analysisIdList == null && !LIST.isEmpty()) {
            String sql = query + ", @fin_persp_list=" + "'" + LIST + "'";
            rdmAnalysisEpCurves = rmsJdbcTemplate.query(
                    sql,
                    new RdmAnalysisEpCurvesRowMapper()
            );
        }
        if (analysisIdList == null && LIST.isEmpty()) {
            rdmAnalysisEpCurves = rmsJdbcTemplate.query(
                    query,
                    new RdmAnalysisEpCurvesRowMapper()
            );
        }
        this.logger.debug("the data returned ", rdmAnalysisEpCurves);
        return rdmAnalysisEpCurves;
    }

    public List<RdmAllAnalysisSummaryStats> getRdmAllAnalysisSummaryStats(Long rdmId, String rdmName, List<String> finPerspList, List<Long> analysisIdList) {

        String LIST = "";
        if (finPerspList != null) {
            for (String s : finPerspList) {

                LIST += s + ",";
            }
            LIST = LIST.substring(0, LIST.length() - 1);
        }
        //finPerspList.stream().collect(Collectors.joining(","));

        List<RdmAllAnalysisSummaryStats> rdmAllAnalysisSummaryStats = new ArrayList<>();

        String query = "execute " + DATABASE + ".dbo.RR_RL_GetRdmAllAnalysisSummaryStats @rdm_id=" + rdmId + ", @rdm_name=" + rdmName;
        this.logger.debug("Service starts executing the q uery ...");
        if (analysisIdList != null && !LIST.isEmpty()) {
            String sql = query + ", @fin_persp_list=" + "'" + LIST + "'" + ", @analysis_id_list=" + analysisIdList;
            rdmAllAnalysisSummaryStats = rmsJdbcTemplate.query(
                    sql,
                    new RdmAllAnalysisSummaryStatsRowMapper()
            );

        }
        if (analysisIdList != null && LIST.isEmpty()) {
            String sql = query + ", @analysis_id_list=" + analysisIdList;
            rdmAllAnalysisSummaryStats = rmsJdbcTemplate.query(
                    sql,
                    new RdmAllAnalysisSummaryStatsRowMapper()
            );
        }
        if (analysisIdList == null && !LIST.isEmpty()) {
            String sql = query + ", @rdm_name=" + rdmName + ", @fin_persp_list=" + "'" + LIST + "'";
            rdmAllAnalysisSummaryStats = rmsJdbcTemplate.query(
                    sql,
                    new RdmAllAnalysisSummaryStatsRowMapper()
            );
        }
        if (analysisIdList == null && LIST.isEmpty()) {
            rdmAllAnalysisSummaryStats = rmsJdbcTemplate.query(
                    query,
                    new RdmAllAnalysisSummaryStatsRowMapper()
            );
        }
        this.logger.debug("the data returned ", rdmAllAnalysisSummaryStats);
        return rdmAllAnalysisSummaryStats;
    }

    public List<AnalysisEpCurves> getAnalysisEpCurves(int rdmID, String rdmName, int analysisId, String finPerspCode, Integer treatyLabelId) {
        List<AnalysisEpCurves> analysisEpCurves = new ArrayList<>();
        String query = "execute " + DATABASE + ".dbo.RR_RL_GetAnalysisEpCurves @rdm_id=" + rdmID + ", @rdm_name=" + rdmName + ", @analysis_id=" + analysisId + ", @fin_persp_code=" + finPerspCode;
        this.logger.debug("Service starts executing the query ...");
        if (treatyLabelId != null) {
            String sql = query + ",@treaty_label_id=" + treatyLabelId;
            analysisEpCurves = rmsJdbcTemplate.query(
                    sql,
                    new AnalysisEpCurvesRowMapper()
            );
        } else {
            analysisEpCurves = rmsJdbcTemplate.query(
                    query,
                    new AnalysisEpCurvesRowMapper()
            );
        }
        this.logger.debug("the data returned ", analysisEpCurves);
        return analysisEpCurves;
    }

    public List<AnalysisSummaryStats> getAnalysisSummaryStats(int rdmId, String rdmName, int analysisId, String fpCode, Integer treatyLabelId) {

        List<AnalysisSummaryStats> analysisSummaryStats = new ArrayList<>();
        String query = " execute " + DATABASE + ".dbo.RR_RL_GetAnalysisSummaryStats @rdm_id=" + rdmId + ", @rdm_name=" + rdmName + ", @analysis_id=" + analysisId + ", @fin_persp_code=" + fpCode;
        this.logger.debug("Service starts executing the query ...");
        if (treatyLabelId != null) {
            String sql = query + ",@treaty_label_id=" + treatyLabelId;
            analysisSummaryStats = rmsJdbcTemplate.query(
                    sql, new AnalysisSummaryStatsRowMapper()
            );
        } else {
            analysisSummaryStats = rmsJdbcTemplate.query(
                    query, new AnalysisSummaryStatsRowMapper()
            );

        }
        this.logger.debug("the data returned ", analysisSummaryStats);
        return analysisSummaryStats;
    }

    public List<RdmAllAnalysisProfileRegions> getRdmAllAnalysisProfileRegions(int rdmId, String rdmName, List<Integer> analysisIdList) {

        List<RdmAllAnalysisProfileRegions> rdmAllAnalysisProfileRegions = new ArrayList<>();
        String query = "execute " + DATABASE + ".dbo.RR_RL_GetRdmAllAnalysisProfileRegions @rdm_id=" + rdmId + ",@rdm_name=" + rdmName;
        this.logger.debug("Service starts executing the query ...");
        if (analysisIdList != null) {

            String sql = query + ",@analysis_id_list=" + analysisIdList;
            rdmAllAnalysisProfileRegions = rmsJdbcTemplate.query(sql, new RdmAllAnalysisProfileRegionsRowMapper());
        } else {
            rdmAllAnalysisProfileRegions = rmsJdbcTemplate.query(query, new RdmAllAnalysisProfileRegionsRowMapper());
        }
        this.logger.debug("the data returned ", rdmAllAnalysisProfileRegions);
        return rdmAllAnalysisProfileRegions;
    }

    public List<AnalysisElt> getAnalysisElt(int rdmId, String rdmName, int analysisId, String finPerspCode, Integer treatyLabelId) {
        String query = "execute " + DATABASE + ".dbo.RR_RL_GetAnalysisElt @rdm_id=" + rdmId + ", @rdm_name=" + rdmName + ", @analysis_id=" + analysisId
                + ", @fin_persp_code=" + finPerspCode;
        List<AnalysisElt> analysisElt = new ArrayList<>();
        this.logger.debug("Service starts executing the query ...");
        if (treatyLabelId != null) {
            String sql = query + ", @treaty_label_id=" + treatyLabelId;
            analysisElt = rmsJdbcTemplate.query(sql, new AnalysisEltRowMapper());
        } else {
            analysisElt = rmsJdbcTemplate.query(query, new AnalysisEltRowMapper());
        }
        this.logger.debug("the data returned ", analysisElt);
        return analysisElt;
    }

    public List<EdmAllPortfolioAnalysisRegions> getEdmAllPortfolioAnalysisRegions(int edmId, String edmName, String ccy) {

        List<EdmAllPortfolioAnalysisRegions> edmAllPortfolioAnalysisRegions = new ArrayList<>();

        String sql = "execute " + DATABASE + ".dbo.RR_RL_GetEdmAllPortfolioAnalysisRegions @edm_id=" + edmId + ", @edm_name=" + edmName + ", @Ccy=" + ccy;
        this.logger.debug("Service starts executing the query ...");
        edmAllPortfolioAnalysisRegions = rmsJdbcTemplate.query(sql, new EdmAllPortfolioAnalysisRegionsRowMapper());
        this.logger.debug("the data returned ", edmAllPortfolioAnalysisRegions);
        return edmAllPortfolioAnalysisRegions;
    }

    public List<RdmAllAnalysisTreatyStructure> getRdmAllAnalysisTreatyStructure(int rdmId, String rdmName, List<Integer> analysisIdList) {

        List<RdmAllAnalysisTreatyStructure> rdmAllAnalysisTreatyStructure = new ArrayList<>();
        String query = "execute " + DATABASE + ".dbo.RR_RL_GetRdmAllAnalysisTreatyStructure @rdm_id=" + rdmId + ", @rdm_name=" + rdmName;
        this.logger.debug("Service starts executing the query ...");
        if (analysisIdList != null) {
            String sql = query + ", @analysis_id_list=" + analysisIdList;
            rdmAllAnalysisTreatyStructure = rmsJdbcTemplate.query(sql, new RdmAllAnalysisTreatyStructureRowMapper());
        } else {
            rdmAllAnalysisTreatyStructure = rmsJdbcTemplate.query(query, new RdmAllAnalysisTreatyStructureRowMapper());
        }
        this.logger.debug("the data returned ", rdmAllAnalysisTreatyStructure);
        return rdmAllAnalysisTreatyStructure;
    }

    public List<RdmAllAnalysisMultiRegionPerils> getRdmAllAnalysisMultiRegionPerils(int rdmId, String rdmName, List<Integer> analysisIdList) {

        List<RdmAllAnalysisMultiRegionPerils> rdmAllAnalysisMultiRegionPerils = new ArrayList<>();

        String query = "execute " + DATABASE + ".dbo.RR_RL_GetRdmAllAnalysisMultiRegionPerils @rdm_id=" + rdmId + ", @rdm_name=" + rdmName;
        this.logger.debug("Service starts executing the query ...");

        if (analysisIdList != null) {
            String sql = query + ", @analysis_id_list=" + analysisIdList;
            rdmAllAnalysisMultiRegionPerils = rmsJdbcTemplate.query(sql, new RdmAllAnalysisMultiRegionPerilsRowMapper());
        } else {
            rdmAllAnalysisMultiRegionPerils = rmsJdbcTemplate.query(query, new RdmAllAnalysisMultiRegionPerilsRowMapper());
        }


        this.logger.debug("the data returned ", rdmAllAnalysisMultiRegionPerils);
        return rdmAllAnalysisMultiRegionPerils;
    }

    public List<RmsExchangeRate> getRmsExchangeRates(List<String> ccyy) {
        String ccy = ccyy.toString().replaceAll(" ", "");
        List<RmsExchangeRate> rmsExchangeRate = new ArrayList<>();
        String sql = "execute " + DATABASE + ".[dbo].[RR_RL_GetRMSExchangeRates] @ccyList=" + ccy;
        this.logger.debug("Service starts executing the query ...");
        rmsExchangeRate = rmsJdbcTemplate.query(sql, new RmsExchangeRateRowMapper());
        this.logger.debug("the data returned ", rmsExchangeRate);
        return rmsExchangeRate;
    }

    public List<CChkBaseCcy> getCChBaseCcy() {
        String sql = " execute " + DATABASE + ".dbo.RR_RL_CChk_BaseCcy";
        List<CChkBaseCcy> cChkBaseCcy = new ArrayList<>();
        cChkBaseCcy = rmsJdbcTemplate.query(sql, new CChkBaseCcyRowMApper());
        return cChkBaseCcy;
    }

    public List<CChkBaseCcyFxRate> getCChkBaseCcyFxRate() {
        String sql = " execute " + DATABASE + ".dbo.RR_RL_CChk_BaseCcyFxRate";
        List<CChkBaseCcyFxRate> cChkBaseCcyFxRate = new ArrayList<>();
        cChkBaseCcyFxRate = rmsJdbcTemplate.query(sql, new CChkBaseCcyFxRateRowMapper());
        return cChkBaseCcyFxRate;
    }

    public String getAnalysisModellingOptionSettings(int rdmId, String rdmName, int analysisId) {
        String query = "execute " + DATABASE + ".[dbo].[RR_RL_GetAnalysisModellingOptionSettings] @rdm_id=" + rdmId + ", @rdm_name=" + rdmName + ", @analysis_id=" + analysisId;
        String json = "";
        List<Map<String, Object>> result = rmsJdbcTemplate.queryForList(query);
        if (result == null) {
            return null;
        }
        StringBuilder builder = new StringBuilder("");
        for (Map<String, Object> map : result) {
            if (map != null) {
                for (Object o : map.values()) {
                    if (o != null) {
                        builder.append(o.toString());
                    }
                }
            }
        }
        try {
            JSONObject jsonObj = XML.toJSONObject(builder.toString());
            json = jsonObj.toString(4);
            return json;
        } catch (JSONException e) {
            e.printStackTrace();
            this.logger.debug("Conversion failed", e);
        }
        return json;
    }
}
