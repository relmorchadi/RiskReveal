package com.rr.riskreveal.repository;

import com.rr.riskreveal.domain.WorkspaceYears;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkspaceYearsRepository extends JpaRepository<WorkspaceYears, String> {
    Page<WorkspaceYears> findByLabelLikeOrderByLabel(String uwy, Pageable pageable);
}
