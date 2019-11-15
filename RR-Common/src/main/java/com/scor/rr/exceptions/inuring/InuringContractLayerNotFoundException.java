package com.scor.rr.exceptions.inuring;

import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;

/**
 * Created by U004602 on 21/10/2019.
 */
public class InuringContractLayerNotFoundException extends RRException {
    public InuringContractLayerNotFoundException(long id) {
        super(ExceptionCodename.INURING_CONTRACT_LAYER_NOT_FOUND, "Inuring Contract Layer id " + id + " not found");
    }
}
