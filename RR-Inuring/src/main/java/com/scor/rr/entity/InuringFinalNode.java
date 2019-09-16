package com.scor.rr.entity;

import com.scor.rr.enums.InuringNodeStatus;
import com.scor.rr.enums.InuringOutputGrain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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

    @Id
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
}
