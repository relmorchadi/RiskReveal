package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "AccumulationPackagePLTContractSection", schema = "dbo", catalog = "RiskReveal")
public class AccumulationPackagePltContractSectionEntity {
    private int id;
    private Integer fkscorpltheader;
    private Integer fkaccumulationPackageid;

    @Id
    @Column(name = "AccumulationPackagePLTContractSectionId", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "ScorPltHeaderId")
    public Integer getFkscorpltheader() {
        return fkscorpltheader;
    }

    public void setFkscorpltheader(Integer fkscorpltheader) {
        this.fkscorpltheader = fkscorpltheader;
    }

    @Basic
    @Column(name = "AccumulationPackageId")
    public Integer getFkaccumulationPackageid() {
        return fkaccumulationPackageid;
    }

    public void setFkaccumulationPackageid(Integer fkaccumulationPackageid) {
        this.fkaccumulationPackageid = fkaccumulationPackageid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccumulationPackagePltContractSectionEntity that = (AccumulationPackagePltContractSectionEntity) o;
        return id == that.id &&
                Objects.equals(fkscorpltheader, that.fkscorpltheader) &&
                Objects.equals(fkaccumulationPackageid, that.fkaccumulationPackageid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fkscorpltheader, fkaccumulationPackageid);
    }
}
