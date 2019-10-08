package com.scor.rr.repository;

import com.scor.rr.domain.AdjustmentThreadEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdjustmentThreadRepository extends JpaRepository<AdjustmentThreadEntity, Integer> {
    AdjustmentThreadEntity getAdjustmentThreadEntityByScorPltHeaderByFkScorPltHeaderThreadId_PkScorPltHeaderId(int pltHeaderId);
}