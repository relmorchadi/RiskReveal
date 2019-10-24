package com.scor.rr.exceptions.inuring;

import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;

public class IllogicalEdgeCreationException extends RRException {
    public IllogicalEdgeCreationException(int id) {
        super(ExceptionCodename.ILLOGICAL_EDGE_CREATION,"IllogicalEdgeCreation ");
    }
}
