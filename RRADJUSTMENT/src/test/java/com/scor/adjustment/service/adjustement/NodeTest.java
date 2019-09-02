package com.scor.adjustment.service.adjustement;

import com.scor.rr.RiskRevealApplication;
import com.scor.rr.domain.AdjustmentNodeEntity;
import com.scor.rr.domain.dto.adjustement.AdjustmentNodeRequest;
import com.scor.rr.service.adjustement.AdjustmentNodeOrderService;
import com.scor.rr.service.adjustement.AdjustmentNodeService;
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
    AdjustmentNodeOrderService adjustmentNodeOrderService;


    @Before
    public void setUp() {


    }

    @After
    public void tearDown() {

    }

    //TODO: expected result ? exception if creation failed (wrong parameter for example)
    @Test
    public void createNode() {
        AdjustmentNodeRequest adjustmentNodeRequest = new AdjustmentNodeRequest("",1,
                false,"",
                false,1,
                4,1,41,1.7,1.1,null,983,2,null);
        AdjustmentNodeEntity adjustmentNodeEntity = adjustmentNodeService.save(adjustmentNodeRequest);
        Assert.assertEquals(adjustmentNodeEntity,adjustmentNodeService.findOne(adjustmentNodeEntity.getAdjustmentNodeId()));
    }
    @Test
    public void updateOrderNode() {
        AdjustmentNodeRequest adjustmentNodeRequest14 = new AdjustmentNodeRequest(39,"",4,
                false,"",
                false,1,
                4,1,41,1.7,1.1,null,983,2,null);
        adjustmentNodeService.save(adjustmentNodeRequest14);
        log.info("adjustment order 1,2,3,4 -> 4,1,2,3 {}",adjustmentNodeOrderService.findAll());
        AdjustmentNodeRequest adjustmentNodeRequest41 = new AdjustmentNodeRequest(61,"",1,
                false,"",
                false,1,
                4,1,41,1.7,1.1,null,983,2,null);
        adjustmentNodeService.save(adjustmentNodeRequest41);
        log.info("adjustment order 1,2,3,4 -> 4,1,2,3 {}",adjustmentNodeOrderService.findAll());
        AdjustmentNodeRequest adjustmentNodeRequest32 = new AdjustmentNodeRequest(60,"",2,
                false,"",
                false,1,
                4,1,41,1.7,1.1,null,983,2,null);
        adjustmentNodeService.save(adjustmentNodeRequest32);
        log.info("adjustment order 1,2,3,4 -> 1,3,2,4 {}",adjustmentNodeOrderService.findAll());
        AdjustmentNodeRequest adjustmentNodeRequest23 = new AdjustmentNodeRequest(59,"",3,
                false,"",
                false,1,
                4,1,41,1.7,1.1,null,983,2,null);
        adjustmentNodeService.save(adjustmentNodeRequest23);
        log.info("adjustment order 1,2,3,4 -> 1,3,2,4 {}",adjustmentNodeOrderService.findAll());
    }

    @Test
    public void deleteNode() {
        //adjustmentNodeService.deleteNode(39);
        adjustmentNodeService.deleteNode(59);
        //adjustmentNodeService.deleteNode(60);
        //adjustmentNodeService.deleteNode(61);
    }
}
