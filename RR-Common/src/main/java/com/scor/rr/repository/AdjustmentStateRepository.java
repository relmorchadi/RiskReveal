package com.scor.rr.repository;

import com.scor.rr.domain.AdjustmentState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AdjustmentStateRepository extends JpaRepository<AdjustmentState, Integer>{
    @Query("select m from AdjustmentState m where m.code = 'Invalid'")
    AdjustmentState getAdjustmentStateEntityByCodeInvalid();
}
