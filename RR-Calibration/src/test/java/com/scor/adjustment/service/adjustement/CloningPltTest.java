package com.scor.adjustment.service.adjustement;

import com.scor.rr.RiskRevealApplication;
import com.scor.rr.domain.AdjustmentThreadEntity;
import com.scor.rr.domain.PltHeaderEntity;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.service.adjustement.*;
import com.scor.rr.service.cloning.CloningScorPltHeader;
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

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {RiskRevealApplication.class})
@SpringBootTest
@Transactional
@PropertySource({"classpath:application.properties"})
public class CloningPltTest {

    @Autowired
    CloningScorPltHeader cloningScorPltHeader;

    @Autowired
    ScorPltHeaderService scorPltHeaderService;

    @Autowired
    AdjustmentThreadService threadService;

    @Autowired
    AdjustmentNodeService nodeService;

    @Autowired
    AdjustmentNodeOrderService adjustmentNodeOrderService;

    @Autowired
    AdjustmentNodeProcessingService processingService;

    @Before
    public void setUp() {


    }

    @After
    public void tearDown() {

    }

    @Test
    public void clonePltWithAdjustment() throws RRException {
        PltHeaderEntity scorPltHeaderCloned = cloningScorPltHeader.clonePltWithAdjustment(435L,"123");
        Assert.assertEquals(scorPltHeaderCloned.getCloningSource().getPltHeaderId(), (Object) 435L);
        Assert.assertEquals(scorPltHeaderCloned.getTargetRap(),scorPltHeaderCloned.getCloningSource().getTargetRap());
        Assert.assertNotEquals(scorPltHeaderCloned.getBinFileEntity().getPath(),null);
        Assert.assertNotEquals(scorPltHeaderCloned.getBinFileEntity().getFqn(),null);
        AdjustmentThreadEntity threadInitial = threadService.getByPltHeader(435L);
        AdjustmentThreadEntity threadCloned = threadService.getByPltHeader(scorPltHeaderCloned.getPltHeaderId());
//        if(threadInitial != null) {
//            List<AdjustmentNodeEntity> nodeEntitiesInitial = nodeService.findByThread(threadInitial.getAdjustmentThreadId());
//            List<AdjustmentNodeEntity> nodeEntitiesCloned = nodeService.findByThread(threadCloned.getAdjustmentThreadId());
//            Assert.assertEquals(nodeEntitiesInitial.size(),nodeEntitiesCloned.size());
//            for(int j=0;j<nodeEntitiesCloned.size();j++){
//                Assert.assertEquals(nodeEntitiesCloned.get(j).getAdjustmentNodeByFkAdjustmentNodeIdCloning(),nodeEntitiesInitial.get(j));
//            }
//        }

    }
}
