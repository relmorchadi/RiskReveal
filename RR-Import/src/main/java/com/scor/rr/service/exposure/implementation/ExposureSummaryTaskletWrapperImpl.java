package com.scor.rr.service.exposure.implementation;

import com.scor.rr.domain.entities.ihub.RRPortfolio;
import com.scor.rr.domain.entities.ihub.RepresentationDataset;
import com.scor.rr.domain.entities.ihub.RmsPortfolioAnalysisRegion;
import com.scor.rr.domain.entities.ihub.SelectedAssociationBag;
import com.scor.rr.domain.entities.meta.exposure.ExposureSummaryExtractFile;
import com.scor.rr.domain.entities.plt.ProjectImportRun;
import com.scor.rr.domain.entities.references.omega.BinFile;
import com.scor.rr.domain.entities.rms.ProjectImportAssetLog;
import com.scor.rr.domain.entities.rms.RmsModelDatasource;
import com.scor.rr.domain.entities.rms.RmsPortfolio;
import com.scor.rr.domain.entities.rms.RmsProjectImportConfig;
import com.scor.rr.domain.entities.rms.exposuresummary.ExposureSummary;
import com.scor.rr.domain.entities.rms.exposuresummary.RmsExposureSummary;
import com.scor.rr.domain.entities.tracking.ProjectImportLog;
import com.scor.rr.domain.entities.workspace.ModelingExposureDataSource;
import com.scor.rr.domain.entities.workspace.Portfolio;
import com.scor.rr.domain.entities.workspace.Project;
import com.scor.rr.domain.enums.AssetType;
import com.scor.rr.domain.enums.ExposureSummaryExtractType;
import com.scor.rr.domain.enums.TrackingStatus;
import com.scor.rr.importBatch.processing.exposure.ExposureSummaryWriter;
import com.scor.rr.importBatch.processing.treaty.TransformationPackage;
import com.scor.rr.importBatch.processing.workflow.io.ExposureSummaryExtractInput;
import com.scor.rr.importBatch.processing.workflow.io.ExposureSummaryExtractOutput;
import com.scor.rr.importBatch.processing.workflow.io.PortfolioExposureSummaryExtractInput;
import com.scor.rr.repository.cat.ModellingSystemInstanceRepository;
import com.scor.rr.repository.exposuresummary.ExposureSummaryRepository;
import com.scor.rr.repository.exposuresummary.RmsExposureSummaryRepository;
import com.scor.rr.repository.ihub.RRPortfolioRepository;
import com.scor.rr.repository.ihub.RRPortfolioStorageRepository;
import com.scor.rr.repository.ihub.RepresentationDatasetRepository;
import com.scor.rr.repository.plt.ProjectImportRunRepository;
import com.scor.rr.repository.rms.ModelingExposureDataSourceRepository;
import com.scor.rr.repository.rms.ProjectImportAssetLogRepository;
import com.scor.rr.repository.rms.RmsProjectImportConfigRepository;
import com.scor.rr.repository.tracking.ProjectImportLogRepository;
import com.scor.rr.repository.workspace.PortfolioRepository;
import com.scor.rr.repository.workspace.ProjectRepository;
import com.scor.rr.service.RmsDataProviderService;
import com.scor.rr.service.exposure.abstraction.ExposureSummaryTaskletWrapper;
import com.scor.rr.utils.Step;
import org.apache.commons.collections.keyvalue.MultiKey;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ExposureSummaryTaskletWrapperImpl implements ExposureSummaryTaskletWrapper
{
	private static final Logger log= LoggerFactory.getLogger(ExposureSummaryTaskletWrapperImpl.class);

	@Autowired
	private RmsDataProviderService rmsDataProvider;

	@Autowired
	private ProjectImportAssetLogRepository projectImportAssetLogRepository;

	@Autowired
	private ProjectRepository projectRepository;

//	@Autowired
//	private ProjectDatasourceAssociationRepository projectDatasourceAssociationRepository;

	@Autowired
	private PortfolioRepository portfolioRepository;

	@Autowired
	private ProjectImportLogRepository projectImportLogRepository;

	@Autowired
	private RmsExposureSummaryRepository rmsExposureSummaryRepository;

	@Autowired
	private ExposureSummaryRepository exposureSummaryRepository;

	@Autowired
	@Qualifier(value = "locationLevelExposure")
	private com.scor.rr.domain.entities.meta.exposure.LocationLevelExposure locationLevelExposure;

//	@Autowired
//	private GlobalViewRepository globalViewRepository;
//
//	@Autowired
//	private GlobalViewService globalViewService;

	// old code ri
//	@Autowired
//	private ImportDecisionRepository importDecisionRepository;

	@Autowired
	private RepresentationDatasetRepository representationDatasetRepository;

	@Autowired
	private ModelingExposureDataSourceRepository modelingExposureDataSourceRepository;

	@Autowired
	private RRPortfolioRepository rrPortfolioRepository;

	@Autowired
	private RRPortfolioStorageRepository rrPortfolioStorageRepository;

	private ExposureSummaryWriter exposureWriter;
	
	private String projectId;

	// old code ri
//	private String importDecisionId;

	// new code ri
	private String projectImportRunId; // last projectImportRunId

	// old code ri
//	private String pdaId;

	// new code ri
	private String rmspicId;

	private Integer importSequence;

	@Autowired
	private ModellingSystemInstanceRepository modellingSystemInstanceRepository;

	@Autowired
	private ProjectImportRunRepository projectImportRunRepository;

	@Autowired
	private RmsProjectImportConfigRepository rmsProjectImportConfigRepository;

	@Autowired
	private TransformationPackage transformationPackage;

	@Override
	public ExitStatus importExposureSummary()
	{
		long time=System.currentTimeMillis();
		log.info("importExposureSummary projectId:'{}', projectImportRunId:'{}' rmspicId:'{}'", new Object[] { projectId, projectImportRunId, rmspicId });

		try
		{
			// tracking rrImportingPortfolio
			Date startDate = new Date();

			List<ProjectImportRun> projectImportRunList = projectImportRunRepository.findByProjectProjectId(projectId);
			ProjectImportRun projectImportRun = projectImportRunRepository.findByProjectProjectIdAndRunId(projectId, projectImportRunList.size());
			Map<String, String> mapPortfolioRRPortfolioIds = transformationPackage.getMapPortfolioRRPortfolioIds();

			// begin tracking here so new HashMap<>();
			Map<String, String> mapPortfolioRRTrackingImportingPortfolioIds = new HashMap<>();

			// FIXME import either by PDA or ImportDecision
			Project project = projectRepository.findById(projectId).orElse(null);
			RmsProjectImportConfig rmspic = null;
			if (rmspicId != null)
			{
				rmspic = rmsProjectImportConfigRepository.findByProjectImportSourceConfigId(rmspicId);
			}

			//List<AssociationVersion> avList=associationVersionRepository.findByProject(project);
			//AssociationVersion version=avList.get(avList.size()-1);
			//log.debug("versions :{} => {}", new Object[] { avList.size(), ToStringBuilder.reflectionToString(version) });
			//ProjectDatasourceAssociation pda=projectDatasourceAssociationRepository.findByProjectAndAssociationVersion(project, version);
			log.debug("rmspic :{}", new Object[] { ToStringBuilder.reflectionToString(rmspic) });

			if(rmspic == null && projectImportRun == null)
			{
				// NOTA : maybe retrieve all PDA and or ImportDecision, but well ....
				log.error("RMSProjectImportConfig and ProjectImportRun from project {} is null.", projectId);
				return ExitStatus.NOOP;
			}

			// begin construction of the call parameters.
			// more or less each ExposureSummaryExtractInput should be paired with an
			// EDM
			// a bit of logic is necessary to go from "Representation" to a Set of
			// "EDM/Portfolio"
			//
			// FIXME : the RepresentedDataset stored in mongodb doesn't contain the project so it cannot be properly found.
			// FIXME use the PDA to retrieve the Represented Dataset to import.
			// that implie that the list of proper Portfolio to extract cannot be properly created.
			// FIXME : the ImportDecision is created by Thai when the import is done, we cannot assume that is properly exist before the import itself.
			// the ImportDecision object must be a parameter of this extraction to contruct the list to extract properly.
			// NOTA : in the meanwhile, assume that the PDA is containing proper relationship and deal with it.
			List<ExposureSummaryExtractInput> extractionInputList = new ArrayList<>(); // TODO Tien
			Set<Portfolio> portfolioGlobalSet = new HashSet<>();
			Set<ModelingExposureDataSource> edmToImport = new HashSet<>();
			if (projectImportRun!=null)
			{
				List<Portfolio> pfList = portfolioRepository.findByRmsProjectImportConfigId(rmspicId);
				if (pfList!=null)
				{
					for (Portfolio pf : pfList)
					{
						edmToImport.add(pf.getModelingExposureDataSource());
					}
				}
			}

			if (rmspic !=null) {
				Map<String, List<Portfolio>> edmPortfolioMap = new HashMap<>();
				// delve into the Representation to list all the EDM/Portfolio to import.
				List<SelectedAssociationBag> bags = rmspic.getSelectedAssociationBags();
				if (bags != null && !bags.isEmpty()) {
					RepresentationDataset dataset = representationDatasetRepository.findByRmsProjectImportConfigId(rmspicId);
					if (dataset != null) {
						dataset = representationDatasetRepository.findById(dataset.getRepresentationDatasetId()).orElse(null);
						if (dataset.getRepresentedPortfolios() != null && !dataset.getRepresentedPortfolios().isEmpty()) {
							for (Portfolio portfolio : dataset.getRepresentedPortfolios()) {
								RRPortfolio rrPortfolio = rrPortfolioRepository.findById(mapPortfolioRRPortfolioIds.get(portfolio.getPortfolioId())).orElse(null);

								// start tracking portfolio
								ProjectImportLog projectImportLogP = new ProjectImportLog();
								//mongoDBSequence.nextSequenceId(projectImportLogP);
								projectImportLogP.setProjectId(transformationPackage.getProjectId());
								projectImportLogP.setProjectImportRunId(projectImportRun.getProjectImportRunId());
								projectImportLogP.setAssetType(AssetType.PORTFOLIO.toString());
								projectImportLogP.setAssetId(rrPortfolio.getRrPortfolioId()); // tracking rrImportingAnalysis
								projectImportLogP.setStartDate(startDate);
								//projectImportLogP.setImportedBy(projectImportRun.getImportedBy());
								projectImportLogP.setStatus(TrackingStatus.INPROGRESS.toString());
								projectImportLogRepository.save(projectImportLogP);
								// endDate, status do more later

								mapPortfolioRRTrackingImportingPortfolioIds.put(portfolio.getPortfolioId(), projectImportLogP.getProjectImportRunId());

								portfolio.setImportStatus(projectImportLogP.getStatus());
								portfolioRepository.save(portfolio);

								ProjectImportAssetLog projectImportAssetLogP = new ProjectImportAssetLog();
								//mongoDBSequence.nextSequenceId(projectImportAssetLogP);
								projectImportAssetLogP.setProjectId(transformationPackage.getProjectId());
								projectImportAssetLogP.setProjectImportLogId(projectImportLogP.getProjectImportLogId());
								projectImportAssetLogP.setStepId(1);
								projectImportAssetLogP.setStepName(Step.getStepNameFromStepIdForPortfolio(projectImportAssetLogP.getStepId()));
								projectImportAssetLogP.setStartDate(startDate);
								projectImportAssetLogRepository.save(projectImportAssetLogP);

								// old code began here
								if (portfolio.getModelingExposureDataSource() != null) {
									String medsId = portfolio.getModelingExposureDataSource().getDataModelId();
									List<Portfolio> portfolios = edmPortfolioMap.get(medsId);
									if (portfolios == null) {
										portfolios = new ArrayList<>();
										edmPortfolioMap.put(medsId, portfolios);
									}
									portfolios.add(portfolio);
								} else {
									log.debug("Something wrong: portfolio id {} has not a ModelingExposureDataSource", portfolio.getPortfolioId());
								}
							}
						}
					} else {
						log.debug("Something wrong PDA id {}: not found in DB", rmspic.getRmsProjectImportConfigId());
					}
				} else {
					log.debug("PDA id {}: SelectedAssociationBags is null or empty", rmspic.getRmsProjectImportConfigId());
				}

				for (Map.Entry<String, List<Portfolio>> entry : edmPortfolioMap.entrySet()) {
					ModelingExposureDataSource meds = modelingExposureDataSourceRepository.findById(entry.getKey()).orElse(null);
					List<Portfolio> pfList = entry.getValue();
					if (meds != null) {
						ExposureSummaryExtractInput esei = new ExposureSummaryExtractInput(meds);
						log.debug("MEDS > {}:'{}' contain {} portfolio(s) ", new Object[]{meds.getRmsModelDataSource().getRmsId(), meds.getRmsModelDataSource().getName(), pfList.size()});
						for (Portfolio pf : pfList) {
							// log.debug("Portfolio > {}:{}:{} contains {} regions", new Object[] { pf.getRmsPortfolio().getPortfolioId(), pf.getRmsPortfolio().getType(), pf.getRmsPortfolio().getName(), pf.getRmsPortfolio().getAnalysisRegions().size() });

							PortfolioExposureSummaryExtractInput pesei = null;
							String conformedCurrency = StringUtils.isNotBlank(pf.getTargetCurrency()) ? pf.getTargetCurrency() : "USD";

							if (pf.getSelectedRmsPortfolioAnalysisRegions() != null && !pf.getSelectedRmsPortfolioAnalysisRegions().isEmpty()) {
								// if the list of selected region in the Portfolio is not empty, we have to remove those that have been unselected
								List<String> regionPerilToExclude = new ArrayList<>();
								// calculate the proper exclusion List
								Set<MultiKey> keySet = new HashSet<>();

								for (RmsPortfolioAnalysisRegion rpar : pf.getRmsPortfolio().getAnalysisRegions()) {
									keySet.add(new MultiKey(rpar.getPeril(), rpar.getAnalysisRegion()));
								}
								for (RmsPortfolioAnalysisRegion rpar : pf.getSelectedRmsPortfolioAnalysisRegions()) {
									if (!keySet.remove(new MultiKey(rpar.getPeril(), rpar.getAnalysisRegion()))) {
										log.warn("the portfolio {}:{}:{} had a {}:{} peril/analysisRegion said to be selected but not in the RmsPorfotio",
												new Object[]{pf.getRmsPortfolio().getPortfolioId(), pf.getRmsPortfolio().getType(), pf.getRmsPortfolio().getName(), rpar.getPeril(), rpar.getAnalysisRegion()});
									}
								}
								for (MultiKey mk : keySet) {
									// add all the key resulting from the (all-selected) set difference in the exclude list
									// NOTA : item => <PERIL>~<AnalysisRegion>

									//NOTE: quick fix for spira 65939
//									regionPerilToExclude.add("" + mk.getKey(0) + "~" + mk.getKey(1));
								}
								pesei = new PortfolioExposureSummaryExtractInput(pf, conformedCurrency, regionPerilToExclude);
							} else {
								// FIXME : add only selected portfolio Region selected
								pesei = new PortfolioExposureSummaryExtractInput(pf, conformedCurrency);
							}
							esei.addToPortfolioList(pesei);
						}
						portfolioGlobalSet.addAll(pfList);
						extractionInputList.add(esei);
					} else {
						log.debug("Something wrong: ModelingExposureDataSource id {} not found in DB", entry.getKey());
					}
				}
			}

			//GlobalView gv = globalViewService.createDefaultGlobalView(project, projectImportRun, portfolioGlobalSet, true);
			List<ExposureSummary> exposureSummaryList=new ArrayList<>();
			for(ExposureSummaryExtractInput esei:extractionInputList) // // TODO Tien
			{
				Map<MultiKey, String> mapMultiKeyRRIP = new HashMap<>();
				for (PortfolioExposureSummaryExtractInput input : esei.getPortfolioList()) {
					MultiKey key = new MultiKey(input.getPortfolio().getRmsPortfolio().getEdmId(), input.getPortfolio().getRmsPortfolio().getPortfolioId(), input.getPortfolio().getRmsPortfolio().getType());
					mapMultiKeyRRIP.put(key, mapPortfolioRRPortfolioIds.get(input.getPortfolio().getPortfolioId()));
				}

				// do the extraction
				ExposureSummaryExtractOutput out = rmsDataProvider.extractExposureSummary(mapMultiKeyRRIP, esei); // TODO Tien
				//
				// assign id and save resulting Exposure summary
				log.debug("will save exposures summaries to mongodb");
				for(RmsExposureSummary res:out.rmsExposureSummaries)
				{
					//this.mongoDBSequence.nextSequenceId(res);
					res.setImportStatus(projectImportRun.getStatus());
					log.debug("saving RmsExposureSummary {}:{} for EDM {}:{} portfolioId {} portfolioType {} peril {}",
							res.getRmsExposureSummaryId(), res.getExposureSummaryName(), res.getEdm().getRmsId(), res.getEdm().getName(), res.getPortfolioId(), res.getPortfolioType(), res.getPeril());
					rmsExposureSummaryRepository.save(res);
				}

				for(ExposureSummary es:out.exposureSummaries)
				{
					//this.mongoDBSequence.nextSequenceId(es);
					es.setImportStatus(projectImportRun.getStatus());
					exposureSummaryList.add(es);
					log.debug("saving ExposureSummary {}:{} for EDM {}:{} portfolioId {} portfolioType {} peril {}",
							es.getExposureSummaryId(), es.getExposureSummaryAlias(), es.getModelingExposureDataSource().getRmsModelDataSource().getRmsId(), es.getModelingExposureDataSource().getRmsModelDataSource().getName(), es.getPortfolioId(), es.getPortfolioType(), es.getPeril());
					exposureSummaryRepository.save(es);
				}
				log.debug("save exposures summaries to mongodb done");
			}

			// new code
			//mongoDBSequence.nextSequenceId(gv);
			// TODO new code
			// TODO : Review Later
//			gv.setRepresentedRRPortfolios(new HashSet<>(mapPortfolioRRPortfolioIds.values()));
//			gv.setRunId(projectImportRun.getRunId());
//
//			globalViewRepository.save(gv);
			//
			log.debug("save default global view to mongodb done");
			//

			log.debug("Starting extract EDM Detail Summary: {}", extractionInputList.size());
			String extractName = "RR_RL_GetEdmDetailSummary";
			for (ExposureSummaryExtractInput esei : extractionInputList) {
				RmsModelDatasource edm = esei.getModelingExposureDataSource().getRmsModelDataSource();
				List<String> portfolioIdList = new ArrayList<>();
				List<String> portfolioExcludeRegionPeril = new ArrayList<>();
				for(PortfolioExposureSummaryExtractInput pesei : esei.getPortfolioList())
				{
					RmsPortfolio pf = pesei.getPortfolio().getRmsPortfolio();
					String pfID = ""+pf.getPortfolioId()+"~"+pf.getType();
					String conformedCurrency="USD";
					if(pesei.getConformedCurrency()!=null)
					{
						conformedCurrency=pesei.getConformedCurrency();
					}
					portfolioIdList.add(pfID+"~"+conformedCurrency);
					if(pesei.getRegionPerilToExclude() != null && !pesei.getRegionPerilToExclude().isEmpty())
					{
						portfolioExcludeRegionPeril.add(pfID+"~"+ StringUtils.join(pesei.getRegionPerilToExclude(), '~'));
					}
				}

				for(PortfolioExposureSummaryExtractInput pesei : esei.getPortfolioList()) {
					RmsPortfolio pf = pesei.getPortfolio().getRmsPortfolio();
					File f = exposureWriter.makeDetailExposureFile(edm.getName(), pf.getPortfolioId());
					if (f == null) {
						log.error("Error while creating detail exposure file !");
					} else {
						log.debug("Export to file: {}}", f.getAbsolutePath());
						rmsDataProvider.extractDetailedExposure(f, edm, extractName, portfolioIdList, portfolioExcludeRegionPeril, esei.getRunId(), pf.getPortfolioId(), pf.getType());

						// dh modified
						byte[] buffer = new byte[1024];
						String zipPath = f.getParent();
						String zipfile = f.getName().replace("txt","zip");

						FileOutputStream fos = new FileOutputStream((zipPath + "/" +zipfile));
						ZipOutputStream zos = new ZipOutputStream(fos);
						ZipEntry ze= new ZipEntry(f.getName());
						zos.putNextEntry(ze);
						FileInputStream in = new FileInputStream( f.getAbsolutePath());

						int len;
						while ((len = in.read(buffer)) > 0) {
							zos.write(buffer, 0, len);
						}
						in.close();
						zos.closeEntry();
						f.delete();
						zos.close();

						log.debug("zip file name", zipfile);
						log.debug("zip file path", zipPath);

						List<ExposureSummaryExtractFile> extractFiles = new ArrayList<>();
						extractFiles.add(new ExposureSummaryExtractFile(new BinFile(zipfile, zipPath, null), "Detailed"));
						String rrPortfolioId = mapPortfolioRRPortfolioIds.get(pesei.getPortfolio().getPortfolioId());
						RRPortfolio rrPortfolio = rrPortfolioRepository.findById(rrPortfolioId).orElse(null);
						exposureWriter.writeExposureSummaryHeader(project, edm, pesei.getPortfolio(), rrPortfolio, ExposureSummaryExtractType.DETAILED_EXPOSURE_SUMMARY, extractFiles);
					}
				}
				rmsDataProvider.releaseContext(edm, esei.getRunId());
			}

			log.debug("Extract EDM Detail Summary completed");

			if (rmspic != null && BooleanUtils.isTrue(rmspic.getExtractLocSum())) {
				log.debug("Starting extract LocationLevelExposureDetails: {}", extractionInputList.size());
				for (ExposureSummaryExtractInput esei : extractionInputList) {
					RmsModelDatasource edm = esei.getModelingExposureDataSource().getRmsModelDataSource();
					for (PortfolioExposureSummaryExtractInput pesei : esei.getPortfolioList()) {
						List<ExposureSummaryExtractFile> extractFiles = new ArrayList<>();
						for (String schema : locationLevelExposure.getAllExtractNames()) {
							String extractFileType = locationLevelExposure.getExtractFileType(schema);
							if (extractFileType == null) {
								log.error("Extract file type for {} not found !", schema);
								continue;
							}
							File file = exposureWriter.makeLocLevelExposureFile(edm.getName(), pesei.getPortfolio(), extractFileType);
							if (file == null) {
								log.error("Error while creating location level exposure details file for {}!", schema);
								continue;
							} else {
								String query = locationLevelExposure.getQuery(schema);
								if (query == null) {
									log.error("Query for {} not found !", schema);
									continue;
								}
								log.debug("Export to file: {}}", file.getAbsolutePath());
								if (rmsDataProvider.extractLocationLevelExposureDetails(edm, pesei.getPortfolio(),file, schema, query)) {
									log.debug("==> success");
									extractFiles.add(new ExposureSummaryExtractFile(new BinFile(file), extractFileType));
								} else {
									log.debug("==> failed");
								}
							}
						}
						if (!extractFiles.isEmpty()) {
							String rrPortfolioId = mapPortfolioRRPortfolioIds.get(pesei.getPortfolio().getPortfolioId());
							RRPortfolio rrPortfolio = rrPortfolioRepository.findById(rrPortfolioId).orElse(null);
							exposureWriter.writeExposureSummaryHeader(project, edm, pesei.getPortfolio(), rrPortfolio, ExposureSummaryExtractType.LOCATION_LEVEL_EXPOSURE_DETAILS, extractFiles);
						}
					}
				}
				log.debug("Extract LocationLevelExposureDetails completed");
			}


			// finish tracking Portfolio --> finish import RRImportingPortfolio
			Date endDate = new Date();
			java.sql.Date endDateSql = new java.sql.Date(endDate.getTime());

			if (rmspic !=null) {
				List<SelectedAssociationBag> bags = rmspic.getSelectedAssociationBags();
				if (bags != null && !bags.isEmpty()) {
					RepresentationDataset dataset = representationDatasetRepository.findByRmsProjectImportConfigId(rmspicId);
					if (dataset != null) {
						dataset = representationDatasetRepository.findById(dataset.getRepresentationDatasetId()).orElse(null);
						if (dataset.getRepresentedPortfolios() != null && !dataset.getRepresentedPortfolios().isEmpty()) {
							for (Portfolio portfolio : dataset.getRepresentedPortfolios()) {

								ProjectImportLog projectImportLogP = projectImportLogRepository.findById(mapPortfolioRRTrackingImportingPortfolioIds.get(portfolio.getPortfolioId())).orElse(null);

								// finish import progress for portfolio step 1
								ProjectImportAssetLog projectImportAssetLogP = projectImportAssetLogRepository.findByProjectImportAssetLogIdAndStepId(projectImportLogP.getProjectImportLogId(),1);
								projectImportAssetLogP.setEndDate(endDate);
								projectImportAssetLogRepository.save(projectImportAssetLogP);
								log.info("Finish import progress STEP 1 : EXTRACT_AND_IMPORT_EXPOSURE_SUMMARY for portfolio: {}", portfolio.getPortfolioId());

								// finish tracking portfolio
								projectImportLogP.setEndDate(endDate);

								RRPortfolio rrPortfolio = rrPortfolioRepository.findById(mapPortfolioRRPortfolioIds.get(portfolio.getPortfolioId())).orElse(null);
								rrPortfolio.setImportedDate(endDateSql);

								if (!projectImportLogP.getStatus().equals(TrackingStatus.ERROR.toString())) {
									projectImportLogP.setStatus(TrackingStatus.DONE.toString());
									rrPortfolio.setImportStatus(TrackingStatus.DONE.toString());
									portfolio.setImportStatus(rrPortfolio.getImportStatus());
									portfolio.setImportedDoneAtLeastOnce(true);
									portfolioRepository.save(portfolio);
								}

								projectImportLogRepository.save(projectImportLogP);
								rrPortfolioRepository.save(rrPortfolio);

								transformationPackage.setMapPortfolioRRPortfolioIds(mapPortfolioRRPortfolioIds);
							}
						}
					}
				}
			}
			return ExitStatus.COMPLETED;
		}

		catch(Throwable th)
		{
			log.error("error in importExposureSummary projectId:'{}', projectImportRunId:'{}' rmspicId:'{}'", new Object[] { projectId,projectImportRunId,rmspicId});
			log.error(th.getMessage(), th);
			return ExitStatus.FAILED;
		}
		finally
		{
			log.info("importExposureSummary projectId:'{}', projectImportRunId:'{}' rmspicId:'{}' took {} ms", new Object[] { projectId,projectImportRunId,rmspicId,System.currentTimeMillis()-time });
		}
	}

}
