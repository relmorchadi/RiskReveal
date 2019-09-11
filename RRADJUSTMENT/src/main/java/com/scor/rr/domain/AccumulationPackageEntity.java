package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "AccumulationPackage", schema = "dbo", catalog = "RiskReveal")
public class AccumulationPackageEntity {
    private int id;
    private String fkworkspaceId;
    private Integer idstatus;

    @Id
    @Column(name = "AccumulationPackageId", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "FKWorkspaceID", length = 255)
    public String getFkworkspaceId() {
        return fkworkspaceId;
    }

    public void setFkworkspaceId(String fkworkspaceId) {
        this.fkworkspaceId = fkworkspaceId;
    }


    @Basic
    @Column(name = "FKStatusId")
    public Integer getIdstatus() {
        return idstatus;
    }

    public void setIdstatus(Integer idstatus) {
        this.idstatus = idstatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccumulationPackageEntity that = (AccumulationPackageEntity) o;
        return id == that.id &&
                Objects.equals(fkworkspaceId, that.fkworkspaceId) &&
                Objects.equals(idstatus, that.idstatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fkworkspaceId, idstatus);
    }
}
