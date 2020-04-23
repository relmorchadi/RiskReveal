package com.scor.rr.repository;

import com.scor.rr.domain.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface TaskRepository  extends JpaRepository<TaskEntity, Long> {

    @Query(value = "EXEC dbo.usp_GetTasksForJob @jobId=:jobId", nativeQuery = true)
    List<Map<String, Object>> getTasksByJobId(@Param("jobId") Long jobId);
}
