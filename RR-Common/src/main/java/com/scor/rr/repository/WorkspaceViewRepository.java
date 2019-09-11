package com.scor.rr.repository;

import com.scor.rr.domain.WorkspaceView;
import com.scor.rr.domain.WorkspaceViewId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface WorkspaceViewRepository extends JpaRepository<WorkspaceView, WorkspaceViewId> , JpaSpecificationExecutor<WorkspaceView>  {


}
