package com.scor.rr.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "SECTION", schema = "dbo", catalog = "RiskReveal")
public class SectionEntity {
    private String id;
    private Boolean isactive;
    private Timestamp lastsynchronized;
    private String fkcontractId;
    private String treatyid;
    private Integer uwyear;
    private Integer uworder;
    private Integer endorsmentnumber;
    private Integer sectionid;
    private String sectionlabel;
    private String statusId;
    private String lineofbusinessId;
    private String scopeofbusinessId;
    private String typeofpolicyId;
    private String naturecodeId;
    private Double cededshare;
    private Double scorwrittenshareofcededshare;
    private Double scorsignedshareofcededshare;
    private Double scorexpectedshareofcededshare;
    private Boolean iseq;
    private Boolean isws;
    private Boolean isfl;
    private String egpicurrencyId;
    private Double scoregpi;
    private String liabilitycurrencyId;
    private Timestamp updateTime;
    private String cancellationdate;
    private String premiumrunoff;
    private String claimrunoff;
    private String premiumcutoff;
    private String claimcutoff;
    private String runoffyearsduration;
    private String liabilitybyrisk;
    private String liabilitybyevent;
    private String sectiontype;
    private String accountingtype;
    private String workingcat;
    private Timestamp lastupdateomega;
    private Timestamp lastextractomega;
    private Timestamp lastupdatecatdomain;
    private Timestamp lastsyncruncatdomain;
    private String reportingcountry;
    private String oldtreaty;
    private Integer oldsection;
    private Integer olduwyear;
    private Integer accumulationPackagePltContractSectionId;

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
    @Column(name = "FKCONTRACT_ID", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getFkcontractId() {
        return fkcontractId;
    }

    public void setFkcontractId(String fkcontractId) {
        this.fkcontractId = fkcontractId;
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
    @Column(name = "SECTIONLABEL", nullable = true, length = 1)
    public String getSectionlabel() {
        return sectionlabel;
    }

    public void setSectionlabel(String sectionlabel) {
        this.sectionlabel = sectionlabel;
    }

    @Basic
    @Column(name = "STATUS_ID", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    @Basic
    @Column(name = "LINEOFBUSINESS_ID", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getLineofbusinessId() {
        return lineofbusinessId;
    }

    public void setLineofbusinessId(String lineofbusinessId) {
        this.lineofbusinessId = lineofbusinessId;
    }

    @Basic
    @Column(name = "SCOPEOFBUSINESS_ID", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getScopeofbusinessId() {
        return scopeofbusinessId;
    }

    public void setScopeofbusinessId(String scopeofbusinessId) {
        this.scopeofbusinessId = scopeofbusinessId;
    }

    @Basic
    @Column(name = "TYPEOFPOLICY_ID", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getTypeofpolicyId() {
        return typeofpolicyId;
    }

    public void setTypeofpolicyId(String typeofpolicyId) {
        this.typeofpolicyId = typeofpolicyId;
    }

    @Basic
    @Column(name = "NATURECODE_ID", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getNaturecodeId() {
        return naturecodeId;
    }

    public void setNaturecodeId(String naturecodeId) {
        this.naturecodeId = naturecodeId;
    }

    @Basic
    @Column(name = "CEDEDSHARE", nullable = true, precision = 0)
    public Double getCededshare() {
        return cededshare;
    }

    public void setCededshare(Double cededshare) {
        this.cededshare = cededshare;
    }

    @Basic
    @Column(name = "SCORWRITTENSHAREOFCEDEDSHARE", nullable = true, precision = 0)
    public Double getScorwrittenshareofcededshare() {
        return scorwrittenshareofcededshare;
    }

    public void setScorwrittenshareofcededshare(Double scorwrittenshareofcededshare) {
        this.scorwrittenshareofcededshare = scorwrittenshareofcededshare;
    }

    @Basic
    @Column(name = "SCORSIGNEDSHAREOFCEDEDSHARE", nullable = true, precision = 0)
    public Double getScorsignedshareofcededshare() {
        return scorsignedshareofcededshare;
    }

    public void setScorsignedshareofcededshare(Double scorsignedshareofcededshare) {
        this.scorsignedshareofcededshare = scorsignedshareofcededshare;
    }

    @Basic
    @Column(name = "SCOREXPECTEDSHAREOFCEDEDSHARE", nullable = true, precision = 0)
    public Double getScorexpectedshareofcededshare() {
        return scorexpectedshareofcededshare;
    }

    public void setScorexpectedshareofcededshare(Double scorexpectedshareofcededshare) {
        this.scorexpectedshareofcededshare = scorexpectedshareofcededshare;
    }

    @Basic
    @Column(name = "ISEQ", nullable = true)
    public Boolean getIseq() {
        return iseq;
    }

    public void setIseq(Boolean iseq) {
        this.iseq = iseq;
    }

    @Basic
    @Column(name = "ISWS", nullable = true)
    public Boolean getIsws() {
        return isws;
    }

    public void setIsws(Boolean isws) {
        this.isws = isws;
    }

    @Basic
    @Column(name = "ISFL", nullable = true)
    public Boolean getIsfl() {
        return isfl;
    }

    public void setIsfl(Boolean isfl) {
        this.isfl = isfl;
    }

    @Basic
    @Column(name = "EGPICURRENCY_ID", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getEgpicurrencyId() {
        return egpicurrencyId;
    }

    public void setEgpicurrencyId(String egpicurrencyId) {
        this.egpicurrencyId = egpicurrencyId;
    }

    @Basic
    @Column(name = "SCOREGPI", nullable = true, precision = 0)
    public Double getScoregpi() {
        return scoregpi;
    }

    public void setScoregpi(Double scoregpi) {
        this.scoregpi = scoregpi;
    }

    @Basic
    @Column(name = "LIABILITYCURRENCY_ID", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getLiabilitycurrencyId() {
        return liabilitycurrencyId;
    }

    public void setLiabilitycurrencyId(String liabilitycurrencyId) {
        this.liabilitycurrencyId = liabilitycurrencyId;
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
    @Column(name = "CANCELLATIONDATE", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getCancellationdate() {
        return cancellationdate;
    }

    public void setCancellationdate(String cancellationdate) {
        this.cancellationdate = cancellationdate;
    }

    @Basic
    @Column(name = "PREMIUMRUNOFF", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getPremiumrunoff() {
        return premiumrunoff;
    }

    public void setPremiumrunoff(String premiumrunoff) {
        this.premiumrunoff = premiumrunoff;
    }

    @Basic
    @Column(name = "CLAIMRUNOFF", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getClaimrunoff() {
        return claimrunoff;
    }

    public void setClaimrunoff(String claimrunoff) {
        this.claimrunoff = claimrunoff;
    }

    @Basic
    @Column(name = "PREMIUMCUTOFF", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getPremiumcutoff() {
        return premiumcutoff;
    }

    public void setPremiumcutoff(String premiumcutoff) {
        this.premiumcutoff = premiumcutoff;
    }

    @Basic
    @Column(name = "CLAIMCUTOFF", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getClaimcutoff() {
        return claimcutoff;
    }

    public void setClaimcutoff(String claimcutoff) {
        this.claimcutoff = claimcutoff;
    }

    @Basic
    @Column(name = "RUNOFFYEARSDURATION", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getRunoffyearsduration() {
        return runoffyearsduration;
    }

    public void setRunoffyearsduration(String runoffyearsduration) {
        this.runoffyearsduration = runoffyearsduration;
    }

    @Basic
    @Column(name = "LIABILITYBYRISK", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getLiabilitybyrisk() {
        return liabilitybyrisk;
    }

    public void setLiabilitybyrisk(String liabilitybyrisk) {
        this.liabilitybyrisk = liabilitybyrisk;
    }

    @Basic
    @Column(name = "LIABILITYBYEVENT", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getLiabilitybyevent() {
        return liabilitybyevent;
    }

    public void setLiabilitybyevent(String liabilitybyevent) {
        this.liabilitybyevent = liabilitybyevent;
    }

    @Basic
    @Column(name = "SECTIONTYPE", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getSectiontype() {
        return sectiontype;
    }

    public void setSectiontype(String sectiontype) {
        this.sectiontype = sectiontype;
    }

    @Basic
    @Column(name = "ACCOUNTINGTYPE", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getAccountingtype() {
        return accountingtype;
    }

    public void setAccountingtype(String accountingtype) {
        this.accountingtype = accountingtype;
    }

    @Basic
    @Column(name = "WORKINGCAT", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getWorkingcat() {
        return workingcat;
    }

    public void setWorkingcat(String workingcat) {
        this.workingcat = workingcat;
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

    @Basic
    @Column(name = "REPORTINGCOUNTRY", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getReportingcountry() {
        return reportingcountry;
    }

    public void setReportingcountry(String reportingcountry) {
        this.reportingcountry = reportingcountry;
    }

    @Basic
    @Column(name = "OLDTREATY", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getOldtreaty() {
        return oldtreaty;
    }

    public void setOldtreaty(String oldtreaty) {
        this.oldtreaty = oldtreaty;
    }

    @Basic
    @Column(name = "OLDSECTION", nullable = true)
    public Integer getOldsection() {
        return oldsection;
    }

    public void setOldsection(Integer oldsection) {
        this.oldsection = oldsection;
    }

    @Basic
    @Column(name = "OLDUWYEAR", nullable = true)
    public Integer getOlduwyear() {
        return olduwyear;
    }

    public void setOlduwyear(Integer olduwyear) {
        this.olduwyear = olduwyear;
    }

    @Basic
    @Column(name = "accumulationPackagePLTContractSectionId", nullable = true)
    public Integer getAccumulationPackagePltContractSectionId() {
        return accumulationPackagePltContractSectionId;
    }

    public void setAccumulationPackagePltContractSectionId(Integer accumulationPackagePltContractSectionId) {
        this.accumulationPackagePltContractSectionId = accumulationPackagePltContractSectionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SectionEntity that = (SectionEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(isactive, that.isactive) &&
                Objects.equals(lastsynchronized, that.lastsynchronized) &&
                Objects.equals(fkcontractId, that.fkcontractId) &&
                Objects.equals(treatyid, that.treatyid) &&
                Objects.equals(uwyear, that.uwyear) &&
                Objects.equals(uworder, that.uworder) &&
                Objects.equals(endorsmentnumber, that.endorsmentnumber) &&
                Objects.equals(sectionid, that.sectionid) &&
                Objects.equals(sectionlabel, that.sectionlabel) &&
                Objects.equals(statusId, that.statusId) &&
                Objects.equals(lineofbusinessId, that.lineofbusinessId) &&
                Objects.equals(scopeofbusinessId, that.scopeofbusinessId) &&
                Objects.equals(typeofpolicyId, that.typeofpolicyId) &&
                Objects.equals(naturecodeId, that.naturecodeId) &&
                Objects.equals(cededshare, that.cededshare) &&
                Objects.equals(scorwrittenshareofcededshare, that.scorwrittenshareofcededshare) &&
                Objects.equals(scorsignedshareofcededshare, that.scorsignedshareofcededshare) &&
                Objects.equals(scorexpectedshareofcededshare, that.scorexpectedshareofcededshare) &&
                Objects.equals(iseq, that.iseq) &&
                Objects.equals(isws, that.isws) &&
                Objects.equals(isfl, that.isfl) &&
                Objects.equals(egpicurrencyId, that.egpicurrencyId) &&
                Objects.equals(scoregpi, that.scoregpi) &&
                Objects.equals(liabilitycurrencyId, that.liabilitycurrencyId) &&
                Objects.equals(updateTime, that.updateTime) &&
                Objects.equals(cancellationdate, that.cancellationdate) &&
                Objects.equals(premiumrunoff, that.premiumrunoff) &&
                Objects.equals(claimrunoff, that.claimrunoff) &&
                Objects.equals(premiumcutoff, that.premiumcutoff) &&
                Objects.equals(claimcutoff, that.claimcutoff) &&
                Objects.equals(runoffyearsduration, that.runoffyearsduration) &&
                Objects.equals(liabilitybyrisk, that.liabilitybyrisk) &&
                Objects.equals(liabilitybyevent, that.liabilitybyevent) &&
                Objects.equals(sectiontype, that.sectiontype) &&
                Objects.equals(accountingtype, that.accountingtype) &&
                Objects.equals(workingcat, that.workingcat) &&
                Objects.equals(lastupdateomega, that.lastupdateomega) &&
                Objects.equals(lastextractomega, that.lastextractomega) &&
                Objects.equals(lastupdatecatdomain, that.lastupdatecatdomain) &&
                Objects.equals(lastsyncruncatdomain, that.lastsyncruncatdomain) &&
                Objects.equals(reportingcountry, that.reportingcountry) &&
                Objects.equals(oldtreaty, that.oldtreaty) &&
                Objects.equals(oldsection, that.oldsection) &&
                Objects.equals(olduwyear, that.olduwyear) &&
                Objects.equals(accumulationPackagePltContractSectionId, that.accumulationPackagePltContractSectionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isactive, lastsynchronized, fkcontractId, treatyid, uwyear, uworder, endorsmentnumber, sectionid, sectionlabel, statusId, lineofbusinessId, scopeofbusinessId, typeofpolicyId, naturecodeId, cededshare, scorwrittenshareofcededshare, scorsignedshareofcededshare, scorexpectedshareofcededshare, iseq, isws, isfl, egpicurrencyId, scoregpi, liabilitycurrencyId, updateTime, cancellationdate, premiumrunoff, claimrunoff, premiumcutoff, claimcutoff, runoffyearsduration, liabilitybyrisk, liabilitybyevent, sectiontype, accountingtype, workingcat, lastupdateomega, lastextractomega, lastupdatecatdomain, lastsyncruncatdomain, reportingcountry, oldtreaty, oldsection, olduwyear, accumulationPackagePltContractSectionId);
    }
}
