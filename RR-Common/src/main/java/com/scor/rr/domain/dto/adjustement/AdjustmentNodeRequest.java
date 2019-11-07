package com.scor.rr.domain.dto.adjustement;

import com.scor.rr.domain.AdjustmentReturnPeriodBandingParameterEntity;
import com.scor.rr.domain.dto.adjustement.loss.PEATData;

import java.util.List;

public class AdjustmentNodeRequest {

    private int adjustmentNodeId;
    private String layer;
    private Integer sequence;
    private Boolean capped;
    private Integer adjustmentBasis;
    private Integer adjustmentType;
    private Integer adjustmentState;
    private Integer adjustmentThreadId;
    private Double lmf;
    private Double rpmf;
    private List<PEATData> peatData;
    private Integer scorPltHeaderInput;
    private List<AdjustmentReturnPeriodBandingParameterEntity> adjustmentReturnPeriodBandings;

    public AdjustmentNodeRequest() {
    }

    public AdjustmentNodeRequest(int adjustmentNodeId,
                                 String layer,
                                 Integer sequence,
                                 Boolean capped,
                                 Integer adjustmentBasis,
                                 Integer adjustmentType,
                                 Integer adjustmentState,
                                 Integer adjustmentThreadId,
                                 Double lmf,
                                 Double rpmf,
                                 List<PEATData> peatData,
                                 Integer scorPltHeaderInput,
                                 List<AdjustmentReturnPeriodBandingParameterEntity> adjustmentReturnPeriodBandings) {
        this.adjustmentNodeId = adjustmentNodeId;
        this.layer = layer;
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
    }

    public AdjustmentNodeRequest(String layer,
                                 Integer sequence,
                                 Boolean capped,
                                 Integer adjustmentBasis,
                                 Integer adjustmentType,
                                 Integer adjustmentState,
                                 Integer adjustmentThreadId,
                                 Double lmf,
                                 Double rpmf,
                                 List<PEATData> peatData, Integer scorPltHeaderInput, List<AdjustmentReturnPeriodBandingParameterEntity> adjustmentReturnPeriodBandings) {
        this.layer = layer;
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
    }

    public int getAdjustmentNodeId() {
        return adjustmentNodeId;
    }

    public void setAdjustmentNodeId(int adjustmentNodeId) {
        this.adjustmentNodeId = adjustmentNodeId;
    }

    public String getLayer() {
        return layer;
    }

    public void setLayer(String layer) {
        this.layer = layer;
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

    public Integer getAdjustmentState() {
        return adjustmentState;
    }

    public void setAdjustmentState(Integer adjustmentState) {
        this.adjustmentState = adjustmentState;
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

    public List<AdjustmentReturnPeriodBandingParameterEntity> getAdjustmentReturnPeriodBandings() {
        return adjustmentReturnPeriodBandings;
    }

    public void setAdjustmentReturnPeriodBandings(List<AdjustmentReturnPeriodBandingParameterEntity> adjustmentReturnPeriodBandings) {
        this.adjustmentReturnPeriodBandings = adjustmentReturnPeriodBandings;
    }

    public Boolean getCapped() {
        return capped;
    }

    public void setCapped(Boolean capped) {
        this.capped = capped;
    }
}
