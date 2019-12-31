package com.scor.rr.repository;

import com.scor.rr.domain.MarketChannelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by u004602 on 27/12/2019.
 */
public interface MarketChannelRepository extends JpaRepository<MarketChannelEntity, Long> {
    MarketChannelEntity findByMarketChannelCode(String marketChannelCode);
}
