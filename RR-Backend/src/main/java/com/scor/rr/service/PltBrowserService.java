package com.scor.rr.service;

import com.scor.rr.domain.TargetBuild.*;
import com.scor.rr.domain.dto.TargetBuild.PLTHeaderDeleteRequest;
import com.scor.rr.domain.dto.TargetBuild.PLTManagerViewRequest;
import com.scor.rr.domain.dto.TargetBuild.PLTManagerViewHelperResponse;
import com.scor.rr.domain.dto.TargetBuild.PLTManagerViewResponse;
import com.scor.rr.repository.TargetBuild.*;
import com.scor.rr.repository.specification.PltTableSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;


@Component
public class PltBrowserService {

    /*@Autowired
    PltManagerViewRepository pltManagerViewRepository;*/
    @Autowired
    PltTableSpecification pltTableSpecification;
    @Autowired
    UserTagRepository userTagRepository;
    @Autowired
    WorkspaceRepository workspaceRepository;
    @Autowired
    PLTHeaderRepository pltHeaderRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    PLTManagerViewRepository pltManagerViewRepository;
    @Autowired
    WorkspaceRepository nworkspaceRepository;
    @Autowired
    PLTManagerViewRepository pltManagerView2Repository;
    @Autowired
    TagRepository tagRepository;
    @Autowired
    PLTHeaderTagRepository pltHeaderTagRepository;

    PLTManagerViewHelperResponse appendTagsToPLTs(Set<PLTManagerView> plts, Workspace ws) {
        HashMap<Integer, Tag> pltHeaderTagCount = new HashMap<>();
        plts.forEach( pltView -> {
                    pltView.setTags(
                            pltHeaderTagRepository.findByPltHeaderId(pltView.getPltId())
                                    .stream()
                                    .map( pltHeaderTag -> {
                                        Optional<Tag> tmpTagOpt = tagRepository.findById(pltHeaderTag.getTagId());
                                        Tag tmpTag = new Tag();
                                        if(tmpTagOpt.isPresent()) {
                                            tmpTag = tmpTagOpt.get();

                                            if(pltHeaderTagCount.containsKey(tmpTag.getTagId())) {
                                                tmpTag.setCount(pltHeaderTagCount.get(tmpTag.getTagId()).getCount());

                                            } else {
                                                tmpTag.setCount(pltHeaderTagRepository.findByWorkspaceId(ws.getWorkspaceId()).size());

                                            }
                                        }
                                        pltHeaderTagCount.put(tmpTag.getTagId(), tmpTag);
                                        return tmpTag;
                                    }).collect(Collectors.toSet())
                    );
                });
        return new PLTManagerViewHelperResponse(plts, new HashSet<>(pltHeaderTagCount.values()));
    }

    public PLTManagerViewResponse getPLTHeaderView(PLTManagerViewRequest request) {
        Workspace ws = nworkspaceRepository.findByWorkspaceContextCodeAndWorkspaceUwYear(request.getWsId(), request.getUwYear()).orElse(null);
        Set<PLTManagerView> plts = pltManagerView2Repository.findPLTs(request.getWsId(), request.getUwYear());
        Set<PLTManagerView> deletedPlts = pltManagerView2Repository.findDeletedPLTs(request.getWsId(), request.getUwYear());

        PLTManagerViewHelperResponse pltManagerViewHelperResponse = appendTagsToPLTs(plts, ws);

        return new PLTManagerViewResponse(pltManagerViewHelperResponse.getPlts(), deletedPlts, pltManagerViewHelperResponse.getTags());
    }

    public Boolean deletePLTheader(PLTHeaderDeleteRequest request) {
        request.getPltHeaderIds().forEach( pltHeaderId -> {
            Optional<PLTHeader> pltHeaderOpt = pltHeaderRepository.findById(pltHeaderId);
            PLTHeader pltHeader;

            if(pltHeaderOpt.isPresent()) {
                pltHeader = pltHeaderOpt.get();

                if(pltHeader.getDeletedBy() == null && pltHeader.getDeletedOn() == null && pltHeader.getDeletedDue() == null) {

                    pltHeader.setDeletedBy(request.getDeletedBy());
                    pltHeader.setDeletedDue(request.getDeletedDue());
                    pltHeader.setDeletedOn(request.getDeletedOn());

                    pltHeaderRepository.save(pltHeader);
                }

            }
        });
        return true;
    }

    public Boolean restorePLTHeader(List<Integer> pltHeaderIds) {
        pltHeaderIds.forEach((pltHeaderId) -> {
            Optional<PLTHeader> pltHeaderOpt = pltHeaderRepository.findById(pltHeaderId);
            PLTHeader pltHeader;

            if(pltHeaderOpt.isPresent()) {
                pltHeader = pltHeaderOpt.get();

                if(pltHeader.getDeletedBy() != null && pltHeader.getDeletedOn() != null && pltHeader.getDeletedDue() != null) {

                    pltHeader.setDeletedBy(null);
                    pltHeader.setDeletedDue(null);
                    pltHeader.setDeletedOn(null);

                    pltHeaderRepository.save(pltHeader);
                }

            }
        });
        return true;
    }
}
