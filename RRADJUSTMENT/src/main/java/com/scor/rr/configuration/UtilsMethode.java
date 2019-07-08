package com.scor.rr.configuration;

import com.google.common.collect.Ordering;
import com.scor.rr.domain.dto.adjustement.loss.PLTLossData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UtilsMethode {

    public static <T extends Comparable> Boolean isSortedReversely(List<T> list) {
        if (list == null || list.size() == 0) {
            return true;
        }
        return Ordering.natural().reverse().isOrdered(list);
    }

    public static <T> void sortReverse(List<T> list) {
        Collections.sort(list, Collections.<T>reverseOrder());
    }

    public static List<Double> lerpLossData(List<PLTLossData> inputs, List<Double> ascLossOEP, List<Double> factors) {
        if (!UtilsMethode.isSorted(ascLossOEP)) {
            throw new IllegalStateException("Input keys not sorted ascendingly");
        }
        if (ascLossOEP.size() != factors.size()) {
            throw new IllegalStateException("Input keys and values not having same size");
        }
        List<Double> outFactors = new ArrayList<>();

        for (PLTLossData input : inputs) {
            double loss = input.getLoss();
            int idx = Collections.binarySearch(ascLossOEP, loss);
            double interped;
            if (idx == -1) { // under the referenced range
                interped = factors.get(0);
            } else if (idx == -1 - ascLossOEP.size()) { // beyond the referenced range
                interped = factors.get(ascLossOEP.size() - 1);
            } else if (idx >= 0) { // it matches one endpoint in RP input list
                interped = factors.get(idx);
            } else { // it falls into an interval
                int loIdx = Math.abs(idx + 2);
                int hiIdx = Math.abs(idx + 1);
                Double loLoss = ascLossOEP.get(loIdx);
                Double hiLoss = ascLossOEP.get(hiIdx);
                Double loFactor = factors.get(loIdx);
                Double hiFactor = factors.get(hiIdx);

                interped = (loss - loLoss) * (hiFactor - loFactor) / (hiLoss - loLoss) + loFactor;
            }
            outFactors.add(interped);
        }
        return outFactors;
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
    public static <T extends Comparable> Boolean isSorted(List<T> list) {
        if (list == null || list.size() == 0) {
            return null;
        }
        return Ordering.natural().isOrdered(list);
    }
    public static void append(List<Double> descOEPRP, List<Double> descOEPLoss, String delimiter, StringBuilder builder, int i, List<Double> inReturnPeriods) {
        if (i < descOEPRP.size()) {
            builder.append(descOEPRP.get(i)).append(delimiter);
        } else {
            builder.append("").append(delimiter);
        }
        if (i < descOEPLoss.size()) {
            builder.append(descOEPLoss.get(i)).append(delimiter);
        } else {
            builder.append("").append(delimiter);
        }
        if (i < inReturnPeriods.size()) {
            builder.append(inReturnPeriods.get(i)).append(delimiter);
        } else {
            builder.append("").append(delimiter);
        }
    }

}
