package com.scor.rr.repository;

import com.scor.rr.domain.JobEntity;
import com.scor.rr.domain.enums.JobStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JobEntityRepository extends JpaRepository<JobEntity, Long> {

    @Query(value = "UPDATE JobEntity job SET job.status=:status WHERE job.jobId=:jobId")
    void updateStatus(@Param("jobId") Long jobId, @Param("status")  JobStatus status);
}
