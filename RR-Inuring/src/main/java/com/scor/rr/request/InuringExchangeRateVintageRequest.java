package com.scor.rr.request;

import lombok.Data;

import java.util.Date;

@Data
public class InuringExchangeRateVintageRequest {

    private long inuringPackageId;
    private Date inceptionDate;

    public InuringExchangeRateVintageRequest() {
    }

    public InuringExchangeRateVintageRequest(long inuringPackageId, Date inceptionDate) {
        this.inuringPackageId = inuringPackageId;
        this.inceptionDate = inceptionDate;
    }
}
