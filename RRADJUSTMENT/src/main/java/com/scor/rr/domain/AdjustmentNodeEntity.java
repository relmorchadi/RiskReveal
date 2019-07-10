package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "AdjustmentNode", schema = "dbo", catalog = "RiskReveal")
public class AdjustmentNodeEntity {
    private int adjustmentNodeId;
    private String layer;
    private Integer sequence;
    private Boolean isInputChanged;
    private String adjustmentParamsSource;
    private String lossNetFlag;
    private Boolean hasNewParamsFile;
    private Boolean capped;
    private AdjustmentThreadEntity adjustmentThread;
    private AdjustmentBasisEntity adjustmentBasis;
    private AdjustmentNodeEntity adjustmentNodeByFkAdjustmentNodeEntityCloning;
    private AdjustmentTypeEntity adjustmentType;
    private AdjustmentStateEntity adjustmentState;

    @Id
    @Column(name = "adjustmentNodeId", nullable = false)
    public int getAdjustmentNodeId() {
        return adjustmentNodeId;
    }

    public void setAdjustmentNodeId(int adjustmentNodeId) {
        this.adjustmentNodeId = adjustmentNodeId;
    }

    @Basic
    @Column(name = "layer", nullable = true, length = 255)
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
    @Column(name = "adjustmentParamsSource", nullable = true, length = 255)
    public String getAdjustmentParamsSource() {
        return adjustmentParamsSource;
    }

    public void setAdjustmentParamsSource(String adjustmentParamsSource) {
        this.adjustmentParamsSource = adjustmentParamsSource;
    }

    @Basic
    @Column(name = "lossNetFlag", nullable = true, length = 255)
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

    @Basic
    @Column(name = "capped", nullable = true)
    public Boolean getCapped() {
        return capped;
    }

    public void setCapped(Boolean capped) {
        this.capped = capped;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdjustmentNodeEntity that = (AdjustmentNodeEntity) o;
        return adjustmentNodeId == that.adjustmentNodeId &&
                Objects.equals(layer, that.layer) &&
                Objects.equals(sequence, that.sequence) &&
                Objects.equals(isInputChanged, that.isInputChanged) &&
                Objects.equals(adjustmentParamsSource, that.adjustmentParamsSource) &&
                Objects.equals(lossNetFlag, that.lossNetFlag) &&
                Objects.equals(hasNewParamsFile, that.hasNewParamsFile) &&
                Objects.equals(capped,that.capped);
    }

    @Override
    public int hashCode() {
        return Objects.hash(adjustmentNodeId, layer, sequence, isInputChanged, adjustmentParamsSource, lossNetFlag, hasNewParamsFile,capped);
    }

    @ManyToOne
    @JoinColumn(name = "adjustmentThreadId", referencedColumnName = "adjustmentThreadId")
    public AdjustmentThreadEntity getAdjustmentThread() {
        return adjustmentThread;
    }

    public void setAdjustmentThread(AdjustmentThreadEntity adjustmentThread) {
        this.adjustmentThread = adjustmentThread;
    }

    @ManyToOne
    @JoinColumn(name = "fk_adjustment_basis", referencedColumnName = "code")
    public AdjustmentBasisEntity getAdjustmentBasis() {
        return adjustmentBasis;
    }

    public void setAdjustmentBasis(AdjustmentBasisEntity adjustmentBasis) {
        this.adjustmentBasis = adjustmentBasis;
    }

    @ManyToOne
    @JoinColumn(name = "fk_adjustmentNodeEntityCloning", referencedColumnName = "adjustmentNodeId")
    public AdjustmentNodeEntity getAdjustmentNodeByFkAdjustmentNodeEntityCloning() {
        return adjustmentNodeByFkAdjustmentNodeEntityCloning;
    }

    public void setAdjustmentNodeByFkAdjustmentNodeEntityCloning(AdjustmentNodeEntity adjustmentNodeByFkAdjustmentNodeEntityCloning) {
        this.adjustmentNodeByFkAdjustmentNodeEntityCloning = adjustmentNodeByFkAdjustmentNodeEntityCloning;
    }

    @ManyToOne
    @JoinColumn(name = "adjustment_type", referencedColumnName = "id_type")
    public AdjustmentTypeEntity getAdjustmentType() {
        return adjustmentType;
    }

    public void setAdjustmentType(AdjustmentTypeEntity adjustmentType) {
        this.adjustmentType = adjustmentType;
    }

    @ManyToOne
    @JoinColumn(name = "id_state", referencedColumnName = "id_state")
    public AdjustmentStateEntity getAdjustmentState() {
        return adjustmentState;
    }

    public void setAdjustmentState(AdjustmentStateEntity adjustmentState) {
        this.adjustmentState = adjustmentState;
    }
}
