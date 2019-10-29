package com.scor.rr.repository;

import com.scor.rr.domain.reference.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ContractRepository extends JpaRepository<Contract, Long> {
    Optional<Contract> findByTreatyIdAndUwYear(String treatyId, Integer uwYear);
}
