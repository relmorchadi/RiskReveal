package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "AdjustmentBasis", schema = "dbo", catalog = "RiskReveal")
public class AdjustmentBasisEntity {
    private int code;
    private String adjustmentBasisName;
    private String basisShortname;
    private String description;
    private String exposureFlag;
    private Boolean capped;
    private Boolean isActive;
    private Boolean isExposureGrowth;
    private Integer sequence;
    private AdjustmentCategoryEntity adjustmentCategory;

    @Id
    @Column(name = "code", nullable = false)
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Basic
    @Column(name = "adjustmentBasisName", nullable = true, length = 255)
    public java.lang.String getAdjustmentBasisName() {
        return adjustmentBasisName;
    }

    public void setAdjustmentBasisName(java.lang.String adjustmentBasisName) {
        this.adjustmentBasisName = adjustmentBasisName;
    }

    @Basic
    @Column(name = "basisShortname", nullable = true, length = 255)
    public java.lang.String getBasisShortname() {
        return basisShortname;
    }

    public void setBasisShortname(java.lang.String basisShortname) {
        this.basisShortname = basisShortname;
    }

    @Basic
    @Column(name = "description", nullable = true, length = 255)
    public java.lang.String getDescription() {
        return description;
    }

    public void setDescription(java.lang.String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "exposureFlag", nullable = true, length = 50)
    public java.lang.String getExposureFlag() {
        return exposureFlag;
    }

    public void setExposureFlag(java.lang.String exposureFlag) {
        this.exposureFlag = exposureFlag;
    }

    @Basic
    @Column(name = "capped", nullable = true)
    public java.lang.Boolean getCapped() {
        return capped;
    }

    public void setCapped(java.lang.Boolean capped) {
        this.capped = capped;
    }

    @Basic
    @Column(name = "isActive", nullable = true)
    public java.lang.Boolean getActive() {
        return isActive;
    }

    public void setActive(java.lang.Boolean active) {
        isActive = active;
    }

    @Basic
    @Column(name = "isExposureGrowth", nullable = true)
    public java.lang.Boolean getExposureGrowth() {
        return isExposureGrowth;
    }

    public void setExposureGrowth(java.lang.Boolean exposureGrowth) {
        isExposureGrowth = exposureGrowth;
    }

    @Basic
    @Column(name = "sequence", nullable = true)
    public java.lang.Integer getSequence() {
        return sequence;
    }

    public void setSequence(java.lang.Integer sequence) {
        this.sequence = sequence;
    }

    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;

        AdjustmentBasisEntity that = (AdjustmentBasisEntity) object;

        if (code != that.code) return false;
        if (!Objects.equals(adjustmentBasisName, that.adjustmentBasisName))
            return false;
        if (!Objects.equals(basisShortname, that.basisShortname))
            return false;
        if (!Objects.equals(description, that.description)) return false;
        if (!Objects.equals(exposureFlag, that.exposureFlag)) return false;
        if (!Objects.equals(capped, that.capped)) return false;
        if (!Objects.equals(isActive, that.isActive)) return false;
        if (!Objects.equals(isExposureGrowth, that.isExposureGrowth))
            return false;
        if (!Objects.equals(sequence, that.sequence)) return false;

        return true;
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + code;
        result = 31 * result + (adjustmentBasisName != null ? adjustmentBasisName.hashCode() : 0);
        result = 31 * result + (basisShortname != null ? basisShortname.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (exposureFlag != null ? exposureFlag.hashCode() : 0);
        result = 31 * result + (capped != null ? capped.hashCode() : 0);
        result = 31 * result + (isActive != null ? isActive.hashCode() : 0);
        result = 31 * result + (isExposureGrowth != null ? isExposureGrowth.hashCode() : 0);
        result = 31 * result + (sequence != null ? sequence.hashCode() : 0);
        return result;
    }

    @ManyToOne(cascade = {})
    @JoinColumn(name = "id_category", referencedColumnName = "id_category", nullable = true, table = "")
    public AdjustmentCategoryEntity getAdjustmentCategory() {
        return adjustmentCategory;
    }

    public void setAdjustmentCategory(AdjustmentCategoryEntity adjustmentCategory) {
        this.adjustmentCategory = adjustmentCategory;
    }
}

