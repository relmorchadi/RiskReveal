package com.scor.rr.rest.importprocess;

import com.scor.rr.domain.dto.ImportFilePLTData;
import com.scor.rr.service.importprocess.ImportiHubService;
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
    ImportiHubService importiHubService;

    @GetMapping("copy-file")
    public void copyFileToiHub(String path) throws IOException {
        importiHubService.copyFileToiHub(path);
    }

    @GetMapping("plt-from-file")
    public List<ImportFilePLTData> getPltFromLossDataFile(String path) {
        return importiHubService.getPltFromLossDataFile(path);
    }

    @GetMapping("verify-file")
    public boolean verifyFilePlt(String path) {
        return importiHubService.verifyFilePlt(path);
    }

}
