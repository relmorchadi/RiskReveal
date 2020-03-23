package com.scor.rr.domain.Response.ScopeAndCompleteness;

import lombok.Data;

import java.util.ArrayList;

@Data
public class AttachedPLT {

    private String workspaceId;
    private String uwy;
    private long pltId;
    private String pltName;
    private String peril;
    private String regionPerilCode;
    private String regionPerilName;
    private String grain;
    private String vendorSystem;
    private String targetRapCode;
    private boolean isScorCurrent;
    private boolean isScorDefault;
    private boolean isScorGenerated;
    private long project;
    private String pltType;
    private String projectName;
    private String creationDate;
    private String year;
    private String fileName;
    private String sourceModellingVendor;
    private String sourceModellingSystem;
    private String dataSourceName;
    private double analysisId;
    private String currency;
    private String userOccurrenceBasis;
    private ArrayList userTags;
    private String xactPublicationDate;
}
