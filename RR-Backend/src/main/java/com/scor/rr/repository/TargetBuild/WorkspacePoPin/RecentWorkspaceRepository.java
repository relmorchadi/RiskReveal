package com.scor.rr.repository.TargetBuild.WorkspacePoPin;

import com.scor.rr.domain.TargetBuild.WorkspacePoPin.RecentWorkspace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecentWorkspaceRepository extends JpaRepository<RecentWorkspace, Long> {
}
