package com.scor.adjustment.service.adjustement;

import com.scor.rr.service.adjustement.DefaultAdjustmentService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class DefaultAdjustmentLookupTest {

    @Autowired
    DefaultAdjustmentService defaultAdjustmentService;
    int scorPltHeaderInput;

    @Before
    public void setUp() throws Exception {


    }

    @After
    public void tearDown() {

    }

    @Test
    public void lookupForDefaultAdjustmentWithInputPLT() {

    }

}
