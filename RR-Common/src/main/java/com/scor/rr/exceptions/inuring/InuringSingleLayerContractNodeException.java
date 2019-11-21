package com.scor.rr.exceptions.inuring;

import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;

public class InuringSingleLayerContractNodeException extends RRException {

        public InuringSingleLayerContractNodeException(long id) {
            super(ExceptionCodename.INURING_SINGLE_LAYER_CONTRACT_NODE, "Inuring Contract Node id " + id);
        }

}
