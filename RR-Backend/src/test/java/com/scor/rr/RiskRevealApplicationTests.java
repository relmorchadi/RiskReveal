package com.scor.rr;

import com.scor.rr.domain.*;
import com.scor.rr.rest.RmsRessource;
import com.scor.rr.service.RmsService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RiskRevealApplicationTests {

    @Mock
    private RmsService rmsService;

    @Autowired
    private RmsRessource rmsRessource;

    private final Logger logger = LoggerFactory.getLogger(RiskRevealApplicationTests.class);

    private RdmAnalysis rdmAnalysis;
    private DataSource dataSource;
    private RdmAnalysisBasic rdmAnalysisBasic;
    private EdmPortfolioBasic edmPortfolioBasic;
    private EdmPortfolio edmPortfolio0;
    private EdmPortfolio edmPortfolio1;
    private RdmAnalysisEpCurves rdmAnalysisEpCurves1;
    private RdmAnalysisEpCurves rdmAnalysisEpCurves2;
    private RdmAnalysisEpCurves rdmAnalysisEpCurves3;
    private RdmAnalysisEpCurves rdmAnalysisEpCurves4;
    private RdmAllAnalysisSummaryStats rdmAllAnalysisSummaryStats1;
    private RdmAllAnalysisSummaryStats rdmAllAnalysisSummaryStats2;
    private RdmAllAnalysisSummaryStats rdmAllAnalysisSummaryStats3;
    private RdmAllAnalysisSummaryStats rdmAllAnalysisSummaryStats4;
    private AnalysisEpCurves analysisEpCurves;
    private AnalysisSummaryStats analysisSummaryStats;
    private RdmAllAnalysisProfileRegions rdmAllAnalysisProfileRegions;
    private AnalysisElt analysisElt;
    private EdmAllPortfolioAnalysisRegions edmAllPortfolioAnalysisRegions;
    private RdmAllAnalysisTreatyStructure rdmAllAnalysisTreatyStructure;
    private RmsExchangeRate rmsExchangeRate1;
    private RmsExchangeRate rmsExchangeRate2;
    private CChkBaseCcy cChkBaseCcy;
    private CChkBaseCcyFxRate cChkBaseCcyFxRate;
    private RdmAllAnalysisMultiRegionPerils rdmAllAnalysisMultiRegionPerils;

    private List<Long> analysisIdList = Arrays.asList(1L, 2L);
    private List<Integer> idList = Arrays.asList(1, 2);
    private List<String> portfolioIdList = Arrays.asList("1~DET", "2~DET");
    private static int epPoints = 50;
    private static String rdmName = "AC15_RL15_AUT_R";
    private static String edmName = "IED2017_TWTY_AD2_TWD_EDM170";
    private static String ccy = "CAD";
    private static Long rdmId = 50L;
    private static int edmId = 148;
    private static int id = 50;
    private static int analysisId = 1;
    private static String finPerspCode = "GR";
    private static Integer treatyLabelId = 120;
    private static List<String> finPerspList = Arrays.asList("GR", "RL");
    private static List<String> ccyList = Arrays.asList("CAD", "ANG");
    private String resultTest;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        dataSource = new DataSource();

        dataSource.setRmsId(49L);
        dataSource.setName("AC15_RL15_AUT_E");
        dataSource.setDateCreated("2018-01-17 19:26:44.910");
        dataSource.setType("EDM");
        dataSource.setVersionId(17);
        //**********************************************************************
        rdmAnalysisBasic = new RdmAnalysisBasic();
        rdmAnalysisBasic.setRdmId(50L);
        rdmAnalysisBasic.setRdmName("AC15_RL15_AUT_R");
        rdmAnalysisBasic.setAnalysisId(1L);
        rdmAnalysisBasic.setAnalysisName("T_QS_EUWS_ZUR_17B280E_WSt");
        rdmAnalysisBasic.setDescription("");
        rdmAnalysisBasic.setEngineVersion("15.0.1625.0");
        rdmAnalysisBasic.setGroupTypeName("Analysis");
        rdmAnalysisBasic.setCedant("WienerStaedtische");
        rdmAnalysisBasic.setLobName("");
        rdmAnalysisBasic.setGrouping(false);
        rdmAnalysisBasic.setEngineType("ALM");
        rdmAnalysisBasic.setRunDate("2015-06-02 10:00:21.000");
        rdmAnalysisBasic.setTypeName("Exceedance Probability");
        rdmAnalysisBasic.setPeril("WS");
        rdmAnalysisBasic.setSubPeril("");
        rdmAnalysisBasic.setLossAmplification("Unknown");
        rdmAnalysisBasic.setRegion("ZZ");
        rdmAnalysisBasic.setRegionName("Unknown Region");
        rdmAnalysisBasic.setModeName("Distributed");
        rdmAnalysisBasic.setUser1("");
        rdmAnalysisBasic.setUser2("");
        rdmAnalysisBasic.setUser3("");
        rdmAnalysisBasic.setUser4("");
        rdmAnalysisBasic.setAnalysisCurrency("EUR");
        rdmAnalysisBasic.setStatusDescription("ok");
        //**********************************************************************
        edmPortfolioBasic = new EdmPortfolioBasic();

        edmPortfolioBasic.setEdmId(148L);
        edmPortfolioBasic.setEdmName("IED2017_TWTY_AD2_TWD_EDM170");
        edmPortfolioBasic.setPortfolioId(1L);
        edmPortfolioBasic.setNumber("Taiwan");
        edmPortfolioBasic.setName("All Lines, Taiwan, Ind 2017 TY AD2");
        edmPortfolioBasic.setCreated("2017-03-16 00:00:00.000");
        edmPortfolioBasic.setDescription("By Country, All Lines");
        edmPortfolioBasic.setType("DET");
/*      edmPortfolioBasic.setPeril("WS");
        edmPortfolioBasic.setAgSource("RMS 2017");
        edmPortfolioBasic.setAgCedant("RMS_TWTY_Industry 2017_AD2");
        edmPortfolioBasic.setAgCurrency("TWD"); for null a values  */
        //***********************************************************************************************************
        rdmAnalysis = new RdmAnalysis();

        rdmAnalysis.setRdmId(50L);
        rdmAnalysis.setRdmName("AC15_RL15_AUT_R");
        rdmAnalysis.setAnalysisId(1L);
        rdmAnalysis.setAnalysisName("T_QS_EUWS_ZUR_17B280E_WSt");
        rdmAnalysis.setDescription("");
        rdmAnalysis.setDefaultGrain("T_QS_EUWS_ZUR_17B280E_WSt");
        rdmAnalysis.setExposureType("AGG-PORTFOLIO");
        rdmAnalysis.setExposureTypeCode(8029);
        rdmAnalysis.setEdmNameSourceLink("ZR1501_AUT_ALMPortfolio_E");
        rdmAnalysis.setExposureId(3L);
        rdmAnalysis.setAnalysisCurrency("EUR");
        //rdmAnalysis.setRmsExchangeRate(); for a null a value
        rdmAnalysis.setTypeCode(102);
        rdmAnalysis.setAnalysisType("SINGLE");
        rdmAnalysis.setRunDate("2015-06-02 10:00:21.000");
        rdmAnalysis.setRegion("ZZ");
        rdmAnalysis.setPeril("WS");
        rdmAnalysis.setRpCode("EUWS");
        //rdmAnalysis.setSubPeril(); for a null a value
        rdmAnalysis.setLossAmplification("Unknown");
        rdmAnalysis.setStatus(102L);
        rdmAnalysis.setAnalysisMode(2);
        rdmAnalysis.setEngineTypeCode(101);
        rdmAnalysis.setEngineType("ALM");
        rdmAnalysis.setEngineVersion("15.0.1625.0");
        rdmAnalysis.setEngineVersionMajor("15.0");
        rdmAnalysis.setProfileName("");
        rdmAnalysis.setProfileKey("RL_EUWS_Mv15.0");
        rdmAnalysis.setHasMultiRegionPerils(false);
        rdmAnalysis.setValidForExtract(true);
        //rdmAnalysis.setNotValidReason(); for a null a value
        //rdmAnalysis.setPurePremium(); for a null a value
        rdmAnalysis.setExposureTiv(0.0);
        rdmAnalysis.setGeoCode("");
        rdmAnalysis.setGeoDescription("EU");
        rdmAnalysis.setUser1("");
        rdmAnalysis.setUser2("");
        rdmAnalysis.setUser3("");
        rdmAnalysis.setUser4("");
        //***********************************************************************************************************
        edmPortfolio0 = new EdmPortfolio();

        edmPortfolio0.setEdmId(148L);
        edmPortfolio0.setEdmName("IED2017_TWTY_AD2_TWD_EDM170");
        edmPortfolio0.setPortfolioId(1L);
        edmPortfolio0.setNumber("Taiwan | All Lines, Taiwan, Ind 2017 TY AD2");
        edmPortfolio0.setName("All Lines, Taiwan, Ind 2017 TY AD2");
        edmPortfolio0.setCreated("2017-03-16 00:00:00.000");
        edmPortfolio0.setDescription("By Country, All Lines");
        edmPortfolio0.setType("DET");
        //edmPortfolio0.setPeril(); for a null a value
        //edmPortfolio0.setAgSource();
        //edmPortfolio0.setAgCedant();
        //edmPortfolio0.setAgCurrency();
        edmPortfolio0.setTiv(BigDecimal.valueOf(0.00).setScale(2));

        edmPortfolio1 = new EdmPortfolio();
        edmPortfolio1.setEdmId(148L);
        edmPortfolio1.setEdmName("IED2017_TWTY_AD2_TWD_EDM170");
        edmPortfolio1.setPortfolioId(2L);
        edmPortfolio1.setNumber("Taiwan, RES | RES, Taiwan, Ind 2017 TY AD2");
        edmPortfolio1.setName("RES, Taiwan, Ind 2017 TY AD2");
        edmPortfolio1.setCreated("2017-03-16 00:00:00.000");
        edmPortfolio1.setDescription("By Country, By Line of Business");
        edmPortfolio1.setType("DET");
        //edmPortfolio1.setPeril(); for a null a value
        //edmPortfolio1.setAgSource();
        //edmPortfolio1.setAgCedant();
        //edmPortfolio1.setAgCurrency();
        edmPortfolio1.setTiv(BigDecimal.valueOf(0.00).setScale(2));
        //*******************************************************************************************

        rdmAnalysisEpCurves1 = new RdmAnalysisEpCurves();

        rdmAnalysisEpCurves1.setId(1L);
        rdmAnalysisEpCurves1.setFinPerspCode("GR");
        rdmAnalysisEpCurves1.setEbpTypeCode(0);
        rdmAnalysisEpCurves1.setLoss(0);
        rdmAnalysisEpCurves1.setExceedanceProbabilty(50);
        rdmAnalysisEpCurves1.setReturnId(BigDecimal.valueOf(0.02).setScale(2));

        rdmAnalysisEpCurves2 = new RdmAnalysisEpCurves();

        rdmAnalysisEpCurves2.setId(1L);
        rdmAnalysisEpCurves2.setFinPerspCode("RL");
        rdmAnalysisEpCurves2.setEbpTypeCode(0);
        rdmAnalysisEpCurves2.setLoss(0);
        rdmAnalysisEpCurves2.setExceedanceProbabilty(50);
        rdmAnalysisEpCurves2.setReturnId(BigDecimal.valueOf(0.02).setScale(2));

        rdmAnalysisEpCurves3 = new RdmAnalysisEpCurves();

        rdmAnalysisEpCurves3.setId(2L);
        rdmAnalysisEpCurves3.setFinPerspCode("GR");
        rdmAnalysisEpCurves3.setEbpTypeCode(10);
        rdmAnalysisEpCurves3.setLoss(0);
        rdmAnalysisEpCurves3.setExceedanceProbabilty(50);
        rdmAnalysisEpCurves3.setReturnId(BigDecimal.valueOf(0.02).setScale(2));

        rdmAnalysisEpCurves4 = new RdmAnalysisEpCurves();

        rdmAnalysisEpCurves4.setId(2L);
        rdmAnalysisEpCurves4.setFinPerspCode("RL");
        rdmAnalysisEpCurves4.setEbpTypeCode(11);
        rdmAnalysisEpCurves4.setLoss(0);
        rdmAnalysisEpCurves4.setExceedanceProbabilty(50);
        rdmAnalysisEpCurves4.setReturnId(BigDecimal.valueOf(0.02).setScale(2));

        rdmAllAnalysisSummaryStats1 = new RdmAllAnalysisSummaryStats();

        rdmAllAnalysisSummaryStats1.setAnalysisId(1L);
        rdmAllAnalysisSummaryStats1.setFinPerspCode("GR");
        rdmAllAnalysisSummaryStats1.setOccurrenceBasis("PerEvent");
        rdmAllAnalysisSummaryStats1.setEpTypeCode(1);
        rdmAllAnalysisSummaryStats1.setPurePremium(BigDecimal.valueOf(11454673.5044312));
        rdmAllAnalysisSummaryStats1.setStdDev(BigDecimal.valueOf(14614733.5169415));
        rdmAllAnalysisSummaryStats1.setCov(BigDecimal.valueOf(1.27587517106339));

        rdmAllAnalysisSummaryStats2 = new RdmAllAnalysisSummaryStats();

        rdmAllAnalysisSummaryStats2.setAnalysisId(1L);
        rdmAllAnalysisSummaryStats2.setFinPerspCode("RL");
        rdmAllAnalysisSummaryStats2.setOccurrenceBasis("PerEvent");
        rdmAllAnalysisSummaryStats2.setEpTypeCode(1);
        rdmAllAnalysisSummaryStats2.setPurePremium(BigDecimal.valueOf(11454673.5044312));
        rdmAllAnalysisSummaryStats2.setStdDev(BigDecimal.valueOf(14614733.5169415));
        rdmAllAnalysisSummaryStats2.setCov(BigDecimal.valueOf(1.27587517106339));

        rdmAllAnalysisSummaryStats3 = new RdmAllAnalysisSummaryStats();

        rdmAllAnalysisSummaryStats3.setAnalysisId(2L);
        rdmAllAnalysisSummaryStats3.setFinPerspCode("GR");
        rdmAllAnalysisSummaryStats3.setOccurrenceBasis("PerEvent");
        rdmAllAnalysisSummaryStats3.setEpTypeCode(1);
        rdmAllAnalysisSummaryStats3.setPurePremium(BigDecimal.valueOf(7525806.3543131));
        rdmAllAnalysisSummaryStats3.setStdDev(BigDecimal.valueOf(10268499.6803201));
        rdmAllAnalysisSummaryStats3.setCov(BigDecimal.valueOf(1.36443846637579));

        rdmAllAnalysisSummaryStats4 = new RdmAllAnalysisSummaryStats();

        rdmAllAnalysisSummaryStats4.setAnalysisId(2L);
        rdmAllAnalysisSummaryStats4.setFinPerspCode("RL");
        rdmAllAnalysisSummaryStats4.setOccurrenceBasis("PerEvent");
        rdmAllAnalysisSummaryStats4.setEpTypeCode(1);
        rdmAllAnalysisSummaryStats4.setPurePremium(BigDecimal.valueOf(7525806.3543131));
        rdmAllAnalysisSummaryStats4.setStdDev(BigDecimal.valueOf(10268499.6803201));
        rdmAllAnalysisSummaryStats4.setCov(BigDecimal.valueOf(1.36443846637579));
        //*****************************************************************************************
        analysisEpCurves = new AnalysisEpCurves();

        analysisEpCurves.setAnalysisId(1);
        analysisEpCurves.setFinPerspCode("GR");
        analysisEpCurves.setEpTypeCode(0);
        analysisEpCurves.setLoss(450926690.8563491);
        analysisEpCurves.setExeceedanceProb(BigDecimal.valueOf(0.00001714694480486000).setScale(20));

        //*************************************************************************************************
        analysisSummaryStats = new AnalysisSummaryStats();

        analysisSummaryStats.setAnalysisId(1L);
        analysisSummaryStats.setFpCode("GR");
        analysisSummaryStats.setEpTypeCode(1);
        analysisSummaryStats.setPurePremium(BigDecimal.valueOf(11454673.5044312).setScale(7).doubleValue());
        analysisSummaryStats.setStdDev(BigDecimal.valueOf(14614733.5169415).setScale(7).doubleValue());
        analysisSummaryStats.setCov(BigDecimal.valueOf(1.27587517106339).setScale(14).doubleValue());

        //**************************************************
        rdmAllAnalysisProfileRegions = new RdmAllAnalysisProfileRegions();
        rdmAllAnalysisProfileRegions.setAnalysisid(2L);
        rdmAllAnalysisProfileRegions.setAnlysisRegion("EU-EU");
        rdmAllAnalysisProfileRegions.setProfileRegion("EU-EU");
        rdmAllAnalysisProfileRegions.setAnalysisRegionName("Europe");
        rdmAllAnalysisProfileRegions.setProfileRegion("EU");
        rdmAllAnalysisProfileRegions.setProfileRegionName("Europe");
        rdmAllAnalysisProfileRegions.setPeril("WS");
        rdmAllAnalysisProfileRegions.setAal(BigDecimal.valueOf(99999999.00).setScale(2));

        //****************************************************************************************
        analysisElt = new AnalysisElt();

        analysisElt.setAnalysisId(1);
        analysisElt.setFinPerspCode("GR");
        analysisElt.setEventId(3160002L);
        analysisElt.setRate(1E-10);
        analysisElt.setLoss(BigDecimal.valueOf(28160796.6674257).setScale(7).doubleValue());
        analysisElt.setStdDevC(BigDecimal.valueOf(19009281.734197).setScale(6).doubleValue());
        analysisElt.setExposureValue(152009835130L);

        //***************************************************************************************************
        edmAllPortfolioAnalysisRegions = new EdmAllPortfolioAnalysisRegions();

        edmAllPortfolioAnalysisRegions.setPortfolioId(2L);
        edmAllPortfolioAnalysisRegions.setPortfolioType("DET");
        edmAllPortfolioAnalysisRegions.setAnalysisRegion("TW-TW");
        edmAllPortfolioAnalysisRegions.setAnalysisRegionDesc("Taiwan");
        edmAllPortfolioAnalysisRegions.setPeril("WS");
        edmAllPortfolioAnalysisRegions.setTotalTiv(BigDecimal.valueOf(303794411788.074).setScale(3, RoundingMode.HALF_UP).doubleValue());
        edmAllPortfolioAnalysisRegions.setLocCount(736);
        //************************************************************************************************************
        rdmAllAnalysisTreatyStructure = new RdmAllAnalysisTreatyStructure();

        rdmAllAnalysisTreatyStructure.setAnalysisId(40);
        rdmAllAnalysisTreatyStructure.setTreatyId(179);
        rdmAllAnalysisTreatyStructure.setTreatyNum("1.WXS 0.3m x 0.3m");
        rdmAllAnalysisTreatyStructure.setTreatyName("1.WXS 0.3m x 0.3m");
        rdmAllAnalysisTreatyStructure.setTreatyType("Working Excess");
        rdmAllAnalysisTreatyStructure.setRiskLimit(300000);
        rdmAllAnalysisTreatyStructure.setOccurenceLimit(300000);
        rdmAllAnalysisTreatyStructure.setAttachmentPoint(300000);
        rdmAllAnalysisTreatyStructure.setLob("HHD, RPO");
        rdmAllAnalysisTreatyStructure.setCedant("Covea UK");
        rdmAllAnalysisTreatyStructure.setPctCovered(100);
        rdmAllAnalysisTreatyStructure.setPctPlaced(100);
        rdmAllAnalysisTreatyStructure.setPctRiShared(100);
        rdmAllAnalysisTreatyStructure.setPctRetention(0);
        rdmAllAnalysisTreatyStructure.setNoofReinstatements(99);
        rdmAllAnalysisTreatyStructure.setInuringPriority(1);
        rdmAllAnalysisTreatyStructure.setCcyCode("GBP");
        rdmAllAnalysisTreatyStructure.setAttachementBasis("Losses Occurring");
        rdmAllAnalysisTreatyStructure.setExposureLevel("Location");
        //************************************************************************************************************
        rmsExchangeRate1 = new RmsExchangeRate();

        rmsExchangeRate1.setCcy("CAD");
        rmsExchangeRate1.setRoe(BigDecimal.valueOf(1.3108429700).setScale(10).doubleValue());
        rmsExchangeRate1.setRoeAsAt("2018-09-30 00:00:00.000");

        rmsExchangeRate2 = new RmsExchangeRate();

        rmsExchangeRate2.setCcy("ANG");
        rmsExchangeRate2.setRoe(BigDecimal.valueOf(1.7609894900).setScale(10).doubleValue());
        rmsExchangeRate2.setRoeAsAt("2018-09-30 00:00:00.000");
        //*************************************************************************************************************
        cChkBaseCcy = new CChkBaseCcy();

        cChkBaseCcy.setResult(0);
        //cChkBaseCcy.setErrorMessage(); for null value

        cChkBaseCcyFxRate = new CChkBaseCcyFxRate();

        cChkBaseCcyFxRate.setResult(0);
        //cChkBaseCcyFxRate.setErrorMessage(); For null value

        //**************************************************************************************************************
        rdmAllAnalysisMultiRegionPerils = new RdmAllAnalysisMultiRegionPerils();

        rdmAllAnalysisMultiRegionPerils.setRdmId(832L);
        rdmAllAnalysisMultiRegionPerils.setRdmName("TC2016_MM_R");
        rdmAllAnalysisMultiRegionPerils.setAnalysisId(123L);
        rdmAllAnalysisMultiRegionPerils.setSsRegion("NA");
        rdmAllAnalysisMultiRegionPerils.setSsPeril("EQ");
        rdmAllAnalysisMultiRegionPerils.setSsRegionPeril("NAEQ");
        rdmAllAnalysisMultiRegionPerils.setEvCount(23107L);
        rdmAllAnalysisMultiRegionPerils.setProfileKey("RL_NAEQ_Mv9.0");

        //**************************************************************************************************************
        resultTest = "{\"RmsDlmProfile\": {\n" +
                "    \"AnalysisType\": {\n" +
                "        \"content\": 102,\n" +
                "        \"Name\": \"Exceedance Probability\"\n" +
                "    },\n" +
                "    \"AnalysisModeValue\": 0,\n" +
                "    \"EventDate\": \"2015-06-02T00:00:00\",\n" +
                "    \"EventRateSet\": {\n" +
                "        \"content\": 84,\n" +
                "        \"Name\": \"RMS 2015 Stochastic Event Rates\"\n" +
                "    },\n" +
                "    \"AssumeUnknownPrimary\": {\n" +
                "        \"Code\": 0,\n" +
                "        \"ConstructionModifier\": {\n" +
                "            \"content\": 0,\n" +
                "            \"Name\": \"None\"\n" +
                "        }\n" +
                "    },\n" +
                "    \"ProfileName\": \"\",\n" +
                "    \"InsuranceType\": {\n" +
                "        \"content\": 1,\n" +
                "        \"Name\": \"Property\"\n" +
                "    },\n" +
                "    \"VulnerabilityCurve\": {\n" +
                "        \"content\": 0,\n" +
                "        \"Name\": \"\"\n" +
                "    },\n" +
                "    \"AddressResidualDemandSurge\": false,\n" +
                "    \"ExposureTime\": {\n" +
                "        \"content\": 0,\n" +
                "        \"Name\": \"12 AM\"\n" +
                "    },\n" +
                "    \"EventTypeFilters\": {\n" +
                "        \"EventTypeFilter\": {\n" +
                "            \"content\": 0,\n" +
                "            \"Name\": \"None\"\n" +
                "        },\n" +
                "        \"Code\": 0\n" +
                "    },\n" +
                "    \"ExposureDay\": {\n" +
                "        \"content\": 0,\n" +
                "        \"Name\": \"\"\n" +
                "    },\n" +
                "    \"AssumeUnknownSecondary\": false,\n" +
                "    \"LossAmplifications\": {\n" +
                "        \"LossAmplification\": {\n" +
                "            \"content\": 536870912,\n" +
                "            \"Name\": \"Unknown\"\n" +
                "        },\n" +
                "        \"Code\": 536870912\n" +
                "    },\n" +
                "    \"ExposureAdjustment\": {\n" +
                "        \"content\": 0,\n" +
                "        \"Name\": \"None\"\n" +
                "    },\n" +
                "    \"PercentSubject\": 0,\n" +
                "    \"ScaleFactor\": 0,\n" +
                "    \"ScaleBIValues\": 0,\n" +
                "    \"AnalysisMode\": {\n" +
                "        \"content\": 2,\n" +
                "        \"Name\": \"Distributed\"\n" +
                "    },\n" +
                "    \"TimeWindow\": 1,\n" +
                "    \"Peril\": {\n" +
                "        \"content\": \"WS\",\n" +
                "        \"Name\": \"Windstorm\"\n" +
                "    },\n" +
                "    \"WindstormOptions\": {\n" +
                "        \"Assume2Percent\": false,\n" +
                "        \"OtherFactor\": 0,\n" +
                "        \"OtherRate\": 0,\n" +
                "        \"MultiFamilyRate\": 0,\n" +
                "        \"MultiFamilyFactor\": 0,\n" +
                "        \"SingleFamilyRate\": 0,\n" +
                "        \"GroundUpModifiers\": {\n" +
                "            \"GroundUpModifier\": {\n" +
                "                \"content\": 0,\n" +
                "                \"Name\": \"None\"\n" +
                "            },\n" +
                "            \"Code\": 0\n" +
                "        },\n" +
                "        \"CoverageLeakage\": {\n" +
                "            \"Coverage\": {\n" +
                "                \"content\": 0,\n" +
                "                \"Name\": \"None\"\n" +
                "            },\n" +
                "            \"Code\": 0\n" +
                "        },\n" +
                "        \"IgnoreLocalDefenses\": false,\n" +
                "        \"SingleFamilyFactor\": 0,\n" +
                "        \"FootprintFile\": \"\",\n" +
                "        \"EventNumber\": 0,\n" +
                "        \"MinSaffCategory\": 0,\n" +
                "        \"ResetFloodElevationToDefault\": false\n" +
                "    },\n" +
                "    \"SubPerils\": {\"Code\": 0},\n" +
                "    \"ScaleBuildingValues\": 0,\n" +
                "    \"Region\": {\n" +
                "        \"content\": \"ZZ\",\n" +
                "        \"Name\": \"Unknown Region\"\n" +
                "    },\n" +
                "    \"InjuryCostScheme\": {\n" +
                "        \"content\": 0,\n" +
                "        \"Name\": \"injury-name\"\n" +
                "    },\n" +
                "    \"ModellingOptions\": {\"ModellingOption\": {\"Code\": \"TEST_OPTION\"}},\n" +
                "    \"AnalysisRegions\": {\"AnalysisRegion\": {\n" +
                "        \"content\": \"EU\",\n" +
                "        \"Name\": \"Europe\"\n" +
                "    }},\n" +
                "    \"ScaleContentValues\": 0\n" +
                "}}";
    }

    @Test
    public void listAvailableDataSources() {
        Mockito.when(rmsService.listAvailableDataSources()).thenReturn(Arrays.asList(dataSource));
        List<DataSource> dataSources = (List<DataSource>) rmsRessource.listAvailableDataSources().getBody();
        Assert.assertNotNull(dataSources.get(0));
        Assert.assertNotNull(dataSource);
        try {
            Assert.assertEquals(dataSource, dataSources.get(0));
            this.logger.debug("Test has been passed successfully,The objects are equal");
        } catch (AssertionError e) {

            this.logger.debug("Test Failed !!!!!, the objects are not equal", e);
            throw e;
        }
    }

    @Test
    public void listRdmAnalysis() {
        Mockito.when(rmsService.listRdmAnalysis(rdmAnalysis.getRdmId(), rdmAnalysis.getRdmName(), analysisIdList)).thenReturn(Arrays.asList(rdmAnalysis));
        List<RdmAnalysis> rdmAnalyses = (List<RdmAnalysis>) rmsRessource.listRdmAnalysis(rdmAnalysis.getRdmId(), rdmAnalysis.getRdmName(), analysisIdList).getBody();
        Assert.assertNotNull(rdmAnalysis);
        Assert.assertNotNull(rdmAnalyses.get(0));

        try {
            Assert.assertEquals(rdmAnalysis, rdmAnalyses.get(0));
            this.logger.debug("Test has been passed successfully,The objects are equal");
        } catch (AssertionError e) {
            this.logger.debug("Test Failed !!!!!, the objects are not equal", e);
            throw e;
        }
    }

    @Test
    public void listRdmAnalysisBasic() {
        Mockito.when(rmsService.listRdmAnalysisBasic(rdmAnalysisBasic.getRdmId(), rdmAnalysisBasic.getRdmName())).thenReturn(Arrays.asList(rdmAnalysisBasic));
        List<RdmAnalysisBasic> rdmAnalysisBasics = (List<RdmAnalysisBasic>) rmsRessource.listRdmAnalysisBasic(rdmAnalysisBasic.getRdmId(), rdmAnalysisBasic.getRdmName()).getBody();
        Assert.assertNotNull(rdmAnalysisBasic);
        Assert.assertNotNull(rdmAnalysisBasics.get(0));
        try {
            Assert.assertEquals(rdmAnalysisBasic, rdmAnalysisBasics.get(0));
            this.logger.debug("Test has been passed successfully,The objects are equal");
        } catch (AssertionError e) {
            this.logger.debug("Test Failed !!!!!, the objects are not equal", e);
            throw e;
        }
    }

    @Test
    public void listEdmPortfolioBasic() {
        Mockito.when(rmsService.listEdmPortfolioBasic(edmPortfolioBasic.getEdmId(), edmPortfolioBasic.getEdmName())).thenReturn(Arrays.asList(edmPortfolioBasic));
        List<EdmPortfolioBasic> edmPortfolioBasics = (List<EdmPortfolioBasic>) rmsRessource.listEdmPortfolioBasic(edmPortfolioBasic.getEdmId(), edmPortfolioBasic.getEdmName()).getBody();
        Assert.assertNotNull(edmPortfolioBasics);
        Assert.assertNotNull(edmPortfolioBasic);
        try {
            Assert.assertEquals(edmPortfolioBasic, edmPortfolioBasics.get(0));
            this.logger.debug("Test has been passed successfully,The objects are equal");

        } catch (AssertionError e) {
            this.logger.debug("Test Failed !!!!!, the objects are not equal", e);
            throw e;
        }
    }

    @Test
    public void listEdmPortfolio() {

        Mockito.when(rmsService.listEdmPortfolio(edmPortfolio0.getEdmId(), edmPortfolio0.getEdmName(), portfolioIdList)).thenReturn(Arrays.asList(edmPortfolio0, edmPortfolio1));
        List<EdmPortfolio> edmPortfolios = (List<EdmPortfolio>) rmsRessource.listEdmPortfolio(edmPortfolio0.getEdmId(), edmPortfolio0.getEdmName(), portfolioIdList).getBody();
        Assert.assertNotNull(edmPortfolios.get(0));
        Assert.assertNotNull(edmPortfolios.get(1));
        Assert.assertNotNull(edmPortfolio0);
        Assert.assertNotNull(edmPortfolio1);
        try {
            Assert.assertEquals(edmPortfolio0, edmPortfolios.get(0));
            Assert.assertEquals(edmPortfolio1, edmPortfolios.get(1));
            this.logger.debug("Test has been passed successfully,The objects are equal");
        } catch (AssertionError e) {
            this.logger.debug("Test Failed !!!!!, the objects are not equal", e);
            throw e;
        }
    }

    @Test
    public void listRdmAllAnalysisEpCurves() {
        List<RdmAnalysisEpCurves> rdmAnalysisEpCurves = Arrays.asList(rdmAnalysisEpCurves1, rdmAnalysisEpCurves2, rdmAnalysisEpCurves3, rdmAnalysisEpCurves4);
        Mockito.when(rmsService.listRdmAllAnalysisEpCurves(rdmId, rdmName, epPoints, analysisIdList, finPerspList)).thenReturn(rdmAnalysisEpCurves);
        List<RdmAnalysisEpCurves> rdmAnalysisEpCurvesList = (List<RdmAnalysisEpCurves>) rmsRessource.listRdmAllAnalysisEpCurves(rdmId, rdmName, epPoints, analysisIdList, finPerspList).getBody();
        Assert.assertNotNull(rdmAnalysisEpCurvesList.get(0));
        Assert.assertNotNull(rdmAnalysisEpCurvesList.get(4));
        Assert.assertNotNull(rdmAnalysisEpCurvesList.get(10));
        Assert.assertNotNull(rdmAnalysisEpCurvesList.get(15));
        try {
            Assert.assertEquals(rdmAnalysisEpCurvesList.get(0), rdmAnalysisEpCurves.get(0));
            Assert.assertEquals(rdmAnalysisEpCurvesList.get(4), rdmAnalysisEpCurves.get(1));
            Assert.assertEquals(rdmAnalysisEpCurvesList.get(10), rdmAnalysisEpCurves.get(2));
            Assert.assertEquals(rdmAnalysisEpCurvesList.get(15), rdmAnalysisEpCurves.get(3));
            this.logger.debug("Test has been passed successfully,The objects are equal");
        } catch (AssertionError e) {
            this.logger.debug("Test Failed !!!!!, the objects are not equal", e);
            throw e;
        }
    }

    @Test
    public void getRdmAllAnalysisSummaryStats() {
        List<RdmAllAnalysisSummaryStats> rdmAllAnalysisSummaryStats = Arrays.asList(rdmAllAnalysisSummaryStats1, rdmAllAnalysisSummaryStats2, rdmAllAnalysisSummaryStats3, rdmAllAnalysisSummaryStats4);
        Mockito.when(rmsService.getRdmAllAnalysisSummaryStats(rdmId, rdmName, finPerspList, analysisIdList)).thenReturn(rdmAllAnalysisSummaryStats);
        List<RdmAllAnalysisSummaryStats> rdmAllAnalysisSummaryStatss = (List<RdmAllAnalysisSummaryStats>) rmsRessource.getRdmAllAnalysisSummaryStats(rdmId, rdmName, finPerspList, analysisIdList).getBody();
        Assert.assertNotNull(rdmAllAnalysisSummaryStatss.get(0));
        Assert.assertNotNull(rdmAllAnalysisSummaryStatss.get(1));
        Assert.assertNotNull(rdmAllAnalysisSummaryStatss.get(2));
        Assert.assertNotNull(rdmAllAnalysisSummaryStatss.get(3));
        try {
            Assert.assertEquals(rdmAllAnalysisSummaryStats.get(0), rdmAllAnalysisSummaryStatss.get(0));
            Assert.assertEquals(rdmAllAnalysisSummaryStats.get(1), rdmAllAnalysisSummaryStatss.get(1));
            Assert.assertEquals(rdmAllAnalysisSummaryStats.get(2), rdmAllAnalysisSummaryStatss.get(2));
            Assert.assertEquals(rdmAllAnalysisSummaryStats.get(3), rdmAllAnalysisSummaryStatss.get(3));
            this.logger.debug("Test has been passed successfully,The objects are equal");
        } catch (AssertionError e) {
            this.logger.debug("Test Failed !!!!!, the objects are not equal", e);
            throw e;
        }
    }

    @Test
    public void getAnalysisEpCurves() {
        Mockito.when(rmsService.getAnalysisEpCurves(id, rdmName, analysisId, finPerspCode, treatyLabelId)).thenReturn(Arrays.asList(analysisEpCurves));
        List<AnalysisEpCurves> analysisEpCurvess = (List<AnalysisEpCurves>) rmsRessource.getAnalysisEpCurves(id, rdmName, analysisId, finPerspCode, treatyLabelId).getBody();
        Assert.assertNotNull(analysisEpCurvess.get(0));

        try {
            Assert.assertEquals(analysisEpCurves, analysisEpCurvess.get(0));
            this.logger.debug("Test has been passed successfully,The objects are equal");
        } catch (AssertionError e) {
            this.logger.debug("Test Failed !!!!!, the objects are not equal", e);
            throw e;
        }
    }

    @Test
    public void getAnalysisSummaryStats() {
        Mockito.when(rmsService.getAnalysisSummaryStats(id, rdmName, analysisId, finPerspCode, treatyLabelId)).thenReturn(Arrays.asList(analysisSummaryStats));
        List<AnalysisSummaryStats> analysisSummaryStatss = (List<AnalysisSummaryStats>) rmsRessource.getAnalysisSummaryStats(id, rdmName, analysisId, finPerspCode, treatyLabelId).getBody();
        Assert.assertNotNull(analysisSummaryStatss.get(0));
        try {
            Assert.assertEquals(analysisSummaryStats, analysisSummaryStatss.get(0));
            this.logger.debug("Test has been passed successfully,The objects are equal");
        } catch (AssertionError e) {
            this.logger.debug("Test Failed !!!!!, the objects are not equal", e);
            throw e;
        }
    }

    @Test
    public void getRdmAllAnalysisProfileRegions() {
        Mockito.when(rmsService.getRdmAllAnalysisProfileRegions(id, rdmName, idList)).thenReturn(Arrays.asList(rdmAllAnalysisProfileRegions));
        List<RdmAllAnalysisProfileRegions> rdmAllAnalysisProfileRegionss = (List<RdmAllAnalysisProfileRegions>) rmsRessource.getRdmAllAnalysisProfileRegions(id, rdmName, idList).getBody();
        Assert.assertNotNull(rdmAllAnalysisProfileRegionss.get(1));
        try {
            Assert.assertEquals(rdmAllAnalysisProfileRegionss.get(1), rdmAllAnalysisProfileRegions);
            this.logger.debug("Test has been passed successfully,The objects are equal");
        } catch (AssertionError e) {
            this.logger.debug("Test Failed !!!!!, the objects are not equal", e);
            throw e;
        }
    }

    @Test
    public void getAnalysisElt() {
        Mockito.when(rmsService.getAnalysisElt(id, rdmName, analysisId, finPerspCode, treatyLabelId)).thenReturn(Arrays.asList(analysisElt));
        List<AnalysisElt> analysisElts = (List<AnalysisElt>) rmsRessource.getAnalysisElt(id, rdmName, analysisId, finPerspCode, treatyLabelId).getBody();
        Assert.assertNotNull(analysisElts.get(0));

        try {
            Assert.assertEquals(analysisElts.get(0), analysisElt);
            this.logger.debug("Test has been passed successfully,The objects are equal");
        } catch (AssertionError e) {
            this.logger.debug("Test Failed !!!!!, the objects are not equal", e);
            throw e;
        }
    }

    @Test
    public void getEdmAllPortfolioAnalysisRegions() {
        Mockito.when(rmsService.getEdmAllPortfolioAnalysisRegions(edmId, edmName, ccy)).thenReturn(Arrays.asList(edmAllPortfolioAnalysisRegions));
        List<EdmAllPortfolioAnalysisRegions> edmAllPortfolioAnalysisRegionss = (List<EdmAllPortfolioAnalysisRegions>) rmsRessource.getEdmAllPortfolioAnalysisRegions(edmId, edmName, ccy).getBody();
        Assert.assertNotNull(edmAllPortfolioAnalysisRegionss.get(1));

        try {
            Assert.assertEquals(edmAllPortfolioAnalysisRegionss.get(1), edmAllPortfolioAnalysisRegions);
            this.logger.debug("Test has been passed successfully,The objects are equal");
        } catch (AssertionError e) {
            this.logger.debug("Test Failed !!!!!, the objects are not equal", e);
            throw e;
        }
    }

    @Test
    public void getRdmAllAnalysisTreatyStructure() {
        Mockito.when(rmsService.getRdmAllAnalysisTreatyStructure(64, "AC15_RL15_GBR_R", Arrays.asList(40, 41))).thenReturn(Arrays.asList(rdmAllAnalysisTreatyStructure));
        List<RdmAllAnalysisTreatyStructure> rdmAllAnalysisTreatyStructures = (List<RdmAllAnalysisTreatyStructure>) rmsRessource.getRdmAllAnalysisTreatyStructure(64, "AC15_RL15_GBR_R", Arrays.asList(40, 41)).getBody();
        Assert.assertNotNull(rdmAllAnalysisTreatyStructures.get(0));
        try {
            Assert.assertEquals(rdmAllAnalysisTreatyStructures.get(0), rdmAllAnalysisTreatyStructure);
            this.logger.debug("Test has been passed successfully,The objects are equal");
        } catch (AssertionError e) {
            this.logger.debug("Test Failed !!!!!, the objects are not equal", e);
            throw e;
        }

    }

    @Test
    public void getRmsExchangeRates() {
        List<RmsExchangeRate> rmsExchangeRates = Arrays.asList(rmsExchangeRate1, rmsExchangeRate2);
        Mockito.when(rmsService.getRmsExchangeRates(ccyList)).thenReturn(rmsExchangeRates);
        List<RmsExchangeRate> exchangeRates = (List<RmsExchangeRate>) rmsRessource.getRmsExchangeRates(ccyList).getBody();
        Assert.assertNotNull(exchangeRates.get(0));
        Assert.assertNotNull(exchangeRates.get(1));
        Assert.assertNotNull(exchangeRates);
        try {
            Assert.assertEquals(exchangeRates.get(0), rmsExchangeRates.get(0));
            Assert.assertEquals(exchangeRates.get(1), rmsExchangeRates.get(1));
            this.logger.debug("Test has been passed successfully,The objects are equal");
        } catch (AssertionError e) {

            this.logger.debug("Test Failed !!!!!, the objects are not equal", e);
            throw e;
        }
    }

    @Test
    public void getCChkBaseCcy() {
        Mockito.when(rmsService.getCChBaseCcy()).thenReturn(Arrays.asList(cChkBaseCcy));
        List<CChkBaseCcy> cChkBaseCcies = (List<CChkBaseCcy>) rmsRessource.getCChBaseCcy().getBody();
        Assert.assertNotNull(cChkBaseCcies.get(0));
        try {
            Assert.assertEquals(cChkBaseCcies.get(0), cChkBaseCcy);
            this.logger.debug("Test has been passed successfully,The objects are equal");
        } catch (AssertionError e) {

            this.logger.debug("Test Failed !!!!!, the objects are not equal", e);
            throw e;
        }
    }

    @Test
    public void getCChkBaseCcyFxRate() {
        Mockito.when(rmsService.getCChkBaseCcyFxRate()).thenReturn(Arrays.asList(cChkBaseCcyFxRate));
        List<CChkBaseCcyFxRate> cChkBaseCcyFxRates = (List<CChkBaseCcyFxRate>) rmsRessource.getCChkBaseCcyFxRate().getBody();
        Assert.assertNotNull(cChkBaseCcyFxRates.get(0));
        try {
            Assert.assertEquals(cChkBaseCcyFxRates.get(0), cChkBaseCcyFxRate);
            this.logger.debug("Test has been passed successfully,The objects are equal");
        } catch (AssertionError e) {

            this.logger.debug("Test Failed !!!!!, the objects are not equal", e);
            throw e;
        }
    }

    @Test
    public void getRdmAllAnalysisMultiRegionPerils() {
        Mockito.when(rmsService.getRdmAllAnalysisMultiRegionPerils(832, "TC2016_MM_R", Arrays.asList(15, 123))).thenReturn(Arrays.asList(rdmAllAnalysisMultiRegionPerils));
        List<RdmAllAnalysisMultiRegionPerils> rdmAllAnalysisMultiRegionPerilss = (List<RdmAllAnalysisMultiRegionPerils>) rmsRessource.getRdmAllAnalysisMultiRegionPerils(832, "TC2016_MM_R", Arrays.asList(15, 123)).getBody();
        Assert.assertNotNull(rdmAllAnalysisMultiRegionPerilss.get(0));
        try {
            Assert.assertEquals(rdmAllAnalysisMultiRegionPerilss.get(0), rdmAllAnalysisMultiRegionPerils);
            this.logger.debug("Test has been passed successfully,The objects are equal");
        } catch (AssertionError e) {
            this.logger.debug("Test Failed !!!!!, the objects are not equal", e);
            throw e;
        }
    }

    @Test
    public void getAnalysisModellingOptionSettings() {
        Mockito.when(rmsService.getAnalysisModellingOptionSettings(50, "AC15_RL15_AUT_R", 1)).thenReturn(resultTest);
        String analysisModellingOptionSettings = rmsRessource.getAnalysisModellingOptionSettings(50, "AC15_RL15_AUT_R", 1);
        Assert.assertNotNull(analysisModellingOptionSettings);
        try {
            Assert.assertEquals(analysisModellingOptionSettings, resultTest);
            this.logger.debug("Test has been passed successfully,The objects are equal");
        } catch (AssertionError e) {
            this.logger.debug("Test Failed !!!!!, the objects are not equal", e);
            throw e;
        }
    }
}

