package com.scor.rr.repository;

import com.scor.rr.domain.RdmAnalysis;
import com.scor.rr.domain.RdmAnalysisBasic;
import com.scor.rr.domain.riskLink.RLAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface RLAnalysisRepository extends JpaRepository<RLAnalysis, Long>, JpaSpecificationExecutor<RLAnalysis> {


    @Query("select rla from RLAnalysis rla " +
            " where rla.projectId= :projectId and " +
            " rla.rdmId= :#{#analysis.rdmId} and " +
            " rla.rdmName= :#{#analysis.rdmName} and " +
            " rla.rlId= :#{#analysis.analysisId} and " +
            " rla.analysisName= :#{#analysis.analysisName} ")
    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    RLAnalysis findByProjectIdAndAnalysis(@Param("projectId") Long projectId,
                                          @Param("analysis") RdmAnalysis analysis);

    RLAnalysis findByRdmIdAndRdmNameAndRlIdAndProjectId(Long rdmId, String rdmName, Long analysisId, Long projectId);

    List<RLAnalysis> findByRlModelDataSourceId(Long rlModelDataSourceId);

    @Query(value = "select rla from RLAnalysis rla where rla.analysisName= :name and rla.rlModelDataSourceId= :rdmId")
    List<RLAnalysis> findByRdmIdAndName(@Param("rdmId") Long rdmId, @Param("name") String name);

    List<RLAnalysis> findByRdmIdAndProjectId(Long rlModelDataSourceId, Long projectId);

    @Modifying
    @Transactional(transactionManager = "theTransactionManager")
    @Query("delete from RLAnalysis where rlId=:analysisId and projectId=:projectId")
    void deleteByRLAnalysisId(@Param("analysisId")Long AnalysisId,@Param("projectId")Long projectId);

}
