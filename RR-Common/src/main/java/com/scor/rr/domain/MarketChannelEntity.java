package com.scor.rr.domain;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Objects;

@Entity
@Table(name = "MarketChannel", schema = "dbo", catalog = "RiskReveal")
public class MarketChannelEntity {
    private int marketChannelId;
    private String marketChannelCode;
    private String marketChannelDescription;
    private Integer entity;

    @Id
    @Column(name = "MarketChannelID", nullable = false)
    public int getMarketChannelId() {
        return marketChannelId;
    }

    public void setMarketChannelId(int marketChannelId) {
        this.marketChannelId = marketChannelId;
    }

    @Basic
    @Column(name = "MarketChannelCode", nullable = true, length = 50)
    public String getMarketChannelCode() {
        return marketChannelCode;
    }

    public void setMarketChannelCode(String marketChannelCode) {
        this.marketChannelCode = marketChannelCode;
    }

    @Basic
    @Column(name = "MarketChannelDescription", nullable = true, length = 200)
    public String getMarketChannelDescription() {
        return marketChannelDescription;
    }

    public void setMarketChannelDescription(String marketChannelDescription) {
        this.marketChannelDescription = marketChannelDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MarketChannelEntity that = (MarketChannelEntity) o;
        return marketChannelId == that.marketChannelId &&
                Objects.equals(marketChannelCode, that.marketChannelCode) &&
                Objects.equals(marketChannelDescription, that.marketChannelDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(marketChannelId, marketChannelCode, marketChannelDescription);
    }

    @Column(name = "Entity")
    public Integer getEntity() {
        return entity;
    }

    public void setEntity(Integer entity) {
        this.entity = entity;
    }
}
