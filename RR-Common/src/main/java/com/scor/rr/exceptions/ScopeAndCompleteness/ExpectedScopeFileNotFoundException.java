package com.scor.rr.exceptions.ScopeAndCompleteness;

import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;

public class ExpectedScopeFileNotFoundException extends RRException {

    public ExpectedScopeFileNotFoundException() {
        super(ExceptionCodename.EXPECTED_SCOPE_FILE_NOT_FOUND_EXCEPTION, "Expected scope file not found!");
    }

}
