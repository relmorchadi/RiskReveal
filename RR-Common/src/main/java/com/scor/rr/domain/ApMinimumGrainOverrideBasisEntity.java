package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "APMinimumGrainOverrideBasis", schema = "dbo", catalog = "RiskReveal")
public class ApMinimumGrainOverrideBasisEntity {
    private int id;
    private Integer fkaccumulationPackageid;
    private String fksectionid;

    @Id
    @Column(name = "APMinimumGrainOverrideBasisId", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "fkaccumulationPackageid", nullable = true)
    public Integer getFkaccumulationPackageid() {
        return fkaccumulationPackageid;
    }

    public void setFkaccumulationPackageid(Integer fkaccumulationPackageid) {
        this.fkaccumulationPackageid = fkaccumulationPackageid;
    }

    @Basic
    @Column(name = "fksectionid", nullable = true, length = 255)
    public String getFksectionid() {
        return fksectionid;
    }

    public void setFksectionid(String fksectionid) {
        this.fksectionid = fksectionid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApMinimumGrainOverrideBasisEntity that = (ApMinimumGrainOverrideBasisEntity) o;
        return id == that.id &&
                Objects.equals(fkaccumulationPackageid, that.fkaccumulationPackageid) &&
                Objects.equals(fksectionid, that.fksectionid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fkaccumulationPackageid, fksectionid);
    }
}
