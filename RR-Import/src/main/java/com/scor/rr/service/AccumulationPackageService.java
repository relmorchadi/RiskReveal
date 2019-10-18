package com.scor.rr.service;

import com.scor.rr.domain.entities.plt.ScorPLTHeader;
import com.scor.rr.domain.entities.references.RegionPeril;
import com.scor.rr.domain.entities.workspace.Project;
import com.scor.rr.domain.entities.workspace.Workspace;
import com.scor.rr.domain.entities.workspace.WorkspaceMinimumGrain;
import com.scor.rr.domain.enums.PLTType;
import com.scor.rr.repository.ihub.RRLossTableRepository;
import com.scor.rr.repository.plt.RegionPerilRepository;
import com.scor.rr.repository.plt.ScorPLTHeaderRepository;
import com.scor.rr.repository.workspace.ProjectRepository;
import com.scor.rr.repository.workspace.WorkspaceMinimumGrainRepository;
import com.scor.rr.repository.workspace.WorkspaceRepository;
import org.apache.commons.collections.keyvalue.MultiKey;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by u004602 on 18/07/2017.
 */
public class AccumulationPackageService {

    @Autowired
    private WorkspaceRepository workspaceRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ScorPLTHeaderRepository scorPLTHeaderRepository;
    @Autowired
    private RegionPerilRepository regionPerilRepositoryz;
    @Autowired
    private WorkspaceMinimumGrainRepository workspaceMinimumGrainRepository;
    @Autowired
    private RRLossTableRepository rrLossTableRepository;

    private static final Logger log= LoggerFactory.getLogger(AccumulationPackageService.class);

    public void updateMinimumGrainImportedPlt() {
//        List<Workspace> workspaces = workspaceRepository.findAll();
//        if (workspaces != null && !workspaces.isEmpty()) {
//            for (Workspace workspace : workspaces) {
//                if (workspace.getProjects() != null && !workspace.getProjects().isEmpty()) {
//                    for (Project project : workspace.getProjects()) {
//                        List<ScorPLTHeader> scorPLTHeaders = scorPLTHeaderRepository.findByProjectAndPltType(project, "Thread");
//                        if (scorPLTHeaders != null && !scorPLTHeaders.isEmpty()) {
//                            for (ScorPLTHeader scorPLTHeader : scorPLTHeaders) {
//                                if (scorPLTHeader.getRegionPeril() != null && scorPLTHeader.getRegionPeril().getRegionPerilCode() != null) {
//                                    RegionPeril targetRP = scorPLTHeader.getRegionPeril();
//                                    MinimumGrainOfImportedEltPlts item = new MinimumGrainOfImportedEltPlts();
//                                    item.setWorkspaceId(workspace.getId());
//                                    item.setProjectId(project.getId());
//                                    item.setScorPLTHeader(scorPLTHeader);
//                                    item.setPeril(scorPLTHeader.getPerilCode());
//                                    item.setMinimumRegionPeril(targetRP.getMinimumGrainRegionPeril() ? targetRP.getRegionPerilCode() : targetRP.getParentMinimumGrainRegionPeril());
//                                    item.setRootRegionPeril(targetRP.getHierarchyLevel() == 0 ? targetRP.getRegionPerilCode() : targetRP.getHierarchyParentCode());
//                                    item.setId(workspace.getId() + "-" + project.getId() + "-" + scorPLTHeader.getId());
//                                    minimumGrainOfImportedEltPltsRepository.save(item);
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
    }

    public void updateWorkspaceMinimalGrain() {
        List<Workspace> workspaces = workspaceRepository.findAll();
        Map<MultiKey, WorkspaceMinimumGrain> workspaceMinimalGrains = new HashMap<>();
        if (workspaces != null && !workspaces.isEmpty()) {
            for (Workspace workspace : workspaces) {
                if (workspace.getProjects() != null && !workspace.getProjects().isEmpty()) {
                    for (Project project : workspace.getProjects()) {
                        List<ScorPLTHeader> scorPLTHeaders = scorPLTHeaderRepository.findByProjectProjectIdAndPltType(project.getProjectId(), PLTType.Thread);
                        if (scorPLTHeaders != null && !scorPLTHeaders.isEmpty()) {
                            for (ScorPLTHeader scorPLTHeader : scorPLTHeaders) {
                                if (scorPLTHeader.getRegionPeril() != null && scorPLTHeader.getRegionPeril().getRegionPerilCode() != null) {
                                    RegionPeril targetRP = scorPLTHeader.getRegionPeril();
                                    if (targetRP != null && !StringUtils.isEmpty(targetRP.getParentMinimumGrainRegionPeril())) {
                                        MultiKey key = new MultiKey(workspace.getWorkspaceId(), targetRP.getParentMinimumGrainRegionPeril());
                                        if (workspaceMinimalGrains.get(key) == null) {
                                            WorkspaceMinimumGrain workspaceMinimalGrain = new WorkspaceMinimumGrain();
                                            workspaceMinimalGrain.setWorkspaceId(workspace.getWorkspaceId());
                                            workspaceMinimalGrain.setMinimumGrain(targetRP.getParentMinimumGrainRegionPeril());
                                            workspaceMinimalGrain.setId(workspace.getWorkspaceId() + "_" + targetRP.getParentMinimumGrainRegionPeril());
                                            workspaceMinimalGrains.put(key, workspaceMinimalGrain);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        if (!workspaceMinimalGrains.values().isEmpty()) {
            workspaceMinimumGrainRepository.saveAll(workspaceMinimalGrains.values());
        }
    }
		
}
