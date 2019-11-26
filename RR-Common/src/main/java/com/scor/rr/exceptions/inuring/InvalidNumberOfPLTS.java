package com.scor.rr.exceptions.inuring;

import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.ExceptionPayload;
import com.scor.rr.exceptions.RRException;

public class InvalidNumberOfPLTS extends RRException {
    public InvalidNumberOfPLTS(int number) {
        super(ExceptionCodename.INURING_INVALID_NUMBER_PLTS, "Can't group "+ number +"PLTS. ");
    }
}
