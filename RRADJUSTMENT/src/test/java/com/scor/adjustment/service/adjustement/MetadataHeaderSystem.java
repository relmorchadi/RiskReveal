package com.scor.adjustment.service.adjustement;

import com.scor.rr.RiskRevealApplication;
import com.scor.rr.configuration.file.LossDataFileUtils;
import com.scor.rr.domain.MetadataHeaderSectionEntity;
import com.scor.rr.domain.MetadataHeaderSegmentEntity;
import com.scor.rr.repository.ImportedFileRepository;
import com.scor.rr.repository.MetadataHeaderSectionRepository;
import com.scor.rr.repository.MetadataHeaderSegmentRepository;
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
public class MetadataHeaderSystem {

    @Autowired
    MetadataHeaderSectionRepository metadataHeader;

    @Autowired
    MetadataHeaderSegmentRepository headerSegment;

    @Autowired
    ImportedFileRepository importedFileRepository;

    List<MetadataHeaderSectionEntity> metadataHeaders;
    List<MetadataHeaderSegmentEntity> metadataSemgments;


    @Before
    public void setUp() {
        metadataHeaders = metadataHeader.findAll();
        metadataSemgments = headerSegment.findAll();

    }

    @After
    public void tearDown() {

    }

    @Test
    public void VerifyFileValid() {
        Assert.assertTrue(LossDataFileUtils.verifyFile(metadataHeaders,metadataSemgments,"src\\main\\resources\\file\\pltValid.txt",importedFileRepository));
    }

    @Test
    public void VerifyFileMandatoryNotExist() {
        Assert.assertFalse(LossDataFileUtils.verifyFile(metadataHeaders,metadataSemgments,"src\\main\\resources\\file\\pltLineMandatoryNotExist.txt",importedFileRepository));
    }

    @Test
    public void VerifyFileDuplicateLine() {
        Assert.assertFalse(LossDataFileUtils.verifyFile(metadataHeaders,metadataSemgments,"src\\main\\resources\\file\\pltDuplicateLine.txt",importedFileRepository));
    }

    @Test
    public void VerifyFileFormatDateNotValid() {
        Assert.assertFalse(LossDataFileUtils.verifyFile(metadataHeaders,metadataSemgments,"src\\main\\resources\\file\\pltFormatDateNotValid.txt",importedFileRepository));
    }

    @Test
    public void VerifyFileMissingHeader() {
        Assert.assertFalse(LossDataFileUtils.verifyFile(metadataHeaders,metadataSemgments,"src\\main\\resources\\file\\pltHeaderMissing.txt",importedFileRepository));
    }



}
