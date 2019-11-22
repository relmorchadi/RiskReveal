package com.scor.rr.domain.dto.adjustement;

import com.scor.rr.domain.ReturnPeriodBandingAdjustmentParameter;
import com.scor.rr.domain.dto.adjustement.loss.PEATData;

import java.io.Serializable;
import java.util.List;

public class AdjustmentParameterRequest implements Serializable {

    private double lmf;
    private double rpmf;
    private List<PEATData> peatData;
    private Integer pltHeaderInput;
    private Integer nodeId;
    private List<ReturnPeriodBandingAdjustmentParameter> adjustmentReturnPeriodBandings;

    public AdjustmentParameterRequest() {
    }

    public AdjustmentParameterRequest(double lmf, double rpmf, List<PEATData> peatData, int pltHeaderInput, int adjustmentNodeId, List<ReturnPeriodBandingAdjustmentParameter> adjustmentReturnPeriodBandings) {
        this.lmf = lmf;
        this.rpmf = rpmf;
        this.peatData = peatData;
        this.pltHeaderInput = pltHeaderInput;
        this.nodeId = adjustmentNodeId;
        this.adjustmentReturnPeriodBandings = adjustmentReturnPeriodBandings;
    }

    public double getLmf() {
        return lmf;
    }

    public void setLmf(double lmf) {
        this.lmf = lmf;
    }

    public double getRpmf() {
        return rpmf;
    }

    public void setRpmf(double rpmf) {
        this.rpmf = rpmf;
    }

    public List<PEATData> getPeatData() {
        return peatData;
    }

    public void setPeatData(List<PEATData> peatData) {
        this.peatData = peatData;
    }

    public Integer getPltHeaderInput() {
        return pltHeaderInput;
    }

    public void setPltHeaderInput(Integer pltHeaderInput) {
        this.pltHeaderInput = pltHeaderInput;
    }

    public Integer getNodeId() {
        return nodeId;
    }

    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
    }

    public List<ReturnPeriodBandingAdjustmentParameter> getAdjustmentReturnPeriodBandings() {
        return adjustmentReturnPeriodBandings;
    }

    public void setAdjustmentReturnPeriodBandings(List<ReturnPeriodBandingAdjustmentParameter> adjustmentReturnPeriodBandings) {
        this.adjustmentReturnPeriodBandings = adjustmentReturnPeriodBandings;
    }
}
