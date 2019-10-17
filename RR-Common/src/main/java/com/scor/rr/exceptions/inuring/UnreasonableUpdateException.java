package com.scor.rr.exceptions.inuring;

import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;

public class UnreasonableUpdateException extends RRException {
    public UnreasonableUpdateException(int id) {
        super(ExceptionCodename.UNREASONABLE_CHANGE, "Inuring Edge id " + id );
    }
}
