package com.scor.rr.repository;

import com.scor.rr.domain.workspace.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
