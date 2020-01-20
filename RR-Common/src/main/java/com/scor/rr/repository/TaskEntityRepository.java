package com.scor.rr.repository;

import com.scor.rr.domain.TaskEntity;
import com.scor.rr.domain.enums.JobStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TaskEntityRepository extends JpaRepository<TaskEntity, Long> {

    @Query(value = "UPDATE TaskEntity task SET task.status=:status WHERE task.taskId=:taskId")
    void updateStatus(@Param("taskId") Long taskId, @Param("status") JobStatus status);

}
