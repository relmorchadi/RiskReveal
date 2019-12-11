package com.scor.rr.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class DataSource {

    private Long rmsId;
    private String name;
    private String type;
    @JsonIgnore
    private int versionId;
    @JsonIgnore
    private String dateCreated;

}
