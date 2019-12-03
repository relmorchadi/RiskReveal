package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@javax.persistence.Entity
@Table(name = "DefaultAdjustment")
public class DefaultAdjustmentEntity {

    private int defaultAdjustmentId;
    private String engineType;
    private MarketChannelEntity marketChannel;
    private TargetRapEntity targetRap;
    private Integer entity;

    @Basic
    @Column(name = "EngineType", nullable = true, length = 200)
    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    @Id
    @Column(name = "DefaultAdjustmentId", nullable = false)
    public int getDefaultAdjustmentId() {
        return defaultAdjustmentId;
    }

    public void setDefaultAdjustmentId(int defaultAdjustmentId) {
        this.defaultAdjustmentId = defaultAdjustmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DefaultAdjustmentEntity that = (DefaultAdjustmentEntity) o;
        return defaultAdjustmentId == that.defaultAdjustmentId &&
                Objects.equals(engineType, that.engineType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(engineType, defaultAdjustmentId);
    }

    @ManyToOne
    @JoinColumn(name = "MarketChannelId", referencedColumnName = "MarketChannelID")
    public MarketChannelEntity getMarketChannel() {
        return marketChannel;
    }

    public void setMarketChannel(MarketChannelEntity marketChannel) {
        this.marketChannel = marketChannel;
    }

    @ManyToOne
    @JoinColumn(name = "TargetRapId", referencedColumnName = "targetRapId")
    public TargetRapEntity getTargetRap() {
        return targetRap;
    }

    public void setTargetRap(TargetRapEntity targetRap) {
        this.targetRap = targetRap;
    }

    @Column(name = "Entity")
    public Integer getEntity() {
        return entity;
    }

    public void setEntity(Integer entity) {
        this.entity = entity;
    }
}
