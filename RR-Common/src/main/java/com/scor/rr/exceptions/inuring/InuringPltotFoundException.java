package com.scor.rr.exceptions.inuring;

import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;

public class InuringPltotFoundException extends RRException {
    public InuringPltotFoundException(int id) {
        super(ExceptionCodename.INURING_PLT_NOT_FOUND, "Inuring PLT with id " + id + " not found");
    }
}
