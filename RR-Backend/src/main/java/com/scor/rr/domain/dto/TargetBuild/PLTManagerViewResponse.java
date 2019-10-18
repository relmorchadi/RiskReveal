package com.scor.rr.domain.dto.TargetBuild;

import com.scor.rr.domain.TargetBuild.PLTManagerView;
import com.scor.rr.domain.TargetBuild.Tag;
import lombok.Data;

import java.util.Set;

@Data
public class PLTManagerViewResponse {
    Set<PLTManagerView> plts;
    Set<PLTManagerView> deletedPlts;
    Set<Tag> tags;

    public PLTManagerViewResponse(Set<PLTManagerView> plts, Set<PLTManagerView> deletedPlts, Set<Tag> tags) {
        this.plts = plts;
        this.deletedPlts = deletedPlts;
        this.tags = tags;
    }
}
