package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ARCPublication", schema = "dbo", catalog = "RiskReveal")
public class ArcPublicationEntity {
    private int arcPublicationId;
    private Integer pltHeaderId;

    @Id
    @Column(name = "ARCPublicationId", nullable = false)
    public int getArcPublicationId() {
        return arcPublicationId;
    }

    public void setArcPublicationId(int arcPublicationId) {
        this.arcPublicationId = arcPublicationId;
    }

    @Basic
    @Column(name = "PLTHeaderId", nullable = true)
    public Integer getPltHeaderId() {
        return pltHeaderId;
    }

    public void setPltHeaderId(Integer pltHeaderId) {
        this.pltHeaderId = pltHeaderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArcPublicationEntity that = (ArcPublicationEntity) o;
        return arcPublicationId == that.arcPublicationId &&
                Objects.equals(pltHeaderId, that.pltHeaderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(arcPublicationId, pltHeaderId);
    }
}
