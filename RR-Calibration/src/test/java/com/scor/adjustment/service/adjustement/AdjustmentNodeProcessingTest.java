package com.scor.adjustment.service.adjustement;

import com.scor.rr.CalibrationApplication;
import com.scor.rr.domain.ReturnPeriodBandingAdjustmentParameter;
import com.scor.rr.domain.dto.adjustement.loss.PEATData;
import com.scor.rr.service.adjustement.AdjustmentNodeProcessingService;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {CalibrationApplication.class})
@SpringBootTest
@Transactional
@PropertySource({"classpath:application.properties"})
public class AdjustmentNodeProcessingTest {

    @Autowired
    AdjustmentNodeProcessingService adjustmentNodeProcessingService;
    private List<ReturnPeriodBandingAdjustmentParameter> adjustmentReturnPeriodBandings;
    private List<PEATData> adjustmentReturnPeriod;
    private double lmf;
    private double rpmf;

    @Before
    public void setUp() {
        adjustmentReturnPeriod = new ArrayList<PEATData>(){{
            add(new PEATData(36,8443694,1,1.4));
            add(new PEATData(68,8441785,1,2.2));
            add(new PEATData(74,8440073,1,0.7));
            add(new PEATData(94,8443621,1,1.5));
        }};

        adjustmentReturnPeriodBandings = new ArrayList<ReturnPeriodBandingAdjustmentParameter>(){{
            add(new ReturnPeriodBandingAdjustmentParameter(500d,0.87));
            add(new ReturnPeriodBandingAdjustmentParameter(750d,0.9));
            add(new ReturnPeriodBandingAdjustmentParameter(10000d,0.93));
            add(new ReturnPeriodBandingAdjustmentParameter(20000d,0.97));

        }};

        lmf = 1.7;
        rpmf = 2;
    }

    @After
    public void tearDown() {

    }

    //TODO: test edge cases: Node not found, PLT not found, PLT could not be read, calculation failed ...
    //TODO: define expected results

//    @Test
//    public void processNodeByAdjusted() throws RRException {
//        AdjustmentNodeProcessingEntity processingEntity = adjustmentNodeProcessingService.saveByAdjustedPlt(new AdjustmentParameterRequest(lmf,rpmf,adjustmentReturnPeriod,983,2,adjustmentReturnPeriodBandings));
//        Assert.assertEquals(processingEntity,adjustmentNodeProcessingService.findOne(processingEntity.getAdjustmentNodeProcessingId()));
//    }
}
