package com.scor.rr.repository;

import com.scor.rr.domain.AdjustmentNodeEntity;
import com.scor.rr.domain.AdjustmentNodeProcessingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AdjustmentnodeprocessingRepository extends JpaRepository<AdjustmentNodeProcessingEntity, Integer> {
    @Query("SELECT np from AdjustmentNodeProcessingEntity np inner join AdjustmentNodeEntity n on np.adjustmentNodeByFkAdjustmentNode.adjustmentNodeId = n.adjustmentNodeId where n.adjustmentNodeId = :nodeId")
    AdjustmentNodeProcessingEntity getAdjustmentNodeProcessingEntity(@Param("nodeId") int nodeId);

    AdjustmentNodeProcessingEntity getAdjustmentNodeProcessingEntitiesByAdjustmentNodeByFkAdjustmentNode(AdjustmentNodeEntity adjustmentNodeByFkAdjustmentNode);
}