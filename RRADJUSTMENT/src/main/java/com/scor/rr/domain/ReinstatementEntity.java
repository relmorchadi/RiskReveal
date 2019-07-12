package com.scor.rr.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "REINSTATEMENT", schema = "dbo", catalog = "RiskReveal")
public class ReinstatementEntity {
    private String id;
    private Boolean isactive;
    private Timestamp lastsynchronized;
    private String treatyid;
    private Integer uwyear;
    private Integer uworder;
    private Integer endorsmentnumber;
    private Integer sectionid;
    private Integer rank;
    private String reinstatementlabel;
    private Double premium;
    private Double proportioncededrate;
    private Boolean proratatemporisyn;
    private Timestamp updateTime;
    private Timestamp lastupdateomega;
    private Timestamp lastextractomega;
    private Timestamp lastupdatecatdomain;
    private Timestamp lastsyncruncatdomain;

    @Id
    @Column(name = "id", nullable = false, length = 255,insertable = false ,updatable = false)
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
    @Column(name = "TREATYID", nullable = true, length = 255,insertable = false ,updatable = false)
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
    @Column(name = "RANK", nullable = true)
    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    @Basic
    @Column(name = "REINSTATEMENTLABEL", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getReinstatementlabel() {
        return reinstatementlabel;
    }

    public void setReinstatementlabel(String reinstatementlabel) {
        this.reinstatementlabel = reinstatementlabel;
    }

    @Basic
    @Column(name = "PREMIUM", nullable = true, precision = 0)
    public Double getPremium() {
        return premium;
    }

    public void setPremium(Double premium) {
        this.premium = premium;
    }

    @Basic
    @Column(name = "PROPORTIONCEDEDRATE", nullable = true, precision = 0)
    public Double getProportioncededrate() {
        return proportioncededrate;
    }

    public void setProportioncededrate(Double proportioncededrate) {
        this.proportioncededrate = proportioncededrate;
    }

    @Basic
    @Column(name = "PRORATATEMPORISYN", nullable = true)
    public Boolean getProratatemporisyn() {
        return proratatemporisyn;
    }

    public void setProratatemporisyn(Boolean proratatemporisyn) {
        this.proratatemporisyn = proratatemporisyn;
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
        ReinstatementEntity that = (ReinstatementEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(isactive, that.isactive) &&
                Objects.equals(lastsynchronized, that.lastsynchronized) &&
                Objects.equals(treatyid, that.treatyid) &&
                Objects.equals(uwyear, that.uwyear) &&
                Objects.equals(uworder, that.uworder) &&
                Objects.equals(endorsmentnumber, that.endorsmentnumber) &&
                Objects.equals(sectionid, that.sectionid) &&
                Objects.equals(rank, that.rank) &&
                Objects.equals(reinstatementlabel, that.reinstatementlabel) &&
                Objects.equals(premium, that.premium) &&
                Objects.equals(proportioncededrate, that.proportioncededrate) &&
                Objects.equals(proratatemporisyn, that.proratatemporisyn) &&
                Objects.equals(updateTime, that.updateTime) &&
                Objects.equals(lastupdateomega, that.lastupdateomega) &&
                Objects.equals(lastextractomega, that.lastextractomega) &&
                Objects.equals(lastupdatecatdomain, that.lastupdatecatdomain) &&
                Objects.equals(lastsyncruncatdomain, that.lastsyncruncatdomain);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isactive, lastsynchronized, treatyid, uwyear, uworder, endorsmentnumber, sectionid, rank, reinstatementlabel, premium, proportioncededrate, proratatemporisyn, updateTime, lastupdateomega, lastextractomega, lastupdatecatdomain, lastsyncruncatdomain);
    }
}
