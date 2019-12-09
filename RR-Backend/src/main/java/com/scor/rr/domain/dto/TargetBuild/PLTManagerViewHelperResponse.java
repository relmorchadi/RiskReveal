package com.scor.rr.domain.dto.TargetBuild;

import com.scor.rr.domain.entities.PLTManagerView;
import com.scor.rr.domain.entities.Tag;
import lombok.Data;

import java.util.Set;

@Data
public class PLTManagerViewHelperResponse {
    Set<PLTManagerView> plts;
    Set<Tag> tags;

    public PLTManagerViewHelperResponse(Set<PLTManagerView> plts, Set<Tag> tags) {
        this.plts = plts;
        this.tags = tags;
    }
}
