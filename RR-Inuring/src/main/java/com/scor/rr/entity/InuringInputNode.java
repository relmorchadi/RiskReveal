package com.scor.rr.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by u004602 on 10/09/2019.
 */

@Entity
@Table(name = "InuringInputNode", schema = "dbo", catalog = "RiskReveal")
public class InuringInputNode {
    private int inuringInputNodeId;
    private int entity;
    private int inuringPackageId;
    private NodeStatus inputNodeStatus;
    private String inputNodeName;

    @Id
    @Column(name = "InuringInputNodeId", nullable = false)
    public int getInuringInputNodeId() {
        return inuringInputNodeId;
    }

    public void setInuringInputNodeId(int inuringInputNodeId) {
        this.inuringInputNodeId = inuringInputNodeId;
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

    @Column(name = "InputNodeStatus", nullable = false)
    public NodeStatus getInputNodeStatus() {
        return inputNodeStatus;
    }

    public void setInputNodeStatus(NodeStatus inputNodeStatus) {
        this.inputNodeStatus = inputNodeStatus;
    }

    @Column(name = "InputNodeName", nullable = false)
    public String getInputNodeName() {
        return inputNodeName;
    }

    public void setInputNodeName(String inputNodeName) {
        this.inputNodeName = inputNodeName;
    }
}
