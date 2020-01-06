package com.scor.rr.domain.dto;

import com.scor.rr.domain.ModellingSystemInstanceEntity;
import lombok.Data;

@Data
public class RmsInstanceDto {
    private String instanceId;
    private String instanceName;

    public RmsInstanceDto(ModellingSystemInstanceEntity msi) {
        this.instanceId=msi.getModellingSystemInstanceId();
        this.instanceName=msi.getName();
    }
}
