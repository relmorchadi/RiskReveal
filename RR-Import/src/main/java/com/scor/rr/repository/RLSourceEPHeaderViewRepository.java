package com.scor.rr.repository;

import com.scor.rr.domain.views.RLSourceEpHeaderView;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RLSourceEPHeaderViewRepository extends JpaRepository<RLSourceEpHeaderView, Long> {

    List<RLSourceEpHeaderView> findByRLAnalysisId(Long rlAnalysisId);
}
