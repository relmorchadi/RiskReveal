package com.scor.rr.repository;

import com.scor.rr.domain.JobExecutionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JobExecutionRepository extends JpaRepository<JobExecutionEntity, Long> {

    @Query(value = "SELECT * FROM dbo.BATCH_JOB_EXECUTION ex " +
            "inner join dbo.BATCH_JOB_EXECUTION_PARAMS params " +
            "on ex.JOB_EXECUTION_ID = params.JOB_EXECUTION_ID " +
            "WHERE params.KEY_NAME='userId' " +
            "AND ex.STATUS='RUNNING' " +
            "AND params.STRING_VAL=:userId", nativeQuery = true)
    List<JobExecutionEntity> findRunningJobsForUser(String userId);
}
