package com.scor.adjustment.service.adjustement;

import com.scor.rr.RiskRevealApplication;
import com.scor.rr.domain.AdjustmentNodeProcessingEntity;
import com.scor.rr.domain.dto.adjustement.AdjustmentNodeProcessingRequest;
import com.scor.rr.domain.dto.adjustement.AdjustmentParameterRequest;
import com.scor.rr.domain.dto.adjustement.loss.AdjustmentReturnPeriodBending;
import com.scor.rr.domain.dto.adjustement.loss.PEATData;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.service.adjustement.AdjustmentNodeProcessingService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
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
@ContextConfiguration(classes = {RiskRevealApplication.class})
@SpringBootTest
@Transactional
@PropertySource({"classpath:application.properties"})
public class AdjustmentNodeProcessingTest {

    @Autowired
    AdjustmentNodeProcessingService adjustmentNodeProcessingService;
    private List<AdjustmentReturnPeriodBending> adjustmentReturnPeriodBandings;
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

        adjustmentReturnPeriodBandings = new ArrayList<AdjustmentReturnPeriodBending>(){{
            add(new AdjustmentReturnPeriodBending(500,0.87));
            add(new AdjustmentReturnPeriodBending(750,0.9));
            add(new AdjustmentReturnPeriodBending(10000,0.93));
            add(new AdjustmentReturnPeriodBending(20000,0.97));

        }};

        lmf = 1.7;
        rpmf = 2;
    }

    @After
    public void tearDown() {

    }

    //TODO: test edge cases: Node not found, PLT not found, PLT could not be read, calculation failed ...
    //TODO: define expected results

    @Test
    public void processNodeByInput() {
        AdjustmentNodeProcessingEntity processingEntity = adjustmentNodeProcessingService.saveByInputPlt(new AdjustmentNodeProcessingRequest(983,2));
        Assert.assertEquals(processingEntity,adjustmentNodeProcessingService.findOne(processingEntity.getAdjustmentNodeProcessingId()));
    }
    @Test
    public void processNodeByAdjusted() throws RRException {
        AdjustmentNodeProcessingEntity processingEntity = adjustmentNodeProcessingService.saveByAdjustedPlt(new AdjustmentParameterRequest(lmf,rpmf,adjustmentReturnPeriod,983,2,adjustmentReturnPeriodBandings));
        Assert.assertEquals(processingEntity,adjustmentNodeProcessingService.findOne(processingEntity.getAdjustmentNodeProcessingId()));
    }
}
