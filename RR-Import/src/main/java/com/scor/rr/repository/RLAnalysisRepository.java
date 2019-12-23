package com.scor.rr.repository;

import com.scor.rr.domain.RdmAnalysis;
import com.scor.rr.domain.riskLink.RLAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface RLAnalysisRepository extends JpaRepository<RLAnalysis, Long> {


    // @TODO : Check Instance ID Param
    @Modifying()
    @Transactional(transactionManager = "rrTransactionManager")
    @Query("update RLAnalysis ra " +
            "set ra.defaultGrain = :#{#analysis.defaultGrain}, " +
            " ra.exposureType = :#{#analysis.exposureType}, " +
            " ra.exposureTypeCode = :#{#analysis.exposureTypeCode}, " +
            " ra.edmNameSourceLink = :#{#analysis.edmNameSourceLink}, " +
            " ra.exposureId = :#{#analysis.exposureId}, " +
            " ra.rlExchangeRate = :#{#analysis.rmsExchangeRate}, " +
            " ra.geoCode = :#{#analysis.geoCode}, " +
            " ra.rpCode = :#{#analysis.rpCode}, " +
            " ra.typeCode = :#{#analysis.typeCode}, " +
            " ra.analysisMode = :#{#analysis.analysisMode}, " +
            " ra.engineTypeCode = :#{#analysis.engineTypeCode}, " +
            " ra.engineVersionMajor = :#{#analysis.engineVersionMajor}, " +
            " ra.profileName = :#{#analysis.profileName}, " +
            " ra.profileKey = :#{#analysis.profileKey}, " +
            " ra.purePremium = :#{#analysis.purePremium}, " +
            " ra.exposureTIV = :#{#analysis.exposureTiv}, " +
            " ra.user1 = :#{#analysis.user1}, " +
            " ra.user2 = :#{#analysis.user2}, " +
            " ra.user3 = :#{#analysis.user3}, " +
            " ra.user4 = :#{#analysis.user4}, " +
            " ra.description = :#{#analysis.description} " +
            " where ra.projectId= :projectId and " +
            " ra.rdmId= :#{#analysis.rdmId} and " +
            " ra.rdmName= :#{#analysis.rdmName} and " +
            " ra.rlId= :#{#analysis.analysisId} and " +
            " ra.analysisName= :#{#analysis.analysisName} ")
    void updateAnalysisById(@Param("projectId") Long projectId,
                            @Param("analysis") RdmAnalysis analysis);

    @Modifying
    @Transactional(transactionManager = "rrTransactionManager")
    @Query("delete from RLAnalysis rla where rla.rlModelDataSourceId= :rlModelDatasourceId")
    void deleteByRlModelDataSourceId(@Param("rlModelDatasourceId") Long rlModelDatasourceId);

    @Query("select rla from RLAnalysis rla " +
            " where rla.projectId= :projectId and " +
            " rla.rdmId= :#{#analysis.rdmId} and " +
            " rla.rdmName= :#{#analysis.rdmName} and " +
            " rla.rlId= :#{#analysis.analysisId} and " +
            " rla.analysisName= :#{#analysis.analysisName} ")
    RLAnalysis findByProjectIdAndAnalysis(@Param("projectId") Long projectId,
                                          @Param("analysis") RdmAnalysis analysis);

    RLAnalysis findByRdmIdAndRdmNameAndRlIdAndProjectId(Long rdmId, String rdmName, Long analysisId, Long projectId);

    List<RLAnalysis> findByRlModelDataSourceId(Long rlModelDataSourceId);

    RLAnalysis findByRlId(Long analysisId);
}
