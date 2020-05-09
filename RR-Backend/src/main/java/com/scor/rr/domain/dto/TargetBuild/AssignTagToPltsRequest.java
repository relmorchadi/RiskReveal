package com.scor.rr.domain.dto.TargetBuild;

import com.scor.rr.domain.entities.PLTManager.Tag;

import java.util.Set;

public class AssignTagToPltsRequest {
    public Set<Tag> selectedTags;
    public Set<Tag> unselectedTags;
    public Set<Long> plts;
    public Long wsId;
    public Long userId;
}
