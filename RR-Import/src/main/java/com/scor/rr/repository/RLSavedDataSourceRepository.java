package com.scor.rr.repository;

import com.scor.rr.domain.riskLink.RLSavedDataSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RLSavedDataSourceRepository extends JpaRepository<RLSavedDataSource, Long> {

    List<RLSavedDataSource> findByUserId(Long userId);

    @Modifying
    @Transactional(transactionManager = "theTransactionManager")
    void deleteByUserId(Long userId);
}
