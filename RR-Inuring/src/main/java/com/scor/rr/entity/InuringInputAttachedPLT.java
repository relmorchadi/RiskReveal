package com.scor.rr.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by u004602 on 10/09/2019.
 */
@Entity
@Table(name = "InuringInputAttachedPLT", schema = "dbo", catalog = "RiskReveal")
public class InuringInputAttachedPLT {
    private int inuringInputAttachedPLTId;
    private int entity;
    private int inuringInputNodeId;
    private int pltHeaderId;

    public InuringInputAttachedPLT(int inuringInputNodeId, int pltHeaderId) {
        this.inuringInputNodeId = inuringInputNodeId;
        this.pltHeaderId = pltHeaderId;
    }

    @Id
    @Column(name = "InuringInputAttachedPLTId", nullable = false)
    public int getInuringInputAttachedPLTId() {
        return inuringInputAttachedPLTId;
    }

    public void setInuringInputAttachedPLTId(int inuringInputAttachedPLTId) {
        this.inuringInputAttachedPLTId = inuringInputAttachedPLTId;
    }

    @Column(name = "Entity")
    public int getEntity() {
        return entity;
    }

    public void setEntity(int entity) {
        this.entity = entity;
    }

    @Column(name = "InuringInputNodeId", nullable = false)
    public int getInuringInputNodeId() {
        return inuringInputNodeId;
    }

    public void setInuringInputNodeId(int inuringInputNodeId) {
        this.inuringInputNodeId = inuringInputNodeId;
    }

    @Column(name = "PltHeaderId", nullable = false)
    public int getPltHeaderId() {
        return pltHeaderId;
    }

    public void setPltHeaderId(int pltHeaderId) {
        this.pltHeaderId = pltHeaderId;
    }
}
