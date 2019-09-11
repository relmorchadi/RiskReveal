package com.scor.rr.repository;

import com.scor.rr.domain.PortfolioView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PortfolioRepository extends JpaRepository<PortfolioView, String>, JpaSpecificationExecutor<PortfolioView> {

}
