package com.scor.adjustment.service.adjustement;

import com.scor.rr.RiskRevealApplication;
import com.scor.rr.domain.dto.adjustement.AdjustmentThreadRequest;
import com.scor.rr.service.adjustement.AdjustmentNodeService;
import com.scor.rr.service.adjustement.AdjustmentThreadService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {RiskRevealApplication.class})
@SpringBootTest
@Transactional
public class ThreadTest {

    @Autowired
    AdjustmentThreadService adjustmentThreadService;

    @Before
    public void setUp() throws Exception {


    }

    @After
    public void tearDown() {

    }

    @Test
    public void createPurePltThread() {
        adjustmentThreadService.savePurePlt(new AdjustmentThreadRequest("",true,
                983,0,
                "",new Timestamp(new Date().getTime()),
                "",new Timestamp(new Date().getTime()),
                "",new Timestamp(new Date().getTime()),
                new Timestamp(new Date().getTime()),null));
    }
    @Test
    public void createThreadThreadPlt() {
        adjustmentThreadService.saveAdjustedPlt(new AdjustmentThreadRequest(1,982,"HAMZA",new Timestamp(new Date().getTime())));
    }
}
