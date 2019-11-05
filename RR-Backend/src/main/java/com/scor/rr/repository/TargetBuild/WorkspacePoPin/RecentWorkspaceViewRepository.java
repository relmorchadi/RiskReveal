package com.scor.rr.repository.TargetBuild.WorkspacePoPin;

import com.scor.rr.domain.TargetBuild.WorkspacePoPin.RecentWorkspaceView;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecentWorkspaceViewRepository extends JpaRepository<RecentWorkspaceView, Long> {

    List<RecentWorkspaceView> findAllByUserId(Integer userId);

}
