package com.scor.rr.repository;

import com.scor.rr.domain.views.RLAnalysisToRegionPeril;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RLAnalysisToRegionPerilsRepository extends JpaRepository<RLAnalysisToRegionPeril, Long> {

    List<RLAnalysisToRegionPeril> findByRlModelAnalysisId(Long rlAnalysisId);
}
