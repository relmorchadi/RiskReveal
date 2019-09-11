package com.scor.rr.repository;

import com.scor.rr.domain.TargetRapEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TargetrapRepository extends JpaRepository<TargetRapEntity, String> {
    TargetRapEntity findBytargetRapId(String id);
}