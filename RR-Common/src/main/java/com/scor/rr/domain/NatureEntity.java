package com.scor.rr.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "NATURE", schema = "dbo", catalog = "RiskReveal")
public class NatureEntity {
    private String id;
    private Boolean isactive;
    private Timestamp lastsynchronized;
    private String naturecode;
    private String shortname;
    private String longname;
    private String mnemonic;
    private String generalnature;
    private String familycode;
    private String familylabel;

    @Id
    @Column(name = "NATUREID", nullable = false, length = 255)
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
    @Column(name = "NATURECODE", length = 255)
    public String getNaturecode() {
        return naturecode;
    }

    public void setNaturecode(String naturecode) {
        this.naturecode = naturecode;
    }

    @Basic
    @Column(name = "SHORTNAME", length = 255)
    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    @Basic
    @Column(name = "LONGNAME", length = 255)
    public String getLongname() {
        return longname;
    }

    public void setLongname(String longname) {
        this.longname = longname;
    }

    @Basic
    @Column(name = "MNEMONIC", length = 255)
    public String getMnemonic() {
        return mnemonic;
    }

    public void setMnemonic(String mnemonic) {
        this.mnemonic = mnemonic;
    }

    @Basic
    @Column(name = "GENERALNATURE", length = 255)
    public String getGeneralnature() {
        return generalnature;
    }

    public void setGeneralnature(String generalnature) {
        this.generalnature = generalnature;
    }

    @Basic
    @Column(name = "FAMILYCODE", length = 255)
    public String getFamilycode() {
        return familycode;
    }

    public void setFamilycode(String familycode) {
        this.familycode = familycode;
    }

    @Basic
    @Column(name = "FAMILYLABEL", length = 255)
    public String getFamilylabel() {
        return familylabel;
    }

    public void setFamilylabel(String familylabel) {
        this.familylabel = familylabel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NatureEntity that = (NatureEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(isactive, that.isactive) &&
                Objects.equals(lastsynchronized, that.lastsynchronized) &&
                Objects.equals(naturecode, that.naturecode) &&
                Objects.equals(shortname, that.shortname) &&
                Objects.equals(longname, that.longname) &&
                Objects.equals(mnemonic, that.mnemonic) &&
                Objects.equals(generalnature, that.generalnature) &&
                Objects.equals(familycode, that.familycode) &&
                Objects.equals(familylabel, that.familylabel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isactive, lastsynchronized, naturecode, shortname, longname, mnemonic, generalnature, familycode, familylabel);
    }
}
