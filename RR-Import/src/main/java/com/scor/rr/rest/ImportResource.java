package com.scor.rr.rest;

import com.scor.rr.domain.dto.ImportLossDataParams;
import com.scor.rr.domain.dto.ImportReferenceData;
import com.scor.rr.service.RefDataService;
import com.scor.rr.service.batch.BatchExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/import")
public class ImportResource {


    @Autowired
    private BatchExecution batchExecution;

    @Autowired
    private RefDataService refDataService;

    @GetMapping("refs")
    public ResponseEntity<ImportReferenceData> getRefData(){
        return ResponseEntity.ok(
                refDataService.getImportRefs()
        );
    }


    @PostMapping("/rms")
    public ResponseEntity<?> doImport(@RequestBody ImportLossDataParams importLossDataParams){
        return new ResponseEntity<>(batchExecution.RunImportLossData(importLossDataParams), HttpStatus.OK);
    }

}
