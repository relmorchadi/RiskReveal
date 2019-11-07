package com.scor.rr.repository;

import com.scor.rr.domain.AdjustmentNodeEntity;
import com.scor.rr.domain.AdjustmentScalingParameterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AdjustmentScalingParameterRepository extends JpaRepository<AdjustmentScalingParameterEntity,Integer> {
    @Query("select p from AdjustmentScalingParameterEntity p inner join AdjustmentNodeEntity n on p.adjustmentNode = n where n.adjustmentNodeId = :nodeId")
    AdjustmentScalingParameterEntity findByNodeId(@Param("nodeId") Integer nodeId);

    void deleteByAdjustmentNode_AdjustmentNodeId(int adjustmentNodeId);
}
