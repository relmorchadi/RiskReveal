package com.scor.rr.domain.dto;

import com.scor.rr.domain.entities.UserTag;

import java.util.List;

public class TagManagerRequest {
    public List<UserTag> selectedTags;
    public String wsId;
    public Integer uwYear;
    public Integer userId;
}
