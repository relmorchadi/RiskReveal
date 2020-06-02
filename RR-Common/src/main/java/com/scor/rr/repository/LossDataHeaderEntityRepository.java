package com.scor.rr.repository;

import com.scor.rr.domain.LossDataHeaderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LossDataHeaderEntityRepository extends JpaRepository<LossDataHeaderEntity, Long> {
    List<LossDataHeaderEntity> findByModelAnalysisId(Long modelAnalysisId);
    //LossDataHeaderEntity findByModelAnalysisId(Long modelAnalysisId);

    @Query("from LossDataHeaderEntity ldh where ldh.modelAnalysisId=:modelAnalysisId")
    List<LossDataHeaderEntity> findByModelAnalysisIdList(@Param("modelAnalysisId") Long modelAnalysisId);
}
