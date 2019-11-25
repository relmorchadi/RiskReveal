package com.scor.rr.repository.TargetBuild.Project;

import com.scor.rr.domain.TargetBuild.Project.ImportedProject;
import com.scor.rr.domain.TargetBuild.Project.NumberOfRegionPerils;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImportedProjectRepository extends JpaRepository<ImportedProject, Long> {
}
