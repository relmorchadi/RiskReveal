package com.scor.rr.repository;

import com.scor.rr.domain.EventBasedAdjustmentParameter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdjustmentEventBasedParameterRepository  extends JpaRepository<EventBasedAdjustmentParameter, Integer> {
//    @Query("select p from AdjustmentEventBasedParameterEntity p inner join AdjustmentNodeEntity n where p.adjustmentNodeByFkAdjustmentNodeId = n and n.adjustmentNodeId = :id")
//    AdjustmentEventBasedParameterEntity findByAdjustmentNodeAdjustmentNodeId(@Param("id") int idAdjustmentNode);
    EventBasedAdjustmentParameter findByAdjustmentNodeAdjustmentNodeId(Long adjustmentNodeId);

    void deleteByAdjustmentNodeAdjustmentNodeId(Integer adjustmentNodeId);
}
