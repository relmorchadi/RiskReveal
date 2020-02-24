package com.scor.rr.domain.entities.ScopeAndCompleteness;

import lombok.Data;

import java.util.List;

@Data
public class ForeWriterExpectedScope {

    private String acctNum;
    private int year;
    private int endorNum;
    private int order;
    private String analysisName;
    private int division;
    private String country;
    private String state;
    List<String> perils;
    private double tiv;
    private String currency;

    public ForeWriterExpectedScope(String acctNum, int year, int endorNum, int order, String analysisName, int division, String country, String state, List<String> perils, double tiv, String currency) {
        this.acctNum = acctNum;
        this.year = year;
        this.endorNum = endorNum;
        this.order = order;
        this.analysisName = analysisName;
        this.division = division;
        this.country = country;
        this.state = state;
        this.perils = perils;
        this.tiv = tiv;
        this.currency = currency;
    }
}
