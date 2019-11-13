package com.scor.rr.domain;


import lombok.Data;

@Data
public class DataSource {

    private Long rmsId;
    private String name;
    private String dateCreated;
    private String type;
    private int versionId;

    public Long getRmsId() {
        return rmsId;
    }

    public void setRmsId(Long rmsId) {
        this.rmsId = rmsId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getVersionId() {
        return versionId;
    }

    public void setVersionId(int versionId) {
        this.versionId = versionId;
    }
}
