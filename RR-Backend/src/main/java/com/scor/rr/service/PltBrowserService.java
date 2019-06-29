package com.scor.rr.service;

import com.scor.rr.domain.PltHeader;
import com.scor.rr.domain.PltManagerView;
import com.scor.rr.domain.UserTag;
import com.scor.rr.domain.Workspace;
import com.scor.rr.domain.dto.AssignPltsRequest;
import com.scor.rr.domain.dto.AssignUpdatePltsRequest;
import com.scor.rr.domain.dto.PltFilter;
import com.scor.rr.domain.dto.PltTagResponse;
import com.scor.rr.repository.PltHeaderRepository;
import com.scor.rr.repository.PltManagerViewRepository;
import com.scor.rr.repository.UserTagRepository;
import com.scor.rr.repository.WorkspaceRepository;
import com.scor.rr.repository.specification.PltTableSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Component
public class PltBrowserService {

    @Autowired
    PltManagerViewRepository pltManagerViewRepository;
    @Autowired
    PltTableSpecification pltTableSpecification;
    @Autowired
    UserTagRepository userTagRepository;
    @Autowired
    WorkspaceRepository workspaceRepository;
    @Autowired
    PltHeaderRepository pltHeaderRepository;

    public PltTagResponse searchPltTable(PltFilter pltFilter) {

        PltTagResponse pltTagResponse = new PltTagResponse();
        List<PltManagerView> plts = pltManagerViewRepository.findAll(pltTableSpecification.getFilter(pltFilter));
        List<UserTag> userTags = userTagRepository.findByWorkspace(
                workspaceRepository.findWorkspaceByWorkspaceId(
                        new Workspace.WorkspaceId(pltFilter.getWorkspaceId(), pltFilter.getUwy())
                ));
        pltTagResponse.setPlts(plts);
        pltTagResponse.setUserTags(userTags);
        return pltTagResponse;
    }

    public UserTag assignUserTag(AssignPltsRequest request) {
        UserTag userTag;
        Workspace workspace = workspaceRepository.findWorkspaceByWorkspaceId(new Workspace.WorkspaceId(request.wsId, request.uwYear));
        List<PltHeader> pltHeaders;
        if (Objects.isNull(request.tag.getTagId())) {
            userTag = userTagRepository.findByTagName(request.tag.getTagName()).orElse(new UserTag(request.tag.getTagName(), request.tag.getTagColor()));
        } else
            userTag = userTagRepository.findById(request.tag.getTagId()).orElse(new UserTag(request.tag.getTagName(), request.tag.getTagColor()));

        if (Objects.nonNull(userTag.getPltHeaders())) {
            if (userTag.getPltHeaders().size() != 0)
                pltHeaders = pltHeaderRepository.findPltHeadersByIdInAndIdNotIn(request.plts,
                        userTag.getPltHeaders().stream().map(PltHeader::getId).collect(Collectors.toList()));
            else pltHeaders = pltHeaderRepository.findPltHeadersByIdIn(request.plts);
        } else
            pltHeaders = pltHeaderRepository.findPltHeadersByIdIn(request.plts);
        userTag.setTagName(request.tag.getTagName());
        if (Objects.isNull(userTag.getPltHeaders())) userTag.setPltHeaders(new HashSet<>());
        userTag.setPltHeaders(new HashSet<>(pltHeaders));
        userTag.setWorkspace(workspace);
        userTagRepository.save(userTag);
        return userTag;
    }

    public void deleteUserTag(Integer id) {
        userTagRepository.delete(userTagRepository.findById(id).orElseThrow(() -> new RuntimeException("ID not Found")));
    }

    public UserTag updateUserTag(UserTag userTag) {
        return userTagRepository.findById(userTag.getTagId())
                .map(tag -> {
                    tag.setTagName(userTag.getTagName());
                    tag.setTagColor(userTag.getTagColor());
                    return tag;
                }).map(userTagRepository::save)
                .orElseThrow(() -> new RuntimeException("Tag not found"));
    }

    @Transactional
    public List<UserTag> assignUpdateUserTag(AssignUpdatePltsRequest request) {
        Workspace workspace = workspaceRepository.findWorkspaceByWorkspaceId(new Workspace.WorkspaceId(request.wsId, request.uwYear));
        List<PltHeader> pltHeaders = pltHeaderRepository.findPltHeadersByIdIn(request.plts);
        request.selectedTags.forEach(userTagId -> {
            UserTag userTag = userTagRepository.findById(userTagId).orElseThrow(()-> new RuntimeException("userTag ID not found"));
            userTag.getPltHeaders().addAll(new HashSet<>(pltHeaders));
            userTag.setWorkspace(workspace);
            userTagRepository.save(userTag);
        });
        request.unselectedTags.forEach(userTagId -> {
            UserTag userTag = userTagRepository.findById(userTagId).orElseThrow(()-> new RuntimeException("userTag ID not found"));
            userTag.setPltHeaders(new HashSet<>(userTag.getPltHeaders().stream().filter(p->!request.plts.contains(p.getId())).collect(Collectors.toList())));
            userTag.setWorkspace(workspace);
            userTagRepository.save(userTag);
        });
        return userTagRepository.findByTagIdIn(Stream.concat(request.selectedTags.stream(), request.unselectedTags.stream()).collect(Collectors.toList()));
    }
}
