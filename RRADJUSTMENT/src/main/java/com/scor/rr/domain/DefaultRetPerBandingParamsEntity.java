package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "DefaultRetPerBandingParams", schema = "dbo", catalog = "RiskReveal")
public class DefaultRetPerBandingParamsEntity {
    private int idParam;
    private Double lmf;
    private Double rpmf;
    private String peatDataPath;
    private String adjustmentReturnPeriodPath;
    private DefaultAdjustmentNodeEntity defaultAdjustmentNodeByIdDefaultNode;

    @Id
    @Column(name = "id_param", nullable = false)
    public int getIdParam() {
        return idParam;
    }

    public void setIdParam(int idParam) {
        this.idParam = idParam;
    }

    @Basic
    @Column(name = "lmf", precision = 7)
    public Double getLmf() {
        return lmf;
    }

    public void setLmf(Double lmf) {
        this.lmf = lmf;
    }

    @Basic
    @Column(name = "rpmf", precision = 7)
    public Double getRpmf() {
        return rpmf;
    }

    public void setRpmf(Double rpmf) {
        this.rpmf = rpmf;
    }

    @Basic
    @Column(name = "peat_data_path", length = 200)
    public String getPeatDataPath() {
        return peatDataPath;
    }

    public void setPeatDataPath(String peatDataPath) {
        this.peatDataPath = peatDataPath;
    }

    @Basic
    @Column(name = "adjustment_return_period_path", length = 200)
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
        return idParam == that.idParam &&
                Objects.equals(lmf, that.lmf) &&
                Objects.equals(rpmf, that.rpmf) &&
                Objects.equals(peatDataPath, that.peatDataPath) &&
                Objects.equals(adjustmentReturnPeriodPath, that.adjustmentReturnPeriodPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idParam, lmf, rpmf, peatDataPath, adjustmentReturnPeriodPath);
    }

    @ManyToOne
    @JoinColumn(name = "id_default_node", referencedColumnName = "id_default_adjustment_node")
    public DefaultAdjustmentNodeEntity getDefaultAdjustmentNodeByIdDefaultNode() {
        return defaultAdjustmentNodeByIdDefaultNode;
    }

    public void setDefaultAdjustmentNodeByIdDefaultNode(DefaultAdjustmentNodeEntity defaultAdjustmentNodeByIdDefaultNode) {
        this.defaultAdjustmentNodeByIdDefaultNode = defaultAdjustmentNodeByIdDefaultNode;
    }
}
