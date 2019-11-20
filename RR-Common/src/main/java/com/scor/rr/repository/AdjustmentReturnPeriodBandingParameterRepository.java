package com.scor.rr.repository;

import com.scor.rr.domain.ReturnPeriodBandingAdjustmentParameter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface AdjustmentReturnPeriodBandingParameterRepository extends JpaRepository<ReturnPeriodBandingAdjustmentParameter,Integer> {
//    @Query("select p from AdjustmentReturnPeriodBandingParameterEntity p inner join AdjustmentNodeEntity n where p.adjustmentNode = n and n.adjustmentNodeId = :id")
    List<ReturnPeriodBandingAdjustmentParameter> findByAdjustmentNodeAdjustmentNodeId(Long id);

    void deleteByAdjustmentNode_AdjustmentNodeId(Long adjustmentNode_adjustmentNodeId); // TODO framework ?
}
