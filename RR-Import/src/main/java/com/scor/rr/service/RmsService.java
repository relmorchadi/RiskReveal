package com.scor.rr.service;

import com.scor.rr.configuration.RmsInstanceCache;
import com.scor.rr.domain.*;
import com.scor.rr.domain.dto.*;
import com.scor.rr.domain.enums.ScanLevelEnum;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.stereotype.Component;

import java.io.File;
import java.sql.Types;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static com.scor.rr.util.Utils.applyOffsetSizeToList;
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

    @Autowired
    private RegionPerilService regionPerilService;

    @Autowired
    private RLImportTargetRAPSelectionRepository rlImportTargetRAPSelectionRepository;

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


    public List<RLModelDataSource> basicScan(List<DataSource> dataSources, Long projectId, String instanceId, String instanceName) {
        return dataSources.stream().map(dataSource -> {

            RLModelDataSource rlModelDataSource =
                    rlModelDataSourcesRepository.findByProjectIdAndTypeAndInstanceIdAndRlId(projectId, dataSource.getType(), instanceId, dataSource.getRmsId());

            if (rlModelDataSource != null) {
                rlAnalysisRepository.deleteByRlModelDataSourceId(rlModelDataSource.getRlModelDataSourceId());
                rlPortfolioRepository.deleteByRlModelDataSourceRlModelDataSourceId(rlModelDataSource.getRlModelDataSourceId());
            } else {
                rlModelDataSource = new RLModelDataSource(dataSource, projectId, instanceId, instanceName);
                rlModelDataSourcesRepository.save(rlModelDataSource);
            }

            Integer count = null;
            if (rlModelDataSource.getType().equalsIgnoreCase("RDM")) {
                count = scanAnalysisBasicForRdm(instanceId, rlModelDataSource);
            } else if (rlModelDataSource.getType().equalsIgnoreCase("EDM")) {
                count = scanPortfolioBasicForEdm(instanceId, rlModelDataSource);
            }
            rlModelDataSourcesRepository.updateCount(rlModelDataSource.getRlModelDataSourceId(), count);
            rlModelDataSource.setCount(count);
            return rlModelDataSource;
        }).collect(toList());
    }

    public RLModelDataSource singleBasicScan(DataSource dataSource, Long projectId, String instanceId, String instanceName) {
        return this.basicScan(
                new ArrayList<>(Collections.singleton(dataSource)),
                projectId,
                instanceId,
                instanceName
        ).get(0);
    }

    public DetailedScanResult detailedScan(DetailedScanDto detailedScanDto) {
        return new DetailedScanResult(
                scanAnalysisDetail(detailedScanDto.getInstanceId(), detailedScanDto.getRlAnalysisList(), detailedScanDto.getProjectId()).stream()
                        .map(analysis -> modelMapper.map(analysis, RLAnalysisDetailedDto.class))
                        .collect(Collectors.toList()),
                scanPortfolioDetail(detailedScanDto.getInstanceId(), detailedScanDto.getRlPortfolioList(), detailedScanDto.getProjectId()));
    }

    /********** END :Scan Basic / Detailed **********/

    /**
     * @param importSelectionDtoList
     * @implNote Method used to save analysis config (SourceResult). values that has been chosen by the user
     */
    public List<Long> saveAnalysisImportSelection(List<ImportSelectionDto> importSelectionDtoList) {

        List<Long> rlImportSelectionId = new ArrayList<>();
        if (importSelectionDtoList != null && !importSelectionDtoList.isEmpty())
            rlImportSelectionRepository.deleteByProjectId(importSelectionDtoList.get(0).getProjectId());
        importSelectionDtoList
                .forEach(importSelectionDto -> {
                    Optional<RLAnalysis> rlAnalysisOptional = rlAnalysisRepository.findById(importSelectionDto.getRlAnalysisId());
                    if (importSelectionDto.getFinancialPerspectives() != null && !importSelectionDto.getFinancialPerspectives().isEmpty())
                        importSelectionDto.getFinancialPerspectives().forEach(fp -> {
                            if (rlAnalysisOptional.isPresent()) {
                                if (importSelectionDto.getDivisions() == null || importSelectionDto.getDivisions().isEmpty()) {
                                    RLImportSelection rlImportSelection = new RLImportSelection(importSelectionDto, fp, rlAnalysisOptional.get());
                                    rlImportSelection = rlImportSelectionRepository.save(rlImportSelection);
                                    rlImportSelectionId.add(rlImportSelection.getRlImportSelectionId());
                                    for (String code : importSelectionDto.getTargetRAPCodes()) {
                                        RLImportTargetRAPSelection rlImportTargetRAPSelection = new RLImportTargetRAPSelection(code, rlImportSelection);
                                        rlImportSelection.addTargetRap(rlImportTargetRAPSelection);
                                        rlImportTargetRAPSelectionRepository.save(rlImportTargetRAPSelection);
                                    }
                                } else {
                                    importSelectionDto.getDivisions().forEach(division -> {
                                        RLImportSelection rlImportSelection = new RLImportSelection(importSelectionDto, fp, rlAnalysisOptional.get(), division);
                                        rlImportSelection = rlImportSelectionRepository.save(rlImportSelection);
                                        rlImportSelectionId.add(rlImportSelection.getRlImportSelectionId());
                                    });
                                }
                            }
                        });
                });
        return rlImportSelectionId;
    }

    /**
     * @param portfolioImportSelectionDtoList
     * @implNote Method used to save portfolio config (PortfolioSelection). values that has been chosen by the user
     */
    public List<Long> savePortfolioImportSelection(List<PortfolioSelectionDto> portfolioImportSelectionDtoList) {

        List<Long> portfolioSelectionIds = new ArrayList<>();

        if (portfolioImportSelectionDtoList != null && !portfolioImportSelectionDtoList.isEmpty()) {
            rlPortfolioSelectionRepository.deleteByProjectId(portfolioImportSelectionDtoList.get(0).getProjectId());
            portfolioImportSelectionDtoList
                    .forEach(rlPortfolioSelection -> {
                        RLPortfolio rlPortfolio = rlPortfolioRepository.findById(rlPortfolioSelection.getRlPortfolioId()).orElse(null);
                        if (rlPortfolio != null) {
                            if (rlPortfolioSelection.getDivisions() != null && !rlPortfolioSelection.getDivisions().isEmpty()) {
                                rlPortfolioSelection.getDivisions().forEach(division -> {
                                    RLPortfolioSelection portfolioSelection = new RLPortfolioSelection(rlPortfolioSelection, rlPortfolio, division);
                                    portfolioSelection = rlPortfolioSelectionRepository.save(portfolioSelection);
                                    portfolioSelectionIds.add(portfolioSelection.getRlPortfolioSelectionId());
                                });

                            } else {
                                RLPortfolioSelection portfolioSelection = new RLPortfolioSelection(rlPortfolioSelection, rlPortfolio, 1);
                                portfolioSelection = rlPortfolioSelectionRepository.save(portfolioSelection);
                                portfolioSelectionIds.add(portfolioSelection.getRlPortfolioSelectionId());
                            }
                        }
                    });
        }
        return portfolioSelectionIds;
    }

    private int scanAnalysisBasicForRdm(String instanceId, RLModelDataSource rdm) {
        rlAnalysisRepository.deleteByRlModelDataSourceId(rdm.getRlModelDataSourceId());
        List<RdmAnalysisBasic> rdmAnalysisBasics = listRdmAnalysisBasic(instanceId, rdm.getRlId(), rdm.getName());
        for (RdmAnalysisBasic rdmAnalysisBasic : rdmAnalysisBasics) {
            RLAnalysis rlAnalysis = this.rlAnalysisRepository.save(
                    new RLAnalysis(rdmAnalysisBasic, rdm)
            );
            RLAnalysisScanStatus rlAnalysisScanStatus = new RLAnalysisScanStatus(rlAnalysis.getRlAnalysisId(), 0);
            rlAnalysisScanStatusRepository.save(rlAnalysisScanStatus);
        }
        return rdmAnalysisBasics.size();
    }

    private List<RLAnalysis> scanAnalysisDetail(String instanceId, List<AnalysisHeader> rlAnalysisList, Long projectId) {
        Map<MultiKey, List<Long>> analysisByRdms = new HashMap<>();

        // TODO eppoint = 1/10 ....
        List<Float> epPoints = Arrays.asList(1 / 10f, 1 / 50f, 1 / 100f, 1 / 250f, 1 / 500f, 1 / 1000f);
        List<String> fpCodes = financialPerspectiveRepository.findAllCodes();

        List<RLAnalysis> allScannedAnalysis = new ArrayList<>();


        if (rlAnalysisList != null && !rlAnalysisList.isEmpty()) {
            rlAnalysisList.stream().map(item -> new MultiKey(item.getRdmId(), item.getRdmName())).distinct()
                    .forEach(key -> analysisByRdms.put(key, this.getAnalysisIdByRdm((Long) key.getKey(0), (String) key.getKey(1), rlAnalysisList)));

            rlSourceEpHeaderRepository.deleteByRLAnalysisIdList(rlAnalysisList.stream().map(AnalysisHeader::getRlAnalysisId).collect(toList()));

            ExecutorService executorService = Executors.newFixedThreadPool(1);
            executorService.execute(() -> this.getSourceEpHeaders(analysisByRdms, epPoints, fpCodes, projectId, instanceId));

            for (Map.Entry<MultiKey, List<Long>> multiKeyListEntry : analysisByRdms.entrySet()) {

                Long rdmId = (Long) multiKeyListEntry.getKey().getKey(0);
                String rdmName = (String) multiKeyListEntry.getKey().getKey(1);

                this.getRdmAllAnalysisProfileRegions(instanceId, rdmId, rdmName, multiKeyListEntry.getValue())
                        .stream()
                        .map(analysisProfileRegion -> {
                            RLAnalysis rlAnalysis = rlAnalysisRepository.findByRdmIdAndRdmNameAndRlIdAndProjectId(rdmId, rdmName, analysisProfileRegion.getAnalysisId(), projectId);
                            return new RLAnalysisProfileRegion(analysisProfileRegion, rlAnalysis);
                        })
                        .forEach(analysisProfileRegion -> rlAnalysisProfileRegionsRepository.save(analysisProfileRegion));

                List<RdmAnalysis> myAnalysis = this.listRdmAnalysis(instanceId, rdmId, rdmName, multiKeyListEntry.getValue());
                myAnalysis.stream()
                        .map(rdmAnalysis -> {
                            RLAnalysis rlAnalysis = this.rlAnalysisRepository.findByProjectIdAndAnalysis(projectId, rdmAnalysis);
                            this.updateRLAnalysis(rlAnalysis, rdmAnalysis);
                            String systemRegionPeril = this.resolveSystemRegionPeril(rlAnalysis.getRlAnalysisId());
                            rlAnalysis.setSystemRegionPeril(systemRegionPeril != null ? systemRegionPeril : rlAnalysis.getRpCode());
                            rlAnalysis = rlAnalysisRepository.save(rlAnalysis);
                            allScannedAnalysis.add(rlAnalysis);
                            return rlAnalysis;
                        }).forEach(rdmAnalysis -> this.rlAnalysisScanStatusRepository.updateScanLevelByRlModelAnalysisId(rdmAnalysis.getRlAnalysisId()));
            }
        }

        return allScannedAnalysis;
    }

    private void updateRLAnalysis(RLAnalysis rlAnalysis, RdmAnalysis rdmAnalysis) {
        rlAnalysis.setRpCode(rdmAnalysis.getRpCode());
        rlAnalysis.setDefaultGrain(rdmAnalysis.getDefaultGrain());
        rlAnalysis.setExposureType(rdmAnalysis.getExposureType());
        rlAnalysis.setExposureTypeCode(rdmAnalysis.getExposureTypeCode());
        rlAnalysis.setEdmNameSourceLink(rdmAnalysis.getEdmNameSourceLink());
        rlAnalysis.setExposureId(rdmAnalysis.getExposureId());
        rlAnalysis.setRlExchangeRate(rdmAnalysis.getRmsExchangeRate());
        rlAnalysis.setGeoCode(rdmAnalysis.getGeoCode());
        rlAnalysis.setTypeCode(rdmAnalysis.getTypeCode());
        rlAnalysis.setAnalysisMode(rdmAnalysis.getAnalysisMode());
        rlAnalysis.setEngineTypeCode(rdmAnalysis.getEngineTypeCode());
        rlAnalysis.setEngineVersionMajor(rdmAnalysis.getEngineVersionMajor());
        rlAnalysis.setProfileKey(rdmAnalysis.getProfileKey());
        rlAnalysis.setProfileName(rdmAnalysis.getProfileName());
        rlAnalysis.setPurePremium(rdmAnalysis.getPurePremium() != null ? rdmAnalysis.getPurePremium().doubleValue() : null);
        rlAnalysis.setExposureTIV(rdmAnalysis.getExposureTiv());
        rlAnalysis.setUser1(rdmAnalysis.getUser1());
        rlAnalysis.setUser2(rdmAnalysis.getUser2());
        rlAnalysis.setUser3(rdmAnalysis.getUser3());
        rlAnalysis.setUser4(rdmAnalysis.getUser4());
        rlAnalysis.setDescription(rdmAnalysis.getDescription());
    }

    private int scanPortfolioBasicForEdm(String instanceId, RLModelDataSource edm) {
        rlPortfolioRepository.deleteByRlModelDataSourceRlModelDataSourceId(edm.getRlModelDataSourceId());
        List<EdmPortfolioBasic> edmPortfolioBasics = listEdmPortfolioBasic(instanceId, edm.getRlId(), edm.getName());
        for (EdmPortfolioBasic edmPortfolioBasic : edmPortfolioBasics) {
            RLPortfolio rlPortfolio = rlPortfolioRepository.save(
                    new RLPortfolio(edmPortfolioBasic, edm)
            );
            RLPortfolioScanStatus rlPortfolioScanStatus = new RLPortfolioScanStatus(rlPortfolio, 0);
            rlPortfolioScanStatusRepository.save(rlPortfolioScanStatus);
            rlPortfolio.setRlPortfolioScanStatus(rlPortfolioScanStatus);
            rlPortfolioRepository.save(rlPortfolio);
        }
        return edmPortfolioBasics.size();
    }

    private List<RLPortfolio> scanPortfolioDetail(String instanceId, List<PortfolioHeader> rlPortfolioList, Long projectId) {

        Integer runId;
        Map<MultiKey, List<String>> portfolioIdAndTypeAndCurrencyByEdms = new HashMap<>();
        List<RLPortfolio> allScannedPortfolios = new ArrayList<>();

        if (rlPortfolioList != null && !rlPortfolioList.isEmpty()) {
            rlPortfolioList.stream().map(item -> new MultiKey(item.getEdmId(), item.getEdmName())).distinct()
                    .forEach(key -> {
                        portfolioIdAndTypeAndCurrencyByEdms.put(key, this.getPortfolioIdPortfolioTypeCurrencyByEdm((Long) key.getKey(0), (String) key.getKey(1), rlPortfolioList));
                    });


            for (Map.Entry<MultiKey, List<String>> multiKeyListEntry : portfolioIdAndTypeAndCurrencyByEdms.entrySet()) {

                Long edmId = (Long) multiKeyListEntry.getKey().getKey(0);
                String edmName = (String) multiKeyListEntry.getKey().getKey(1);

                runId = createEDMPortfolioContext(instanceId, edmId, edmName, multiKeyListEntry.getValue(), new ArrayList<String>());

                this.listEdmPortfolio(instanceId, edmId, edmName, multiKeyListEntry.getValue())
                        .forEach(edmPortfolio -> {
                            this.rlPortfolioRepository.updatePortfolioById(projectId, edmPortfolio);
                            RLPortfolio rlPortfolio = rlPortfolioRepository.findByEdmIdAndEdmNameAndRlIdAndProjectId(edmPortfolio.getEdmId(),
                                    edmPortfolio.getEdmName(), edmPortfolio.getPortfolioId(), projectId);
                            allScannedPortfolios.add(rlPortfolio);
                        });

                this.getEdmAllPortfolioAnalysisRegions(instanceId, edmId, edmName, runId)
                        .stream()
                        .map(portfolioAnalysisRegions -> {
                            RLPortfolio rlPortfolio = rlPortfolioRepository.findByEdmIdAndEdmNameAndRlIdAndProjectId(edmId, edmName, portfolioAnalysisRegions.getPortfolioId(), projectId);
                            rlPortfolio.getRlPortfolioScanStatus().setScanLevel(ScanLevelEnum.Detailed);
                            rlPortfolio = rlPortfolioRepository.save(rlPortfolio);
                            rlPortfolioAnalysisRegionRepository.deleteByRlPortfolioRlPortfolioId(rlPortfolio.getRlPortfolioId());
                            return new RLPortfolioAnalysisRegion(portfolioAnalysisRegions, rlPortfolio);
                        })
                        .forEach(analysisProfileRegion -> rlPortfolioAnalysisRegionRepository.save(analysisProfileRegion));

                this.removeEDMPortfolioContext(instanceId, runId);
            }
        }
        return allScannedPortfolios;
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
                .map(portfolioHeader -> {
                    String currency = portfolioHeader.getCurrency() == null ? "USD" : portfolioHeader.getCurrency();
                    return portfolioHeader.getPortfolioId() + "~" + portfolioHeader.getPortfolioType() + "~" + currency;
                }).collect(toList());
    }

    private void getSourceEpHeaders(Map<MultiKey, List<Long>> analysisByRDM, List<Float> epPoints, List<String> fpCodes, Long projectId, String instanceId) {
        for (Map.Entry<MultiKey, List<Long>> multiKeyListEntry : analysisByRDM.entrySet()) {

            Long rdmId = (Long) multiKeyListEntry.getKey().getKey(0);
            String rdmName = (String) multiKeyListEntry.getKey().getKey(1);

            this.extractSourceEpHeaders(instanceId, rdmId, rdmName, projectId, epPoints, multiKeyListEntry.getValue(), fpCodes);
        }
    }

    /****** Risk Link Interface ******/

    public Page<DataSource> listAvailableDataSources(String instanceId, String keyword, int offset, int size) {
        String sql = "execute " + DATABASE + ".dbo.RR_RL_ListAvailableDataSources";
        this.logger.debug("Service starts executing the query ...");
        List<DataSource> dataSources = getJdbcTemplate(instanceId).query(
                sql,
                new DataSourceRowMapper());
        if (!StringUtils.isEmpty(keyword))
            dataSources = dataSources.stream().filter(ds -> StringUtils.containsIgnoreCase(ds.getName(), keyword))
                    .collect(toList());

        List<DataSource> parsedDatasources = applyOffsetSizeToList(dataSources, offset, size);

        this.logger.debug("the data returned ", parsedDatasources);
        return new PageImpl<>(parsedDatasources, PageRequest.of(offset / size, size), dataSources.size());
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

    public List<EdmPortfolio> listEdmPortfolio(String instanceId, Long id, String name, List<String> portfolioList) {
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

    public List<RdmAnalysisEpCurves> getRdmAllAnalysisEpCurves(String instanceId, Long id, String name, List<Float> epPoints, List<Long> analysisIdList, List<String> finPerspList) {

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

    public List<EdmAllPortfolioAnalysisRegions> getEdmAllPortfolioAnalysisRegions(String instanceId, Long edmId, String edmName, Integer runId) {

        String sql = "execute " + DATABASE + ".dbo.RR_RL_GetEDMPortfolioAnalysisRegionsList @edm_id=" + edmId.longValue() + ", @edm_name=" + edmName + ", @RunID=" + runId;
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
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = createNamedParameterTemplate(instanceId);
        params.put("RunID", runId);
        logger.debug("removeEDMPortfolioContextSQL: {}, params: {}, runId: {}", removeEDMPortfolioContextSQL, params, runId);
        namedParameterJdbcTemplate.update(removeEDMPortfolioContextSQL, params);
    }

    public boolean extractLocationLevelExposureDetails(Long edmId, String edmName, String instance, Long projectId, RLPortfolio rlPortfolio, ModelPortfolioEntity modelPortfolio, File file, String extractName, String sqlQuery) {

        //TODO : Review this method with Viet
        Map<String, Object> dataQueryParams = new HashMap<>();
//        dataQueryParams.put("Edm_id", edm.getRlId());
        dataQueryParams.put("Edm_id", edmId);
        dataQueryParams.put("Edm_name", edmName);
        dataQueryParams.put("PortfolioID_RMS", rlPortfolio.getRlId());
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

    public List<Map<String, Object>> getByQuery(String query, String instanceId, Object... args) {
        return this.getJdbcTemplate(instanceId).queryForList(query, args);
    }

    private JdbcTemplate getJdbcTemplate(String instanceId) {
        return new JdbcTemplate(rmsInstanceCache.getDataSource(instanceId));
    }

    public NamedParameterJdbcTemplate createNamedParameterTemplate(String instanceId) {
        return new NamedParameterJdbcTemplate(this.getJdbcTemplate(instanceId));
    }

    private void extractSourceEpHeaders(String instanceId, Long rdmId, String rdmName, Long projectId, List<Float> epPoints, List<Long> analysisIds, List<String> fpCodes) {

        List<RdmAnalysisEpCurves> epCurves = this.getRdmAllAnalysisEpCurves(instanceId, rdmId, rdmName, epPoints, analysisIds, fpCodes);

        this.getRdmAllAnalysisSummaryStats(instanceId, rdmId, rdmName, fpCodes, analysisIds)
                .forEach(stat -> {
                    RLSourceEpHeader sourceEpHeader = new RLSourceEpHeader(stat);
                    RLAnalysis rlAnalysis = rlAnalysisRepository.findByRdmIdAndRdmNameAndRlIdAndProjectId(rdmId, rdmName, stat.getAnalysisId(), projectId);
                    sourceEpHeader.setRlAnalysis(rlAnalysis);
                    filterEpCurvesByStat(epCurves, stat).forEach(epC -> {
                        int statisticMetric = epC.getEbpTypeCode();
                        try {
                            String fieldName = this.generateEpCurveFieldName(epC.getReturnPeriod(), statisticMetric);
                            Utils.setAttribute(sourceEpHeader, fieldName, epC.getLoss());
                        } catch (Exception exp) {
                            exp.printStackTrace();
                        }
                    });
                    rlSourceEpHeaderRepository.save(sourceEpHeader);
                });
    }

    private String generateEpCurveFieldName(Long returnPeriod, int statisticMetric) throws Exception {
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
                    logger.warn("Non supported Stat Metric by RlSourceEpHeader Entity " + statisticMetric);
            }
        }
        if (fieldName != null) {
            return fieldName.concat(String.valueOf(returnPeriod));
        } else {
            logger.warn("Non supported Return Period by RlSourceEpHeader Entity " + returnPeriod);
            return null;
        }
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

    private String resolveSystemRegionPeril(long rlAnalysisId) {
        String systemRegionPeril = null;
        Optional<RLAnalysis> rlAnalysis = rlAnalysisRepository.findById(rlAnalysisId);
        if (rlAnalysis.isPresent()) {
            Map<String, RegionPerilNode> regionPerilMap = new HashMap<>();
            List<RLAnalysisProfileRegion> analysisProfileRegions = rlAnalysisProfileRegionsRepository.findByRlAnalysisRlAnalysisId(rlAnalysisId);
            for (RLAnalysisProfileRegion analysisProfileRegion : analysisProfileRegions) {
                RegionPerilEntity rp = regionPerilService.findRegionPerilByCountryCodeAdmin1CodePerilCode(analysisProfileRegion.getAnalysisRegion(), "", analysisProfileRegion.getPeril());
                if (rp != null) {
                    String rootRegionPerilCode = rlAnalysis.get().getRpCode();
                    //TODO: need to define a new ref table
//                    PEQTRegionPerilMapping peqtRegionPerilMapping = peqtRegionPerilMappingRepository.findByPeqtRegionPeril(analysis.getRpCode());
//                    if (peqtRegionPerilMapping != null && ! StringUtils.isEmpty(peqtRegionPerilMapping.getRrRegionPeril())) {
//                        rootRegionPerilCode = peqtRegionPerilMapping.getRrRegionPeril();
//                    }
                    if (isSameRegionPerilHierarchy(rootRegionPerilCode, rp)) {
                        fillWithParenting(regionPerilMap, rp, analysisProfileRegion);
                    }
                }
            }
            RegionPerilNode topLevelRegionPeril = null;
            for (Map.Entry<String, RegionPerilNode> e : regionPerilMap.entrySet()) {
                RegionPerilNode children = e.getValue();
                RegionPerilNode parent = regionPerilMap.get(e.getValue().regionPeril.getHierarchyParentCode());
                if (parent != null) {
                    parent.children.add(children);
                    children.parent = parent;
                } else {
                    topLevelRegionPeril = children;
                }
            }
            if (topLevelRegionPeril != null) {
                systemRegionPeril = crawlDownToSystemRegionPeril(topLevelRegionPeril);
            } else {
                logger.error("no top level RegionPeil found for analysis '{}':'{}' from RDM '{}':'{}'",
                        rlAnalysis.get().getRlId(), rlAnalysis.get().getAnalysisName(), rlAnalysis.get().getRdmId(), rlAnalysis.get().getRdmName());
            }
        } else {
            logger.error("RLAnalysis Id {} not found", rlAnalysisId);
        }
        return systemRegionPeril;
    }

    private boolean isSameRegionPerilHierarchy(String rootRegionPerilCode, RegionPerilEntity regionPeril) {
        RegionPerilEntity rp = regionPeril;
        while (rp != null && !StringUtils.isEmpty(rp.getHierarchyParentCode())) {
            rp = regionPerilService.findRegionPerilByRegionPerilCode(rp.getHierarchyParentCode());
        }

        if (rp != null && rp.getRegionPerilCode().equals(rootRegionPerilCode))
            return true;
        return false;
    }

    private void fillWithParenting(Map<String, RegionPerilNode> regionPerilMap, RegionPerilEntity rp, RLAnalysisProfileRegion rlAnalysisProfileRegion) {
        if (rp != null) {
            RegionPerilNode rpn = regionPerilMap.get(rp.getRegionPerilCode());
            if (rpn == null) {
                rpn = new RegionPerilNode(rp);
                regionPerilMap.put(rp.getRegionPerilCode(), rpn);
            }
            if (rlAnalysisProfileRegion != null) {
                rpn.associatedAnalysisRegions.add(rlAnalysisProfileRegion);
            }
            if (rp.getHierarchyLevel() > 0 && !regionPerilMap.containsKey(rp.getHierarchyParentCode())) {
                RegionPerilEntity rpParent = regionPerilService.findRegionPerilByRegionPerilCode(rp.getHierarchyParentCode());
                fillWithParenting(regionPerilMap, rpParent, null);
            }
        }
    }

    private String crawlDownToSystemRegionPeril(RegionPerilNode node) {
        if (node.getRegionPerilAAL(false) > 0.0d) {
            return node.getRegionPeril().getRegionPerilCode();
        } else {
            List<RegionPerilNode> candidates = new ArrayList<>();
            for (RegionPerilNode child : node.children) {
                if (child.getRegionPerilAAL(true) > 0.0d) {
                    candidates.add(child);
                }
            }
            if (candidates.size() > 1) {
                return node.getRegionPeril().getRegionPerilCode();
            } else {
                for (RegionPerilNode c : candidates) {
                    return crawlDownToSystemRegionPeril(c);
                }
            }
            return null;
        }
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

    private static class RegionPerilNode {
        private RegionPerilEntity regionPeril;
        private RegionPerilNode parent;
        private Set<RegionPerilNode> children = new HashSet<>();
        private Set<RLAnalysisProfileRegion> associatedAnalysisRegions = new HashSet<>();

        public RegionPerilNode(RegionPerilEntity regionPeril) {

            this.regionPeril = regionPeril;
        }

        public double getRegionPerilAAL(boolean withChildren) {
            if (associatedAnalysisRegions != null) {
                double sum = 0d;
                for (RLAnalysisProfileRegion analysisRegion : associatedAnalysisRegions) {
                    if (analysisRegion.getAal() != null) {
                        sum += analysisRegion.getAal().doubleValue();
                    }
                }
                if (withChildren && children != null && !children.isEmpty()) {
                    for (RegionPerilNode rpn : children) {
                        sum += rpn.getRegionPerilAAL(withChildren);
                    }
                }
                return sum;
            } else {
                return 0d;
            }
        }


        public RegionPerilNode getParent() {
            return parent;
        }

        public void setParent(RegionPerilNode parent) {
            this.parent = parent;
        }


        public Set<RegionPerilNode> getChildren() {
            return children;
        }

        public void setChildren(Set<RegionPerilNode> children) {
            this.children = children;
        }

        public RegionPerilEntity getRegionPeril() {
            return regionPeril;
        }

        public void setRegionPeril(RegionPerilEntity regionPeril) {
            this.regionPeril = regionPeril;
        }

        public Set<RLAnalysisProfileRegion> getAssociatedAnalysisRegions() {
            return associatedAnalysisRegions;
        }

        public void setAssociatedAnalysisRegions(Set<RLAnalysisProfileRegion> associatedAnalysisRegions) {
            this.associatedAnalysisRegions = associatedAnalysisRegions;
        }
    }
}
