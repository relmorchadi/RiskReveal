package com.scor.adjustment.service.adjustement;

import com.scor.rr.RiskRevealApplication;
import com.scor.rr.domain.AdjustmentNodeEntity;
import com.scor.rr.service.adjustement.AdjustmentNodeService;
import com.scor.rr.service.adjustement.DefaultAdjustmentService;
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

import java.util.List;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {RiskRevealApplication.class})
@SpringBootTest
@Transactional
@PropertySource({"classpath:application.properties"})
public class DefaultAdjustmentLookupTest {

    @Autowired
    DefaultAdjustmentService defaultAdjustmentService;

    @Autowired
    AdjustmentNodeService adjustmentNodeService;

    @Before
    public void setUp() {


    }

    @After
    public void tearDown() {

    }

    //TODO: test case for two processes
    // 1. Given a pure PLT, perform a look up and return a list of required default adjustment that matched all criteria. Note: be careful for testing the edge cases for versioning
    // 2. Given a Default Adjustment, create correctly the default adjustment nodes.

    @Test
    public void lookupForDefaultAdjustmentWithInputPLT() {
        List<AdjustmentNodeEntity> nodeEntities = defaultAdjustmentService.getDefaultAdjustmentNodeByPurePltRPAndTRAndETAndMC(983);
        if(nodeEntities != null) {
            for (AdjustmentNodeEntity nodeEntity : nodeEntities) {
                Assert.assertEquals(nodeEntity, adjustmentNodeService.findOne(nodeEntity.getAdjustmentNodeId()));
            }
        }
        List<AdjustmentNodeEntity> nodeEntitie =defaultAdjustmentService.getDefaultAdjustmentNodeByPurePltRPAndTRAndETAndMC(1);
        if(nodeEntitie != null) {
            for (AdjustmentNodeEntity nodeEntity : nodeEntitie) {
                Assert.assertEquals(nodeEntity, adjustmentNodeService.findOne(nodeEntity.getAdjustmentNodeId()));

            }
        }
    }

}
