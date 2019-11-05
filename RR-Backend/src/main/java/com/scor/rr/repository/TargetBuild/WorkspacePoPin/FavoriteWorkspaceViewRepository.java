package com.scor.rr.repository.TargetBuild.WorkspacePoPin;

import com.scor.rr.domain.TargetBuild.WorkspacePoPin.FavoriteWorkspaceView;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteWorkspaceViewRepository extends JpaRepository<FavoriteWorkspaceView, Long> {

    List<FavoriteWorkspaceView> findAllByUserId(Integer userId);

}
