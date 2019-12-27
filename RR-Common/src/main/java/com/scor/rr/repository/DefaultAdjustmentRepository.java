package com.scor.rr.repository;

import com.scor.rr.domain.DefaultAdjustmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface DefaultAdjustmentRepository extends JpaRepository<DefaultAdjustmentEntity,Integer> {

    List<DefaultAdjustmentEntity> findByTargetRapTargetRAPIdEqualsAndMarketChannel_MarketChannelIdAndEngineTypeEqualsAndEntityEquals(
            long targetRap_targetRapId,
            long marketChannel_marketChannelId,
            String engineType,
            int entity_entityId);

    @Transactional
    @Query(value = "exec dbonew.usp_GetDefaultAdjustmentsInScope @workspaceContextCode=:workspaceContextCode, @uwYear=:uwYear", nativeQuery = true)
    List<Map<String, Object>> getDefaultAdjustmentsInScope(@Param("workspaceContextCode") String workspaceContextCode, @Param("uwYear") Integer uwYear);

}
