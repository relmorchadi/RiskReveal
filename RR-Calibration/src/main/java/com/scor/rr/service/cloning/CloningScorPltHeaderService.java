package com.scor.rr.service.cloning;

import com.scor.rr.configuration.UtilsMethod;
import com.scor.rr.configuration.security.UserPrincipal;
import com.scor.rr.domain.*;
import com.scor.rr.domain.dto.ClonePltsRequest;
import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.repository.*;
import com.scor.rr.service.adjustement.AdjustmentNodeOrderService;
import com.scor.rr.service.adjustement.AdjustmentNodeProcessingService;
import com.scor.rr.service.adjustement.AdjustmentNodeService;
import com.scor.rr.service.adjustement.AdjustmentThreadService;
import com.scor.rr.utils.RRDateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CloningScorPltHeaderService {


    @Autowired
    private AdjustmentThreadRepository adjustmentThreadRepository;
    @Autowired
    ModelAnalysisEntityRepository modelAnalysisEntityRepository;

    @Autowired
    LossDataHeaderEntityRepository lossDataHeaderEntityRepository;

    @Autowired
    EPCurveHeaderEntityRepository epCurveHeaderEntityRepository;

    @Autowired
    SummaryStatisticHeaderRepository summaryStatisticHeaderRepository;

    @Autowired
    PltHeaderRepository pltHeaderRepository;

    @Autowired
    AdjustmentThreadService threadService;

    //TODO: we need to clone ALL properties from source PLT EXCEPT the following ones:
    // - createdDate, inuringPackageId, pltLossDataFileName, pltLossDataFilePath, projectByFkProjectId, binFileEntity: refer to target context
    // - publishToPricing: reset to FALSE
    @Autowired
    AdjustmentNodeService nodeService;

    @Autowired
    AdjustmentNodeOrderService adjustmentNodeOrderService;

    @Autowired
    AdjustmentNodeProcessingService processingService;

    @Autowired
    WorkspaceRepository workspaceRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Value("${ihub.contract.path}")
    String ihubContractPath;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");

    /**
     *
     *  Create project fct !!!!
     *
     * */
    private ProjectEntity createNewProject( Integer workspaceUwYear, String workspaceContextCode,
                                               String projectName,String projectDescription)
                                            throws com.scor.rr.exceptions.RRException{
        if (this.workspaceRepository.findByWorkspaceContextCodeAndWorkspaceUwYear(workspaceContextCode, workspaceUwYear).isPresent()) {
            WorkspaceEntity workspaceTarget = this.workspaceRepository.findByWorkspaceContextCodeAndWorkspaceUwYear
                    (workspaceContextCode, workspaceUwYear).get();
            ProjectEntity newProject = new ProjectEntity();
            newProject.initProject(workspaceTarget.getWorkspaceId());
            newProject.setProjectName(projectName);
            newProject.setProjectDescription(projectDescription);
            newProject.setCreationDate(RRDateUtils.getDateNow());
            newProject.setIsCloned(true);
            newProject.setEntity(1);

            UserRrEntity user = ( (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
            newProject.setCreatedBy(user.getFirstName() + " " + user.getLastName());
            newProject = this.projectRepository.save(newProject);
            return newProject;
        }
        else {
            throw new com.scor.rr.exceptions.RRException(ExceptionCodename.WORKSPACE_NOT_FOUND, 1);
        }
    }

    public ProjectEntity getCloningIntoProject(String cloningType, Integer workspaceUwYear, String workspaceContextCode,
                                               String projectName, String projectDescription, Long existingProjectId,
                                               List<PltHeaderEntity> pltsResults)
    throws com.scor.rr.exceptions.RRException{
        if (this.workspaceRepository.findByWorkspaceContextCodeAndWorkspaceUwYear(workspaceContextCode, workspaceUwYear).isPresent()) {
            WorkspaceEntity workspaceTarget = this.workspaceRepository.findByWorkspaceContextCodeAndWorkspaceUwYear
                    (workspaceContextCode, workspaceUwYear).get();
            switch(cloningType) {
                case "KEEP_PROJECT_NAME":
                    for(PltHeaderEntity plt : pltsResults) {
                        ProjectEntity project = this.projectRepository.findById(plt.getProjectId()).get();
                        if (project.getProjectName().equals(projectName)){
                            return project;
                        }
                    }
                case "NEW_PROJECT":
                    for(PltHeaderEntity plt : pltsResults) {
                        ProjectEntity project = this.projectRepository.findById(plt.getProjectId()).get();
                        if (project.getProjectName().equals(projectName)){
                            return project;
                        }
                    }
                    return this.createNewProject(workspaceUwYear,
                            workspaceContextCode,
                            projectName,
                            projectDescription);
                case "EXISTING_PROJECT":
                    Optional<ProjectEntity> projs = this.projectRepository.findById(existingProjectId);
                    if (projs.isPresent()) {
                        return projs.get();
                    } else {
                        throw new com.scor.rr.exceptions.RRException(ExceptionCodename.PROJECT_NOT_FOUND, 1);
                    }
                default:
                    throw new com.scor.rr.exceptions.RRException(ExceptionCodename.PROJECT_NOT_FOUND, 1);
            }

        } else {
            throw new com.scor.rr.exceptions.RRException(ExceptionCodename.WORKSPACE_NOT_FOUND, 1);
        }
    }


    public List<PltHeaderEntity> cloneDataPlts(ClonePltsRequest request)
            throws com.scor.rr.exceptions.RRException{

        List<PltHeaderEntity> pltsResults = new ArrayList<PltHeaderEntity>();

        for(Long pltId : request.getPltIds()) {
            // create new plt header entity
            PltHeaderEntity newPLT = this.cloneScorPltHeader(pltId);
            PltHeaderEntity sourcePlt = this.pltHeaderRepository.findByPltHeaderId(pltId);

            ProjectEntity sourceProject = this.projectRepository.findById(sourcePlt.getProjectId()).get();

            // clone the pure plt header

            Optional<AdjustmentThread> optionalAdjustmentThread = this.adjustmentThreadRepository.findByFinalPLT(sourcePlt);
            Long sourcePurePltId = null;
            if(optionalAdjustmentThread.isPresent())
                sourcePurePltId = optionalAdjustmentThread.get().getInitialPLT().getPltHeaderId();

            PltHeaderEntity newPurePlt = this.cloneScorPltHeader(sourcePurePltId);
            AdjustmentThread newAdjustmentThread = new AdjustmentThread();
              newAdjustmentThread.setInitialPLT(newPurePlt);
            newAdjustmentThread.setFinalPLT(newPLT);
            newAdjustmentThread.setLocked(false);
            newAdjustmentThread.setThreadIndex(1);
            newAdjustmentThread.setThreadStatus("valid");
            this.adjustmentThreadRepository.save(newAdjustmentThread);
            // clone model analysis
            Optional<ModelAnalysisEntity> modelAnalysisEntity = this.modelAnalysisEntityRepository.findById(sourcePlt.getModelAnalysisId());
            if (modelAnalysisEntity.isPresent()) {

                ModelAnalysisEntity other = new ModelAnalysisEntity(modelAnalysisEntity.get());
                other.setCreationDate(RRDateUtils.getDateNow());
                other.setProjectId(newPLT.getProjectId());
                other = this.modelAnalysisEntityRepository.save(other);
                newPLT.setModelAnalysisId(other.getRrAnalysisId());


            } else {
                throw new com.scor.rr.exceptions.RRException(ExceptionCodename.MODEL_ANALYSIS_NOT_FOUND, 1);
            }
            // clone LossDataHeader
            List<LossDataHeaderEntity> lossDataHeaderEntities = this.lossDataHeaderEntityRepository.findByModelAnalysisId(modelAnalysisEntity.get().getRrAnalysisId());
            LossDataHeaderEntity newLossData = new LossDataHeaderEntity();
            for (LossDataHeaderEntity lossDataHeaderEntity : lossDataHeaderEntities) {
                newLossData = new LossDataHeaderEntity(lossDataHeaderEntity);
                newLossData.setModelAnalysisId(newPLT.getModelAnalysisId());
                newLossData.setCloningSourceId(lossDataHeaderEntity.getLossDataHeaderId());
                newLossData = this.lossDataHeaderEntityRepository.save(newLossData);
                // clone ep curve
                List<EPCurveHeaderEntity> epCurveHeaderEntities = this.epCurveHeaderEntityRepository.findByLossDataId(lossDataHeaderEntity.getLossDataHeaderId());
                for(EPCurveHeaderEntity epCurveHeaderEntity : epCurveHeaderEntities) {
                    EPCurveHeaderEntity newEpCurve = new EPCurveHeaderEntity(epCurveHeaderEntity);
                    newEpCurve.setLossDataId(newLossData.getLossDataHeaderId());
                    this.epCurveHeaderEntityRepository.save(newEpCurve);
                }
            }
            // clone Summary Statistics Header
            Optional<SummaryStatisticHeaderEntity> summaryStatisticHeaderEntity = this.summaryStatisticHeaderRepository.findById(sourcePlt.getSummaryStatisticHeaderId());
            if (summaryStatisticHeaderEntity.isPresent()) {
                SummaryStatisticHeaderEntity other = new SummaryStatisticHeaderEntity(summaryStatisticHeaderEntity.get(), true);
                other = this.summaryStatisticHeaderRepository.save(other);
                newPLT.setSummaryStatisticHeaderId(other.getSummaryStatisticHeaderId());
            } else {
                throw new com.scor.rr.exceptions.RRException(ExceptionCodename.SUMMARY_STATISTICS_HEADER_NOT_FOUND,1);
            }

            // copy plt files
            try {
                File dstFile = this.copyPltFile(sourcePlt, newPLT ,
                        this.ihubContractPath+ request.getTargetWorkspaceContextCode()
                                + "/" + request.getTargetWorkspaceUwYear() + "/"  +
                                this.projectRepository.findById(newPLT.getProjectId()).get().getProjectName()
                );
                newPLT.setLossDataFilePath(dstFile.getParent());
                newPLT.setLossDataFileName(dstFile.getName());
            } catch (Exception e) {
                e.printStackTrace();
            }


            // get or create target project (depends on clone type)
            switch(request.getCloningType()) {
                case "KEEP_PROJECT_NAME":
                    newPLT.setProjectId(this.getCloningIntoProject(
                            request.getCloningType(),
                            request.getTargetWorkspaceUwYear(),
                            request.getTargetWorkspaceContextCode(),
                            sourceProject.getProjectName(),
                            sourceProject.getProjectDescription(),
                            request.getExistingProjectId(), pltsResults).getProjectId());
                    break;
                case "NEW_PROJECT":
                    newPLT.setProjectId(this.getCloningIntoProject(request.getCloningType(),
                            request.getTargetWorkspaceUwYear(),
                            request.getTargetWorkspaceContextCode(),
                            request.getNewProjectName(),
                            request.getNewProjectDescription(),
                            request.getExistingProjectId(), pltsResults).getProjectId());
                    break;
                case "EXISTING_PROJECT":
                    newPLT.setProjectId(this.getCloningIntoProject(
                            request.getCloningType(),
                            request.getTargetWorkspaceUwYear(),
                            request.getTargetWorkspaceContextCode(),
                            null,
                            null,
                            request.getExistingProjectId(),pltsResults).getProjectId());
                    break;
            }
            newPurePlt.setProjectId(newPLT.getProjectId());
            this.pltHeaderRepository.save(newPurePlt);
            pltsResults.add(this.pltHeaderRepository.save(newPLT));
        }
        return pltsResults;
    }
    // TODO:  fix this !!!!!!!!!!!

    public PltHeaderEntity cloneScorPltHeader(Long pltId) throws com.scor.rr.exceptions.RRException {
        PltHeaderEntity plt = pltHeaderRepository.findByPltHeaderId(pltId);
        if (plt != null) {

            PltHeaderEntity newPLT = new PltHeaderEntity(plt);
            newPLT.setCreatedDate(RRDateUtils.getDateNow());
            newPLT.setCloningSourceId(pltId);
     //       newPLT.setIsLocked(false);

            return pltHeaderRepository.save(newPLT);
        } else {
            throw new com.scor.rr.exceptions.RRException(ExceptionCodename.PLT_NOT_FOUND, 1);
        }
    }

    private File copyPltFile(PltHeaderEntity plt, PltHeaderEntity newPLT, String targetPath) throws Exception {

        String dstFilePath = targetPath ;
        File sourceFile = new File(plt.getLossDataFilePath(), plt.getLossDataFileName());
        File dstFile = new File(dstFilePath, "PLT_" + newPLT.getPltHeaderId() + "_" + newPLT.getPltType() + "_" + sdf.format(new Date()) + ".bin");
        UtilsMethod.copyFile(sourceFile, dstFile);
        return dstFile;
        }



//    public PltHeaderEntity clonePltWithAdjustment(Long pltHeaderEntityInitialId, String workspaceId) throws com.scor.rr.exceptions.RRException {
//        PltHeaderEntity scorPltHeaderCloned = cloneScorPltHeader(pltHeaderEntityInitialId);
//        if (scorPltHeaderCloned.getPltType().equalsIgnoreCase("pure")) {
//            AdjustmentThread threadCloned = threadService.cloneThread(pltHeaderEntityInitialId, scorPltHeaderCloned);
//            if (threadCloned != null) {
//                AdjustmentThread threadParent = threadService.getByPltHeader(435L); // what ???
//                List<AdjustmentNode> nodeEntities = nodeService.cloneNode(threadCloned, threadParent);
//                if (nodeEntities != null) {
//                    for (AdjustmentNode adjustmentNodeCloned : nodeEntities) {
//                        AdjustmentNodeOrder order = adjustmentNodeOrderService.findByAdjustmentNode(adjustmentNodeCloned);
//                        adjustmentNodeOrderService.createNodeOrder(adjustmentNodeCloned, order.getAdjustmentOrder());
//                    }
//                    processingService.cloneAdjustmentNodeProcessing(nodeEntities, threadParent, threadCloned);
//                    return scorPltHeaderCloned;
//                } else {
//                    throw new com.scor.rr.exceptions.RRException(ExceptionCodename.NODE_NOT_FOUND, 1);
//                }
//            } else {
//                throw new com.scor.rr.exceptions.RRException(ExceptionCodename.THREAD_NOT_FOUND, 1);
//            }
//        } else {
//            return scorPltHeaderCloned;
//        }
//    }

}
