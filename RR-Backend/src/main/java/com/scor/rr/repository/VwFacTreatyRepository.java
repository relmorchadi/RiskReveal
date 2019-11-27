package com.scor.rr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface VwFacTreatyRepository extends JpaRepository<VwFacTreaty, String>, JpaSpecificationExecutor<VwFacTreaty> {
}
