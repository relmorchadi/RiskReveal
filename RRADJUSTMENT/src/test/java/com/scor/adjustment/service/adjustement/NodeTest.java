package com.scor.adjustment.service.adjustement;

import com.scor.rr.RiskRevealApplication;
import com.scor.rr.domain.AdjustmentNodeEntity;
import com.scor.rr.domain.AdjustmentNodeOrderEntity;
import com.scor.rr.domain.AdjustmentThreadEntity;
import com.scor.rr.domain.dto.adjustement.AdjustmentNodeRequest;
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
@ContextConfiguration(classes = {RiskRevealApplication.class})
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

    AdjustmentThreadEntity threadEntityForUpdateOrder;

    AdjustmentNodeEntity nodeEntity1;

    AdjustmentNodeEntity nodeEntity2;

    AdjustmentNodeEntity nodeEntity3;

    AdjustmentNodeEntity nodeEntity4;
    
    AdjustmentThreadEntity threadTest;
    
    AdjustmentNodeEntity nodeTest;
    



    @Before
    public void setUp() throws RRException {
        threadTest = adjustmentThreadService.createNewAdjustmentThread(new AdjustmentThreadCreationRequest("",
                983,
                "",
                false));

        nodeTest = adjustmentNodeService.save(new AdjustmentNodeRequest("",1, false,
                1,
                4,1,threadTest.getAdjustmentThreadId(),1.7,1.1,null,983,null));

        threadEntityForUpdateOrder = adjustmentThreadService.createNewAdjustmentThread(new AdjustmentThreadCreationRequest("",
                983,
                "",
                false));

        nodeEntity1 = adjustmentNodeService.save(new AdjustmentNodeRequest("",1, false,
                1,
                4,1,threadEntityForUpdateOrder.getAdjustmentThreadId(),1.7,1.1,null,983,null));

        nodeEntity2 = adjustmentNodeService.save(new AdjustmentNodeRequest("",2, false,
                1,
                4,1,threadEntityForUpdateOrder.getAdjustmentThreadId(),1.7,1.1,null,983,null));

        nodeEntity3 = adjustmentNodeService.save(new AdjustmentNodeRequest("",3, false,
                1,
                4,1,threadEntityForUpdateOrder.getAdjustmentThreadId(),1.7,1.1,null,983,null));

        nodeEntity4 = adjustmentNodeService.save(new AdjustmentNodeRequest("",4, false,
                1,
                4,1,threadEntityForUpdateOrder.getAdjustmentThreadId(),1.7,1.1,null,983,null));


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
        AdjustmentNodeRequest adjustmentNodeRequestLinear = new AdjustmentNodeRequest("",1, false,
                1,
                1,1,threadTest.getAdjustmentThreadId(),null,1.1,null,983,null);
        adjustmentNodeService.save(adjustmentNodeRequestLinear);
        //return period banding with parameter null
        AdjustmentNodeRequest adjustmentNodeRequestReturnPeriodBanding = new AdjustmentNodeRequest("",1, false,
                1,
                2,1,threadTest.getAdjustmentThreadId(),null,1.1,null,983,null);
        adjustmentNodeService.save(adjustmentNodeRequestReturnPeriodBanding);
    }

    @Test
    public void createNode() throws RRException {
        AdjustmentNodeRequest adjustmentNodeRequest = new AdjustmentNodeRequest("",1, false,
                1,
                4,1,threadTest.getAdjustmentThreadId(),1.7,1.1,null,983,null);
        AdjustmentNodeEntity adjustmentNodeEntity = adjustmentNodeService.save(adjustmentNodeRequest);
        Assert.assertEquals(adjustmentNodeEntity,adjustmentNodeService.findOne(adjustmentNodeEntity.getAdjustmentNodeId()));
    }

    @Test
    public void updateOrderNode14() throws RRException {
        AdjustmentNodeRequest adjustmentNodeRequest14 = new AdjustmentNodeRequest(nodeEntity1.getAdjustmentNodeId(), "", 4, false,
                1,
                4, 1, threadEntityForUpdateOrder.getAdjustmentThreadId(), 1.7, 1.1, null, 983, null);
        adjustmentNodeService.save(adjustmentNodeRequest14);
        AdjustmentNodeOrderEntity orderentity1 = adjustmentNodeOrderService.getAdjustmentOrderByThreadIdAndNodeId(threadEntityForUpdateOrder.getAdjustmentThreadId(),nodeEntity1.getAdjustmentNodeId());
        AdjustmentNodeOrderEntity orderentity2 = adjustmentNodeOrderService.getAdjustmentOrderByThreadIdAndNodeId(threadEntityForUpdateOrder.getAdjustmentThreadId(),nodeEntity2.getAdjustmentNodeId());
        AdjustmentNodeOrderEntity orderentity3 = adjustmentNodeOrderService.getAdjustmentOrderByThreadIdAndNodeId(threadEntityForUpdateOrder.getAdjustmentThreadId(),nodeEntity3.getAdjustmentNodeId());
        AdjustmentNodeOrderEntity orderentity4 = adjustmentNodeOrderService.getAdjustmentOrderByThreadIdAndNodeId(threadEntityForUpdateOrder.getAdjustmentThreadId(),nodeEntity4.getAdjustmentNodeId());
        Assert.assertTrue(orderentity1.getOrderNode()==4);
        Assert.assertTrue(orderentity2.getOrderNode()==1);
        Assert.assertTrue(orderentity3.getOrderNode()==2);
        Assert.assertTrue(orderentity4.getOrderNode()==3);
    }
    @Test
    public void updateOrderNode41() throws RRException {
        AdjustmentNodeRequest adjustmentNodeRequest41 = new AdjustmentNodeRequest(nodeEntity4.getAdjustmentNodeId(), "", 1, false,
                1,
                4, 1, threadEntityForUpdateOrder.getAdjustmentThreadId(), 1.7, 1.1, null, 983, null);
        adjustmentNodeService.save(adjustmentNodeRequest41);
        log.info("adjustment order 1,2,3,4 -> 2,3,4,1 ");
        AdjustmentNodeOrderEntity orderentity1 = adjustmentNodeOrderService.getAdjustmentOrderByThreadIdAndNodeId(threadEntityForUpdateOrder.getAdjustmentThreadId(),nodeEntity1.getAdjustmentNodeId());
        AdjustmentNodeOrderEntity orderentity2 = adjustmentNodeOrderService.getAdjustmentOrderByThreadIdAndNodeId(threadEntityForUpdateOrder.getAdjustmentThreadId(),nodeEntity2.getAdjustmentNodeId());
        AdjustmentNodeOrderEntity orderentity3 = adjustmentNodeOrderService.getAdjustmentOrderByThreadIdAndNodeId(threadEntityForUpdateOrder.getAdjustmentThreadId(),nodeEntity3.getAdjustmentNodeId());
        AdjustmentNodeOrderEntity orderentity4 = adjustmentNodeOrderService.getAdjustmentOrderByThreadIdAndNodeId(threadEntityForUpdateOrder.getAdjustmentThreadId(),nodeEntity4.getAdjustmentNodeId());
        Assert.assertTrue(orderentity1.getOrderNode()==2);
        Assert.assertTrue(orderentity2.getOrderNode()==3);
        Assert.assertTrue(orderentity3.getOrderNode()==4);
        Assert.assertTrue(orderentity4.getOrderNode()==1);
        }
    @Test
    public void updateOrderNode32() throws RRException {
        AdjustmentNodeRequest adjustmentNodeRequest32 = new AdjustmentNodeRequest(nodeEntity3.getAdjustmentNodeId(), "", 2, false,
                1,
                4, 1, threadEntityForUpdateOrder.getAdjustmentThreadId(), 1.7, 1.1, null, 983, null);
        adjustmentNodeService.save(adjustmentNodeRequest32);
        log.info("adjustment order 1,2,3,4 -> 1,3,2,4 ");
        AdjustmentNodeOrderEntity orderentity1 = adjustmentNodeOrderService.getAdjustmentOrderByThreadIdAndNodeId(threadEntityForUpdateOrder.getAdjustmentThreadId(),nodeEntity1.getAdjustmentNodeId());
        AdjustmentNodeOrderEntity orderentity2 = adjustmentNodeOrderService.getAdjustmentOrderByThreadIdAndNodeId(threadEntityForUpdateOrder.getAdjustmentThreadId(),nodeEntity2.getAdjustmentNodeId());
        AdjustmentNodeOrderEntity orderentity3 = adjustmentNodeOrderService.getAdjustmentOrderByThreadIdAndNodeId(threadEntityForUpdateOrder.getAdjustmentThreadId(),nodeEntity3.getAdjustmentNodeId());
        AdjustmentNodeOrderEntity orderentity4 = adjustmentNodeOrderService.getAdjustmentOrderByThreadIdAndNodeId(threadEntityForUpdateOrder.getAdjustmentThreadId(),nodeEntity4.getAdjustmentNodeId());
        Assert.assertTrue(orderentity1.getOrderNode()==1);
        Assert.assertTrue(orderentity2.getOrderNode()==3);
        Assert.assertTrue(orderentity3.getOrderNode()==2);
        Assert.assertTrue(orderentity4.getOrderNode()==4);
    }
    @Test
    public void updateOrderNode23() throws RRException {
        AdjustmentNodeRequest adjustmentNodeRequest23 = new AdjustmentNodeRequest(nodeEntity2.getAdjustmentNodeId(),"",3, false,
                1,
                4,1,threadEntityForUpdateOrder.getAdjustmentThreadId(),1.7,1.1,null,983,null);
        adjustmentNodeService.save(adjustmentNodeRequest23);
        log.info("adjustment order 1,2,3,4 -> 1,3,2,4 ");
        AdjustmentNodeOrderEntity orderentity1 = adjustmentNodeOrderService.getAdjustmentOrderByThreadIdAndNodeId(threadEntityForUpdateOrder.getAdjustmentThreadId(),nodeEntity1.getAdjustmentNodeId());
        AdjustmentNodeOrderEntity orderentity2 = adjustmentNodeOrderService.getAdjustmentOrderByThreadIdAndNodeId(threadEntityForUpdateOrder.getAdjustmentThreadId(),nodeEntity2.getAdjustmentNodeId());
        AdjustmentNodeOrderEntity orderentity3 = adjustmentNodeOrderService.getAdjustmentOrderByThreadIdAndNodeId(threadEntityForUpdateOrder.getAdjustmentThreadId(),nodeEntity3.getAdjustmentNodeId());
        AdjustmentNodeOrderEntity orderentity4 = adjustmentNodeOrderService.getAdjustmentOrderByThreadIdAndNodeId(threadEntityForUpdateOrder.getAdjustmentThreadId(),nodeEntity4.getAdjustmentNodeId());
        Assert.assertTrue(orderentity1.getOrderNode()==1);
        Assert.assertTrue(orderentity2.getOrderNode()==3);
        Assert.assertTrue(orderentity3.getOrderNode()==2);
        Assert.assertTrue(orderentity4.getOrderNode()==4);
    }


    @Test
    public void deleteNode() {
        adjustmentNodeService.deleteNode(nodeEntity2.getAdjustmentNodeId());
        AdjustmentNodeOrderEntity orderentity1 = adjustmentNodeOrderService.getAdjustmentOrderByThreadIdAndNodeId(threadEntityForUpdateOrder.getAdjustmentThreadId(),nodeEntity1.getAdjustmentNodeId());
        AdjustmentNodeOrderEntity orderentity3 = adjustmentNodeOrderService.getAdjustmentOrderByThreadIdAndNodeId(threadEntityForUpdateOrder.getAdjustmentThreadId(),nodeEntity3.getAdjustmentNodeId());
        AdjustmentNodeOrderEntity orderentity4 = adjustmentNodeOrderService.getAdjustmentOrderByThreadIdAndNodeId(threadEntityForUpdateOrder.getAdjustmentThreadId(),nodeEntity4.getAdjustmentNodeId());
        Assert.assertTrue(orderentity1.getOrderNode()==1);
        Assert.assertTrue(orderentity3.getOrderNode()==2);
        Assert.assertTrue(orderentity4.getOrderNode()==3);
    }
}
