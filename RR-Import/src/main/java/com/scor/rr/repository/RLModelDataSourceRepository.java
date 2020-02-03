package com.scor.rr.repository;

import com.scor.rr.domain.riskLink.RLModelDataSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


public interface RLModelDataSourceRepository extends JpaRepository<RLModelDataSource, Long> {

    List<RLModelDataSource> findByProjectId(Long projectId);

    RLModelDataSource findByProjectIdAndTypeAndInstanceIdAndRlId(Long projectId, String type, String instanceId, Long rlId);

    RLModelDataSource findByInstanceIdAndProjectIdAndRlId(String instanceId, Long projectId, Long rlId);


    RLModelDataSource findByRlIdAndNameAndProjectId(Long edmId,String edmName, Long projectId);

    @Modifying
    @Query("update RLModelDataSource rlmd set rlmd.count=:count where rlmd.rlModelDataSourceId=:rlModelDataSourceId")
    @Transactional(transactionManager = "rrTransactionManager")
    void updateCount(@Param("rlModelDataSourceId") Long rlModelDataSourceId, @Param("count") Integer count);

    /***
     * @param rlDataSourceId
     * @return -1 => Operation failed / 1 => Deleted Successfully
     */
//    @Query(value = "EXEC dbonew.usp_RiskLinkDeleteDataSource @RlModelDataSourceId=:rlDataSourceId", nativeQuery = true)
//    Integer deleteRLModelDataSourceById(@Param("rlDataSourceId") Long rlDataSourceId);

    @Procedure(procedureName = "dbonew.usp_RiskLinkDeleteDataSource", outputParameterName = "Status")
    Integer deleteRLModelDataSourceById(@Param("RlModelDataSourceId") Long rlDataSourceId);
}
