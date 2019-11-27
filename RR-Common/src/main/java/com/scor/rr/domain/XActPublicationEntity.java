package com.scor.rr.domain;

import javax.persistence.*;
import javax.persistence.Entity;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "XActPublication")
public class XActPublicationEntity {
    private int xactPublicationId;
    private Integer scorPltHeaderId;
    private Boolean xActAvailable;
    private Boolean xActUsed;
    private Timestamp xActPublicationDate;

    @Id
    @Column(name = "xactPublicationId", nullable = false)
    public int getXactPublicationId() {
        return xactPublicationId;
    }

    public void setXactPublicationId(int xactPublicationId) {
        this.xactPublicationId = xactPublicationId;
    }

    @Basic
    @Column(name = "scorPLTHeaderId")
    public Integer getScorPltHeaderId() {
        return scorPltHeaderId;
    }

    public void setScorPltHeaderId(Integer scorPltHeaderId) {
        this.scorPltHeaderId = scorPltHeaderId;
    }

    @Basic
    @Column(name = "xActAvailable")
    public Boolean getxActAvailable() {
        return xActAvailable;
    }

    public void setxActAvailable(Boolean xActAvailable) {
        this.xActAvailable = xActAvailable;
    }

    @Basic
    @Column(name = "xActUsed")
    public Boolean getxActUsed() {
        return xActUsed;
    }

    public void setxActUsed(Boolean xActUsed) {
        this.xActUsed = xActUsed;
    }

    @Basic
    @Column(name = "xActPublicationDate")
    public Timestamp getxActPublicationDate() {
        return xActPublicationDate;
    }

    public void setxActPublicationDate(Timestamp xActPublicationDate) {
        this.xActPublicationDate = xActPublicationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        XActPublicationEntity that = (XActPublicationEntity) o;
        return xactPublicationId == that.xactPublicationId &&
                Objects.equals(scorPltHeaderId, that.scorPltHeaderId) &&
                Objects.equals(xActAvailable, that.xActAvailable) &&
                Objects.equals(xActUsed, that.xActUsed) &&
                Objects.equals(xActPublicationDate, that.xActPublicationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(xactPublicationId, scorPltHeaderId, xActAvailable, xActUsed, xActPublicationDate);
    }
}
