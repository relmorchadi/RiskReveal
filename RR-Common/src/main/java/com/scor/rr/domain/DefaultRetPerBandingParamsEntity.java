package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "DefaultRetPerBandingParams", schema = "dbo", catalog = "RiskReveal")
public class DefaultRetPerBandingParamsEntity {
    private int defaultRetPerBandingParamsId;
    private Double lmf;
    private Double rpmf;
    private String peatDataPath;
    private String adjustmentReturnPeriodPath;
    private DefaultAdjustmentNodeEntity defaultAdjustmentNodeByFkDefaultNode;

    @Id
    @Column(name = "DefaultRetPerBandingParamsId", nullable = false)
    public int getDefaultRetPerBandingParamsId() {
        return defaultRetPerBandingParamsId;
    }

    public void setDefaultRetPerBandingParamsId(int defaultRetPerBandingParamsId) {
        this.defaultRetPerBandingParamsId = defaultRetPerBandingParamsId;
    }

    @Basic
    @Column(name = "lmf", nullable = true, precision = 7)
    public Double getLmf() {
        return lmf;
    }

    public void setLmf(Double lmf) {
        this.lmf = lmf;
    }

    @Basic
    @Column(name = "rpmf", nullable = true, precision = 7)
    public Double getRpmf() {
        return rpmf;
    }

    public void setRpmf(Double rpmf) {
        this.rpmf = rpmf;
    }

    @Basic
    @Column(name = "PeatDataPath", nullable = true, length = 200)
    public String getPeatDataPath() {
        return peatDataPath;
    }

    public void setPeatDataPath(String peatDataPath) {
        this.peatDataPath = peatDataPath;
    }

    @Basic
    @Column(name = "AdjustmentReturnPeriodPath", nullable = true, length = 200)
    public String getAdjustmentReturnPeriodPath() {
        return adjustmentReturnPeriodPath;
    }

    public void setAdjustmentReturnPeriodPath(String adjustmentReturnPeriodPath) {
        this.adjustmentReturnPeriodPath = adjustmentReturnPeriodPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DefaultRetPerBandingParamsEntity that = (DefaultRetPerBandingParamsEntity) o;
        return defaultRetPerBandingParamsId == that.defaultRetPerBandingParamsId &&
                Objects.equals(lmf, that.lmf) &&
                Objects.equals(rpmf, that.rpmf) &&
                Objects.equals(peatDataPath, that.peatDataPath) &&
                Objects.equals(adjustmentReturnPeriodPath, that.adjustmentReturnPeriodPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(defaultRetPerBandingParamsId, lmf, rpmf, peatDataPath, adjustmentReturnPeriodPath);
    }

    @ManyToOne
    @JoinColumn(name = "DefaultNode", referencedColumnName = "DefaultAdjustmentNodeId")
    public DefaultAdjustmentNodeEntity getDefaultAdjustmentNodeByFkDefaultNode() {
        return defaultAdjustmentNodeByFkDefaultNode;
    }

    public void setDefaultAdjustmentNodeByFkDefaultNode(DefaultAdjustmentNodeEntity defaultAdjustmentNodeByFkDefaultNode) {
        this.defaultAdjustmentNodeByFkDefaultNode = defaultAdjustmentNodeByFkDefaultNode;
    }
}
