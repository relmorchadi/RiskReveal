package com.scor.rr.repository;

import com.scor.rr.domain.AdjustmentThread;
import com.scor.rr.domain.PltHeaderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdjustmentThreadRepository extends JpaRepository<AdjustmentThread, Integer> {
    AdjustmentThread getByFinalPLTPltHeaderId(Long pltHeaderId);
    List<AdjustmentThread> findByInitialPLT(PltHeaderEntity plt);

}