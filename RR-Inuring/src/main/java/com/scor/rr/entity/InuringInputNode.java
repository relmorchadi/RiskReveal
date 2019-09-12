package com.scor.rr.entity;

import org.apache.commons.lang.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

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

    public InuringInputNode(int inuringPackageId) {
        this(inuringPackageId, "Input Node");
    }

    public InuringInputNode(int inuringPackageId, String inputNodeName) {
        this.inuringPackageId = inuringPackageId;
        this.inputNodeStatus = NodeStatus.Valid;
        this.inputNodeName = ! StringUtils.isEmpty(inputNodeName) ? inputNodeName : "Input Node";
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InuringInputNode that = (InuringInputNode) o;
        return inuringInputNodeId == that.inuringInputNodeId &&
                entity == that.entity &&
                inuringPackageId == that.inuringPackageId &&
                inputNodeStatus == that.inputNodeStatus &&
                Objects.equals(inputNodeName, that.inputNodeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inuringInputNodeId, entity, inuringPackageId, inputNodeStatus, inputNodeName);
    }
}
