package com.scor.rr.importBatch.processing.elt;

import com.scor.rr.importBatch.processing.domain.loss.EventLoss;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
//import java.util.stream.Stream;

/**
 * Created by U002629 on 19/02/2015.
 */
public class ELTFileReader implements ELTReader {
    private static final Logger log= LoggerFactory.getLogger(ELTFileReader.class);
    private final String eltFilePath;
    private final String eltFileExtension;
    private final String eltFileFieldSeparator;

    private ELTFileReader(String eltFilePath, String eltFileExtension, String eltFileFieldSeparator) {
        this.eltFilePath = eltFilePath;
        this.eltFileExtension = eltFileExtension;
        this.eltFileFieldSeparator = eltFileFieldSeparator;
    }

    @Override
    public List<EventLoss> readELTs(String[] elts) {
        final List<EventLoss> eventLosses = Collections.synchronizedList(new ArrayList<EventLoss>());

//        for (String elt : elts) {
//            Stream<String> lines = null;
//            try {
//                lines = Files.lines(Paths.get(eltFilePath + elt + eltFileExtension));
//                lines.parallel().forEach(new java.util.function.Consumer<String>() {
//                    @Override
//                    public void accept(String s) {
//                        // Event ID 	Frequency	Mean Loss	StDeviation I	StDeviation C	Exposure Value
//                        //EventID	Frequency	Value	Desc	Year	Day	StdDevC	StdDevU	ExpTIV
//                        try {
//                            String[] fields = s.split(eltFileFieldSeparator);
//                            Integer eventID = Integer.valueOf(fields[0]);
//                            Double meanLoss = new Double(fields[2].replace(",", "."));
//                            Double exposure = new Double(fields[8].replace(",", "."));
//                            Double stdDevC = new Double(fields[6].replace(",", "."));
//                            Double stdDevU = new Double(fields[7].replace(",", "."));
//                            Double freq = new Double(fields[1]);
//
//                            EventLoss myLoss = new EventLoss(eventID,meanLoss, stdDevC, stdDevU, exposure, freq, new Double("0"));
//                            eventLosses.add(myLoss);
//                        } catch (NumberFormatException e) {
////                            log.info("ignoring header line");
//                        }
//                    }
//                });
//            } catch (IOException e) {
//                log.error("Exception: ",e);
//            } finally {
//                if (lines != null)
//                    lines.close();
//            }
//        }

        return eventLosses;
    }


    public Map<Integer, EventLoss> aggregateELTsAndBuildBetas(String[] elts) {
        final Map<Integer, EventLoss> eventLossMap = Collections.synchronizedMap(new HashMap<Integer, EventLoss>());
        log.info("aggregat eELTs And Build Betas");
//        for (String elt : elts) {
//            log.info("reading "+eltFilePath+elt+eltFileExtension);
//            Stream<String> lines = null;
//            try {
//                lines = Files.lines(Paths.get(eltFilePath + elt + eltFileExtension));
//                lines.parallel().forEach(new java.util.function.Consumer<String>() {
//                    @Override
//                    public void accept(String s) {
//                        // Event ID 	Frequency	Mean Loss	StDeviation I	StDeviation C	Exposure Value
//                        //EventID	Frequency	Value	Desc	Year	Day	StdDevC	StdDevU	ExpTIV
//                        try {
//                            String[] fields = s.split(eltFileFieldSeparator);
//                            Integer eventID = Integer.valueOf(fields[0]);
//                            Double meanLoss = new Double(fields[2].replace(",", "."));
//                            Double exposure = new Double(fields[8].replace(",", "."));
//                            Double stdDevC = new Double(fields[6].replace(",", "."));
//                            Double stdDevU = new Double(fields[7].replace(",", "."));
//                            Double freq = new Double(fields[1]);
//
//                            EventLoss myLoss = eventLossMap.get(eventID);
//                            if (myLoss == null) {
//                                myLoss = new EventLoss(eventID, meanLoss, stdDevC, stdDevU, exposure, freq, new Double("0"));
//                                eventLossMap.put(eventID, myLoss);
//                            } else {
//                                myLoss.addLoss(meanLoss, stdDevC, stdDevU, exposure, freq);
//                            }
//                        } catch (NumberFormatException e) {
////                            log.info("ignoring header line");
//                        }
//                    }
//                });
//            } catch (IOException e) {
//                log.error("Exception: ",e);
//            } finally {
//                if (lines != null)
//                    lines.close();
//            }
//        }
        log.info("finished reading ELTs");
        log.info("Building Beta Functions for all events");
        for (EventLoss eventLoss : eventLossMap.values()) {
            eventLoss.buildFunction(null);
        }
        log.info("finished building Beta functions");
        return eventLossMap;
    }

    @Override
    public Map<Integer, EventLoss> aggregateELTsAndBuildBetas(List<EventLoss> events) {
        final Map<Integer, EventLoss> eventLossMap = Collections.synchronizedMap(new HashMap<Integer, EventLoss>());

        for (EventLoss elt : events) {
            EventLoss myLoss = eventLossMap.get(elt.getEventId());
            if (myLoss == null) {
                eventLossMap.put(elt.getEventId(), elt);
            } else {
                myLoss.addLoss(elt);
            }
        }
        for (EventLoss eventLoss : eventLossMap.values()) {
            eventLoss.buildFunction(null);
        }
        return eventLossMap;
    }
}
