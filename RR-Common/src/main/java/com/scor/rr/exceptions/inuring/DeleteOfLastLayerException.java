package com.scor.rr.exceptions.inuring;

import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;

public class DeleteOfLastLayerException extends RRException {
    public DeleteOfLastLayerException(long id) {
        super(ExceptionCodename.DELETE_LAST_LAYER_IMPOSSIBLE, "Impossible delete of the last Layer with id: " + id );
    }
}
