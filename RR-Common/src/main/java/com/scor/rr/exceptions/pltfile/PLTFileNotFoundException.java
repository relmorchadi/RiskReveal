package com.scor.rr.exceptions.pltfile;

import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;

/**
 * Created by u004602 on 25/06/2019.
 */
public class PLTFileNotFoundException  extends RRException {
    public PLTFileNotFoundException() {
        super(ExceptionCodename.PLT_FILE_NOT_FOUND, 1, "PLT file not found");
    }
}
