package com.scor.rr.domain.dto.TargetBuild;

import com.scor.rr.domain.entities.PLTManager.PLTManagerView;
import com.scor.rr.domain.entities.PLTManager.Tag;
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
