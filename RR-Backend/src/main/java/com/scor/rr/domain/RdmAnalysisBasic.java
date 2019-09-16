package com.scor.rr.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class RdmAnalysisBasic {

    private Long rdmId;
    private String rdmName;
    private Long analysisId;
    private String analysisName;
    private String description;
    private String engineVersion;
    private String groupTypeName;
    private String cedant;
    private String lobName;
    private Boolean grouping;
    private String engineType;
    private String runDate;
    private String typeName;
    private String peril;
    private String subPeril;
    private String lossAmplification;
    private String region;
    private String regionName;
    private String modeName;
    private String user1;
    private String user2;
    private String user3;
    private String user4;
    private String analysisCurrency;
    private String statusDescription;

}
