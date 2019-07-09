package com.scor.rr.domain.dto.adjustement;

public class AdjustmentNodeRequest {

    private int adjustmentNodeId;
    private String layer;
    private Integer sequence;
    private Boolean isInputChanged;
    private String adjustmentParamsSource;
    private String lossNetFlag;
    private Boolean hasNewParamsFile;
    private Integer adjustmentBasis;
    private Integer adjustmentType;
    private Integer adjustmentCategory;
    private Integer adjustmentState;
    private Integer adjustmentThreadId;


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

    public String getAdjustmentParamsSource() {
        return adjustmentParamsSource;
    }

    public void setAdjustmentParamsSource(String adjustmentParamsSource) {
        this.adjustmentParamsSource = adjustmentParamsSource;
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

    public Integer getAdjustmentCategory() {
        return adjustmentCategory;
    }

    public void setAdjustmentCategory(Integer adjustmentCategory) {
        this.adjustmentCategory = adjustmentCategory;
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
}
