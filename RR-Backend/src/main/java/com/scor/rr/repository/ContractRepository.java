package com.scor.rr.repository;

import com.scor.rr.domain.ContractEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRepository extends JpaRepository<ContractEntity, Integer> {
}