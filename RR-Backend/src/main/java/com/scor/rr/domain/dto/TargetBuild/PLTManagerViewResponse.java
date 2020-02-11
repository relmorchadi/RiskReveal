package com.scor.rr.domain.dto.TargetBuild;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class PLTManagerViewResponse {
    List<Map<String, Object>> plts;
    Integer totalCount;

    public PLTManagerViewResponse(List<Map<String, Object>> plts, Integer totalCount) {
        this.plts = plts;
        this.totalCount = totalCount;
    }
}
