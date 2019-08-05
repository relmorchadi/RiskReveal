package com.scor.rr.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "RmsSourceResult", schema = "dbo", catalog = "RiskReveal")
public class RmsSourceResultEntity {
    private int rmsSourceResultId;
    private Integer projectId;
    private String targetCurrency;
    private String targetRegionPeril;
    private String overrideRegionPerilBasis;
    private String occurrenceBasis;
    private String financialPerspective;
    private BigDecimal unitMultiplier;
    private BigDecimal proportion;
    private String targetRapCode;

    @Id
    @Column(name = "rmsSourceResultId", nullable = false)
    public int getRmsSourceResultId() {
        return rmsSourceResultId;
    }

    public void setRmsSourceResultId(int rmsSourceResultId) {
        this.rmsSourceResultId = rmsSourceResultId;
    }

    @Basic
    @Column(name = "projectId")
    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    @Basic
    @Column(name = "targetCurrency", length = 255,insertable = false ,updatable = false)
    public String getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(String targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    @Basic
    @Column(name = "targetRegionPeril", length = 255,insertable = false ,updatable = false)
    public String getTargetRegionPeril() {
        return targetRegionPeril;
    }

    public void setTargetRegionPeril(String targetRegionPeril) {
        this.targetRegionPeril = targetRegionPeril;
    }

    @Basic
    @Column(name = "overrideRegionPerilBasis", length = 255,insertable = false ,updatable = false)
    public String getOverrideRegionPerilBasis() {
        return overrideRegionPerilBasis;
    }

    public void setOverrideRegionPerilBasis(String overrideRegionPerilBasis) {
        this.overrideRegionPerilBasis = overrideRegionPerilBasis;
    }

    @Basic
    @Column(name = "occurrenceBasis", length = 255,insertable = false ,updatable = false)
    public String getOccurrenceBasis() {
        return occurrenceBasis;
    }

    public void setOccurrenceBasis(String occurrenceBasis) {
        this.occurrenceBasis = occurrenceBasis;
    }

    @Basic
    @Column(name = "financialPerspective", length = 255,insertable = false ,updatable = false)
    public String getFinancialPerspective() {
        return financialPerspective;
    }

    public void setFinancialPerspective(String financialPerspective) {
        this.financialPerspective = financialPerspective;
    }

    @Basic
    @Column(name = "unitMultiplier", precision = 7)
    public BigDecimal getUnitMultiplier() {
        return unitMultiplier;
    }

    public void setUnitMultiplier(BigDecimal unitMultiplier) {
        this.unitMultiplier = unitMultiplier;
    }

    @Basic
    @Column(name = "proportion", precision = 7)
    public BigDecimal getProportion() {
        return proportion;
    }

    public void setProportion(BigDecimal proportion) {
        this.proportion = proportion;
    }

    @Basic
    @Column(name = "targetRapCode", length = 255,insertable = false ,updatable = false)
    public String getTargetRapCode() {
        return targetRapCode;
    }

    public void setTargetRapCode(String targetRapCode) {
        this.targetRapCode = targetRapCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RmsSourceResultEntity that = (RmsSourceResultEntity) o;
        return rmsSourceResultId == that.rmsSourceResultId &&
                Objects.equals(projectId, that.projectId) &&
                Objects.equals(targetCurrency, that.targetCurrency) &&
                Objects.equals(targetRegionPeril, that.targetRegionPeril) &&
                Objects.equals(overrideRegionPerilBasis, that.overrideRegionPerilBasis) &&
                Objects.equals(occurrenceBasis, that.occurrenceBasis) &&
                Objects.equals(financialPerspective, that.financialPerspective) &&
                Objects.equals(unitMultiplier, that.unitMultiplier) &&
                Objects.equals(proportion, that.proportion) &&
                Objects.equals(targetRapCode, that.targetRapCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rmsSourceResultId, projectId, targetCurrency, targetRegionPeril, overrideRegionPerilBasis, occurrenceBasis, financialPerspective, unitMultiplier, proportion, targetRapCode);
    }
}
