package com.scor.rr.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "COUNTRY", schema = "dbo", catalog = "RiskReveal")
public class CountryEntity {
    private String id;
    private Boolean isactive;
    private Timestamp lastsynchronized;
    private String countrycode;
    private String countryofficialname;
    private String countryshortname;
    private String currencycode;
    private String telephonecode;
    private Boolean embargostatus;
    private Timestamp expirydate;
    private Timestamp inceptiondate;
    private String replaceby;

    @Id
    @Column(name = "id", nullable = false, length = 255,insertable = false ,updatable = false)
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
    @Column(name = "COUNTRYCODE", length = 255,insertable = false ,updatable = false)
    public String getCountrycode() {
        return countrycode;
    }

    public void setCountrycode(String countrycode) {
        this.countrycode = countrycode;
    }

    @Basic
    @Column(name = "COUNTRYOFFICIALNAME", length = 255,insertable = false ,updatable = false)
    public String getCountryofficialname() {
        return countryofficialname;
    }

    public void setCountryofficialname(String countryofficialname) {
        this.countryofficialname = countryofficialname;
    }

    @Basic
    @Column(name = "COUNTRYSHORTNAME", length = 255,insertable = false ,updatable = false)
    public String getCountryshortname() {
        return countryshortname;
    }

    public void setCountryshortname(String countryshortname) {
        this.countryshortname = countryshortname;
    }

    @Basic
    @Column(name = "CURRENCYCODE", length = 255,insertable = false ,updatable = false)
    public String getCurrencycode() {
        return currencycode;
    }

    public void setCurrencycode(String currencycode) {
        this.currencycode = currencycode;
    }

    @Basic
    @Column(name = "TELEPHONECODE", length = 255,insertable = false ,updatable = false)
    public String getTelephonecode() {
        return telephonecode;
    }

    public void setTelephonecode(String telephonecode) {
        this.telephonecode = telephonecode;
    }

    @Basic
    @Column(name = "EMBARGOSTATUS")
    public Boolean getEmbargostatus() {
        return embargostatus;
    }

    public void setEmbargostatus(Boolean embargostatus) {
        this.embargostatus = embargostatus;
    }

    @Basic
    @Column(name = "EXPIRYDATE")
    public Timestamp getExpirydate() {
        return expirydate;
    }

    public void setExpirydate(Timestamp expirydate) {
        this.expirydate = expirydate;
    }

    @Basic
    @Column(name = "INCEPTIONDATE")
    public Timestamp getInceptiondate() {
        return inceptiondate;
    }

    public void setInceptiondate(Timestamp inceptiondate) {
        this.inceptiondate = inceptiondate;
    }

    @Basic
    @Column(name = "REPLACEBY", length = 255,insertable = false ,updatable = false)
    public String getReplaceby() {
        return replaceby;
    }

    public void setReplaceby(String replaceby) {
        this.replaceby = replaceby;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CountryEntity that = (CountryEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(isactive, that.isactive) &&
                Objects.equals(lastsynchronized, that.lastsynchronized) &&
                Objects.equals(countrycode, that.countrycode) &&
                Objects.equals(countryofficialname, that.countryofficialname) &&
                Objects.equals(countryshortname, that.countryshortname) &&
                Objects.equals(currencycode, that.currencycode) &&
                Objects.equals(telephonecode, that.telephonecode) &&
                Objects.equals(embargostatus, that.embargostatus) &&
                Objects.equals(expirydate, that.expirydate) &&
                Objects.equals(inceptiondate, that.inceptiondate) &&
                Objects.equals(replaceby, that.replaceby);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isactive, lastsynchronized, countrycode, countryofficialname, countryshortname, currencycode, telephonecode, embargostatus, expirydate, inceptiondate, replaceby);
    }
}
