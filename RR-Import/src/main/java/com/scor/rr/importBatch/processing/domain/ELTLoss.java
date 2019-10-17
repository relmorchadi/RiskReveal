package com.scor.rr.importBatch.processing.domain;

import com.scor.rr.importBatch.processing.domain.loss.EventLoss;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by U002629 on 11/03/2015.
 */
public class ELTLoss  extends LossData {
    private Integer analysisId;
    private String dlmProfileName;
    private List<EventLoss> eventLosses;

    public ELTLoss() {
        eventLosses = new LinkedList<EventLoss>();
    }

    public ELTLoss(String region, String peril, String currency, Integer analysisId, String financialPerspective, String dlmProfileName) {
        super(region, peril, currency, financialPerspective);
        this.analysisId = analysisId;
        this.eventLosses = new LinkedList<EventLoss>();
        this.dlmProfileName = dlmProfileName;
    }

    public Integer getAnalysisId() {
        return analysisId;
    }

    public void setAnalysisId(Integer analysisId) {
        this.analysisId = analysisId;
    }

    public List<EventLoss> getEventLosses() {
        return eventLosses;
    }

    public void setEventLosses(List<EventLoss> eventLosses) {
        this.eventLosses = eventLosses;
    }

    public void addLoss(EventLoss loss){
        this.eventLosses.add(loss);
    }

    public String getDlmProfileName() {
        return dlmProfileName;
    }

    public void setDlmProfileName(String dlmProfileName) {
        this.dlmProfileName = dlmProfileName;
    }
}
