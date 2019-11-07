package com.scor.rr.repository.TargetBuild.WorkspacePoPin;

import com.scor.rr.domain.TargetBuild.WorkspacePoPin.FavoriteWorkspaceView;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FavoriteWorkspaceViewRepository extends JpaRepository<FavoriteWorkspaceView, Long> {

    @Query("from FavoriteWorkspaceView fwsv where fwsv.userId = :userId order by fwsv.createdDate desc")
    List<FavoriteWorkspaceView> findAllByUserId(@Param("userId") Integer userId, Pageable page);

}
