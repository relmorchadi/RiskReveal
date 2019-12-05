package com.scor.rr.repository;

import com.scor.rr.domain.EventBasedAdjustmentParameter;
import com.scor.rr.domain.ReturnPeriodBandingAdjustmentParameter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventBasedAdjustmentParameterRepository extends JpaRepository<EventBasedAdjustmentParameter, Integer> {
    EventBasedAdjustmentParameter findByAdjustmentNodeAdjustmentNodeId(Integer id);
}
