package com.scor.rr.domain.dto;

import com.scor.rr.domain.TargetBuild.UserTag;

import java.util.List;

public class AssignTagPltRequest {
    public List<String> plts;
    public List<UserTag> selectedTags;
    public List<UserTag> unselectedTags;
    public String wsId;
    public Integer uwYear;
    public Integer userId;
}
