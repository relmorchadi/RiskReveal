package com.scor.rr.repository;

import com.scor.rr.domain.ScalingAdjustmentParameter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdjustmentScalingParameterRepository extends JpaRepository<ScalingAdjustmentParameter, Integer> {
//    @Query("select p from AdjustmentScalingParameterEntity p inner join AdjustmentNodeEntity n on p.adjustmentNode = n where n.adjustmentNodeId = :nodeId")
    ScalingAdjustmentParameter findByAdjustmentNodeAdjustmentNodeId(Integer nodeId);

    void deleteByAdjustmentNode_AdjustmentNodeId(Integer adjustmentNodeId);
}
