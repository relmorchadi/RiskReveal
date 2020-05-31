package com.scor.rr.service;

import com.scor.rr.configuration.security.UserPrincipal;
import com.scor.rr.domain.MarketChannelEntity;
import com.scor.rr.domain.ProjectEntity;
import com.scor.rr.domain.UserRrEntity;
import com.scor.rr.domain.WorkspaceEntity;
import com.scor.rr.domain.dto.TargetBuild.ProjectEditRequest;
import com.scor.rr.domain.dto.TargetBuild.ProjectStatistics;
import com.scor.rr.domain.entities.Project.*;
import com.scor.rr.repository.*;
import com.scor.rr.repository.Project.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class ProjectService {

    @Autowired
    private ProjectEntityRepository projectEntityRepository;

    @Autowired
    private ContractSearchResultRepository contractSearchResultRepository;

    @Autowired
    private WorkspaceEntityRepository workspaceEntityRepository;

    @Autowired
    private NumberOfPLTPublishedToAccumulationRepository numberOfPLTPublishedToAccumulationRepository;

    @Autowired
    private NumberOfPLTsPricedInForeWriterRepository numberOfPLTsPricedInForeWriterRepository;

    @Autowired
    private NumberOfPLTsPricedInxActRepository numberOfPLTsPricedInxActRepository;

    @Autowired
    private NumberOfPLTsPublishedInxActRepository numberOfPLTsPublishedInxActRepository;

    @Autowired
    private NumberOfPLTThreadEndOrPostInuredRepository numberOfPLTThreadEndOrPostInuredRepository;

    @Autowired
    private NumberOfRegionPerilsRepository numberOfRegionPerilsRepository;

    @Autowired
    private ImportedProjectRepository importedProjectRepository;

    @Autowired
    private AccumulatedProjectRepository accumulatedProjectRepository;

    @Autowired
    private ProjectConfigurationForeWriterRepository projectConfigurationForeWriterRepository;

    @Autowired
    private ProjectCardViewRepository projectCardViewRepository;

    @Autowired
    private MarketChannelRepository marketChannelRepository;

    public List<ProjectCardView> getAllProjectByWorkspace(String workspaceContextCode, Integer workspaceUwYear) {
        return this.workspaceEntityRepository.findByWorkspaceContextCodeAndWorkspaceUwYear(workspaceContextCode,workspaceUwYear)
                .map(ws -> this.projectCardViewRepository.findAllByWorkspaceId(ws.getWorkspaceId()))
                .orElse(null);
    }

    //FIXME: need to group into one function later
    public ProjectEntity addNewProjectFac(String workspaceContextCode, Integer uwy, String workspaceName, String clientName,String clientId, String lob, String contractId, ProjectEntity p) {
        return workspaceEntityRepository.findByWorkspaceContextCodeAndWorkspaceUwYear(workspaceContextCode, uwy)
                .map(ws -> projectEntityRepository.save(this.prePersistProject(p, ws.getWorkspaceId(), true)))
                .orElseGet(() -> {
                            WorkspaceEntity newWs = workspaceEntityRepository.save(
                                    new WorkspaceEntity(
                                            workspaceContextCode,
                                            uwy,
                                            "FAC",
                                            workspaceName, //FIXME: workspace name and client name - check with Shaun
                                            clientName,
                                            clientId,
                                            lob,
                                            contractId
                                    ));
                            return projectEntityRepository.save(this.prePersistProject(p, newWs.getWorkspaceId(), true));
                        }
                );
    }

    public ProjectCardView addNewProject(String wsId, Integer uwy, ProjectEntity p) {
        return workspaceEntityRepository.findByWorkspaceContextCodeAndWorkspaceUwYear(wsId, uwy)
                .map(ws -> {
                    ProjectEntity project = projectEntityRepository.saveAndFlush(this.prePersistProject(p, ws.getWorkspaceId(), false));
                    return projectCardViewRepository.findByProjectId(project.getProjectId());
                })
                .orElseGet(() ->
                        contractSearchResultRepository.findTop1ByWorkSpaceIdAndUwYearOrderByWorkSpaceIdAscUwYearAsc(wsId, uwy)
                                .map(targetContract -> workspaceEntityRepository.save(
                                        new WorkspaceEntity(
                                                targetContract.getWorkSpaceId(),
                                                targetContract.getUwYear(),
                                                "TTY",
                                                targetContract.getWorkspaceName(),
                                                targetContract.getCedantName(),
                                                targetContract.getCedantCode(),
                                                targetContract.getLoBid(),
                                                targetContract.getId()
                                        )
                                ))
                                .map(newWs -> {
                                    ProjectEntity project = projectEntityRepository.save(this.prePersistProject(p, newWs.getWorkspaceId(), false));
                                    return projectCardViewRepository.findByProjectId(project.getProjectId());
                                })
                                .orElseThrow(() -> new RuntimeException("No available Workspace with ID : " + wsId + "-" + uwy))
                );
    }

    private ProjectEntity prePersistProject(ProjectEntity p, Long wsId, Boolean isFac) {
        UserRrEntity user = isFac ? null : ( (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        p.initProject(wsId);
        if(!isFac) p.setCreatedBy(user.getLastName() + ' ' + user.getFirstName());
        p.setAssignedTo(!isFac ? user.getLastName() + ' ' + user.getFirstName(): null);
        return p;
    }

    public ResponseEntity updateProject(ProjectEditRequest request) {

        if(request.getProjectId() != null) {
            Optional<ProjectEntity> prjOpt = projectEntityRepository.findById(request.getProjectId());
            if(prjOpt.isPresent()) {
                UserRrEntity user = ( (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
                ProjectEntity prj = prjOpt.get();

                prj.setAssignedTo(request.getAssignedTo());
                prj.setProjectName(request.getProjectName());
                prj.setProjectDescription(request.getProjectDescription());
                prj.setDueDate(request.getDueDate());
                prj.setLastUpdatedBy(user.getUserCode());
                prj.setLastUpdatedOn(new Date());
                projectEntityRepository.saveAndFlush(prj);

                return new ResponseEntity<>(projectCardViewRepository.findByProjectId(request.getProjectId()), HttpStatus.OK);
            }
        } else{
            return new ResponseEntity<>("No available Project with ID : " + request.getProjectId(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("No available Project with ID : " + request.getProjectId(), HttpStatus.BAD_REQUEST);
    }

    public void deleteProject(Long projectId) {
        if (projectEntityRepository.existsById(projectId))
            projectEntityRepository.deleteById(projectId);
        else
            throw new RuntimeException("No available Project with ID : " + projectId);
    }

    public ProjectStatistics getProjetStatistics(Long projectId) {
        Integer regionPerils = numberOfRegionPerilsRepository.findById(projectId).orElse(new NumberOfRegionPerils(projectId, 0)).getCount();
        Integer accumulatedPlts = numberOfPLTPublishedToAccumulationRepository.findById(projectId).orElse(new NumberOfPLTPublishedToAccumulation(projectId, 0)).getCount();
        Integer finalPricing;
        Integer publishedForPricingCount;
        Integer plts = numberOfPLTThreadEndOrPostInuredRepository.findById(projectId).orElse(new NumberOfPLTThreadEndOrPostInured(projectId, 0)).getCount();

        if(projectConfigurationForeWriterRepository.existsByProjectId(projectId)) {
            publishedForPricingCount = numberOfPLTsPublishedInxActRepository.findById(projectId).orElse(new NumberOfPLTsPublishedInxAct(projectId, 0)).getCount();
            finalPricing = numberOfPLTsPricedInForeWriterRepository.findById(projectId).orElse(new NumberOfPLTsPricedInForeWriter(projectId, 0)).getCount();
        } else {
            publishedForPricingCount = numberOfPLTsPublishedInxActRepository.findById(projectId).orElse(new NumberOfPLTsPublishedInxAct(projectId, 0)).getCount();
            finalPricing = numberOfPLTsPricedInxActRepository.findById(projectId).orElse(new NumberOfPLTsPricedInxAct(projectId, 0)).getCount();
        }

        Boolean importedFlag = importedProjectRepository.findById(projectId).orElse(new ImportedProject(projectId, 0)).getCount() > 0;
        Boolean accumulated = accumulatedProjectRepository.findById(projectId).orElse(new AccumulatedProject(projectId, 0)).getCount() > 0;
        Boolean publishedForPricingFlag;

        publishedForPricingFlag = publishedForPricingCount > 0;


        return ProjectStatistics
                .builder()
                .regionPerils(regionPerils)
                .accumulatedPlts(accumulatedPlts)
                .finalPricing(finalPricing)
                .plts(plts)
                .publishedForPricingCount(publishedForPricingCount)
                .importedFlag(importedFlag)
                .accumulatedFlag(accumulated)
                .publishedForPricingFlag(publishedForPricingFlag)
                .build();
    }

//    public ProjectCardView appendProjectStats(ProjectCardView project) {
//        ProjectStatistics statistics = this.getProjetStatistics(project.getProjectId());
//
//        project.setRegionPerils(statistics.getRegionPerils());
//        project.setAccumulatedPlts(statistics.getAccumulatedPlts());
//        project.setFinalPricing(statistics.getFinalPricing());
//        project.setPublishedForPricingCount(statistics.getPublishedForPricingCount());
//        project.setPlts(statistics.getPlts());
//
//        project.setImportedFlag(statistics.getImportedFlag());
//        project.setAccumulatedFlag(statistics.getAccumulatedFlag());
//        project.setPublishedForPricingFlag(statistics.getPublishedForPricingFlag());
//
//        return project;
//    }

}
