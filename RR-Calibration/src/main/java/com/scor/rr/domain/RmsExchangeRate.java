package com.scor.rr.domain;

import lombok.Data;

@Data
public class RmsExchangeRate {

    private String ccy;
    private double roe;
    private String roeAsAt;
}
