package com.scor.adjustment.service.adjustement;

import com.scor.rr.RiskRevealApplication;
import com.scor.rr.domain.AdjustmentNode;
import com.scor.rr.domain.AdjustmentThread;
import com.scor.rr.domain.dto.adjustement.AdjustmentNodeRequest;
import com.scor.rr.domain.dto.adjustement.AdjustmentThreadCreationRequest;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.service.adjustement.AdjustmentNodeService;
import com.scor.rr.service.adjustement.AdjustmentThreadService;
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
public class ThreadTest {

    @Autowired
    AdjustmentThreadService adjustmentThreadService;

    @Autowired
    AdjustmentNodeService adjustmentNodeService;

    @Before
    public void setUp() throws Exception {


    }

    @After
    public void tearDown() {

    }

    //NOTE: only tests for empty threads (i.e no nodes) ?

    @Test
    public void createPurePltThread() throws RRException {
        AdjustmentThread threadEntity = adjustmentThreadService.createNewAdjustmentThread(new AdjustmentThreadCreationRequest(983L,
                "",
                false));
        Assert.assertEquals(threadEntity,adjustmentThreadService.findOne(threadEntity.getAdjustmentThreadId()));
        AdjustmentNode nodeEntity1 = adjustmentNodeService.createAdjustmentNode(new AdjustmentNodeRequest(1, false,
                1,
                4,threadEntity.getAdjustmentThreadId(),1.7,1.1,null,null));
        AdjustmentNode nodeEntity2 = adjustmentNodeService.createAdjustmentNode(new AdjustmentNodeRequest(2, false,
                1,
                4, threadEntity.getAdjustmentThreadId(),1.7,1.1,null,null));
        List<AdjustmentNode> nodeEntities = adjustmentNodeService.findByThread(threadEntity.getAdjustmentThreadId());
        Assert.assertEquals(2,nodeEntities.size());
        Assert.assertEquals(nodeEntities.get(0),nodeEntity1);
        Assert.assertEquals(nodeEntities.get(1),nodeEntity2);
    }
}
