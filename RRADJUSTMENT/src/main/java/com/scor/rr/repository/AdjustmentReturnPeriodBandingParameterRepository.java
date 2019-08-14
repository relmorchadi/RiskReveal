package com.scor.rr.repository;

import com.scor.rr.domain.AdjustmentNodeEntity;
import com.scor.rr.domain.AdjustmentReturnPeriodBandingParameterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AdjustmentReturnPeriodBandingParameterRepository extends JpaRepository<AdjustmentReturnPeriodBandingParameterEntity,Integer> {
    @Query("select p from AdjustmentReturnPeriodBandingParameterEntity p inner join AdjustmentNodeEntity n where p.adjustmentNodeByIdAdjustmentNode = n and n.idAdjustmentNode = :id")
    AdjustmentReturnPeriodBandingParameterEntity getAdjustmentReturnPeriodBandingParameterByAdjustmentNodeBy(@Param("id") Integer id);
}
