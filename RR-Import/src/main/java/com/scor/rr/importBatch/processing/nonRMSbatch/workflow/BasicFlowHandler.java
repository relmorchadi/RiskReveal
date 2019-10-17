/*
package com.scor.rr.importBatch.processing.nonRMSbatch.workflow;

import com.scor.rr.domain.entities.plt.ProjectImportRun;
import com.scor.rr.domain.entities.references.User;
import com.scor.rr.domain.entities.workspace.Project;
import com.scor.rr.importBatch.domain.*;
import com.scor.rr.importBatch.processing.batch.Notifier;
import com.scor.rr.importBatch.processing.nonRMSbatch.bean.BaseNonRMSBean;
import com.scor.rr.importBatch.processing.nonRMSbatch.workflow.bundle.TransformationBundleNonRMS;
import com.scor.rr.importBatch.processing.nonRMSbatch.workflow.bundle.TransformationPackageNonRMS;
import com.scor.rr.importBatch.repository.*;
import com.scor.rr.repository.references.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import com.scor.almf.treaty.cdm.repository.dss.agnostic.*;

*/
/**
 * Created by U005342 on 14/07/2018.
 *//*

public class BasicFlowHandler extends BaseNonRMSBean implements FlowHandler {

    private static final Logger log= LoggerFactory.getLogger(BasicFlowHandler.class);

    private Notifier notifier;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NonRmsProjectImportConfigRepository nonRmsProjectImportConfigRepository;

    private NonRmsProjectImportConfig nonRmsProjectImportConfig;

    private User user;

    private Project project;

    private Date endDate;

    private ProjectImportLog projectImportLog;

    private ProjectImportAssetLog projectImportAssetLog;

    private ProjectImportRun projectImportRun;

    private TransformationPackageNonRMS transformationPackage;

    @Override
    public Boolean handleCompletion() {
        log.debug("Starting handleCompletion");
        endDate = new Date();

        for (TransformationBundleNonRMS bundle : transformationPackage.getBundles()) {
            ProjectImportLog projectImportLogA = projectImportLogRepository.findOne(bundle.getProjectImportLogAnalysisId());
            projectImportLogA.setEndDate(endDate);

            RRAnalysis rrAnalysis = rrAnalysisRepository.findOne(bundle.getRrAnalysis().getId());
            rrAnalysis.setImportedDate(endDate);

            if (!projectImportLogA.getStatus().equals(ProjectImportLog.TrackingStatus.Error.toString())) {
                projectImportLogA.setStatus(ProjectImportLog.TrackingStatus.Done.toString());
                rrAnalysis.setImportStatus(ProjectImportLog.TrackingStatus.Done.toString());
                bundle.getSourceResult().setImportStatus(rrAnalysis.getImportStatus());
                bundle.getSourceResult().setImportedDoneAtLeastOnce(true);
                fileImportSourceResultRepository.save(bundle.getSourceResult());
            }

            projectImportLogRepository.save(projectImportLogA);
            rrAnalysisRepository.save(rrAnalysis);
        }

        // finish tracking PR
        ProjectImportLog projectImportLogPR = projectImportLogRepository.findOne(transformationPackage.getProjectImportLogPRId());
        projectImportLogPR.setEndDate(endDate);
        projectImportLogPR.setStatus(ProjectImportLog.TrackingStatus.Done.toString());
        projectImportLogRepository.save(projectImportLogPR);

        // build RR : representationDataset
        ProjectImportRun projectImportRun = projectImportRunRepository.findOne(projectImportLogPR.getProjectImportRunId());
        projectImportRun.setStatus(ProjectImportLog.TrackingStatus.Done.toString());
        projectImportRun.setEndDate(endDate);
        projectImportRunRepository.save(projectImportRun);

        // RRRepresentationDataset ---------------------------------------------------------------------------
        List<String> repRRAnalysis = new ArrayList<>();

        List<RRAnalysis> rrAnalyses = rrAnalysisRepository.findByProjectImportRunProjectImportRunId(projectImportRun.getId());
        if (rrAnalyses != null && rrAnalyses.size() > 0) {
            for (RRAnalysis rrAnalysis : rrAnalyses) {
                repRRAnalysis.add(rrAnalysis.getId());
            }
        }

        RRRepresentationDataset rrRepDataset = rrRepresentationDatasetRepository.findByProjectProjectId(getProjectId());
        rrRepDataset.getRepresentationAnalysis().addAll(repRRAnalysis);
        rrRepresentationDatasetRepository.save(rrRepDataset);

        nonRmsProjectImportConfig = nonRmsProjectImportConfigRepository.findOne(getNonrmspicId());
        nonRmsProjectImportConfig.setImporting(false);
        nonRmsProjectImportConfigRepository.save(nonRmsProjectImportConfig);

        log.debug("handleCompletion completed");

        return true;
    }

    @Override
    public Boolean handleError(){
        log.debug("Starting handleError");
        endDate = new Date();

        if (transformationPackage.getProjectImportLogPRId() != null) {
            ProjectImportLog projectImportLogPR = projectImportLogRepository.findOne(transformationPackage.getProjectImportLogPRId());
            if (projectImportLogPR != null) {
                projectImportLogPR.setEndDate(endDate);
                projectImportLogPR.setStatus(ProjectImportLog.TrackingStatus.Error.toString());
                projectImportLogRepository.save(projectImportLogPR);
                // build RR : representationDataset
                ProjectImportRun projectImportRun = projectImportRunRepository.findOne(projectImportLogPR.getProjectImportRunId());
                projectImportRun.setStatus(ProjectImportLog.TrackingStatus.Error.toString());
                if (projectImportRun != null) {
                    RmsProjectImportConfig rmsProjectImportConfig = rmsProjectImportConfigRepository.findByProjectImportSourceConfigId(projectImportRun.getProjectImportSourceConfigId());
                    if (rmsProjectImportConfig != null) {

                        // build RRRepresentationDataset ---------------------------------------------------------------------------
                        List<String> repRRAnalysis = new ArrayList<>();

                        for (TransformationBundleNonRMS bundle : transformationPackage.getBundles()) {
                            if (bundle.getRrAnalysis().getId() != null) {

                                ProjectImportLog projectImportLogA = projectImportLogRepository.findOne(bundle.getProjectImportLogAnalysisId());
                                projectImportLogA.setEndDate(endDate);

                                RRAnalysis rrAnalysis = rrAnalysisRepository.findOne(bundle.getRrAnalysis().getId());
                                rrAnalysis.setImportedDate(endDate);

                                if (!projectImportLogA.getStatus().equals(ProjectImportLog.TrackingStatus.Error.toString())) {
                                    projectImportLogA.setStatus(ProjectImportLog.TrackingStatus.Done.toString());
                                    rrAnalysis.setImportStatus(ProjectImportLog.TrackingStatus.Done.toString());
                                    bundle.getSourceResult().setImportStatus(rrAnalysis.getImportStatus());
                                    bundle.getSourceResult().setImportedDoneAtLeastOnce(true);
                                    fileImportSourceResultRepository.save(bundle.getSourceResult());
                                }

                                if (!ProjectImportLog.TrackingStatus.Done.toString().equals(rrAnalysis.getImportStatus())) {
                                    rrAnalysis.setImportStatus(ProjectImportLog.TrackingStatus.Error.toString());
                                    rrAnalysisRepository.save(rrAnalysis);
                                    bundle.getSourceResult().setImportStatus(rrAnalysis.getImportStatus());
                                    fileImportSourceResultRepository.save(bundle.getSourceResult());
                                }

                                repRRAnalysis.add(rrAnalysis.getId());
                            }
                        }

                        if (repRRAnalysis.size() > 0) {
                            RRRepresentationDataset rrRepDataset = rrRepresentationDatasetRepository.findByProjectProjectId(getProjectId());
                            rrRepDataset.getRepresentationAnalysis().addAll(repRRAnalysis);
                            rrRepresentationDatasetRepository.save(rrRepDataset);
                        }

                    }
                }
            }
        }

        nonRmsProjectImportConfig = nonRmsProjectImportConfigRepository.findOne(getNonrmspicId());
        nonRmsProjectImportConfig.setImporting(false);
        nonRmsProjectImportConfigRepository.save(nonRmsProjectImportConfig);
        log.info("NonRMSPIC {}: status {}", nonRmsProjectImportConfig.getId(), projectImportRun.getStatus());

        log.debug("handleError completed");
        return true;
    }

    @Autowired
    private ProjectImportLogRepository projectImportLogRepository;

    @Autowired
    private RRAnalysisRepository rrAnalysisRepository;

    @Autowired
    private RmsProjectImportConfigRepository rmsProjectImportConfigRepository;

    @Autowired
    private RepresentationDatasetRepository representationDatasetRepository;


    @Autowired
    private ProjectImportRunRepository projectImportRunRepository;

    @Autowired
    private MongoDBSequence mongoDBSequence;

    @Autowired
    private FileImportSourceResultRepository fileImportSourceResultRepository;

    @Autowired
    private RRRepresentationDatasetRepository rrRepresentationDatasetRepository;

    @Autowired
    private ScorPLTHeaderRepository scorPLTHeaderRepository;

    @Autowired
    private RRLossTableRepository rrLossTableRepository;

    @Autowired
    private RRStatisticHeaderRepository rrStatisticHeaderRepository;

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

    public TransformationPackageNonRMS getTransformationPackage() {
        return transformationPackage;
    }

    public void setTransformationPackage(TransformationPackageNonRMS transformationPackage) {
        this.transformationPackage = transformationPackage;
    }
}

*/
