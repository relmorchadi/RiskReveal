package com.scor.rr.service.adjustement.pltAdjustment;

import com.google.common.collect.Ordering;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class TransformationUtils {

    private static final int PERIOD_THRESHOLD = 100000;

    private static final Logger logger = LoggerFactory.getLogger(TransformationUtils.class);

    public static long copyFile(File sourceFile, File destFile) throws IOException {
        FileChannel source = null;
        FileChannel destination = null;
        long size = 0;
        try {
            if (!destFile.exists()) {
                destFile.createNewFile();
            }
            logger.debug("source file {} dest file {}", sourceFile, destFile);
            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();
            destination.transferFrom(source, 0, source.size());
            size = source.size();
        } catch (Exception e) {
            logger.debug("Error in copyFile {}", e);
            throw e;
        } finally {
            try {
                if (source != null) {
                    source.close();
                }
                if (destination != null) {
                    destination.close();
                }
                logger.debug("Channel closed with size {}", size);
            } catch (Exception ex) {
                logger.debug("Closed channel exception {}", ex.getMessage());
            }
        }
        return size;
    }

    public static <K, V> SortedMap<K, V> newReverseMap() {
        return new TreeMap<K, V>(Collections.reverseOrder());
    }

    @Deprecated
    public static <T> SortedSet<T> newReverseSet() {
        return new TreeSet<T>(Collections.reverseOrder());
    }

    public static <T> void sortReverse(List<T> list) {
        Collections.sort(list, Collections.<T>reverseOrder());
    }

    public static <T extends Comparable> Boolean isSortedReversely(List<T> list) {
        if (list == null || list.size() == 0) {
            return true;
        }
        return Ordering.natural().reverse().isOrdered(list);
    }

    public static <T extends Comparable> Boolean isSorted(List<T> list) {
        if (list == null || list.size() == 0) {
            return null;
        }
        return Ordering.natural().isOrdered(list);
    }


    public static List<Double> lerp(List<Double> inputs, List<Double> ascKeys, List<Double> values) {
        if (!isSorted(ascKeys)) {
            throw new IllegalStateException("Input ascKeys not sorted ascendingly");
        }
        if (ascKeys.size() != values.size()) {
            throw new IllegalStateException("Input ascKeys and values not having same size");
        }
        List<Double> out = new ArrayList<>();
        for (Double input : inputs) {
            int idx = Collections.binarySearch(ascKeys, input);
            double interped;
            if (idx == -1) { // under the referenced range
                interped = values.get(0);
            } else if (idx == -1 - ascKeys.size()) { // beyond the referenced range
                interped = values.get(ascKeys.size() - 1);
            } else if (idx >= 0) { // it matches one endpoint in RP input list
                interped = values.get(idx);
            } else { // it falls into an interval
                int lowIdx = Math.abs(idx + 2);
                int highIdx = Math.abs(idx + 1);
                Double loKey = ascKeys.get(lowIdx);
                Double hiKey = ascKeys.get(highIdx);
                Double loValue = values.get(lowIdx);
                Double hiValue = values.get(highIdx);

                interped = (input - loKey) * (hiValue - loValue) / (hiKey - loKey) + loValue;
            }
            out.add(interped);
        }
        return out;
    }
}
