package com.scor.rr.repository;

import com.scor.rr.domain.riskLink.RlModelDataSource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface RlModelDataSourceRepository extends JpaRepository<RlModelDataSource, Integer> {

    List<RlModelDataSource> findByProjectId(Integer projectId);
    RlModelDataSource findByProjectIdAndTypeAndInstanceIdAndRlId(int projectId, String type, String instanceId, String rrId);

}
