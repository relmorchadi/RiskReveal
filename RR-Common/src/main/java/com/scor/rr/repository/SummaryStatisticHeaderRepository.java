package com.scor.rr.repository;

import com.scor.rr.domain.SummaryStatisticHeaderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SummaryStatisticHeaderRepository extends JpaRepository<SummaryStatisticHeaderEntity, Long> {

    List<SummaryStatisticHeaderEntity> findByLossDataIdAndLossDataType(Long lossDataId, String lossDataType);

    @Query(value = "select ssh.* from dbo.SummaryStatisticHeader ssh \n" +
            "join dbo.PLTHeader plt on ssh.SummaryStatisticHeaderId=plt.SummaryStatisticHeaderId\n" +
            "where plt.PLTHeaderId=:pltId", nativeQuery = true)
    SummaryStatisticHeaderEntity findByPltId(@Param("pltId") Long pltId);

}
