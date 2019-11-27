package com.scor.rr.entity;

import com.scor.rr.enums.InuringNodeStatus;
import org.apache.commons.lang.StringUtils;

import javax.persistence.*;
import java.util.Objects;

/**
 * Created by u004602 on 10/09/2019.
 */

@Entity
@Table(name = "InuringInputNode")
public class InuringInputNode {
    private long inuringInputNodeId;
    private int entity;
    private long inuringPackageId;
    private InuringNodeStatus inputInuringNodeStatus;
    private String inputNodeName;

    public InuringInputNode(long inuringPackageId) {
        this(inuringPackageId, "Input Node");
    }

    public InuringInputNode(long inuringPackageId, String inputNodeName) {
        this.entity = 1;
        this.inuringPackageId = inuringPackageId;
        this.inputInuringNodeStatus = InuringNodeStatus.Valid;
        this.inputNodeName = !StringUtils.isEmpty(inputNodeName) ? inputNodeName : "Input Node";
    }

    public InuringInputNode() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "InuringInputNodeId", nullable = false)
    public long getInuringInputNodeId() {
        return inuringInputNodeId;
    }

    public void setInuringInputNodeId(long inuringInputNodeId) {
        this.inuringInputNodeId = inuringInputNodeId;
    }

    @Column(name = "RREntity")
    public int getEntity() {
        return entity;
    }

    public void setEntity(int entity) {
        this.entity = entity;
    }

    @Column(name = "InuringPackageId", nullable = false)
    public long getInuringPackageId() {
        return inuringPackageId;
    }

    public void setInuringPackageId(long inuringPackageId) {
        this.inuringPackageId = inuringPackageId;
    }

    @Column(name = "InputNodeStatus", nullable = false)
    public InuringNodeStatus getInputInuringNodeStatus() {
        return inputInuringNodeStatus;
    }

    public void setInputInuringNodeStatus(InuringNodeStatus inputInuringNodeStatus) {
        this.inputInuringNodeStatus = inputInuringNodeStatus;
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
                inputInuringNodeStatus == that.inputInuringNodeStatus &&
                Objects.equals(inputNodeName, that.inputNodeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inuringInputNodeId, entity, inuringPackageId, inputInuringNodeStatus, inputNodeName);
    }
}
