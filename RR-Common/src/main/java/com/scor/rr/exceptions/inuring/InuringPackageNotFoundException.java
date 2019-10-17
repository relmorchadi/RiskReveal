package com.scor.rr.exceptions.inuring;

import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;

/**
 * Created by u004602 on 11/09/2019.
 */
public class InuringPackageNotFoundException extends RRException {
    public InuringPackageNotFoundException(int id) {
        super(ExceptionCodename.INURING_PACKAGE_NOT_FOUND, "Inuring Package id " + id + " not found");
    }
}
