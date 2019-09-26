package com.scor.rr.repository;

import com.scor.rr.domain.FacRmsAnalysisBasic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FacRmsAnalysisBasicRepository extends JpaRepository<FacRmsAnalysisBasic, Integer> {

    List<FacRmsAnalysisBasic> findByRdmIdAndRdmNameAndAnalysisName(Integer rdmId, String rdmName, String analysisName);

}
