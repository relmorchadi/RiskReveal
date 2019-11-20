package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "AccumulationPackagePLTContractSection", schema = "dbo", catalog = "RiskReveal")
public class AccumulationPackagePltContractSectionEntity {
    private int id;
    private Integer pltHeaderId;
    private Integer accumulationPackageId;

    @Id
    @Column(name = "AccumulationPackagePLTContractSectionId", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "PltHeaderId", nullable = true)
    public Integer getPltHeaderId() {
        return pltHeaderId;
    }

    public void setPltHeaderId(Integer pltHeaderId) {
        this.pltHeaderId = pltHeaderId;
    }

    @Basic
    @Column(name = "AccumulationPackageId", nullable = true)
    public Integer getAccumulationPackageId() {
        return accumulationPackageId;
    }

    public void setAccumulationPackageId(Integer accumulationPackageId) {
        this.accumulationPackageId = accumulationPackageId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccumulationPackagePltContractSectionEntity that = (AccumulationPackagePltContractSectionEntity) o;
        return id == that.id &&
                Objects.equals(pltHeaderId, that.pltHeaderId) &&
                Objects.equals(accumulationPackageId, that.accumulationPackageId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pltHeaderId, accumulationPackageId);
    }
}
