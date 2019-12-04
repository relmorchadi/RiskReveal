package com.scor.rr.repository;

import com.scor.rr.domain.DefaultRetPerBandingParamsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DefaultRetPerBandingParamsRepository extends JpaRepository<DefaultRetPerBandingParamsEntity,Integer> {

    @Query("select m from DefaultRetPerBandingParamsEntity m where m.defaultAdjustmentNodeByFkDefaultNode.defaultAdjustmentNodeId = :param")
    DefaultRetPerBandingParamsEntity getByDefaultAdjustmentNodeByIdDefaultNode(@Param("param") Integer defaultAdjustmentNodeEntityId);
}
