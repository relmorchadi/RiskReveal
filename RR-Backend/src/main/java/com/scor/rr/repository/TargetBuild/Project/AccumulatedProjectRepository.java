package com.scor.rr.repository.TargetBuild.Project;

import com.scor.rr.domain.TargetBuild.Project.AccumulatedProject;
import com.scor.rr.domain.TargetBuild.Project.NumberOfRegionPerils;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccumulatedProjectRepository extends JpaRepository<AccumulatedProject, Long> {
}
