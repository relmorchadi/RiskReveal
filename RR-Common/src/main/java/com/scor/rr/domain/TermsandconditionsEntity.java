package com.scor.rr.domain;

import javax.persistence.*;
import javax.persistence.Entity;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "TERMSANDCONDITIONS", schema = "dbo", catalog = "RiskReveal")
public class TermsandconditionsEntity {
    private String id;
    private Boolean isactive;
    private Timestamp lastsynchronized;
    private String fksectionId;
    private String treatyid;
    private Integer uwyear;
    private Integer uworder;
    private Integer endorsmentnumber;
    private Integer sectionid;
    private String occurencebasis;
    private String subjectpremiumcurrencyId;
    private Double estimatedsubjectpremium;
    private String subjectpremiumbasisId;
    private Boolean isunlimited;
    private String cededor1Pctshare;
    private Double eventlimit;
    private Double annuallimit;
    private Double annualdeductible;
    private Double eventlimitforeq;
    private Double eventlimitforws;
    private Double eventlimitforfl;
    private Double capacity;
    private Double attachmentpoint;
    private Double aggregatedeductible;
    private Boolean unlimitedreinstatementyn;
    private Timestamp updateTime;
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
    @Column(name = "OCCURENCEBASIS", nullable = true, length = 255)
    public String getOccurencebasis() {
        return occurencebasis;
    }

    public void setOccurencebasis(String occurencebasis) {
        this.occurencebasis = occurencebasis;
    }

    @Basic
    @Column(name = "SUBJECTPREMIUMCURRENCY_ID", nullable = true, length = 255)
    public String getSubjectpremiumcurrencyId() {
        return subjectpremiumcurrencyId;
    }

    public void setSubjectpremiumcurrencyId(String subjectpremiumcurrencyId) {
        this.subjectpremiumcurrencyId = subjectpremiumcurrencyId;
    }

    @Basic
    @Column(name = "ESTIMATEDSUBJECTPREMIUM", nullable = true, precision = 0)
    public Double getEstimatedsubjectpremium() {
        return estimatedsubjectpremium;
    }

    public void setEstimatedsubjectpremium(Double estimatedsubjectpremium) {
        this.estimatedsubjectpremium = estimatedsubjectpremium;
    }

    @Basic
    @Column(name = "SUBJECTPREMIUMBASIS_ID", nullable = true, length = 255)
    public String getSubjectpremiumbasisId() {
        return subjectpremiumbasisId;
    }

    public void setSubjectpremiumbasisId(String subjectpremiumbasisId) {
        this.subjectpremiumbasisId = subjectpremiumbasisId;
    }

    @Basic
    @Column(name = "ISUNLIMITED", nullable = true)
    public Boolean getIsunlimited() {
        return isunlimited;
    }

    public void setIsunlimited(Boolean isunlimited) {
        this.isunlimited = isunlimited;
    }

    @Basic
    @Column(name = "CEDEDOR1PCTSHARE", nullable = true, length = 255)
    public String getCededor1Pctshare() {
        return cededor1Pctshare;
    }

    public void setCededor1Pctshare(String cededor1Pctshare) {
        this.cededor1Pctshare = cededor1Pctshare;
    }

    @Basic
    @Column(name = "EVENTLIMIT", nullable = true, precision = 0)
    public Double getEventlimit() {
        return eventlimit;
    }

    public void setEventlimit(Double eventlimit) {
        this.eventlimit = eventlimit;
    }

    @Basic
    @Column(name = "ANNUALLIMIT", nullable = true, precision = 0)
    public Double getAnnuallimit() {
        return annuallimit;
    }

    public void setAnnuallimit(Double annuallimit) {
        this.annuallimit = annuallimit;
    }

    @Basic
    @Column(name = "ANNUALDEDUCTIBLE", nullable = true, precision = 0)
    public Double getAnnualdeductible() {
        return annualdeductible;
    }

    public void setAnnualdeductible(Double annualdeductible) {
        this.annualdeductible = annualdeductible;
    }

    @Basic
    @Column(name = "EVENTLIMITFOREQ", nullable = true, precision = 0)
    public Double getEventlimitforeq() {
        return eventlimitforeq;
    }

    public void setEventlimitforeq(Double eventlimitforeq) {
        this.eventlimitforeq = eventlimitforeq;
    }

    @Basic
    @Column(name = "EVENTLIMITFORWS", nullable = true, precision = 0)
    public Double getEventlimitforws() {
        return eventlimitforws;
    }

    public void setEventlimitforws(Double eventlimitforws) {
        this.eventlimitforws = eventlimitforws;
    }

    @Basic
    @Column(name = "EVENTLIMITFORFL", nullable = true, precision = 0)
    public Double getEventlimitforfl() {
        return eventlimitforfl;
    }

    public void setEventlimitforfl(Double eventlimitforfl) {
        this.eventlimitforfl = eventlimitforfl;
    }

    @Basic
    @Column(name = "CAPACITY", nullable = true, precision = 0)
    public Double getCapacity() {
        return capacity;
    }

    public void setCapacity(Double capacity) {
        this.capacity = capacity;
    }

    @Basic
    @Column(name = "ATTACHMENTPOINT", nullable = true, precision = 0)
    public Double getAttachmentpoint() {
        return attachmentpoint;
    }

    public void setAttachmentpoint(Double attachmentpoint) {
        this.attachmentpoint = attachmentpoint;
    }

    @Basic
    @Column(name = "AGGREGATEDEDUCTIBLE", nullable = true, precision = 0)
    public Double getAggregatedeductible() {
        return aggregatedeductible;
    }

    public void setAggregatedeductible(Double aggregatedeductible) {
        this.aggregatedeductible = aggregatedeductible;
    }

    @Basic
    @Column(name = "UNLIMITEDREINSTATEMENTYN", nullable = true)
    public Boolean getUnlimitedreinstatementyn() {
        return unlimitedreinstatementyn;
    }

    public void setUnlimitedreinstatementyn(Boolean unlimitedreinstatementyn) {
        this.unlimitedreinstatementyn = unlimitedreinstatementyn;
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
        TermsandconditionsEntity that = (TermsandconditionsEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(isactive, that.isactive) &&
                Objects.equals(lastsynchronized, that.lastsynchronized) &&
                Objects.equals(fksectionId, that.fksectionId) &&
                Objects.equals(treatyid, that.treatyid) &&
                Objects.equals(uwyear, that.uwyear) &&
                Objects.equals(uworder, that.uworder) &&
                Objects.equals(endorsmentnumber, that.endorsmentnumber) &&
                Objects.equals(sectionid, that.sectionid) &&
                Objects.equals(occurencebasis, that.occurencebasis) &&
                Objects.equals(subjectpremiumcurrencyId, that.subjectpremiumcurrencyId) &&
                Objects.equals(estimatedsubjectpremium, that.estimatedsubjectpremium) &&
                Objects.equals(subjectpremiumbasisId, that.subjectpremiumbasisId) &&
                Objects.equals(isunlimited, that.isunlimited) &&
                Objects.equals(cededor1Pctshare, that.cededor1Pctshare) &&
                Objects.equals(eventlimit, that.eventlimit) &&
                Objects.equals(annuallimit, that.annuallimit) &&
                Objects.equals(annualdeductible, that.annualdeductible) &&
                Objects.equals(eventlimitforeq, that.eventlimitforeq) &&
                Objects.equals(eventlimitforws, that.eventlimitforws) &&
                Objects.equals(eventlimitforfl, that.eventlimitforfl) &&
                Objects.equals(capacity, that.capacity) &&
                Objects.equals(attachmentpoint, that.attachmentpoint) &&
                Objects.equals(aggregatedeductible, that.aggregatedeductible) &&
                Objects.equals(unlimitedreinstatementyn, that.unlimitedreinstatementyn) &&
                Objects.equals(updateTime, that.updateTime) &&
                Objects.equals(lastupdateomega, that.lastupdateomega) &&
                Objects.equals(lastextractomega, that.lastextractomega) &&
                Objects.equals(lastupdatecatdomain, that.lastupdatecatdomain) &&
                Objects.equals(lastsyncruncatdomain, that.lastsyncruncatdomain);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isactive, lastsynchronized, fksectionId, treatyid, uwyear, uworder, endorsmentnumber, sectionid, occurencebasis, subjectpremiumcurrencyId, estimatedsubjectpremium, subjectpremiumbasisId, isunlimited, cededor1Pctshare, eventlimit, annuallimit, annualdeductible, eventlimitforeq, eventlimitforws, eventlimitforfl, capacity, attachmentpoint, aggregatedeductible, unlimitedreinstatementyn, updateTime, lastupdateomega, lastextractomega, lastupdatecatdomain, lastsyncruncatdomain);
    }
}
