package com.scor.rr.repository;

import com.scor.rr.domain.model.AnalysisIncludedTargetRAP;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.stream.Stream;

public interface AnalysisIncludedTargetRAPRepository extends JpaRepository<AnalysisIncludedTargetRAP, Long> {

    Stream<AnalysisIncludedTargetRAP> findByModelAnalysisId(Long modelAnalysisId);
}
