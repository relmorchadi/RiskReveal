package com.rr.riskreveal.domain.dto;

import static com.google.common.base.Strings.isNullOrEmpty;
import static java.util.Optional.ofNullable;

public class WorkspaceFilter {
    private String cedant;
    private String country;
    private String year;
    private String program;
    private String treaty;
    private String analyst;
    private String projectId;
    private String plt;
    private String globalKeyword;

    public WorkspaceFilter() {
    }

    public String getCedant() {
        return cedant;
    }

    public void setCedant(String cedant) {
        this.cedant = cedant;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getTreaty() {
        return treaty;
    }

    public void setTreaty(String treaty) {
        this.treaty = treaty;
    }

    public String getAnalyst() {
        return analyst;
    }

    public void setAnalyst(String analyst) {
        this.analyst = analyst;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getPlt() {
        return plt;
    }

    public void setPlt(String plt) {
        this.plt = plt;
    }

    public String getGlobalKeyword() {
        return globalKeyword;
    }

    public void setGlobalKeyword(String globalKeyword) {
        this.globalKeyword = globalKeyword;
    }

    public boolean isEmpty() {
        return ofNullable(globalKeyword).map(glbKw -> glbKw.isEmpty()).orElse(false);
    }

    public boolean isGlobalSearch() {
        return isNullOrEmpty(cedant) && isNullOrEmpty(country) && isNullOrEmpty(year) && isNullOrEmpty(program)
                && isNullOrEmpty(treaty) && isNullOrEmpty(analyst) && isNullOrEmpty(projectId)  && isNullOrEmpty(plt);

    }
}
