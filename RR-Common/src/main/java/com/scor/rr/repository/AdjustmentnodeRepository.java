package com.scor.rr.repository;

import com.scor.rr.domain.AdjustmentNodeEntity;
import com.scor.rr.domain.AdjustmentThreadEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdjustmentnodeRepository extends JpaRepository<AdjustmentNodeEntity, Integer> {
    List<AdjustmentNodeEntity> getAdjustmentNodeEntitiesByAdjustmentThread(AdjustmentThreadEntity adjustmentThread);
}