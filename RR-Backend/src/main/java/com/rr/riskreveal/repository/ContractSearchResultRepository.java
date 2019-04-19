package com.rr.riskreveal.repository;

import com.rr.riskreveal.domain.ContractSearchResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public interface ContractSearchResultRepository extends JpaRepository<ContractSearchResult, String>, JpaSpecificationExecutor {

    Stream<ContractSearchResult> findDistinctByWorkSpaceId(String uwy);

}
