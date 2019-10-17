package com.scor.rr.repository.ihub;

import com.scor.rr.domain.entities.ihub.RRPortfolio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Workspace Repository
 *
 * @author HADDINI Zakariyae
 *
 */
public interface RRPortfolioRepository extends JpaRepository<RRPortfolio, String> {

    List<RRPortfolio> findByProjectImportRunProjectImportRunId(String id);
}