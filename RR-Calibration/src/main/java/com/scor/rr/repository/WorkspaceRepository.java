package com.scor.rr.repository;

import com.scor.rr.domain.WorkspaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkspaceRepository extends JpaRepository<WorkspaceEntity, Long> {

    public Optional<WorkspaceEntity> findByWorkspaceContextCodeAndWorkspaceUwYear(String ctx, Integer uwYear);
}
