package com.scor.rr.domain.dto.adjustement;

import com.scor.rr.domain.ReturnPeriodBandingAdjustmentParameter;
import com.scor.rr.domain.dto.adjustement.loss.PEATData;

import java.util.List;

public class AdjustmentNodeRequest {

    private Long adjustmentNodeId;
    private Integer sequence;
    private Boolean capped;
    private Long adjustmentBasis;
    private Long adjustmentType;
    private Long adjustmentState;
    private Long adjustmentThreadId;
    private Double lmf;
    private Double rpmf;
    private List<PEATData> peatData;
    private Long scorPltHeaderInput;
    private List<ReturnPeriodBandingAdjustmentParameter> adjustmentReturnPeriodBandings;

    public AdjustmentNodeRequest() {
    }

    public AdjustmentNodeRequest(Long adjustmentNodeId,
                                 Integer sequence,
                                 Boolean capped,
                                 Long adjustmentBasis,
                                 Long adjustmentType,
                                 Long adjustmentState,
                                 Long adjustmentThreadId,
                                 Double lmf,
                                 Double rpmf,
                                 List<PEATData> peatData,
                                 Long scorPltHeaderInput,
                                 List<ReturnPeriodBandingAdjustmentParameter> adjustmentReturnPeriodBandings) {
        this.adjustmentNodeId = adjustmentNodeId;
        this.sequence = sequence;
        this.adjustmentBasis = adjustmentBasis;
        this.adjustmentType = adjustmentType;
        this.adjustmentState = adjustmentState;
        this.adjustmentThreadId = adjustmentThreadId;
        this.lmf = lmf;
        this.rpmf = rpmf;
        this.peatData = peatData;
        this.scorPltHeaderInput = scorPltHeaderInput;
        this.adjustmentReturnPeriodBandings = adjustmentReturnPeriodBandings;
        this.capped = capped;
    }

    public AdjustmentNodeRequest(Integer sequence,
                                 Boolean capped,
                                 Long adjustmentBasis,
                                 Long adjustmentType,
                                 Long adjustmentState,
                                 Long adjustmentThreadId,
                                 Double lmf,
                                 Double rpmf,
                                 List<PEATData> peatData,
                                 Long scorPltHeaderInput, List<ReturnPeriodBandingAdjustmentParameter> adjustmentReturnPeriodBandings) {
        this.sequence = sequence;
        this.adjustmentBasis = adjustmentBasis;
        this.adjustmentType = adjustmentType;
        this.adjustmentState = adjustmentState;
        this.adjustmentThreadId = adjustmentThreadId;
        this.lmf = lmf;
        this.rpmf = rpmf;
        this.peatData = peatData;
        this.scorPltHeaderInput = scorPltHeaderInput;
        this.adjustmentReturnPeriodBandings = adjustmentReturnPeriodBandings;
        this.capped = capped;
    }

    public Long getAdjustmentNodeId() {
        return adjustmentNodeId;
    }

    public void setAdjustmentNodeId(Long adjustmentNodeId) {
        this.adjustmentNodeId = adjustmentNodeId;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Long getAdjustmentBasis() {
        return adjustmentBasis;
    }

    public void setAdjustmentBasis(Long adjustmentBasis) {
        this.adjustmentBasis = adjustmentBasis;
    }

    public Long getAdjustmentType() {
        return adjustmentType;
    }

    public void setAdjustmentType(Long adjustmentType) {
        this.adjustmentType = adjustmentType;
    }

    public Long getAdjustmentState() {
        return adjustmentState;
    }

    public void setAdjustmentState(Long adjustmentState) {
        this.adjustmentState = adjustmentState;
    }

    public Long getAdjustmentThreadId() {
        return adjustmentThreadId;
    }

    public void setAdjustmentThreadId(Long adjustmentThreadId) {
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

    public List<PEATData> getPeatData() {
        return peatData;
    }

    public void setPeatData(List<PEATData> peatData) {
        this.peatData = peatData;
    }

    public Long getScorPltHeaderInput() {
        return scorPltHeaderInput;
    }

    public void setScorPltHeaderInput(Long scorPltHeaderInput) {
        this.scorPltHeaderInput = scorPltHeaderInput;
    }

    public List<ReturnPeriodBandingAdjustmentParameter> getAdjustmentReturnPeriodBandings() {
        return adjustmentReturnPeriodBandings;
    }

    public void setAdjustmentReturnPeriodBandings(List<ReturnPeriodBandingAdjustmentParameter> adjustmentReturnPeriodBandings) {
        this.adjustmentReturnPeriodBandings = adjustmentReturnPeriodBandings;
    }

    public Boolean getCapped() {
        return capped;
    }

    public void setCapped(Boolean capped) {
        this.capped = capped;
    }
}
