package com.scor.rr.domain.entities.ScopeAndCompleteness;

import lombok.Data;

import java.util.List;

@Data
public class ForeWriterExpectedScope {

    private String fACNumber;
    private int uWYear;
    private int endorsementNumber;
    private int uWOrder;
    private String sourceAnalysisName;
    private int divisionNumber;
    private String country;
    private String state;
    private String perils;
    private double tIV;
    private String tIVCurrency;

    public ForeWriterExpectedScope(String acctNum, int year, int endorNum, int order, String analysisName, int division, String country, String state, String perils, double tiv, String currency) {
        this.fACNumber = acctNum;
        this.uWYear = year;
        this.endorsementNumber = endorNum;
        this.uWOrder = order;
        this.sourceAnalysisName = analysisName;
        this.divisionNumber = division;
        this.country = country;
        this.state = state;
        this.perils = perils;
        this.tIV = tiv;
        this.tIVCurrency = currency;
    }
}
