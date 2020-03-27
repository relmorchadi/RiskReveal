package com.scor.rr.repository;

import com.scor.rr.domain.JobEntity;
import com.scor.rr.domain.enums.JobStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface JobEntityRepository extends JpaRepository<JobEntity, Long> {

    @Query(value = "UPDATE JobEntity job SET job.status=:status WHERE job.jobId=:jobId")
    @Modifying
    @Transactional(transactionManager = "theTransactionManager")
    void updateStatus(@Param("jobId") Long jobId, @Param("status") JobStatus status);

    @Query("FROM JobEntity WHERE userId=:userId AND (status='RUNNING' OR status='PENDING' OR status='FAILED')")
    List<JobEntity> findAllByUserIdAndStatus(@Param("userId") Long userId);
}
