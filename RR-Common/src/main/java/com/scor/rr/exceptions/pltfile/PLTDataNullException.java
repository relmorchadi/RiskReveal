package com.scor.rr.exceptions.pltfile;

import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;

/**
 * Created by u004602 on 28/06/2019.
 */
public class PLTDataNullException extends RRException {
    public PLTDataNullException() {
        super(ExceptionCodename.PLT_DATA_NULL, 1, "PLT Data is null");
    }
}
