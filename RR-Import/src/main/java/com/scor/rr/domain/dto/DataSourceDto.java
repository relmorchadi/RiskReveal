package com.scor.rr.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataSourceDto {
    private String dataSourceName;
    private Long dataSourceId;
    private String dataSourceType;
    private String instanceId;
    private String instanceName;
}
