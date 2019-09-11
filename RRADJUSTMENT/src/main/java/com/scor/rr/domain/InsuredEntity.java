package com.scor.rr.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "INSURED", schema = "dbo", catalog = "RiskReveal")
public class InsuredEntity {
    private String id;
    private Boolean isactive;
    private Timestamp lastsynchronized;
    private String code;
    private String name;
    private String secondname;
    private String clientcodeId;

    @Id
    @Column(name = "INSUREDID", nullable = false, length = 255)
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
    @Column(name = "CODE", length = 255)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "NAME", length = 1)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "SECONDNAME", length = 1)
    public String getSecondname() {
        return secondname;
    }

    public void setSecondname(String secondname) {
        this.secondname = secondname;
    }

    @Basic
    @Column(name = "FKCLIENTCODE", length = 255)
    public String getClientcodeId() {
        return clientcodeId;
    }

    public void setClientcodeId(String clientcodeId) {
        this.clientcodeId = clientcodeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InsuredEntity that = (InsuredEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(isactive, that.isactive) &&
                Objects.equals(lastsynchronized, that.lastsynchronized) &&
                Objects.equals(code, that.code) &&
                Objects.equals(name, that.name) &&
                Objects.equals(secondname, that.secondname) &&
                Objects.equals(clientcodeId, that.clientcodeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isactive, lastsynchronized, code, name, secondname, clientcodeId);
    }
}
