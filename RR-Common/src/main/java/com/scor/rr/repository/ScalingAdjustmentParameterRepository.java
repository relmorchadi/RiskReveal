package com.scor.rr.repository;

import com.scor.rr.domain.AdjustmentNode;
import com.scor.rr.domain.ScalingAdjustmentParameter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScalingAdjustmentParameterRepository extends JpaRepository<ScalingAdjustmentParameter, Integer> {
//    @Query("select p from AdjustmentScalingParameterEntity p inner join AdjustmentNodeEntity n on p.adjustmentNode = n where n.adjustmentNodeId = :nodeId")
    ScalingAdjustmentParameter findByAdjustmentNodeAdjustmentNodeId(Integer nodeId);
    ScalingAdjustmentParameter findByAdjustmentNode(AdjustmentNode node);

    void deleteByAdjustmentNodeAdjustmentNodeId(Integer adjustmentNodeId);
}
