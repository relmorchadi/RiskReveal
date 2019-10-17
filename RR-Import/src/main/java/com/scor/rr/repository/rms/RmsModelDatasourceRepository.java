package com.scor.rr.repository.rms;

import com.scor.rr.domain.entities.rms.RmsModelDatasource;
import com.scor.rr.domain.entities.workspace.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * @author Hamiani Mohammed
 * creation date  25/09/2019 at 16:46
 **/
public interface RmsModelDatasourceRepository extends JpaRepository<RmsModelDatasource, Long> {
    List<RmsModelDatasource> findByProject(Project project);

    RmsModelDatasource findByRmsIdAndProject(Long rmsId, Project project);

    @Query("SELECT RMD FROM RmsModelDatasource RMD WHERE RMD.type='EDM'") // TODO: to review
    RmsModelDatasource findEDMByRmsIdAndProjectIdAndType(Long rmsId, String projectId);

    Long deleteByRmsIdAndProject(Long rmsId, Project project);

    Long deleteByProject(Project project);

    Optional<RmsModelDatasource> findByRmsId(Long id);
}
