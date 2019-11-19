package com.scor.rr.repository.TargetBuild.Project;

import com.scor.rr.domain.TargetBuild.Project.ProjectConfigurationForeWriter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectConfigurationForeWriterRepository extends JpaRepository<ProjectConfigurationForeWriter, Long> {
    Boolean existsByProjectId(Long projectId);
}
