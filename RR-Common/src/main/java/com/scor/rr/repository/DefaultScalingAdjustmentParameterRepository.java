package com.scor.rr.repository;

import com.scor.rr.domain.DefaultScalingAdjustmentParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface DefaultScalingAdjustmentParameterRepository extends JpaRepository<DefaultScalingAdjustmentParameter,Integer> {
    DefaultScalingAdjustmentParameter findByDefaultAdjustmentNodeDefaultAdjustmentNodeId(Integer defaultAdjustmentNodeId);
}
