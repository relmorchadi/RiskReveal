package com.scor.rr.exceptions.pltfile;

import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;

/**
 * Created by u004602 on 28/06/2019.
 */
public class PLTFileWriteException extends RRException {
    public PLTFileWriteException(String message) {
        super(ExceptionCodename.PLT_FILE_WRITE_ERROR, 1, "Error while writing to file: " + message);
    }
}
