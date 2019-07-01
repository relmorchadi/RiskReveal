package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "AdjustmentNode", schema = "dbo", catalog = "RiskReveal")
public class AdjustmentNodeEntity {
    private int adjustmentNodeId;
    private String layer;
    private Integer sequence;
    private String adjustmentType;
    private String adjustmentBasis;
    private String status;
    private String adjustementStructureMode;
    private String adjustementCategory;
    private Boolean isInputChanged;
    private String adjustmentParamsSource;
    private double adjustmentParam;
    private String lossNetFlag;
    private Boolean hasNewParamsFile;
    private AdjustmentNodeEntity adjustmentNodeByAdjustmentNodeId;
    private AdjustmentNodeEntity adjustmentNodeByAdjustmentNodeId_0;
    private AdjustmentBasisEntity adjustmentBasisByFkAdjustmentBasis;
    private AdjustmentNodeEntity adjustmentNodeByFkAdjustmentNodeEntityChildren;
    private AdjustmentNodeEntity adjustmentNodeByFkAdjustmentNodeEntityPure;
    private AdjustmentNodeEntity adjustmentNodeByFkAdjustmentNodeEntityCloning;

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
    @Column(name = "adjustmentType", nullable = true, length = 255)
    public String getAdjustmentType() {
        return adjustmentType;
    }

    public void setAdjustmentType(String adjustmentType) {
        this.adjustmentType = adjustmentType;
    }

    @Basic
    @Column(name = "adjustmentBasis", nullable = true, length = 255)
    public String getAdjustmentBasis() {
        return adjustmentBasis;
    }

    public void setAdjustmentBasis(String adjustmentBasis) {
        this.adjustmentBasis = adjustmentBasis;
    }

    @Basic
    @Column(name = "status", nullable = true, length = 255)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "adjustementStructureMode", nullable = true, length = 255)
    public String getAdjustementStructureMode() {
        return adjustementStructureMode;
    }

    public void setAdjustementStructureMode(String adjustementStructureMode) {
        this.adjustementStructureMode = adjustementStructureMode;
    }

    @Basic
    @Column(name = "adjustementCategory", nullable = true, length = 255)
    public String getAdjustementCategory() {
        return adjustementCategory;
    }

    public void setAdjustementCategory(String adjustementCategory) {
        this.adjustementCategory = adjustementCategory;
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
    @Column(name = "adjustmentParam", nullable = true, precision = 0)
    public double getAdjustmentParam() {
        return adjustmentParam;
    }

    public void setAdjustmentParam(double adjustmentParam) {
        this.adjustmentParam = adjustmentParam;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdjustmentNodeEntity that = (AdjustmentNodeEntity) o;
        return adjustmentNodeId == that.adjustmentNodeId &&
                Objects.equals(layer, that.layer) &&
                Objects.equals(sequence, that.sequence) &&
                Objects.equals(adjustmentType, that.adjustmentType) &&
                Objects.equals(adjustmentBasis, that.adjustmentBasis) &&
                Objects.equals(status, that.status) &&
                Objects.equals(adjustementStructureMode, that.adjustementStructureMode) &&
                Objects.equals(adjustementCategory, that.adjustementCategory) &&
                Objects.equals(isInputChanged, that.isInputChanged) &&
                Objects.equals(adjustmentParamsSource, that.adjustmentParamsSource) &&
                Objects.equals(adjustmentParam, that.adjustmentParam) &&
                Objects.equals(lossNetFlag, that.lossNetFlag) &&
                Objects.equals(hasNewParamsFile, that.hasNewParamsFile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(adjustmentNodeId, layer, sequence, adjustmentType, adjustmentBasis, status, adjustementStructureMode, adjustementCategory, isInputChanged, adjustmentParamsSource, adjustmentParam, lossNetFlag, hasNewParamsFile);
    }

    @OneToOne
    @JoinColumn(name = "adjustmentNodeId", referencedColumnName = "adjustmentNodeId", nullable = false)
    public AdjustmentNodeEntity getAdjustmentNodeByAdjustmentNodeId() {
        return adjustmentNodeByAdjustmentNodeId;
    }

    public void setAdjustmentNodeByAdjustmentNodeId(AdjustmentNodeEntity adjustmentNodeByAdjustmentNodeId) {
        this.adjustmentNodeByAdjustmentNodeId = adjustmentNodeByAdjustmentNodeId;
    }

    @OneToOne(mappedBy = "adjustmentNodeByAdjustmentNodeId")
    public AdjustmentNodeEntity getAdjustmentNodeByAdjustmentNodeId_0() {
        return adjustmentNodeByAdjustmentNodeId_0;
    }

    public void setAdjustmentNodeByAdjustmentNodeId_0(AdjustmentNodeEntity adjustmentNodeByAdjustmentNodeId_0) {
        this.adjustmentNodeByAdjustmentNodeId_0 = adjustmentNodeByAdjustmentNodeId_0;
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
    @JoinColumn(name = "fk_adjustmentNodeEntityChildren", referencedColumnName = "adjustmentNodeId")
    public AdjustmentNodeEntity getAdjustmentNodeByFkAdjustmentNodeEntityChildren() {
        return adjustmentNodeByFkAdjustmentNodeEntityChildren;
    }

    public void setAdjustmentNodeByFkAdjustmentNodeEntityChildren(AdjustmentNodeEntity adjustmentNodeByFkAdjustmentNodeEntityChildren) {
        this.adjustmentNodeByFkAdjustmentNodeEntityChildren = adjustmentNodeByFkAdjustmentNodeEntityChildren;
    }

    @ManyToOne
    @JoinColumn(name = "fk_adjustmentNodeEntityPure", referencedColumnName = "adjustmentNodeId")
    public AdjustmentNodeEntity getAdjustmentNodeByFkAdjustmentNodeEntityPure() {
        return adjustmentNodeByFkAdjustmentNodeEntityPure;
    }

    public void setAdjustmentNodeByFkAdjustmentNodeEntityPure(AdjustmentNodeEntity adjustmentNodeByFkAdjustmentNodeEntityPure) {
        this.adjustmentNodeByFkAdjustmentNodeEntityPure = adjustmentNodeByFkAdjustmentNodeEntityPure;
    }

    @ManyToOne
    @JoinColumn(name = "fk_adjustmentNodeEntityCloning", referencedColumnName = "adjustmentNodeId")
    public AdjustmentNodeEntity getAdjustmentNodeByFkAdjustmentNodeEntityCloning() {
        return adjustmentNodeByFkAdjustmentNodeEntityCloning;
    }

    public void setAdjustmentNodeByFkAdjustmentNodeEntityCloning(AdjustmentNodeEntity adjustmentNodeByFkAdjustmentNodeEntityCloning) {
        this.adjustmentNodeByFkAdjustmentNodeEntityCloning = adjustmentNodeByFkAdjustmentNodeEntityCloning;
    }

}
