package com.scor.rr.repository;

import com.scor.rr.domain.riskReveal.RRAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RRAnalysisRepository extends JpaRepository<RRAnalysis, Long> {

    @Modifying
    @Query("update RRAnalysis rra set rra.exchangeRate= :exchangeRate where rra.analysisId= :analysisId")
    void updateExchangeRateByAnalysisId(@Param("analysisId") Long analysisId, @Param("exRate") Double exchangeRate);

}
