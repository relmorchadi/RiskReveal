package com.scor.rr.exceptions.ScopeAndCompleteness;

import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;

public class ExpectedScopeFileCorruptedException extends RRException {
    public ExpectedScopeFileCorruptedException() {
        super(ExceptionCodename.EXPECTED_SCOPE_FILE_CORRUPTED_EXCEPTION, "Expected scope file is corrupted!");
    }
}
