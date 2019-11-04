package com.scor.rr.entity;

import com.scor.rr.enums.InuringNodeStatus;
import com.scor.rr.enums.InuringOutputGrain;

import javax.persistence.*;
import java.util.Objects;

/**
 * Created by u004602 on 16/09/2019.
 */
@Entity
@Table(name = "InuringFinalNode", schema = "dbo", catalog = "RiskReveal")
public class InuringFinalNode {
    private int inuringFinalNodeId;
    private int entity;
    private int inuringPackageId;
    private InuringNodeStatus finalNodeStatus;
    private InuringOutputGrain  inuringOutputGrain;

    public InuringFinalNode(int inuringPackageId) {
        this.entity = 1;
        this.inuringPackageId = inuringPackageId;
        this.finalNodeStatus = InuringNodeStatus.Invalid;
        this.inuringOutputGrain = InuringOutputGrain.MinimunRegionPeril;
    }

    public InuringFinalNode(){
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "InuringFinalNodeId", nullable = false)
    public int getInuringFinalNodeId() {
        return inuringFinalNodeId;
    }

    public void setInuringFinalNodeId(int inuringFinalNodeId) {
        this.inuringFinalNodeId = inuringFinalNodeId;
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

    @Column(name = "FinalNodeStatus", nullable = false)
    public InuringNodeStatus getFinalNodeStatus() {
        return finalNodeStatus;
    }

    public void setFinalNodeStatus(InuringNodeStatus finalNodeStatus) {
        this.finalNodeStatus = finalNodeStatus;
    }

    @Column(name = "InuringOutputGrain", nullable = false)
    public InuringOutputGrain getInuringOutputGrain() {
        return inuringOutputGrain;
    }

    public void setInuringOutputGrain(InuringOutputGrain inuringOutputGrain) {
        this.inuringOutputGrain = inuringOutputGrain;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InuringFinalNode that = (InuringFinalNode) o;
        return inuringFinalNodeId == that.inuringFinalNodeId &&
                entity == that.entity &&
                inuringPackageId == that.inuringPackageId &&
                finalNodeStatus == that.finalNodeStatus &&
                inuringOutputGrain == that.inuringOutputGrain;
    }

    @Override
    public int hashCode() {
        return Objects.hash(inuringFinalNodeId, entity, inuringPackageId, finalNodeStatus, inuringOutputGrain);
    }
}
