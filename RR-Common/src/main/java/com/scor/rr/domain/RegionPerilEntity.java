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
    private String regionPerilGroupDescription;
    private Integer regionHierarchy;
    private String regionDesc;
    private Boolean isModelled;
    private String hierarchyParentCode;
    private Integer hierarchyLevel;
    private Boolean isMinimumGrainRegionPeril;
    private String parentMinimumGrainRegionPeril;
    private Boolean isActive;
    private Boolean isEntityled;
    private Timestamp lastUpdatedRiskReveal;
    private Timestamp lastUpdatedCatDomain;
    private Timestamp lastSyncRunCatDomain;
    private Integer hierachyLevel;
    private String hierachyParentCode;
    private Integer regionHierachy;
    private String regionPerilGroupDesc;

    @Id
    @Column(name = "regionPerilId", nullable = false)
    public int getRegionPerilId() {
        return regionPerilId;
    }

    public void setRegionPerilId(int regionPerilId) {
        this.regionPerilId = regionPerilId;
    }

    @Basic
    @Column(name = "regionPerilCode", length = 255)
    public String getRegionPerilCode() {
        return regionPerilCode;
    }

    public void setRegionPerilCode(String regionPerilCode) {
        this.regionPerilCode = regionPerilCode;
    }

    @Basic
    @Column(name = "regionPerilDesc", length = 255)
    public String getRegionPerilDesc() {
        return regionPerilDesc;
    }

    public void setRegionPerilDesc(String regionPerilDesc) {
        this.regionPerilDesc = regionPerilDesc;
    }

    @Basic
    @Column(name = "perilCode", length = 255)
    public String getPerilCode() {
        return perilCode;
    }

    public void setPerilCode(String perilCode) {
        this.perilCode = perilCode;
    }

    @Basic
    @Column(name = "regionPerilGroupCode", length = 255)
    public String getRegionPerilGroupCode() {
        return regionPerilGroupCode;
    }

    public void setRegionPerilGroupCode(String regionPerilGroupCode) {
        this.regionPerilGroupCode = regionPerilGroupCode;
    }

    @Basic
    @Column(name = "regionPerilGroupDescription", length = 255)
    public String getRegionPerilGroupDescription() {
        return regionPerilGroupDescription;
    }

    public void setRegionPerilGroupDescription(String regionPerilGroupDescription) {
        this.regionPerilGroupDescription = regionPerilGroupDescription;
    }

    @Basic
    @Column(name = "regionHierarchy")
    public Integer getRegionHierarchy() {
        return regionHierarchy;
    }

    public void setRegionHierarchy(Integer regionHierarchy) {
        this.regionHierarchy = regionHierarchy;
    }

    @Basic
    @Column(name = "regionDesc", length = 255)
    public String getRegionDesc() {
        return regionDesc;
    }

    public void setRegionDesc(String regionDesc) {
        this.regionDesc = regionDesc;
    }

    @Basic
    @Column(name = "isModelled")
    public Boolean getModelled() {
        return isModelled;
    }

    public void setModelled(Boolean modelled) {
        isModelled = modelled;
    }

    @Basic
    @Column(name = "hierarchyParentCode", length = 255)
    public String getHierarchyParentCode() {
        return hierarchyParentCode;
    }

    public void setHierarchyParentCode(String hierarchyParentCode) {
        this.hierarchyParentCode = hierarchyParentCode;
    }

    @Basic
    @Column(name = "hierarchyLevel")
    public Integer getHierarchyLevel() {
        return hierarchyLevel;
    }

    public void setHierarchyLevel(Integer hierarchyLevel) {
        this.hierarchyLevel = hierarchyLevel;
    }

    @Basic
    @Column(name = "isMinimumGrainRegionPeril")
    public Boolean getMinimumGrainRegionPeril() {
        return isMinimumGrainRegionPeril;
    }

    public void setMinimumGrainRegionPeril(Boolean minimumGrainRegionPeril) {
        isMinimumGrainRegionPeril = minimumGrainRegionPeril;
    }

    @Basic
    @Column(name = "parentMinimumGrainRegionPeril", length = 255)
    public String getParentMinimumGrainRegionPeril() {
        return parentMinimumGrainRegionPeril;
    }

    public void setParentMinimumGrainRegionPeril(String parentMinimumGrainRegionPeril) {
        this.parentMinimumGrainRegionPeril = parentMinimumGrainRegionPeril;
    }

    @Basic
    @Column(name = "isActive")
    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @Basic
    @Column(name = "isEntityled")
    public Boolean getEntityled() {
        return isEntityled;
    }

    public void setEntityled(Boolean entityled) {
        isEntityled = entityled;
    }

    @Basic
    @Column(name = "LastUpdatedRiskReveal")
    public Timestamp getLastUpdatedRiskReveal() {
        return lastUpdatedRiskReveal;
    }

    public void setLastUpdatedRiskReveal(Timestamp lastUpdatedRiskReveal) {
        this.lastUpdatedRiskReveal = lastUpdatedRiskReveal;
    }

    @Basic
    @Column(name = "LastUpdatedCatDomain")
    public Timestamp getLastUpdatedCatDomain() {
        return lastUpdatedCatDomain;
    }

    public void setLastUpdatedCatDomain(Timestamp lastUpdatedCatDomain) {
        this.lastUpdatedCatDomain = lastUpdatedCatDomain;
    }

    @Basic
    @Column(name = "LastSyncRunCatDomain")
    public Timestamp getLastSyncRunCatDomain() {
        return lastSyncRunCatDomain;
    }

    public void setLastSyncRunCatDomain(Timestamp lastSyncRunCatDomain) {
        this.lastSyncRunCatDomain = lastSyncRunCatDomain;
    }


    @Basic
    @Column(name = "hierachyLevel")
    public Integer getHierachyLevel() {
        return hierachyLevel;
    }

    public void setHierachyLevel(Integer hierachyLevel) {
        this.hierachyLevel = hierachyLevel;
    }

    @Basic
    @Column(name = "hierachyParentCode", length = 255)
    public String getHierachyParentCode() {
        return hierachyParentCode;
    }

    public void setHierachyParentCode(String hierachyParentCode) {
        this.hierachyParentCode = hierachyParentCode;
    }

    @Basic
    @Column(name = "regionHierachy")
    public Integer getRegionHierachy() {
        return regionHierachy;
    }

    public void setRegionHierachy(Integer regionHierachy) {
        this.regionHierachy = regionHierachy;
    }

    @Basic
    @Column(name = "regionPerilGroupDesc", length = 255)
    public String getRegionPerilGroupDesc() {
        return regionPerilGroupDesc;
    }

    public void setRegionPerilGroupDesc(String regionPerilGroupDesc) {
        this.regionPerilGroupDesc = regionPerilGroupDesc;
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
                Objects.equals(regionPerilGroupDescription, that.regionPerilGroupDescription) &&
                Objects.equals(regionHierarchy, that.regionHierarchy) &&
                Objects.equals(regionDesc, that.regionDesc) &&
                Objects.equals(isModelled, that.isModelled) &&
                Objects.equals(hierarchyParentCode, that.hierarchyParentCode) &&
                Objects.equals(hierarchyLevel, that.hierarchyLevel) &&
                Objects.equals(isMinimumGrainRegionPeril, that.isMinimumGrainRegionPeril) &&
                Objects.equals(parentMinimumGrainRegionPeril, that.parentMinimumGrainRegionPeril) &&
                Objects.equals(isActive, that.isActive) &&
                Objects.equals(isEntityled, that.isEntityled) &&
                Objects.equals(lastUpdatedRiskReveal, that.lastUpdatedRiskReveal) &&
                Objects.equals(lastUpdatedCatDomain, that.lastUpdatedCatDomain) &&
                Objects.equals(lastSyncRunCatDomain, that.lastSyncRunCatDomain) &&
                Objects.equals(hierachyLevel, that.hierachyLevel) &&
                Objects.equals(hierachyParentCode, that.hierachyParentCode) &&
                Objects.equals(regionHierachy, that.regionHierachy) &&
                Objects.equals(regionPerilGroupDesc, that.regionPerilGroupDesc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(regionPerilId, regionPerilCode, regionPerilDesc, perilCode, regionPerilGroupCode, regionPerilGroupDescription, regionHierarchy, regionDesc, isModelled, hierarchyParentCode, hierarchyLevel, isMinimumGrainRegionPeril, parentMinimumGrainRegionPeril, isActive, isEntityled, lastUpdatedRiskReveal, lastUpdatedCatDomain, lastSyncRunCatDomain, comments, hierachyLevel, hierachyParentCode, regionHierachy, regionPerilGroupDesc);
    }
}
