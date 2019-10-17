package com.scor.rr.domain;


import lombok.Data;

@Data
public class DataSource {

    private Long rmsId;
    private String name;
    private String dateCreated;
    private String type;
    private int versionId;


}
