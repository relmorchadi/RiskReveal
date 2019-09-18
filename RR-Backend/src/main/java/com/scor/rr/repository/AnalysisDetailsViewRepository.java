package com.scor.rr.repository;

import com.scor.rr.domain.views.AnalysisDetailView;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnalysisDetailsViewRepository extends JpaRepository<AnalysisDetailView, String> {

    List<AnalysisDetailView> findAllByAnalysisIdAndAnalysisName(Double analysisId, String analysisName);
}


