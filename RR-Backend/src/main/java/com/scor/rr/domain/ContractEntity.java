package com.scor.rr.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "CONTRACT", schema = "dbo", catalog = "RiskReveal")
public class ContractEntity {
    private String id;
    private Boolean isactive;
    private Timestamp lastsynchronized;
    private String fksubsidiaryledgercodeId;
    private String treatyid;
    private Integer uwyear;
    private Integer uworder;
    private Integer endorsmentnumber;
    private String treatylabel;
    private String statusId;
    private Integer subsidiarycodeId;
    private String uwunitidId;
    private String subsidiaryledgercode;
    private Timestamp inceptiondate;
    private Timestamp expirydate;
    private String programid;
    private String programname;
    private String bouquetid;
    private String bouquetname;
    private String cedentidId;
    private String underwriterid;
    private String underwriterfirstname;
    private String underwriterlastname;
    private String liabilitycurrencycodeId;
    private String annuallimitamount;
    private String eventlimitamount;
    private Timestamp updateTime;
    private String leader;
    private String underwritingunitcode;
    private String underwritingunitnamell;
    private String underwritingunitnamels;
    private String ultimategroupcode;
    private String ultimategroupname;
    private String groupsegmentnamelm;
    private String groupsegmentcode;
    private String groupsegmentnamels;
    private String intermediarycode;
    private String uwUnitOmega2;
    private Timestamp lastupdateomega;
    private Timestamp lastextractomega;
    private Timestamp lastupdatecatdomain;
    private Timestamp lastsyncruncatdomain;

    @Id
    @Column(name = "id", nullable = false, length = 255)
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
    @Column(name = "FKSUBSIDIARYLEDGERCODE_ID", nullable = true, length = 255)
    public String getFksubsidiaryledgercodeId() {
        return fksubsidiaryledgercodeId;
    }

    public void setFksubsidiaryledgercodeId(String fksubsidiaryledgercodeId) {
        this.fksubsidiaryledgercodeId = fksubsidiaryledgercodeId;
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
    @Column(name = "TREATYLABEL", nullable = true, length = 255)
    public String getTreatylabel() {
        return treatylabel;
    }

    public void setTreatylabel(String treatylabel) {
        this.treatylabel = treatylabel;
    }

    @Basic
    @Column(name = "STATUS_ID", nullable = true, length = 255)
    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    @Basic
    @Column(name = "SUBSIDIARYCODE_ID", nullable = true)
    public Integer getSubsidiarycodeId() {
        return subsidiarycodeId;
    }

    public void setSubsidiarycodeId(Integer subsidiarycodeId) {
        this.subsidiarycodeId = subsidiarycodeId;
    }

    @Basic
    @Column(name = "UWUNITID_ID", nullable = true, length = 255)
    public String getUwunitidId() {
        return uwunitidId;
    }

    public void setUwunitidId(String uwunitidId) {
        this.uwunitidId = uwunitidId;
    }

    @Basic
    @Column(name = "SUBSIDIARYLEDGERCODE", nullable = true, length = 255)
    public String getSubsidiaryledgercode() {
        return subsidiaryledgercode;
    }

    public void setSubsidiaryledgercode(String subsidiaryledgercode) {
        this.subsidiaryledgercode = subsidiaryledgercode;
    }

    @Basic
    @Column(name = "INCEPTIONDATE", nullable = true)
    public Timestamp getInceptiondate() {
        return inceptiondate;
    }

    public void setInceptiondate(Timestamp inceptiondate) {
        this.inceptiondate = inceptiondate;
    }

    @Basic
    @Column(name = "EXPIRYDATE", nullable = true)
    public Timestamp getExpirydate() {
        return expirydate;
    }

    public void setExpirydate(Timestamp expirydate) {
        this.expirydate = expirydate;
    }

    @Basic
    @Column(name = "PROGRAMID", nullable = true, length = 255)
    public String getProgramid() {
        return programid;
    }

    public void setProgramid(String programid) {
        this.programid = programid;
    }

    @Basic
    @Column(name = "PROGRAMNAME", nullable = true, length = 255)
    public String getProgramname() {
        return programname;
    }

    public void setProgramname(String programname) {
        this.programname = programname;
    }

    @Basic
    @Column(name = "BOUQUETID", nullable = true, length = 255)
    public String getBouquetid() {
        return bouquetid;
    }

    public void setBouquetid(String bouquetid) {
        this.bouquetid = bouquetid;
    }

    @Basic
    @Column(name = "BOUQUETNAME", nullable = true, length = 255)
    public String getBouquetname() {
        return bouquetname;
    }

    public void setBouquetname(String bouquetname) {
        this.bouquetname = bouquetname;
    }

    @Basic
    @Column(name = "CEDENTID_ID", nullable = true, length = 255)
    public String getCedentidId() {
        return cedentidId;
    }

    public void setCedentidId(String cedentidId) {
        this.cedentidId = cedentidId;
    }

    @Basic
    @Column(name = "UNDERWRITERID", nullable = true, length = 255)
    public String getUnderwriterid() {
        return underwriterid;
    }

    public void setUnderwriterid(String underwriterid) {
        this.underwriterid = underwriterid;
    }

    @Basic
    @Column(name = "UNDERWRITERFIRSTNAME", nullable = true, length = 255)
    public String getUnderwriterfirstname() {
        return underwriterfirstname;
    }

    public void setUnderwriterfirstname(String underwriterfirstname) {
        this.underwriterfirstname = underwriterfirstname;
    }

    @Basic
    @Column(name = "UNDERWRITERLASTNAME", nullable = true, length = 255)
    public String getUnderwriterlastname() {
        return underwriterlastname;
    }

    public void setUnderwriterlastname(String underwriterlastname) {
        this.underwriterlastname = underwriterlastname;
    }

    @Basic
    @Column(name = "LIABILITYCURRENCYCODE_ID", nullable = true, length = 255)
    public String getLiabilitycurrencycodeId() {
        return liabilitycurrencycodeId;
    }

    public void setLiabilitycurrencycodeId(String liabilitycurrencycodeId) {
        this.liabilitycurrencycodeId = liabilitycurrencycodeId;
    }

    @Basic
    @Column(name = "ANNUALLIMITAMOUNT", nullable = true, length = 255)
    public String getAnnuallimitamount() {
        return annuallimitamount;
    }

    public void setAnnuallimitamount(String annuallimitamount) {
        this.annuallimitamount = annuallimitamount;
    }

    @Basic
    @Column(name = "EVENTLIMITAMOUNT", nullable = true, length = 255)
    public String getEventlimitamount() {
        return eventlimitamount;
    }

    public void setEventlimitamount(String eventlimitamount) {
        this.eventlimitamount = eventlimitamount;
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
    @Column(name = "LEADER", nullable = true, length = 255)
    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    @Basic
    @Column(name = "UNDERWRITINGUNITCODE", nullable = true, length = 255)
    public String getUnderwritingunitcode() {
        return underwritingunitcode;
    }

    public void setUnderwritingunitcode(String underwritingunitcode) {
        this.underwritingunitcode = underwritingunitcode;
    }

    @Basic
    @Column(name = "UNDERWRITINGUNITNAMELL", nullable = true, length = 255)
    public String getUnderwritingunitnamell() {
        return underwritingunitnamell;
    }

    public void setUnderwritingunitnamell(String underwritingunitnamell) {
        this.underwritingunitnamell = underwritingunitnamell;
    }

    @Basic
    @Column(name = "UNDERWRITINGUNITNAMELS", nullable = true, length = 255)
    public String getUnderwritingunitnamels() {
        return underwritingunitnamels;
    }

    public void setUnderwritingunitnamels(String underwritingunitnamels) {
        this.underwritingunitnamels = underwritingunitnamels;
    }

    @Basic
    @Column(name = "ULTIMATEGROUPCODE", nullable = true, length = 255)
    public String getUltimategroupcode() {
        return ultimategroupcode;
    }

    public void setUltimategroupcode(String ultimategroupcode) {
        this.ultimategroupcode = ultimategroupcode;
    }

    @Basic
    @Column(name = "ULTIMATEGROUPNAME", nullable = true, length = 255)
    public String getUltimategroupname() {
        return ultimategroupname;
    }

    public void setUltimategroupname(String ultimategroupname) {
        this.ultimategroupname = ultimategroupname;
    }

    @Basic
    @Column(name = "GROUPSEGMENTNAMELM", nullable = true, length = 255)
    public String getGroupsegmentnamelm() {
        return groupsegmentnamelm;
    }

    public void setGroupsegmentnamelm(String groupsegmentnamelm) {
        this.groupsegmentnamelm = groupsegmentnamelm;
    }

    @Basic
    @Column(name = "GROUPSEGMENTCODE", nullable = true, length = 255)
    public String getGroupsegmentcode() {
        return groupsegmentcode;
    }

    public void setGroupsegmentcode(String groupsegmentcode) {
        this.groupsegmentcode = groupsegmentcode;
    }

    @Basic
    @Column(name = "GROUPSEGMENTNAMELS", nullable = true, length = 255)
    public String getGroupsegmentnamels() {
        return groupsegmentnamels;
    }

    public void setGroupsegmentnamels(String groupsegmentnamels) {
        this.groupsegmentnamels = groupsegmentnamels;
    }

    @Basic
    @Column(name = "INTERMEDIARYCODE", nullable = true, length = 255)
    public String getIntermediarycode() {
        return intermediarycode;
    }

    public void setIntermediarycode(String intermediarycode) {
        this.intermediarycode = intermediarycode;
    }

    @Basic
    @Column(name = "UWUnitOmega2", nullable = true, length = 255)
    public String getUwUnitOmega2() {
        return uwUnitOmega2;
    }

    public void setUwUnitOmega2(String uwUnitOmega2) {
        this.uwUnitOmega2 = uwUnitOmega2;
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
        ContractEntity that = (ContractEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(isactive, that.isactive) &&
                Objects.equals(lastsynchronized, that.lastsynchronized) &&
                Objects.equals(fksubsidiaryledgercodeId, that.fksubsidiaryledgercodeId) &&
                Objects.equals(treatyid, that.treatyid) &&
                Objects.equals(uwyear, that.uwyear) &&
                Objects.equals(uworder, that.uworder) &&
                Objects.equals(endorsmentnumber, that.endorsmentnumber) &&
                Objects.equals(treatylabel, that.treatylabel) &&
                Objects.equals(statusId, that.statusId) &&
                Objects.equals(subsidiarycodeId, that.subsidiarycodeId) &&
                Objects.equals(uwunitidId, that.uwunitidId) &&
                Objects.equals(subsidiaryledgercode, that.subsidiaryledgercode) &&
                Objects.equals(inceptiondate, that.inceptiondate) &&
                Objects.equals(expirydate, that.expirydate) &&
                Objects.equals(programid, that.programid) &&
                Objects.equals(programname, that.programname) &&
                Objects.equals(bouquetid, that.bouquetid) &&
                Objects.equals(bouquetname, that.bouquetname) &&
                Objects.equals(cedentidId, that.cedentidId) &&
                Objects.equals(underwriterid, that.underwriterid) &&
                Objects.equals(underwriterfirstname, that.underwriterfirstname) &&
                Objects.equals(underwriterlastname, that.underwriterlastname) &&
                Objects.equals(liabilitycurrencycodeId, that.liabilitycurrencycodeId) &&
                Objects.equals(annuallimitamount, that.annuallimitamount) &&
                Objects.equals(eventlimitamount, that.eventlimitamount) &&
                Objects.equals(updateTime, that.updateTime) &&
                Objects.equals(leader, that.leader) &&
                Objects.equals(underwritingunitcode, that.underwritingunitcode) &&
                Objects.equals(underwritingunitnamell, that.underwritingunitnamell) &&
                Objects.equals(underwritingunitnamels, that.underwritingunitnamels) &&
                Objects.equals(ultimategroupcode, that.ultimategroupcode) &&
                Objects.equals(ultimategroupname, that.ultimategroupname) &&
                Objects.equals(groupsegmentnamelm, that.groupsegmentnamelm) &&
                Objects.equals(groupsegmentcode, that.groupsegmentcode) &&
                Objects.equals(groupsegmentnamels, that.groupsegmentnamels) &&
                Objects.equals(intermediarycode, that.intermediarycode) &&
                Objects.equals(uwUnitOmega2, that.uwUnitOmega2) &&
                Objects.equals(lastupdateomega, that.lastupdateomega) &&
                Objects.equals(lastextractomega, that.lastextractomega) &&
                Objects.equals(lastupdatecatdomain, that.lastupdatecatdomain) &&
                Objects.equals(lastsyncruncatdomain, that.lastsyncruncatdomain);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isactive, lastsynchronized, fksubsidiaryledgercodeId, treatyid, uwyear, uworder, endorsmentnumber, treatylabel, statusId, subsidiarycodeId, uwunitidId, subsidiaryledgercode, inceptiondate, expirydate, programid, programname, bouquetid, bouquetname, cedentidId, underwriterid, underwriterfirstname, underwriterlastname, liabilitycurrencycodeId, annuallimitamount, eventlimitamount, updateTime, leader, underwritingunitcode, underwritingunitnamell, underwritingunitnamels, ultimategroupcode, ultimategroupname, groupsegmentnamelm, groupsegmentcode, groupsegmentnamels, intermediarycode, uwUnitOmega2, lastupdateomega, lastextractomega, lastupdatecatdomain, lastsyncruncatdomain);
    }
}
