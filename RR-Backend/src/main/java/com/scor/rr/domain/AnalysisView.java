package com.scor.rr.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class AnalysisView {

    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "analysisId")
    private Integer analysisId;
    @Column(name = "analysisName")
    private String analysisName;
    @Column(name = "description")
    private String description;
    @Column(name = "engineVersion")
    private String engineVersion;
    @Column(name = "groupType")
    private String groupType;
    @Column(name = "cedant")
    private String cedant;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public Integer getAnalysisId() {
        return analysisId;
    }

    public void setAnalysisId(Integer analysisId) {
        this.analysisId = analysisId;
    }


    public String getAnalysisName() {
        return analysisName;
    }

    public void setAnalysisName(String analysisName) {
        this.analysisName = analysisName;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getEngineVersion() {
        return engineVersion;
    }

    public void setEngineVersion(String engineVersion) {
        this.engineVersion = engineVersion;
    }


    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }


    public String getCedant() {
        return cedant;
    }

    public void setCedant(String cedant) {
        this.cedant = cedant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnalysisView that = (AnalysisView) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(analysisId, that.analysisId) &&
                Objects.equals(analysisName, that.analysisName) &&
                Objects.equals(description, that.description) &&
                Objects.equals(engineVersion, that.engineVersion) &&
                Objects.equals(groupType, that.groupType) &&
                Objects.equals(cedant, that.cedant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, analysisId, analysisName, description, engineVersion, groupType, cedant);
    }
}
