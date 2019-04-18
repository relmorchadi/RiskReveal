package com.rr.riskreveal.domain;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class WorkspaceViewId implements Serializable {
    //        @Column(name = "UwYear")
    private Integer uwYear;
    //        @Column(name = "WorkSpaceId")
    private String workSpaceId;

    public WorkspaceViewId() {
    }

    public Integer getUwYear() {
        return uwYear;
    }

    public void setUwYear(Integer uwYear) {
        this.uwYear = uwYear;
    }

    public String getWorkSpaceId() {
        return workSpaceId;
    }

    public void setWorkSpaceId(String workSpaceId) {
        this.workSpaceId = workSpaceId;
    }
}
