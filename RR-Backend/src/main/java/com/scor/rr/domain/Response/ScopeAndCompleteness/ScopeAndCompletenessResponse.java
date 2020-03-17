package com.scor.rr.domain.Response.ScopeAndCompleteness;

import lombok.Data;

import java.util.List;

@Data
public class ScopeAndCompletenessResponse {

    private String id;
    private String name;
    private String divisionInfo;
    private String property;
    private boolean attached;
    private List<RegionPerils> regionPerils;
    private List<TargetRaps> targetRaps;
}
