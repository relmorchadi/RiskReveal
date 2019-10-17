package com.scor.adjustment.service.adjustement;

import com.scor.rr.RiskRevealApplication;
import com.scor.rr.domain.AdjustmentNodeEntity;
import com.scor.rr.domain.AdjustmentTypeEntity;
import com.scor.rr.domain.DefaultAdjustmentNodeEntity;
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
    public void lookupForDefaultAdjustment() throws RRException {
        List<DefaultAdjustmentNodeEntity> nodeEntities = defaultAdjustmentService.getDefaultAdjustmentNodeByPurePltRPAndTRAndETAndMC(
                470,
                1,
                1,
                "AGG",
                1);
        Assert.assertNotNull(nodeEntities);
        Assert.assertEquals(1, nodeEntities.size());
        assert(nodeEntities.get(0).getDefaultAdjustmentNodeId() == 1);
        assert(nodeEntities.get(0).getAdjustmentType().getAdjustmentTypeId() == 1);
        assert(nodeEntities.get(0).getAdjustmentBasis().getAdjustmentBasisId() == 28);
        Assert.assertEquals(1, nodeEntities.get(0).getDefaultAdjustmentNodeId());
    }

}
