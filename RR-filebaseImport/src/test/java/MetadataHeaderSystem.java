
import com.scor.rr.ImportBackend;
import com.scor.rr.domain.importfile.FileBasedImportProducer;
import com.scor.rr.domain.importfile.MetadataHeaderSectionEntity;
import com.scor.rr.repository.ImportedFileRepository;
import com.scor.rr.repository.MetadataHeaderSectionRepository;
import com.scor.rr.repository.FileBasedImportProducerRepository;
import com.scor.rr.service.importprocess.ImportFileService;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ImportBackend.class})
@SpringBootTest
@Transactional
@PropertySource({"classpath:application.properties"})
public class MetadataHeaderSystem {

    @Autowired
    MetadataHeaderSectionRepository metadataHeader;

    @Autowired
    FileBasedImportProducerRepository headerSegment;

    @Autowired
    ImportedFileRepository importedFileRepository;

    @Autowired
    ImportFileService importFileService;

    List<MetadataHeaderSectionEntity> metadataHeaders;
    List<FileBasedImportProducer> metadataSegments;


    @Before
    public void setUp() {
        metadataHeaders = metadataHeader.findAll();
        metadataSegments = headerSegment.findAll();
    }

    @After
    public void tearDown() {

    }

//    @Test
//    public void VerifyFileValid() {
//        Assert.assertTrue(importFileService.verifyFile(metadataHeaders,metadataSegments,"src\\main\\resources\\file\\pltValid.txt", );
//    }
//
//    @Test
//    public void VerifyFileMandatoryNotExist() {
//        Assert.assertFalse(importFileService.verifyFile(metadataHeaders,metadataSegments,"src\\main\\resources\\file\\pltLineMandatoryNotExist.txt", );
//    }
//
//    @Test
//    public void VerifyFileDuplicateLine() {
//        Assert.assertFalse(importFileService.verifyFile(metadataHeaders,metadataSegments,"src\\main\\resources\\file\\pltDuplicateLine.txt", );
//    }
//
//    @Test
//    public void VerifyFileFormatDateNotValid() {
//        Assert.assertFalse(importFileService.verifyFile(metadataHeaders,metadataSegments,"src\\main\\resources\\file\\pltFormatDateNotValid.txt", );
//    }
//
//    @Test
//    public void VerifyFileMissingHeader() {
//        Assert.assertFalse(importFileService.verifyFile(metadataHeaders,metadataSegments,"src\\main\\resources\\file\\pltHeaderMissing.txt", );
//    }
}
