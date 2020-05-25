package com.scor.rr.domain.dto;

import com.scor.rr.domain.enums.NumberOperator;
import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
public class PLTManagerFilter {


    private Long pltId;
    private String pltName;
    private String pltType;
    private String pltStatus;
    private Boolean groupedPlt;
    private String grain;
    private Boolean xActPublication;
    private Boolean xActPriced;
    private Boolean arcPublication;
    private String perilGroupCode;
    private String regionPerilCode;
    private String regionPerilDesc;
    private String minimumGrainRPCode;
    private String minimumGrainRPDescription;
    private String financialPerspective;
    private String targetRAPCode;
    private String targetRAPDesc;
    private String rootRegionPeril;
    private String vendorSystem;
    private String modellingDataSource;
    private Long analysisId;
    private String analysisName;
    private String defaultOccurenceBasis;
    private String occurenceBasis;
    private Boolean baseAdjustment;
    private Boolean defaultAdjustment;
    private Boolean clientAdjustment;
    private Long projectId;
    private String projectName;
    private String workspaceContextCode;
    private String client;
    private Integer uwYear;
    private Boolean clonedPlt;
    private Long clonedSourcePlt;
    private Long clonedSourceProject;
    private Long clonedSourceWorkspace;
    private String pltCcy;
    private Double aal;
    private Double cov;
    private Double stdDev;
    private Double oep10;
    private Double oep50;
    private Double oep100;
    private Double oep250;
    private Double oep500;
    private Double oep1000;
    private Double aep10;
    private Double aep50;
    private Double aep100;
    private Double aep250;
    private Double aep500;
    private Double aep1000;
    private Date createdDate;
    private String importedBy;
    private Date xActPublicationDate;
    private String publishedBy;
    private Boolean archived;
    private Date archivedDate;
    private String deletedBy;
    private String deletedDue;
    private Date deletedOn;

    //ADDITIONAL FIELDS

    private Long targetRapId;
    private Long regionPerilId;

    private Map<String, NumberOperator> numberOperators;

}
