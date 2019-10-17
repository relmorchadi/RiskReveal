package com.scor.rr.importBatch.processing.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by U002629 on 11/03/2015.
 */
public class PLTLoss extends LossData{
    private List<PLTPeriod> periods;

    public PLTLoss() {
        periods = new ArrayList<>();
    }

    public PLTLoss(String region, String peril, String currency, String financialPerspective) {
        super(region, peril, currency, financialPerspective);
        periods = new ArrayList<>();
    }

    public List<PLTPeriod> getPeriods() {
        return periods;
    }

    public void setPeriods(List<PLTPeriod> periods) {
        this.periods = periods;
    }

    public synchronized void addPeriod(PLTPeriod p){
        periods.add(p);
    }

    public synchronized void sortPeriods(){
        Collections.sort(periods);
    }
}
