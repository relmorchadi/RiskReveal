package com.scor.rr.repository;

import com.scor.rr.domain.AdjustmentNode;
import com.scor.rr.domain.AdjustmentNodeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface AdjustmentNodeOrderRepository extends JpaRepository<AdjustmentNodeOrder, Integer> {
//    @Query("select m from AdjustmentNodeEntity p inner join AdjustmentNodeOrderEntity m on p.adjustmentNodeId = m.adjustmentNode.adjustmentNodeId inner join AdjustmentThreadEntity t on t.adjustmentThreadId = m.adjustmentThread.adjustmentThreadId where t.adjustmentThreadId = :threadId and p.adjustmentNodeId = :nodeId")
//    AdjustmentNodeOrderEntity getAdjustmentOrderByThreadIdAndNodeId(@Param("threadId") Integer threadId, @Param("nodeId") Integer nodeId);

//    @Query("select m from AdjustmentNodeOrderEntity m inner join AdjustmentThreadEntity t on t.adjustmentThreadId = m.adjustmentThread.adjustmentThreadId where t.adjustmentThreadId = :threadId")
//    List<AdjustmentNodeOrderEntity> getAdjustmentOrderByThread(@Param("threadId") Integer threadId);

//    AdjustmentNodeOrderEntity getAdjustmentNodeOrderEntitiesByAdjustmentThread_AdjustmentThreadIdAndOrderNode(int adjustmentThread_adjustmentThreadId, Integer orderNode);

    AdjustmentNodeOrder findByAdjustmentThreadAdjustmentThreadIdAndAdjustmentOrder(Integer threadId, Integer order);

    AdjustmentNodeOrder findByAdjustmentThreadAdjustmentThreadIdAndAdjustmentNodeAdjustmentNodeId(Integer threadId, Integer nodeId);

    AdjustmentNodeOrder findByAdjustmentNodeAdjustmentNodeId(Integer adjustmentNodeId);

    List<AdjustmentNodeOrder> findByAdjustmentThreadAdjustmentThreadId(Integer adjustmentThreadId);

//    List<AdjustmentNodeOrderEntity> getAdjustmentNodeOrderEntityByAdjustmentThread_AdjustmentThreadIdAndAdjustmentNodeOrderIdGreaterThanEqual(int adjustmentThread_adjustmentThreadId, int adjustmentNodeOrderId);

    void deleteByAdjustmentNodeAdjustmentNodeId(Integer adjustmentNodeId);

    void deleteByAdjustmentThreadAdjustmentThreadId(Long adjustmentThreadId);

    AdjustmentNodeOrder findByAdjustmentNode(AdjustmentNode node);


}
