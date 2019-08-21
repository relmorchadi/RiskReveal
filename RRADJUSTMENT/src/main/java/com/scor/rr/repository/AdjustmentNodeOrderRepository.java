package com.scor.rr.repository;

import com.scor.rr.domain.AdjustmentNodeOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface AdjustmentNodeOrderRepository extends JpaRepository<AdjustmentNodeOrderEntity,Integer> {
    @Query("select m from AdjustmentNodeEntity p inner join AdjustmentNodeOrderEntity m on p.adjustmentNodeId = m.adjustmentNode.adjustmentNodeId inner join AdjustmentThreadEntity t on t.adjustmentThreadId = m.adjustmentThread.adjustmentThreadId where t.adjustmentThreadId = :threadId")
    AdjustmentNodeOrderEntity getAdjustmentOrderByThreadIdAndNodeId(@Param("threadId") Integer threadId,@Param("nodeId") Integer nodeId);
}
