package com.scor.rr.repository;

import com.scor.rr.domain.StepEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StepRepository extends JpaRepository<StepEntity, String> {
}
