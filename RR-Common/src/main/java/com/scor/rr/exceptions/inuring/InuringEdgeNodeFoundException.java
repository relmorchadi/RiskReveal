package com.scor.rr.exceptions.inuring;

import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;

/**
 * Created by u004602 on 11/09/2019.
 */
public class InuringEdgeNodeFoundException extends RRException {
    public InuringEdgeNodeFoundException(long id) {
        super(ExceptionCodename.INURING_EDGE_NOT_FOUND, "Inuring Edge id " + id + " not found");
    }
}
