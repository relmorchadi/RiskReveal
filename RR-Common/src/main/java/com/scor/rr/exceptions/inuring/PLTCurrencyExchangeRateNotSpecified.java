package com.scor.rr.exceptions.inuring;

import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;

public class PLTCurrencyExchangeRateNotSpecified extends RRException {
    public PLTCurrencyExchangeRateNotSpecified(String ccy) {
        super(ExceptionCodename.CURRENCY_NOT_FOUND, "Currency Exchange rate for "+ ccy + "not found.");
    }
}
