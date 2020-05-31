package com.scor.rr.util;

import com.scor.rr.util.excel.FileHandler;
import com.scor.rr.util.excel.XLSHandler;
import com.scor.rr.util.excel.XLSXHandler;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Supplier;

@Component
public class ExcelService {

    private java.util.Map<String, Supplier<FileHandler>> fileSuppliers = new HashMap<>();

    @Value("${ihub.treaty.out.path}")
    private String iHub;

    //private static final Pattern pattern = Pattern.compile();

    public FileHandler supplyFileHandler(String type) {
        Supplier<FileHandler> fileHandler = fileSuppliers.get(type.toLowerCase().trim());

        if (fileHandler == null) {
            throw new IllegalArgumentException("Invalid file type: "
                    + type);
        }

        return fileHandler.get();
    }

    @PostConstruct
    private void init() {

        final java.util.Map<String, Supplier<FileHandler>>
                cleansers = new HashMap<>();

        cleansers.put("xls", XLSHandler::new);
        cleansers.put("xlsx", XLSXHandler::new);
        cleansers.put("cls", XLSXHandler::new);

        fileSuppliers = Collections.unmodifiableMap(cleansers);
    }

    public Path saveToDisk(MultipartFile file) {
        try {

            Path iHubPath = Paths.get(iHub);
            File theFile = PathUtils.makeFullFile("BulkImport", file.getOriginalFilename(), iHubPath);

            Files.write(Paths.get(theFile.getPath()), file.getBytes());

            return Paths.get(theFile.getPath());

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<String> getHeader(Workbook workbook, int sheetIndex) {
        Sheet sheet = workbook.getSheetAt(sheetIndex);
        Row header = sheet.getRow(0);
        List<String> columns = new ArrayList<>();
        Iterator<Cell> cellIterator = header.cellIterator();

        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            columns.add(cell.getStringCellValue());
        }

        return columns;
    }

    public Iterator<Row> getRowDataIterator(Workbook workbook, int sheetIndex) {
        Sheet sheet = workbook.getSheetAt(sheetIndex);
        sheet.removeRow(sheet.getRow(0));
        return sheet.rowIterator();
    }
}
