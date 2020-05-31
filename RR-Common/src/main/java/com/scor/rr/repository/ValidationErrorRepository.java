package com.scor.rr.repository;

import com.scor.rr.domain.ValidationError;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ValidationErrorRepository extends JpaRepository<ValidationError, Long> {
}
