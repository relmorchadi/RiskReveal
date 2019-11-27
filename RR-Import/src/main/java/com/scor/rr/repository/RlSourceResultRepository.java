package com.scor.rr.repository;

import com.scor.rr.domain.riskLink.RLImportSelection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RlSourceResultRepository  extends JpaRepository<RLImportSelection, Long> {
}
