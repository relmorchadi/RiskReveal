package com.scor.rr.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.util.StringUtils;


@Data
@AllArgsConstructor
public class AnalysisFilter {

    private String id;
    private Integer analysisId;
    private String analysisName;
    private String description;
    private String engineVersion;
    private String groupType;
    private String cedant;
    private Integer rdmId;
    private String rdmName;
    private String lobName;
    private String statusDescription;
    private String analysisCurrency;
    private String engineType;
    private String grouping;
    private String lossAmplification;
    private String modeName;
    private String peril;
    private String region;
    private String regionName;
    private String runDate;
    private String subPeril;
    private String typeName;
    private String user1;
    private String user2;
    private String user3;
    private String user4;

    public AnalysisFilter() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = StringUtils.isEmpty(id) ? null: id;
    }

    public Integer getAnalysisId() {
        return analysisId;
    }

    public void setAnalysisId(Integer analysisId) {
        this.analysisId = StringUtils.isEmpty(analysisId) ? null: analysisId;
    }

    public String getAnalysisName() {
        return analysisName;
    }

    public void setAnalysisName(String analysisName) {
        this.analysisName = StringUtils.isEmpty(analysisName) ? null: analysisName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = StringUtils.isEmpty(description) ? null: description;
    }

    public String getEngineVersion() {
        return engineVersion;
    }

    public void setEngineVersion(String engineVersion) {
        this.engineVersion = StringUtils.isEmpty(engineVersion) ? null: engineVersion;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = StringUtils.isEmpty(groupType) ? null: groupType;
    }

    public String getCedant() {
        return cedant;
    }

    public void setCedant(String cedant) {
        this.cedant = StringUtils.isEmpty(cedant) ? null: cedant;
    }

    public Integer getRdmId() {
        return rdmId;
    }

    public void setRdmId(Integer rdmId) {
        this.rdmId = rdmId;
    }

    public String getRdmName() {
        return rdmName;
    }

    public void setRdmName(String rdmName) {
        this.rdmName = rdmName;
    }
}
