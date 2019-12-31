package com.scor.rr.exceptions.inuring;

import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;

public class InuringContractLayerParamNotFoundException extends RRException {
    public InuringContractLayerParamNotFoundException(String  name) {
        super(ExceptionCodename.INURING_CONTRACT_LAYER_PARAM_NOT_FOUND, "Inuring Contract Layer Param " + name + " not found");
    }

}
