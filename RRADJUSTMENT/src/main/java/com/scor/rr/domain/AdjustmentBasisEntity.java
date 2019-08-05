package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "AdjustmentBasis", schema = "dbo", catalog = "RiskReveal")
public class AdjustmentBasisEntity {
    private int code;
    private String adjustmentBasisName;
    private String basisShortName;
    private String description;
    private String exposureFlag;
    private Boolean capped;
    private Boolean isActive;
    private Boolean isExposureGrowth;
    private Integer sequence;
    private AdjustmentCategoryEntity adjustmentCategoryByIdCategory;

    @Id
    @Column(name = "code", nullable = false)
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Basic
    @Column(name = "adjustment_basis_name", length = 255)
    public String getAdjustmentBasisName() {
        return adjustmentBasisName;
    }

    public void setAdjustmentBasisName(String adjustmentBasisName) {
        this.adjustmentBasisName = adjustmentBasisName;
    }

    @Basic
    @Column(name = "basis_short_name", length = 255)
    public String getBasisShortName() {
        return basisShortName;
    }

    public void setBasisShortName(String basisShortName) {
        this.basisShortName = basisShortName;
    }

    @Basic
    @Column(name = "description", length = 255)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "exposure_flag", length = 50)
    public String getExposureFlag() {
        return exposureFlag;
    }

    public void setExposureFlag(String exposureFlag) {
        this.exposureFlag = exposureFlag;
    }

    @Basic
    @Column(name = "capped")
    public Boolean getCapped() {
        return capped;
    }

    public void setCapped(Boolean capped) {
        this.capped = capped;
    }

    @Basic
    @Column(name = "is_active")
    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @Basic
    @Column(name = "is_exposure_growth")
    public Boolean getExposureGrowth() {
        return isExposureGrowth;
    }

    public void setExposureGrowth(Boolean exposureGrowth) {
        isExposureGrowth = exposureGrowth;
    }

    @Basic
    @Column(name = "sequence")
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
        AdjustmentBasisEntity that = (AdjustmentBasisEntity) o;
        return code == that.code &&
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
        return Objects.hash(code, adjustmentBasisName, basisShortName, description, exposureFlag, capped, isActive, isExposureGrowth, sequence);
    }

    @ManyToOne
    @JoinColumn(name = "id_category", referencedColumnName = "id_category")
    public AdjustmentCategoryEntity getAdjustmentCategoryByIdCategory() {
        return adjustmentCategoryByIdCategory;
    }

    public void setAdjustmentCategoryByIdCategory(AdjustmentCategoryEntity adjustmentCategoryByIdCategory) {
        this.adjustmentCategoryByIdCategory = adjustmentCategoryByIdCategory;
    }
}
