package com.scor.adjustment.service.adjustement;

import com.scor.rr.RiskRevealApplication;
import com.scor.rr.domain.AdjustmentThreadEntity;
import com.scor.rr.domain.dto.adjustement.AdjustmentThreadRequest;
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

import java.sql.Timestamp;
import java.util.Date;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {RiskRevealApplication.class})
@SpringBootTest
@Transactional
@PropertySource({"classpath:application.properties"})
public class ThreadTest {

    @Autowired
    AdjustmentThreadService adjustmentThreadService;

    @Before
    public void setUp() throws Exception {


    }

    @After
    public void tearDown() {

    }

    //NOTE: only tests for empty threads (i.e no nodes) ?

    @Test
    public void createPurePltThread() {
        AdjustmentThreadEntity threadEntity = adjustmentThreadService.savePurePlt(new AdjustmentThreadRequest("",true,
                983,0,
                "",new Timestamp(new Date().getTime()),
                "",new Timestamp(new Date().getTime()),
                "",new Timestamp(new Date().getTime()),
                new Timestamp(new Date().getTime()),null));
        Assert.assertEquals(threadEntity,adjustmentThreadService.findOne(threadEntity.getAdjustmentThreadId()));
    }
    @Test
    public void createThreadThreadPlt() {
        AdjustmentThreadEntity threadEntity = adjustmentThreadService.saveAdjustedPlt(new AdjustmentThreadRequest(1,982,"HAMZA",new Timestamp(new Date().getTime())));
        Assert.assertEquals(threadEntity,adjustmentThreadService.findOne(threadEntity.getAdjustmentThreadId()));
    }
}
