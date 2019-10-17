package com.scor.rr.repository.ihub;

import com.scor.rr.domain.entities.ihub.RRLossTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * RRLossTable Repository
 *
 * @author HADDINI Zakariyae
 */
public interface RRLossTableRepository extends JpaRepository<RRLossTable, String> {

    List<RRLossTable> findByRrAnalysisId(String rrAnalysisId);
}
