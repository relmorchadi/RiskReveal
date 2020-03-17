package com.scor.rr.domain.Response.ScopeAndCompleteness;

import lombok.Data;

import java.util.List;

@Data
public class InsideObject {

    private String id;
    private String description;
    private boolean overridden;
    private boolean selected;
    private String reason;
    private String reasonDescribed;
    private boolean attached;
    private List<AttachedPLT> pltsAttached;
}
