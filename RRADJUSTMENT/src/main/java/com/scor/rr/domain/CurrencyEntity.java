package com.scor.rr.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "CURRENCY", schema = "dbo", catalog = "RiskReveal")
public class CurrencyEntity {
    private String id;
    private Boolean isactive;
    private Timestamp lastsynchronized;
    private String code;
    private String label;
    private String countrycodeId;
    private Timestamp inceptiondate;
    private Timestamp expirydate;
    private Boolean isreportingcurrency;
    private Integer reportingcurrencycodeId;

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
    @Column(name = "CODE", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "LABEL", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Basic
    @Column(name = "COUNTRYCODE_ID", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getCountrycodeId() {
        return countrycodeId;
    }

    public void setCountrycodeId(String countrycodeId) {
        this.countrycodeId = countrycodeId;
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
    @Column(name = "ISREPORTINGCURRENCY", nullable = true)
    public Boolean getIsreportingcurrency() {
        return isreportingcurrency;
    }

    public void setIsreportingcurrency(Boolean isreportingcurrency) {
        this.isreportingcurrency = isreportingcurrency;
    }

    @Basic
    @Column(name = "REPORTINGCURRENCYCODE_ID", nullable = true)
    public Integer getReportingcurrencycodeId() {
        return reportingcurrencycodeId;
    }

    public void setReportingcurrencycodeId(Integer reportingcurrencycodeId) {
        this.reportingcurrencycodeId = reportingcurrencycodeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrencyEntity that = (CurrencyEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(isactive, that.isactive) &&
                Objects.equals(lastsynchronized, that.lastsynchronized) &&
                Objects.equals(code, that.code) &&
                Objects.equals(label, that.label) &&
                Objects.equals(countrycodeId, that.countrycodeId) &&
                Objects.equals(inceptiondate, that.inceptiondate) &&
                Objects.equals(expirydate, that.expirydate) &&
                Objects.equals(isreportingcurrency, that.isreportingcurrency) &&
                Objects.equals(reportingcurrencycodeId, that.reportingcurrencycodeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isactive, lastsynchronized, code, label, countrycodeId, inceptiondate, expirydate, isreportingcurrency, reportingcurrencycodeId);
    }
}
