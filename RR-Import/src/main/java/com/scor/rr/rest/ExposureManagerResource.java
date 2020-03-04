package com.scor.rr.rest;

import com.scor.rr.domain.dto.ExposureManagerParamsDto;
import com.scor.rr.service.abstraction.ExposureManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/exposure-manager")
@Slf4j
public class ExposureManagerResource {

    @Autowired
    private ExposureManagerService exposureManagerService;

    @GetMapping("references")
    public ResponseEntity<?> getReferencesForProject(@RequestParam Long projectId) {
        try {
            return new ResponseEntity<>(exposureManagerService.getRefForExposureManager(projectId), HttpStatus.OK);
        } catch (Exception ex) {
            log.error("an error has occurred for projectId {} while extracting exposure manager's refs", projectId);
            ex.printStackTrace();
            return new ResponseEntity<>("Operation failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("exposure-manager-data")
    public ResponseEntity<?> getData(@RequestBody ExposureManagerParamsDto params) {
        try {
            return new ResponseEntity<>(exposureManagerService.getExposureManagerData(params), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>("Operation failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
