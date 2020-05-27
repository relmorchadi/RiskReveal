package com.scor.rr.service.bulkImport;

import com.scor.rr.configuration.security.UserPrincipal;
import com.scor.rr.domain.*;
import com.scor.rr.domain.dto.ValidationDto;
import com.scor.rr.domain.riskLink.RLModelDataSource;
import com.scor.rr.repository.*;
import com.scor.rr.service.RmsService;
import com.scor.rr.service.bulkImport.abstraction.BulkImportService;
import com.scor.rr.util.ExcelService;
import com.scor.rr.util.excel.FileHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.reflect.Field;
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

            Path path = Paths.get(file.getFilePath() + "\\" + file.getFileName());
            FileHandler fileHandler = excelService.supplyFileHandler(FilenameUtils.getExtension(file.getFileName()));
            Workbook workbook = fileHandler.getWorkbook(path.toString());

            List<String> columns = excelService.getHeader(workbook, 0);
//            Map<String, String> headersForFE = new LinkedHashMap<>();

            ValidationDto validationDto = new ValidationDto();
//            columns.forEach(e -> headersForFE.put(!e.equalsIgnoreCase("") ? e : (columns.indexOf(e) + 1) + " column", e));
//            validationDto.setHeader(headersForFE);

            List<ValidationError> headerErrors = this.firstLevelOfValidation(columns, file);

            if (headerErrors != null && !headerErrors.isEmpty()) {
                headerErrors = validationErrorRepository.saveAll(headerErrors);
                validationDto.addErrors(headerErrors);
            }

            Iterator<Row> rows = excelService.getRowDataIterator(workbook, 0);
            int index = 1;
            while (rows.hasNext()) {
                Row row = rows.next();
                List<ValidationError> dataErrors = this.secondLevelOfValidation(columns, row, index, file);
                if (!dataErrors.isEmpty()) {
                    dataErrors = validationErrorRepository.saveAll(dataErrors);
//                    Map<String, Object> data = new HashMap<>();
//                    Iterator<Cell> cellDataIterator = row.cellIterator();
//
//                    while (cellDataIterator.hasNext()) {
//                        Cell cell = cellDataIterator.next();
//                        if (cell.getCellType() == CellType.NUMERIC) {
//                            data.put(!columns.get(cell.getColumnIndex()).equalsIgnoreCase("") ? columns.get(cell.getColumnIndex()) :
//                                            (cell.getColumnIndex() + 1) + " column"
//                                    , (int) cell.getNumericCellValue());
//                        } else {
//                            data.put(!columns.get(cell.getColumnIndex()).equalsIgnoreCase("") ? columns.get(cell.getColumnIndex()) :
//                                    (cell.getColumnIndex() + 1) + " column", cell.getStringCellValue());
////                        }
//                    }
//                    validationDto.addData(data);
                    validationDto.addErrors(dataErrors);
                }
                index++;
            }

            if (validationDto.getErrors() != null && !validationDto.getErrors().isEmpty()) {
                file.setHasPassedValidation(false);
                bulkImportFileRepository.save(file);
            }
            return validationDto;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public void importFile(Long id) {
        bulkImportFileRepository.findById(id).ifPresent(e -> {
            try {
                Path path = Paths.get(e.getFilePath() + "\\" + e.getFileName());
                FileHandler fileHandler = excelService.supplyFileHandler(FilenameUtils.getExtension(e.getFileName()));
                Workbook workbook = fileHandler.getWorkbook(path.toString());

                List<String> columns = excelService.getHeader(workbook, 0);
                Iterator<Row> rows = excelService.getRowDataIterator(workbook, 0);

                Map<String, Long> workspaceCodeToProjectId = new HashMap<>();
                RestTemplate restTemplate = new RestTemplate();

                while (rows.hasNext()) {
                    Row row = rows.next();

                    String workspaceContextCode = row.getCell(columns.indexOf("WorkspaceReference")).getStringCellValue();
                    Integer uwYear = (int) row.getCell(columns.indexOf("UWYear")).getNumericCellValue();
                    Long projectId = null;

                    if (!workspaceCodeToProjectId.containsKey(workspaceContextCode)) {
                        HttpHeaders requestHeaders = new HttpHeaders();
                        requestHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);

                        HttpEntity<String> request = new HttpEntity<>(requestHeaders);
                        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(projectCalculationURL)
                                .queryParam("wsId", workspaceContextCode)
                                .queryParam("uwy", uwYear)
                                .queryParam("projectEntity", new ProjectEntity("Bulk-Import " + (new Date()).toString(),
                                        "Project used to do bulk import for the file named " + e.getFileName()));

                        ResponseEntity<Object> creationResponse = restTemplate
                                .exchange(uriBuilder.toUriString(), HttpMethod.POST, request, Object.class);

                        if (creationResponse.getStatusCode().equals(HttpStatus.OK)) {
                            log.info("Project's creation has ended successfully");

                            try {
                                Object o = creationResponse.getBody();
                                if (o != null) {
                                    Class<?> c = o.getClass();

                                    Field f = c.getDeclaredField("projectId");
                                    f.setAccessible(true);

                                    projectId = (Long) f.get(o);
                                    workspaceCodeToProjectId.put(workspaceContextCode, projectId);
                                } else {
                                    log.info("The creation response is empty");
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
                    } else
                        projectId = workspaceCodeToProjectId.get(workspaceContextCode);

                    String instanceName = row.getCell(columns.indexOf("Instance")).getStringCellValue();
                    String type = row.getCell(columns.indexOf("Type")).getStringCellValue();
                    String dataSourceName = row.getCell(columns.indexOf("DataSourceName")).getStringCellValue();
                    ModellingSystemInstanceEntity instance = modellingSystemInstanceRepository.findByName(instanceName);
                    String instanceId = instance != null ? instance.getModellingSystemInstanceId() : "";

                    Page<DataSource> rmsDataSource = rmsService.listAvailableDataSources(instanceName, dataSourceName, 0, 1);
                    if (rmsDataSource != null) {
                        RLModelDataSource dataSource = new RLModelDataSource(rmsDataSource.getContent().get(0), projectId, instanceId, instanceName);
                        dataSource = rlModelDataSourceRepository.save(dataSource);
                    } else {
                        log.error("No datasource has been found for {}", dataSourceName);
                        break;
                    }
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
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
