package com.scor.adjustment.service.adjustement;

import com.scor.rr.RiskRevealApplication;
import com.scor.rr.domain.ScorPltHeaderEntity;
import com.scor.rr.repository.ScorpltheaderRepository;
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

    @Before
    public void setUp() {


    }

    @After
    public void tearDown() {

    }

    //TODO: expected results

    @Test
    public void lookupForDefaultAdjustmentWithInputPLT() {
        ScorPltHeaderEntity scorPltHeaderEntity = cloningScorPltHeader.cloneScorPltHeader(983);
        Assert.assertEquals(scorPltHeaderEntity,scorpltheaderRepository.getOne(scorPltHeaderEntity.getPkScorPltHeaderId()));
    }
}
