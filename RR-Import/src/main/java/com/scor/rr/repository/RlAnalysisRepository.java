package com.scor.rr.repository;

import com.scor.rr.domain.RdmAnalysis;
import com.scor.rr.domain.riskLink.RLAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface RlAnalysisRepository extends JpaRepository<RLAnalysis, Integer> {


    // @TODO : Check Instance ID Param
    @Modifying()
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
            " ra.exposureTIV = :#{#analysis.exposureTiv} " +
            " where ra.projectId= :projectId and " +
            " ra.rdmId= :#{#analysis.rdmId} and " +
            " ra.rdmName= :#{#analysis.rdmName} and " +
            " ra.analysisId= :#{#analysis.analysisId} and " +
            " ra.analysisName= :#{#analysis.analysisName} ")
    void updateAnalysiById(@Param("projectId") Integer projectId,
                           @Param("analysis") RdmAnalysis analysis);
}
