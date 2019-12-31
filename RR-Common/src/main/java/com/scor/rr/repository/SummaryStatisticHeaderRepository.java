package com.scor.rr.repository;

import com.scor.rr.domain.SummaryStatisticHeaderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SummaryStatisticHeaderRepository extends JpaRepository<SummaryStatisticHeaderEntity, Long> {

    List<SummaryStatisticHeaderEntity> findByLossDataIdAndLossDataType(Long lossDataId, String lossDataType);
}
