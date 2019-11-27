package com.scor.rr.domain;

import javax.persistence.*;
import javax.persistence.Entity;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "COUNTRYPERIL")
public class CountryPerilEntity {
    private String id;
    private Boolean isactive;
    private Timestamp lastsynchronized;
    private String fksectionId;
    private String treatyid;
    private Integer uwyear;
    private Integer uworder;
    private Integer endorsmentnumber;
    private Integer sectionid;
    private String inclusionid;
    private String countrycodeId;
    private String perilcodeId;
    private Timestamp updateTime;
    private Timestamp lastupdateomega;
    private Timestamp lastextractomega;
    private Timestamp lastupdatecatdomain;
    private Timestamp lastsyncruncatdomain;

    @Id
    @Basic
    @Column(name = "COUNTRYPERILID", nullable = true, length = 255)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "ISACTIVE", nullable = true)
    public Boolean getIsactive() {
        return isactive;
    }

    public void setIsactive(Boolean isactive) {
        this.isactive = isactive;
    }

    @Basic
    @Column(name = "LASTSYNCHRONIZED", nullable = true)
    public Timestamp getLastsynchronized() {
        return lastsynchronized;
    }

    public void setLastsynchronized(Timestamp lastsynchronized) {
        this.lastsynchronized = lastsynchronized;
    }

    @Basic
    @Column(name = "FKSECTION_ID", nullable = true, length = 255)
    public String getFksectionId() {
        return fksectionId;
    }

    public void setFksectionId(String fksectionId) {
        this.fksectionId = fksectionId;
    }

    @Basic
    @Column(name = "TREATYID", nullable = true, length = 255)
    public String getTreatyid() {
        return treatyid;
    }

    public void setTreatyid(String treatyid) {
        this.treatyid = treatyid;
    }

    @Basic
    @Column(name = "UWYEAR", nullable = true)
    public Integer getUwyear() {
        return uwyear;
    }

    public void setUwyear(Integer uwyear) {
        this.uwyear = uwyear;
    }

    @Basic
    @Column(name = "UWORDER", nullable = true)
    public Integer getUworder() {
        return uworder;
    }

    public void setUworder(Integer uworder) {
        this.uworder = uworder;
    }

    @Basic
    @Column(name = "ENDORSMENTNUMBER", nullable = true)
    public Integer getEndorsmentnumber() {
        return endorsmentnumber;
    }

    public void setEndorsmentnumber(Integer endorsmentnumber) {
        this.endorsmentnumber = endorsmentnumber;
    }

    @Basic
    @Column(name = "SECTIONID", nullable = true)
    public Integer getSectionid() {
        return sectionid;
    }

    public void setSectionid(Integer sectionid) {
        this.sectionid = sectionid;
    }

    @Basic
    @Column(name = "INCLUSIONID", nullable = true, length = 255)
    public String getInclusionid() {
        return inclusionid;
    }

    public void setInclusionid(String inclusionid) {
        this.inclusionid = inclusionid;
    }

    @Basic
    @Column(name = "FKCOUNTRYCODE", nullable = true, length = 255)
    public String getCountrycodeId() {
        return countrycodeId;
    }

    public void setCountrycodeId(String countrycodeId) {
        this.countrycodeId = countrycodeId;
    }

    @Basic
    @Column(name = "PERILCODE_ID", nullable = true, length = 255)
    public String getPerilcodeId() {
        return perilcodeId;
    }

    public void setPerilcodeId(String perilcodeId) {
        this.perilcodeId = perilcodeId;
    }

    @Basic
    @Column(name = "UPDATE_TIME", nullable = true)
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @Basic
    @Column(name = "LASTUPDATEOMEGA", nullable = true)
    public Timestamp getLastupdateomega() {
        return lastupdateomega;
    }

    public void setLastupdateomega(Timestamp lastupdateomega) {
        this.lastupdateomega = lastupdateomega;
    }

    @Basic
    @Column(name = "LASTEXTRACTOMEGA", nullable = true)
    public Timestamp getLastextractomega() {
        return lastextractomega;
    }

    public void setLastextractomega(Timestamp lastextractomega) {
        this.lastextractomega = lastextractomega;
    }

    @Basic
    @Column(name = "LASTUPDATECATDOMAIN", nullable = true)
    public Timestamp getLastupdatecatdomain() {
        return lastupdatecatdomain;
    }

    public void setLastupdatecatdomain(Timestamp lastupdatecatdomain) {
        this.lastupdatecatdomain = lastupdatecatdomain;
    }

    @Basic
    @Column(name = "LASTSYNCRUNCATDOMAIN", nullable = true)
    public Timestamp getLastsyncruncatdomain() {
        return lastsyncruncatdomain;
    }

    public void setLastsyncruncatdomain(Timestamp lastsyncruncatdomain) {
        this.lastsyncruncatdomain = lastsyncruncatdomain;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CountryPerilEntity that = (CountryPerilEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(isactive, that.isactive) &&
                Objects.equals(lastsynchronized, that.lastsynchronized) &&
                Objects.equals(fksectionId, that.fksectionId) &&
                Objects.equals(treatyid, that.treatyid) &&
                Objects.equals(uwyear, that.uwyear) &&
                Objects.equals(uworder, that.uworder) &&
                Objects.equals(endorsmentnumber, that.endorsmentnumber) &&
                Objects.equals(sectionid, that.sectionid) &&
                Objects.equals(inclusionid, that.inclusionid) &&
                Objects.equals(countrycodeId, that.countrycodeId) &&
                Objects.equals(perilcodeId, that.perilcodeId) &&
                Objects.equals(updateTime, that.updateTime) &&
                Objects.equals(lastupdateomega, that.lastupdateomega) &&
                Objects.equals(lastextractomega, that.lastextractomega) &&
                Objects.equals(lastupdatecatdomain, that.lastupdatecatdomain) &&
                Objects.equals(lastsyncruncatdomain, that.lastsyncruncatdomain);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isactive, lastsynchronized, fksectionId, treatyid, uwyear, uworder, endorsmentnumber, sectionid, inclusionid, countrycodeId, perilcodeId, updateTime, lastupdateomega, lastextractomega, lastupdatecatdomain, lastsyncruncatdomain);
    }
}
