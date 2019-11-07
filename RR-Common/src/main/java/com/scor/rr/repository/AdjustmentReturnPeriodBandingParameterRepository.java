package com.scor.rr.repository;

import com.scor.rr.domain.AdjustmentReturnPeriodBandingParameterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;


public interface AdjustmentReturnPeriodBandingParameterRepository extends JpaRepository<AdjustmentReturnPeriodBandingParameterEntity,Integer> {
    @Query("select p from AdjustmentReturnPeriodBandingParameterEntity p inner join AdjustmentNodeEntity n where p.adjustmentNode = n and n.adjustmentNodeId = :id")
    List<AdjustmentReturnPeriodBandingParameterEntity> findByNodeId(@Param("id") Integer id);

    void deleteByAdjustmentNode_AdjustmentNodeId(int adjustmentNode_adjustmentNodeId); // TODO framework ?
}
