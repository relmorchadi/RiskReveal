package com.scor.rr.repository;

import com.scor.rr.domain.views.RLAnalysisToTargetRAP;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RLAnalysisToTargetRAPRepository extends JpaRepository<RLAnalysisToTargetRAP, Long> {

    List<RLAnalysisToTargetRAP> findByRlAnalysisId(Long rlAnalysisId);

    List<RLAnalysisToTargetRAP> findByRlAnalysisIdAndDefaultIs(Long rlAnalysisId, boolean isDefault);
}
