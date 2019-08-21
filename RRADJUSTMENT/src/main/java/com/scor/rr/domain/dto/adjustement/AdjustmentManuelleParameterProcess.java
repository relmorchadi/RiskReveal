package com.scor.rr.domain.dto.adjustement;

import com.scor.rr.domain.AdjustmentReturnPeriodBandingParameterEntity;
import com.scor.rr.domain.dto.adjustement.loss.PEATData;

import java.util.List;

public class AdjustmentManuelleParameterProcess {
    private double lmf;
    private double rpmf;
    private List<PEATData> peatData;
    private boolean capped;
    private List<AdjustmentReturnPeriodBandingParameterEntity> adjustmentReturnPeriodBendings;
    private String type;

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

    public boolean isCapped() {
        return capped;
    }

    public void setCapped(boolean capped) {
        this.capped = capped;
    }

    public List<AdjustmentReturnPeriodBandingParameterEntity> getAdjustmentReturnPeriodBendings() {
        return adjustmentReturnPeriodBendings;
    }

    public void setAdjustmentReturnPeriodBendings(List<AdjustmentReturnPeriodBandingParameterEntity> adjustmentReturnPeriodBendings) {
        this.adjustmentReturnPeriodBendings = adjustmentReturnPeriodBendings;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
