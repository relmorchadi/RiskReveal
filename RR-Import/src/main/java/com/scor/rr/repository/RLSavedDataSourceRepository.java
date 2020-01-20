package com.scor.rr.repository;

import com.scor.rr.domain.riskLink.RLSavedDataSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RLSavedDataSourceRepository extends JpaRepository<RLSavedDataSource, Long> {

    @Modifying
    @Transactional(transactionManager = "rrTransactionManager")
    void deleteByProjectIdAndUserIdAndInstanceId(Long projectId, Long userId, String instanceId);

    List<RLSavedDataSource> findByProjectIdAndInstanceIdAndUserId(Long projectId, String instanceId, Long userId);

    List<RLSavedDataSource> findByInstanceIdAndUserId(String instanceId, Long userId);

    void deleteByUserIdAndInstanceId(Long userId, String instanceId);
}
