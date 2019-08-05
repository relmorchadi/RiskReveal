package com.scor.rr.domain.dto.adjustement;

import com.scor.rr.domain.dto.adjustement.loss.AdjustmentReturnPeriodBending;
import com.scor.rr.domain.dto.adjustement.loss.PEATData;
import com.scor.rr.domain.dto.adjustement.loss.PLTLossData;

import java.util.List;

public class AdjustmentParameterRequest {

    private double lmf;
    private double rpmf;
    private List<PEATData> peatData;
    private Integer scorPltHeaderInput;
    private Integer nodeId;
    private List<AdjustmentReturnPeriodBending> adjustmentReturnPeriodBendings;

    public AdjustmentParameterRequest(double lmf, double rpmf, List<PEATData> peatData, Integer scorPltHeaderInput, Integer nodeId, List<AdjustmentReturnPeriodBending> adjustmentReturnPeriodBendings) {
        this.lmf = lmf;
        this.rpmf = rpmf;
        this.peatData = peatData;
        this.scorPltHeaderInput = scorPltHeaderInput;
        this.nodeId = nodeId;
        this.adjustmentReturnPeriodBendings = adjustmentReturnPeriodBendings;
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

    public Integer getScorPltHeaderInput() {
        return scorPltHeaderInput;
    }

    public void setScorPltHeaderInput(Integer scorPltHeaderInput) {
        this.scorPltHeaderInput = scorPltHeaderInput;
    }

    public Integer getNodeId() {
        return nodeId;
    }

    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
    }

    public List<AdjustmentReturnPeriodBending> getAdjustmentReturnPeriodBendings() {
        return adjustmentReturnPeriodBendings;
    }

    public void setAdjustmentReturnPeriodBendings(List<AdjustmentReturnPeriodBending> adjustmentReturnPeriodBendings) {
        this.adjustmentReturnPeriodBendings = adjustmentReturnPeriodBendings;
    }
}
