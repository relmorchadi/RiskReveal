package com.scor.rr.exceptions.inuring;

import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;

public class InuringFilterCriteriaNotFoundException extends RRException {
    public InuringFilterCriteriaNotFoundException(long inuringFilterCriteriaId){
        super(ExceptionCodename.INURING_FILTER_CRITERIA_NOT_FOUND, "Inuring Filter Criteria " + inuringFilterCriteriaId + " not found");
    }
}
