package com.scor.rr.repository;

import com.scor.rr.domain.DefaultReturnPeriodBandingAdjustmentParameter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DefaultReturnPeriodBandingAdjustmentParameterRepository extends JpaRepository<DefaultReturnPeriodBandingAdjustmentParameter,Integer> {
    List<DefaultReturnPeriodBandingAdjustmentParameter> findByDefaultAdjustmentNodeDefaultAdjustmentNodeId(Integer defaultAdjustmentNodeId);
}
