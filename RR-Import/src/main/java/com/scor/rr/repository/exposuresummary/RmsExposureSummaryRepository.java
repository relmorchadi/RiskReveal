package com.scor.rr.repository.exposuresummary;

import com.scor.rr.domain.entities.rms.RmsModelDatasource;
import com.scor.rr.domain.entities.rms.exposuresummary.RmsExposureSummary;
import com.scor.rr.domain.entities.workspace.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RmsExposureSummaryRepository extends JpaRepository<RmsExposureSummary, Long>
{
	List<RmsExposureSummary> findByProject(Project project);
	List<RmsExposureSummary> findByProjectAndEdm(Project project, RmsModelDatasource edm);
	Long deleteByProject(Project project);
}
