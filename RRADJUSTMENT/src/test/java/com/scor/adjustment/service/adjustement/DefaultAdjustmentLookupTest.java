package com.scor.adjustment.service.adjustement;

import com.scor.rr.RiskRevealApplication;
import com.scor.rr.service.adjustement.DefaultAdjustmentService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {RiskRevealApplication.class})
@SpringBootTest
@Transactional
public class DefaultAdjustmentLookupTest {

    @Autowired
    DefaultAdjustmentService defaultAdjustmentService;
    int scorPltHeaderInput;

    @Before
    public void setUp() throws Exception {


    }

    @After
    public void tearDown() {

    }

    //TODO: expected results

    @Test
    public void lookupForDefaultAdjustmentWithInputPLT() {
        defaultAdjustmentService.getDefaultAdjustmentNodeByPurePltRPAndTRAndETAndMC(983);
        defaultAdjustmentService.getDefaultAdjustmentNodeByPurePltRPAndTRAndETAndMC(1);
    }

}
