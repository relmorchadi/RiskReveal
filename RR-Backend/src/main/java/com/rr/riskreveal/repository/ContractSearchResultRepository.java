package com.rr.riskreveal.repository;

import com.rr.riskreveal.domain.ContractSearchResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ContractSearchResultRepository extends JpaRepository<ContractSearchResult, String>, JpaSpecificationExecutor {
}
