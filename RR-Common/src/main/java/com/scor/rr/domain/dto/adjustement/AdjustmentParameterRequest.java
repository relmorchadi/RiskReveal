package com.scor.rr.domain.dto.adjustement;

import com.scor.rr.domain.AdjustmentReturnPeriodBandingParameterEntity;
import com.scor.rr.domain.dto.adjustement.loss.AdjustmentReturnPeriodBending;
import com.scor.rr.domain.dto.adjustement.loss.PEATData;
import com.scor.rr.domain.dto.adjustement.loss.PLTLossData;

import java.io.Serializable;
import java.util.List;

public class AdjustmentParameterRequest implements Serializable {

    private double lmf;
    private double rpmf;
    private List<PEATData> peatData;
    private Integer scorPltHeaderInput;
    private Integer nodeId;
    private List<AdjustmentReturnPeriodBending> adjustmentReturnPeriodBendings;

    public AdjustmentParameterRequest() {
    }

    public AdjustmentParameterRequest(double lmf, double rpmf, List<PEATData> peatData, int pkScorPltHeaderId, int adjustmentNodeId, List<AdjustmentReturnPeriodBending> returnPeriodBendings) {
        this.lmf = lmf;
        this.rpmf = rpmf;
        this.peatData = peatData;
        this.scorPltHeaderInput = pkScorPltHeaderId;
        this.nodeId = adjustmentNodeId;
        this.adjustmentReturnPeriodBendings = returnPeriodBendings;
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
