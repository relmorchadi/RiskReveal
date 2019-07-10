package com.scor.adjustment.service.adjustement;

import com.scor.rr.domain.dto.adjustement.loss.PLTLossData;
import com.scor.rr.service.adjustement.pltAdjustment.CalculAdjustement;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CalculAdjustementEEFFrequencyTest {
    private static final Logger log = LoggerFactory.getLogger(CalculAdjustementEEFFrequencyTest.class);
    private List<PLTLossData> pltLossDataList;
    private double rpmf;
    private boolean cap;
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
        cap = true;
        rpmf =0.4;
        calculAdjustement = new CalculAdjustement();
    }

    @After
    public void tearDown() {
        log.info("END test for EEF Frequency Adjustment");
    }
    @Test
    public void eefFrequencyNegativeRpmf() {
        log.info("Launch test for EEF Frequency Adjustment for negative rpmf = {}",rpmf);
        assertNull(calculAdjustement.eefFrequency(pltLossDataList,cap,-2,1000000));
    }
    @Test
    public void eefFrequencyNullPlt() {
        log.info("Launch test for EEF Frequency Adjustment for null plt ");
        assertNull(calculAdjustement.eefFrequency(null,cap,rpmf,1000000));
    }
    @Test
    public void eefFrequencyEmptyPlt() {
        log.info("Launch test for EEF Frequency Adjustment for an empty plt ");
        assertNull(calculAdjustement.eefFrequency(new ArrayList<>(),cap,rpmf,1000000));
    }
}