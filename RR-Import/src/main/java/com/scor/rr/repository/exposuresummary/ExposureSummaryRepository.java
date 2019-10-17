package com.scor.rr.repository.exposuresummary;

import com.scor.rr.domain.entities.rms.exposuresummary.ExposureSummary;
import com.scor.rr.domain.entities.workspace.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// old code ri
//import com.scor.almf.treaty.cdm.domain.dss.ImportDecision;

public interface ExposureSummaryRepository extends JpaRepository<ExposureSummary, String>
{
	List<ExposureSummary> findByProject(Project project);
	List<ExposureSummary> findByProjectAndImportStatusAndExposureSummaryAlias(Project project, String importStatus, String exposureSummaryAlias);
	List<ExposureSummary> findByProjectAndExposureSummaryAlias(Project project, String exposureSummaryAlias);

//	@Query(value="{ '_id': { $in: ?0 } }")
//	List<ExposureSummary> findByIdIn(List<String> idList);
//
//	Long deleteByProject(Project project);
//
//	@Query(value = "{ $and: [{'exposureSummaryItems.regionPerilCode': 'Unmapped'}, { 'exposureSummaryItems.peril': { $ne: 'Total' } } ] }", fields="{ '_id' : 1}")
//	List<ExposureSummary> findByUnmapedItems();
//
//	@Query(value = "{ $and: [ { 'exposureSummaryItems.edmId': ?1 }, { 'exposureSummaryItems.portfolioId': ?2 }, { 'project.$id': ?0 } ] }",  fields="{ '_id' : 1}")
//	List<ExposureSummary> findByProjectIdAndEdmIdAndPortfolioId(String projectId, Long edmId, Long portfolioId);

	List<ExposureSummary> findByRrPortfolioId(String rrPortfolioId);

	ExposureSummary findByRrPortfolioIdAndExposureSummaryAliasAndPeril(String rrPortfolioId, String exposureSummaryAlias, String peril);
}
