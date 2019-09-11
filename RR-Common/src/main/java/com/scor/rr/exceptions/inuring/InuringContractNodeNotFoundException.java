package com.scor.rr.exceptions.inuring;

import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;

/**
 * Created by u004602 on 11/09/2019.
 */
public class InuringContractNodeNotFoundException extends RRException {
    InuringContractNodeNotFoundException(String id) {
        super(ExceptionCodename.INURING_CONTRACT_NODE_NOT_FOUND, "Inuring Contract Node id " + id + " not found");
    }
}
