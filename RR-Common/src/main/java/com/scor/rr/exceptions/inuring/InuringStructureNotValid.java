package com.scor.rr.exceptions.inuring;

import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;

public class InuringStructureNotValid extends RRException {
    public InuringStructureNotValid() {
        super(ExceptionCodename.INURING_STRUCTURE_NOT_VALID, "Inuring Structure not Valid to expect outcome PLTs. " );
    }

}
