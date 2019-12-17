package com.scor.rr.exceptions.inuring;

import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;

public class InuringContractTypeNotFoundException extends RRException {
    public InuringContractTypeNotFoundException(String id) {
        super(ExceptionCodename.CONTRACT_TYPE_NOT_FOUND, "ContractType " + id + " not found");
    }
}
