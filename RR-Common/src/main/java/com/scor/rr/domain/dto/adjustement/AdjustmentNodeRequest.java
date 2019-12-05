package com.scor.rr.domain.dto.adjustement;

import com.scor.rr.domain.ReturnPeriodBandingAdjustmentParameterRequest;
import com.scor.rr.domain.dto.adjustement.loss.PEATData;
import com.scor.rr.domain.dto.adjustement.loss.PEATDataRequest;

import java.util.List;

public class AdjustmentNodeRequest {
    private Integer sequence;
    private Boolean capped;
    private Integer adjustmentBasis;
    private Integer adjustmentType;
    private Integer adjustmentThreadId;
    private Double lmf;
    private Double rpmf;
//    private List<PEATData> peatData;
    private PEATDataRequest peatData;
    private List<ReturnPeriodBandingAdjustmentParameterRequest> adjustmentReturnPeriodBandings;

    public AdjustmentNodeRequest() {
    }

    public AdjustmentNodeRequest(Integer sequence,
                                 Boolean capped,
                                 Integer adjustmentBasis,
                                 Integer adjustmentType,
                                 Integer adjustmentThreadId,
                                 Double lmf,
                                 Double rpmf,
                                 PEATDataRequest peatData,
                                 List<ReturnPeriodBandingAdjustmentParameterRequest> adjustmentReturnPeriodBandings) {
        this.sequence = sequence;
        this.adjustmentBasis = adjustmentBasis;
        this.adjustmentType = adjustmentType;
        this.adjustmentThreadId = adjustmentThreadId;
        this.lmf = lmf;
        this.rpmf = rpmf;
        this.peatData = peatData;
        this.adjustmentReturnPeriodBandings = adjustmentReturnPeriodBandings;
        this.capped = capped;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Integer getAdjustmentBasis() {
        return adjustmentBasis;
    }

    public void setAdjustmentBasis(Integer adjustmentBasis) {
        this.adjustmentBasis = adjustmentBasis;
    }

    public Integer getAdjustmentType() {
        return adjustmentType;
    }

    public void setAdjustmentType(Integer adjustmentType) {
        this.adjustmentType = adjustmentType;
    }


    public Integer getAdjustmentThreadId() {
        return adjustmentThreadId;
    }

    public void setAdjustmentThreadId(Integer adjustmentThreadId) {
        this.adjustmentThreadId = adjustmentThreadId;
    }

    public Double getLmf() {
        return lmf;
    }

    public void setLmf(Double lmf) {
        this.lmf = lmf;
    }

    public Double getRpmf() {
        return rpmf;
    }

    public void setRpmf(Double rpmf) {
        this.rpmf = rpmf;
    }

//    public List<PEATData> getPeatData() {
//        return peatData;
//    }
//
//    public void setPeatData(List<PEATData> peatData) {
//        this.peatData = peatData;
//    }

    public PEATDataRequest getPeatData() {
        return peatData;
    }

    public void setPeatData(PEATDataRequest peatData) {
        this.peatData = peatData;
    }

    public List<ReturnPeriodBandingAdjustmentParameterRequest> getAdjustmentReturnPeriodBandings() {
        return adjustmentReturnPeriodBandings;
    }

    public void setAdjustmentReturnPeriodBandings(List<ReturnPeriodBandingAdjustmentParameterRequest> adjustmentReturnPeriodBandings) {
        this.adjustmentReturnPeriodBandings = adjustmentReturnPeriodBandings;
    }

    public Boolean getCapped() {
        return capped;
    }

    public void setCapped(Boolean capped) {
        this.capped = capped;
    }
}
