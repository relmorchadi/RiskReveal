package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "InuringReport", schema = "dbo", catalog = "RiskReveal")
public class InuringReportEntity {
    private int inuringReportId;
    private Integer binFileId;
    private Integer inuringPackageId;

    @Id
    @Column(name = "InuringReportId", nullable = false, precision = 0)
    public int getInuringReportId() {
        return inuringReportId;
    }

    public void setInuringReportId(int inuringReportId) {
        this.inuringReportId = inuringReportId;
    }

    @Basic
    @Column(name = "FKBinFileId", nullable = true, precision = 0)
    public Integer getBinFileId() {
        return binFileId;
    }

    public void setBinFileId(Integer binFileId) {
        this.binFileId = binFileId;
    }

    @Basic
    @Column(name = "FKInuringPackageId", nullable = true, precision = 0)
    public Integer getInuringPackageId() {
        return inuringPackageId;
    }

    public void setInuringPackageId(Integer inuringPackageId) {
        this.inuringPackageId = inuringPackageId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InuringReportEntity that = (InuringReportEntity) o;
        return inuringReportId == that.inuringReportId &&
                Objects.equals(binFileId, that.binFileId) &&
                Objects.equals(inuringPackageId, that.inuringPackageId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inuringReportId, binFileId, inuringPackageId);
    }
}
