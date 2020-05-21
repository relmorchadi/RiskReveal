package com.scor.rr.rest;


import com.scor.rr.domain.DataSource;
import com.scor.rr.domain.dto.*;
import com.scor.rr.domain.enums.ModelDataSourceType;
import com.scor.rr.service.RmsService;
import com.scor.rr.service.abstraction.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    public ResponseEntity<?> basicScan(@RequestBody List<DataSource> dataSources, @RequestParam Long projectId) {
        return ResponseEntity.ok(
                rmsService.basicScan(dataSources, projectId)
        );
    }

    @PostMapping("single-basic-scan")
    public ResponseEntity<?> singleBasicScan(@RequestBody DataSource dataSources, @RequestParam Long projectId) {
        return ResponseEntity.ok(
                rmsService.singleBasicScan(dataSources, projectId)
        );
    }

    @PostMapping("detailed-scan")
    public ResponseEntity<?> analysisDetailScan(@RequestBody DetailedScanDto detailedScanDto) {
        return new ResponseEntity<>(rmsService.paralleledDetailedScan(detailedScanDto), HttpStatus.OK);
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

    @GetMapping(value = "filter-riskLink-analysis")
    public ResponseEntity<?> filterRiskLinkAnalysis(@RequestParam String instanceId, @RequestParam Long projectId, @RequestParam Long userId, @RequestParam Long rdmId, @PageableDefault(size = 20) Pageable pageable, RLAnalysisDto filter, @RequestParam(defaultValue = "false") Boolean withPagination) {
        if (withPagination)
            return ResponseEntity.ok(configurationService.filterRLAnalysisByRLModelDataSourceId(instanceId, projectId, userId, rdmId, filter, pageable));
        else
            return ResponseEntity.ok(configurationService.filterRLAnalysisByRLModelDataSourceId(instanceId, projectId, userId, rdmId, filter));
    }

    @GetMapping(value = "filter-riskLink-portfolio")
    public ResponseEntity<?> filterRiskLinkPortfolio(@RequestParam String instanceId, @RequestParam Long projectId, @RequestParam Long userId, @RequestParam Long edmId, @PageableDefault(size = 20) Pageable pageable, RLPortfolioDto filter, @RequestParam(defaultValue = "false") Boolean withPagination) {
        if (withPagination)
            return ResponseEntity.ok(configurationService.filterRLPortfolioByRLModelDataSourceId(instanceId, projectId, userId, edmId, filter, pageable));
        else
            return ResponseEntity.ok(configurationService.filterRLPortfolioByRLModelDataSourceId(instanceId, projectId, userId, edmId, filter));
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
    public ResponseEntity<?> getDefaultDataSources() {
        try {
            return new ResponseEntity<>(configurationService.getDefaultDataSources(), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>("An error has occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "saved-data-source")
    public ResponseEntity<?> deleteSavedDataSource(@RequestParam Long savedDataSourceId) {
        try {
            configurationService.deleteSavedSataSourceById(savedDataSourceId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>("An error has occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "check-if-project-has-been-imported")
    public ResponseEntity<?> checkIfProjectHasBeenImported(@RequestParam Long projectId) {
        try {
            return new ResponseEntity<>(configurationService.checkIfProjectHasBeenImportedBefore(projectId), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Operation Failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "get-imported-data-sources")
    public ResponseEntity<?> getImportedDataSources(@RequestParam Long projectId) {
        try {
            return new ResponseEntity<>(configurationService.getDataSourcesWithSelectedAnalysis(projectId), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Operation Failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "get-imported-analysis-configuration")
    public ResponseEntity<?> getImportedAnalysisConfiguration(@RequestParam Long projectId) {
        try {
            return new ResponseEntity<>(configurationService.getRLModelAnalysisConfigs(projectId), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Operation Failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "get-imported-portfolio-configuration")
    public ResponseEntity<?> getImportedPortfolioConfiguration(@RequestParam Long projectId) {
        try {
            return new ResponseEntity<>(configurationService.getRLPortfolioConfigs(projectId), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Operation Failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "get-global-data-sources")
    public ResponseEntity<?> getGlobalDataSources(@RequestParam Long projectId) {
        try {
            if (configurationService.checkIfProjectHasScannedDataSources(projectId))
                return new ResponseEntity<>(new GlobalDataSourceDto(true, configurationService.getDataSourcesWithSelectedAnalysis(projectId)), HttpStatus.OK);
            else
                return new ResponseEntity<>(new GlobalDataSourceDto(false, configurationService.getDefaultDataSources()), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Operation Failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("AutoAttach")
    public ResponseEntity<?> autoAttachWs(@RequestParam String wsId, @RequestParam List<Long> rdmIds, @RequestParam List<Long> edmIds, @RequestParam List<Long> divisionsIds) {
        return ResponseEntity.ok(configurationService.getAutoAttach(wsId, edmIds, rdmIds, divisionsIds));
    }

    @DeleteMapping("data-source")
    public ResponseEntity<?> deleteDataSources(@RequestParam Long rlModelDataSourceId) {
        configurationService.deleteRlDataSource(rlModelDataSourceId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "delete-datasources-by-project-id")
    public ResponseEntity<?> clear(@RequestParam Long projectId) {
        try {
            configurationService.clearProjectAndLoadDefaultDataSources(projectId);
            return new ResponseEntity<>("Operation succeeded", HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Operation Failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "delete-analysis-summary")
    public ResponseEntity<?> deleteAnalysisSummary(@RequestParam List<Long> rlAnalysisId) {
        try {
            configurationService.deleteAnalysisSummary(rlAnalysisId);
            return new ResponseEntity<>("Operation succeeded", HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>("Operation Failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "delete-portfolio-summary")
    public ResponseEntity<?> deletePortfolioSummary(@RequestParam List<Long> rlPortfolioId) {
        try {
            configurationService.deletePortfolioSummary(rlPortfolioId);
            return new ResponseEntity<>("Operation succeeded", HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>("Operation Failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
