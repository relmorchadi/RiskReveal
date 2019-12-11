package com.scor.rr.domain.dto.main;

import lombok.Data;

@Data
public class ExpectedRegionPerils {
    String workspaceContextCode;
    Integer uwYear;
    Integer expectedRegionPerilsCount;
}
