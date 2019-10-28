package com.scor.rr.domain;

import lombok.Data;

@Data
public class RmsExchangeRate {

    private String ccy;
    private double roe;
    private String roeAsAt;

    public String getCcy() {
        return ccy;
    }

    public void setCcy(String ccy) {
        this.ccy = ccy;
    }

    public double getRoe() {
        return roe;
    }

    public void setRoe(double roe) {
        this.roe = roe;
    }

    public String getRoeAsAt() {
        return roeAsAt;
    }

    public void setRoeAsAt(String roeAsAt) {
        this.roeAsAt = roeAsAt;
    }
}
