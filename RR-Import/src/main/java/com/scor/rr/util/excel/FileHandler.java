package com.scor.rr.util.excel;

import org.apache.poi.ss.usermodel.Workbook;

public abstract class FileHandler {

    public abstract Workbook getWorkbook(String path) throws Exception;

}
