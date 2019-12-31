package com.scor.rr.service.batch.processor.rows;

import lombok.Data;

import java.util.Date;

@Data
public class RLAccRow {

    private String carID;
    private String division;
    private String regionPerilCode;
    private String countryCode;
    private String locationGroupCode;
    private String peril;
    private Double catDeductibleAmount;
    private Double catSubLimit;
    private Date inceptionDate;
    private Date practicalCompletionDate;

    public RLAccRow(String carID, String division, Date inceptionDate, Date practicalCompletionDate, String regionPerilCode, Double catDeductibleAmount, Double catSubLimit) {
        this.carID = carID;
        this.division = division;
        this.inceptionDate = inceptionDate;
        this.practicalCompletionDate = practicalCompletionDate;
        this.regionPerilCode = regionPerilCode;
        this.catDeductibleAmount = catDeductibleAmount;
        this.catSubLimit = catSubLimit;
    }
}
