package com.scor.rr.exceptions.inuring;

import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.ExceptionPayload;
import com.scor.rr.exceptions.RRException;

/**
 * Created by u004602 on 17/09/2019.
 */
public class InuringIllegalModificationException extends RRException {
    public InuringIllegalModificationException(int inuringPackageId) {
        super(ExceptionCodename.INURING_ILLEGAL_MODIFICATION, "Inuring Package ID " + inuringPackageId + " was locked and could not be modified or deleted");
    }
}
