package com.scor.rr.service;

import com.scor.rr.domain.PltHeaderEntity;
import com.scor.rr.domain.TargetBuild.*;
import com.scor.rr.domain.WorkspaceEntity;
import com.scor.rr.domain.dto.TargetBuild.PLTHeaderDeleteRequest;
import com.scor.rr.domain.dto.TargetBuild.PLTManagerViewRequest;
import com.scor.rr.domain.dto.TargetBuild.PLTManagerViewHelperResponse;
import com.scor.rr.domain.dto.TargetBuild.PLTManagerViewResponse;
import com.scor.rr.repository.PltHeaderRepository;
import com.scor.rr.repository.TargetBuild.*;
import com.scor.rr.repository.UserRrRepository;
import com.scor.rr.repository.WorkspaceEntityRepository;
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
    WorkspaceEntityRepository workspaceEntityRepository;
    @Autowired
    PltHeaderRepository pltHeaderRepository;
    @Autowired
    UserRrRepository userRrRepository;

    @Autowired
    PLTManagerViewRepository pltManagerViewRepository;
    @Autowired
    PLTManagerViewRepository pltManagerView2Repository;
    @Autowired
    TagRepository tagRepository;
    @Autowired
    PLTHeaderTagRepository pltHeaderTagRepository;

    PLTManagerViewHelperResponse appendTagsToPLTs(Set<PLTManagerView> plts, WorkspaceEntity ws) {
        HashMap<Long, Tag> pltHeaderTagCount = new HashMap<>();
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
        WorkspaceEntity ws = workspaceEntityRepository.findByWorkspaceContextCodeAndWorkspaceUwYear(request.getWsId(), request.getUwYear()).orElse(null);
        Set<PLTManagerView> plts = pltManagerView2Repository.findPLTs(request.getWsId(), request.getUwYear());
        Set<PLTManagerView> deletedPlts = pltManagerView2Repository.findDeletedPLTs(request.getWsId(), request.getUwYear());

        PLTManagerViewHelperResponse pltManagerViewHelperResponse = appendTagsToPLTs(plts, ws);

        return new PLTManagerViewResponse(pltManagerViewHelperResponse.getPlts(), deletedPlts, pltManagerViewHelperResponse.getTags());
    }

    public Boolean deletePLTheader(PLTHeaderDeleteRequest request) {
        request.getPltHeaderIds().forEach( pltHeaderId -> {
            Optional<PltHeaderEntity> pltHeaderOpt = pltHeaderRepository.findById(pltHeaderId);
            PltHeaderEntity pltHeaderEntity;

            if(pltHeaderOpt.isPresent()) {
                pltHeaderEntity = pltHeaderOpt.get();

                if(pltHeaderEntity.getDeletedBy() == null && pltHeaderEntity.getDeletedOn() == null && pltHeaderEntity.getDeletedDue() == null) {

                    pltHeaderEntity.setDeletedBy(request.getDeletedBy());
                    pltHeaderEntity.setDeletedDue(request.getDeletedDue());
                    pltHeaderEntity.setDeletedOn(request.getDeletedOn());

                    pltHeaderRepository.save(pltHeaderEntity);
                }

            }
        });
        return true;
    }

    public Boolean restorePLTHeader(List<Long> pltHeaderIds) {
        pltHeaderIds.forEach((pltHeaderId) -> {
            Optional<PltHeaderEntity> pltHeaderOpt = pltHeaderRepository.findById(pltHeaderId);
            PltHeaderEntity pltHeaderEntity;

            if(pltHeaderOpt.isPresent()) {
                pltHeaderEntity = pltHeaderOpt.get();

                if(pltHeaderEntity.getDeletedBy() != null && pltHeaderEntity.getDeletedOn() != null && pltHeaderEntity.getDeletedDue() != null) {

                    pltHeaderEntity.setDeletedBy(null);
                    pltHeaderEntity.setDeletedDue(null);
                    pltHeaderEntity.setDeletedOn(null);

                    pltHeaderRepository.save(pltHeaderEntity);
                }

            }
        });
        return true;
    }
}
