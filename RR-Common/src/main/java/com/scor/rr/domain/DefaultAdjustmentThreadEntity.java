package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "DefaultAdjustmentThread", schema = "dbo", catalog = "RiskReveal")
public class DefaultAdjustmentThreadEntity {
    private int defaultAdjustmentThreadId;
    private DefaultAdjustmentVersionEntity defaultAdjustmentVersion;

    @Id
    @Column(name = "DefaultAdjustmentThreadId", nullable = false)
    public int getDefaultAdjustmentThreadId() {
        return defaultAdjustmentThreadId;
    }

    public void setDefaultAdjustmentThreadId(int defaultAdjustmentThreadId) {
        this.defaultAdjustmentThreadId = defaultAdjustmentThreadId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DefaultAdjustmentThreadEntity that = (DefaultAdjustmentThreadEntity) o;
        return defaultAdjustmentThreadId == that.defaultAdjustmentThreadId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(defaultAdjustmentThreadId);
    }

    @ManyToOne
    @JoinColumn(name = "DefaultAdjustmentVersion", referencedColumnName = "DefaultAdjustmentVersionId")
    public DefaultAdjustmentVersionEntity getDefaultAdjustmentVersion() {
        return defaultAdjustmentVersion;
    }

    public void setDefaultAdjustmentVersion(DefaultAdjustmentVersionEntity defaultAdjustmentVersion) {
        this.defaultAdjustmentVersion = defaultAdjustmentVersion;
    }
}
