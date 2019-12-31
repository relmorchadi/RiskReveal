package com.scor.rr.repository;

import com.scor.rr.domain.SummaryStatisticsDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SummaryStatisticsDetailRepository extends JpaRepository<SummaryStatisticsDetail, Long> {
    SummaryStatisticsDetail findByPltHeaderIdAndCurveTypeAndLossType(Long pltId, String curveType, String lossType);
}
