package com.scor.adjustment.service.adjustement;

import com.scor.rr.RiskRevealApplication;
import com.scor.rr.domain.AdjustmentNodeEntity;
import com.scor.rr.domain.dto.adjustement.AdjustmentNodeRequest;
import com.scor.rr.service.adjustement.AdjustmentNodeService;
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
public class NodeTest {
    @Autowired
    AdjustmentNodeService adjustmentNodeService;

    @Before
    public void setUp() throws Exception {


    }

    @After
    public void tearDown() {

    }

    @Test
    public void createNode() {
        AdjustmentNodeRequest adjustmentNodeRequest = new AdjustmentNodeRequest("",1,
                false,"",
                false,1,
                4,1,41,1.7,1.1,null,983,2,null);
        AdjustmentNodeEntity adjustmentNodeEntity = adjustmentNodeService.save(adjustmentNodeRequest);
        Assert.assertEquals(adjustmentNodeEntity,adjustmentNodeService.findOne(adjustmentNodeEntity.getAdjustmentNodeId()));
    }

    //TODO: check the consistency between adjustment type and adjustment parameters
    // - if mandatory parameter for given adjustment type is missing --> error
    // - if there are another parameters along with the mandatory parameter for a given adjustment type --> ignore the redundant ones, a warning is needed

}
