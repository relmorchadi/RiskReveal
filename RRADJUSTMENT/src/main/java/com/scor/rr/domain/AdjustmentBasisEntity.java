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
    public String getAdjustmentBasisName() {
        return adjustmentBasisName;
    }

    public void setAdjustmentBasisName(String adjustmentBasisName) {
        this.adjustmentBasisName = adjustmentBasisName;
    }

    @Basic
    @Column(name = "basisShortname", nullable = true, length = 255)
    public String getBasisShortname() {
        return basisShortname;
    }

    public void setBasisShortname(String basisShortname) {
        this.basisShortname = basisShortname;
    }

    @Basic
    @Column(name = "description", nullable = true, length = 255)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "exposureFlag", nullable = true, length = 50)
    public String getExposureFlag() {
        return exposureFlag;
    }

    public void setExposureFlag(String exposureFlag) {
        this.exposureFlag = exposureFlag;
    }

    @Basic
    @Column(name = "capped", nullable = true)
    public Boolean getCapped() {
        return capped;
    }

    public void setCapped(Boolean capped) {
        this.capped = capped;
    }

    @Basic
    @Column(name = "isActive", nullable = true)
    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @Basic
    @Column(name = "isExposureGrowth", nullable = true)
    public Boolean getExposureGrowth() {
        return isExposureGrowth;
    }

    public void setExposureGrowth(Boolean exposureGrowth) {
        isExposureGrowth = exposureGrowth;
    }

    @Basic
    @Column(name = "sequence", nullable = true)
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
                Objects.equals(basisShortname, that.basisShortname) &&
                Objects.equals(description, that.description) &&
                Objects.equals(exposureFlag, that.exposureFlag) &&
                Objects.equals(capped, that.capped) &&
                Objects.equals(isActive, that.isActive) &&
                Objects.equals(isExposureGrowth, that.isExposureGrowth) &&
                Objects.equals(sequence, that.sequence);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, adjustmentBasisName, basisShortname, description, exposureFlag, capped, isActive, isExposureGrowth, sequence);
    }
}
