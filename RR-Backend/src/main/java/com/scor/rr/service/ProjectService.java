package com.scor.rr.service;

import com.scor.rr.domain.TargetBuild.Project.*;
import com.scor.rr.domain.TargetBuild.Workspace;
import com.scor.rr.domain.dto.TargetBuild.ProjectEditRequest;
import com.scor.rr.domain.dto.TargetBuild.ProjectStatistics;
import com.scor.rr.repository.ContractSearchResultRepository;
import com.scor.rr.repository.TargetBuild.Project.*;
import com.scor.rr.repository.TargetBuild.WorkspaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;


@Service
@Transactional
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ContractSearchResultRepository contractSearchResultRepository;

    @Autowired
    private WorkspaceRepository workspaceRepository;

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


    public Project addNewProject(String wsId, Integer uwy, Project p) {
        return workspaceRepository.findByWorkspaceContextCodeAndWorkspaceUwYear(wsId, uwy)
                .map(ws -> projectRepository.save(this.prePersistProject(p, ws.getWorkspaceId())))
                .orElseGet(() ->
                        contractSearchResultRepository.findByWorkspaceIdAndUwYear(wsId, uwy)
                                .map(targetContract -> workspaceRepository.save(new Workspace(targetContract)))
                                .map(newWs -> projectRepository.save(this.prePersistProject(p, newWs.getWorkspaceId())))
                                .orElseThrow(() -> new RuntimeException("No available Workspace with ID : " + wsId + "-" + uwy))
                );
    }

    private Project prePersistProject(Project p, Integer wsId) {
        p.initProject(wsId);
        return p;
    }

    public ResponseEntity updateProject(ProjectEditRequest request) {

        if(request.getProjectId() != null) {
            Optional<Project> prjOpt = projectRepository.findById(request.getProjectId());
            if(prjOpt.isPresent()) {
                Project prj = prjOpt.get();

                prj.setAssignedTo(request.getAssignedTo());
                prj.setProjectName(request.getProjectName());
                prj.setProjectDescription(request.getProjectDescription());
                projectRepository.save(prj);

                return new ResponseEntity<>(projectCardViewRepository.findByProjectId(request.getProjectId()), HttpStatus.OK);
            }
        } else{
            return new ResponseEntity<>("No available Project with ID : " + request.getProjectId(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("No available Project with ID : " + request.getProjectId(), HttpStatus.BAD_REQUEST);
    }

    public void deleteProject(Long projectId) {
        if (projectRepository.existsById(projectId))
            projectRepository.deleteById(projectId);
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
