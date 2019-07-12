package com.scor.rr.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "SUBSIDIARYLEDGER", schema = "dbo", catalog = "RiskReveal")
public class SubsidiaryledgerEntity {
    private String id;
    private Boolean isactive;
    private Timestamp lastsynchronized;
    private String subsidiaryledgercode;
    private String name;
    private String longnamename;
    private String subsidiarycodeId;

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
    @Column(name = "SUBSIDIARYLEDGERCODE", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getSubsidiaryledgercode() {
        return subsidiaryledgercode;
    }

    public void setSubsidiaryledgercode(String subsidiaryledgercode) {
        this.subsidiaryledgercode = subsidiaryledgercode;
    }

    @Basic
    @Column(name = "NAME", nullable = true, length = 1)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "LONGNAMENAME", nullable = true, length = 1)
    public String getLongnamename() {
        return longnamename;
    }

    public void setLongnamename(String longnamename) {
        this.longnamename = longnamename;
    }

    @Basic
    @Column(name = "SUBSIDIARYCODE_ID", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getSubsidiarycodeId() {
        return subsidiarycodeId;
    }

    public void setSubsidiarycodeId(String subsidiarycodeId) {
        this.subsidiarycodeId = subsidiarycodeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubsidiaryledgerEntity that = (SubsidiaryledgerEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(isactive, that.isactive) &&
                Objects.equals(lastsynchronized, that.lastsynchronized) &&
                Objects.equals(subsidiaryledgercode, that.subsidiaryledgercode) &&
                Objects.equals(name, that.name) &&
                Objects.equals(longnamename, that.longnamename) &&
                Objects.equals(subsidiarycodeId, that.subsidiarycodeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isactive, lastsynchronized, subsidiaryledgercode, name, longnamename, subsidiarycodeId);
    }
}
