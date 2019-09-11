package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "RREdmRdm", schema = "dbo", catalog = "RiskReveal")
public class RrEdmRdmEntity {
    private Double dbId;
    private String dbName;
    private String createDt;
    private String dbType;
    private Double versionNum;

    @Id
    @Basic
    @Column(name = "db_id", nullable = true, precision = 0)
    public Double getDbId() {
        return dbId;
    }

    public void setDbId(Double dbId) {
        this.dbId = dbId;
    }

    @Basic
    @Column(name = "db_name", nullable = true, length = 255)
    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    @Basic
    @Column(name = "create_dt", nullable = true, length = 255)
    public String getCreateDt() {
        return createDt;
    }

    public void setCreateDt(String createDt) {
        this.createDt = createDt;
    }

    @Basic
    @Column(name = "db_type", nullable = true, length = 255)
    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    @Basic
    @Column(name = "version_num", nullable = true, precision = 0)
    public Double getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(Double versionNum) {
        this.versionNum = versionNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RrEdmRdmEntity that = (RrEdmRdmEntity) o;
        return Objects.equals(dbId, that.dbId) &&
                Objects.equals(dbName, that.dbName) &&
                Objects.equals(createDt, that.createDt) &&
                Objects.equals(dbType, that.dbType) &&
                Objects.equals(versionNum, that.versionNum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dbId, dbName, createDt, dbType, versionNum);
    }
}
