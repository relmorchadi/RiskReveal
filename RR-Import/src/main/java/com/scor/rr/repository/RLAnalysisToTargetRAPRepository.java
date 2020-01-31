package com.scor.rr.repository;

import com.scor.rr.domain.views.RLAnalysisToTargetRAP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RLAnalysisToTargetRAPRepository extends JpaRepository<RLAnalysisToTargetRAP, Long> {

    List<RLAnalysisToTargetRAP> findByRlAnalysisId(Long rlAnalysisId);

    @Query("FROM RLAnalysisToTargetRAP WHERE rlAnalysisId=:rlAnalysisId AND isDefault=true")
    List<RLAnalysisToTargetRAP> findByRlAnalysisIdAndDefaultIsTrue(Long rlAnalysisId);

}
