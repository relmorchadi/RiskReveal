package com.scor.rr.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "PLTPricingSection", schema = "dbo", catalog = "RiskReveal")
public class PltPricingSectionEntity {
    private String id;
    private Timestamp lastSynchronized;
    private String pltPricingSectionId;
    private String pltPricingId;
    private String currency;
    private String name;
    private String omegaTreatyNo;
    private Integer omegaSectionNo;
    private Timestamp lastUpdateRiskReveal;

    @Id
    @Column(name = "id", nullable = false, length = 255)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "lastSynchronized", nullable = true)
    public Timestamp getLastSynchronized() {
        return lastSynchronized;
    }

    public void setLastSynchronized(Timestamp lastSynchronized) {
        this.lastSynchronized = lastSynchronized;
    }

    @Basic
    @Column(name = "pltPricingSectionId", nullable = true, length = 255)
    public String getPltPricingSectionId() {
        return pltPricingSectionId;
    }

    public void setPltPricingSectionId(String pltPricingSectionId) {
        this.pltPricingSectionId = pltPricingSectionId;
    }

    @Basic
    @Column(name = "pltPricingId", nullable = true, length = 255)
    public String getPltPricingId() {
        return pltPricingId;
    }

    public void setPltPricingId(String pltPricingId) {
        this.pltPricingId = pltPricingId;
    }

    @Basic
    @Column(name = "currency", nullable = true, length = 255)
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Basic
    @Column(name = "name", nullable = true, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "omegaTreatyNo", nullable = true, length = 255)
    public String getOmegaTreatyNo() {
        return omegaTreatyNo;
    }

    public void setOmegaTreatyNo(String omegaTreatyNo) {
        this.omegaTreatyNo = omegaTreatyNo;
    }

    @Basic
    @Column(name = "omegaSectionNo", nullable = true)
    public Integer getOmegaSectionNo() {
        return omegaSectionNo;
    }

    public void setOmegaSectionNo(Integer omegaSectionNo) {
        this.omegaSectionNo = omegaSectionNo;
    }

    @Basic
    @Column(name = "lastUpdateRiskReveal", nullable = true)
    public Timestamp getLastUpdateRiskReveal() {
        return lastUpdateRiskReveal;
    }

    public void setLastUpdateRiskReveal(Timestamp lastUpdateRiskReveal) {
        this.lastUpdateRiskReveal = lastUpdateRiskReveal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PltPricingSectionEntity that = (PltPricingSectionEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(lastSynchronized, that.lastSynchronized) &&
                Objects.equals(pltPricingSectionId, that.pltPricingSectionId) &&
                Objects.equals(pltPricingId, that.pltPricingId) &&
                Objects.equals(currency, that.currency) &&
                Objects.equals(name, that.name) &&
                Objects.equals(omegaTreatyNo, that.omegaTreatyNo) &&
                Objects.equals(omegaSectionNo, that.omegaSectionNo) &&
                Objects.equals(lastUpdateRiskReveal, that.lastUpdateRiskReveal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lastSynchronized, pltPricingSectionId, pltPricingId, currency, name, omegaTreatyNo, omegaSectionNo, lastUpdateRiskReveal);
    }
}
