package com.scor.rr.service.cloning;

import com.scor.rr.configuration.UtilsMethod;
import com.scor.rr.configuration.security.UserPrincipal;
import com.scor.rr.domain.*;
import com.scor.rr.domain.dto.ClonePltsRequest;
import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.repository.ModelAnalysisEntityRepository;
import com.scor.rr.repository.PltHeaderRepository;
import com.scor.rr.repository.ProjectRepository;
import com.scor.rr.repository.WorkspaceRepository;
import com.scor.rr.service.adjustement.AdjustmentNodeOrderService;
import com.scor.rr.service.adjustement.AdjustmentNodeProcessingService;
import com.scor.rr.service.adjustement.AdjustmentNodeService;
import com.scor.rr.service.adjustement.AdjustmentThreadService;
import com.scor.rr.utils.RRDateUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    ModelAnalysisEntityRepository modelAnalysisEntityRepository;

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
                                               String projectName, String projectDescription, Long existingProjectId)
    throws com.scor.rr.exceptions.RRException{
        //System.out.println("[clone data]: creating target project; cloning type : " + cloningType);
        //System.out.println(projectDescription + " " + projectName);
        if (this.workspaceRepository.findByWorkspaceContextCodeAndWorkspaceUwYear(workspaceContextCode, workspaceUwYear).isPresent()) {
            WorkspaceEntity workspaceTarget = this.workspaceRepository.findByWorkspaceContextCodeAndWorkspaceUwYear
                    (workspaceContextCode, workspaceUwYear).get();
            Optional<ProjectEntity> projs = this.projectRepository.findByProjectNameAndWorkspaceId(projectName, workspaceTarget.getWorkspaceId());
            if (projs.isPresent()) {
                return projs.get();
            } else {
                /**
                 *  CREATE PROJECT
                 */
                return this.createNewProject(workspaceUwYear,
                        workspaceContextCode,
                        projectName,
                        projectDescription);
            }

/*            switch(cloningType) {
                case "KEEP_PROJECT_NAME":
                    Optional<ProjectEntity> projs = this.projectRepository.findByProjectNameAndWorkspaceId(projectName, workspaceTarget.getWorkspaceId());
                    if (projs.isPresent()) {
                        return projs.get();
                    } else {
                        /**
                         *  CREATE PROJECT
                        return this.createNewProject(workspaceUwYear,
                                workspaceContextCode,
                                projectName,
                                projectDescription);
                    }
                case "NEW_PROJECT":
                    return this.createNewProject(workspaceUwYear,
                            workspaceContextCode,
                            projectName,
                            projectDescription);
                case "EXISTING_PROJECT":
                    Optional<ProjectEntity> existingProjects = this.projectRepository.findById(existingProjectId);
                    if (existingProjects.isPresent()) {
                        return existingProjects.get();
                    } else {
                        throw new com.scor.rr.exceptions.RRException(ExceptionCodename.PROJECT_NOT_FOUND, 1);
                    }
                default:
                    return null;
            }
  */      } else {
            throw new com.scor.rr.exceptions.RRException(ExceptionCodename.WORKSPACE_NOT_FOUND, 1);
        }
    }

    public List<PltHeaderEntity> cloneDataPlts(ClonePltsRequest request)
            throws com.scor.rr.exceptions.RRException{

        List<PltHeaderEntity> pltsResults = new ArrayList<PltHeaderEntity>();

        System.out.println("************************** request ");
        System.out.println(request);


        for(Long pltId : request.getPltIds()) {
            // create new plt header entity
            PltHeaderEntity newPLT = this.cloneScorPltHeader(pltId);
            PltHeaderEntity sourcePlt = this.pltHeaderRepository.findByPltHeaderId(pltId);

            ProjectEntity sourceProject = this.projectRepository.findById(sourcePlt.getProjectId()).get();

            // get or create target project (depends on clone type)
            switch(request.getCloningType()) {
                case "KEEP_PROJECT_NAME":
                    newPLT.setProjectId(this.getCloningIntoProject(
                            request.getCloningType(),
                            request.getTargetWorkspaceUwYear(),
                            request.getTargetWorkspaceContextCode(),
                            sourceProject.getProjectName(),
                            sourceProject.getProjectDescription(),
                            request.getExistingProjectId()).getProjectId());
                    break;
                case "NEW_PROJECT":
                    newPLT.setProjectId(this.getCloningIntoProject(
                            request.getCloningType(),
                            request.getTargetWorkspaceUwYear(),
                            request.getTargetWorkspaceContextCode(),
                            request.getNewProjectName(),
                            request.getNewProjectDescription(),
                            request.getExistingProjectId()).getProjectId());
                    break;
                case "EXISTING_PROJECT":
                    newPLT.setProjectId(this.getCloningIntoProject(
                            request.getCloningType(),
                            request.getTargetWorkspaceUwYear(),
                            request.getTargetWorkspaceContextCode(),
                            null,
                            null,
                            request.getExistingProjectId()).getProjectId());
                    break;
            }
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
            // @TODO: FIX THIS !!!!!!!!!!!!
            // copy plt files
          /*  try {
                File dstFile = this.copyPltFile(sourcePlt, newPLT ,
                        "/scor/data/ihub/v4/Facultative/Contracts/" + request.getTargetWorkspaceContextCode()
                                + "/" + request.getTargetWorkspaceUwYear() + "/" +
                        this.projectRepository.findById(newPLT.getProjectId()).get().getProjectName()
                );
                newPLT.setLossDataFilePath(dstFile.getParent());
                newPLT.setLossDataFileName(dstFile.getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
*/
            newPLT.setCloningSourceId(sourcePlt.getPltHeaderId());
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
            newPLT.setIsLocked(false);

            return pltHeaderRepository.save(newPLT);
        } else {
            throw new com.scor.rr.exceptions.RRException(ExceptionCodename.PLT_NOT_FOUND, 1);
        }
    }

    private File copyPltFile(PltHeaderEntity plt, PltHeaderEntity newPLT, String targetPath) throws Exception {

        //System.out.println(">>>>>>>>>>>>>> source: " + plt.getLossDataFilePath());
        //System.out.println(">>>>>>>>>>>>>> new: " + newPLT.getLossDataFilePath());
        String dstFilePath = "/scor/data/ihub/v4/Facultative/Contracts/" + targetPath ;
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
