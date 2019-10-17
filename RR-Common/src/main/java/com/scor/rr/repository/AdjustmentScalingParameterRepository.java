package com.scor.rr.repository;

import com.scor.rr.domain.AdjustmentScalingParameterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AdjustmentScalingParameterRepository extends JpaRepository<AdjustmentScalingParameterEntity,Integer> {
    @Query("select p from AdjustmentScalingParameterEntity p inner join AdjustmentNodeEntity n on p.adjustmentNodeByFkAdjustmentNodeScaling = n where n.adjustmentNodeId = :id")
    AdjustmentScalingParameterEntity getAdjustmentScalingParameterByAdjustmentNodeBy(@Param("id") Integer id);

    void deleteByAdjustmentNodeByFkAdjustmentNodeScaling_AdjustmentNodeId(int adjustmentNodeId);
}
