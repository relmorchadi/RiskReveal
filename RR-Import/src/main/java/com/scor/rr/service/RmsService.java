package com.scor.rr.service;

import com.scor.rr.configuration.RmsInstanceCache;
import com.scor.rr.domain.*;
import com.scor.rr.domain.dto.*;
import com.scor.rr.domain.enums.StatisticMetric;
import com.scor.rr.domain.riskLink.*;
import com.scor.rr.mapper.*;
import com.scor.rr.repository.*;
import org.apache.commons.collections.keyvalue.MultiKey;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.sql.Types;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;
import java.util.stream.Collectors;

import static com.scor.rr.util.Utils.setAttribute;
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
    private RLSourceEpHeaderRepository rlSourceEpHeaderRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    @Qualifier(value = "dataSource")
    private javax.sql.DataSource rlDataSource;

    @Value("${rms.ds.dbname}")
    private String DATABASE;

    @Value("${rms.ds.dbname}.[dbo].[RR_RL_BaseEdmSummaryPrep]")
    private String createEDMPortfolioContextSQL;

    @Value("exec ${rms.ds.dbname}.[dbo].[RR_RL_BaseEdmSummaryClear] @RunID=:RunID")
    private String removeEDMPortfolioContextSQL;

    @Value(value = "extractLocationLevelSchemaQuery")
    private String extractLocationLevelSchemaQuery;

    @Autowired
    private RmsInstanceCache rmsInstanceCache;

    private DecimalFormat df;

    @PostConstruct
    private void buildDecimalFormatter(){
        df=new DecimalFormat("#0.0", DecimalFormatSymbols.getInstance(Locale.US));
        df.setGroupingUsed(false);
        df.setMinimumFractionDigits(10);
        df.setMinimumFractionDigits(1);
        df.setMinimumIntegerDigits(1);
        df.setDecimalSeparatorAlwaysShown(true);
    }

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

    private void scanAnalysisBasicForRdm(String instanceId, RLModelDataSource rdm) {
        rlAnalysisRepository.deleteByRlModelDataSourceId(rdm.getRlModelDataSourceId());
        List<RdmAnalysisBasic> rdmAnalysisBasics = listRdmAnalysisBasic(instanceId, Long.parseLong(rdm.getRlId()), rdm.getName());
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

        if (rlAnalysisList != null && !rlAnalysisList.isEmpty()) {
            rlAnalysisList.stream().map(item -> new MultiKey(item.getRdmId(), item.getRdmName())).distinct()
                    .forEach(key -> analysisByRdms.put(key, this.getAnalysisIdByRdm((Long) key.getKey(0), (String) key.getKey(1), rlAnalysisList)));

            for (Map.Entry<MultiKey, List<Long>> multiKeyListEntry : analysisByRdms.entrySet()) {

                Long rdmId = (Long) multiKeyListEntry.getKey().getKey(0);
                String rdmName = (String) multiKeyListEntry.getKey().getKey(1);

                List<RLAnalysis> analyses=this.listRdmAnalysis(instanceId, rdmId, rdmName, multiKeyListEntry.getValue())
                        .stream()
                        .peek(rdmAnalysis -> this.rlAnalysisRepository.updateAnalysisById(projectId, rdmAnalysis))
                        .map(rdmAnalysis -> this.rlAnalysisRepository.findByProjectIdAndAnalysis(projectId, rdmAnalysis))
                        .peek(rdmAnalysis -> this.rlAnalysisScanStatusRepository.updateScanLevelByRlModelAnalysisId(rdmAnalysis.getRlAnalysisId()))
                        .collect(toList());

                /**
                 * Extract EP SourceHeaders
                 * @Todo load Fp / Return Periods
                 */
                this.extractSourceEpHeaders(instanceId, rdmId, rdmName,new ArrayList<>(), analyses, new ArrayList<>());
            }
        }
    }


    private void extractSourceEpHeaders(String instanceId, Long rdmId, String rdmName,List<Double> returnPeriods, List<RLAnalysis> analyses, List<String> fpCodes){
        List<Long> analysisIds= analyses.stream().map(RLAnalysis::getAnalysisId).collect(toList());
        List<RdmAnalysisEpCurves> epCurves= this.listRdmAllAnalysisEpCurves(instanceId, rdmId, rdmName, returnPeriods, analysisIds, fpCodes);
        this.getRdmAllAnalysisSummaryStats(instanceId, rdmId, rdmName,fpCodes, analysisIds)
                .forEach(stat -> {
                    RLSourceEpHeader epHeader= new RLSourceEpHeader(stat);
                    filterEpCurvesByStat(epCurves, stat).forEach(epC -> {
                        int returnPeriod= 1 /  epC.getExceedanceProbabilty();
                        int statisticMetric = epC.getEbpTypeCode();
                        try {
                            String fieldName= this.generateEpCurveFieldName(returnPeriod, statisticMetric);
                            setAttribute(epHeader, fieldName, epC.getLoss());
                        }catch (Exception exp){
                            exp.printStackTrace();
                        }
                    });
                    rlSourceEpHeaderRepository.save(epHeader);
                });

    }

    private String generateEpCurveFieldName(int returnPeriod, int statisticMetric) throws Exception{
        StatisticMetric statMetric= StatisticMetric.getFrom(statisticMetric);
        String fieldName=null;
        switch (statMetric){
            case AEP:
                fieldName="aEP";
                break;
            case OEP:
                fieldName="oEP";
                break;
            default:
                throw new Exception("Non supported Stat Metric by RlSourceEpHeader Entity " + statisticMetric);
        }
        if(fieldName != null && RLSourceEpHeader.isValidReturnPeriod(returnPeriod)){
            return fieldName.concat(String.valueOf(returnPeriod));
        }
        else {
            throw new Exception("Non supported Return Period by RlSourceEpHeader Entity " + returnPeriod);
        }
    }

    private List<RdmAnalysisEpCurves> filterEpCurvesByStat(List<RdmAnalysisEpCurves> epCurves, RdmAllAnalysisSummaryStats stats){
        return epCurves.stream().filter(epC -> {
            try {
                return epC.getId().equals(stats.getAnalysisId()) && epC.getFinPerspCode().equals(stats.getFinPerspCode()) && epC.getTreatyLabelId().equals(stats.getTreatyLabelId());
            } catch (NullPointerException exp){
                exp.printStackTrace();
                return false;
            }
        } ).collect(toList());
    }

    private void scanPortfolioBasicForEdm(String instanceId, RLModelDataSource edm) {
        rlPortfolioRepository.deleteByRlModelDataSourceRlModelDataSourceId(edm.getRlModelDataSourceId());
        List<EdmPortfolioBasic> edmPortfolioBasics = listEdmPortfolioBasic(instanceId, Long.parseLong(edm.getRlId()), edm.getName());
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

        if (rlPortfolioList != null && !rlPortfolioList.isEmpty()) {
            rlPortfolioList.stream().map(item -> new MultiKey(item.getEdmId(), item.getEdmName())).distinct()
                    .forEach(key -> portfolioByEdms.put(key, this.getPortfolioIdByEdm((Long) key.getKey(0), (String) key.getKey(1), rlPortfolioList)));

            for (Map.Entry<MultiKey, List<Long>> multiKeyListEntry : portfolioByEdms.entrySet()) {

                Long edmId = (Long) multiKeyListEntry.getKey().getKey(0);
                String edmName = (String) multiKeyListEntry.getKey().getKey(1);

                this.listEdmPortfolio(instanceId, edmId, edmName, multiKeyListEntry.getValue())
                        .forEach(edmPortfolio -> this.rlPortfolioRepository.updatePortfolioById(projectId, edmPortfolio));
            }
        }
    }

    /********** Scan Basic / Detailed **********/

    public void basicScan(List<DataSource> dataSources, Long projectId, String instanceId, String instanceName) {
        Set<MultiKey> selectedDataSources = new HashSet<>();
        for (DataSource dataSource : dataSources) {
            selectedDataSources.add(new MultiKey(dataSource.getType(), instanceId, dataSource.getRmsId().toString()));
        }
        List<RLModelDataSource> existingRLModelDataSources = rlModelDataSourcesRepository.findByProjectId(projectId);
        for (RLModelDataSource existingRLModelDataSource : existingRLModelDataSources) {
            if (!selectedDataSources.contains(new MultiKey(existingRLModelDataSource.getType(), existingRLModelDataSource.getInstanceId(), existingRLModelDataSource.getRlId()))) {
                rlModelDataSourcesRepository.delete(existingRLModelDataSource);
            }
        }

        for (DataSource dataSource : dataSources) {
            RLModelDataSource rlModelDataSource = rlModelDataSourcesRepository.findByProjectIdAndTypeAndInstanceIdAndRlId
                    (projectId, dataSource.getType(), instanceId, dataSource.getRmsId().toString());
            if (rlModelDataSource == null) {
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

    private List<Long> getAnalysisIdByRdm(Long rdmId, String rdmName, List<AnalysisHeader> rlAnalysisList) {
        return rlAnalysisList.stream().filter(item -> item.getRdmId().longValue() == rdmId.longValue() && item.getRdmName().equals(rdmName))
                .map(AnalysisHeader::getAnalysisId).collect(toList());
    }

    private List<Long> getPortfolioIdByEdm(Long edmId, String edmName, List<PortfolioHeader> rlPortfolioList) {
        return rlPortfolioList.stream().filter(item -> item.getEdmId().longValue() == edmId.longValue() && item.getEdmName().equals(edmName))
                .map(PortfolioHeader::getPortfolioId).collect(toList());
    }

    public List<RdmAnalysisEpCurves> listRdmAllAnalysisEpCurves(String instanceId, Long id, String name, List<Double> returnPeriods, List<Long> analysisIdList, List<String> finPerspList) {
        List<String> values=new ArrayList<>();
        for(Double rp:returnPeriods) {
            values.add(df.format(rp));
        }
        String LIST = "";
        
        if (finPerspList != null) {
            for (String s : finPerspList) {
                LIST += s + ",";
            }
            LIST = LIST.substring(0, LIST.length() - 1);
        }
        List<RdmAnalysisEpCurves> rdmAnalysisEpCurves = new ArrayList<>();

        String query = "execute " + DATABASE + ".dbo.RR_RL_GetRdmAllAnalysisEpCurves @rdm_id=" + id + ", @rdm_name=" + name + ",@ep_points=" + StringUtils.join(values, ",");
            if (analysisIdList != null) {
            query += ", @analysis_id_list=" + analysisIdList;
     
        }
            if (!LIST.isEmpty()) {
            query += ", @fin_persp_list=" + "'" + LIST + "'";
        }
    
        this.logger.debug("Service starts executing the query ...");
        rdmAnalysisEpCurves = getJdbcTemplate(instanceId).query(
                                    query,
                                    new RdmAnalysisEpCurvesRowMapper()
            );
        this.logger.debug("the data returned ", rdmAnalysisEpCurves);
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
        this.logger.debug("the data returned ", analysisEpCurves);
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
        this.logger.debug("the data returned ", analysisSummaryStats);
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
        this.logger.debug("the data returned ", rdmAllAnalysisProfileRegions);
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

        List<EdmAllPortfolioAnalysisRegions> edmAllPortfolioAnalysisRegions = new ArrayList<>();

        String sql = "execute " + DATABASE + ".dbo.RR_RL_GetEdmAllPortfolioAnalysisRegions @edm_id=" + edmId.longValue() + ", @edm_name=" + edmName + ", @Ccy=" + ccy;
        this.logger.debug("Service starts executing the query ...");
        edmAllPortfolioAnalysisRegions = getJdbcTemplate(instanceId).query(sql, new EdmAllPortfolioAnalysisRegionsRowMapper());
        this.logger.debug("the data returned ", edmAllPortfolioAnalysisRegions);
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

    private String convertList(List<String> list) {
        return list.stream().collect(Collectors.joining(","));
    }

    private String convertListOldImpl(List<String> finPerspList) {
        String LIST = "";
        if (finPerspList != null) {
            for (String s : finPerspList) {

                LIST += s + ",";
            }
            LIST = LIST.substring(0, LIST.length() - 1);
        }
        return LIST;
    }

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

    private class CreateEdmSummaryStoredProc extends StoredProcedure {
        private String sqlProc;

        public String getSqlProc() {
            return sqlProc;
        }

        public CreateEdmSummaryStoredProc(JdbcTemplate jdbcTemplate, String sqlProc) {
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
    }

    private JdbcTemplate getJdbcTemplate(String instanceId) {
        return new JdbcTemplate(rmsInstanceCache.getDataSource(instanceId));
    }

    public NamedParameterJdbcTemplate createNamedParameterTemplate(String instanceId) {
        return new NamedParameterJdbcTemplate(this.getJdbcTemplate(instanceId));
    }
}
