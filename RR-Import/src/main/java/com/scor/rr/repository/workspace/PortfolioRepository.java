package com.scor.rr.repository.workspace;

import com.scor.rr.domain.entities.workspace.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Portfolio Repository
 * 
 * @author HADDINI Zakariyae
 *
 */
public interface PortfolioRepository extends JpaRepository<Portfolio, String> {

    List<Portfolio> findByRmsProjectImportConfigId(String rmspicId);
}