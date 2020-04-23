package com.scor.adjustment.service.adjustement;

import com.scor.rr.CalibrationApplication;
import com.scor.rr.domain.AdjustmentNode;
import com.scor.rr.domain.AdjustmentNodeOrder;
import com.scor.rr.domain.AdjustmentThread;
import com.scor.rr.domain.dto.adjustement.AdjustmentNodeRequest;
import com.scor.rr.domain.dto.adjustement.AdjustmentNodeUpdateRequest;
import com.scor.rr.domain.dto.adjustement.AdjustmentThreadCreationRequest;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.service.adjustement.AdjustmentNodeOrderService;
import com.scor.rr.service.adjustement.AdjustmentNodeService;
import com.scor.rr.service.adjustement.AdjustmentThreadService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {CalibrationApplication.class})
@SpringBootTest
@Transactional
@PropertySource({"classpath:application.properties"})
public class NodeTest {

    private static final Logger log = LoggerFactory.getLogger(NodeTest.class);

    @Autowired
    AdjustmentNodeService adjustmentNodeService;

    @Autowired
    AdjustmentThreadService adjustmentThreadService;

    @Autowired
    AdjustmentNodeOrderService adjustmentNodeOrderService;

    AdjustmentThread threadEntityForUpdateOrder;

    AdjustmentNode nodeEntity1;

    AdjustmentNode nodeEntity2;

    AdjustmentNode nodeEntity3;

    AdjustmentNode nodeEntity4;
    
    AdjustmentThread threadTest;
    
    AdjustmentNode nodeTest;
    



    @Before
    public void setUp() throws RRException {
        threadTest = adjustmentThreadService.createNewAdjustmentThread(new AdjustmentThreadCreationRequest(983L,
                "",
                false));

        nodeTest = adjustmentNodeService.createAdjustmentNode(new AdjustmentNodeRequest(1, false,
                1,
                4, threadTest.getAdjustmentThreadId(),1.7,1.1,null,null));

        threadEntityForUpdateOrder = adjustmentThreadService.createNewAdjustmentThread(new AdjustmentThreadCreationRequest(983L,
                "",
                false));

        nodeEntity1 = adjustmentNodeService.createAdjustmentNode(new AdjustmentNodeRequest(1, false,
                1,
                4,threadEntityForUpdateOrder.getAdjustmentThreadId(),1.7,1.1,null,null));

        nodeEntity2 = adjustmentNodeService.createAdjustmentNode(new AdjustmentNodeRequest(2, false,
                1,
                4,threadEntityForUpdateOrder.getAdjustmentThreadId(),1.7,1.1,null,null));

        nodeEntity3 = adjustmentNodeService.createAdjustmentNode(new AdjustmentNodeRequest(3, false,
                1,
                4,threadEntityForUpdateOrder.getAdjustmentThreadId(),1.7,1.1,null,null));

        nodeEntity4 = adjustmentNodeService.createAdjustmentNode(new AdjustmentNodeRequest(4, false,
                1,
                4,threadEntityForUpdateOrder.getAdjustmentThreadId(),1.7,1.1,null,null));


    }

    @After
    public void tearDown() {

    }

    // check the consistency between adjustment type and adjustment parameters
    // - if mandatory parameter for given adjustment type is missing --> error
    // - if there are another parameters along with the mandatory parameter for a given adjustment type --> ignore the redundant ones, a warning is needed
    // DONE Please Check

    @Test
    public void missingParam() throws RRException {
        //Linear with lmf null
        AdjustmentNodeRequest adjustmentNodeRequestLinear = new AdjustmentNodeRequest(1, false,
                1,
                1,threadTest.getAdjustmentThreadId(),null,1.1,null,null);
        adjustmentNodeService.createAdjustmentNode(adjustmentNodeRequestLinear);
        //return period banding with parameter null
        AdjustmentNodeRequest adjustmentNodeRequestReturnPeriodBanding = new AdjustmentNodeRequest(1, false,
                1,
                2,threadTest.getAdjustmentThreadId(),null,1.1,null,null);
        adjustmentNodeService.createAdjustmentNode(adjustmentNodeRequestReturnPeriodBanding);
    }

    @Test
    public void createNode() throws RRException {
        AdjustmentNodeRequest adjustmentNodeRequest = new AdjustmentNodeRequest(1, false,
                1,
                4,threadTest.getAdjustmentThreadId(),1.7,1.1,null,null);
        AdjustmentNode adjustmentNodeEntity = adjustmentNodeService.createAdjustmentNode(adjustmentNodeRequest);
        Assert.assertEquals(adjustmentNodeEntity,adjustmentNodeService.findOne(adjustmentNodeEntity.getAdjustmentNodeId()));
    }

    @Test
    public void updateOrderNode14() throws RRException {
        AdjustmentNodeUpdateRequest adjustmentNodeRequest14 = new AdjustmentNodeUpdateRequest(nodeEntity1.getAdjustmentNodeId(),  4, false,
                1,
                4, 1, threadEntityForUpdateOrder.getAdjustmentThreadId(), 1.7, 1.1, null,  null);
        adjustmentNodeService.updateAdjustmentNode(adjustmentNodeRequest14);
        AdjustmentNodeOrder orderentity1 = adjustmentNodeOrderService.getAdjustmentOrderByThreadIdAndNodeId(threadEntityForUpdateOrder.getAdjustmentThreadId(),nodeEntity1.getAdjustmentNodeId());
        AdjustmentNodeOrder orderentity2 = adjustmentNodeOrderService.getAdjustmentOrderByThreadIdAndNodeId(threadEntityForUpdateOrder.getAdjustmentThreadId(),nodeEntity2.getAdjustmentNodeId());
        AdjustmentNodeOrder orderentity3 = adjustmentNodeOrderService.getAdjustmentOrderByThreadIdAndNodeId(threadEntityForUpdateOrder.getAdjustmentThreadId(),nodeEntity3.getAdjustmentNodeId());
        AdjustmentNodeOrder orderentity4 = adjustmentNodeOrderService.getAdjustmentOrderByThreadIdAndNodeId(threadEntityForUpdateOrder.getAdjustmentThreadId(),nodeEntity4.getAdjustmentNodeId());
        Assert.assertTrue(orderentity1.getAdjustmentOrder() == 4);
        Assert.assertTrue(orderentity2.getAdjustmentOrder() == 1);
        Assert.assertTrue(orderentity3.getAdjustmentOrder() == 2);
        Assert.assertTrue(orderentity4.getAdjustmentOrder() == 3);
    }
    @Test
    public void updateOrderNode41() throws RRException {
        AdjustmentNodeUpdateRequest adjustmentNodeRequest41 = new AdjustmentNodeUpdateRequest(nodeEntity4.getAdjustmentNodeId(),  1, false,
                1,
                4, 1, threadEntityForUpdateOrder.getAdjustmentThreadId(), 1.7, 1.1, null, null);
        adjustmentNodeService.updateAdjustmentNode(adjustmentNodeRequest41);
        log.info("adjustment order 1,2,3,4 -> 2,3,4,1 ");
        AdjustmentNodeOrder orderentity1 = adjustmentNodeOrderService.getAdjustmentOrderByThreadIdAndNodeId(threadEntityForUpdateOrder.getAdjustmentThreadId(),nodeEntity1.getAdjustmentNodeId());
        AdjustmentNodeOrder orderentity2 = adjustmentNodeOrderService.getAdjustmentOrderByThreadIdAndNodeId(threadEntityForUpdateOrder.getAdjustmentThreadId(),nodeEntity2.getAdjustmentNodeId());
        AdjustmentNodeOrder orderentity3 = adjustmentNodeOrderService.getAdjustmentOrderByThreadIdAndNodeId(threadEntityForUpdateOrder.getAdjustmentThreadId(),nodeEntity3.getAdjustmentNodeId());
        AdjustmentNodeOrder orderentity4 = adjustmentNodeOrderService.getAdjustmentOrderByThreadIdAndNodeId(threadEntityForUpdateOrder.getAdjustmentThreadId(),nodeEntity4.getAdjustmentNodeId());
        Assert.assertTrue(orderentity1.getAdjustmentOrder() == 2);
        Assert.assertTrue(orderentity2.getAdjustmentOrder() == 3);
        Assert.assertTrue(orderentity3.getAdjustmentOrder() == 4);
        Assert.assertTrue(orderentity4.getAdjustmentOrder() == 1);
        }
    @Test
    public void updateOrderNode32() throws RRException {
        AdjustmentNodeUpdateRequest adjustmentNodeRequest32 = new AdjustmentNodeUpdateRequest(nodeEntity3.getAdjustmentNodeId(),  2, false,
                1,
                4, 1, threadEntityForUpdateOrder.getAdjustmentThreadId(), 1.7, 1.1, null, null);
        adjustmentNodeService.updateAdjustmentNode(adjustmentNodeRequest32);
        log.info("adjustment order 1,2,3,4 -> 1,3,2,4 ");
        AdjustmentNodeOrder orderentity1 = adjustmentNodeOrderService.getAdjustmentOrderByThreadIdAndNodeId(threadEntityForUpdateOrder.getAdjustmentThreadId(),nodeEntity1.getAdjustmentNodeId());
        AdjustmentNodeOrder orderentity2 = adjustmentNodeOrderService.getAdjustmentOrderByThreadIdAndNodeId(threadEntityForUpdateOrder.getAdjustmentThreadId(),nodeEntity2.getAdjustmentNodeId());
        AdjustmentNodeOrder orderentity3 = adjustmentNodeOrderService.getAdjustmentOrderByThreadIdAndNodeId(threadEntityForUpdateOrder.getAdjustmentThreadId(),nodeEntity3.getAdjustmentNodeId());
        AdjustmentNodeOrder orderentity4 = adjustmentNodeOrderService.getAdjustmentOrderByThreadIdAndNodeId(threadEntityForUpdateOrder.getAdjustmentThreadId(),nodeEntity4.getAdjustmentNodeId());
        Assert.assertTrue(orderentity1.getAdjustmentOrder() == 1);
        Assert.assertTrue(orderentity2.getAdjustmentOrder() == 3);
        Assert.assertTrue(orderentity3.getAdjustmentOrder() == 2);
        Assert.assertTrue(orderentity4.getAdjustmentOrder() == 4);
    }

    @Test
    public void updateOrderNode23() throws RRException {
        AdjustmentNodeUpdateRequest adjustmentNodeRequest23 = new AdjustmentNodeUpdateRequest(nodeEntity2.getAdjustmentNodeId(),3, false,
                1,
                4,1,threadEntityForUpdateOrder.getAdjustmentThreadId(),1.7,1.1,null,null);
        adjustmentNodeService.updateAdjustmentNode(adjustmentNodeRequest23);
        log.info("adjustment order 1,2,3,4 -> 1,3,2,4 ");
        AdjustmentNodeOrder orderentity1 = adjustmentNodeOrderService.getAdjustmentOrderByThreadIdAndNodeId(threadEntityForUpdateOrder.getAdjustmentThreadId(),nodeEntity1.getAdjustmentNodeId());
        AdjustmentNodeOrder orderentity2 = adjustmentNodeOrderService.getAdjustmentOrderByThreadIdAndNodeId(threadEntityForUpdateOrder.getAdjustmentThreadId(),nodeEntity2.getAdjustmentNodeId());
        AdjustmentNodeOrder orderentity3 = adjustmentNodeOrderService.getAdjustmentOrderByThreadIdAndNodeId(threadEntityForUpdateOrder.getAdjustmentThreadId(),nodeEntity3.getAdjustmentNodeId());
        AdjustmentNodeOrder orderentity4 = adjustmentNodeOrderService.getAdjustmentOrderByThreadIdAndNodeId(threadEntityForUpdateOrder.getAdjustmentThreadId(),nodeEntity4.getAdjustmentNodeId());
        Assert.assertTrue(orderentity1.getAdjustmentOrder() == 1);
        Assert.assertTrue(orderentity2.getAdjustmentOrder() == 3);
        Assert.assertTrue(orderentity3.getAdjustmentOrder() == 2);
        Assert.assertTrue(orderentity4.getAdjustmentOrder() == 4);
    }


    @Test
    public void deleteNode() {
        adjustmentNodeService.deleteNode(nodeEntity2.getAdjustmentNodeId());
        AdjustmentNodeOrder orderentity1 = adjustmentNodeOrderService.getAdjustmentOrderByThreadIdAndNodeId(threadEntityForUpdateOrder.getAdjustmentThreadId(),nodeEntity1.getAdjustmentNodeId());
        AdjustmentNodeOrder orderentity3 = adjustmentNodeOrderService.getAdjustmentOrderByThreadIdAndNodeId(threadEntityForUpdateOrder.getAdjustmentThreadId(),nodeEntity3.getAdjustmentNodeId());
        AdjustmentNodeOrder orderentity4 = adjustmentNodeOrderService.getAdjustmentOrderByThreadIdAndNodeId(threadEntityForUpdateOrder.getAdjustmentThreadId(),nodeEntity4.getAdjustmentNodeId());
        Assert.assertTrue(orderentity1.getAdjustmentOrder() == 1);
        Assert.assertTrue(orderentity3.getAdjustmentOrder() == 2);
        Assert.assertTrue(orderentity4.getAdjustmentOrder() == 3);
    }
}
