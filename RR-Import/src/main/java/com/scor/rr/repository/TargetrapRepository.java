package com.scor.rr.repository;

import com.scor.rr.domain.model.TargetRAP;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TargetrapRepository extends JpaRepository<TargetRAP, Long> {

    Optional<TargetRAP> findByTargetRAPCode(String code);
}
