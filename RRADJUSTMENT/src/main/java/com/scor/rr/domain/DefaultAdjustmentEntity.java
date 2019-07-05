package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "DefaultAdjustment", schema = "dbo", catalog = "RiskReveal")
public class DefaultAdjustmentEntity {
    private int id;
    private String entity;
    private String marketChannel;
    private String engineType;
    private Integer sequence;
    private TargetRapEntity targetRapByTargetRapId;
    private RegionPerilEntity regionPerilByRegionPerilId;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "entity", nullable = true, length = 200)
    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    @Basic
    @Column(name = "market_channel", nullable = true, length = 200)
    public String getMarketChannel() {
        return marketChannel;
    }

    public void setMarketChannel(String marketChannel) {
        this.marketChannel = marketChannel;
    }

    @Basic
    @Column(name = "engine_type", nullable = true, length = 200)
    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    @Basic
    @Column(name = "sequence", nullable = true)
    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DefaultAdjustmentEntity that = (DefaultAdjustmentEntity) o;
        return id == that.id &&
                Objects.equals(entity, that.entity) &&
                Objects.equals(marketChannel, that.marketChannel) &&
                Objects.equals(engineType, that.engineType) &&
                Objects.equals(sequence, that.sequence);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, entity, marketChannel, engineType, sequence);
    }

    @ManyToOne
    @JoinColumn(name = "target_rap_id", referencedColumnName = "targetRapId")
    public TargetRapEntity getTargetRapByTargetRapId() {
        return targetRapByTargetRapId;
    }

    public void setTargetRapByTargetRapId(TargetRapEntity targetRapByTargetRapId) {
        this.targetRapByTargetRapId = targetRapByTargetRapId;
    }

    @ManyToOne
    @JoinColumn(name = "region_peril_id", referencedColumnName = "regionPerilId")
    public RegionPerilEntity getRegionPerilByRegionPerilId() {
        return regionPerilByRegionPerilId;
    }

    public void setRegionPerilByRegionPerilId(RegionPerilEntity regionPerilByRegionPerilId) {
        this.regionPerilByRegionPerilId = regionPerilByRegionPerilId;
    }
}
