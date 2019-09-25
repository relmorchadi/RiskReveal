package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ARCPublication", schema = "dbo", catalog = "RiskReveal")
public class ArcPublicationEntity {
    private int arcPublicationId;
    private Integer scorPltHeaderId;

    @Id
    @Column(name = "arcPublicationId", nullable = false)
    public int getArcPublicationId() {
        return arcPublicationId;
    }

    public void setArcPublicationId(int arcPublicationId) {
        this.arcPublicationId = arcPublicationId;
    }

    @Basic
    @Column(name = "scorPLTHeaderId")
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
        ArcPublicationEntity that = (ArcPublicationEntity) o;
        return arcPublicationId == that.arcPublicationId &&
                Objects.equals(scorPltHeaderId, that.scorPltHeaderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(arcPublicationId, scorPltHeaderId);
    }
}
