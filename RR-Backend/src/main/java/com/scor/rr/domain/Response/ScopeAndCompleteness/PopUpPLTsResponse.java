package com.scor.rr.domain.Response.ScopeAndCompleteness;

import lombok.Data;

@Data
public class PopUpPLTsResponse {

    private long pltHeaderId;
    private String DefaultPltName;
    private String perilCode;
    private String regionPerilCode;
    private String regionPerilDesc;
    private String grain;
    private String contractSectionId;
}
