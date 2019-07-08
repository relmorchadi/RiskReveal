package com.scor.rr.repository;

import com.scor.rr.domain.StatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<StatusEntity, String> {
}