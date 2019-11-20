package com.scor.rr.exceptions.inuring;

import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;

/**
 * Created by u004602 on 18/09/2019.
 */
public class InuringNodeNotFoundException extends RRException {
    public InuringNodeNotFoundException(String nodeType, int id) {
        super(ExceptionCodename.INURING_NODE_NOT_FOUND, "Inuring Node type " + nodeType + " id " + id + " not found");
    }
}
