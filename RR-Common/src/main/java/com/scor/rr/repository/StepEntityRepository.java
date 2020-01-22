package com.scor.rr.repository;

import com.scor.rr.domain.StepEntity;
import com.scor.rr.domain.enums.StepStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StepEntityRepository extends JpaRepository<StepEntity, Long> {

    @Query(value = "UPDATE StepEntity step SET step.status=:status WHERE step.stepId=:stepId")
    void updateStatus(@Param("stepId") Long stepId, @Param("status") StepStatus status);
}
