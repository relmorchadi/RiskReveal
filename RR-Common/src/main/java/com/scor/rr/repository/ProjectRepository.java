package com.scor.rr.repository;

import com.scor.rr.domain.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<ProjectEntity,Long> {
}
