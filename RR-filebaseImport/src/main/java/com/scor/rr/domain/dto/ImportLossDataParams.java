package com.scor.rr.domain.dto;

import lombok.Data;

@Data
public class ImportLossDataParams {

    private String projectId;
    private String userId;
    private String instanceId;
    private String sourceResultInputIds;
}
