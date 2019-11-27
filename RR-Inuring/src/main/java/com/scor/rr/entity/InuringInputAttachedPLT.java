package com.scor.rr.entity;

import javax.persistence.*;

/**
 * Created by u004602 on 10/09/2019.
 */
@Entity
@Table(name = "InuringInputAttachedPLT")
public class InuringInputAttachedPLT {
    private long inuringInputAttachedPLTId;
    private int entity;
    private long inuringInputNodeId;
    private int pltHeaderId;

    public InuringInputAttachedPLT(long inuringInputNodeId, int pltHeaderId) {
        this.entity = 1;
        this.inuringInputNodeId = inuringInputNodeId;
        this.pltHeaderId = pltHeaderId;
    }

    public InuringInputAttachedPLT() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "InuringInputAttachedPLTId", nullable = false)
    public long getInuringInputAttachedPLTId() {
        return inuringInputAttachedPLTId;
    }

    public void setInuringInputAttachedPLTId(long inuringInputAttachedPLTId) {
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
    public long getInuringInputNodeId() {
        return inuringInputNodeId;
    }

    public void setInuringInputNodeId(long inuringInputNodeId) {
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
