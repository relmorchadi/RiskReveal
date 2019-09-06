package com.scor.adjustment.service.adjustement;

import com.scor.rr.RiskRevealApplication;
import com.scor.rr.domain.AdjustmentNodeEntity;
import com.scor.rr.exceptions.RRException;
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

    @Test
    public void lookupForDefaultAdjustmentWithInputPLT() throws RRException {
        List<AdjustmentNodeEntity> nodeEntities = defaultAdjustmentService.getDefaultAdjustmentNodeByPurePltRPAndTRAndETAndMC(983);
        if(nodeEntities != null) {
            for (AdjustmentNodeEntity nodeEntity : nodeEntities) {
                Assert.assertEquals(nodeEntity, adjustmentNodeService.findOne(nodeEntity.getAdjustmentNodeId()));
                Assert.assertNotEquals(nodeEntity.getAdjustmentThread().getScorPltHeaderByFkScorPltHeaderThreadId(),null);
                Assert.assertNotEquals(nodeEntity.getAdjustmentThread().getScorPltHeaderByFkScorPltHeaderThreadPureId(),null);
                Assert.assertNotEquals(nodeEntity.getAdjustmentThread().getScorPltHeaderByFkScorPltHeaderThreadPureId().getBinFileEntity(),null);
                Assert.assertNotEquals(nodeEntity.getAdjustmentThread().getScorPltHeaderByFkScorPltHeaderThreadId().getBinFileEntity(),null);
                //Assert.assertEquals(nodeEntity.getAdjustmentNode().getAdjustmentBasis().getAdjustmentCategoryByFkCategoryId(),1);
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
