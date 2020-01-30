package com.scor.rr.repository;

import com.scor.rr.domain.entities.ContractSearchResultFac;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContractSearchResultFacRepository extends JpaRepository<ContractSearchResultFac, String> {
    Optional<ContractSearchResultFac> findByWorkspaceContextCodeAndWorkspaceUwYear(String workspaceContextCode, Integer workspaceUwYear);
}
