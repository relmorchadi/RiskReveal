package com.scor.rr.repository;

import com.scor.rr.domain.AdjustmentStateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AdjustmentStateRepository extends JpaRepository<AdjustmentStateEntity,Integer>{
    @Query("select m from AdjustmentStateEntity m where m.code = 'Valid'")
    AdjustmentStateEntity getAdjustmentStateEntityByCodeValid();
}
