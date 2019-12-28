package com.scor.rr.repository;

import com.scor.rr.domain.riskLink.RLImportSelection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface RLImportSelectionRepository extends JpaRepository<RLImportSelection, Long> {

    @Transactional(transactionManager = "rrTransactionManager")
    void deleteByProjectId(Long projectId);
}
