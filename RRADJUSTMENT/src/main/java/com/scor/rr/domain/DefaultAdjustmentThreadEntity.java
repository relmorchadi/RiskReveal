package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "DefaultAdjustmentThread", schema = "dbo", catalog = "RiskReveal")
public class DefaultAdjustmentThreadEntity {
    private int id;
    private String defaultVersionId;
    private DefaultAdjustmentVersionEntity defaultAdjustmentVersionByIdDefaultAdjustmentVersion;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "default_version_id", nullable = true, length = 50)
    public String getDefaultVersionId() {
        return defaultVersionId;
    }

    public void setDefaultVersionId(String defaultVersionId) {
        this.defaultVersionId = defaultVersionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DefaultAdjustmentThreadEntity that = (DefaultAdjustmentThreadEntity) o;
        return id == that.id &&
                Objects.equals(defaultVersionId, that.defaultVersionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, defaultVersionId);
    }

    @ManyToOne
    @JoinColumn(name = "id_default_adjustment_version", referencedColumnName = "id")
    public DefaultAdjustmentVersionEntity getDefaultAdjustmentVersionByIdDefaultAdjustmentVersion() {
        return defaultAdjustmentVersionByIdDefaultAdjustmentVersion;
    }

    public void setDefaultAdjustmentVersionByIdDefaultAdjustmentVersion(DefaultAdjustmentVersionEntity defaultAdjustmentVersionByIdDefaultAdjustmentVersion) {
        this.defaultAdjustmentVersionByIdDefaultAdjustmentVersion = defaultAdjustmentVersionByIdDefaultAdjustmentVersion;
    }
}
