package com.scor.rr.repository.Project;

import com.scor.rr.domain.entities.Project.ImportedProject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImportedProjectRepository extends JpaRepository<ImportedProject, Long> {
}
