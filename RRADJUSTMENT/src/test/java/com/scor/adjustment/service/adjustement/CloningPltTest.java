package com.scor.adjustment.service.adjustement;

import com.scor.rr.RiskRevealApplication;
import com.scor.rr.domain.AdjustmentNodeEntity;
import com.scor.rr.domain.AdjustmentThreadEntity;
import com.scor.rr.domain.ScorPltHeaderEntity;
import com.scor.rr.repository.AdjustmentThreadRepository;
import com.scor.rr.repository.ScorpltheaderRepository;
import com.scor.rr.service.adjustement.AdjustmentNodeOrderService;
import com.scor.rr.service.adjustement.AdjustmentNodeProcessingService;
import com.scor.rr.service.adjustement.AdjustmentNodeService;
import com.scor.rr.service.adjustement.AdjustmentThreadService;
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

import java.util.List;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {RiskRevealApplication.class})
@SpringBootTest
@Transactional
@PropertySource({"classpath:application.properties"})
public class CloningPltTest {

    @Autowired
    CloningScorPltHeader cloningScorPltHeader;

    @Autowired
    ScorpltheaderRepository scorpltheaderRepository;

    @Autowired
    AdjustmentThreadService threadService;

    @Autowired
    AdjustmentNodeService nodeService;

    @Autowired
    AdjustmentThreadRepository adjustmentthreadRepository;

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
    public void lookupForDefaultAdjustmentWithInputPLT() {
        ScorPltHeaderEntity scorPltHeaderCloned = cloningScorPltHeader.clonePltWithAdjustment(scorpltheaderRepository.getOne(435));
        Assert.assertEquals(scorPltHeaderCloned.getScorPltHeader().getPkScorPltHeaderId(),435);
        AdjustmentThreadEntity threadInitial = threadService.getByScorPltHeader(435);
        AdjustmentThreadEntity threadCloned = threadService.getByScorPltHeader(scorPltHeaderCloned.getPkScorPltHeaderId());
        if(threadInitial != null) {
            List<AdjustmentNodeEntity> nodeEntitiesInitial = nodeService.findByThread(threadInitial.getAdjustmentThreadId());
            List<AdjustmentNodeEntity> nodeEntitiesCloned = nodeService.findByThread(threadCloned.getAdjustmentThreadId());
            Assert.assertEquals(nodeEntitiesInitial.size(),nodeEntitiesCloned.size());
            for(int j=0;j<nodeEntitiesCloned.size();j++){
                Assert.assertEquals(nodeEntitiesCloned.get(j).getAdjustmentNodeByFkAdjustmentNodeIdCloning(),nodeEntitiesInitial.get(j));
            }
        }

    }
}
