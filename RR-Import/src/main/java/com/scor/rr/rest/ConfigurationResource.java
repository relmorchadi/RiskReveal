package com.scor.rr.rest;


import com.scor.rr.domain.DataSource;
import com.scor.rr.domain.dto.DataSourcesDto;
import com.scor.rr.domain.dto.DetailedScanDto;
import com.scor.rr.domain.dto.ImportSelectionDto;
import com.scor.rr.domain.dto.PortfolioSelectionDto;
import com.scor.rr.domain.enums.ModelDataSourceType;
import com.scor.rr.service.RmsService;
import com.scor.rr.service.abstraction.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/import/config")
public class ConfigurationResource {

    @Autowired
    RmsService rmsService;

    @Autowired
    private ConfigurationService configurationService;

    @PostMapping("basic-scan")
    public ResponseEntity<?> basicScan(@RequestBody List<DataSource> dataSources, @RequestParam Long projectId, @RequestParam String instanceId, @RequestParam String instanceName) {
        return ResponseEntity.ok(
                rmsService.basicScan(dataSources, projectId, instanceId, instanceName)
        );
    }

    @PostMapping("single-basic-scan")
    public ResponseEntity<?> singleBasicScan(@RequestBody DataSource dataSources, @RequestParam Long projectId, @RequestParam String instanceId, @RequestParam String instanceName) {
        return ResponseEntity.ok(
                rmsService.singleBasicScan(dataSources, projectId, instanceId, instanceName)
        );
    }

    @PostMapping("detailed-scan")
    public ResponseEntity<?> analysisDetailScan(@RequestBody DetailedScanDto detailedScanDto) {
        return new ResponseEntity<>(rmsService.detailedScan(detailedScanDto), HttpStatus.OK);
    }

    @PostMapping("save-analysis-import-selection")
    public ResponseEntity<?> saveSourceResults(@RequestBody List<ImportSelectionDto> sourceResultDtoList) {
        try {
            return new ResponseEntity<>(rmsService.saveAnalysisImportSelection(sourceResultDtoList), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>("Operation failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("save-portfolio-import-selection")
    public ResponseEntity<?> savePortfolioSelections(@RequestBody List<PortfolioSelectionDto> portfolioSelectionDtoList) {
        try {
            return new ResponseEntity<>(rmsService.savePortfolioImportSelection(portfolioSelectionDtoList), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>("Operation failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "get-riskLink-analysis-portfolios")
    public ResponseEntity<?> getRLAnalysisOrRLPortfolios(@RequestParam String instanceId,
                                                         @RequestParam Long projectId,
                                                         @RequestParam Long rmsId,
                                                         @RequestParam ModelDataSourceType type) {
        try {
            switch (type) {
                case EDM:
                    return new ResponseEntity<>(configurationService.getRLPortfolioByRLModelDataSourceId(instanceId, projectId, rmsId), HttpStatus.OK);
                case RDM:
                    return new ResponseEntity<>(configurationService.getRLAnalysisByRLModelDataSourceId(instanceId, projectId, rmsId), HttpStatus.OK);
                default:
                    return new ResponseEntity<>("Unknown type", HttpStatus.NOT_ACCEPTABLE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>("An error has Occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "get-source-ep-headers")
    public ResponseEntity<?> getSourceEpHeaders(@RequestParam Long rlAnalysisId) {
        try {
            return new ResponseEntity<>(configurationService.getSourceEpHeadersByAnalysis(rlAnalysisId), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>("An error has occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "get-target-raps-for-analysis")
    public ResponseEntity<?> getTargetRaps(@RequestParam Long rlAnalysisId) {
        try {
            return new ResponseEntity<>(configurationService.getTargetRapByAnalysisId(rlAnalysisId), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("An error has occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "get-region-peril-for-analysis")
    public ResponseEntity<?> getRegionPerils(@RequestParam Long rlAnalysisId) {
        try {
            return new ResponseEntity<>(configurationService.getRegionPeril(rlAnalysisId), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>("An error has occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "get-divisions-for-car")
    public ResponseEntity<?> getDivisions(@RequestParam String carId) {
        try {
            return new ResponseEntity<>(configurationService.getDivisions(carId), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>("An error has occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "get-region-peril-for-multi-analysis")
    public ResponseEntity<?> getRegionPerilsForMultiAnalysis(@RequestParam List<Long> rlAnalysisIds) {
        try {
            return new ResponseEntity<>(configurationService.getRegionPerilForMultiAnalysis(rlAnalysisIds), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>("An error has occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "save-default-data-sources")
    public ResponseEntity<?> saveDefaultDataSources(@RequestBody DataSourcesDto dataSourcesDto) {
        try {
            configurationService.saveDefaultDataSources(dataSourcesDto);
            return new ResponseEntity<>("Operation successful", HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>("An error has occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "get-default-data-sources")
    public ResponseEntity<?> getDefaultDataSources(@RequestParam Long projectId, @RequestParam Long userId, @RequestParam String instanceId) {
        try {
            return new ResponseEntity<>(configurationService.getDefaultDataSources(projectId, userId, instanceId), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>("An error has occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
