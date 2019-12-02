package com.scor.rr.repository;

import com.scor.rr.domain.ModelAnalysisEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ModelAnalysisEntityRepository extends JpaRepository<ModelAnalysisEntity, Long> {

    @Modifying
    @Query("update ModelAnalysisEntity rra set rra.exchangeRate=:exRate where rra.analysisId=:analysisId")
    void updateExchangeRateByAnalysisId(@Param("analysisId") Long analysisId, @Param("exRate") Double exchangeRate);

    ModelAnalysisEntity findByAnalysisId(Long s);
}
