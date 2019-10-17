package com.scor.rr.repository.rms;

import com.scor.rr.domain.entities.rms.RmsModelDatasource;
import com.scor.rr.domain.entities.workspace.AssociationVersion;
import com.scor.rr.domain.entities.workspace.ModelingExposureDataSource;
import com.scor.rr.domain.entities.workspace.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Hamiani Mohammed
 * creation date  25/09/2019 at 17:42
 **/
public interface ModelingExposureDataSourceRepository extends JpaRepository<ModelingExposureDataSource, String> {

    List<ModelingExposureDataSource> findByProjectAndAssociationVersion(Project project, AssociationVersion associationVersion);

    ModelingExposureDataSource findByRmsModelDataSource(RmsModelDatasource rmsModelDatasource);

    Long deleteByDataModelIdAndProject(String id, Project project);

    ModelingExposureDataSource findByDataModelId(String id);

    Long deleteByProject(Project project);
}
