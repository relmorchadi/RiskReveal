package com.scor.rr.exceptions.inuring;

import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;

public class ContributionMatrixNotFoundException extends RRException {
    public ContributionMatrixNotFoundException(int id) {
        super(ExceptionCodename.CONTRIBUTION_NOT_FOUND_EXCEPTION, "Couldn't find the contribution (or empty) with phase:  " + id );
    }
}
