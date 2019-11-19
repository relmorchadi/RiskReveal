package com.scor.rr.repository;

import com.scor.rr.domain.AdjustmentNodeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface AdjustmentNodeOrderRepository extends JpaRepository<AdjustmentNodeOrder, Long> {
//    @Query("select m from AdjustmentNodeEntity p inner join AdjustmentNodeOrderEntity m on p.adjustmentNodeId = m.adjustmentNode.adjustmentNodeId inner join AdjustmentThreadEntity t on t.adjustmentThreadId = m.adjustmentThread.adjustmentThreadId where t.adjustmentThreadId = :threadId and p.adjustmentNodeId = :nodeId")
//    AdjustmentNodeOrderEntity getAdjustmentOrderByThreadIdAndNodeId(@Param("threadId") Integer threadId, @Param("nodeId") Integer nodeId);

//    @Query("select m from AdjustmentNodeOrderEntity m inner join AdjustmentThreadEntity t on t.adjustmentThreadId = m.adjustmentThread.adjustmentThreadId where t.adjustmentThreadId = :threadId")
//    List<AdjustmentNodeOrderEntity> getAdjustmentOrderByThread(@Param("threadId") Integer threadId);

//    AdjustmentNodeOrderEntity getAdjustmentNodeOrderEntitiesByAdjustmentThread_AdjustmentThreadIdAndOrderNode(int adjustmentThread_adjustmentThreadId, Integer orderNode);

    AdjustmentNodeOrder findByAdjustmentThreadAdjustmentThreadIdAndAdjustmentOrder(Long threadId, Integer order);

    AdjustmentNodeOrder findByAdjustmentThreadAdjustmentThreadIdAndAdjustmentNodeAdjustmentNodeId(Long threadId, Long nodeId);

    AdjustmentNodeOrder findByAdjustmentNodeAdjustmentNodeId(Long adjustmentNodeId);

    List<AdjustmentNodeOrder> findByAdjustmentThreadAdjustmentThreadId(Long adjustmentThreadId);

//    List<AdjustmentNodeOrderEntity> getAdjustmentNodeOrderEntityByAdjustmentThread_AdjustmentThreadIdAndAdjustmentNodeOrderIdGreaterThanEqual(int adjustmentThread_adjustmentThreadId, int adjustmentNodeOrderId);

    void deleteByAdjustmentNodeAdjustmentNodeId(Long adjustmentNodeId);

    void deleteByAdjustmentThreadAdjustmentThreadId(Long adjustmentThreadId);
}
