package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "FWPublication", schema = "dbo", catalog = "RiskReveal")
public class FwPublicationEntity {
    private int fwPublicationId;
    private Integer scorPltHeaderId;

    @Id
    @Column(name = "fwPublicationId", nullable = false)
    public int getFwPublicationId() {
        return fwPublicationId;
    }

    public void setFwPublicationId(int fwPublicationId) {
        this.fwPublicationId = fwPublicationId;
    }

    @Basic
    @Column(name = "ScorPLTHeaderId")
    public Integer getScorPltHeaderId() {
        return scorPltHeaderId;
    }

    public void setScorPltHeaderId(Integer scorPltHeaderId) {
        this.scorPltHeaderId = scorPltHeaderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FwPublicationEntity that = (FwPublicationEntity) o;
        return fwPublicationId == that.fwPublicationId &&
                Objects.equals(scorPltHeaderId, that.scorPltHeaderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fwPublicationId, scorPltHeaderId);
    }
}
