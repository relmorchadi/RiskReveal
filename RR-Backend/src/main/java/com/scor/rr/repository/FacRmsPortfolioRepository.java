package com.scor.rr.repository;

import com.scor.rr.domain.FacRmsPortfolio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FacRmsPortfolioRepository extends JpaRepository<FacRmsPortfolio, Integer> {

    List<FacRmsPortfolio> findByEdmIdAndEdmName(Integer edmId, String edmName);
}
