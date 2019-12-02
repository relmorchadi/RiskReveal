package com.scor.rr.repository;

import com.scor.rr.domain.riskLink.RlModelDataSource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface RLModelDataSourceRepository extends JpaRepository<RlModelDataSource, Long> {

    List<RlModelDataSource> findByProjectId(Long projectId);
    RlModelDataSource findByProjectIdAndTypeAndInstanceIdAndRlId(Long projectId, String type, String instanceId, String rrId);

}
