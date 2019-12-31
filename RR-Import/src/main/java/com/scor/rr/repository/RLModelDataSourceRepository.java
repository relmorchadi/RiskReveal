package com.scor.rr.repository;

import com.scor.rr.domain.riskLink.RLModelDataSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface RLModelDataSourceRepository extends JpaRepository<RLModelDataSource, Long> {

    List<RLModelDataSource> findByProjectId(Long projectId);

    RLModelDataSource findByProjectIdAndTypeAndInstanceIdAndRlId(Long projectId, String type, String instanceId, Long rlId);

    RLModelDataSource findByInstanceIdAndProjectIdAndRlId(String instanceId, Long projectId, Long rlId);

    @Modifying
    @Query("update RLModelDataSource rlmd set rlmd.count=:count where rlmd.rlModelDataSourceId=:rlModelDataSourceId")
    @Transactional(transactionManager = "rrTransactionManager")
    void updateCount(@Param("rlModelDataSourceId") Long rlModelDataSourceId, @Param("count") Integer count);

    RLModelDataSource findByRlIdAndNameAndProjectId(Long edmId,String edmName, Long projectId);
}
