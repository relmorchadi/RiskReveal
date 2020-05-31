package com.scor.rr.util.excel;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;

public class XLSXHandler extends FileHandler {

    @Override
    public Workbook getWorkbook(String path) throws Exception {
        FileInputStream excelFile = new FileInputStream(new File(path));
        return new XSSFWorkbook(excelFile);
    }
}
