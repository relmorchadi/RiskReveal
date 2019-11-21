package com.scor.rr.exceptions.inuring;

import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;

/**
 * Created by u004602 on 11/09/2019.
 */
public class InuringFinalNodeNotFoundException extends RRException {
    public InuringFinalNodeNotFoundException(long id) {
        super(ExceptionCodename.INURING_FINAL_NODE_NOT_FOUND, "Inuring Final Node id " + id + " not found");
    }
}
