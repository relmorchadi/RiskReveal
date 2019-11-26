package com.scor.rr.domain;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Objects;

@Entity
@Table(name = "FWPublication", schema = "dbo", catalog = "RiskReveal")
public class FwPublicationEntity {
    private int fwPublicationId;
    private Integer pltHeaderId;

    @Id
    @Column(name = "FWPublicationId", nullable = false)
    public int getFwPublicationId() {
        return fwPublicationId;
    }

    public void setFwPublicationId(int fwPublicationId) {
        this.fwPublicationId = fwPublicationId;
    }

    @Basic
    @Column(name = "PLTHeaderId")
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
        FwPublicationEntity that = (FwPublicationEntity) o;
        return fwPublicationId == that.fwPublicationId &&
                Objects.equals(pltHeaderId, that.pltHeaderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fwPublicationId, pltHeaderId);
    }
}
