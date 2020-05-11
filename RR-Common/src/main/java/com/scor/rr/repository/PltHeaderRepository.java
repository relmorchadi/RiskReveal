package com.scor.rr.repository;

import com.scor.rr.domain.PltHeaderEntity;
import com.scor.rr.domain.WorkspaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface PltHeaderRepository extends JpaRepository<PltHeaderEntity, Long> {
    PltHeaderEntity findByPltHeaderId(Long pkScorPltHeaderId);

    @Query("FROM WorkspaceEntity \n" +
            "WHERE\n" +
            "workspaceId IN ( SELECT workspaceId FROM ProjectEntity WHERE projectId IN " +
            "( SELECT projectId FROM PltHeaderEntity  WHERE pltHeaderId = :pltHeaderId)" +
            ")")
    WorkspaceEntity findParentWorkspace(@Param("pltHeaderId") Integer pltHeaderId);

    @Query("select plt.summaryStatisticHeaderId from PltHeaderEntity plt where plt.pltHeaderId= :pltId")
    Long getSummaryStatHeaderIdById(@Param("pltId") Long pltId);

    @Modifying
    @Transactional
    @Query("update PltHeaderEntity set summaryStatisticHeaderId= :summaryStatisticHeaderId " +
            "where pltHeaderId=:pltHeaderId")
    void updateSummaryStatisticHeaderId(@Param("pltHeaderId") Long pltHeaderId, @Param("summaryStatisticHeaderId") Long summaryStatisticHeaderId);

}

