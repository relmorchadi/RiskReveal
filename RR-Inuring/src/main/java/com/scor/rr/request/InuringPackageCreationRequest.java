package com.scor.rr.request;

import com.scor.rr.enums.InuringPackageStatus;

import lombok.Data;

/**
 * Created by u004602 on 16/09/2019.
 */

public class InuringPackageCreationRequest {
    private String packageName;
    private String packageDescription;
    private int workspaceId;
    private int createdBy;

    public InuringPackageCreationRequest(String packageName, String packageDescription, int workspaceId, int createdBy) {
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

    public int getWorkspaceId() {
        return workspaceId;
    }

    public int getCreatedBy() {
        return createdBy;
    }
}
