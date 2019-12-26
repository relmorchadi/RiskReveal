package com.scor.rr.configuration;

import com.scor.rr.configuration.file.CSVPLTFileReader;
import com.scor.rr.domain.ReturnPeriodBandingAdjustmentParameter;
import com.scor.rr.domain.dto.adjustement.loss.PEATData;
import com.scor.rr.exceptions.RRException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

public class UtilsMethod {

    private static final Logger log = LoggerFactory.getLogger(UtilsMethod.class);

    public static List<PEATData> getEvenPeriodDrivenPeatDataFromFile(String path){
        if (path != null) {
            File file = new File(path);
            CSVPLTFileReader csvpltFileReader = new CSVPLTFileReader();
            try {
                return csvpltFileReader.readEvenPeriodDrivenPeatData(file);
            } catch (RRException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static List<PEATData> getEvenDrivenPeatDataFromFile(String path){
        if (path != null) {
            File file = new File(path);
            CSVPLTFileReader csvpltFileReader = new CSVPLTFileReader();
            try {
                return csvpltFileReader.readEvenDrivenPeatData(file);
            } catch (RRException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public static List<ReturnPeriodBandingAdjustmentParameter> getReturnPeriodBandings(String path) {
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


    public static long copyFile(File sourceFile, File destFile) throws IOException {
        FileChannel source = null;
        FileChannel destination = null;
        long size = 0;
        log.debug("source file {} dest file {}", sourceFile.getAbsolutePath(), destFile.getAbsolutePath());
        try {
            if (!destFile.exists()) {
                if (!destFile.getParentFile().exists()) {
                    destFile.getParentFile().mkdirs();
                }
                destFile.createNewFile();
            }
            log.debug("source file {} dest file {}", sourceFile, destFile);
            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();
            destination.transferFrom(source, 0, source.size());
            size = source.size();
        } catch (Exception e) {
            log.debug("Error in copyFile {}", e);
            throw e;
        } finally {
            try {
                if (source != null) {
                    source.close();
                }
                if (destination != null) {
                    destination.close();
                }
                log.debug("Channel closed with size {}", size);
            } catch (Exception ex) {
                log.debug("Closed channel exception {}", ex.getMessage());
            }
        }
        return size;
    }

}
