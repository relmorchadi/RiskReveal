package com.scor.rr.repository;

import com.scor.rr.domain.DefaultEventBasedAdjustmentParameter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DefaultEventBasedAdjustmentParameterRepository extends JpaRepository<DefaultEventBasedAdjustmentParameter,Integer> {
        DefaultEventBasedAdjustmentParameter findByDefaultAdjustmentNodeDefaultAdjustmentNodeId(Integer defaultAdjustmentNodeId);
}

