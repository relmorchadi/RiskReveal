package com.scor.rr.repository.Project;

import com.scor.rr.domain.entities.Project.AccumulatedProject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccumulatedProjectRepository extends JpaRepository<AccumulatedProject, Long> {
}
