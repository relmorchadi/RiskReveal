package com.scor.rr.rest.importprocess;

import com.scor.rr.domain.dto.ImportFilePLTData;
import com.scor.rr.service.importprocess.ImportFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/ihub")
public class ImportiHubRest {

    @Autowired
    ImportFileService importFileService;

    @GetMapping("copy-file")
    public void copyFileToiHub(String path) throws IOException {
        importFileService.copyFileToiHub(path);
    }

    @GetMapping("plt-from-file")
    public List<ImportFilePLTData> getPltFromLossDataFile(String path) {
        return importFileService.getPltFromLossDataFile(path);
    }

    @GetMapping("verify-file")
    public boolean verifyFilePlt(String path, String peqtPath) {
        return importFileService.verifyFilePlt(path, peqtPath);
    }

}
