package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "DefaultAdjustmentThread", schema = "dbo", catalog = "RiskReveal")
public class DefaultAdjustmentThreadEntity {
    private int idDefaultAdjustmentThread;
    private DefaultAdjustmentVersionEntity defaultAdjustmentVersionByIdDefaultAdjustmentVersion;

    @Id
    @Column(name = "id_default_adjustment_thread", nullable = false)
    public int getIdDefaultAdjustmentThread() {
        return idDefaultAdjustmentThread;
    }

    public void setIdDefaultAdjustmentThread(int idDefaultAdjustmentThread) {
        this.idDefaultAdjustmentThread = idDefaultAdjustmentThread;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DefaultAdjustmentThreadEntity that = (DefaultAdjustmentThreadEntity) o;
        return idDefaultAdjustmentThread == that.idDefaultAdjustmentThread;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idDefaultAdjustmentThread);
    }

    @ManyToOne
    @JoinColumn(name = "id_default_adjustment_version", referencedColumnName = "id_default_adjustment_version")
    public DefaultAdjustmentVersionEntity getDefaultAdjustmentVersionByIdDefaultAdjustmentVersion() {
        return defaultAdjustmentVersionByIdDefaultAdjustmentVersion;
    }

    public void setDefaultAdjustmentVersionByIdDefaultAdjustmentVersion(DefaultAdjustmentVersionEntity defaultAdjustmentVersionByIdDefaultAdjustmentVersion) {
        this.defaultAdjustmentVersionByIdDefaultAdjustmentVersion = defaultAdjustmentVersionByIdDefaultAdjustmentVersion;
    }
}
