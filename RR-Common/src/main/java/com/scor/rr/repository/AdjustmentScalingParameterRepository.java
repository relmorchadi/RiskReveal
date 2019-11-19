package com.scor.rr.repository;

import com.scor.rr.domain.AdjustmentScalingParameterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdjustmentScalingParameterRepository extends JpaRepository<AdjustmentScalingParameterEntity, Long> {
//    @Query("select p from AdjustmentScalingParameterEntity p inner join AdjustmentNodeEntity n on p.adjustmentNode = n where n.adjustmentNodeId = :nodeId")
    AdjustmentScalingParameterEntity findByAdjustmentNodeAdjustmentNodeId(Long nodeId);

    void deleteByAdjustmentNode_AdjustmentNodeId(Long adjustmentNodeId);
}
