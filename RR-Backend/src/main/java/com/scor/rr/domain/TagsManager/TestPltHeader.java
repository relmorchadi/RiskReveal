package com.scor.rr.domain.TagsManager;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "TmPltHeader", schema = "PRD", catalog = "RR")
public class TestPltHeader {
    private int pltId;
    private String pltIdMongo;
    private Integer pltProjectId;
    private String pltProjectIdMongo;
    private Timestamp pltCreated;
    private Integer purePltId;
    private String purePltIdMongo;
    private String pltType;
    private String pltStatus;
    private String pltDefaultName;
    private String pltThreadName;
    private Integer pltRegionPerilId;
    private String pltRegionPerilIdMongo;
    private String pltPerilCode;
    private Integer pltSimSet;
    private Long pltImportSeq;
    private Integer pltAdjustStrutureId;
    private String pltAdjustStructureIdMongo;
    private Integer inuringPackageId;
    private String inuringPackageIdMongo;
    private Timestamp pltPublishPricing;
    private String regionPerilCode;
    private String regionPerilDesc;
    private String parentMinimumGrainRegionPeril;

    @Id
    @Column(name = "PltId")
    public int getPltId() {
        return pltId;
    }

    public void setPltId(int pltId) {
        this.pltId = pltId;
    }

    @Basic
    @Column(name = "PltIdMongo")
    public String getPltIdMongo() {
        return pltIdMongo;
    }

    public void setPltIdMongo(String pltIdMongo) {
        this.pltIdMongo = pltIdMongo;
    }

    @Basic
    @Column(name = "PltProjectId")
    public Integer getPltProjectId() {
        return pltProjectId;
    }

    public void setPltProjectId(Integer pltProjectId) {
        this.pltProjectId = pltProjectId;
    }

    @Basic
    @Column(name = "PltProjectIdMongo")
    public String getPltProjectIdMongo() {
        return pltProjectIdMongo;
    }

    public void setPltProjectIdMongo(String pltProjectIdMongo) {
        this.pltProjectIdMongo = pltProjectIdMongo;
    }

    @Basic
    @Column(name = "PltCreated")
    public Timestamp getPltCreated() {
        return pltCreated;
    }

    public void setPltCreated(Timestamp pltCreated) {
        this.pltCreated = pltCreated;
    }

    @Basic
    @Column(name = "PurePltId")
    public Integer getPurePltId() {
        return purePltId;
    }

    public void setPurePltId(Integer purePltId) {
        this.purePltId = purePltId;
    }

    @Basic
    @Column(name = "PurePltIdMongo")
    public String getPurePltIdMongo() {
        return purePltIdMongo;
    }

    public void setPurePltIdMongo(String purePltIdMongo) {
        this.purePltIdMongo = purePltIdMongo;
    }

    @Basic
    @Column(name = "PltType")
    public String getPltType() {
        return pltType;
    }

    public void setPltType(String pltType) {
        this.pltType = pltType;
    }

    @Basic
    @Column(name = "PltStatus")
    public String getPltStatus() {
        return pltStatus;
    }

    public void setPltStatus(String pltStatus) {
        this.pltStatus = pltStatus;
    }

    @Basic
    @Column(name = "PltDefaultName")
    public String getPltDefaultName() {
        return pltDefaultName;
    }

    public void setPltDefaultName(String pltDefaultName) {
        this.pltDefaultName = pltDefaultName;
    }

    @Basic
    @Column(name = "PltThreadName")
    public String getPltThreadName() {
        return pltThreadName;
    }

    public void setPltThreadName(String pltThreadName) {
        this.pltThreadName = pltThreadName;
    }

    @Basic
    @Column(name = "PltRegionPerilId")
    public Integer getPltRegionPerilId() {
        return pltRegionPerilId;
    }

    public void setPltRegionPerilId(Integer pltRegionPerilId) {
        this.pltRegionPerilId = pltRegionPerilId;
    }

    @Basic
    @Column(name = "PltRegionPerilIdMongo")
    public String getPltRegionPerilIdMongo() {
        return pltRegionPerilIdMongo;
    }

    public void setPltRegionPerilIdMongo(String pltRegionPerilIdMongo) {
        this.pltRegionPerilIdMongo = pltRegionPerilIdMongo;
    }

    @Basic
    @Column(name = "PltPerilCode")
    public String getPltPerilCode() {
        return pltPerilCode;
    }

    public void setPltPerilCode(String pltPerilCode) {
        this.pltPerilCode = pltPerilCode;
    }

    @Basic
    @Column(name = "PltSimSet")
    public Integer getPltSimSet() {
        return pltSimSet;
    }

    public void setPltSimSet(Integer pltSimSet) {
        this.pltSimSet = pltSimSet;
    }

    @Basic
    @Column(name = "PltImportSeq")
    public Long getPltImportSeq() {
        return pltImportSeq;
    }

    public void setPltImportSeq(Long pltImportSeq) {
        this.pltImportSeq = pltImportSeq;
    }

    @Basic
    @Column(name = "PltAdjustStrutureID")
    public Integer getPltAdjustStrutureId() {
        return pltAdjustStrutureId;
    }

    public void setPltAdjustStrutureId(Integer pltAdjustStrutureId) {
        this.pltAdjustStrutureId = pltAdjustStrutureId;
    }

    @Basic
    @Column(name = "PltAdjustStructureIDMongo")
    public String getPltAdjustStructureIdMongo() {
        return pltAdjustStructureIdMongo;
    }

    public void setPltAdjustStructureIdMongo(String pltAdjustStructureIdMongo) {
        this.pltAdjustStructureIdMongo = pltAdjustStructureIdMongo;
    }

    @Basic
    @Column(name = "InuringPackageId")
    public Integer getInuringPackageId() {
        return inuringPackageId;
    }

    public void setInuringPackageId(Integer inuringPackageId) {
        this.inuringPackageId = inuringPackageId;
    }

    @Basic
    @Column(name = "InuringPackageIdMongo")
    public String getInuringPackageIdMongo() {
        return inuringPackageIdMongo;
    }

    public void setInuringPackageIdMongo(String inuringPackageIdMongo) {
        this.inuringPackageIdMongo = inuringPackageIdMongo;
    }

    @Basic
    @Column(name = "PltPublishPricing")
    public Timestamp getPltPublishPricing() {
        return pltPublishPricing;
    }

    public void setPltPublishPricing(Timestamp pltPublishPricing) {
        this.pltPublishPricing = pltPublishPricing;
    }

    @Basic
    @Column(name = "regionPerilCode")
    public String getRegionPerilCode() {
        return regionPerilCode;
    }

    public void setRegionPerilCode(String regionPerilCode) {
        this.regionPerilCode = regionPerilCode;
    }

    @Basic
    @Column(name = "regionPerilDesc")
    public String getRegionPerilDesc() {
        return regionPerilDesc;
    }

    public void setRegionPerilDesc(String regionPerilDesc) {
        this.regionPerilDesc = regionPerilDesc;
    }

    @Basic
    @Column(name = "parentMinimumGrainRegionPeril")
    public String getParentMinimumGrainRegionPeril() {
        return parentMinimumGrainRegionPeril;
    }

    public void setParentMinimumGrainRegionPeril(String parentMinimumGrainRegionPeril) {
        this.parentMinimumGrainRegionPeril = parentMinimumGrainRegionPeril;
    }


    @Override
    public int hashCode() {
        return Objects.hash(pltId, pltIdMongo, pltProjectId, pltProjectIdMongo, pltCreated, purePltId, purePltIdMongo, pltType, pltStatus, pltDefaultName, pltThreadName, pltRegionPerilId, pltRegionPerilIdMongo, pltPerilCode, pltSimSet, pltImportSeq, pltAdjustStrutureId, pltAdjustStructureIdMongo, inuringPackageId, inuringPackageIdMongo, pltPublishPricing, regionPerilCode, regionPerilDesc, parentMinimumGrainRegionPeril);
    }
}
