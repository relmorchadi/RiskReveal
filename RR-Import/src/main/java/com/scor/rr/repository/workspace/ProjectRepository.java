package com.scor.rr.repository.workspace;

import com.scor.rr.domain.entities.workspace.Project;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Project Repository
 *
 * @author HADDINI Zakariyae
 *
 */
public interface ProjectRepository extends JpaRepository<Project, String> {

}