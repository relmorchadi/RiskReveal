package com.scor.rr.service;

import com.scor.rr.domain.*;
import com.scor.rr.domain.dto.*;
import com.scor.rr.repository.*;
import com.scor.rr.repository.specification.PltTableSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


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
    @Autowired
    UserRepository userRepository;

    public PltTagResponse searchPltTable(PltFilter pltFilter) {

        PltTagResponse pltTagResponse = new PltTagResponse();
        List<PltManagerView> plts = pltManagerViewRepository.findAll(pltTableSpecification.getFilter(pltFilter)).stream().distinct().collect(Collectors.toList());
        List<UserTag> userTags = userTagRepository.findByWorkspace(
                workspaceRepository.findWorkspaceByWorkspaceId(
                        new Workspace.WorkspaceId(pltFilter.getWorkspaceId(), pltFilter.getUwy())
                ));
        pltTagResponse.setPlts(plts);
        pltTagResponse.setUserTags(userTags);
        return pltTagResponse;
    }

    public UserTag assignUserTag(AssignPltsRequest request) {
        /*UserTag userTag;
        Workspace workspace = workspaceRepository.findWorkspaceByWorkspaceId(new Workspace.WorkspaceId(request.wsId, request.uwYear));
        List<PltHeader> pltHeaders;
        if (Objects.isNull(request.tag.getTagId())) {
            userTag = userTagRepository.findByTagName(request.tag.getTagName()).orElse(new UserTag(request.tag.getTagName(), request.tag.getTagColor()));
        } else
            userTag = userTagRepository.findById(request.tag.getTagId()).orElse(new UserTag(request.tag.getTagName(), request.tag.getTagColor()));

        if (Objects.nonNull(userTag.getPltHeaders())) {
            if (userTag.getPltHeaders().size() != 0) {
                pltHeaders = pltHeaderRepository.findPltHeadersByIdInAndIdNotIn(request.plts, userTag.getPltHeaders().stream().map(PltHeader::getId).collect(Collectors.toList()));
            }
            else pltHeaders = pltHeaderRepository.findPltHeadersByIdIn(request.plts);
        } else
            pltHeaders = pltHeaderRepository.findPltHeadersByIdIn(request.plts);
        userTag.setTagName(request.tag.getTagName());
        if (Objects.isNull(userTag.getPltHeaders())) userTag.setPltHeaders(new HashSet<>());
        //userTag.setPltHeaders(new HashSet<>(pltHeaders));
        userTag.setWorkspace(workspace);
        userTagRepository.save(userTag);*/
        return null;
    }

    public void deleteUserTag(Integer id) {
        //TO-DO
    }

    public UserTag updateUserTag(UserTag userTag) {
        /*return userTagRepository.findById(userTag.getTagId())
                .map(tag -> {
                    tag.setTagName(userTag.getTagName());
                    tag.setTagColor(userTag.getTagColor());
                    return tag;
                }).map(userTagRepository::save)
                .orElseThrow(() -> new RuntimeException("Tag not found"));*/
        return null;
    }

    @Transactional
    public List<UserTag> assignUpdateUserTag(AssignUpdatePltsRequest request) {
        Workspace workspace = workspaceRepository.findWorkspaceByWorkspaceId(new Workspace.WorkspaceId(request.wsId, request.uwYear));
        List<PltHeader> pltHeaders = pltHeaderRepository.findPltHeadersByIdIn(request.plts);
        request.selectedTags.forEach(userTagId -> {
            UserTag userTag = userTagRepository.findById(userTagId).orElseThrow(()-> new RuntimeException("userTag ID not found"));
            //userTag.getPltHeaders().addAll(new HashSet<>(pltHeaders));
            userTag.setWorkspace(workspace);
            userTagRepository.save(userTag);
        });
        request.unselectedTags.forEach(userTagId -> {
            UserTag userTag = userTagRepository.findById(userTagId).orElseThrow(()-> new RuntimeException("userTag ID not found"));
            //userTag.setPltHeaders(new HashSet<>(userTag.getPltHeaders().stream().filter(p->!request.plts.contains(p.getId())).collect(Collectors.toList())));
            userTag.setWorkspace(workspace);
            userTagRepository.save(userTag);
        });
        //return userTagRepository.findByTagIdIn(Stream.concat(request.selectedTags.stream(), request.unselectedTags.stream()).collect(Collectors.toList()));
        return null;
    }

    public UserTag createUserTag(UserTagRequest request) {
        try {
            UserTag userTag= new UserTag();

            User user= userRepository.findById(request.userId).orElseThrow( () -> new RuntimeException("User Not Found"));

            userTag.setUser(user);
            userTag.setTagName(request.tagName);
            userTag.setTagColor(request.tagColor);

            return userTagRepository.save(userTag);
        } catch(Exception exp) {
            throw new RuntimeException("Tag Name Should Be Unique");
        }
    }
}
