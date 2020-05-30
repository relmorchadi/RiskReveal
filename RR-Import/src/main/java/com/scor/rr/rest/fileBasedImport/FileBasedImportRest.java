package com.scor.rr.rest.fileBasedImport;

import com.scor.rr.domain.importfile.FileBasedImportConfig;
import com.scor.rr.domain.importfile.FileImportSourceResult;
import com.scor.rr.service.fileBasedImport.ImportFileService;
import com.scor.rr.domain.importfile.FileBasedImportConfigRequest;
import com.scor.rr.exceptions.RRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/ihub")
public class FileBasedImportRest {

    @Autowired
    ImportFileService importFileService;

//    @GetMapping("copy-file")
//    public void copyFileToiHub(String path) throws IOException {
//        importFileService.copyFileToiHub(path);
//    }
//
//    @GetMapping("plt-from-file")
//    public List<ImportFilePLTData> getPltFromLossDataFile(String path) {
//        return importFileService.getPltFromLossDataFile(path);
//    }
//
//    @GetMapping("verify-file")
//    public boolean verifyFilePlt(String path, String peqtPath) {
//        return importFileService.verifyFilePlt(path, peqtPath);
//    }

    @GetMapping("read-metadata")
    public Map<String, String> readMetadata(String path) {
        return importFileService.readMetadata(path);
    }

    @GetMapping("read-PLTdata")
    public Map<String, String> readPLTdata(String path) {
        return importFileService.readPLTdata(path);
    }

    @GetMapping("validate-metadata")
    public boolean validateMetadata(String path) {
        return importFileService.validateMetadata(path);
    }

    @GetMapping("validate-PLTdata")
    public boolean validatePLTdata(String path, String peqtPath) {
        return importFileService.validatePLTdata(path, peqtPath);
    }

    @GetMapping("directoryListing")
    public String directoryListing() {
        return importFileService.directoryListing();
    }

    @GetMapping("retrieveTextFiles")
    public List<Map<String,String>> retrieveTextFiles(String path) {
        return importFileService.retrieveTextFiles(path);
    }

    @PostMapping("updateFileBasedConfig")
    public ResponseEntity<?> updateFileBasedConfig(@RequestBody FileBasedImportConfigRequest request) throws RRException {
        importFileService.updateFileBasedConfig(request);
        return ResponseEntity.ok("Updated Successfully");
    }

    @PostMapping("persisteFileBasedImportConfig")
    public List<FileImportSourceResult> persisteFileBasedImportConfig(@RequestBody FileBasedImportConfigRequest request, String folderPath)  {
        return importFileService.persisteFileBasedImportConfig(request,folderPath);
    }

    @GetMapping("retrieveFileBasedConfig")
    public String retrieveFileBasedConfig(String projectId) {
        return importFileService.retrieveFileBasedConfig(projectId);
    }

    @GetMapping("launchFileBasedImport")
    public Long launchFileBasedImport(String instanceId,
                                            Long nonrmspicId,
                                            Long fileBasedImportConfigId,
                                            String userId,
                                            Long projectId,
                                            String fileImportSourceResultIds) {
        return importFileService.launchFileBasedImport(instanceId, nonrmspicId, fileBasedImportConfigId, userId, projectId, fileImportSourceResultIds);
    }



}
