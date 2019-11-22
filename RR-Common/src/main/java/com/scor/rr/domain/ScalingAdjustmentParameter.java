package com.scor.rr.domain;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Objects;

@Entity
@Table(name = "ScalingAdjustmentParameter", schema = "dbo", catalog = "RiskReveal")
public class ScalingAdjustmentParameter {
    private Long id;
//    private EntityEntity entity;
    private AdjustmentNode adjustmentNode;
    private Double adjustmentFactor;

    public ScalingAdjustmentParameter(Double adjustmentFactor, AdjustmentNode adjustmentNode) {
        this.adjustmentNode = adjustmentNode;
        this.adjustmentFactor = adjustmentFactor;
    }

    public ScalingAdjustmentParameter() {

    }

    @Id
    @Column(name = "AdjustmentParameterId", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    @ManyToOne
//    @JoinColumn(name = "Entity", referencedColumnName = "EntityId", insertable = false, updatable = false)
//    public EntityEntity getEntity() {
//        return entity;
//    }
//
//    public void setEntity(EntityEntity entity) {
//        this.entity = entity;
//    }

    @ManyToOne
    @JoinColumn(name = "AdjustmentNodeId", referencedColumnName = "AdjustmentNodeId")
    public AdjustmentNode getAdjustmentNode() {
        return adjustmentNode;
    }

    public void setAdjustmentNode(AdjustmentNode adjustmentNode) {
        this.adjustmentNode = adjustmentNode;
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
        ScalingAdjustmentParameter that = (ScalingAdjustmentParameter) o;
        return id == that.id &&
                Objects.equals(adjustmentFactor, that.adjustmentFactor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, adjustmentFactor);
    }
}
