package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "MarketChannel", schema = "dbo", catalog = "RiskReveal")
public class MarketChannelEntity {
    private int idMarketChannel;
    private String marketChannelCode;
    private String marketChannelDescription;

    @Id
    @Column(name = "id_market_channel", nullable = false)
    public int getIdMarketChannel() {
        return idMarketChannel;
    }

    public void setIdMarketChannel(int idMarketChannel) {
        this.idMarketChannel = idMarketChannel;
    }

    @Basic
    @Column(name = "market_channel_code", nullable = true, length = 50)
    public String getMarketChannelCode() {
        return marketChannelCode;
    }

    public void setMarketChannelCode(String marketChannelCode) {
        this.marketChannelCode = marketChannelCode;
    }

    @Basic
    @Column(name = "market_channel_description", nullable = true, length = 200)
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
        return idMarketChannel == that.idMarketChannel &&
                Objects.equals(marketChannelCode, that.marketChannelCode) &&
                Objects.equals(marketChannelDescription, that.marketChannelDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMarketChannel, marketChannelCode, marketChannelDescription);
    }
}
