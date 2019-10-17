package com.scor.rr.importBatch.processing.treaty.loss;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class pnly for processing and temporary storage. Not for persistence.
 */

public class ScorPLTLossDataHeader implements Serializable {

    public ScorPLTLossDataHeader() {
    }

    public ScorPLTLossDataHeader(List<PLTLossPeriod> pltLossPeriods) {
        this.pltLossPeriods = pltLossPeriods;
    }

    public List<PLTLossPeriod> getPltLossPeriods() {
        return pltLossPeriods;
    }

    public void setPltLossPeriods(List<PLTLossPeriod> pltLossPeriods) {
        this.pltLossPeriods = pltLossPeriods;
    }

    public synchronized void addPLTLossPeriod(PLTLossPeriod lossPeriod) {
        if (pltLossPeriods == null) {
            pltLossPeriods = new ArrayList<>();
        }
        pltLossPeriods.add(lossPeriod);
    }

    public synchronized void addPLTLossPeriods(List<PLTLossPeriod> lossPeriods) {
        if (pltLossPeriods == null) {
            pltLossPeriods = new ArrayList<>();
        }
        pltLossPeriods.addAll(lossPeriods);
    }

    public synchronized List<PLTLossData> getSortedLossData() {
        List<PLTLossData> lossDataList = new ArrayList<>();
        for (PLTLossPeriod pltLossPeriod : pltLossPeriods) {
            lossDataList.addAll(pltLossPeriod.getPltLossDataByPeriods());
        }
        return lossDataList;
    }

    private List<PLTLossPeriod> pltLossPeriods;

    // Common properties - TODO - can be excluded?
    private String region;
    private String peril;
    private String currency;
    private String financialPerspective;
    private Integer truncatedEvents;
    private Double truncatedAAL;
    private Double truncatedSD;
    private Double truncationThreshold;
    private String truncationCurrency;

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPeril() {
        return peril;
    }

    public void setPeril(String peril) {
        this.peril = peril;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getFinancialPerspective() {
        return financialPerspective;
    }

    public void setFinancialPerspective(String financialPerspective) {
        this.financialPerspective = financialPerspective;
    }

    public Integer getTruncatedEvents() {
        return truncatedEvents;
    }

    public void setTruncatedEvents(Integer truncatedEvents) {
        this.truncatedEvents = truncatedEvents;
    }

    public Double getTruncatedAAL() {
        return truncatedAAL;
    }

    public void setTruncatedAAL(Double truncatedAAL) {
        this.truncatedAAL = truncatedAAL;
    }

    public Double getTruncatedSD() {
        return truncatedSD;
    }

    public void setTruncatedSD(Double truncatedSD) {
        this.truncatedSD = truncatedSD;
    }

    public Double getTruncationThreshold() {
        return truncationThreshold;
    }

    public void setTruncationThreshold(Double truncationThreshold) {
        this.truncationThreshold = truncationThreshold;
    }

    public String getTruncationCurrency() {
        return truncationCurrency;
    }

    public void setTruncationCurrency(String truncationCurrency) {
        this.truncationCurrency = truncationCurrency;
    }

}
