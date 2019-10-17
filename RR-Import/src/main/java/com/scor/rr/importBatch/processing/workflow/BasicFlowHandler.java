package com.scor.rr.importBatch.processing.workflow;

import com.scor.rr.domain.entities.cat.GlobalView;
import com.scor.rr.domain.entities.ihub.*;
import com.scor.rr.domain.entities.plt.ProjectImportRun;
import com.scor.rr.domain.entities.plt.RRAnalysis;
import com.scor.rr.domain.entities.references.User;
import com.scor.rr.domain.entities.rms.ProjectImportAssetLog;
import com.scor.rr.domain.entities.rms.RmsProjectImportConfig;
import com.scor.rr.domain.entities.tracking.ProjectImportLog;
import com.scor.rr.domain.entities.workspace.Portfolio;
import com.scor.rr.domain.entities.workspace.Project;
import com.scor.rr.domain.enums.TrackingStatus;
import com.scor.rr.importBatch.processing.batch.BaseBatchBeanImpl;
import com.scor.rr.importBatch.processing.batch.Notifier;
import com.scor.rr.importBatch.processing.treaty.TransformationBundle;
import com.scor.rr.importBatch.processing.treaty.TransformationPackage;
import com.scor.rr.repository.cat.GlobalViewRepository;
import com.scor.rr.repository.ihub.*;
import com.scor.rr.repository.plt.ProjectImportRunRepository;
import com.scor.rr.repository.plt.RRAnalysisRepository;
import com.scor.rr.repository.plt.ScorPLTHeaderRepository;
import com.scor.rr.repository.references.UserRepository;
import com.scor.rr.repository.rms.RmsProjectImportConfigRepository;
import com.scor.rr.repository.stat.RRStatisticHeaderRepository;
import com.scor.rr.repository.tracking.ProjectImportLogRepository;
import com.scor.rr.repository.workspace.PortfolioRepository;
import com.scor.rr.repository.workspace.ProjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

//import com.scor.almf.treaty.cdm.repository.dss.*;
//import com.scor.almf.treaty.cdm.repository.dss.agnostic.*;

/**
 * Created by U002629 on 17/03/2015.
 */
public class BasicFlowHandler extends BaseBatchBeanImpl implements FlowHandler {
    private static final Logger log = LoggerFactory.getLogger(BasicFlowHandler.class);

    private Notifier notifier;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SourceResultRepository sourceResultRepository;

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private TransformationPackage transformationPackage;

    private RmsProjectImportConfig rmsProjectImportConfig;

    private User user;

    private Project project;

    private Date endDate;

    private ProjectImportLog projectImportLog;

    private ProjectImportAssetLog projectImportAssetLog;

    private ProjectImportRun projectImportRun;
    @Autowired
    private ProjectImportLogRepository projectImportLogRepository;
    @Autowired
    private RRAnalysisRepository rrAnalysisRepository;
    @Autowired
    private RmsProjectImportConfigRepository rmsProjectImportConfigRepository;
    @Autowired
    private LinkedDatasetRepository linkedDatasetRepository;
    @Autowired
    private RepresentationDatasetRepository representationDatasetRepository;
    @Autowired
    private RRPortfolioRepository rrPortfolioRepository;
    @Autowired
    private ProjectImportRunRepository projectImportRunRepository;
    //    @Autowired
//    private MongoDBSequence mongoDBSequence;
    @Autowired
    private RRLinkedDataSetRepository rrLinkedDataSetRepository;
    @Autowired
    private RRRepresentationDatasetRepository rrRepresentationDatasetRepository;
    @Autowired
    private GlobalViewRepository globalViewRepository;
    @Autowired
    private ScorPLTHeaderRepository scorPLTHeaderRepository;
    @Autowired
    private RRLossTableRepository rrLossTableRepository;
    @Autowired
    private RRStatisticHeaderRepository rrStatisticHeaderRepository;

    private void initialize() {
        user = userRepository.findById(getUserId()).orElse(new User());
        project = projectRepository.findById(getProjectId()).orElse(new Project());
        log.info("initialize user {}", user.getUserId());
        log.info("initialize project {}", project.getProjectId());
    }

    @Override
    public ExitStatus handleInit() {
        log.debug("Starting handleInit");
        initialize();
        return ExitStatus.COMPLETED;
    }

    protected void clearData() {
        transformationPackage.gc();
        rmsProjectImportConfig = null;
        user = null;
        project = null;
    }

    @Override
    public Boolean handleCompletion() {
        log.debug("Starting handleCompletion");
        java.sql.Date endDate = new java.sql.Date((new Date()).getTime());

        ProjectImportLog projectImportLogPR = projectImportLogRepository.findById(transformationPackage.getProjectImportLogPRId()).orElse(new ProjectImportLog());
        projectImportLogPR.setEndDate(endDate);
        projectImportLogPR.setStatus(TrackingStatus.DONE.toString());
        projectImportLogRepository.save(projectImportLogPR);

        // build RR : linkDataset, representationDataset
        ProjectImportRun projectImportRun = projectImportRunRepository.findById(projectImportLogPR.getProjectImportRunId()).orElse(new ProjectImportRun());
        projectImportRun.setStatus(TrackingStatus.DONE.toString());
        projectImportRun.setEndDate(endDate);
        projectImportRunRepository.save(projectImportRun);

        List<LinkedDataset> linkedDatasets = linkedDatasetRepository.findByRmsProjectImportConfigId(getRmspicId());

        for (LinkedDataset linkedDataset : linkedDatasets) {
            List<String> linkedRRAnalysis = new ArrayList<>();
            List<String> linkedRRPortfolio = new ArrayList<>();
            if (linkedDataset.getLinkedPortfolio() != null) {
                for (Portfolio lkdPortfolio : linkedDataset.getLinkedPortfolio()) {
                    // before after import we can repair link/representation so must check Null here
                    if (transformationPackage.getMapPortfolioRRPortfolioIds().get(lkdPortfolio.getPortfolioId()) != null) {
                        linkedRRPortfolio.add(transformationPackage.getMapPortfolioRRPortfolioIds().get(lkdPortfolio.getPortfolioId()));
                    }
                }
            }
            if (linkedDataset.getLinkedSourceResult() != null) {
                for (SourceResult lkdAnalysis : linkedDataset.getLinkedSourceResult()) {
                    // before after import we can repair link/representation so must check Null here
                    if (transformationPackage.getMapAnalysisRRAnalysisIds().get(lkdAnalysis.getSourceResultId()) != null) {
                        linkedRRAnalysis.addAll(transformationPackage.getMapAnalysisRRAnalysisIds().get(lkdAnalysis.getSourceResultId()).values());
                    }
                }
            }
            if (linkedRRPortfolio.size() > 0 && linkedRRAnalysis.size() > 0) {
                RRLinkedDataSet rrLinkedDataSet = new RRLinkedDataSet();
                rrLinkedDataSet.setProjectId(getProjectId());
                rrLinkedDataSet.setProjectImportRunId(projectImportRun.getProjectImportRunId());
                rrLinkedDataSet.setName(linkedDataset.getName());
                // BIG WTF ??
                rrLinkedDataSet.setLinkedPortfolio(linkedRRPortfolio);
                rrLinkedDataSet.setLinkedAnalysis(linkedRRAnalysis);
                rrLinkedDataSetRepository.save(rrLinkedDataSet);
            }
        }

        // RRRepresentationDataset ---------------------------------------------------------------------------
        List<String> repRRPortfolio = new ArrayList<>();
        List<String> repRRAnalysis = new ArrayList<>();

        List<RRPortfolio> rrPortfolios = rrPortfolioRepository.findByProjectImportRunProjectImportRunId(projectImportRun.getProjectImportRunId());
        if (rrPortfolios != null && rrPortfolios.size() > 0) {
            for (RRPortfolio rrPortfolio : rrPortfolios) {
                repRRPortfolio.add(rrPortfolio.getPortfolioId());
            }
        }

        List<RRAnalysis> rrAnalyses = rrAnalysisRepository.findByProjectImportRunProjectImportRunId(projectImportRun.getProjectImportRunId());
        if (rrAnalyses != null && rrAnalyses.size() > 0) {
            for (RRAnalysis rrAnalysis : rrAnalyses) {
                repRRAnalysis.add(rrAnalysis.getAnalysisId());
            }
        }

        RRRepresentationDataset rrRepDataset = rrRepresentationDatasetRepository.findByProjectId(getProjectId());
        rrRepDataset.getRepresentationPortfolio().addAll(repRRPortfolio);
        rrRepDataset.getRepresentationAnalysis().addAll(repRRAnalysis);
        rrRepresentationDatasetRepository.save(rrRepDataset);

        GlobalView globalView = globalViewRepository.findByProjectProjectIdAndRunId(getProjectId(), projectImportRun.getRunId());
        if (globalView != null) {
            globalView.setImportStatus(projectImportRun.getStatus());
            globalViewRepository.save(globalView);
        }

        //TODO : Review orElse
        rmsProjectImportConfig = rmsProjectImportConfigRepository.findById(getRmspicId()).orElse(new RmsProjectImportConfig());
        rmsProjectImportConfig.setImporting(false);
        rmsProjectImportConfigRepository.save(rmsProjectImportConfig);

        Long jobId = 1L;
        notifier.notifyImport(jobId, rmsProjectImportConfig.getProjectId(), endDate.toString(), true);
        log.debug("handleCompletion completed");

        return true;
    }

    @Override
    public Boolean handleError() {
        log.debug("Starting handleError");
        endDate = new Date();

        // finish tracking PR
        if (transformationPackage.getProjectImportLogPRId() != null) {
            ProjectImportLog projectImportLogPR = projectImportLogRepository.findById(transformationPackage.getProjectImportLogPRId())
                    .orElse(null);
            if (projectImportLogPR != null) {
                projectImportLogPR.setEndDate(endDate);
                projectImportLogPR.setStatus(TrackingStatus.ERROR.toString());
                projectImportLogRepository.save(projectImportLogPR);
                // build RR : linkDataset, representationDataset
                ProjectImportRun projectImportRun = projectImportRunRepository.findById(projectImportLogPR.getProjectImportRunId()).orElse(null);
                projectImportRun.setStatus(TrackingStatus.ERROR.toString());
                if (projectImportRun != null) {
                    RmsProjectImportConfig rmsProjectImportConfig = rmsProjectImportConfigRepository.findByProjectImportSourceConfigId(projectImportRun.getProjectImportSourceConfigId());
                    if (rmsProjectImportConfig != null) {
                        // partie avant import no change :
                        List<LinkedDataset> linkedDatasets = linkedDatasetRepository.findByRmsProjectImportConfigId(getRmspicId());
                        for (LinkedDataset linkedDataset : linkedDatasets) {
                            List<String> linkedRRAnalysis = new ArrayList<>();
                            List<String> linkedRRPortfolio = new ArrayList<>();
                            if (linkedDataset.getLinkedPortfolio() != null) {
                                for (Portfolio lkdPortfolio : linkedDataset.getLinkedPortfolio()) {
                                    // before after import we can repair link/representation so must check Null here
                                    if (transformationPackage.getMapPortfolioRRPortfolioIds().get(lkdPortfolio.getPortfolioId()) != null) {
                                        if (!TrackingStatus.DONE.toString().equals(lkdPortfolio.getImportStatus())) {
                                            lkdPortfolio.setImportStatus(TrackingStatus.ERROR.toString());
                                            portfolioRepository.save(lkdPortfolio);
                                        }

                                        List<RRPortfolio> rrPortfolioList = rrPortfolioRepository.findByProjectImportRunProjectImportRunId(transformationPackage.getMapPortfolioRRPortfolioIds().get(lkdPortfolio.getPortfolioId()));
                                        rrPortfolioList.stream().forEach(rrPortfolio -> {
                                            if (!TrackingStatus.DONE.toString().equals(rrPortfolio.getImportStatus())) {
                                                rrPortfolio.setImportStatus(TrackingStatus.ERROR.toString());
                                                rrPortfolioRepository.save(rrPortfolio);
                                            }
                                            linkedRRPortfolio.add(transformationPackage.getMapPortfolioRRPortfolioIds().get(lkdPortfolio.getPortfolioId()));
                                        });


                                    }
                                }
                            }
                            if (linkedDataset.getLinkedSourceResult() != null) {
                                for (SourceResult lkdAnalysis : linkedDataset.getLinkedSourceResult()) {
                                    // before after import we can repair link/representation so must check Null here
                                    if (transformationPackage.getMapAnalysisRRAnalysisIds().get(lkdAnalysis.getSourceResultId()) != null) {
                                        if (!TrackingStatus.DONE.toString().equals(lkdAnalysis.getImportStatus())) {
                                            lkdAnalysis.setImportStatus(TrackingStatus.ERROR.toString());
                                            sourceResultRepository.save(lkdAnalysis);
                                        }

                                        for (Map.Entry<String, String> entry : transformationPackage.getMapAnalysisRRAnalysisIds().get(lkdAnalysis.getSourceResultId()).entrySet()) {
                                            rrAnalysisRepository.findById(entry.getValue()).ifPresent(rrAnalysis -> {
                                                if (!TrackingStatus.DONE.toString().equals(rrAnalysis.getImportStatus())) {
                                                    rrAnalysis.setImportStatus(TrackingStatus.ERROR.toString());
                                                    rrAnalysisRepository.save(rrAnalysis);
                                                }
                                            });
                                        }


                                        linkedRRAnalysis.addAll(transformationPackage.getMapAnalysisRRAnalysisIds().get(lkdAnalysis.getSourceResultId()).values());
                                    }
                                }
                            }
                            if (linkedRRPortfolio.size() > 0 && linkedRRAnalysis.size() > 0) {
                                RRLinkedDataSet rrLinkedDataSet = new RRLinkedDataSet();
                                rrLinkedDataSet.setProjectId(getProjectId());
                                rrLinkedDataSet.setName(linkedDataset.getName());
                                rrLinkedDataSet.setProjectImportRunId(projectImportRun.getProjectImportRunId());
                                rrLinkedDataSet.setLinkedAnalysis(linkedRRAnalysis);
                                rrLinkedDataSet.setLinkedPortfolio(linkedRRPortfolio);
                                rrLinkedDataSetRepository.save(rrLinkedDataSet);
                            }
                        }

                        // build RRRepresentationDataset ---------------------------------------------------------------------------
                        List<String> repRRPortfolio = new ArrayList<>();
                        List<String> repRRAnalysis = new ArrayList<>();
                        RepresentationDataset repDataset = representationDatasetRepository.findByRmsProjectImportConfigId(rmsProjectImportConfig.getRmsProjectImportConfigId());
                        if (repDataset != null) {
                            if (repDataset.getRepresentedPortfolios() != null) {
                                for (Portfolio repPortfolio : repDataset.getRepresentedPortfolios()) {
                                    // before after import we can repair link/representation so must check Null here
                                    if (transformationPackage.getMapPortfolioRRPortfolioIds().get(repPortfolio.getPortfolioId()) != null) {
                                        if (!TrackingStatus.DONE.toString().equals(repPortfolio.getImportStatus())) {
                                            repPortfolio.setImportStatus(TrackingStatus.ERROR.toString());
                                            portfolioRepository.save(repPortfolio);
                                        }

                                        rrPortfolioRepository.findById(transformationPackage.getMapPortfolioRRPortfolioIds().get(repPortfolio.getPortfolioId())).ifPresent(rrPortfolio -> {
                                            if (!TrackingStatus.DONE.toString().equals(rrPortfolio.getImportStatus())) {
                                                rrPortfolio.setImportStatus(TrackingStatus.ERROR.toString());
                                                rrPortfolioRepository.save(rrPortfolio);
                                            }

                                            repRRPortfolio.add(transformationPackage.getMapPortfolioRRPortfolioIds().get(repPortfolio.getPortfolioId()));
                                        });

                                    }
                                }
                            }

                            if (repDataset.getRepresentedSourceResults() != null) {
                                for (SourceResult repAnalysis : repDataset.getRepresentedSourceResults()) { // source
                                    // before after import we can repair link/representation so must check Null here
                                    if (transformationPackage.getMapAnalysisRRAnalysisIds().get(repAnalysis.getSourceResultId()) != null) {
                                        if (!TrackingStatus.DONE.toString().equals(repAnalysis.getImportStatus())) {
                                            repAnalysis.setImportStatus(TrackingStatus.ERROR.toString());
                                            sourceResultRepository.save(repAnalysis);
                                        }

                                        for (Map.Entry<String, String> entry : transformationPackage.getMapAnalysisRRAnalysisIds().get(repAnalysis.getSourceResultId()).entrySet()) {
                                            rrAnalysisRepository.findById(entry.getValue()).ifPresent(rrAnalysis -> {
                                                if (!TrackingStatus.DONE.toString().equals(rrAnalysis.getImportStatus())) {
                                                    rrAnalysis.setImportStatus(TrackingStatus.ERROR.toString());
                                                    rrAnalysisRepository.save(rrAnalysis);
                                                }
                                            });

                                        }

                                        repRRAnalysis.addAll(transformationPackage.getMapAnalysisRRAnalysisIds().get(repAnalysis.getSourceResultId()).values());
                                    }
                                }
                            }
                        }

                        if (repRRPortfolio.size() > 0 || repRRAnalysis.size() > 0) {
                            RRRepresentationDataset rrRepDataset = rrRepresentationDatasetRepository.findByProjectId(getProjectId());
                            rrRepDataset.getRepresentationPortfolio().addAll(repRRPortfolio);
                            rrRepDataset.getRepresentationAnalysis().addAll(repRRAnalysis);
                            rrRepresentationDatasetRepository.save(rrRepDataset);
                        }

                        GlobalView globalView = globalViewRepository.findByProjectProjectIdAndRunId(getProjectId(), projectImportRun.getRunId());
                        if (globalView != null) {
                            globalView.setImportStatus(projectImportRun.getStatus());
                            globalViewRepository.save(globalView);
                        }
                    }
                }
            }
        }

        rmsProjectImportConfig = rmsProjectImportConfigRepository.findById(getRmspicId()).orElse(new RmsProjectImportConfig());
        rmsProjectImportConfig.setImporting(false);
        rmsProjectImportConfigRepository.save(rmsProjectImportConfig);
        log.info("RMSPIC {}: status {}", rmsProjectImportConfig.getRmsProjectImportConfigId(), projectImportRun.getStatus());

        Long jobId = 1L;
        notifier.notifyImport(jobId, rmsProjectImportConfig.getRmsProjectImportConfigId(), endDate.toString(), true);

        log.debug("handleError completed");
        return true;
    }

    @Override
    public Boolean handleRunning() {
        log.debug("Starting handleRunning");
        addError("EXPRESS IMPORT", "An extraction is already running for this CAR id, division and period basis combination");
        log.debug("handleRunning completed");
        return true;
    }

    public Boolean handleAnalysisImportCompletion() {
        endDate = new Date();
        java.sql.Date endDateSql = new java.sql.Date(endDate.getTime());

        for (TransformationBundle bundle : transformationPackage.getBundles()) {
            ProjectImportLog projectImportLogA = projectImportLogRepository.findById(bundle.getProjectImportLogAnalysisId()).get();
            projectImportLogA.setEndDate(endDate);

            Map<String, String> fpRRAnalysis = transformationPackage.getMapAnalysisRRAnalysisIds().get(bundle.getSourceResult().getSourceResultId());
            if (fpRRAnalysis != null && fpRRAnalysis.get(bundle.getFinancialPerspective().getDisplayCode()) != null) {
                RRAnalysis rrAnalysis = rrAnalysisRepository.findById(fpRRAnalysis.get(bundle.getFinancialPerspective().getDisplayCode())).orElse(null);
                if (rrAnalysis != null) {
                    rrAnalysis.setImportedDate(endDateSql);

                    if (!projectImportLogA.getStatus().equals(TrackingStatus.ERROR.toString())) {
                        projectImportLogA.setStatus(TrackingStatus.DONE.toString());
                        rrAnalysis.setImportStatus(TrackingStatus.DONE.toString());
                        bundle.getSourceResult().setImportStatus(rrAnalysis.getImportStatus());
                        bundle.getSourceResult().setImportedDoneAtLeastOnce(true);
                        sourceResultRepository.save(bundle.getSourceResult());
                    }

                    projectImportLogRepository.save(projectImportLogA);
                    rrAnalysisRepository.save(rrAnalysis);
                }
            }


        }
        rmsProjectImportConfig = rmsProjectImportConfigRepository.findById(getRmspicId()).get();
        projectImportRun = projectImportRunRepository.findById(rmsProjectImportConfig.getLastProjectImportRunId()).get();
        projectImportRun.setLossImportEndDate(endDateSql); // it's the last projectImportRun
        projectImportRunRepository.save(projectImportRun);
        return true;
    }

    public RmsProjectImportConfig getRmsProjectImportConfig() {
        return rmsProjectImportConfig;
    }

    public void setRmsProjectImportConfig(RmsProjectImportConfig rmsProjectImportConfig) {
        this.rmsProjectImportConfig = rmsProjectImportConfig;
    }

    public Notifier getNotifier() {
        return notifier;
    }

    public void setNotifier(Notifier notifier) {
        this.notifier = notifier;
    }

    public ProjectImportLog getProjectImportLog() {
        return projectImportLog;
    }

    public void setProjectImportLog(ProjectImportLog projectImportLog) {
        this.projectImportLog = projectImportLog;
    }

    public ProjectImportAssetLog getProjectImportAssetLog() {
        return projectImportAssetLog;
    }

    public void setProjectImportAssetLog(ProjectImportAssetLog projectImportAssetLog) {
        this.projectImportAssetLog = projectImportAssetLog;
    }

    public ProjectImportRun getProjectImportRun() {
        return projectImportRun;
    }

    public void setProjectImportRun(ProjectImportRun projectImportRun) {
        this.projectImportRun = projectImportRun;
    }

    class CustomeError {
        Boolean error;
        String errorMessage;

        public CustomeError() {
            error = Boolean.FALSE;
            errorMessage = null;
        }

        public CustomeError(Boolean error, String errorMessage) {
            this.error = error;
            this.errorMessage = errorMessage;
        }

        public Boolean getError() {
            return error;
        }

        public void setError(Boolean error) {
            this.error = error;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }
    }
}
