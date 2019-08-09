package com.scor.rr.rest;


import com.scor.rr.service.FileBaseImportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("api/filebaseimport")
public class FileBaseImportResource {

    private FileBaseImportService fileBaseImportService;

    public FileBaseImportResource(FileBaseImportService fileBaseImportService) {
        this.fileBaseImportService = fileBaseImportService;
    }


    @GetMapping("files-list")
    ResponseEntity<?> getFilesList(@RequestParam String path){
        return ResponseEntity.ok(
                this.fileBaseImportService.listFilesFromSandbox(path)
        ) ;
    }

    @GetMapping("folders-list")
    ResponseEntity<?> getFoldersList(@RequestParam String path){
        return ResponseEntity.ok(
                this.fileBaseImportService.listFoldersFromSandbox(path)
        ) ;
    }

    @GetMapping("read-file")
    ResponseEntity<?> readFile(String fileName) throws IOException {
        return ResponseEntity.ok(
                this.fileBaseImportService.readFile(fileName)
        ) ;
    }


}
