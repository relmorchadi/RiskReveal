package com.scor.rr.repository;

import com.scor.rr.domain.AdjustmentThreadEntity;
import com.scor.rr.domain.ScorPltHeaderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdjustmentThreadRepository extends JpaRepository<AdjustmentThreadEntity, Integer> {
    List<AdjustmentThreadEntity> getAdjustmentThreadEntitiesByScorPltHeaderByFkScorPltHeaderThreadId(ScorPltHeaderEntity scorPltHeaderByFkScorPltHeaderThreadId);
}