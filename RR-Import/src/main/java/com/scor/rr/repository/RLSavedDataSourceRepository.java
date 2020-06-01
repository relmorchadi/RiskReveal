package com.scor.rr.repository;

import com.scor.rr.domain.riskLink.RLSavedDataSource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RLSavedDataSourceRepository extends JpaRepository<RLSavedDataSource, Long> {

    List<RLSavedDataSource> findByUserId(Long userId);

    void deleteByUserId(Long userId);
}
