package com.scor.rr.repository;

import com.scor.rr.domain.AdjustmentProcessingAuditEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdjustmentprocessingauditRepository extends JpaRepository<AdjustmentProcessingAuditEntity, Integer> {
}