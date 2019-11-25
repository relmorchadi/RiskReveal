package com.scor.rr.repository;

import com.scor.rr.domain.RrAnalysisEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RranalysisRepository extends JpaRepository<RrAnalysisEntity, Integer> {
    RrAnalysisEntity findByAnalysisId(Integer s);
}

