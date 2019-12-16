package com.scor.rr.exceptions.inuring;

import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;

public class PltWithSameNameAlreadyExists extends RRException {
    public PltWithSameNameAlreadyExists(String name) {
        super(ExceptionCodename.INURING_GROUPED_PLT_ALREADY_EXISTS, "groupedPlt with the name: "+name+" already exists!");
    }
}
