package com.scor.rr.repository;

import com.scor.rr.domain.AdjustmentNodeEntity;
import com.scor.rr.domain.AdjustmentNodeProcessingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AdjustmentNodeProcessingRepository extends JpaRepository<AdjustmentNodeProcessingEntity, Integer> {
    @Query("SELECT np from AdjustmentNodeProcessingEntity np inner join AdjustmentNodeEntity n on np.adjustmentNode.adjustmentNodeId = n.adjustmentNodeId where n.adjustmentNodeId = :nodeId")
    AdjustmentNodeProcessingEntity findByAdjustmentNodeId(@Param("nodeId") int nodeId);

    AdjustmentNodeProcessingEntity findByAdjustmentNode(AdjustmentNodeEntity adjustmentNodeByFkAdjustmentNode);
}