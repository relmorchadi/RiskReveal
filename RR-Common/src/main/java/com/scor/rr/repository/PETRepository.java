package com.scor.rr.repository;

import com.scor.rr.domain.PetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PETRepository extends JpaRepository<PetEntity, Long> {
}
