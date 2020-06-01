package com.scor.rr.repository;

import com.scor.rr.domain.StepEntity;
import com.scor.rr.domain.enums.StepStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface StepEntityRepository extends JpaRepository<StepEntity, Long> {

    @Query(value = "UPDATE StepEntity step SET step.status=:status, step.finishedDate=:finishDate WHERE step.stepId=:stepId")
    @Modifying
    @Transactional(transactionManager = "theTransactionManager")
    void updateStatus(@Param("stepId") Long stepId, @Param("status") String status, @Param("finishDate") Date finishDate);

//    List<StepEntity> findByTask
}
