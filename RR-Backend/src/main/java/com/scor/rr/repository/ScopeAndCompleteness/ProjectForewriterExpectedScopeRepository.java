package com.scor.rr.repository.ScopeAndCompleteness;

import com.scor.rr.domain.entities.ScopeAndCompleteness.ProjectForewriterExpectedScope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectForewriterExpectedScopeRepository extends JpaRepository<ProjectForewriterExpectedScope,Long> {

    @Query(nativeQuery = true, value = "select projectId from dbo.vw_projectCard where carRequestId = ?1")
    Long getProjectIdByCarRequestId( String carId);
}
