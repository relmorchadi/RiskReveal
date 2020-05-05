package com.scor.rr.exceptions.ScopeAndCompleteness;

import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;

public class WorkspaceNotFoundException extends RRException {
    public WorkspaceNotFoundException(String name, int uwyear) {
        super(ExceptionCodename.WORKSPACE_NOT_FOUND, "Workspace with name : " + name +" and uwyear: "+uwyear +" was not found!" );
    }
}

