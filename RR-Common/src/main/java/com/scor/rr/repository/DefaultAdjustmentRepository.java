package com.scor.rr.repository;

import com.scor.rr.domain.DefaultAdjustmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DefaultAdjustmentRepository extends JpaRepository<DefaultAdjustmentEntity,Integer> {

    List<DefaultAdjustmentEntity> findByTargetRapTargetRapIdEqualsAndMarketChannel_MarketChannelIdAndEngineTypeEqualsAndEntityEntityIdEquals(int targetRap_targetRapId, int marketChannel_marketChannelId, String engineType, int entity_entityId);
}
