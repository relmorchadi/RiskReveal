package com.scor.rr.rest;

import com.scor.rr.domain.dto.ExposureManagerParamsDto;
import com.scor.rr.service.RegionPerilService;
import com.scor.rr.service.abstraction.ExposureManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/exposure-manager")
@Slf4j
public class ExposureManagerResource {

    @Autowired
    private ExposureManagerService exposureManagerService;

    @Autowired
    private RegionPerilService regionPerilService;

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

    @PostMapping("exposure-manager-export-data")
    public ResponseEntity<?> exportData(@RequestBody ExposureManagerParamsDto params) {
        try {
            byte[] bytes = exposureManagerService.exportExposureManagerData(params);
            HttpHeaders header = new HttpHeaders();
            header.setContentType(new MediaType("application", "vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
            header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + exposureManagerService.exposureManagerDataExport(params));
            header.setContentLength(bytes.length);
            return new ResponseEntity<>(bytes, header, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>("Operation failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("region-peril-description")
    public ResponseEntity<?> getRegionPerilDescription(@RequestParam String regionPeril) {
        try {
            return new ResponseEntity<>(regionPerilService.getRegionPerilDescription(regionPeril), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>("Operation failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
