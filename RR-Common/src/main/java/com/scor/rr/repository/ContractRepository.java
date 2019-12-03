package com.scor.rr.repository;

import com.scor.rr.domain.ContractEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContractRepository extends JpaRepository<ContractEntity, String> {
    Optional<ContractEntity> findByTreatyIdAndUwYear(String treatyId, Integer uwYear);
}