package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "DefaultAdjustmentNode", schema = "dbo", catalog = "RiskReveal")
public class DefaultAdjustmentNodeEntity {
    private int adjustmentNodeId;
    private String layer;
    private Integer sequence;
    private Boolean isInputChanged;
    private String adjustmentParamsSource;
    private String lossNetFlag;
    private Boolean hasNewParamsFile;
    private AdjustmentBasisEntity adjustmentBasisByFkAdjustmentBasis;
    private AdjustmentTypeEntity adjustmentTypeByAdjustmentType;
    private AdjustmentCategoryEntity adjustmentCategoryByIdCategory;
    private AdjustmentStateEntity adjustmentStateByIdState;
    private DefaultAdjustmentThreadEntity defaultAdjustmentThreadByIdAdjustmentThread;

    @Id
    @Column(name = "adjustmentNodeId", nullable = false)
    public int getAdjustmentNodeId() {
        return adjustmentNodeId;
    }

    public void setAdjustmentNodeId(int adjustmentNodeId) {
        this.adjustmentNodeId = adjustmentNodeId;
    }

    @Basic
    @Column(name = "layer", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getLayer() {
        return layer;
    }

    public void setLayer(String layer) {
        this.layer = layer;
    }

    @Basic
    @Column(name = "sequence", nullable = true)
    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    @Basic
    @Column(name = "isInputChanged", nullable = true)
    public Boolean getInputChanged() {
        return isInputChanged;
    }

    public void setInputChanged(Boolean inputChanged) {
        isInputChanged = inputChanged;
    }

    @Basic
    @Column(name = "adjustmentParamsSource", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getAdjustmentParamsSource() {
        return adjustmentParamsSource;
    }

    public void setAdjustmentParamsSource(String adjustmentParamsSource) {
        this.adjustmentParamsSource = adjustmentParamsSource;
    }

    @Basic
    @Column(name = "lossNetFlag", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getLossNetFlag() {
        return lossNetFlag;
    }

    public void setLossNetFlag(String lossNetFlag) {
        this.lossNetFlag = lossNetFlag;
    }

    @Basic
    @Column(name = "hasNewParamsFile", nullable = true)
    public Boolean getHasNewParamsFile() {
        return hasNewParamsFile;
    }

    public void setHasNewParamsFile(Boolean hasNewParamsFile) {
        this.hasNewParamsFile = hasNewParamsFile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DefaultAdjustmentNodeEntity that = (DefaultAdjustmentNodeEntity) o;
        return adjustmentNodeId == that.adjustmentNodeId &&
                Objects.equals(layer, that.layer) &&
                Objects.equals(sequence, that.sequence) &&
                Objects.equals(isInputChanged, that.isInputChanged) &&
                Objects.equals(adjustmentParamsSource, that.adjustmentParamsSource) &&
                Objects.equals(lossNetFlag, that.lossNetFlag) &&
                Objects.equals(hasNewParamsFile, that.hasNewParamsFile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(adjustmentNodeId, layer, sequence, isInputChanged, adjustmentParamsSource, lossNetFlag, hasNewParamsFile);
    }

    @ManyToOne
    @JoinColumn(name = "fk_adjustment_basis", referencedColumnName = "code")
    public AdjustmentBasisEntity getAdjustmentBasisByFkAdjustmentBasis() {
        return adjustmentBasisByFkAdjustmentBasis;
    }

    public void setAdjustmentBasisByFkAdjustmentBasis(AdjustmentBasisEntity adjustmentBasisByFkAdjustmentBasis) {
        this.adjustmentBasisByFkAdjustmentBasis = adjustmentBasisByFkAdjustmentBasis;
    }

    @ManyToOne
    @JoinColumn(name = "adjustment_type", referencedColumnName = "id_type")
    public AdjustmentTypeEntity getAdjustmentTypeByAdjustmentType() {
        return adjustmentTypeByAdjustmentType;
    }

    public void setAdjustmentTypeByAdjustmentType(AdjustmentTypeEntity adjustmentTypeByAdjustmentType) {
        this.adjustmentTypeByAdjustmentType = adjustmentTypeByAdjustmentType;
    }

    @ManyToOne
    @JoinColumn(name = "id_category", referencedColumnName = "id_category")
    public AdjustmentCategoryEntity getAdjustmentCategoryByIdCategory() {
        return adjustmentCategoryByIdCategory;
    }

    public void setAdjustmentCategoryByIdCategory(AdjustmentCategoryEntity adjustmentCategoryByIdCategory) {
        this.adjustmentCategoryByIdCategory = adjustmentCategoryByIdCategory;
    }

    @ManyToOne
    @JoinColumn(name = "id_state", referencedColumnName = "id_state")
    public AdjustmentStateEntity getAdjustmentStateByIdState() {
        return adjustmentStateByIdState;
    }

    public void setAdjustmentStateByIdState(AdjustmentStateEntity adjustmentStateByIdState) {
        this.adjustmentStateByIdState = adjustmentStateByIdState;
    }

    @ManyToOne
    @JoinColumn(name = "id_adjustment_thread", referencedColumnName = "id")
    public DefaultAdjustmentThreadEntity getDefaultAdjustmentThreadByIdAdjustmentThread() {
        return defaultAdjustmentThreadByIdAdjustmentThread;
    }

    public void setDefaultAdjustmentThreadByIdAdjustmentThread(DefaultAdjustmentThreadEntity defaultAdjustmentThreadByIdAdjustmentThread) {
        this.defaultAdjustmentThreadByIdAdjustmentThread = defaultAdjustmentThreadByIdAdjustmentThread;
    }
}
