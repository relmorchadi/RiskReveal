import com.scor.rr.configuration.fileBasedImport.FileBasedImportJob;
import com.scor.rr.service.fileBasedImport.ImportFileService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class FileBasedImportTest {

    @InjectMocks
    private ImportFileService importFileService;

    @Mock
    private JobLauncher jobLauncher;

    @SpyBean
    private FileBasedImportJob fileBasedImport;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

//    @Test
//    public void launchFileBasedImport() {
//        String instanceId = "";
//        String nonrmspicId = "";
//        String userId = "";
//        String projectId = "";
//        String fileImportSourceResultIds = "";
//        importFileService.launchFileBasedImport(instanceId, nonrmspicId, userId, projectId, fileImportSourceResultIds);
//    }
}
