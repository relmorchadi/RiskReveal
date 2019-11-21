package com.scor.rr.JsonFormat;

import lombok.Data;

@Data
public class ExchangeRate {

    private String ccy;
    private double rateToTargeCcy;
}
