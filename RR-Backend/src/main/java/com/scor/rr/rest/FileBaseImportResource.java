package com.scor.rr.rest;


import com.scor.rr.service.FileBaseImportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static java.util.Arrays.asList;

@RestController
@RequestMapping("api/filebaseimport")
public class FileBaseImportResource {

    private FileBaseImportService fileBaseImportService;

    public FileBaseImportResource(FileBaseImportService fileBaseImportService) {
        this.fileBaseImportService = fileBaseImportService;
    }


    @GetMapping("files-list")
    ResponseEntity<?> getFilesList(@RequestParam String path) {
        try {
            return ResponseEntity.ok(
                    this.fileBaseImportService.listFilesFromSandbox(path)
            );
        } catch (Exception e) {
            return ResponseEntity.ok(
                    asList(
                            "52_SAGI_XOL_2019 - SAGI_XOL_2019_EventSetId_7511_100k-1547193581-Original-20190111_155940.txt",
                            "6_IDFL_all_cresta - Analysis_EventSetId_7511_100k-1547173370-Original-20190111_102249.txt",
                            "5_IDFL_Cresta3 - Analysis_EventSetId_7511_100k-1547173150-Original-20190111_101910.txt",
                            "21_Baoviet_XOL - Analysis_EventSetId_7511_100k-1548815065-Original-20190130_102425.txt"
                    )
            );
        }
    }

    @GetMapping("folders-list")
    ResponseEntity<?> getFoldersList(@RequestParam String path) {
        return ResponseEntity.ok(
                this.fileBaseImportService.listFoldersFromSandbox(path)
        );
    }

    @GetMapping("read-file")
    ResponseEntity<?> readFile(String fileName) throws IOException {
        return ResponseEntity.ok(
                this.fileBaseImportService.readFile(fileName)
        );
    }


}
