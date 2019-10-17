package com.scor.rr.configuration.file;

import com.scor.rr.domain.dto.ImportFilePLTData;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class LossDataFileUtils {

    private static final Logger log = LoggerFactory.getLogger(LossDataFileUtils.class);

    private static final String START_MARKER = "LOSSFILE";
    private static final String END_MARKER = "#[HEADER-END]";
    private static final String PLT_DATA_EVENT_DATE = "MM-DD(N)";
    private static final String PLT_DATA_YEAR = "YEAR";
    private static final String PLT_DATA_EVENT_ID = "EVENTID";
    private static final String PLT_DATA_VALUE = "VALUE";
    private static final String PLT_DATA_MAX_EXPOSURE = "MAXEXPOSURE";

     static Pattern pattern = Pattern.compile("(\\d+)-(\\d+)\\((\\d+)\\)");

    public static List<ImportFilePLTData> getPltFromLossDataFile(String path) {
        List<ImportFilePLTData> pltDataList = null;
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            AtomicBoolean testEndMarker = new AtomicBoolean(false);
            AtomicBoolean testHeaderPlt = new AtomicBoolean(false);
            AtomicReference<Map<String, Integer>> pltHeaderOrder = new AtomicReference<>();
            pltDataList = new ArrayList<>();
            List<ImportFilePLTData> finalPltDataList = pltDataList;
            stream.forEach(s -> {
                        if (!StringUtils.isEmpty(s)) {
                            if (testHeaderPlt.get()) {
                                if (pltHeaderOrder.get() != null) {
                                    String[] pltline = s.split("\\s+");
                                    ImportFilePLTData importFilePLTData = new ImportFilePLTData();
                                    if (pltHeaderOrder.get().get(PLT_DATA_EVENT_ID) != null) {
                                        importFilePLTData.setEventId(Integer.parseInt(pltline[pltHeaderOrder.get().get(PLT_DATA_EVENT_ID)]));
                                    }
                                    if (pltHeaderOrder.get().get(PLT_DATA_YEAR) != null) {
                                        importFilePLTData.setYear(Integer.parseInt(pltline[pltHeaderOrder.get().get(PLT_DATA_YEAR)]));
                                    }
                                    if (pltline[pltHeaderOrder.get().get(PLT_DATA_VALUE)] != null) {
                                        importFilePLTData.setValue(Float.parseFloat(pltline[pltHeaderOrder.get().get(PLT_DATA_VALUE)]));
                                    }
                                    if (pltHeaderOrder.get().get(PLT_DATA_MAX_EXPOSURE) != null) {
                                        importFilePLTData.setMaxExposure(Float.parseFloat(pltline[pltHeaderOrder.get().get(PLT_DATA_MAX_EXPOSURE)]));
                                    } else {
                                        importFilePLTData.setMaxExposure(importFilePLTData.getValue());
                                    }
                                    if (pltHeaderOrder.get().get(PLT_DATA_EVENT_DATE) != null) {
                                        String eventDate = pltline[pltHeaderOrder.get().get(PLT_DATA_EVENT_DATE)];
                                        importFilePLTData.setEventDate(eventDate);
                                        Matcher matcher = pattern.matcher(eventDate);
                                        if (matcher.find()) {
                                            if (matcher.group(1) != null) {
                                                importFilePLTData.setMonth(Integer.parseInt(matcher.group(1)));
                                            }
                                            if (matcher.group(2) != null) {
                                                importFilePLTData.setDay(Integer.parseInt(matcher.group(2)));
                                            }
                                            if (matcher.group(3) != null) {
                                                importFilePLTData.setRepetition(Integer.parseInt(matcher.group(3)));
                                            }
                                        }
                                    }
                                    finalPltDataList.add(importFilePLTData);
                                }
                            }
                            if (testEndMarker.get()) {
                                String[] pltline = s.split("\\s+");
                                pltHeaderOrder.set(retrieveFieldOrder(pltline));
                                testHeaderPlt.set(true);
                                testEndMarker.set(false);
                            }
                            if (s.equals(END_MARKER)) {
                                testEndMarker.set(true);
                            }
                        }
                    }
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pltDataList;
    }

    private static Map<String, Integer> retrieveFieldOrder(String[] headings) {
        Map<String, Integer> fieldOrder = new HashMap<>();
        for (int i = 0; i < headings.length; i++) {
            switch (headings[i].toUpperCase()) {
                case PLT_DATA_EVENT_DATE:
                    fieldOrder.put(PLT_DATA_EVENT_DATE, i);
                    break;
                case PLT_DATA_YEAR:
                    fieldOrder.put(PLT_DATA_YEAR, i);
                    break;
                case PLT_DATA_EVENT_ID:
                    fieldOrder.put(PLT_DATA_EVENT_ID, i);
                    break;
                case PLT_DATA_VALUE:
                    fieldOrder.put(PLT_DATA_VALUE, i);
                    break;
                case PLT_DATA_MAX_EXPOSURE:
                    fieldOrder.put(PLT_DATA_MAX_EXPOSURE, i);
                    break;
                default:
                    log.debug("Field {} unknown", headings[i]);
                    return null;
            }
        }
        return fieldOrder;
    }
}
