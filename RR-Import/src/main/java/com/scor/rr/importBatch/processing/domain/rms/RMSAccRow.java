package com.scor.rr.importBatch.processing.domain.rms;

import java.util.Date;
import java.util.Objects;

/**
 * Created by U002629 on 03/04/2015.
 */
public class RMSAccRow {
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

    public RMSAccRow() {
    }

    public RMSAccRow(String carID, String division, Date inceptionDate, Date practicalCompletionDate, String regionPerilCode, Double catDeductibleAmount, Double catSubLimit) {
        this.carID = carID;
        this.division = division;
        this.inceptionDate = inceptionDate;
        this.practicalCompletionDate = practicalCompletionDate;
        this.regionPerilCode = regionPerilCode;
        this.catDeductibleAmount = catDeductibleAmount;
        this.catSubLimit = catSubLimit;
    }

    public RMSAccRow(String countryCode, String locationGroupCode, String peril, Double catDeductibleAmount, Double catSubLimit, Date inceptionDate, Date practicalCompletionDate) {
        this.countryCode = countryCode;
        this.locationGroupCode = locationGroupCode;
        this.peril = peril;
        this.catDeductibleAmount = catDeductibleAmount;
        this.catSubLimit = catSubLimit;
        this.inceptionDate = inceptionDate;
        this.practicalCompletionDate = practicalCompletionDate;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getLocationGroupCode() {
        return locationGroupCode;
    }

    public void setLocationGroupCode(String locationGroupCode) {
        this.locationGroupCode = locationGroupCode;
    }

    public String getPeril() {
        return peril;
    }

    public void setPeril(String peril) {
        this.peril = peril;
    }

    public Double getCatDeductibleAmount() {
        return catDeductibleAmount;
    }

    public void setCatDeductibleAmount(Double catDeductibleAmount) {
        this.catDeductibleAmount = catDeductibleAmount;
    }

    public Double getCatSubLimit() {
        return catSubLimit;
    }

    public void setCatSubLimit(Double catSubLimit) {
        this.catSubLimit = catSubLimit;
    }


    public String getCarID() {
        return carID;
    }

    public void setCarID(String carID) {
        this.carID = carID;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getRegionPerilCode() {
        return regionPerilCode;
    }

    public void setRegionPerilCode(String regionPerilCode) {
        this.regionPerilCode = regionPerilCode;
    }

    public Date getInceptionDate() {
        return inceptionDate;
    }

    public void setInceptionDate(Date inceptionDate) {
        this.inceptionDate = inceptionDate;
    }

    public Date getPracticalCompletionDate() {
        return practicalCompletionDate;
    }

    public void setPracticalCompletionDate(Date practicalCompletionDate) {
        this.practicalCompletionDate = practicalCompletionDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RMSAccRow rmsAccRow = (RMSAccRow) o;
        return Objects.equals(countryCode, rmsAccRow.countryCode) &&
                Objects.equals(locationGroupCode, rmsAccRow.locationGroupCode) &&
                Objects.equals(peril, rmsAccRow.peril);
    }

    @Override
    public int hashCode() {
        return Objects.hash(countryCode, locationGroupCode, peril);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RMSAccRow{");
        sb.append("carID='").append(carID).append('\'');
        sb.append(", division='").append(division).append('\'');
        sb.append(", regionPerilCode='").append(regionPerilCode).append('\'');
        sb.append(", countryCode='").append(countryCode).append('\'');
        sb.append(", locationGroupCode='").append(locationGroupCode).append('\'');
        sb.append(", peril='").append(peril).append('\'');
        sb.append(", catDeductibleAmount=").append(catDeductibleAmount);
        sb.append(", catSubLimit=").append(catSubLimit);
        sb.append(", inceptionDate=").append(inceptionDate);
        sb.append(", practicalCompletionDate=").append(practicalCompletionDate);
        sb.append('}');
        return sb.toString();
    }
}
