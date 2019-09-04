package com.scor.rr.repository;

import com.scor.rr.domain.AdjustmentThreadEntity;
import com.scor.rr.domain.ScorPltHeaderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdjustmentThreadRepository extends JpaRepository<AdjustmentThreadEntity, Integer> {
    AdjustmentThreadEntity getAdjustmentThreadEntityByScorPltHeaderByFkScorPltHeaderThreadId(ScorPltHeaderEntity scorPltHeaderByFkScorPltHeaderThreadId);
}