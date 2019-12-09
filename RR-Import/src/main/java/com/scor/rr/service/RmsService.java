package com.scor.rr.service;

import com.scor.rr.configuration.RmsInstanceCache;
import com.scor.rr.domain.*;
import com.scor.rr.domain.dto.*;
import com.scor.rr.domain.enums.StatisticMetric;
import com.scor.rr.domain.riskLink.*;
import com.scor.rr.mapper.*;
import com.scor.rr.repository.*;
import com.scor.rr.util.Utils;
import org.apache.commons.collections.keyvalue.MultiKey;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.stereotype.Component;

import java.io.File;
import java.sql.Types;
import java.util.*;

import static java.util.stream.Collectors.toList;

@Component
public class RmsService {

    private final Logger logger = LoggerFactory.getLogger(RmsService.class);

    @Autowired
    private RLModelDataSourceRepository rlModelDataSourcesRepository;

    @Autowired
    private RLAnalysisScanStatusRepository rlAnalysisScanStatusRepository;

    @Autowired
    private RLAnalysisRepository rlAnalysisRepository;

    @Autowired
    private RLPortfolioRepository rlPortfolioRepository;

    @Autowired
    private RLImportSelectionRepository rlImportSelectionRepository;

    @Autowired
    private RLPortfolioScanStatusRepository rlPortfolioScanStatusRepository;

    @Autowired
    private RLPortfolioSelectionRepository rlPortfolioSelectionRepository;

    @Autowired
    private RLAnalysisProfileRegionsRepository rlAnalysisProfileRegionsRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FinancialPerspectiveRepository financialPerspectiveRepository;

    @Autowired
    private RLSourceEpHeaderRepository rlSourceEpHeaderRepository;

    @Autowired
    private RLPortfolioAnalysisRegionRepository rlPortfolioAnalysisRegionRepository;

    @Value("${rms.ds.dbname}")
    private String DATABASE;

    @Value("${rms.ds.dbname}.[dbo].[RR_RL_BaseEdmSummaryPrep]")
    private String createEDMPortfolioContextSQL;

    @Value("exec ${rms.ds.dbname}.[dbo].[RR_RL_BaseEdmSummaryClear] @RunID=:RunID")
    private String removeEDMPortfolioContextSQL;

    @Value(value = "extractLocationLevelSchemaQuery")
    private String extractLocationLevelSchemaQuery;

    @Value(value = "extractEdmDetailsSummarySqlQuery")
    private String getEdmDetailsSummarySqlQuery;

    @Autowired
    private RmsInstanceCache rmsInstanceCache;

    /********** Scan Basic / Detailed **********/

    public void basicScan(List<DataSource> dataSources, Long projectId, String instanceId, String instanceName) {

        for (DataSource dataSource : dataSources) {

            RLModelDataSource rlModelDataSource =
                    rlModelDataSourcesRepository.findByProjectIdAndTypeAndInstanceIdAndRlId(projectId, dataSource.getType(), instanceId, dataSource.getRmsId());

            if (rlModelDataSource != null) {
                rlAnalysisRepository.deleteByRlModelDataSourceId(rlModelDataSource.getRlModelDataSourceId());
                rlPortfolioRepository.deleteByRlModelDataSourceRlModelDataSourceId(rlModelDataSource.getRlModelDataSourceId());
            } else {
                rlModelDataSource = new RLModelDataSource(dataSource, projectId, instanceId, instanceName);
                rlModelDataSourcesRepository.save(rlModelDataSource);
            }

            if (rlModelDataSource.getType().equalsIgnoreCase("RDM")) {
                scanAnalysisBasicForRdm(instanceId, rlModelDataSource);
            } else if (rlModelDataSource.getType().equalsIgnoreCase("EDM")) {
                scanPortfolioBasicForEdm(instanceId, rlModelDataSource);
            }
        }

    }

    public void detailedScan(DetailedScanDto detailedScanDto) {
        this.scanAnalysisDetail(detailedScanDto.getInstanceId(), detailedScanDto.getRlAnalysisList(), detailedScanDto.getProjectId());
        this.scanPortfolioDetail(detailedScanDto.getInstanceId(), detailedScanDto.getRlPortfolioList(), detailedScanDto.getProjectId());
    }

    /********** END :Scan Basic / Detailed **********/

    /**
     * @param importSelectionDtoList
     * @implNote Method used to save analysis config (SourceResult). values that has been chosen by the user
     */
    public List<Long> saveAnalysisImportSelection(List<ImportSelectionDto> importSelectionDtoList) {

        if (importSelectionDtoList != null && !importSelectionDtoList.isEmpty())
            return importSelectionDtoList.stream()
                    .map(RLImportSelection::new)
                    .map(importSelection -> {
                        importSelection = rlImportSelectionRepository.save(importSelection);
                        return importSelection.getRlImportSelectionId();
                    }).collect(toList());
        return new ArrayList<>();
    }

    /**
     * @param portfolioImportSelectionDtoList
     * @implNote Method used to save portfolio config (PortfolioSelection). values that has been chosen by the user
     */
    public List<Long> savePortfolioImportSelection(List<PortfolioSelectionDto> portfolioImportSelectionDtoList) {

        if (portfolioImportSelectionDtoList != null && !portfolioImportSelectionDtoList.isEmpty())
            return portfolioImportSelectionDtoList.stream()
                    .map(RLPortfolioSelection::new)
                    .map(portfolioSelection -> {
                        portfolioSelection = rlPortfolioSelectionRepository.save(portfolioSelection);
                        return portfolioSelection.getRlPortfolioSelectionId();
                    }).collect(toList());
        return new ArrayList<>();
    }

    private void scanAnalysisBasicForRdm(String instanceId, RLModelDataSource rdm) {
        rlAnalysisRepository.deleteByRlModelDataSourceId(rdm.getRlModelDataSourceId());
        List<RdmAnalysisBasic> rdmAnalysisBasics = listRdmAnalysisBasic(instanceId, rdm.getRlId(), rdm.getName());
        for (RdmAnalysisBasic rdmAnalysisBasic : rdmAnalysisBasics) {
            RLAnalysis rlAnalysis = this.rlAnalysisRepository.save(
                    new RLAnalysis(rdmAnalysisBasic, rdm)
            );
            RLAnalysisScanStatus rlAnalysisScanStatus = new RLAnalysisScanStatus(rlAnalysis.getRlAnalysisId(), 0);
            rlAnalysisScanStatusRepository.save(rlAnalysisScanStatus);
        }
    }

    private void scanAnalysisDetail(String instanceId, List<AnalysisHeader> rlAnalysisList, Long projectId) {
        Map<MultiKey, List<Long>> analysisByRdms = new HashMap<>();

        List<Integer> epPoints = Arrays.asList(10, 50, 100, 250, 500, 1000);
        List<String> fpCodes = financialPerspectiveRepository.findAllCodes();

        if (rlAnalysisList != null && !rlAnalysisList.isEmpty()) {
            rlAnalysisList.stream().map(item -> new MultiKey(item.getRdmId(), item.getRdmName())).distinct()
                    .forEach(key -> analysisByRdms.put(key, this.getAnalysisIdByRdm((Long) key.getKey(0), (String) key.getKey(1), rlAnalysisList)));

            for (Map.Entry<MultiKey, List<Long>> multiKeyListEntry : analysisByRdms.entrySet()) {

                Long rdmId = (Long) multiKeyListEntry.getKey().getKey(0);
                String rdmName = (String) multiKeyListEntry.getKey().getKey(1);

                this.listRdmAnalysis(instanceId, rdmId, rdmName, multiKeyListEntry.getValue())
                        .stream()
                        .peek(rdmAnalysis -> this.rlAnalysisRepository.updateAnalysisById(projectId, rdmAnalysis))
                        .map(rdmAnalysis -> this.rlAnalysisRepository.findByProjectIdAndAnalysis(projectId, rdmAnalysis))
                        .forEach(rdmAnalysis -> this.rlAnalysisScanStatusRepository.updateScanLevelByRlModelAnalysisId(rdmAnalysis.getRlAnalysisId()));

                this.getRdmAllAnalysisProfileRegions(instanceId, rdmId, rdmName, multiKeyListEntry.getValue())
                        .stream()
                        .map(analysisProfileRegion -> {
                            RLAnalysis rlAnalysis = rlAnalysisRepository.findByRdmIdAndRdmNameAndRlId(rdmId, rdmName, analysisProfileRegion.getAnalysisId());
                            return new RLAnalysisProfileRegion(analysisProfileRegion, rlAnalysis);
                        })
                        .forEach(analysisProfileRegion -> rlAnalysisProfileRegionsRepository.save(analysisProfileRegion));

                this.extractSourceEpHeaders(instanceId, rdmId, rdmName, epPoints, multiKeyListEntry.getValue(), fpCodes);
            }
        }
    }

    private void scanPortfolioBasicForEdm(String instanceId, RLModelDataSource edm) {
        rlPortfolioRepository.deleteByRlModelDataSourceRlModelDataSourceId(edm.getRlModelDataSourceId());
        List<EdmPortfolioBasic> edmPortfolioBasics = listEdmPortfolioBasic(instanceId, edm.getRlId(), edm.getName());
        for (EdmPortfolioBasic edmPortfolioBasic : edmPortfolioBasics) {
            RLPortfolio rlPortfolio = this.rlPortfolioRepository.save(
                    new RLPortfolio(edmPortfolioBasic, edm)
            );
            RLPortfolioScanStatus rlAnalysisScanStatus = new RLPortfolioScanStatus(rlPortfolio, 0);
            rlPortfolioScanStatusRepository.save(rlAnalysisScanStatus);
        }
    }

    private void scanPortfolioDetail(String instanceId, List<PortfolioHeader> rlPortfolioList, Long projectId) {
        Map<MultiKey, List<Long>> portfolioByEdms = new HashMap<>();
        Map<MultiKey, List<String>> portfolioIdAndTypeAndCurrencyByEdms = new HashMap<>();

        if (rlPortfolioList != null && !rlPortfolioList.isEmpty()) {
            rlPortfolioList.stream().map(item -> new MultiKey(item.getEdmId(), item.getEdmName())).distinct()
                    .forEach(key -> {
                        portfolioByEdms.put(key, this.getPortfolioIdByEdm((Long) key.getKey(0), (String) key.getKey(1), rlPortfolioList));
                    });

            for (Map.Entry<MultiKey, List<Long>> multiKeyListEntry : portfolioByEdms.entrySet()) {

                Long edmId = (Long) multiKeyListEntry.getKey().getKey(0);
                String edmName = (String) multiKeyListEntry.getKey().getKey(1);
                this.listEdmPortfolio(instanceId, edmId, edmName, multiKeyListEntry.getValue())
                        .forEach(edmPortfolio -> this.rlPortfolioRepository.updatePortfolioById(projectId, edmPortfolio));

                this.getEdmAllPortfolioAnalysisRegions(instanceId, edmId, edmName, "USD")
                        .stream()
                        .map(portfolioAnalysisRegions -> {
                            RLPortfolio rlPortfolio = rlPortfolioRepository.findByEdmIdAndEdmNameAndPortfolioId(edmId, edmName, portfolioAnalysisRegions.getPortfolioId());
                            return new RLPortfolioAnalysisRegion(portfolioAnalysisRegions, rlPortfolio, "USD");
                        })
                        .forEach(analysisProfileRegion -> rlPortfolioAnalysisRegionRepository.save(analysisProfileRegion));
            }

        }
    }

    private List<Long> getAnalysisIdByRdm(Long rdmId, String rdmName, List<AnalysisHeader> rlAnalysisList) {
        return rlAnalysisList.stream().filter(item -> item.getRdmId().longValue() == rdmId.longValue() && item.getRdmName().equals(rdmName))
                .map(AnalysisHeader::getAnalysisId).collect(toList());
    }

    private List<Long> getPortfolioIdByEdm(Long edmId, String edmName, List<PortfolioHeader> rlPortfolioList) {
        return rlPortfolioList.stream().filter(item -> item.getEdmId().longValue() == edmId.longValue() && item.getEdmName().equals(edmName))
                .map(PortfolioHeader::getPortfolioId).collect(toList());
    }

    private List<String> getPortfolioIdPortfolioTypeCurrencyByEdm(Long edmId, String edmName, List<PortfolioHeader> rlPortfolioList) {
        return rlPortfolioList.stream().filter(item -> item.getEdmId().longValue() == edmId.longValue() && item.getEdmName().equals(edmName))
                .map(portfolioHeader -> portfolioHeader.getPortfolioId() + "~" + portfolioHeader.getPortfolioType() + "~" + portfolioHeader.getCurrency()).collect(toList());
    }

    /****** Risk Link Interface ******/

    public List<DataSource> listAvailableDataSources(String instanceId) {
        String sql = "execute " + DATABASE + ".dbo.RR_RL_ListAvailableDataSources";
        this.logger.debug("Service starts executing the query ...");
        List<DataSource> dataSources = getJdbcTemplate(instanceId).query(
                sql,
                new DataSourceRowMapper());
        this.logger.debug("the data returned ", dataSources);
        return dataSources;
    }

    public List<RdmAnalysisBasic> listRdmAnalysisBasic(String instanceId, Long id, String name) {
        String sql = "execute " + DATABASE + ".dbo.RR_RL_ListRdmAnalysisBasic @rdm_id=" + id + " ,@rdm_name=" + name;
        this.logger.debug("Service starts executing the query ...");
        List<RdmAnalysisBasic> rdmAnalysisBasic = getJdbcTemplate(instanceId).query(
                sql, new RdmAnalysisBasicRowMapper()
        );
        this.logger.debug("the data returned ", rdmAnalysisBasic);
        return rdmAnalysisBasic;
    }

    public List<RdmAnalysis> listRdmAnalysis(String instanceId, Long id, String name, List<Long> analysisIdList) {
        String query = "execute " + DATABASE + ".dbo.RR_RL_ListRdmAnalysis @rdm_id=" + id + ", @rdm_name=" + name;
        List<RdmAnalysis> rdmAnalysis = new ArrayList<>();
        this.logger.debug("Service starts executing the query ...");
        if (analysisIdList != null) {
            String sql = query + ",@analysis_id_list=" + analysisIdList;
            rdmAnalysis = getJdbcTemplate(instanceId).query(
                    sql, new RdmAnalysisRowMapper()
            );
        } else {
            rdmAnalysis = getJdbcTemplate(instanceId).query(
                    query, new RdmAnalysisRowMapper()
            );
        }
        this.logger.debug("the data returned ", rdmAnalysis);
        return rdmAnalysis;
    }

    public List<EdmPortfolioBasic> listEdmPortfolioBasic(String instanceId, Long id, String name) {
        String sql = "execute " + DATABASE + ".dbo.RR_RL_ListEdmPortfolioBasic @edm_id=" + id + ",@edm_name=" + name;
        this.logger.debug("Service starts executing the query ...");
        List<EdmPortfolioBasic> edmPortfolioBasic = getJdbcTemplate(instanceId).query(
                sql, new EdmPortfolioBasicRowMapper()
        );
        this.logger.debug("the data returned ", edmPortfolioBasic);
        return edmPortfolioBasic;
    }

    public List<EdmPortfolio> listEdmPortfolio(String instanceId, Long id, String name, List<Long> portfolioList) {
        String query = "execute " + DATABASE + ".dbo.RR_RL_ListEdmPortfolio @edm_id=" + id + ",@edm_name=" + name;
        List<EdmPortfolio> edmPortfolios = new ArrayList<>();
        this.logger.debug("Service starts executing the query ...");

        if (portfolioList != null) {
            String sql = query + ",@portfolioList=" + portfolioList;
            edmPortfolios = getJdbcTemplate(instanceId).query(
                    sql, new EdmPortfolioRowMapper()
            );
        } else {
            edmPortfolios = getJdbcTemplate(instanceId).query(
                    query, new EdmPortfolioRowMapper()
            );
        }
        this.logger.debug("the data returned ", edmPortfolios);
        return edmPortfolios;
    }

    public List<RdmAnalysisEpCurves> getRdmAllAnalysisEpCurves(String instanceId, Long id, String name, List<Integer> epPoints, List<Long> analysisIdList, List<String> finPerspList) {

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
            rdmAnalysisEpCurves = getJdbcTemplate(instanceId).query(
                    sql,
                    new RdmAnalysisEpCurvesRowMapper()
            );
        }
        if (analysisIdList != null && LIST.isEmpty()) {
            String sql = query + ", @analysis_id_list=" + analysisIdList;
            rdmAnalysisEpCurves = getJdbcTemplate(instanceId).query(
                    sql,
                    new RdmAnalysisEpCurvesRowMapper()
            );
        }
        if (analysisIdList == null && !LIST.isEmpty()) {
            String sql = query + ", @fin_persp_list=" + "'" + LIST + "'";
            rdmAnalysisEpCurves = getJdbcTemplate(instanceId).query(
                    sql,
                    new RdmAnalysisEpCurvesRowMapper()
            );
        }
        if (analysisIdList == null && LIST.isEmpty()) {
            rdmAnalysisEpCurves = getJdbcTemplate(instanceId).query(
                    query,
                    new RdmAnalysisEpCurvesRowMapper()
            );
        }
        this.logger.debug("the data returned {}", rdmAnalysisEpCurves);
        return rdmAnalysisEpCurves;
    }

    public List<RdmAllAnalysisSummaryStats> getRdmAllAnalysisSummaryStats(String instanceId, Long rdmId, String rdmName, List<String> finPerspList, List<Long> analysisIdList) {

        String LIST = "";
        if (finPerspList != null) {
            for (String s : finPerspList) {

                LIST += s + ",";
            }
            LIST = LIST.substring(0, LIST.length() - 1);
        }
        //finPerspList.stream().collect(Collectors.joining(","));

        List<RdmAllAnalysisSummaryStats> rdmAllAnalysisSummaryStats = new ArrayList<>();

        String query = "execute " + DATABASE + ".dbo.RR_RL_GetRdmAllAnalysisSummaryStats @rdm_id=" + rdmId.longValue() + ", @rdm_name=" + rdmName;
        this.logger.debug("Service starts executing the q uery ...");
        if (analysisIdList != null && !LIST.isEmpty()) {
            String sql = query + ", @fin_persp_list=" + "'" + LIST + "'" + ", @analysis_id_list=" + analysisIdList;
            rdmAllAnalysisSummaryStats = getJdbcTemplate(instanceId).query(
                    sql,
                    new RdmAllAnalysisSummaryStatsRowMapper()
            );

        }
        if (analysisIdList != null && LIST.isEmpty()) {
            String sql = query + ", @analysis_id_list=" + analysisIdList;
            rdmAllAnalysisSummaryStats = getJdbcTemplate(instanceId).query(
                    sql,
                    new RdmAllAnalysisSummaryStatsRowMapper()
            );
        }
        if (analysisIdList == null && !LIST.isEmpty()) {
            String sql = query + ", @rdm_name=" + rdmName + ", @fin_persp_list=" + "'" + LIST + "'";
            rdmAllAnalysisSummaryStats = getJdbcTemplate(instanceId).query(
                    sql,
                    new RdmAllAnalysisSummaryStatsRowMapper()
            );
        }
        if (analysisIdList == null && LIST.isEmpty()) {
            rdmAllAnalysisSummaryStats = getJdbcTemplate(instanceId).query(
                    query,
                    new RdmAllAnalysisSummaryStatsRowMapper()
            );
        }
        this.logger.debug("the data returned ", rdmAllAnalysisSummaryStats);
        return rdmAllAnalysisSummaryStats;
    }

    public List<AnalysisEpCurves> getAnalysisEpCurves(String instanceId, Long rdmID, String rdmName, Long analysisId, String finPerspCode, Integer treatyLabelId) {
        List<AnalysisEpCurves> analysisEpCurves = new ArrayList<>();
        String query = "execute " + DATABASE + ".dbo.RR_RL_GetAnalysisEpCurves @rdm_id=" + rdmID.longValue() + ", @rdm_name=" + rdmName + ", @analysis_id=" + analysisId.longValue() + ", @fin_persp_code=" + finPerspCode;
        this.logger.debug("Service starts executing the query ...");
        if (treatyLabelId != null) {
            String sql = query + ",@treaty_label_id=" + treatyLabelId;
            analysisEpCurves = getJdbcTemplate(instanceId).query(
                    sql,
                    new AnalysisEpCurvesRowMapper()
            );
        } else {
            analysisEpCurves = getJdbcTemplate(instanceId).query(
                    query,
                    new AnalysisEpCurvesRowMapper()
            );
        }
        this.logger.debug("the data returned {}", analysisEpCurves);
        return analysisEpCurves;
    }

    public List<AnalysisSummaryStats> getAnalysisSummaryStats(String instanceId, Long rdmId, String rdmName, Long analysisId, String fpCode, Integer treatyLabelId) {

        List<AnalysisSummaryStats> analysisSummaryStats = new ArrayList<>();
        String query = " execute " + DATABASE + ".dbo.RR_RL_GetAnalysisSummaryStats @rdm_id=" + rdmId.longValue() + ", @rdm_name=" + rdmName + ", @analysis_id=" + analysisId.longValue() + ", @fin_persp_code=" + fpCode;
        this.logger.debug("Service starts executing the query ...");
        if (treatyLabelId != null) {
            String sql = query + ",@treaty_label_id=" + treatyLabelId;
            analysisSummaryStats = getJdbcTemplate(instanceId).query(
                    sql, new AnalysisSummaryStatsRowMapper()
            );
        } else {
            analysisSummaryStats = getJdbcTemplate(instanceId).query(
                    query, new AnalysisSummaryStatsRowMapper()
            );

        }
        this.logger.debug("the data returned {}", analysisSummaryStats);
        return analysisSummaryStats;
    }

    public List<RdmAllAnalysisProfileRegions> getRdmAllAnalysisProfileRegions(String instanceId, Long rdmId, String rdmName, List<Long> analysisIdList) {

        List<RdmAllAnalysisProfileRegions> rdmAllAnalysisProfileRegions = new ArrayList<>();
        String query = "execute " + DATABASE + ".dbo.RR_RL_GetRdmAllAnalysisProfileRegions @rdm_id=" + rdmId.longValue() + ",@rdm_name=" + rdmName;
        this.logger.debug("Service starts executing the query ...");
        if (analysisIdList != null) {
            String sql = query + ",@analysis_id_list=" + analysisIdList;
            rdmAllAnalysisProfileRegions = getJdbcTemplate(instanceId).query(sql, new RdmAllAnalysisProfileRegionsRowMapper());
        } else {
            rdmAllAnalysisProfileRegions = getJdbcTemplate(instanceId).query(query, new RdmAllAnalysisProfileRegionsRowMapper());
        }
        this.logger.debug("the data returned {}", rdmAllAnalysisProfileRegions);
        return rdmAllAnalysisProfileRegions;
    }

    public RLAnalysisELT getAnalysisElt(String instanceId, Long rdmId, String rdmName, Long analysisId, String finPerspCode, Integer treatyLabelId) {
        String query = "execute " + DATABASE + ".dbo.RR_RL_GetAnalysisElt @rdm_id=" + rdmId.longValue() + ", @rdm_name=" + rdmName + ", @analysis_id=" + analysisId.longValue()
                + ", @fin_persp_code=" + finPerspCode;
        List<RlEltLoss> rlEltLoss = new ArrayList<>();
        this.logger.debug("Service starts executing the query ...");
        if (treatyLabelId != null) {
            String sql = query + ", @treaty_label_id=" + treatyLabelId;
            rlEltLoss = getJdbcTemplate(instanceId).query(sql, new AnalysisEltRowMapper());
        } else {
            rlEltLoss = getJdbcTemplate(instanceId).query(query, new AnalysisEltRowMapper());
        }
        this.logger.debug("the data returned ", rlEltLoss);
        return new RLAnalysisELT(instanceId, rdmId, rdmName, analysisId, finPerspCode, rlEltLoss);
    }

    public List<EdmAllPortfolioAnalysisRegions> getEdmAllPortfolioAnalysisRegions(String instanceId, Long edmId, String edmName, String ccy) {

        String sql = "execute " + DATABASE + ".dbo.RR_RL_GetEdmAllPortfolioAnalysisRegions @edm_id=" + edmId.longValue() + ", @edm_name=" + edmName + ", @Ccy=" + ccy;
        this.logger.debug("Service starts executing the query ...");
        List<EdmAllPortfolioAnalysisRegions> edmAllPortfolioAnalysisRegions = getJdbcTemplate(instanceId).query(sql, new EdmAllPortfolioAnalysisRegionsRowMapper());
        this.logger.debug("the data returned {}", edmAllPortfolioAnalysisRegions);
        return edmAllPortfolioAnalysisRegions;
    }

    public List<RdmAllAnalysisTreatyStructure> getRdmAllAnalysisTreatyStructure(String instanceId, Long rdmId, String rdmName, List<Long> analysisIdList) {

        List<RdmAllAnalysisTreatyStructure> rdmAllAnalysisTreatyStructure = new ArrayList<>();
        String query = "execute " + DATABASE + ".dbo.RR_RL_GetRdmAllAnalysisTreatyStructure @rdm_id=" + rdmId.longValue() + ", @rdm_name=" + rdmName;
        this.logger.debug("Service starts executing the query ...");
        if (analysisIdList != null) {
            String sql = query + ", @analysis_id_list=" + analysisIdList;
            rdmAllAnalysisTreatyStructure = getJdbcTemplate(instanceId).query(sql, new RdmAllAnalysisTreatyStructureRowMapper());
        } else {
            rdmAllAnalysisTreatyStructure = getJdbcTemplate(instanceId).query(query, new RdmAllAnalysisTreatyStructureRowMapper());
        }
        this.logger.debug("the data returned ", rdmAllAnalysisTreatyStructure);
        return rdmAllAnalysisTreatyStructure;
    }

    public List<RdmAllAnalysisMultiRegionPerils> getRdmAllAnalysisMultiRegionPerils(String instanceId, Long rdmId, String rdmName, List<Long> analysisIdList) {

        List<RdmAllAnalysisMultiRegionPerils> rdmAllAnalysisMultiRegionPerils = new ArrayList<>();

        String query = "execute " + DATABASE + ".dbo.RR_RL_GetRdmAllAnalysisMultiRegionPerils @rdm_id=" + rdmId.longValue() + ", @rdm_name=" + rdmName;
        this.logger.debug("Service starts executing the query ...");

        if (analysisIdList != null) {
            String sql = query + ", @analysis_id_list=" + analysisIdList;
            rdmAllAnalysisMultiRegionPerils = getJdbcTemplate(instanceId).query(sql, new RdmAllAnalysisMultiRegionPerilsRowMapper());
        } else {
            rdmAllAnalysisMultiRegionPerils = getJdbcTemplate(instanceId).query(query, new RdmAllAnalysisMultiRegionPerilsRowMapper());
        }


        this.logger.debug("the data returned ", rdmAllAnalysisMultiRegionPerils);
        return rdmAllAnalysisMultiRegionPerils;
    }

    public List<RmsExchangeRate> getRmsExchangeRates(String instanceId, List<String> ccyy) {

        String ccy = ccyy.toString().replaceAll(" ", "");
        List<RmsExchangeRate> rmsExchangeRate = new ArrayList<>();
        String sql = "execute " + DATABASE + ".[dbo].[RR_RL_GetRMSExchangeRates] @ccyList=" + ccy;
        this.logger.debug("Service starts executing the query ...");
        rmsExchangeRate = getJdbcTemplate(instanceId).query(sql, new RmsExchangeRateRowMapper());
        this.logger.debug("the data returned ", rmsExchangeRate);

        return rmsExchangeRate;
    }

    public List<CChkBaseCcy> getCChBaseCcy(String instanceId) {
        String sql = " execute " + DATABASE + ".dbo.RR_RL_CChk_BaseCcy";
        List<CChkBaseCcy> cChkBaseCcy = new ArrayList<>();
        cChkBaseCcy = getJdbcTemplate(instanceId).query(sql, new CChkBaseCcyRowMApper());
        return cChkBaseCcy;
    }

    public List<CChkBaseCcyFxRate> getCChkBaseCcyFxRate(String instanceId) {
        String sql = " execute " + DATABASE + ".dbo.RR_RL_CChk_BaseCcyFxRate";
        List<CChkBaseCcyFxRate> cChkBaseCcyFxRate = new ArrayList<>();
        cChkBaseCcyFxRate = getJdbcTemplate(instanceId).query(sql, new CChkBaseCcyFxRateRowMapper());
        return cChkBaseCcyFxRate;
    }

    public String getAnalysisModellingOptionSettings(String instanceId, Long rdmId, String rdmName, Long analysisId) {
        String query = "execute " + DATABASE + ".[dbo].[RR_RL_GetAnalysisModellingOptionSettings] @rdm_id=" + rdmId.longValue() + ", @rdm_name=" + rdmName + ", @analysis_id=" + analysisId.longValue();
        //String json = "";

        List<Map<String, Object>> result = getJdbcTemplate(instanceId).queryForList(query);
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
//        try {
//            JSONObject jsonObj = XML.toJSONObject(builder.toString());
//            json = jsonObj.toString(4);
//            return json;
//        } catch (JSONException e) {
//            e.printStackTrace();
//            this.logger.debug("Conversion failed", e);
//        }
        return builder.toString();
    }

    /****** Exposure summaries ******/

    public Integer createEDMPortfolioContext(String instanceId, Long edmId, String edmName, List<String> portfolioList, List<String> portfolioExcludedRegionPeril) {
        // @TODO Implement the Datasource Logic
        CreateEdmSummaryStoredProc proc = new CreateEdmSummaryStoredProc(getJdbcTemplate(instanceId), createEDMPortfolioContextSQL);
        String portfolioIdParam = "";
        String portfolioExcludedRegionPerilParam = "";

        if (portfolioList != null && !portfolioList.isEmpty()) {
            portfolioIdParam = StringUtils.join(portfolioList, ',');
        }
        if (portfolioExcludedRegionPeril != null && !portfolioExcludedRegionPeril.isEmpty()) {
            portfolioExcludedRegionPerilParam = StringUtils.join(portfolioExcludedRegionPeril, ',');
        }
        long time = System.currentTimeMillis();
        Map<String, Object> result = proc.execute(edmId, edmName, portfolioIdParam, portfolioExcludedRegionPerilParam);
        Integer runId = (Integer) result.get("RunID");
        logger.debug("created EDM Portfolio context #{} for EDM {}:{} and Portfolio ID(s) '{}' with portfolioExcludedRegionPeril(s) '{}'", new Object[]{runId, edmId, edmName, portfolioIdParam, portfolioExcludedRegionPerilParam});
        logger.debug("created EDM Portfolio context #{} for EDM {}:{} took {} ms", new Object[]{runId, edmId, edmName, (System.currentTimeMillis() - time)});
        return runId;
    }

    public void removeEDMPortfolioContext(String instanceId, Integer runId) {
        // @TODO Implement the Datasource Logic
        Map<String, Object> params = new HashMap<>();
        params.put("RunID", runId);
        logger.debug("removeEDMPortfolioContextSQL: {}, params: {}, runId: {}", removeEDMPortfolioContextSQL, params, runId);
        getJdbcTemplate(instanceId).update(removeEDMPortfolioContextSQL, params);
    }

    public boolean extractLocationLevelExposureDetails(Long edmId, String edmName, String instance, Long projectId, RLPortfolio rlPortfolio, ModelPortfolio modelPortfolio, File file, String extractName, String sqlQuery) {

        //TODO : Review this method with Viet
        Map<String, Object> dataQueryParams = new HashMap<>();
//        dataQueryParams.put("Edm_id", edm.getRlId());
        dataQueryParams.put("Edm_id", edmId);
        dataQueryParams.put("Edm_name", edmName);
        dataQueryParams.put("PortfolioID_RMS", rlPortfolio.getPortfolioId());
        dataQueryParams.put("PortfolioID_RR", rlPortfolio.getRlPortfolioId());

        if (LocationLevelExposure.EXTRACT_PORT.equals(extractName)) {
            dataQueryParams.put("ProjectID_RR", projectId);
        } else if ((LocationLevelExposure.EXTRACT_PORT_ACCOUNT_POL.equals(extractName)) ||
                (LocationLevelExposure.EXTRACT_PORT_ACCOUNT_POL_CVG.equals(extractName)) ||
                (LocationLevelExposure.EXTRACT_PORT_ACCOUNT_LOC_CVG.equals(extractName))) {
            dataQueryParams.put("ConformedCcy", modelPortfolio.getCurrency());
        }

        List<GenericDescriptor> descriptors = extractSchema(instance, extractName);

        if (descriptors.isEmpty()) {
            logger.error("Error: retrieve no descriptors");
            return false;
        }
        extractExposureToFile(instance, sqlQuery, dataQueryParams, descriptors, file);

        return true;
    }

    private List<GenericDescriptor> extractSchema(String instanceId, String extractName) {
        logger.debug("extractSchema");
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = createNamedParameterTemplate(instanceId);
        Map<String, Object> schemaQueryParams = new HashMap<>();
        schemaQueryParams.put("extract_name", extractName);
        logger.debug("extractSchema | schemaSqlQuery: {}, params: {}", extractLocationLevelSchemaQuery, schemaQueryParams);
        List<GenericDescriptor> descriptors = namedParameterJdbcTemplate.query(extractLocationLevelSchemaQuery, schemaQueryParams, new GenericDescriptorMapper());

        return descriptors;
    }

    private void extractExposureToFile(String instanceId, String sqlQuery, Map<String, Object> dataQueryParams, List<GenericDescriptor> descriptors, File file) {
        logger.debug("run query {} with params {}", sqlQuery, dataQueryParams);
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = createNamedParameterTemplate(instanceId);
        ExposureExtractor extractor = new ExposureExtractor(descriptors, file);
        namedParameterJdbcTemplate.query(sqlQuery, dataQueryParams, extractor);
    }

    private JdbcTemplate getJdbcTemplate(String instanceId) {
        return new JdbcTemplate(rmsInstanceCache.getDataSource(instanceId));
    }

    public NamedParameterJdbcTemplate createNamedParameterTemplate(String instanceId) {
        return new NamedParameterJdbcTemplate(this.getJdbcTemplate(instanceId));
    }

    private void extractSourceEpHeaders(String instanceId, Long rdmId, String rdmName, List<Integer> epPoints, List<Long> analysisIds, List<String> fpCodes) {

        List<RdmAnalysisEpCurves> epCurves = this.getRdmAllAnalysisEpCurves(instanceId, rdmId, rdmName, epPoints, analysisIds, fpCodes);

        this.getRdmAllAnalysisSummaryStats(instanceId, rdmId, rdmName, fpCodes, analysisIds)
                .forEach(stat -> {
                    RLSourceEpHeader sourceEpHeader = new RLSourceEpHeader(stat);
                    filterEpCurvesByStat(epCurves, stat).forEach(epC -> {
                        int statisticMetric = epC.getEbpTypeCode();
                        try {
                            String fieldName = this.generateEpCurveFieldName(epC.getExceedanceProbabilty(), statisticMetric);
                            Utils.setAttribute(sourceEpHeader, fieldName, epC.getLoss());
                        } catch (Exception exp) {
                            exp.printStackTrace();
                        }
                    });
                    rlSourceEpHeaderRepository.deleteByRLAnalysisIdAndFinancialPerspective(sourceEpHeader.getRLAnalysisId(), sourceEpHeader.getFinancialPerspective());
                    rlSourceEpHeaderRepository.save(sourceEpHeader);
                });

    }

    private String generateEpCurveFieldName(int exceedanceProbability, int statisticMetric) throws Exception {
        StatisticMetric statMetric = StatisticMetric.getFrom(statisticMetric);
        String fieldName = null;
        if (statMetric != null) {
            switch (statMetric) {
                case AEP:
                    fieldName = "aEP";
                    break;
                case OEP:
                    fieldName = "oEP";
                    break;
                default:
                    throw new Exception("Non supported Stat Metric by RlSourceEpHeader Entity " + statisticMetric);
            }
        }
        if (fieldName != null)
            return fieldName.concat(String.valueOf(exceedanceProbability));
        else
            throw new Exception("Non supported Return Period by RlSourceEpHeader Entity " + exceedanceProbability);
    }

    private List<RdmAnalysisEpCurves> filterEpCurvesByStat(List<RdmAnalysisEpCurves> epCurves, RdmAllAnalysisSummaryStats stats) {
        return epCurves.stream().filter(epC -> {
            try {
                if (epC.getAnalysisId() != null && epC.getFinPerspCode() != null && epC.getTreatyLabelId() != null)
                    return epC.getAnalysisId().equals(stats.getAnalysisId()) &&
                            epC.getFinPerspCode().equals(stats.getFinPerspCode()) &&
                            epC.getTreatyLabelId().equals(String.valueOf(stats.getTreatyLabelId()));
                else if (epC.getAnalysisId() != null && epC.getFinPerspCode() != null && epC.getTreatyLabelId() == null)
                    return epC.getAnalysisId().equals(stats.getAnalysisId()) &&
                            epC.getFinPerspCode().equals(stats.getFinPerspCode());
                else
                    return false;
            } catch (NullPointerException exp) {
                exp.printStackTrace();
                return false;
            }
        }).collect(toList());
    }

    public Map<String, List<String>> extractDetailedExposure(File file, Long edmId, String edmName, String instance, String extractName, List<String> portfolioIdList, List<String> portfolioExcludeRegionPeril,
                                                             Integer runId, Long portfolioId, String portfolioType) {
        List<String> errors = new ArrayList<>();
        List<String> warnings = new ArrayList<>();
        boolean needToRemove = false;

        if (runId == null) {
            warnings.add("runId is null. Create a new one with parameters:" +
                    " InstanceId=" + instance +
                    " RmsId=" + edmId +
                    " Name=" + edmName +
                    " portfolioIdList=" + portfolioIdList +
                    " portfolioExcludeRegionPeril=" + portfolioExcludeRegionPeril);
            runId = createEDMPortfolioContext(instance, edmId, edmName, portfolioIdList, portfolioExcludeRegionPeril);
            needToRemove = true;
        }
        if (runId != null && runId > 0) {
            List<GenericDescriptor> descriptors = extractSchema(instance, extractName);
            if (descriptors == null || descriptors.isEmpty()) {
                logger.error("Error: retrieve no descriptors");
            }

            extractDetailedExposureToFile(instance, edmId, edmName, runId, portfolioId, portfolioType, descriptors, file);

            if (needToRemove) {
                removeEDMPortfolioContext(instance, runId);
            }
        } else {
            logger.error("RunID is null or equal to 0");
            errors.add("RunID is null or equal to 0");
        }

        Map<String, List<String>> res = new HashMap<>();
        res.put("Error", errors);
        res.put("Warning", warnings);
        return res;
    }

    private void extractDetailedExposureToFile(String instanceId, Long edmId, String edmName, Integer runId, Long portfolioId, String portfolioType,
                                               List<GenericDescriptor> descriptors, File file) {
        logger.debug("extractDetailedExposureToFile");
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = this.createNamedParameterTemplate(instanceId);
        Map<String, Object> dataQueryParams = new HashMap<>();
        dataQueryParams.put("edm_id", edmId);
        dataQueryParams.put("edm_name", edmName);
        dataQueryParams.put("runid", runId);
        if (portfolioId != null && portfolioType != null) {
            dataQueryParams.put("Portfolio_Id", portfolioId);
            dataQueryParams.put("Portfolio_Type", portfolioType);
        }
        ExposureExtractor extractor = new ExposureExtractor(descriptors, file);
        logger.debug("run query {} with params {}", getEdmDetailsSummarySqlQuery, dataQueryParams);
        namedParameterJdbcTemplate.query(getEdmDetailsSummarySqlQuery, dataQueryParams, extractor);
    }

    private static class CreateEdmSummaryStoredProc extends StoredProcedure {
        private String sqlProc;

        CreateEdmSummaryStoredProc(JdbcTemplate jdbcTemplate, String sqlProc) {
            // NOTA : regionPerilExclude maybe an DEV schema artifact
            super(jdbcTemplate, sqlProc);
            this.sqlProc = sqlProc;
            //
            setFunction(false);
            SqlParameter edmID = new SqlParameter("Edm_ID", Types.INTEGER);
            SqlParameter edmName = new SqlParameter("Edm_Name", Types.VARCHAR);
            SqlParameter edmPortList = new SqlParameter("PortfolioList", Types.VARCHAR);
            SqlParameter regionPerilExclude = new SqlParameter("RegionPerilExclude", Types.VARCHAR);
            SqlOutParameter runID = new SqlOutParameter("RunID", Types.INTEGER);
            SqlParameter[] paramArray = {edmID, edmName, edmPortList, regionPerilExclude, runID};
            super.setParameters(paramArray);
            super.compile();
        }

        public String getSqlProc() {
            return sqlProc;
        }
    }
}
