package com.scor.rr.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "RegionPeril", schema = "dbo", catalog = "RiskReveal")
public class RegionPerilEntity {
    private int regionPerilId;
    private String regionPerilCode;
    private String regionPerilDesc;
    private String perilCode;
    private String regionPerilGroupCode;
    private String regionPerilGroupDesc;
    private Integer regionHierachy;
    private String regionDesc;
    private Boolean isEntityled;
    private String hierachyParentCode;
    private Integer hierachyLevel;
    private Boolean isMinimumGrainRegionPeril;
    private String parentMinimumGrainRegionPeril;
    private Boolean isActive;
    private Boolean isModelled;
    private String regionPerilGroupDescription;
    private Integer regionHierarchy;
    private String hierarchyParentCode;
    private Integer hierarchyLevel;
    private Timestamp lastUpdatedRiskReveal;
    private Timestamp lastUpdatedCatDomain;
    private Timestamp lastSyncRunCatDomain;
    private String comments;

    @Id
    @Column(name = "regionPerilId", nullable = false)
    public int getRegionPerilId() {
        return regionPerilId;
    }

    public void setRegionPerilId(int regionPerilId) {
        this.regionPerilId = regionPerilId;
    }

    @Basic
    @Column(name = "regionPerilCode", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getRegionPerilCode() {
        return regionPerilCode;
    }

    public void setRegionPerilCode(String regionPerilCode) {
        this.regionPerilCode = regionPerilCode;
    }

    @Basic
    @Column(name = "regionPerilDesc", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getRegionPerilDesc() {
        return regionPerilDesc;
    }

    public void setRegionPerilDesc(String regionPerilDesc) {
        this.regionPerilDesc = regionPerilDesc;
    }

    @Basic
    @Column(name = "perilCode", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getPerilCode() {
        return perilCode;
    }

    public void setPerilCode(String perilCode) {
        this.perilCode = perilCode;
    }

    @Basic
    @Column(name = "regionPerilGroupCode", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getRegionPerilGroupCode() {
        return regionPerilGroupCode;
    }

    public void setRegionPerilGroupCode(String regionPerilGroupCode) {
        this.regionPerilGroupCode = regionPerilGroupCode;
    }

    @Basic
    @Column(name = "regionPerilGroupDesc", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getRegionPerilGroupDesc() {
        return regionPerilGroupDesc;
    }

    public void setRegionPerilGroupDesc(String regionPerilGroupDesc) {
        this.regionPerilGroupDesc = regionPerilGroupDesc;
    }

    @Basic
    @Column(name = "regionHierachy", nullable = true)
    public Integer getRegionHierachy() {
        return regionHierachy;
    }

    public void setRegionHierachy(Integer regionHierachy) {
        this.regionHierachy = regionHierachy;
    }

    @Basic
    @Column(name = "regionDesc", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getRegionDesc() {
        return regionDesc;
    }

    public void setRegionDesc(String regionDesc) {
        this.regionDesc = regionDesc;
    }

    @Basic
    @Column(name = "isEntityled", nullable = true)
    public Boolean getEntityled() {
        return isEntityled;
    }

    public void setEntityled(Boolean modelled) {
        isEntityled = modelled;
    }

    @Basic
    @Column(name = "hierachyParentCode", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getHierachyParentCode() {
        return hierachyParentCode;
    }

    public void setHierachyParentCode(String hierachyParentCode) {
        this.hierachyParentCode = hierachyParentCode;
    }

    @Basic
    @Column(name = "hierachyLevel", nullable = true)
    public Integer getHierachyLevel() {
        return hierachyLevel;
    }

    public void setHierachyLevel(Integer hierachyLevel) {
        this.hierachyLevel = hierachyLevel;
    }

    @Basic
    @Column(name = "isMinimumGrainRegionPeril", nullable = true)
    public Boolean getMinimumGrainRegionPeril() {
        return isMinimumGrainRegionPeril;
    }

    public void setMinimumGrainRegionPeril(Boolean minimumGrainRegionPeril) {
        isMinimumGrainRegionPeril = minimumGrainRegionPeril;
    }

    @Basic
    @Column(name = "parentMinimumGrainRegionPeril", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getParentMinimumGrainRegionPeril() {
        return parentMinimumGrainRegionPeril;
    }

    public void setParentMinimumGrainRegionPeril(String parentMinimumGrainRegionPeril) {
        this.parentMinimumGrainRegionPeril = parentMinimumGrainRegionPeril;
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
        RegionPerilEntity that = (RegionPerilEntity) o;
        return regionPerilId == that.regionPerilId &&
                Objects.equals(regionPerilCode, that.regionPerilCode) &&
                Objects.equals(regionPerilDesc, that.regionPerilDesc) &&
                Objects.equals(perilCode, that.perilCode) &&
                Objects.equals(regionPerilGroupCode, that.regionPerilGroupCode) &&
                Objects.equals(regionPerilGroupDesc, that.regionPerilGroupDesc) &&
                Objects.equals(regionHierachy, that.regionHierachy) &&
                Objects.equals(regionDesc, that.regionDesc) &&
                Objects.equals(isEntityled, that.isEntityled) &&
                Objects.equals(hierachyParentCode, that.hierachyParentCode) &&
                Objects.equals(hierachyLevel, that.hierachyLevel) &&
                Objects.equals(isMinimumGrainRegionPeril, that.isMinimumGrainRegionPeril) &&
                Objects.equals(parentMinimumGrainRegionPeril, that.parentMinimumGrainRegionPeril) &&
                Objects.equals(isActive, that.isActive);
    }

    @Override
    public int hashCode() {
        return Objects.hash(regionPerilId, regionPerilCode, regionPerilDesc, perilCode, regionPerilGroupCode, regionPerilGroupDesc, regionHierachy, regionDesc, isEntityled, hierachyParentCode, hierachyLevel, isMinimumGrainRegionPeril, parentMinimumGrainRegionPeril, isActive);
    }

    @Basic
    @Column(name = "isModelled", nullable = true)
    public Boolean getModelled() {
        return isModelled;
    }

    public void setModelled(Boolean modelled) {
        isModelled = modelled;
    }

    @Basic
    @Column(name = "regionPerilGroupDescription", nullable = true, length = 255)
    public String getRegionPerilGroupDescription() {
        return regionPerilGroupDescription;
    }

    public void setRegionPerilGroupDescription(String regionPerilGroupDescription) {
        this.regionPerilGroupDescription = regionPerilGroupDescription;
    }

    @Basic
    @Column(name = "regionHierarchy", nullable = true)
    public Integer getRegionHierarchy() {
        return regionHierarchy;
    }

    public void setRegionHierarchy(Integer regionHierarchy) {
        this.regionHierarchy = regionHierarchy;
    }

    @Basic
    @Column(name = "hierarchyParentCode", nullable = true, length = 255)
    public String getHierarchyParentCode() {
        return hierarchyParentCode;
    }

    public void setHierarchyParentCode(String hierarchyParentCode) {
        this.hierarchyParentCode = hierarchyParentCode;
    }

    @Basic
    @Column(name = "hierarchyLevel", nullable = true)
    public Integer getHierarchyLevel() {
        return hierarchyLevel;
    }

    public void setHierarchyLevel(Integer hierarchyLevel) {
        this.hierarchyLevel = hierarchyLevel;
    }

    @Basic
    @Column(name = "LastUpdatedRiskReveal", nullable = true)
    public Timestamp getLastUpdatedRiskReveal() {
        return lastUpdatedRiskReveal;
    }

    public void setLastUpdatedRiskReveal(Timestamp lastUpdatedRiskReveal) {
        this.lastUpdatedRiskReveal = lastUpdatedRiskReveal;
    }

    @Basic
    @Column(name = "LastUpdatedCatDomain", nullable = true)
    public Timestamp getLastUpdatedCatDomain() {
        return lastUpdatedCatDomain;
    }

    public void setLastUpdatedCatDomain(Timestamp lastUpdatedCatDomain) {
        this.lastUpdatedCatDomain = lastUpdatedCatDomain;
    }

    @Basic
    @Column(name = "LastSyncRunCatDomain", nullable = true)
    public Timestamp getLastSyncRunCatDomain() {
        return lastSyncRunCatDomain;
    }

    public void setLastSyncRunCatDomain(Timestamp lastSyncRunCatDomain) {
        this.lastSyncRunCatDomain = lastSyncRunCatDomain;
    }

    @Basic
    @Column(name = "comments", nullable = true, length = 100)
    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
