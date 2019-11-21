package com.scor.rr.exceptions.inuring;

import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;

/**
 * Created by u004602 on 11/09/2019.
 */
public class InuringInputNodeNotFoundException extends RRException{
    public InuringInputNodeNotFoundException(long id) {
        super(ExceptionCodename.INURING_INPUT_NODE_NOT_FOUND, "Inuring Input Node id " + id + " not found");
    }
}
