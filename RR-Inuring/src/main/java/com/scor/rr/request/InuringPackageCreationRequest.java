package com.scor.rr.request;

import lombok.Data;

/**
 * Created by u004602 on 16/09/2019.
 */

public class InuringPackageCreationRequest {
    private String packageName;
    private String packageDescription;
    private long workspaceId;
    private long createdBy;

    public InuringPackageCreationRequest(String packageName, String packageDescription, long workspaceId, long createdBy) {
        this.packageName = packageName;
        this.packageDescription = packageDescription;
        this.workspaceId = workspaceId;
        this.createdBy = createdBy;
    }

    public InuringPackageCreationRequest() {
    }

    public String getPackageName() {
        return packageName;
    }

    public String getPackageDescription() {
        return packageDescription;
    }

    public long getWorkspaceId() {
        return workspaceId;
    }

    public long getCreatedBy() {
        return createdBy;
    }
}
