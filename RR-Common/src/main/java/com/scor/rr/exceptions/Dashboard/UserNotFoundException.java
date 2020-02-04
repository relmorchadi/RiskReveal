package com.scor.rr.exceptions.Dashboard;

import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;

public class UserNotFoundException extends RRException {
    public UserNotFoundException(long id) {
        super(ExceptionCodename.USER_NOT_FOUND_EXCEPTION, "User with id: " + id + " was not found");
    }
}
