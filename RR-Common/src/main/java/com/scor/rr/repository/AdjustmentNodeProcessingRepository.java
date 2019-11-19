package com.scor.rr.repository;

import com.scor.rr.domain.AdjustmentNode;
import com.scor.rr.domain.AdjustmentNodeProcessingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdjustmentNodeProcessingRepository extends JpaRepository<AdjustmentNodeProcessingEntity, Long> {
    AdjustmentNodeProcessingEntity findByAdjustmentNodeAdjustmentNodeId(Long nodeId);

    AdjustmentNodeProcessingEntity findByAdjustmentNode(AdjustmentNode adjustmentNode);
}