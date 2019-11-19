package com.scor.rr.repository;

import com.scor.rr.domain.AdjustmentEventBasedParameterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AdjustmentEventBasedParameterRepository  extends JpaRepository<AdjustmentEventBasedParameterEntity, Long> {
//    @Query("select p from AdjustmentEventBasedParameterEntity p inner join AdjustmentNodeEntity n where p.adjustmentNodeByFkAdjustmentNodeId = n and n.adjustmentNodeId = :id")
//    AdjustmentEventBasedParameterEntity findByAdjustmentNodeAdjustmentNodeId(@Param("id") int idAdjustmentNode);
    AdjustmentEventBasedParameterEntity findByAdjustmentNodeAdjustmentNodeId(Long adjustmentNodeId);

    void deleteByAdjustmentNodeAdjustmentNodeId(Long adjustmentNodeId);
}
