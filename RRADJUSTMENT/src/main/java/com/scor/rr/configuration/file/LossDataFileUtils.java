package com.scor.rr.configuration.file;

import com.scor.rr.domain.dto.adjustement.loss.PLTLossData;
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

    public static List<PLTLossData> getPltFromLossDataFile(String path) {
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            AtomicBoolean testEndMarker = new AtomicBoolean(false);
            AtomicBoolean testHeaderPlt = new AtomicBoolean(false);
            AtomicReference<Map<String, Integer>> pltHeaderOrder = new AtomicReference<>();
            List<PLTLossData> lossDataList = new ArrayList<>();
            stream.forEach(s -> {
                        if (StringUtils.isEmpty(s)) {
                            if (testEndMarker.get()) {
                                String[] pltline = s.split("\\s+");
                                pltHeaderOrder.set(retrieveFieldOrder(pltline));
                                testHeaderPlt.set(true);
                            }
                            if (s.equals(END_MARKER)) {
                                testEndMarker.set(true);
                            }
                            if(testHeaderPlt.get()) {
                                if(pltHeaderOrder.get()!= null) {
                                    String[] pltline =  s.split("\\s+");
                                    lossDataList.add(new PLTLossData());

                                }
                            }
                        }
                    }
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
