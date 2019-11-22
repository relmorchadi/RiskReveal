package com.scor.rr.domain;

import javax.persistence.*;
import javax.persistence.Entity;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "TYPEOFPOLICY", schema = "dbo", catalog = "RiskReveal")
public class TypeofpolicyEntity {
    private String id;
    private Boolean isactive;
    private Timestamp lastsynchronized;
    private String code;
    private String label;
    private String longname;

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
    @Column(name = "CODE", nullable = true, length = 255)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "LABEL", nullable = true, length = 255)
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Basic
    @Column(name = "LONGNAME", nullable = true, length = 255)
    public String getLongname() {
        return longname;
    }

    public void setLongname(String longname) {
        this.longname = longname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TypeofpolicyEntity that = (TypeofpolicyEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(isactive, that.isactive) &&
                Objects.equals(lastsynchronized, that.lastsynchronized) &&
                Objects.equals(code, that.code) &&
                Objects.equals(label, that.label) &&
                Objects.equals(longname, that.longname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isactive, lastsynchronized, code, label, longname);
    }
}
