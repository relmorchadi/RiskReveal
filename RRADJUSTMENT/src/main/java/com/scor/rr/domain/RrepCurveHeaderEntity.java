package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "RREPCurveHeader", schema = "dbo", catalog = "RiskReveal")
public class RrepCurveHeaderEntity {
    private int rrEpCurveHeaderId;
    private String lossDataType;
    private String financialPerspective;
    private String statisticMetric;
    private String epCurves;
    private String epcFilePath;
    private String epcFileName;

    @Id
    @Column(name = "rrEPCurveHeaderId", nullable = false)
    public int getRrEpCurveHeaderId() {
        return rrEpCurveHeaderId;
    }

    public void setRrEpCurveHeaderId(int rrEpCurveHeaderId) {
        this.rrEpCurveHeaderId = rrEpCurveHeaderId;
    }

    @Basic
    @Column(name = "lossDataType", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getLossDataType() {
        return lossDataType;
    }

    public void setLossDataType(String lossDataType) {
        this.lossDataType = lossDataType;
    }

    @Basic
    @Column(name = "financialPerspective", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getFinancialPerspective() {
        return financialPerspective;
    }

    public void setFinancialPerspective(String financialPerspective) {
        this.financialPerspective = financialPerspective;
    }

    @Basic
    @Column(name = "statisticMetric", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getStatisticMetric() {
        return statisticMetric;
    }

    public void setStatisticMetric(String statisticMetric) {
        this.statisticMetric = statisticMetric;
    }

    @Basic
    @Column(name = "epCurves", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getEpCurves() {
        return epCurves;
    }

    public void setEpCurves(String epCurves) {
        this.epCurves = epCurves;
    }

    @Basic
    @Column(name = "epcFilePath", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getEpcFilePath() {
        return epcFilePath;
    }

    public void setEpcFilePath(String epcFilePath) {
        this.epcFilePath = epcFilePath;
    }

    @Basic
    @Column(name = "epcFileName", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getEpcFileName() {
        return epcFileName;
    }

    public void setEpcFileName(String epcFileName) {
        this.epcFileName = epcFileName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RrepCurveHeaderEntity that = (RrepCurveHeaderEntity) o;
        return rrEpCurveHeaderId == that.rrEpCurveHeaderId &&
                Objects.equals(lossDataType, that.lossDataType) &&
                Objects.equals(financialPerspective, that.financialPerspective) &&
                Objects.equals(statisticMetric, that.statisticMetric) &&
                Objects.equals(epCurves, that.epCurves) &&
                Objects.equals(epcFilePath, that.epcFilePath) &&
                Objects.equals(epcFileName, that.epcFileName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rrEpCurveHeaderId, lossDataType, financialPerspective, statisticMetric, epCurves, epcFilePath, epcFileName);
    }
}
