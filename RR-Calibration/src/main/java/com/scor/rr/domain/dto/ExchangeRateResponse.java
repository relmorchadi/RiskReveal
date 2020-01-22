package com.scor.rr.domain.dto;


public class ExchangeRateResponse {

    Double GBP_Rate;
    Double SGD_Rate;
    Double CAD_Rate;
    Double EUR_Rate;
    Double USD_Rate;
    String domesticCurrencyCode;


    public Double getGBP_Rate() {
        return GBP_Rate;
    }

    public void setGBP_Rate(Double GBP_Rate) {
        this.GBP_Rate = GBP_Rate;
    }

    public Double getSGD_Rate() {
        return SGD_Rate;
    }

    public void setSGD_Rate(Double SGD_Rate) {
        this.SGD_Rate = SGD_Rate;
    }

    public Double getCAD_Rate() {
        return CAD_Rate;
    }

    public void setCAD_Rate(Double CAD_Rate) {
        this.CAD_Rate = CAD_Rate;
    }

    public Double getEUR_Rate() {
        return EUR_Rate;
    }

    public void setEUR_Rate(Double EUR_Rate) {
        this.EUR_Rate = EUR_Rate;
    }

    public Double getUSD_Rate() {
        return USD_Rate;
    }

    public void setUSD_Rate(Double USD_Rate) {
        this.USD_Rate = USD_Rate;
    }

    public String getCurrencyCode() {
        return this.domesticCurrencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.domesticCurrencyCode = currencyCode;
    }
}
