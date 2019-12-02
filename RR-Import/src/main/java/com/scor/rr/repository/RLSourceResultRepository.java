package com.scor.rr.repository;

import com.scor.rr.domain.riskLink.RLImportSelection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RLSourceResultRepository extends JpaRepository<RLImportSelection, Long> {
}
