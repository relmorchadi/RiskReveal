package com.scor.rr.domain;

import javax.persistence.*;
import javax.persistence.Entity;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "PLTPricingLossEngine")
public class PltPricingLossEngineEntity {
    private String id;
    private Timestamp lastSynchronized;
    private Integer engineId;
    private String pltPricingId;
    private String pltPricingSectionId;
    private String name;
    private Long nonNatCatLoss;
    private String type;
    private Double pltsFxRate;
    private String pltsRiskRevealId;
    private String franchiseDeductibleType;
    private Integer franchiseDeductibleAmount;
    private Integer capLossesAmount;
    private Timestamp lastUpdateRiskReveal;

    @Id
    @Column(name = "PLTPricingLossEngineId", nullable = false, length = 255)
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
    @Column(name = "engineId", nullable = true)
    public Integer getEngineId() {
        return engineId;
    }

    public void setEngineId(Integer engineId) {
        this.engineId = engineId;
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
    @Column(name = "pltPricingSectionId", nullable = true, length = 255)
    public String getPltPricingSectionId() {
        return pltPricingSectionId;
    }

    public void setPltPricingSectionId(String pltPricingSectionId) {
        this.pltPricingSectionId = pltPricingSectionId;
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
    @Column(name = "nonNatCatLoss", nullable = true)
    public Long getNonNatCatLoss() {
        return nonNatCatLoss;
    }

    public void setNonNatCatLoss(Long nonNatCatLoss) {
        this.nonNatCatLoss = nonNatCatLoss;
    }

    @Basic
    @Column(name = "type", nullable = true, length = 255)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "pltsFxRate", nullable = true, precision = 0)
    public Double getPltsFxRate() {
        return pltsFxRate;
    }

    public void setPltsFxRate(Double pltsFxRate) {
        this.pltsFxRate = pltsFxRate;
    }

    @Basic
    @Column(name = "pltsRiskRevealId", nullable = true, length = 255)
    public String getPltsRiskRevealId() {
        return pltsRiskRevealId;
    }

    public void setPltsRiskRevealId(String pltsRiskRevealId) {
        this.pltsRiskRevealId = pltsRiskRevealId;
    }

    @Basic
    @Column(name = "franchiseDeductibleType", nullable = true, length = 255)
    public String getFranchiseDeductibleType() {
        return franchiseDeductibleType;
    }

    public void setFranchiseDeductibleType(String franchiseDeductibleType) {
        this.franchiseDeductibleType = franchiseDeductibleType;
    }

    @Basic
    @Column(name = "franchiseDeductibleAmount", nullable = true, precision = 0)
    public Integer getFranchiseDeductibleAmount() {
        return franchiseDeductibleAmount;
    }

    public void setFranchiseDeductibleAmount(Integer franchiseDeductibleAmount) {
        this.franchiseDeductibleAmount = franchiseDeductibleAmount;
    }

    @Basic
    @Column(name = "capLossesAmount", nullable = true, precision = 0)
    public Integer getCapLossesAmount() {
        return capLossesAmount;
    }

    public void setCapLossesAmount(Integer capLossesAmount) {
        this.capLossesAmount = capLossesAmount;
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
        PltPricingLossEngineEntity that = (PltPricingLossEngineEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(lastSynchronized, that.lastSynchronized) &&
                Objects.equals(engineId, that.engineId) &&
                Objects.equals(pltPricingId, that.pltPricingId) &&
                Objects.equals(pltPricingSectionId, that.pltPricingSectionId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(nonNatCatLoss, that.nonNatCatLoss) &&
                Objects.equals(type, that.type) &&
                Objects.equals(pltsFxRate, that.pltsFxRate) &&
                Objects.equals(pltsRiskRevealId, that.pltsRiskRevealId) &&
                Objects.equals(franchiseDeductibleType, that.franchiseDeductibleType) &&
                Objects.equals(franchiseDeductibleAmount, that.franchiseDeductibleAmount) &&
                Objects.equals(capLossesAmount, that.capLossesAmount) &&
                Objects.equals(lastUpdateRiskReveal, that.lastUpdateRiskReveal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lastSynchronized, engineId, pltPricingId, pltPricingSectionId, name, nonNatCatLoss, type, pltsFxRate, pltsRiskRevealId, franchiseDeductibleType, franchiseDeductibleAmount, capLossesAmount, lastUpdateRiskReveal);
    }
}
