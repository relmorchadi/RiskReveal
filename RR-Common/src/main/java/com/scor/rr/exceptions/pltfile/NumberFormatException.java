package com.scor.rr.exceptions.pltfile;

import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;

/**
 * Created by u004602 on 27/06/2019.
 */
public class NumberFormatException extends RRException {
    public NumberFormatException(String message) {
        super(ExceptionCodename.NUMBER_FORMAT_WRONG, 1, "Number Format wrong: " + message);
    }
}
