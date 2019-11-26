package com.scor.rr.repository;

import com.scor.rr.domain.riskLink.RLModelDataSource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface RlModelDataSourceRepository extends JpaRepository<RLModelDataSource, Long> {

    List<RLModelDataSource> findByProjectId(Long projectId);
    RLModelDataSource findByProjectIdAndTypeAndInstanceIdAndRlId(Long projectId, String type, String instanceId, String rrId);

}
