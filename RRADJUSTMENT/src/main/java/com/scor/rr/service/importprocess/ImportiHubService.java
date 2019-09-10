package com.scor.rr.service.importprocess;

import com.scor.rr.configuration.file.CopyFile;
import com.scor.rr.domain.dto.adjustement.loss.PLTLossData;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class ImportiHubService {

    private static final String PATH_IHUB = "RRADJUSTMENT/src/main/resources/copyfile/";

    public void copyFileToiHub(String path) throws IOException {
        File file = new File(path);
        CopyFile.copyFileFromPath(file,PATH_IHUB);
    }

    public PLTLossData getPltFromLossDataFile(String path) {
        return null;
    }
}
