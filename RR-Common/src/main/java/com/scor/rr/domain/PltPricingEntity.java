package com.scor.rr.domain;

import javax.persistence.*;
import javax.persistence.Entity;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "PLTPricing")
public class PltPricingEntity {
    private String pltPricingId;
    private Timestamp lastSynchronized;
    private Integer companyCode;
    private String countryIsoCode;
    private String importedOmegaId;
    private String name;
    private String pricingStructure;
    private String status;
    private Integer uwYear;
    private Timestamp lastUpdateRiskReveal;

    @Id
    @Column(name = "pltPricingId", nullable = false, length = 255)
    public String getPltPricingId() {
        return pltPricingId;
    }

    public void setPltPricingId(String pltPricingId) {
        this.pltPricingId = pltPricingId;
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
    @Column(name = "companyCode", nullable = true)
    public Integer getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(Integer companyCode) {
        this.companyCode = companyCode;
    }

    @Basic
    @Column(name = "countryIsoCode", nullable = true, length = 255)
    public String getCountryIsoCode() {
        return countryIsoCode;
    }

    public void setCountryIsoCode(String countryIsoCode) {
        this.countryIsoCode = countryIsoCode;
    }

    @Basic
    @Column(name = "importedOmegaId", nullable = true, length = 255)
    public String getImportedOmegaId() {
        return importedOmegaId;
    }

    public void setImportedOmegaId(String importedOmegaId) {
        this.importedOmegaId = importedOmegaId;
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
    @Column(name = "pricingStructure", nullable = true, length = 255)
    public String getPricingStructure() {
        return pricingStructure;
    }

    public void setPricingStructure(String pricingStructure) {
        this.pricingStructure = pricingStructure;
    }

    @Basic
    @Column(name = "status", nullable = true, length = 255)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "uwYear", nullable = true)
    public Integer getUwYear() {
        return uwYear;
    }

    public void setUwYear(Integer uwYear) {
        this.uwYear = uwYear;
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
        PltPricingEntity that = (PltPricingEntity) o;
        return Objects.equals(pltPricingId, that.pltPricingId) &&
                Objects.equals(lastSynchronized, that.lastSynchronized) &&
                Objects.equals(companyCode, that.companyCode) &&
                Objects.equals(countryIsoCode, that.countryIsoCode) &&
                Objects.equals(importedOmegaId, that.importedOmegaId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(pricingStructure, that.pricingStructure) &&
                Objects.equals(status, that.status) &&
                Objects.equals(uwYear, that.uwYear) &&
                Objects.equals(lastUpdateRiskReveal, that.lastUpdateRiskReveal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pltPricingId, lastSynchronized, companyCode, countryIsoCode, importedOmegaId, name, pricingStructure, status, uwYear, lastUpdateRiskReveal);
    }
}
