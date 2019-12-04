package com.scor.rr.exceptions.inuring;

import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.ExceptionPayload;
import com.scor.rr.exceptions.RRException;

public class PositivePLTNotFoundException extends RRException {
    public PositivePLTNotFoundException() {
        super(ExceptionCodename.INURING_PLT_NOT_FOUND, "No positive PLT found");
    }
}
