package com.scor.rr.service.adjustement;

import com.scor.rr.configuration.excel.ErrorExcelFile;
import com.scor.rr.configuration.excel.Voof;
import com.scor.rr.domain.dto.adjustement.loss.PLTLossData;
import com.scor.rr.domain.dto.adjustement.modelcsvadjustement.PLTLossDataExcelModel;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.*;

@Service
public class PltFromCsv {

    @Autowired
    private DozerBeanMapper mapper;

    public List<ErrorExcelFile> checkFileExcel(String fileName) {
        List<ErrorExcelFile> cellErrors = new ArrayList<>();
        Voof.fromFile(fileName, PLTLossDataExcelModel.class, (o, errors) -> {
            errors.forEach(t -> cellErrors.add(new ErrorExcelFile(o.getRowIndex()-1, Integer.valueOf(t.getIndex()), t.getCause(), t.getValue())));
        });
        return cellErrors;
    }

    public void saveExcelFile(String fileName, PLTLossDataExcelModel natCatMetaEstimate) {
        Voof.fromFile(fileName, PLTLossDataExcelModel.class, (o, errors) -> {
            PLTLossData pltLossData = new PLTLossData();
            mapper.map(o, pltLossData);
        });
    }

    public List<PLTLossData> getFileExcelRows(String fileName) {
        List<PLTLossData> pltLossDataList = Collections.synchronizedList(new ArrayList<>());
        Voof.fromFile(fileName, PLTLossDataExcelModel.class).parallelStream().forEach((e) -> {
            PLTLossData pltLossData = new PLTLossData();
            mapper = new DozerBeanMapper();
            mapper.map(e, pltLossData);
            pltLossDataList.add(pltLossData);
        });
        return pltLossDataList;
    }

}
