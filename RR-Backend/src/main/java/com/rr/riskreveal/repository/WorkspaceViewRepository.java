package com.rr.riskreveal.repository;

import com.rr.riskreveal.domain.WorkspaceView;
import com.rr.riskreveal.domain.WorkspaceViewId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface WorkspaceViewRepository extends JpaRepository<WorkspaceView, WorkspaceViewId> , JpaSpecificationExecutor<WorkspaceView>  {


}
