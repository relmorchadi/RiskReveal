package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "TargetRap", schema = "dbo", catalog = "RiskReveal")
public class TargetRapEntity {
    private int targetRapId;
    private String modellingVendor;
    private String modellingSystem;
    private String modellingSystemVersion;
    private String targetRapCode;
    private String targetRapDesc;
    private Integer petId;
    private String sourceRapCode;
    private Boolean isScorGenerated;
    private Boolean isScorCurrent;
    private Boolean isScorDefault;
    private Boolean isActive;

    @Basic
    @Column(name = "modellingVendor", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getModellingVendor() {
        return modellingVendor;
    }

    public void setModellingVendor(String modellingVendor) {
        this.modellingVendor = modellingVendor;
    }

    @Basic
    @Column(name = "modellingSystem", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getModellingSystem() {
        return modellingSystem;
    }

    public void setModellingSystem(String modellingSystem) {
        this.modellingSystem = modellingSystem;
    }

    @Basic
    @Column(name = "modellingSystemVersion", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getModellingSystemVersion() {
        return modellingSystemVersion;
    }

    public void setModellingSystemVersion(String modellingSystemVersion) {
        this.modellingSystemVersion = modellingSystemVersion;
    }

    @Id
    @Column(name = "targetRapId", nullable = false)
    public int getTargetRapId() {
        return targetRapId;
    }

    public void setTargetRapId(int targetRapId) {
        this.targetRapId = targetRapId;
    }

    @Basic
    @Column(name = "modellingVendor", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getEntitylingVendor() {
        return modellingVendor;
    }

    public void setEntitylingVendor(String modellingVendor) {
        this.modellingVendor = modellingVendor;
    }

    @Basic
    @Column(name = "modellingSystem", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getEntitylingSystem() {
        return modellingSystem;
    }

    public void setEntitylingSystem(String modellingSystem) {
        this.modellingSystem = modellingSystem;
    }

    @Basic
    @Column(name = "modellingSystemVersion", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getEntitylingSystemVersion() {
        return modellingSystemVersion;
    }

    public void setEntitylingSystemVersion(String modellingSystemVersion) {
        this.modellingSystemVersion = modellingSystemVersion;
    }

    @Basic
    @Column(name = "targetRapCode", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getTargetRapCode() {
        return targetRapCode;
    }

    public void setTargetRapCode(String targetRapCode) {
        this.targetRapCode = targetRapCode;
    }

    @Basic
    @Column(name = "targetRapDesc", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getTargetRapDesc() {
        return targetRapDesc;
    }

    public void setTargetRapDesc(String targetRapDesc) {
        this.targetRapDesc = targetRapDesc;
    }

    @Basic
    @Column(name = "petId", nullable = true)
    public Integer getPetId() {
        return petId;
    }

    public void setPetId(Integer petId) {
        this.petId = petId;
    }

    @Basic
    @Column(name = "sourceRapCode", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getSourceRapCode() {
        return sourceRapCode;
    }

    public void setSourceRapCode(String sourceRapCode) {
        this.sourceRapCode = sourceRapCode;
    }

    @Basic
    @Column(name = "isScorGenerated", nullable = true)
    public Boolean getScorGenerated() {
        return isScorGenerated;
    }

    public void setScorGenerated(Boolean scorGenerated) {
        isScorGenerated = scorGenerated;
    }

    @Basic
    @Column(name = "isScorCurrent", nullable = true)
    public Boolean getScorCurrent() {
        return isScorCurrent;
    }

    public void setScorCurrent(Boolean scorCurrent) {
        isScorCurrent = scorCurrent;
    }

    @Basic
    @Column(name = "isScorDefault", nullable = true)
    public Boolean getScorDefault() {
        return isScorDefault;
    }

    public void setScorDefault(Boolean scorDefault) {
        isScorDefault = scorDefault;
    }

    @Basic
    @Column(name = "isActive", nullable = true)
    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TargetRapEntity that = (TargetRapEntity) o;
        return targetRapId == that.targetRapId &&
                Objects.equals(modellingVendor, that.modellingVendor) &&
                Objects.equals(modellingSystem, that.modellingSystem) &&
                Objects.equals(modellingSystemVersion, that.modellingSystemVersion) &&
                Objects.equals(targetRapCode, that.targetRapCode) &&
                Objects.equals(targetRapDesc, that.targetRapDesc) &&
                Objects.equals(petId, that.petId) &&
                Objects.equals(sourceRapCode, that.sourceRapCode) &&
                Objects.equals(isScorGenerated, that.isScorGenerated) &&
                Objects.equals(isScorCurrent, that.isScorCurrent) &&
                Objects.equals(isScorDefault, that.isScorDefault) &&
                Objects.equals(isActive, that.isActive);
    }

    @Override
    public int hashCode() {
        return Objects.hash(targetRapId, modellingVendor, modellingSystem, modellingSystemVersion, targetRapCode, targetRapDesc, petId, sourceRapCode, isScorGenerated, isScorCurrent, isScorDefault, isActive);
    }
}
