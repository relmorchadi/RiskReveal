package com.scor.rr.repository;

import com.scor.rr.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ProjectRepository extends JpaRepository<Project, String> {

    @Modifying
    @Query(value = "INSERT INTO [poc].[RR-WorkspaceProjectMapping]  VALUES(:workspaceId,:project)", nativeQuery = true)
    void addProjectToWorkspace(@Param("workspaceId") String workspaceId, @Param("project") String project);

    @Modifying
    @Query(value = "DELETE FROM [poc].[RR-WorkspaceProjectMapping] WHERE _id=:workspaceId and project=:project", nativeQuery = true)
    void deleteProjectFromWorkspace(@Param("workspaceId") String workspaceId, @Param("project") String project);

}
