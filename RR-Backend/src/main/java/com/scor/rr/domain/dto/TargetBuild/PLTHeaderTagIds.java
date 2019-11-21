package com.scor.rr.domain.dto.TargetBuild;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class PLTHeaderTagIds {
    Integer pltHeaderId;
    Integer tagId;

    public PLTHeaderTagIds(Integer tagId, Integer pltHeaderId) {
        this.tagId = tagId;
        this.pltHeaderId = pltHeaderId;
    }
}
