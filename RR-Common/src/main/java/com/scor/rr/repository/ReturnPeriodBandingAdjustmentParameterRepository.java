package com.scor.rr.repository;

import com.scor.rr.domain.AdjustmentNode;
import com.scor.rr.domain.ReturnPeriodBandingAdjustmentParameter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ReturnPeriodBandingAdjustmentParameterRepository extends JpaRepository<ReturnPeriodBandingAdjustmentParameter, Integer> {
//    @Query("select p from AdjustmentReturnPeriodBandingParameterEntity p inner join AdjustmentNodeEntity n where p.adjustmentNode = n and n.adjustmentNodeId = :id")
    List<ReturnPeriodBandingAdjustmentParameter> findByAdjustmentNodeAdjustmentNodeId(Integer id);
    List<ReturnPeriodBandingAdjustmentParameter> findByAdjustmentNode(AdjustmentNode node);

    void deleteByAdjustmentNode_AdjustmentNodeId(Integer adjustmentNode_adjustmentNodeId); // TODO framework ?
}
