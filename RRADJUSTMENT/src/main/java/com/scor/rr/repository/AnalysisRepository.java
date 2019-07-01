package com.scor.rr.repository;

import com.scor.rr.domain.AnalysisView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AnalysisRepository extends JpaRepository<AnalysisView, String>, JpaSpecificationExecutor<AnalysisView> {
}
