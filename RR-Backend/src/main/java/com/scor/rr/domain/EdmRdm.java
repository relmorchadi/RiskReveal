package com.scor.rr.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

//@Entity
//@Table(name = "RREdmRdm")
public class EdmRdm {

    @Id
    @Column(name = "db_id")
    private Integer id;
    @Column(name = "db_name")
    private String name;
    @Column(name = "create_dt")
    private String createDt;
    @Column(name = "db_type")
    private String type;
    @Column(name = "version_num")
    private Integer versionNum;

    public EdmRdm() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer dbId) {
        this.id = dbId;
    }


    public String getName() {
        return name;
    }

    public void setName(String dbName) {
        this.name = dbName;
    }


    public String getCreateDt() {
        return createDt;
    }

    public void setCreateDt(String createDt) {
        this.createDt = createDt;
    }


    public String getType() {
        return type;
    }

    public void setType(String dbType) {
        this.type = dbType;
    }


    public Integer getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(Integer versionNum) {
        this.versionNum = versionNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EdmRdm edmRdm = (EdmRdm) o;
        return id == edmRdm.id &&
                versionNum == edmRdm.versionNum &&
                Objects.equals(name, edmRdm.name) &&
                Objects.equals(createDt, edmRdm.createDt) &&
                Objects.equals(type, edmRdm.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, createDt, type, versionNum);
    }
}
