package com.scor.adjustment.service.adjustement;

import com.scor.rr.configuration.file.CSVPLTFileReader;
import com.scor.rr.domain.dto.adjustement.loss.PLTLossData;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.service.adjustement.pltAdjustment.CalculateAdjustmentService;
import com.scor.rr.service.adjustement.pltAdjustment.StatisticAdjustment;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CalculateAdjustmentServiceAdditionalCurvesTest {
    private static final Logger log = LoggerFactory.getLogger(CalculateAdjustmentServiceAdditionalCurvesTest.class);
    private List<PLTLossData> pltLossDataList;

    @Before
    public void setUp() throws Exception {
        log.info("Launch test for EEF Frequency Adjustment");
        pltLossDataList = new ArrayList<PLTLossData>(){{
            add(new PLTLossData(36,8443694,41334,1,(double)3957801220L,(double)14240566));
            add(new PLTLossData(68,8441785,41542,1,(double)476993400L, 3455929.25));
            add(new PLTLossData(74,8440073,41512,1,(double)75025550L, 211684.75));
            add(new PLTLossData(94,8440368,41414,1,(double)292462560L, 112700.48));
            add(new PLTLossData(117,8309116,41292,1,(double)116508000000L, 12625397800L));
        }};
    }

    @After
    public void tearDown() {
        log.info("END test for EEF Frequency Adjustment");
    }

    //TODO: expected results for ALL tests. You could liaise with BA for the manual calculation or asking the result from RR 3.x

    @Test
    public void AverageAnnualLossTest() throws RRException {
        CSVPLTFileReader csvpltFileReader = new CSVPLTFileReader();
        log.info("Launch test for Average Annual Loss with a plt file ");
        List<PLTLossData> pltLossData = csvpltFileReader.read(new File("src/main/resources/file/PLT Adjustment Test PLT (Pure) 1.csv"));
        log.info("Average Annual Loss with a plt file = {}", StatisticAdjustment.averageAnnualLoss(pltLossData));
        log.info("Average Annual Loss with a plt null = {}",StatisticAdjustment.averageAnnualLoss(null));
        log.info("Average Annual Loss with empty plt = {}",StatisticAdjustment.averageAnnualLoss(new ArrayList<>()));

    }

    @Test
    public void AEPTVaRMetricsTest() throws RRException {
        CSVPLTFileReader csvpltFileReader = new CSVPLTFileReader();
        log.info("Launch test for Average Annual Loss with a plt file ");
        List<PLTLossData> pltLossData = csvpltFileReader.read(new File("src/main/resources/file/PLT Adjustment Test PLT (Pure) 1.csv"));
        log.info("OEP-TVaR with a plt file = {}",StatisticAdjustment.AEPTVaRMetrics(CalculateAdjustmentService.getAEPMetric(pltLossData).getEpMetricPoints()));
        log.info("OEP-TVaR with a plt null = {}",StatisticAdjustment.AEPTVaRMetrics(null));
        log.info("OEP-TVaR with empty plt = {}",StatisticAdjustment.AEPTVaRMetrics(new ArrayList<>()));
    }

    @Test
    public void OEPTVaRMetricsTest() throws RRException {
        CSVPLTFileReader csvpltFileReader = new CSVPLTFileReader();
        log.info("Launch test for Average Annual Loss with a plt file ");
        List<PLTLossData> pltLossData = csvpltFileReader.read(new File("src/main/resources/file/PLT Adjustment Test PLT (Pure) 1.csv"));
        log.info("Average Annual Loss with a plt file = {}",StatisticAdjustment.OEPTVaRMetrics(CalculateAdjustmentService.getOEPMetric(pltLossData).getEpMetricPoints()));
        log.info("Average Annual Loss with a plt null = {}",StatisticAdjustment.OEPTVaRMetrics(null));
        log.info("Average Annual Loss with empty plt = {}",StatisticAdjustment.OEPTVaRMetrics(new ArrayList<>()));
    }
    @Test
    public void stdDevTest() throws RRException {
        CSVPLTFileReader csvpltFileReader = new CSVPLTFileReader();
        log.info("Launch test for Average Annual Loss with a plt file ");
        List<PLTLossData> pltLossData = csvpltFileReader.read(new File("src/main/resources/file/PLT Adjustment Test PLT (Pure) 1.csv"));
        log.info("Standard Deviation with a plt file = {}",StatisticAdjustment.stdDev(pltLossData));
        log.info("Standard Deviation with a plt null = {}",StatisticAdjustment.stdDev(null));
        log.info("Standard Deviation with empty plt = {}",StatisticAdjustment.stdDev(new ArrayList<>()));
    }

    @Test
    public void CoefOfVarianceTest() throws RRException {
        CSVPLTFileReader csvpltFileReader = new CSVPLTFileReader();
        log.info("Launch test for Average Annual Loss with a plt file ");
        List<PLTLossData> pltLossData = csvpltFileReader.read(new File("src/main/resources/file/PLT Adjustment Test PLT (Pure) 1.csv"));
        log.info("Coefficient of Variance (CoV) with a plt file = {}",StatisticAdjustment.CoefOfVariance(pltLossData));
        log.info("Coefficient of Variance (CoV) with a plt null = {}",StatisticAdjustment.CoefOfVariance(null));
        log.info("Coefficient of Variance (CoV) with empty plt = {}",StatisticAdjustment.CoefOfVariance(new ArrayList<>()));
    }
}