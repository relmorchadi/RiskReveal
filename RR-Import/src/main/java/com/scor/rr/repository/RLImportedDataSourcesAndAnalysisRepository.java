package com.scor.rr.repository;

import com.scor.rr.domain.views.RLImportedDataSourcesAndAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RLImportedDataSourcesAndAnalysisRepository extends JpaRepository<RLImportedDataSourcesAndAnalysis, Long> {

    List<RLImportedDataSourcesAndAnalysis> findByProjectId(Long projectId);
}
