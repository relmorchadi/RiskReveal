package com.scor.rr.domain;

import javax.persistence.*;
import javax.persistence.Entity;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "RRSummaryStatisticHeader")
public class RrSummaryStatisticHeaderEntity {
    private int rrSummaryStatisticHeaderId;
    private String lossDataType;
    private String financialPerspective;
    private BigDecimal purePremium;
    private BigDecimal standardDeviation;
    private BigDecimal cov;
    private String epsFilePath;
    private String epsFileName;
    private BigDecimal oep100;
    private BigDecimal oep250;
    private BigDecimal oep500;
    private BigDecimal aep100;
    private BigDecimal aep250;
    private BigDecimal aep500;

    @Id
    @Column(name = "rrSummaryStatisticHeaderId", nullable = false)
    public int getRrSummaryStatisticHeaderId() {
        return rrSummaryStatisticHeaderId;
    }

    public void setRrSummaryStatisticHeaderId(int rrSummaryStatisticHeaderId) {
        this.rrSummaryStatisticHeaderId = rrSummaryStatisticHeaderId;
    }

    @Basic
    @Column(name = "lossDataType", nullable = true, length = 255)
    public String getLossDataType() {
        return lossDataType;
    }

    public void setLossDataType(String lossDataType) {
        this.lossDataType = lossDataType;
    }

    @Basic
    @Column(name = "financialPerspective", nullable = true, length = 255)
    public String getFinancialPerspective() {
        return financialPerspective;
    }

    public void setFinancialPerspective(String financialPerspective) {
        this.financialPerspective = financialPerspective;
    }

    @Basic
    @Column(name = "purePremium", nullable = true, precision = 7)
    public BigDecimal getPurePremium() {
        return purePremium;
    }

    public void setPurePremium(BigDecimal purePremium) {
        this.purePremium = purePremium;
    }

    @Basic
    @Column(name = "standardDeviation", nullable = true, precision = 7)
    public BigDecimal getStandardDeviation() {
        return standardDeviation;
    }

    public void setStandardDeviation(BigDecimal standardDeviation) {
        this.standardDeviation = standardDeviation;
    }

    @Basic
    @Column(name = "cov", nullable = true, precision = 7)
    public BigDecimal getCov() {
        return cov;
    }

    public void setCov(BigDecimal cov) {
        this.cov = cov;
    }

    @Basic
    @Column(name = "epsFilePath", nullable = true, length = 255)
    public String getEpsFilePath() {
        return epsFilePath;
    }

    public void setEpsFilePath(String epsFilePath) {
        this.epsFilePath = epsFilePath;
    }

    @Basic
    @Column(name = "epsFileName", nullable = true, length = 255)
    public String getEpsFileName() {
        return epsFileName;
    }

    public void setEpsFileName(String epsFileName) {
        this.epsFileName = epsFileName;
    }

    @Basic
    @Column(name = "OEP100", nullable = true, precision = 7)
    public BigDecimal getOep100() {
        return oep100;
    }

    public void setOep100(BigDecimal oep100) {
        this.oep100 = oep100;
    }

    @Basic
    @Column(name = "OEP250", nullable = true, precision = 7)
    public BigDecimal getOep250() {
        return oep250;
    }

    public void setOep250(BigDecimal oep250) {
        this.oep250 = oep250;
    }

    @Basic
    @Column(name = "OEP500", nullable = true, precision = 7)
    public BigDecimal getOep500() {
        return oep500;
    }

    public void setOep500(BigDecimal oep500) {
        this.oep500 = oep500;
    }

    @Basic
    @Column(name = "AEP100", nullable = true, precision = 7)
    public BigDecimal getAep100() {
        return aep100;
    }

    public void setAep100(BigDecimal aep100) {
        this.aep100 = aep100;
    }

    @Basic
    @Column(name = "AEP250", nullable = true, precision = 7)
    public BigDecimal getAep250() {
        return aep250;
    }

    public void setAep250(BigDecimal aep250) {
        this.aep250 = aep250;
    }

    @Basic
    @Column(name = "AEP500", nullable = true, precision = 7)
    public BigDecimal getAep500() {
        return aep500;
    }

    public void setAep500(BigDecimal aep500) {
        this.aep500 = aep500;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RrSummaryStatisticHeaderEntity that = (RrSummaryStatisticHeaderEntity) o;
        return rrSummaryStatisticHeaderId == that.rrSummaryStatisticHeaderId &&
                Objects.equals(lossDataType, that.lossDataType) &&
                Objects.equals(financialPerspective, that.financialPerspective) &&
                Objects.equals(purePremium, that.purePremium) &&
                Objects.equals(standardDeviation, that.standardDeviation) &&
                Objects.equals(cov, that.cov) &&
                Objects.equals(epsFilePath, that.epsFilePath) &&
                Objects.equals(epsFileName, that.epsFileName) &&
                Objects.equals(oep100, that.oep100) &&
                Objects.equals(oep250, that.oep250) &&
                Objects.equals(oep500, that.oep500) &&
                Objects.equals(aep100, that.aep100) &&
                Objects.equals(aep250, that.aep250) &&
                Objects.equals(aep500, that.aep500);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rrSummaryStatisticHeaderId, lossDataType, financialPerspective, purePremium, standardDeviation, cov, epsFilePath, epsFileName, oep100, oep250, oep500, aep100, aep250, aep500);
    }
}
