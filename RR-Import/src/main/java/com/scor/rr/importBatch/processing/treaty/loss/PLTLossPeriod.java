package com.scor.rr.importBatch.processing.treaty.loss;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.*;

/**
 * Created by u004119 on 03/05/2016.
 */
public class PLTLossPeriod implements Comparable, Serializable {

    private static final Logger log = LoggerFactory.getLogger(PLTLossPeriod.class);

    private int simPeriod; // shared properties among PLTLossData in one analysis!

    private List<PLTLossData> pltLossDataByPeriods;

    public PLTLossPeriod(int period) {
        this.simPeriod = period;
        pltLossDataByPeriods = new ArrayList<>();
    }

    public int getSimPeriod() {
        return simPeriod;
    }

    public void setSimPeriod(int simPeriod) {
        this.simPeriod = simPeriod;
    }

    public List<PLTLossData> getPltLossDataByPeriods() {
        return pltLossDataByPeriods;
    }

    public void setPltLossDataByPeriods(List<PLTLossData> pltLossDataByPeriods) {
        this.pltLossDataByPeriods = pltLossDataByPeriods;
    }

    public synchronized void addPLTLossData(PLTLossData data) {
        if (this.pltLossDataByPeriods == null) {
            this.pltLossDataByPeriods = new ArrayList<>();
        }
        this.pltLossDataByPeriods.add(data);
    }

    @Override
    public String toString() {
        return new StringBuilder().append(Integer.toString(simPeriod)).append(";")
                .append(pltLossDataByPeriods).append(";").toString();
    }

    @Override
    public int compareTo(Object o) {
        if(! (o instanceof PLTLossPeriod))
            return -1;

        PLTLossPeriod r = (PLTLossPeriod)o;
        return new CompareToBuilder()
                .append(simPeriod, r.simPeriod)
                .toComparison();
    }


    @Override
    public int hashCode() {
        return Objects.hash(simPeriod);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final PLTLossPeriod other = (PLTLossPeriod) obj;
        return Objects.equals(this.simPeriod, other.simPeriod);
    }

    public static double maxLoss(PLTLossPeriod pltLossPeriod) {
        PLTLossData pltLossData = Collections.max(pltLossPeriod.getPltLossDataByPeriods(), new Comparator<PLTLossData>() {
            @Override
            public int compare(PLTLossData o1, PLTLossData o2) {
                return Double.compare(o1.getLoss(), o2.getLoss());
            }
        });
        return pltLossData.getLoss();
    }


}
