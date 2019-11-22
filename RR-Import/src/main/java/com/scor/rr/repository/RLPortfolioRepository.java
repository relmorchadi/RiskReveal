package com.scor.rr.repository;

import com.scor.rr.domain.riskLink.RLPortfolio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RLPortfolioRepository extends JpaRepository<RLPortfolio,Long> {
    RLPortfolio findByPortfolioId(Long portfolioId);
}
