package com.scor.rr.configuration;

import com.scor.rr.configuration.file.CSVPLTFileReader;
import com.scor.rr.domain.dto.adjustement.loss.AdjustmentReturnPeriodBending;
import com.scor.rr.domain.dto.adjustement.loss.PEATData;
import com.scor.rr.exceptions.RRException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

public class UtilsMethode {

    public static List<PEATData> getPeatDataFromFile(String path){
        List<PEATData> peatData = new ArrayList<>();
        if (path != null) {
            File file = new File(path);
            CSVPLTFileReader csvpltFileReader = new CSVPLTFileReader();
            try {
                return csvpltFileReader.readPeatData(file);
            } catch (RRException e) {
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
            } catch (RRException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

}
