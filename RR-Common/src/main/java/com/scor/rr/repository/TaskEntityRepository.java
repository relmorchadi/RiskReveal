package com.scor.rr.repository;

import com.scor.rr.domain.TaskEntity;
import com.scor.rr.domain.enums.JobStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface TaskEntityRepository extends JpaRepository<TaskEntity, Long> {

    @Query(value = "UPDATE TaskEntity task SET task.status=:status WHERE task.taskId=:taskId")
    @Modifying
    @Transactional(transactionManager = "theTransactionManager")
    void updateStatus(@Param("taskId") Long taskId, @Param("status") JobStatus status);

}
