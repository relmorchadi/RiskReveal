package com.scor.rr.service;


import com.scor.rr.domain.*;
import com.scor.rr.domain.dto.TargetBuild.AssignTagToPltsRequest;
import com.scor.rr.domain.dto.TargetBuild.SaveOrUpdateTagRequest;
import com.scor.rr.domain.entities.PLTHeaderTag;
import com.scor.rr.domain.entities.Tag;
import com.scor.rr.domain.entities.UserTag;
import com.scor.rr.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class TagService {

    @Autowired
    TagRepository tagRepository;

    @Autowired
    UserTagRepository userTagRepository;

    @Autowired
    PLTHeaderTagRepository pltHeaderTagRepository;

    @Autowired
    UserRrRepository userRrRepository;

    @Autowired
    WorkspaceEntityRepository workspaceEntityRepository;

    @Autowired
    PltHeaderRepository pltHeaderRepository;

    public Boolean assignTagToPlts(AssignTagToPltsRequest request) {
        WorkspaceEntity workspaceEntity = workspaceEntityRepository.findById(request.wsId).orElse(null);
        UserRrEntity user = userRrRepository.findById(request.userId).orElse(null);

        //TODO: replace orElse By Exceptions

        Set<PltHeaderEntity> pltHeaderEntities = new HashSet<>(pltHeaderRepository.findAllById(request.plts));

        request.selectedTags.forEach(tag -> {

            Tag newTag;
            UserTag userTag;

            //Check if Tag exist else create it
            if(tag.getTagId() != null && !tagRepository.existsById(tag.getTagId())) {

                newTag = tagRepository.findById(tag.getTagId()).get();

            } else {

                newTag = new Tag();

                newTag.setEntity(1);
                newTag.setTagName(tag.getTagName());
                newTag.setDefaultColor(tag.getDefaultColor());
                newTag.setCreatedBy(tag.getCreatedBy());
                tagRepository.save(newTag);

            }

            //Update UserTag preference if changed
            Optional<UserTag> userTagOpt = userTagRepository.findByTagIdAndUser(newTag.getTagId(), user.getUserId());

            if(userTagOpt.isPresent()) {
                userTag = userTagOpt.get();

                if(!userTag.getUserOverrideColour().equalsIgnoreCase(tag.getDefaultColor())) {

                    userTag.setUserOverrideColour(tag.getDefaultColor());
                    userTagRepository.save(userTag);
                }

            } else {

                userTag = new UserTag();

                userTag.setUserOverrideColour(tag.getDefaultColor());
                userTag.setUser(user.getUserId());
                userTag.setTagId(newTag.getTagId());

            }

            //Set Assignment
            pltHeaderEntities.forEach(pltHeader -> {

                PLTHeaderTag pltHeaderTag = new PLTHeaderTag(pltHeader.getPltHeaderId(), newTag.getTagId());
                pltHeaderTag.setWorkspaceId(workspaceEntity.getWorkspaceId());
                pltHeaderTag.setCreatedBy(user.getUserId());
                pltHeaderTagRepository.save(pltHeaderTag);

            });

        });
        request.unselectedTags.forEach(tag -> {

            if(tagRepository.existsById(tag.getTagId())) {
                Optional<Tag> tmpTagOpt = tagRepository.findById(tag.getTagId());

                if(tmpTagOpt.isPresent()) {
                    Tag tmpTag = tmpTagOpt.get();

                    pltHeaderEntities.forEach(pltHeader -> pltHeaderTagRepository.findByPltHeaderIdAndTagId(tmpTag.getTagId(), pltHeader.getPltHeaderId()).ifPresent(pltHeaderTagRepository::delete));
                }
            }

        });
        return true;
    }

    public Boolean saveOrUpdateTag(SaveOrUpdateTagRequest request) {

        //Check if tag already exists
        //if exist then assign plts to it either globaly or partially from a workspace
        //if it doesn't exist then create new one

        return false;
    }


}
