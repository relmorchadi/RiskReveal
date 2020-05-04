package com.scor.rr.exceptions.ScopeAndCompleteness;

import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;

public class ExpectedScopeFileNotSupportedException extends RRException {

    public ExpectedScopeFileNotSupportedException() {
        super(ExceptionCodename.EXPECTED_SCOPE_FILE_EXTENSION_NOT_SUPPORTED_EXCEPTION, "Expected scope file extension not supported!");
    }
}
