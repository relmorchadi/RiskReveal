package com.scor.rr.repository;

import com.scor.rr.domain.AnalysisIncludedTargetRAPEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.stream.Stream;

public interface AnalysisIncludedTargetRAPRepository extends JpaRepository<AnalysisIncludedTargetRAPEntity, Long> {

    Stream<AnalysisIncludedTargetRAPEntity> findByModelAnalysisId(Long modelAnalysisId);
}
