package com.scor.rr.repository;

import com.scor.rr.domain.views.RLAnalysisToTargetRAP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RLAnalysisToTargetRAPRepository extends JpaRepository<RLAnalysisToTargetRAP, Long> {

    List<RLAnalysisToTargetRAP> findByRlAnalysisId(Long rlAnalysisId);

    @Query
    List<RLAnalysisToTargetRAP> findByRlAnalysisIdAndDefaultIsTrue(Long rlAnalysisId);
}
