package com.scor.rr.service;


import com.scor.rr.domain.*;
import com.scor.rr.domain.dto.AssignTagPltRequest;
import com.scor.rr.domain.dto.TagManagerRequest;
import com.scor.rr.domain.dto.TagManagerResponse;
import com.scor.rr.domain.dto.UserTagRequest;
import com.scor.rr.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class TagService {

    @Autowired
    UserTagRepository userTagRepository;

    @Autowired
    PltUserTagRepository pltUserTagRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PltHeaderRepository pltHeaderRepository;

    @Autowired
    WorkspaceRepository workspaceRepository;

    public TagManagerResponse getTagsByPltSelection(TagManagerRequest request) {
        User user = userRepository.findById(request.userId).orElse(null);

        List<UserTagPlt> usedInWs= pltUserTagRepository.findByAssignerAndWorkSpaceIdAndUwYear(user, request.wsId, request.uwYear);
        List<UserTagPlt> favorite= pltUserTagRepository.findTop10ByAssignerOrderByAssignedAtDesc(user);
        List<UserTagPlt> recent= pltUserTagRepository.findUserTagPltsByAssigner(user);
        List<UserTag> allAssignment= userTagRepository.findAllUserTags();

        return new TagManagerResponse(usedInWs.stream().map(UserTagPlt::getTag).collect(Collectors.toList()), Stream.concat(favorite.stream(), recent.stream()).distinct().map(UserTagPlt::getTag).collect(Collectors.toList()), allAssignment);
    }

    public UserTag createUserTag(UserTagRequest request) {
        UserTag userTag= new UserTag();
        User user= this.userRepository.findById(request.userId).orElse(null);

        userTag.setUser(user);
        userTag.setTagColor(request.tagColor);
        userTag.setTagName(request.tagName);

        this.userTagRepository.save(userTag);

        request.selectedPlts.forEach( pltId -> {
            PltHeader pltHeader = this.pltHeaderRepository.findById(pltId).orElseThrow(()-> new RuntimeException("Plt Not Found"));
            UserTagPlt assignment = new UserTagPlt();

            assignment.setAssigner(user);
            assignment.setPlt(pltHeader);
            assignment.setTag(userTag);
            assignment.setAssignedAt(new Date());
            assignment.setWorkSpaceId(request.wsId);
            assignment.setUwYear(request.uwYear);

            this.pltUserTagRepository.save(assignment);
        });

        return userTag;
    }


    public List<UserTag> assignTagPlt(AssignTagPltRequest request) {
        Workspace workspace = workspaceRepository.findWorkspaceByWorkspaceId(new Workspace.WorkspaceId(request.wsId, request.uwYear));
        List<PltHeader> pltHeaders = pltHeaderRepository.findPltHeadersByIdIn(request.plts);
        request.selectedTags.forEach(tag -> {
            /*UserTag userTag = userTagRepository.findById(tag.getTagId()).orElseGet( () -> {
                UserTag newTag = new UserTag();
                newTag.setTagColor(tag.getTagColor());
                newTag.setTagName(tag.getTagName());
                newTag.setUser(userRepository.findById(request.userId).orElse(null));
                return userTagRepository.save(newTag);
            });*/

            UserTag userTag;
            if(Optional.ofNullable(tag.getTagId()).isPresent()) {
                userTag= userTagRepository.findById(tag.getTagId()).get();
            } else {
                userTag= new UserTag();
                userTag.setTagColor(tag.getTagColor());
                userTag.setTagName(tag.getTagName());
                userTag.setUser(userRepository.findById(request.userId).orElse(null));
                userTagRepository.save(userTag);
            }


            pltHeaders.forEach( pltHeader -> {
                UserTagPlt assignment = new UserTagPlt(userTag, pltHeader);
                assignment.setAssignedAt(new Date());
                assignment.setAssigner(userRepository.findById(request.userId).orElse(null));
                assignment.setUwYear(request.uwYear);
                assignment.setWorkSpaceId(request.wsId);
                pltUserTagRepository.save(assignment);
            });
        });
        request.unselectedTags.forEach(tag -> {
            UserTag userTag = userTagRepository.findById(tag.getTagId()).orElseThrow(()-> new RuntimeException("userTag ID not found"));
            System.out.println(userTag);
            pltHeaders.forEach(pltHeader -> pltUserTagRepository.delete(pltUserTagRepository.findByTagAndPlt(userTag, pltHeader)));
        });

        /*request.unselectedTags.forEach(tag -> {
            UserTag userTag = userTagRepository.findById(tag.getTagId()).orElseThrow(()-> new RuntimeException("userTag ID not found"));
            userTag.setAssignment(new HashSet<>(tag.getAssignment().stream().filter( assignment -> !request.plts.contains(assignment.getPltHeader().getId())).collect(Collectors.toList())));
            userTagRepository.save(userTag);
        });*/

        return userTagRepository.findByTagIdIn(Stream.concat(request.selectedTags.stream().map(UserTag::getTagId), request.unselectedTags.stream().map(UserTag::getTagId)).collect(Collectors.toList()));
    }


}
