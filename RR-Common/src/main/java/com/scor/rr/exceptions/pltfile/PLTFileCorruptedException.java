package com.scor.rr.exceptions.pltfile;

import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;

/**
 * Created by u004602 on 25/06/2019.
 */
public class PLTFileCorruptedException extends RRException {
    public PLTFileCorruptedException() {
        super(ExceptionCodename.PLT_FILE_CORRUPTED, 1, "PLT file was corrupted or truncated");
    }
}
