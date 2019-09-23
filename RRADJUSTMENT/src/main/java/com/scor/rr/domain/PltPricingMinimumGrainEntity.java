package com.scor.rr.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "PLTPricingMinimumGrain", schema = "dbo", catalog = "RiskReveal")
public class PltPricingMinimumGrainEntity {
    private String id;
    private Timestamp lastSynchronized;
    private String engineId;
    private String pltPricingId;
    private String pltPricingSectionId;
    private String code;
    private String expectedLoss;
    private Timestamp lastUpdateRiskReveal;

    @Id
    @Column(name = "PLTPricingMinimumGrainId", nullable = false, length = 255)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "lastSynchronized")
    public Timestamp getLastSynchronized() {
        return lastSynchronized;
    }

    public void setLastSynchronized(Timestamp lastSynchronized) {
        this.lastSynchronized = lastSynchronized;
    }

    @Basic
    @Column(name = "engineId", length = 255)
    public String getEngineId() {
        return engineId;
    }

    public void setEngineId(String engineId) {
        this.engineId = engineId;
    }

    @Basic
    @Column(name = "PltPricingId", length = 255)
    public String getPltPricingId() {
        return pltPricingId;
    }

    public void setPltPricingId(String pltPricingId) {
        this.pltPricingId = pltPricingId;
    }

    @Basic
    @Column(name = "PltPricingSectionId", length = 255)
    public String getPltPricingSectionId() {
        return pltPricingSectionId;
    }

    public void setPltPricingSectionId(String pltPricingSectionId) {
        this.pltPricingSectionId = pltPricingSectionId;
    }

    @Basic
    @Column(name = "code", length = 255)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "expectedLoss", length = 255)
    public String getExpectedLoss() {
        return expectedLoss;
    }

    public void setExpectedLoss(String expectedLoss) {
        this.expectedLoss = expectedLoss;
    }

    @Basic
    @Column(name = "lastUpdateRiskReveal")
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
        PltPricingMinimumGrainEntity that = (PltPricingMinimumGrainEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(lastSynchronized, that.lastSynchronized) &&
                Objects.equals(engineId, that.engineId) &&
                Objects.equals(pltPricingId, that.pltPricingId) &&
                Objects.equals(pltPricingSectionId, that.pltPricingSectionId) &&
                Objects.equals(code, that.code) &&
                Objects.equals(expectedLoss, that.expectedLoss) &&
                Objects.equals(lastUpdateRiskReveal, that.lastUpdateRiskReveal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lastSynchronized, engineId, pltPricingId, pltPricingSectionId, code, expectedLoss, lastUpdateRiskReveal);
    }
}
