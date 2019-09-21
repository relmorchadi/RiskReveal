package com.scor.rr.repository;

import com.scor.rr.domain.FacRmsAnalysisDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FacRmsAnalysisDetailRepository extends JpaRepository<FacRmsAnalysisDetail, Integer> {

    List<FacRmsAnalysisDetail> findByAnalysisIdAndAnalysisName(Integer analysisId, String analysisName);
}
