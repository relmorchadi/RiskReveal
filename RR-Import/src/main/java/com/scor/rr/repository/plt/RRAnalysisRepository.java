package com.scor.rr.repository.plt;

import com.scor.rr.domain.entities.plt.RRAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * RRAnalysis Repository
 *
 * @author HADDINI Zakariyae
 */
public interface RRAnalysisRepository extends JpaRepository<RRAnalysis, String> {

    List<RRAnalysis> findByProjectImportRunProjectImportRunId(String id);
}
