package com.scor.rr.request;

import java.util.Date;

public class InuringExchangeRateVintageRequest {

    private String currency;
    private String targetCurrency;
    private String type;
    private Date date;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(String targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public InuringExchangeRateVintageRequest() {
    }

    public InuringExchangeRateVintageRequest(String currency, String targetCurrency, String type, Date date) {
        this.currency = currency;
        this.targetCurrency = targetCurrency;
        this.type = type;
        this.date = date;
    }
}
