package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "DefaultScalingAdjustmentParameter", schema = "dbo", catalog = "RiskReveal")
public class DefaultScalingAdjustmentParameter {
    private Long id;
    private EntityEntity entity;
    private DefaultAdjustmentNode defaultAdjustmentNode;
    private Double adjustmentFactor; // lmf

    @Id
    @Column(name = "DefaultScalingAdjustmentParameterId", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "Entity", referencedColumnName = "EntityId", insertable = false, updatable = false)
    public EntityEntity getEntity() {
        return entity;
    }

    public void setEntity(EntityEntity entity) {
        this.entity = entity;
    }

    @ManyToOne
    @JoinColumn(name = "DefaultAdjustmentNodeId", referencedColumnName = "DefaultAdjustmentNodeId")
    public DefaultAdjustmentNode getDefaultAdjustmentNode() {
        return defaultAdjustmentNode;
    }

    public void setDefaultAdjustmentNode(DefaultAdjustmentNode defaultAdjustmentNode) {
        this.defaultAdjustmentNode = defaultAdjustmentNode;
    }

    @Basic
    @Column(name = "AdjustmentFactor", nullable = true, precision = 7)
    public Double getAdjustmentFactor() {
        return adjustmentFactor;
    }

    public void setAdjustmentFactor(Double adjustmentFactor) {
        this.adjustmentFactor = adjustmentFactor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DefaultScalingAdjustmentParameter that = (DefaultScalingAdjustmentParameter) o;
        return id == that.id &&
                Objects.equals(defaultAdjustmentNode, that.defaultAdjustmentNode) &&
                Objects.equals(adjustmentFactor, that.adjustmentFactor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, defaultAdjustmentNode, adjustmentFactor);
    }

}
