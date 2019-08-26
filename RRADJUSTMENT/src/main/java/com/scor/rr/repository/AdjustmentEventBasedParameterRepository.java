package com.scor.rr.repository;

import com.scor.rr.domain.AdjustmentEventBasedParameterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AdjustmentEventBasedParameterRepository  extends JpaRepository<AdjustmentEventBasedParameterEntity,Integer> {
    @Query("select p from AdjustmentEventBasedParameterEntity p inner join AdjustmentNodeEntity n where p.adjustmentNodeByFkAdjustmentNodeId = n and n.adjustmentNodeId = :id")
    AdjustmentEventBasedParameterEntity findAdjustmentEventBasedParameterByAdjustmentNode(@Param("id") int idAdjustmentNode);

    void deleteByAdjustmentNodeByFkAdjustmentNodeId_AdjustmentNodeId(int adjustmentNodeId);
}
