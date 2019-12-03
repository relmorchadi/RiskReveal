package com.scor.rr.repository;

import com.scor.rr.domain.riskLink.RLPortfolioScanStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RLPortfolioScanStatusRepository extends JpaRepository<RLPortfolioScanStatus, Long> {
}
