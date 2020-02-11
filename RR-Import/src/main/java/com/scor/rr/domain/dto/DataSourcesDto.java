package com.scor.rr.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataSourcesDto {

    private List<DataSourceDto> dataSources;
    private String instanceId;
    private String instanceName;
    private Long projectId;
    private Long userId;
}
