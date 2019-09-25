package com.scor.rr.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "EXCHANGERATE", schema = "dbo", catalog = "RiskReveal")
public class ExchangerateEntity {
    private String id;
    private Boolean isactive;
    private Timestamp lastsynchronized;
    private String currencycodeId;
    private String type;
    private String usd;
    private String eur;
    private String gbp;
    private String sgd;
    private String cad;
    private Timestamp effectivedate;
    private String createdby;
    private Timestamp createddate;
    private String updatedby;
    private Timestamp updateddate;

    @Id
    @Column(name = "EXCHANGERATEID", nullable = false, length = 255)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "ISACTIVE")
    public Boolean getIsactive() {
        return isactive;
    }

    public void setIsactive(Boolean isactive) {
        this.isactive = isactive;
    }

    @Basic
    @Column(name = "LASTSYNCHRONIZED")
    public Timestamp getLastsynchronized() {
        return lastsynchronized;
    }

    public void setLastsynchronized(Timestamp lastsynchronized) {
        this.lastsynchronized = lastsynchronized;
    }

    @Basic
    @Column(name = "CURRENCYCODE", length = 255)
    public String getCurrencycodeId() {
        return currencycodeId;
    }

    public void setCurrencycodeId(String currencycodeId) {
        this.currencycodeId = currencycodeId;
    }

    @Basic
    @Column(name = "TYPE", length = 255)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "USD", length = 255)
    public String getUsd() {
        return usd;
    }

    public void setUsd(String usd) {
        this.usd = usd;
    }

    @Basic
    @Column(name = "EUR", length = 255)
    public String getEur() {
        return eur;
    }

    public void setEur(String eur) {
        this.eur = eur;
    }

    @Basic
    @Column(name = "GBP", length = 255)
    public String getGbp() {
        return gbp;
    }

    public void setGbp(String gbp) {
        this.gbp = gbp;
    }

    @Basic
    @Column(name = "SGD", length = 255)
    public String getSgd() {
        return sgd;
    }

    public void setSgd(String sgd) {
        this.sgd = sgd;
    }

    @Basic
    @Column(name = "CAD", length = 255)
    public String getCad() {
        return cad;
    }

    public void setCad(String cad) {
        this.cad = cad;
    }

    @Basic
    @Column(name = "EFFECTIVEDATE")
    public Timestamp getEffectivedate() {
        return effectivedate;
    }

    public void setEffectivedate(Timestamp effectivedate) {
        this.effectivedate = effectivedate;
    }

    @Basic
    @Column(name = "CREATEDBY", length = 255)
    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    @Basic
    @Column(name = "CREATEDDATE")
    public Timestamp getCreateddate() {
        return createddate;
    }

    public void setCreateddate(Timestamp createddate) {
        this.createddate = createddate;
    }

    @Basic
    @Column(name = "UPDATEDBY", length = 255)
    public String getUpdatedby() {
        return updatedby;
    }

    public void setUpdatedby(String updatedby) {
        this.updatedby = updatedby;
    }

    @Basic
    @Column(name = "UPDATEDDATE")
    public Timestamp getUpdateddate() {
        return updateddate;
    }

    public void setUpdateddate(Timestamp updateddate) {
        this.updateddate = updateddate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExchangerateEntity that = (ExchangerateEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(isactive, that.isactive) &&
                Objects.equals(lastsynchronized, that.lastsynchronized) &&
                Objects.equals(currencycodeId, that.currencycodeId) &&
                Objects.equals(type, that.type) &&
                Objects.equals(usd, that.usd) &&
                Objects.equals(eur, that.eur) &&
                Objects.equals(gbp, that.gbp) &&
                Objects.equals(sgd, that.sgd) &&
                Objects.equals(cad, that.cad) &&
                Objects.equals(effectivedate, that.effectivedate) &&
                Objects.equals(createdby, that.createdby) &&
                Objects.equals(createddate, that.createddate) &&
                Objects.equals(updatedby, that.updatedby) &&
                Objects.equals(updateddate, that.updateddate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isactive, lastsynchronized, currencycodeId, type, usd, eur, gbp, sgd, cad, effectivedate, createdby, createddate, updatedby, updateddate);
    }
}
