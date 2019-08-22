package com.scor.rr.domain.dto.adjustement;

import com.scor.rr.domain.DefaultAdjustmentEntity;
import com.scor.rr.domain.DefaultAdjustmentNodeEntity;
import com.scor.rr.domain.dto.adjustement.loss.AdjustmentReturnPeriodBending;
import com.scor.rr.domain.dto.adjustement.loss.PEATData;

import java.util.List;

public class AdjustmentNodeRequest {

    private int adjustmentNodeId;
    private String layer;
    private Integer sequence;
    private Boolean isInputChanged;
    private String lossNetFlag;
    private Boolean hasNewParamsFile;
    private Integer adjustmentBasis;
    private Integer adjustmentType;
    private Integer adjustmentState;
    private Integer adjustmentThreadId;
    private Double lmf;
    private Double rpmf;
    private List<PEATData> peatData;
    private Integer scorPltHeaderInput;
    private Integer nodeId;
    private List<AdjustmentReturnPeriodBending> adjustmentReturnPeriodBendings;

    public AdjustmentNodeRequest(String layer, Integer sequence, Boolean isInputChanged, String lossNetFlag, Boolean hasNewParamsFile, Integer adjustmentBasis, Integer adjustmentType, Integer adjustmentState, Integer adjustmentThreadId, Double lmf, Double rpmf, List<PEATData> peatData, Integer scorPltHeaderInput, Integer nodeId, List<AdjustmentReturnPeriodBending> adjustmentReturnPeriodBendings) {
        this.layer = layer;
        this.sequence = sequence;
        this.isInputChanged = isInputChanged;
        this.lossNetFlag = lossNetFlag;
        this.hasNewParamsFile = hasNewParamsFile;
        this.adjustmentBasis = adjustmentBasis;
        this.adjustmentType = adjustmentType;
        this.adjustmentState = adjustmentState;
        this.adjustmentThreadId = adjustmentThreadId;
        this.lmf = lmf;
        this.rpmf = rpmf;
        this.peatData = peatData;
        this.scorPltHeaderInput = scorPltHeaderInput;
        this.nodeId = nodeId;
        this.adjustmentReturnPeriodBendings = adjustmentReturnPeriodBendings;
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

    public Boolean getInputChanged() {
        return isInputChanged;
    }

    public void setInputChanged(Boolean inputChanged) {
        isInputChanged = inputChanged;
    }

    public String getLossNetFlag() {
        return lossNetFlag;
    }

    public void setLossNetFlag(String lossNetFlag) {
        this.lossNetFlag = lossNetFlag;
    }

    public Boolean getHasNewParamsFile() {
        return hasNewParamsFile;
    }

    public void setHasNewParamsFile(Boolean hasNewParamsFile) {
        this.hasNewParamsFile = hasNewParamsFile;
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
