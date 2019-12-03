package com.scor.rr.domain;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Objects;

@Entity
@Table(name = "AdjustmentBasis")
public class AdjustmentBasis {
    private Integer adjustmentBasisId;
    private String adjustmentBasisName;
    private String basisShortName;
    private String description;
    private String exposureFlag;
    private Boolean capped;
    private Boolean isActive;
    private Boolean isExposureGrowth;
    private Integer sequence;
    private AdjustmentCategoryEntity adjustmentCategory;

    @Id
    @Column(name = "AdjustmentBasisId", nullable = false)
    public Integer getAdjustmentBasisId() {
        return adjustmentBasisId;
    }

    public void setAdjustmentBasisId(Integer adjustmentBasisId) {
        this.adjustmentBasisId = adjustmentBasisId;
    }

    @Basic
    @Column(name = "AdjustmentBasisName", nullable = true, length = 255)
    public String getAdjustmentBasisName() {
        return adjustmentBasisName;
    }

    public void setAdjustmentBasisName(String adjustmentBasisName) {
        this.adjustmentBasisName = adjustmentBasisName;
    }

    @Basic
    @Column(name = "BasisShortName", nullable = true, length = 255)
    public String getBasisShortName() {
        return basisShortName;
    }

    public void setBasisShortName(String basisShortName) {
        this.basisShortName = basisShortName;
    }

    @Basic
    @Column(name = "Description", nullable = true, length = 255)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "ExposureFlag", nullable = true, length = 50)
    public String getExposureFlag() {
        return exposureFlag;
    }

    public void setExposureFlag(String exposureFlag) {
        this.exposureFlag = exposureFlag;
    }

    @Basic
    @Column(name = "Capped", nullable = true)
    public Boolean getCapped() {
        return capped;
    }

    public void setCapped(Boolean capped) {
        this.capped = capped;
    }

    @Basic
    @Column(name = "IsActive", nullable = true)
    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @Basic
    @Column(name = "IsExposureGrowth", nullable = true)
    public Boolean getExposureGrowth() {
        return isExposureGrowth;
    }

    public void setExposureGrowth(Boolean exposureGrowth) {
        isExposureGrowth = exposureGrowth;
    }

    @Basic
    @Column(name = "Sequence", nullable = true)
    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdjustmentBasis that = (AdjustmentBasis) o;
        return adjustmentBasisId == that.adjustmentBasisId &&
                Objects.equals(adjustmentBasisName, that.adjustmentBasisName) &&
                Objects.equals(basisShortName, that.basisShortName) &&
                Objects.equals(description, that.description) &&
                Objects.equals(exposureFlag, that.exposureFlag) &&
                Objects.equals(capped, that.capped) &&
                Objects.equals(isActive, that.isActive) &&
                Objects.equals(isExposureGrowth, that.isExposureGrowth) &&
                Objects.equals(sequence, that.sequence);
    }

    @Override
    public int hashCode() {
        return Objects.hash(adjustmentBasisId, adjustmentBasisName, basisShortName, description, exposureFlag, capped, isActive, isExposureGrowth, sequence);
    }

    @ManyToOne
    @JoinColumn(name = "CategoryId", referencedColumnName = "AdjustmentCategoryId")
    public AdjustmentCategoryEntity getAdjustmentCategory() {
        return adjustmentCategory;
    }

    public void setAdjustmentCategory(AdjustmentCategoryEntity adjustmentCategory) {
        this.adjustmentCategory = adjustmentCategory;
    }
}
