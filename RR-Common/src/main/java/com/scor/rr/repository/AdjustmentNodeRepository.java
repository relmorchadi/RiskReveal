package com.scor.rr.repository;

import com.scor.rr.domain.AdjustmentNode;
import com.scor.rr.domain.AdjustmentThread;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdjustmentNodeRepository extends JpaRepository<AdjustmentNode, Integer> {
    List<AdjustmentNode> findByAdjustmentThread(AdjustmentThread adjustmentThread);
}