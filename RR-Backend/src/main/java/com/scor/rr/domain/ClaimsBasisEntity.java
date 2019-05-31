package com.scor.rr.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "ClaimsBasis", schema = "dbo", catalog = "RiskReveal")
public class ClaimsBasisEntity {
    private int claimsBasisId;

    @Id
    @Column(name = "ClaimsBasis_Id", nullable = false, precision = 0)
    public int getClaimsBasisId() {
        return claimsBasisId;
    }

    public void setClaimsBasisId(int claimsBasisId) {
        this.claimsBasisId = claimsBasisId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClaimsBasisEntity that = (ClaimsBasisEntity) o;
        return claimsBasisId == that.claimsBasisId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(claimsBasisId);
    }
}
