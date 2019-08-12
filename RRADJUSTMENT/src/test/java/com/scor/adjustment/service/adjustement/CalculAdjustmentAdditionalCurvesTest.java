package com.scor.adjustment.service.adjustement;

import com.scor.rr.configuration.file.CSVPLTFileReader;
import com.scor.rr.domain.dto.adjustement.loss.PLTLossData;
import com.scor.rr.exceptions.fileExceptionPlt.RRException;
import com.scor.rr.service.adjustement.pltAdjustment.CalculAdjustement;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CalculAdjustmentAdditionalCurvesTest {
    private static final Logger log = LoggerFactory.getLogger(CalculAdjustmentAdditionalCurvesTest.class);
    private List<PLTLossData> pltLossDataList;
    CalculAdjustement calculAdjustement;
    @Before
    public void setUp() throws Exception {
        log.info("Launch test for EEF Frequency Adjustment");
        pltLossDataList = new ArrayList<PLTLossData>(){{
            add(new PLTLossData(36,8443694,(double)41334,1,(double)3957801220L,(double)14240566));
            add(new PLTLossData(68,8441785,(double)41542,1,(double)476993400L, 3455929.25));
            add(new PLTLossData(74,8440073,(double)41512,1,(double)75025550L, 211684.75));
            add(new PLTLossData(94,8440368,(double)41414,1,(double)292462560L, 112700.48));
            add(new PLTLossData(117,8309116,(double)41292,1,(double)116508000000L, 12625397800L));
        }};
        calculAdjustement = new CalculAdjustement();
    }

    @After
    public void tearDown() {
        log.info("END test for EEF Frequency Adjustment");
    }


    @Test
    public void AverageAnnualLossTest() throws RRException {
        CSVPLTFileReader csvpltFileReader = new CSVPLTFileReader();
        log.info("Launch test for Average Annual Loss with a plt file ");
        List<PLTLossData> pltLossData = csvpltFileReader.read(new File("src/main/resources/file/PLT Adjustment Test PLT (Pure).csv"));
        log.info("Average Annual Loss with a plt file = {}",calculAdjustement.averageAnnualLoss(pltLossData));
        log.info("Average Annual Loss with a plt null = {}",calculAdjustement.averageAnnualLoss(null));
        log.info("Average Annual Loss with empty plt = {}",calculAdjustement.averageAnnualLoss(new ArrayList<>()));

    }

    @Test
    public void AEPTVaRMetricsTest() throws RRException {
        CSVPLTFileReader csvpltFileReader = new CSVPLTFileReader();
        log.info("Launch test for Average Annual Loss with a plt file ");
        List<PLTLossData> pltLossData = csvpltFileReader.read(new File("src/main/resources/file/PLT Adjustment Test PLT (Pure).csv"));
        log.info("OEP-TVaR with a plt file = {}",calculAdjustement.AEPTVaRMetrics(calculAdjustement.getAEPMetric(pltLossData)));
        log.info("OEP-TVaR with a plt null = {}",calculAdjustement.AEPTVaRMetrics(null));
        log.info("OEP-TVaR with empty plt = {}",calculAdjustement.AEPTVaRMetrics(new ArrayList<>()));
    }

    @Test
    public void OEPTVaRMetricsTest() throws RRException {
        CSVPLTFileReader csvpltFileReader = new CSVPLTFileReader();
        log.info("Launch test for Average Annual Loss with a plt file ");
        List<PLTLossData> pltLossData = csvpltFileReader.read(new File("src/main/resources/file/PLT Adjustment Test PLT (Pure).csv"));
        log.info("Average Annual Loss with a plt file = {}",calculAdjustement.OEPTVaRMetrics(calculAdjustement.getOEPMetric(pltLossData)));
        log.info("Average Annual Loss with a plt null = {}",calculAdjustement.OEPTVaRMetrics(null));
        log.info("Average Annual Loss with empty plt = {}",calculAdjustement.OEPTVaRMetrics(new ArrayList<>()));
    }
    @Test
    public void stdDevTest() throws RRException {
        CSVPLTFileReader csvpltFileReader = new CSVPLTFileReader();
        log.info("Launch test for Average Annual Loss with a plt file ");
        List<PLTLossData> pltLossData = csvpltFileReader.read(new File("src/main/resources/file/PLT Adjustment Test PLT (Pure).csv"));
        log.info("Standard Deviation with a plt file = {}",calculAdjustement.stdDev(pltLossData));
        log.info("Standard Deviation with a plt null = {}",calculAdjustement.stdDev(null));
        log.info("Standard Deviation with empty plt = {}",calculAdjustement.stdDev(new ArrayList<>()));
    }
}