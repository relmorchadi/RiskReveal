package com.scor.rr.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "InuringPltElement", schema = "dbo", catalog = "RiskReveal")
public class InuringPltElementEntity {
    private int inuringPltElementId;

    @Id
    @Column(name = "InuringPltElementId", nullable = false, precision = 0)
    public int getInuringPltElementId() {
        return inuringPltElementId;
    }

    public void setInuringPltElementId(int inuringPltElementId) {
        this.inuringPltElementId = inuringPltElementId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InuringPltElementEntity that = (InuringPltElementEntity) o;
        return inuringPltElementId == that.inuringPltElementId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(inuringPltElementId);
    }
}
