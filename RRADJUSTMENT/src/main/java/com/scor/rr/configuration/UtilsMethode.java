package com.scor.rr.configuration;

import com.scor.rr.configuration.file.CSVPLTFileReader;
import com.scor.rr.domain.dto.adjustement.loss.AdjustmentReturnPeriodBending;
import com.scor.rr.domain.dto.adjustement.loss.PEATData;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class UtilsMethode {

    public static List<PEATData> getPeatDataFromFile(String path){
        List<PEATData> peatData = new ArrayList<>();
        if (path != null) {
            File file = new File(path);
            CSVPLTFileReader csvpltFileReader = new CSVPLTFileReader();
            try {
                return csvpltFileReader.readPeatData(file);
            } catch (com.scor.rr.exceptions.fileExceptionPlt.RRException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static List<AdjustmentReturnPeriodBending> getReturnPeriodBendings(String path) {
        if (path != null) {
            File file = new File(path);
            CSVPLTFileReader csvpltFileReader = new CSVPLTFileReader();
            try {
                return csvpltFileReader.readAdjustmentReturnPeriodBanding(file);
            } catch (com.scor.rr.exceptions.fileExceptionPlt.RRException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
