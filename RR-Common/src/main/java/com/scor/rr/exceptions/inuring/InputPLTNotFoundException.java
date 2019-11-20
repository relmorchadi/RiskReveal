package com.scor.rr.exceptions.inuring;

import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;

/**
 * Created by u004602 on 11/09/2019.
 */
public class InputPLTNotFoundException extends RRException {
    public InputPLTNotFoundException(int pltId) {
        super(ExceptionCodename.PLT_NOT_FOUND, "Input PLT id " + pltId + " not found");
    }
}
