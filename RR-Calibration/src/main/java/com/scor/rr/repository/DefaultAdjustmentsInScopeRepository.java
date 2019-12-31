package com.scor.rr.repository;

import com.scor.rr.domain.DefaultAdjustmentsInScopeView;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DefaultAdjustmentsInScopeRepository extends JpaRepository<DefaultAdjustmentsInScopeView, Long> {
    List<DefaultAdjustmentsInScopeView> findByWorkspaceContextCodeAndUwYear(String workspaceContextCode, Integer uwYear);
}
