package com.scor.rr.request;

import lombok.Data;

import java.util.List;

@Data
public class InuringSplittingRequest {
    private String pltGroupedPath;
    private String contributionMatriceLossPath;
    private String contributionMatriceMaxExpoPath;
    private List<Integer> pltIds;

    public InuringSplittingRequest() {
    }

    public InuringSplittingRequest(String pltGroupedPath, String contributionMatriceLossPath, String contributionMatriceMaxExpoPath, List<Integer> pltIds) {
        this.pltGroupedPath = pltGroupedPath;
        this.contributionMatriceLossPath = contributionMatriceLossPath;
        this.contributionMatriceMaxExpoPath = contributionMatriceMaxExpoPath;
        this.pltIds = pltIds;
    }
}
