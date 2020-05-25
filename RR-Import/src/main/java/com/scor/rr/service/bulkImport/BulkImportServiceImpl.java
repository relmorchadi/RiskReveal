package com.scor.rr.service.bulkImport;

import com.scor.rr.configuration.security.UserPrincipal;
import com.scor.rr.domain.BulkImportFile;
import com.scor.rr.domain.ValidationError;
import com.scor.rr.domain.dto.ValidationDto;
import com.scor.rr.repository.*;
import com.scor.rr.service.bulkImport.abstraction.BulkImportService;
import com.scor.rr.util.ExcelService;
import com.scor.rr.util.excel.FileHandler;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BulkImportServiceImpl implements BulkImportService {


    @Autowired
    private ExcelService excelService;

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

            ValidationDto validationDto = new ValidationDto();
            validationDto.addData(0, columns);
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
                if (dataErrors != null && !dataErrors.isEmpty()) {
                    dataErrors = validationErrorRepository.saveAll(dataErrors);
                    List<Object> data = new ArrayList<>();
                    Iterator<Cell> cellDataIterator = row.cellIterator();

                    while (cellDataIterator.hasNext()) {
                        Cell cell = cellDataIterator.next();
                        if (cell.getCellType() == CellType.NUMERIC) {
                            data.add((int) cell.getNumericCellValue());
                        } else {
                            data.add(cell.getStringCellValue());
                        }
                    }
                    validationDto.addData(index, data);
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
                Map<String, Long> workspaceCodeToProjectId = new HashMap<>();
                Iterator<Row> rows = excelService.getRowDataIterator(workbook, 0);


            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    private List<ValidationError> firstLevelOfValidation(List<String> headers, BulkImportFile file) {
        List<String> mandatoryFields = bulkImportFileColumnsRepository.findByImportance("Mandatory");
        List<String> missingFields = ListUtils.subtract(mandatoryFields, headers);

        return missingFields.stream()
                .map(e -> new ValidationError(e, 0, "This is a mandatory field but it is not present in the file", file))
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
                        error = new ValidationError("WorkspaceContext", rowIndex, "The WorkspaceContext's value isn't one of the following (Fac, Treaty, F, T)", file);
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

                                error = new ValidationError("UWYear", rowIndex, "The UWYear is 3 years more or less than the current one", file);
                                secondLevelValidationErrors.add(error);
                            }
                        }
                        break;
                    } catch (ClassCastException | NumberFormatException | IllegalStateException ex) {
                        error = new ValidationError("UWYear", rowIndex, "The UWYear's value isn't an integer", file);
                        secondLevelValidationErrors.add(error);
                        break;
                    }

                case "Instance":
                    cell = rowData.getCell(headers.indexOf(header));
                    if (cell != null && modellingSystemInstanceRepository.findByName(cell.getStringCellValue().trim()) == null) {
                        error = new ValidationError("Instance", rowIndex, "The instance name wasn't found", file);
                        secondLevelValidationErrors.add(error);
                    }
                    break;
                case "Type":
                    cell = rowData.getCell(headers.indexOf(header));
                    if (cell != null && !(cell.getStringCellValue().trim().equalsIgnoreCase("EDM") || cell.getStringCellValue().trim().equalsIgnoreCase("RDM"))) {
                        error = new ValidationError("Type", rowIndex, "The type column should contain one of the following values (RDM,EDM)", file);
                        secondLevelValidationErrors.add(error);
                    }
                    break;
                case "TargetCurrency":
                    cell = rowData.getCell(headers.indexOf(header));
                    if (cell != null && currencyRepository.findByCode(cell.getStringCellValue()) == null) {
                        error = new ValidationError("Type", rowIndex, "The currency chosen isn't a supported one", file);
                        secondLevelValidationErrors.add(error);
                    }
                    break;
            }
        }
        return secondLevelValidationErrors;
    }
}
