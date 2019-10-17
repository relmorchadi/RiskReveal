package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "InuringExchangeRate", schema = "dbo", catalog = "RiskReveal")
public class InuringExchangeRateEntity {
    private int inuringExchangeRateId;
    private String targetCurrencyId;
    private String sourceCurrencyId;
    private Integer inuringPackageOperationId;
    private String exchangeRateId;

    @Id
    @Column(name = "InuringExchangeRateId", nullable = false, precision = 0)
    public int getInuringExchangeRateId() {
        return inuringExchangeRateId;
    }

    public void setInuringExchangeRateId(int inuringExchangeRateId) {
        this.inuringExchangeRateId = inuringExchangeRateId;
    }

    @Basic
    @Column(name = "FKTargetCurrencyId", nullable = true, length = 255)
    public String getTargetCurrencyId() {
        return targetCurrencyId;
    }

    public void setTargetCurrencyId(String targetCurrencyId) {
        this.targetCurrencyId = targetCurrencyId;
    }

    @Basic
    @Column(name = "FKSourceCurrencyId", nullable = true, length = 255)
    public String getSourceCurrencyId() {
        return sourceCurrencyId;
    }

    public void setSourceCurrencyId(String sourceCurrencyId) {
        this.sourceCurrencyId = sourceCurrencyId;
    }

    @Basic
    @Column(name = "FKInuringPackageOperationId", nullable = true, precision = 0)
    public Integer getInuringPackageOperationId() {
        return inuringPackageOperationId;
    }

    public void setInuringPackageOperationId(Integer inuringPackageOperationId) {
        this.inuringPackageOperationId = inuringPackageOperationId;
    }

    @Basic
    @Column(name = "FKExchangeRateId", nullable = true, length = 255)
    public String getExchangeRateId() {
        return exchangeRateId;
    }

    public void setExchangeRateId(String exchangeRateId) {
        this.exchangeRateId = exchangeRateId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InuringExchangeRateEntity that = (InuringExchangeRateEntity) o;
        return inuringExchangeRateId == that.inuringExchangeRateId &&
                Objects.equals(targetCurrencyId, that.targetCurrencyId) &&
                Objects.equals(sourceCurrencyId, that.sourceCurrencyId) &&
                Objects.equals(inuringPackageOperationId, that.inuringPackageOperationId) &&
                Objects.equals(exchangeRateId, that.exchangeRateId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inuringExchangeRateId, targetCurrencyId, sourceCurrencyId, inuringPackageOperationId, exchangeRateId);
    }
}
