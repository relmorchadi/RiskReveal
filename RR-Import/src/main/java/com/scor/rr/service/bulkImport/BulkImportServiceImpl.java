package com.scor.rr.service.bulkImport;

import com.scor.rr.configuration.security.UserPrincipal;
import com.scor.rr.domain.*;
import com.scor.rr.domain.dto.*;
import com.scor.rr.domain.riskLink.RLAnalysis;
import com.scor.rr.domain.riskLink.RLModelDataSource;
import com.scor.rr.domain.riskLink.RLPortfolio;
import com.scor.rr.repository.*;
import com.scor.rr.service.RmsService;
import com.scor.rr.service.batch.BatchExecution;
import com.scor.rr.service.bulkImport.abstraction.BulkImportService;
import com.scor.rr.util.ExcelService;
import com.scor.rr.util.excel.FileHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.keyvalue.MultiKey;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BulkImportServiceImpl implements BulkImportService {


    @Autowired
    private ExcelService excelService;

    @Autowired
    private RmsService rmsService;

    @Autowired
    private BatchExecution batchExecution;

    @Autowired
    private BulkImportFileRepository bulkImportFileRepository;

    @Autowired
    private ValidationErrorRepository validationErrorRepository;

    @Autowired
    private BulkImportFileColumnsRepository bulkImportFileColumnsRepository;

    @Autowired
    private ModellingSystemInstanceRepository modellingSystemInstanceRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private RLModelDataSourceRepository rlModelDataSourceRepository;

    @Value(value = "${project.creation.service}")
    private String projectCalculationURL;

    @Override
    public BulkImportFile uploadFile(MultipartFile file) {
        Path path = excelService.saveToDisk(file);
        if (path != null) {
            Long userId = null;
            if (SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null)
                userId = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getUserId();
            BulkImportFile importedFile = new BulkImportFile(path.getParent().toString(), path.getFileName().toString(), false);
            importedFile.setUserId(userId);
            bulkImportFileRepository.save(importedFile);
            return importedFile;
        }
        return null;
    }

    @Override
    public ValidationDto validateFile(BulkImportFile file) {

        try {

            Path path = Paths.get(file.getFilePath() + File.separator + file.getFileName());
            FileHandler fileHandler = excelService.supplyFileHandler(FilenameUtils.getExtension(file.getFileName()));
            Workbook workbook = fileHandler.getWorkbook(path.toString());

            List<String> columns = excelService.getHeader(workbook, 0);

            ValidationDto validationDto = new ValidationDto();

            List<ValidationError> headerErrors = this.firstLevelOfValidation(columns, file);

            if (headerErrors != null && !headerErrors.isEmpty()) {
                headerErrors = validationErrorRepository.saveAll(headerErrors);
                validationDto.addErrors(headerErrors);
            }

            Iterator<Row> rows = excelService.getRowDataIterator(workbook, 0);
            int index = 1;
            int rowsWithErrors = 0;
            while (rows.hasNext()) {
                Row row = rows.next();
                List<ValidationError> dataErrors = this.secondLevelOfValidation(columns, row, index, file);
                if (!dataErrors.isEmpty()) {
                    dataErrors = validationErrorRepository.saveAll(dataErrors);
                    validationDto.addErrors(dataErrors);
                    rowsWithErrors++;
                }
                index++;
            }

            if (validationDto.getErrors() == null || validationDto.getErrors().isEmpty()) {
                file.setHasPassedValidation(true);
                file.setRowErrorsCount(rowsWithErrors);
                file = bulkImportFileRepository.save(file);
            }
            validationDto.setFile(file);
            return validationDto;
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ValidationDto();
        }
    }

    @Override
    public void importFile(Long id) {
        bulkImportFileRepository.findById(id).ifPresent(e -> {
            try {
                Path path = Paths.get(e.getFilePath() + File.separator + e.getFileName());
                FileHandler fileHandler = excelService.supplyFileHandler(FilenameUtils.getExtension(e.getFileName()));
                Workbook workbook = fileHandler.getWorkbook(path.toString());

                List<String> columns = excelService.getHeader(workbook, 0);
                Iterator<Row> rows = excelService.getRowDataIterator(workbook, 0);

                Map<MultiKey, Long> workspaceCodeToProjectId = new HashMap<>();
                RestTemplate restTemplate = new RestTemplate();
                Map<Long, ImportParamsAndConfig> imports = new HashMap<>();

                while (rows.hasNext()) {
                    Row row = rows.next();

                    String workspaceContextCode = row.getCell(columns.indexOf("WorkspaceReference"), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
                    Integer uwYear = (int) row.getCell(columns.indexOf("UWYear"), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    Long projectId = null;

                    MultiKey key = new MultiKey(workspaceContextCode, uwYear);

                    if (!workspaceCodeToProjectId.containsKey(key)) {
                        UserRrEntity user = null;
                        if (SecurityContextHolder.getContext().getAuthentication() != null) {
                            user = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
                        }
                        HttpHeaders requestHeaders = new HttpHeaders();
                        requestHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);
                        if (user != null)
                            requestHeaders.set("Authorization", "Bearer " + user.getJwtToken());

                        HttpEntity<ProjectEntity> request = new HttpEntity<>(new ProjectEntity("Bulk-Import " + (new Date()).toString(),
                                "Project used to do bulk import for the file named " + e.getFileName()), requestHeaders);

                        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(projectCalculationURL)
                                .queryParam("wsId", workspaceContextCode)
                                .queryParam("uwy", uwYear);
                        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

                        try {
                            ResponseEntity<Object> creationResponse = restTemplate
                                    .exchange(uriBuilder.toUriString(), HttpMethod.POST, request, Object.class);

//                        Object creationRes = restTemplate
//                                .postForEntity(uriBuilder.toUriString(), request, Object.class);

                            if (creationResponse.getStatusCode().equals(HttpStatus.OK)) {
                                log.info("Project's creation has ended successfully");

                                try {
                                    Map<String, Object> o = (LinkedHashMap<String, Object>) creationResponse.getBody();
                                    if (o != null) {
                                        projectId = (long) (Integer) o.get("projectId");
                                        workspaceCodeToProjectId.put(key, projectId);
                                    } else {
                                        log.info("The project creation response is empty");
                                        break;
                                    }
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                    log.info("Can't retrieve projectId from the response");
                                    break;
                                }
                            } else {
                                log.error("Project creation has failed for the following workspace {}", workspaceContextCode);
                                break;
                            }

                        } catch (RuntimeException ex) {
                            log.error(ex.getMessage());
                            ex.printStackTrace();
                            break;
                        }
                    } else
                        projectId = workspaceCodeToProjectId.get(key);
                    String instanceName = row.getCell(columns.indexOf("Instance"), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
                    String type = row.getCell(columns.indexOf("Type"), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
                    String dataSourceName = row.getCell(columns.indexOf("DataSourceName"), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
                    ModellingSystemInstanceEntity instance = modellingSystemInstanceRepository.findByName(instanceName);
                    String instanceId = instance != null ? instance.getModellingSystemInstanceId() : "";
                    String fp = row.getCell(columns.indexOf("FinancialPerspective"), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
                    Long objectSourceId = (long) row.getCell(columns.indexOf("ObjectSourceId"), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    String objectSourceName = row.getCell(columns.indexOf("ObjectSourceName"), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
                    String objectSourceType = row.getCell(columns.indexOf("ObjectSourceType"), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
                    String currency = row.getCell(columns.indexOf("TargetCurrency"), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();

                    Page<DataSource> rmsDataSource = rmsService.listAvailableDataSources(instanceId, dataSourceName, 0, 1);

                    if (rmsDataSource != null) {
                        RLModelDataSource dataSource = rlModelDataSourceRepository.findByProjectIdAndTypeAndInstanceIdAndRlId(projectId, type, instanceId, rmsDataSource.getContent().get(0).getRmsId());
                        if (dataSource == null) {
                            dataSource = new RLModelDataSource(rmsDataSource.getContent().get(0), projectId, instanceId, instanceName);
                            dataSource = rlModelDataSourceRepository.save(dataSource);
                        }

                        String targetRap = row.getCell(columns.indexOf("PEQT_ID"), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
                        String regionPeril = row.getCell(columns.indexOf("RegionPeril"), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
                        String occurrenceBasisOverRide = row.getCell(columns.indexOf("OccurrenceBasisOverRide"), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
                        String occurrenceOverrideReason = row.getCell(columns.indexOf("OccurrenceBasisOverrideReason"), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
                        Float unitMultiplier = (float) row.getCell(columns.indexOf("UnitMultiplier"), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getNumericCellValue();
                        Float proportion = (float) row.getCell(columns.indexOf("ProportionPct"), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getNumericCellValue();
                        Integer division = (int) row.getCell(columns.indexOf("SectionDivisionId"), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getNumericCellValue();

                        if (type.equalsIgnoreCase("EDM")) {
                            PortfolioHeader portfolioHeader = new PortfolioHeader(objectSourceId, objectSourceName, objectSourceType, currency, dataSource.getRlId(), dataSource.getName());
                            RLPortfolio portfolio = rmsService.scanPortfolioForBulkImport(portfolioHeader, projectId, instanceId, dataSource);

                            PortfolioSelectionDto portfolioSelectionDto = new PortfolioSelectionDto();

                            portfolioSelectionDto.setProjectId(projectId);
                            portfolioSelectionDto.setDivisions(Collections.singletonList(division));
                            portfolioSelectionDto.setProportion((double) proportion);
                            portfolioSelectionDto.setUnitMultiplier((double) unitMultiplier);
                            portfolioSelectionDto.setTargetCurrency(currency);
                            portfolioSelectionDto.setRlPortfolioId(portfolio.getRlPortfolioId());

                            if (imports.get(projectId) != null) {
                                imports.get(projectId).addPortfolioConfig(portfolioSelectionDto);
                            } else {
                                ImportParamsAndConfig importConfig = new ImportParamsAndConfig(instanceId, String.valueOf(projectId), null, new ArrayList<>()
                                        , new ArrayList<>(Arrays.asList(portfolioSelectionDto)));
                                imports.put(projectId, importConfig);
                            }

                        } else if (type.equalsIgnoreCase("RDM")) {
                            AnalysisHeader analysisHeader = new AnalysisHeader(objectSourceId, objectSourceName, dataSource.getRlId(), dataSourceName);
                            RLAnalysis analysis = rmsService.scanAnalysisForBulkImport(analysisHeader, projectId, fp, instanceId, dataSource.getRlModelDataSourceId());
                            ImportSelectionDto rlImportSelectionDto = new ImportSelectionDto();

                            rlImportSelectionDto.setProjectId(projectId);
                            rlImportSelectionDto.setFinancialPerspectives(Collections.singletonList(fp));
                            rlImportSelectionDto.setDivisions(Collections.singletonList(division));
                            rlImportSelectionDto.setOccurrenceBasis(occurrenceBasisOverRide);
                            rlImportSelectionDto.setOccurrenceBasisOverrideReason(occurrenceOverrideReason);
                            rlImportSelectionDto.setProportion(proportion);
                            rlImportSelectionDto.setUnitMultiplier(unitMultiplier);
                            rlImportSelectionDto.setTargetRegionPeril(regionPeril);
                            rlImportSelectionDto.setTargetRAPCodes(Collections.singletonList(targetRap));
                            rlImportSelectionDto.setRlAnalysisId(analysis.getRlAnalysisId());
                            rlImportSelectionDto.setTargetCurrency(currency);

                            if (imports.get(projectId) != null) {
                                imports.get(projectId).addAnalysisConfig(rlImportSelectionDto);
                            } else {
                                ImportParamsAndConfig importConfig = new ImportParamsAndConfig(instanceId, String.valueOf(projectId), null,
                                        new ArrayList<>(Arrays.asList(rlImportSelectionDto)), new ArrayList<>());
                                imports.put(projectId, importConfig);
                            }

                        } else {
                            log.error("The datasource type isn't one of the following (EDM,RDM)");
                            break;
                        }
                    } else {
                        log.error("No datasource has been found for {}", dataSourceName);
                        break;
                    }
                }

                imports.forEach((k, value) -> {
                    List<Long> analysisIds = rmsService.saveAnalysisImportSelection(value.getAnalysisConfig());
                    List<Long> portfolioIds = rmsService.savePortfolioImportSelection(value.getPortfolioConfig());
                    ImportLossDataParams params = new ImportLossDataParams(value, analysisIds, portfolioIds);
                    batchExecution.queueImportLossData(params.getInstanceId(), Long.valueOf(params.getProjectId()), Long.valueOf(params.getUserId()));
                });

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    @Override
    public Page<BulkImportFile> getImportHistory(int page, int records) {
        UserRrEntity user = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        return bulkImportFileRepository
                .findByUserId(user.getUserId(), PageRequest.of(page - 1, records, Sort.by("bulkImportFileId").descending()));
    }

    private List<ValidationError> firstLevelOfValidation(List<String> headers, BulkImportFile file) {
        List<String> mandatoryFields = bulkImportFileColumnsRepository.findByImportance("Mandatory");
        List<String> missingFields = ListUtils.subtract(mandatoryFields, headers);

        return missingFields.stream()
                .map(e -> new ValidationError(e, 0, headers.indexOf(e), "This is a mandatory field but it is not present in the file", file))
                .collect(Collectors.toList());
    }

    private List<ValidationError> secondLevelOfValidation(List<String> headers, Row rowData, int rowIndex, BulkImportFile file) {

        List<ValidationError> secondLevelValidationErrors = new ArrayList<>();
        for (String header : headers) {
            Cell cell;
            ValidationError error;
            switch (header.trim()) {
                case "WorkspaceContext":
                    cell = rowData.getCell(headers.indexOf(header));
                    if (cell != null && !(cell.getStringCellValue().equalsIgnoreCase("Treaty") || cell.getStringCellValue().equalsIgnoreCase("Fac")
                            || cell.getStringCellValue().equalsIgnoreCase("T") || cell.getStringCellValue().equalsIgnoreCase("F"))) {
                        error = new ValidationError("WorkspaceContext", rowIndex, headers.indexOf(header), "The WorkspaceContext's value isn't one of the following (Fac, Treaty, F, T)", file);
                        secondLevelValidationErrors.add(error);
                    }
                    break;
                case "UWYear":
                    cell = rowData.getCell(headers.indexOf(header));
                    try {
                        if (cell != null) {
                            Integer year = (int) cell.getNumericCellValue();
                            Integer currentYear = Calendar.getInstance().get(Calendar.YEAR);
                            if (Math.abs(currentYear - year) > 3) {

                                error = new ValidationError("UWYear", rowIndex, headers.indexOf(header), "The UWYear is 3 years more or less than the current one", file);
                                secondLevelValidationErrors.add(error);
                            }
                        }
                        break;
                    } catch (ClassCastException | NumberFormatException | IllegalStateException ex) {
                        error = new ValidationError("UWYear", rowIndex, headers.indexOf(header), "The UWYear's value isn't an integer", file);
                        secondLevelValidationErrors.add(error);
                        break;
                    }

                case "Instance":
                    cell = rowData.getCell(headers.indexOf(header));
                    if (cell != null && modellingSystemInstanceRepository.findByName(cell.getStringCellValue().trim()) == null) {
                        error = new ValidationError("Instance", rowIndex, headers.indexOf(header), "The instance name wasn't found", file);
                        secondLevelValidationErrors.add(error);
                    }
                    break;
                case "Type":
                    cell = rowData.getCell(headers.indexOf(header));
                    if (cell != null && !(cell.getStringCellValue().trim().equalsIgnoreCase("EDM") || cell.getStringCellValue().trim().equalsIgnoreCase("RDM"))) {
                        error = new ValidationError("Type", rowIndex, headers.indexOf(header), "The type column should contain one of the following values (RDM,EDM)", file);
                        secondLevelValidationErrors.add(error);
                    }
                    break;
                case "TargetCurrency":
                    cell = rowData.getCell(headers.indexOf(header));
                    if (cell != null && currencyRepository.findByCode(cell.getStringCellValue()) == null) {
                        error = new ValidationError("Type", rowIndex, headers.indexOf(header), "The currency chosen isn't a supported one", file);
                        secondLevelValidationErrors.add(error);
                    }
                    break;
            }
        }
        return secondLevelValidationErrors;
    }
}
