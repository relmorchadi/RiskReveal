package com.scor.rr.repository;

import com.scor.rr.domain.TargetRapEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TargetRapRepository extends JpaRepository<TargetRapEntity, Long> {
    TargetRapEntity findBytargetRapId(Long id);
    Optional<TargetRapEntity> findByTargetRAPCode(String code);
}