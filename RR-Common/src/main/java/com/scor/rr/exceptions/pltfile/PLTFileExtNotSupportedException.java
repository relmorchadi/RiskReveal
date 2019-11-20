package com.scor.rr.exceptions.pltfile;

import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;

/**
 * Created by u004602 on 25/06/2019.
 */
public class PLTFileExtNotSupportedException extends RRException {
    public PLTFileExtNotSupportedException() {
        super(ExceptionCodename.PLT_FILE_EXT_NOT_SUPPORTED, 1, "PLT file extension not supported");
    }
}
