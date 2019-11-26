package com.scor.rr.repository;

import com.scor.rr.domain.model.TargetRAPEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TargetrapRepository extends JpaRepository<TargetRAPEntity, Long> {

    Optional<TargetRAPEntity> findByTargetRAPCode(String code);
}
