package com.scor.rr.rest;

import com.scor.rr.domain.BulkImportFile;
import com.scor.rr.service.bulkImport.abstraction.BulkImportService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/bulk-import")
public class BulkImportResource {

    @Autowired
    private BulkImportService bulkImportService;

    @PostMapping(value = "/upload-and-validate")
    @ApiOperation(value = "upload and validate an excel file to update a reference table")
    public ResponseEntity<?> uploadAndValidate(@RequestParam(value = "payload") MultipartFile payload) {
        try {
            BulkImportFile bulkImportFile = bulkImportService.uploadFile(payload);
            return new ResponseEntity<>(bulkImportService.validateFile(bulkImportFile), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("upload has failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/import")
    @ApiOperation(value = "Launch bulk import for a file")
    public ResponseEntity<?> importFile(@RequestParam(value = "id") Long id) {
        try {
            bulkImportService.importFile(id);
            return new ResponseEntity<>("Operation done", HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Operation failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/history")
    @ApiOperation(value = "bulk import history")
    public ResponseEntity<?> getImportHistory(@RequestParam(value = "userId") Long userId, int page, int records) {
        try {
            return new ResponseEntity<>(bulkImportService.getImportHistory(page, records, userId), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Operation failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
