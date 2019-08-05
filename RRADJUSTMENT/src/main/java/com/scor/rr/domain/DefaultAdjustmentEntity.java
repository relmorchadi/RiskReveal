package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "DefaultAdjustment", schema = "dbo", catalog = "RiskReveal")
public class DefaultAdjustmentEntity {
    private String engineType;
    private int idDefaultAdjustment;
    private MarketChannelEntity marketChannel;
    private TargetRapEntity targetRap;
    private EntityEntity entity;

    @Basic
    @Column(name = "engine_type", length = 200)
    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    @Id
    @Column(name = "id_default_adjustment", nullable = false)
    public int getIdDefaultAdjustment() {
        return idDefaultAdjustment;
    }

    public void setIdDefaultAdjustment(int idDefaultAdjustment) {
        this.idDefaultAdjustment = idDefaultAdjustment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DefaultAdjustmentEntity that = (DefaultAdjustmentEntity) o;
        return idDefaultAdjustment == that.idDefaultAdjustment &&
                Objects.equals(engineType, that.engineType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(engineType, idDefaultAdjustment);
    }

    @ManyToOne
    @JoinColumn(name = "id_market_channel", referencedColumnName = "id_market_channel")
    public MarketChannelEntity getMarketChannel() {
        return marketChannel;
    }

    public void setMarketChannel(MarketChannelEntity marketChannel) {
        this.marketChannel = marketChannel;
    }

    @ManyToOne
    @JoinColumn(name = "id_target_rap", referencedColumnName = "targetRapId")
    public TargetRapEntity getTargetRap() {
        return targetRap;
    }

    public void setTargetRap(TargetRapEntity targetRap) {
        this.targetRap = targetRap;
    }

    @ManyToOne
    @JoinColumn(name = "id_entity", referencedColumnName = "id_entity")
    public EntityEntity getEntity() {
        return entity;
    }

    public void setEntity(EntityEntity entity) {
        this.entity = entity;
    }
}
