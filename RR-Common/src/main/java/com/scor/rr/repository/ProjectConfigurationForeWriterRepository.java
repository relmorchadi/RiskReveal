package com.scor.rr.repository;

import com.scor.rr.domain.ProjectConfigurationForeWriter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectConfigurationForeWriterRepository extends JpaRepository<ProjectConfigurationForeWriter, Long> {
    Boolean existsByProjectId(Long projectId);
}
