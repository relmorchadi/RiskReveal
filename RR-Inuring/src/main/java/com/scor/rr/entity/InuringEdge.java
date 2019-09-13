package com.scor.rr.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by u004602 on 13/09/2019.
 */
@Entity
@Table(name = "InuringEdge", schema = "dbo", catalog = "RiskReveal")
public class InuringEdge {
    private int inuringEdgeId;
    private int entity;
    private int inuringPackageId;
    private int sourceNodeId;
    private InuringNodeType sourceNodeType;
    private int targetNodeId;
    private InuringNodeType targetNodeType;
    private InuringFinancialPerspective outputPerspective;
    private InuringFinancialTreatment financialTreatment;
    private boolean outputAtLayerLevel;

    public InuringEdge(int inuringPackageId, int sourceNodeId, InuringNodeType sourceNodeType, int targetNodeId, InuringNodeType targetNodeType) {
        this(inuringPackageId, sourceNodeId, sourceNodeType, targetNodeId, targetNodeType, InuringFinancialPerspective.Net, InuringFinancialTreatment.Positive);
    }

    public InuringEdge(int inuringPackageId, int sourceNodeId, InuringNodeType sourceNodeType, int targetNodeId, InuringNodeType targetNodeType, InuringFinancialPerspective outputPerspective, InuringFinancialTreatment financialTreatment) {
        this.inuringPackageId = inuringPackageId;
        this.sourceNodeId = sourceNodeId;
        this.sourceNodeType = sourceNodeType;
        this.targetNodeId = targetNodeId;
        this.targetNodeType = targetNodeType;
        this.outputPerspective = outputPerspective;
        this.financialTreatment = financialTreatment;
        this.outputAtLayerLevel = outputAtLayerLevel;
    }

    @Id
    @Column(name = "InuringEdgeId", nullable = false)
    public int getInuringEdgeId() {
        return inuringEdgeId;
    }

    public void setInuringEdgeId(int inuringEdgeId) {
        this.inuringEdgeId = inuringEdgeId;
    }

    @Column(name = "Entity")
    public int getEntity() {
        return entity;
    }

    public void setEntity(int entity) {
        this.entity = entity;
    }

    @Column(name = "InuringPackageId", nullable = false)
    public int getInuringPackageId() {
        return inuringPackageId;
    }

    public void setInuringPackageId(int inuringPackageId) {
        this.inuringPackageId = inuringPackageId;
    }

    @Column(name = "SourceNodeId", nullable = false)
    public int getSourceNodeId() {
        return sourceNodeId;
    }

    public void setSourceNodeId(int sourceNodeId) {
        this.sourceNodeId = sourceNodeId;
    }

    @Column(name = "SourceNodeTyp", nullable = false)
    public InuringNodeType getSourceNodeType() {
        return sourceNodeType;
    }

    public void setSourceNodeType(InuringNodeType sourceNodeType) {
        this.sourceNodeType = sourceNodeType;
    }

    @Column(name = "TargetNodeId", nullable = false)
    public int getTargetNodeId() {
        return targetNodeId;
    }

    public void setTargetNodeId(int targetNodeId) {
        this.targetNodeId = targetNodeId;
    }

    @Column(name = "TargetNodeType", nullable = false)
    public InuringNodeType getTargetNodeType() {
        return targetNodeType;
    }

    public void setTargetNodeType(InuringNodeType targetNodeType) {
        this.targetNodeType = targetNodeType;
    }

    @Column(name = "OutputPerspective", nullable = false)
    public InuringFinancialPerspective getOutputPerspective() {
        return outputPerspective;
    }

    public void setOutputPerspective(InuringFinancialPerspective outputPerspective) {
        this.outputPerspective = outputPerspective;
    }

    @Column(name = "FinancialTreatment", nullable = false)
    public InuringFinancialTreatment getFinancialTreatment() {
        return financialTreatment;
    }

    public void setFinancialTreatment(InuringFinancialTreatment financialTreatment) {
        this.financialTreatment = financialTreatment;
    }

    @Column(name = "OutputAtLayerLevel", nullable = false)
    public boolean isOutputAtLayerLevel() {
        return outputAtLayerLevel;
    }

    public void setOutputAtLayerLevel(boolean outputAtLayerLevel) {
        this.outputAtLayerLevel = outputAtLayerLevel;
    }
}
