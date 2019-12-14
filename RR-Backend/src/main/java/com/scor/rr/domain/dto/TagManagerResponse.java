package com.scor.rr.domain.dto;

import com.scor.rr.domain.entities.UserTag;
import lombok.Data;

import java.util.List;

@Data
public class TagManagerResponse {
    List<UserTag> usedInWs;
    List<UserTag> suggested;
    List<UserTag> allTags;


    public TagManagerResponse(List<UserTag> usedInWs, List<UserTag> suggested, List<UserTag> allTags) {
        this.usedInWs = usedInWs;
        this.suggested = suggested;
        this.allTags= allTags;
    }
}
