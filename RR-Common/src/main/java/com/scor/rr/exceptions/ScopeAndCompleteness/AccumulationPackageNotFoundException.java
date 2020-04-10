package com.scor.rr.exceptions.ScopeAndCompleteness;

import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;

public class AccumulationPackageNotFoundException extends RRException {
    public AccumulationPackageNotFoundException(long id) {
        super(ExceptionCodename.ACCUMULATION_PACKAGE_NOT_FOUND, "Accumulation package with id :" + id +" not found!" );
    }
}
