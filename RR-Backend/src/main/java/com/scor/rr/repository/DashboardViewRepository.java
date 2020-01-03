package com.scor.rr.repository;

import com.scor.rr.domain.entities.DashboardView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DashboardViewRepository extends JpaRepository<DashboardView, Long>, JpaSpecificationExecutor<DashboardView> {
}
