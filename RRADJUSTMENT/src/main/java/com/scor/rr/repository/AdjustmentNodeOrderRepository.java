package com.scor.rr.repository;

import com.scor.rr.domain.AdjustmentNodeOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface AdjustmentNodeOrderRepository extends JpaRepository<AdjustmentNodeOrderEntity,Integer> {
    @Query("select m from AdjustmentNodeEntity p inner join AdjustmentNodeOrderEntity m on p.adjustmentNodeId = m.adjustmentNode.adjustmentNodeId inner join AdjustmentThreadEntity t on t.adjustmentThreadId = m.adjustmentThread.adjustmentThreadId where t.adjustmentThreadId = :threadId and p.adjustmentNodeId = :nodeId")
    AdjustmentNodeOrderEntity getAdjustmentOrderByThreadIdAndNodeId(@Param("threadId") Integer threadId,@Param("nodeId") Integer nodeId);

    @Query("select m from AdjustmentNodeOrderEntity m inner join AdjustmentThreadEntity t on t.adjustmentThreadId = m.adjustmentThread.adjustmentThreadId where t.adjustmentThreadId = :threadId")
    List<AdjustmentNodeOrderEntity> getAdjustmentOrderByThread(@Param("threadId") Integer threadId);

    AdjustmentNodeOrderEntity getAdjustmentNodeOrderEntitiesByAdjustmentThread_AdjustmentThreadIdAndOrderNode(int adjustmentThread_adjustmentThreadId, Integer orderNode);

    AdjustmentNodeOrderEntity getAdjustmentNodeOrderEntityByAdjustmentNode_AdjustmentNodeId(int adjustmentNodeId);

    List<AdjustmentNodeOrderEntity> getAdjustmentNodeOrderEntitiesByAdjustmentThread_AdjustmentThreadId(int adjustmentThreadId);

    List<AdjustmentNodeOrderEntity> getAdjustmentNodeOrderEntityByAdjustmentThread_AdjustmentThreadIdAndAdjustmentNodeOrderIdGreaterThanEqual(int adjustmentThread_adjustmentThreadId, int adjustmentNodeOrderId);

    void deleteByAdjustmentNode_AdjustmentNodeId(int adjustmentNodeId);

    void deleteByAdjustmentThread_AdjustmentThreadId(int adjustmentThreadId);
}
