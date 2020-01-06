package com.scor.rr.exceptions.inuring;

import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.ExceptionPayload;
import com.scor.rr.exceptions.RRException;

public class InuringFinalAttachedPltNotFoundException extends RRException {
    public InuringFinalAttachedPltNotFoundException(long id) {
        super(ExceptionCodename.INURING_FINAL_ATTACHED_PLT_NOT_FOUND, "Inuring Final ExpectedPlt with id: "+ id+" not Found!");
    }
}
