package com.scor.rr.repository;

import com.scor.rr.domain.entities.ContractSearchResultFac;
import com.scor.rr.domain.entities.ProjectDivisions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProjectDivisionsRepository extends JpaRepository<ProjectDivisions, Long> {

    @Query("SELECT distinct pd.divisionNumber FROM ProjectDivisions pd WHERE pd.projectId = :projectId")
    List<String> findAllDivisions(@Param("projectId") Long projectId);
}
