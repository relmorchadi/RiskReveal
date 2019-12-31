package com.scor.rr.exceptions.inuring;

import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;

public class ExchangeRateTypeNotFoundException extends RRException {
    public ExchangeRateTypeNotFoundException(String id) {
        super(ExceptionCodename.EXCHANGE_RATE_TYPE_NOT_FOUND,"ExchangeRate Type: "+id +" not found!");
    }
}
