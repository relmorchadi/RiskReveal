package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "RREdmRdm-old", schema = "dbo", catalog = "RiskReveal")
public class RrEdmRdmOldEntity {
    private int dbId;
    private String createDt;
    private String dbName;
    private String dbType;
    private Integer versionNum;

    @Id
    @Column(name = "db_id", nullable = false)
    public int getDbId() {
        return dbId;
    }

    public void setDbId(int dbId) {
        this.dbId = dbId;
    }

    @Basic
    @Column(name = "create_dt", length = 255)
    public String getCreateDt() {
        return createDt;
    }

    public void setCreateDt(String createDt) {
        this.createDt = createDt;
    }

    @Basic
    @Column(name = "db_name", length = 255)
    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    @Basic
    @Column(name = "db_type", length = 255)
    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    @Basic
    @Column(name = "version_num")
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
        RrEdmRdmOldEntity that = (RrEdmRdmOldEntity) o;
        return dbId == that.dbId &&
                Objects.equals(createDt, that.createDt) &&
                Objects.equals(dbName, that.dbName) &&
                Objects.equals(dbType, that.dbType) &&
                Objects.equals(versionNum, that.versionNum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dbId, createDt, dbName, dbType, versionNum);
    }
}
