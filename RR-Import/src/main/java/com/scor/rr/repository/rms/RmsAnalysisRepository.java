package com.scor.rr.repository.rms;

import com.scor.rr.domain.entities.rms.RmsAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * RmsAnalysis Repository
 *
 * @author HADDINI Zakariyae
 */
public interface RmsAnalysisRepository extends JpaRepository<RmsAnalysis, String> {

    RmsAnalysis findByRmsAnalysisId(String analysisId);

    RmsAnalysis findByProjectProjectIdAndAnalysisId(String projectId, String analysisId);
}
