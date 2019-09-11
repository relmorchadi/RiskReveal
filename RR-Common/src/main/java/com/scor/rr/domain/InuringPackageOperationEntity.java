package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "InuringPackageOperation", schema = "dbo", catalog = "RiskReveal")
public class InuringPackageOperationEntity {
    private int inuringPackageOperationId;
    private String exchangeRateId;
    private Integer inuringPackageId;
    private Integer userId;

    @Id
    @Column(name = "InuringPackageOperationId", nullable = false, precision = 0)
    public int getInuringPackageOperationId() {
        return inuringPackageOperationId;
    }

    public void setInuringPackageOperationId(int inuringPackageOperationId) {
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

    @Basic
    @Column(name = "FKInuringPackageId", nullable = true, precision = 0)
    public Integer getInuringPackageId() {
        return inuringPackageId;
    }

    public void setInuringPackageId(Integer inuringPackageId) {
        this.inuringPackageId = inuringPackageId;
    }

    @Basic
    @Column(name = "FKUserId", nullable = true, precision = 0)
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InuringPackageOperationEntity that = (InuringPackageOperationEntity) o;
        return inuringPackageOperationId == that.inuringPackageOperationId &&
                Objects.equals(exchangeRateId, that.exchangeRateId) &&
                Objects.equals(inuringPackageId, that.inuringPackageId) &&
                Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inuringPackageOperationId, exchangeRateId, inuringPackageId, userId);
    }
}
