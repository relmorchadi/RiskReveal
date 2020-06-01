package com.scor.rr.repository;

import com.scor.rr.domain.LossDataHeaderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LossDataHeaderEntityRepository extends JpaRepository<LossDataHeaderEntity, Long> {
    List<LossDataHeaderEntity> findByModelAnalysisId(Long modelAnalysisId);
}
