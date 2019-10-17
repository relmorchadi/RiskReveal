package com.scor.rr.repository.rms;

import com.scor.rr.domain.entities.ihub.ModelingResultDataSource;
import com.scor.rr.domain.entities.rms.RmsModelDatasource;
import com.scor.rr.domain.entities.workspace.AssociationVersion;
import com.scor.rr.domain.entities.workspace.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Hamiani Mohammed
 * creation date  25/09/2019 at 17:40
 **/
public interface ModelingResultDataSourceRepository extends JpaRepository<ModelingResultDataSource, Long> {

    List<ModelingResultDataSource> findByProjectAndAssociationVersion(Project project, AssociationVersion associationVersion);

    Long deleteByModelingResultDataSourceIdAndProject(String id, Project project);

    ModelingResultDataSource findByRmsModelDataSource(RmsModelDatasource rmsModelDatasource);

    ModelingResultDataSource findByModelingResultDataSourceId(String id);

    Long deleteByProject(Project project);
}
