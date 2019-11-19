package com.scor.rr.service;

import com.scor.rr.domain.TargetBuild.Project.NumberOfEntityForProject;
import com.scor.rr.domain.TargetBuild.Project.Project;
import com.scor.rr.domain.TargetBuild.Workspace;
import com.scor.rr.domain.dto.TargetBuild.ProjectStatistics;
import com.scor.rr.repository.ContractSearchResultRepository;
import com.scor.rr.repository.TargetBuild.Project.*;
import com.scor.rr.repository.TargetBuild.WorkspaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


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
    private NumberOfPLTsPublishedForPricingRepository numberOfPLTsPublishedForPricingRepository;

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
        p.setProjectId(null);
        p.setWorkspaceId(wsId);
        return p;
    }

    public Project updateProject(Long projectId, Project project) {
        if (!projectRepository.existsById(projectId))
            throw new RuntimeException("No available Project with ID : " + projectId);
        project.setProjectId(projectId);
        return projectRepository.save(project);
    }

    public void deleteProject(Long projectId) {
        if (projectRepository.existsById(projectId))
            projectRepository.deleteById(projectId);
        else
            throw new RuntimeException("No available Project with ID : " + projectId);
    }

    public ProjectStatistics getProjetStatistics(Long projectId) {
        Integer regionPerils = numberOfRegionPerilsRepository.findById(projectId).orElseThrow(() -> new RuntimeException("Couldnt Find project " + projectId)).getCount();
        Integer accumulatedPlts = numberOfPLTPublishedToAccumulationRepository.findById(projectId).orElseThrow(() -> new RuntimeException("Couldnt Find project " + projectId)).getCount();
        Integer finalPricing;
        Integer publishedForPricingCount;
        Integer plts = numberOfPLTThreadEndOrPostInuredRepository.findById(projectId).orElseThrow(() -> new RuntimeException("Couldnt Find project " + projectId)).getCount();

        if(projectConfigurationForeWriterRepository.existsByProjectId(projectId)) {
            publishedForPricingCount = numberOfPLTsPublishedForPricingRepository.findById(projectId).orElseThrow(() -> new RuntimeException("Couldnt Find project " + projectId)).getCount();
            finalPricing = numberOfPLTsPricedInForeWriterRepository.findById(projectId).orElseThrow(() -> new RuntimeException("Couldnt Find project " + projectId)).getCount();
        } else {
            publishedForPricingCount = numberOfPLTsPublishedForPricingRepository.findById(projectId).orElseThrow(() -> new RuntimeException("Couldnt Find project " + projectId)).getCount();
            finalPricing = numberOfPLTsPricedInxActRepository.findById(projectId).orElseThrow(() -> new RuntimeException("Couldnt Find project " + projectId)).getCount();
        }

        Boolean importedFlag = importedProjectRepository.findById(projectId).orElseThrow(() -> new RuntimeException("Couldnt Find project " + projectId)).getCount() > 0;
        Boolean accumulated = accumulatedProjectRepository.findById(projectId).orElseThrow(() -> new RuntimeException("Couldnt Find project " + projectId)).getCount() > 0;
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

}
