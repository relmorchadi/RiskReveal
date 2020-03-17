package com.scor.rr.domain.Response.ScopeAndCompleteness;

import lombok.Data;

import java.util.List;

@Data
public class RegionPerils {

    private String id;
    private String description;
    private boolean attached;
    private boolean overridden;
    private List<InsideObject> targetRaps;
}
