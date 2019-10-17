package com.scor.rr.importBatch.processing.adjustment;

import java.util.List;

/**
 * Created by u004119 on 17/05/2016.
 */
public class DefaultAdjustmentRequest {

    private String projectId;

    private List<String> structureIds;

    private String scorPLTHeaderId;

    private Long analysisId;

    private String analysisName;

    private String rdmName;

    private Long rdmId;

    private Integer targetRapId;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public DefaultAdjustmentRequest(String scorPLTHeaderId) {
        this.scorPLTHeaderId = scorPLTHeaderId;
    }

    public List<String> getStructureIds() {
        return structureIds;
    }

    public void setStructureIds(List<String> structureIds) {
        this.structureIds = structureIds;
    }

    public String getScorPLTHeaderId() {
        return scorPLTHeaderId;
    }

    public void setScorPLTHeaderId(String scorPLTHeaderId) {
        this.scorPLTHeaderId = scorPLTHeaderId;
    }

    public Long getAnalysisId() {
        return analysisId;
    }

    public void setAnalysisId(Long analysisId) {
        this.analysisId = analysisId;
    }

    public String getAnalysisName() {
        return analysisName;
    }

    public void setAnalysisName(String analysisName) {
        this.analysisName = analysisName;
    }

    public String getRdmName() {
        return rdmName;
    }

    public void setRdmName(String rdmName) {
        this.rdmName = rdmName;
    }

    public Long getRdmId() {
        return rdmId;
    }

    public void setRdmId(Long rdmId) {
        this.rdmId = rdmId;
    }

    public Integer getTargetRapId() {
        return targetRapId;
    }

    public void setTargetRapId(Integer targetRapId) {
        this.targetRapId = targetRapId;
    }
}
