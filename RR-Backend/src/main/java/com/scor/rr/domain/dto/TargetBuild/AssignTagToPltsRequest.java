package com.scor.rr.domain.dto.TargetBuild;

import com.scor.rr.domain.TargetBuild.Tag;

import java.util.Set;

public class AssignTagToPltsRequest {
    public Set<Tag> selectedTags;
    public Set<Tag> unselectedTags;
    public Set<Integer> plts;
    public Long wsId;
    public Integer userId;
}
