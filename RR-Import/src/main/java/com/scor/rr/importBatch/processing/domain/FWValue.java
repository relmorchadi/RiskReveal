package com.scor.rr.importBatch.processing.domain;

import java.util.Objects;

/**
 * Created by U002629 on 09/04/2015.
 */
public class FWValue {
    private String CatAnalysisRequestID;
    private Integer DivisionNumber;
    private String RegionPerilCode;
    private Integer ReturnPeriod;
    private String Currencycode;
    private Double GroundUpLoss;
    private Double GrossLoss;
    private String periodBasis;

    public FWValue() {
    }

    public FWValue(String catAnalysisRequestID, Integer divisionNumber, String regionPerilCode, Integer returnPeriod, String currencycode, Double groundUpLoss, Double grossLoss, String periodBasis) {
        CatAnalysisRequestID = catAnalysisRequestID;
        DivisionNumber = divisionNumber;
        RegionPerilCode = regionPerilCode;
        ReturnPeriod = returnPeriod;
        Currencycode = currencycode;
        GroundUpLoss = groundUpLoss;
        GrossLoss = grossLoss;
        this.periodBasis = periodBasis;
    }

    public String getCatAnalysisRequestID() {
        return CatAnalysisRequestID;
    }

    public void setCatAnalysisRequestID(String catAnalysisRequestID) {
        CatAnalysisRequestID = catAnalysisRequestID;
    }

    public Integer getDivisionNumber() {
        return DivisionNumber;
    }

    public void setDivisionNumber(Integer divisionNumber) {
        DivisionNumber = divisionNumber;
    }

    public String getRegionPerilCode() {
        return RegionPerilCode;
    }

    public void setRegionPerilCode(String regionPerilCode) {
        RegionPerilCode = regionPerilCode;
    }

    public Integer getReturnPeriod() {
        return ReturnPeriod;
    }

    public void setReturnPeriod(Integer returnPeriod) {
        ReturnPeriod = returnPeriod;
    }

    public String getCurrencycode() {
        return Currencycode;
    }

    public void setCurrencycode(String currencycode) {
        Currencycode = currencycode;
    }

    public Double getGroundUpLoss() {
        return GroundUpLoss;
    }

    public void setGroundUpLoss(Double groundUpLoss) {
        GroundUpLoss = groundUpLoss;
    }

    public Double getGrossLoss() {
        return GrossLoss;
    }

    public void setGrossLoss(Double grossLoss) {
        GrossLoss = grossLoss;
    }

    public String getPeriodBasis() {
        return periodBasis;
    }

    public void setPeriodBasis(String periodBasis) {
        this.periodBasis = periodBasis;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FWValue fwValue = (FWValue) o;
        return Objects.equals(CatAnalysisRequestID, fwValue.CatAnalysisRequestID) &&
                Objects.equals(DivisionNumber, fwValue.DivisionNumber) &&
                Objects.equals(RegionPerilCode, fwValue.RegionPerilCode) &&
                Objects.equals(ReturnPeriod, fwValue.ReturnPeriod) &&
                Objects.equals(periodBasis, fwValue.periodBasis);
    }

    @Override
    public int hashCode() {
        return Objects.hash(CatAnalysisRequestID, DivisionNumber, RegionPerilCode, ReturnPeriod, periodBasis);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FWValue{");
        sb.append("CatAnalysisRequestID='").append(CatAnalysisRequestID).append('\'');
        sb.append(", DivisionNumber=").append(DivisionNumber);
        sb.append(", RegionPerilCode='").append(RegionPerilCode).append('\'');
        sb.append(", ReturnPeriod=").append(ReturnPeriod);
        sb.append(", Currencycode='").append(Currencycode).append('\'');
        sb.append(", GroundUpLoss=").append(GroundUpLoss);
        sb.append(", GrossLoss=").append(GrossLoss);
        sb.append(", periodBasis='").append(periodBasis).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
