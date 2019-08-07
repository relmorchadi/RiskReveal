package com.scor.rr.rest;


import com.scor.rr.service.FileBaseImportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    ResponseEntity<?> getFilesList(){
        return ResponseEntity.ok(
                this.fileBaseImportService.listFilesFromSandbox()
        ) ;
    }

    @GetMapping("folders-list")
    ResponseEntity<?> getFoldersList(){
        return ResponseEntity.ok(
                this.fileBaseImportService.listFoldersFromSandbox()
        ) ;
    }

    @GetMapping("read-file")
    ResponseEntity<?> readFile(String fileName) throws IOException {
        return ResponseEntity.ok(
                this.fileBaseImportService.readFile(fileName)
        ) ;
    }


}
