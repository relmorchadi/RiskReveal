package com.scor.rr.util.excel;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;

public class XLSHandler extends FileHandler {


    @Override
    public Workbook getWorkbook(String path) throws Exception {

        FileInputStream excelFile = new FileInputStream(new File(path));
        return new HSSFWorkbook(excelFile);
    }

}
