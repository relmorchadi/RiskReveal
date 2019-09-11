package com.scor.rr.repository;

import com.scor.rr.domain.InsuredEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsuredRepository extends JpaRepository<InsuredEntity, Integer> {
}