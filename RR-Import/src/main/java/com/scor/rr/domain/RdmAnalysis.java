package com.scor.rr.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RdmAnalysis {

    private Long rdmId;
    private String rdmName;
    private Long analysisId;
    private String analysisName;
    private String description;
    private String defaultGrain;
    private String exposureType;
    private int exposureTypeCode;
    private String edmNameSourceLink;
    private Long exposureId;
    private String analysisCurrency;
    private BigDecimal rmsExchangeRate;
    private int typeCode;
    private String analysisType;
    private String runDate;
    private String region;
    private String peril;
    private String rpCode;
    private String subPeril;
    private String lossAmplification;
    private Long status;
    private String analysisMode;
    private int engineTypeCode;
    private String engineType;
    private String engineVersion;
    private String engineVersionMajor;
    private String profileName;
    private String profileKey;
    private Boolean hasMultiRegionPerils;
    private Boolean validForExtract;
    private String notValidReason;
    private BigDecimal purePremium;
    private double exposureTiv;
    private String geoCode;
    private String geoDescription;
    private String user1;
    private String user2;
    private String user3;
    private String user4;
}
