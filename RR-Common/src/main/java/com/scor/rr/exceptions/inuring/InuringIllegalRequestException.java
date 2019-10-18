package com.scor.rr.exceptions.inuring;

import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;
import com.sun.org.apache.regexp.internal.RE;

/**
 * Created by u004602 on 25/09/2019.
 */
public class InuringIllegalRequestException extends RRException {
    public InuringIllegalRequestException(String msg) {
        super(ExceptionCodename.INURING_ILLEGAL_REQUEST, msg);
    }
}
