package com.scor.rr.domain.dto;

import com.scor.rr.domain.riskLink.RLSavedDataSource;
import com.scor.rr.domain.views.RLImportedDataSourcesAndAnalysis;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RLDataSourcesDto {

    private Long rlSavedDataSourceId;
    private Long rlDataSourceId;
    private String dataSourceName;
    private Long dataSourceId;
    private String dataSourceType;
    private Long modelCount;
    private Long projectId;
    private Long userId;
    private String instanceId;
    private String instanceName;
    private List<Long> rlModelIdList;

    public RLDataSourcesDto(RLImportedDataSourcesAndAnalysis rlImportedDataSourcesAndAnalysis) {
        this.setRlDataSourceId(rlImportedDataSourcesAndAnalysis.getRlDataSourceId());
        this.setDataSourceId(rlImportedDataSourcesAndAnalysis.getRlDatabaseId());
        this.setModelCount(rlImportedDataSourcesAndAnalysis.getModelCount());
        this.setProjectId(rlImportedDataSourcesAndAnalysis.getProjectId());
        this.setDataSourceName(rlImportedDataSourcesAndAnalysis.getRlDataSourceName());
        this.setDataSourceType(rlImportedDataSourcesAndAnalysis.getType());
        this.rlModelIdList = new ArrayList<>();
        this.addToRlModelIdList(rlImportedDataSourcesAndAnalysis.getRlModelId());
        this.instanceId= rlImportedDataSourcesAndAnalysis.getInstanceId();
        this.instanceName= rlImportedDataSourcesAndAnalysis.getInstanceName();
    }

    public RLDataSourcesDto(RLSavedDataSource rlSavedDataSource) {
        this.setRlSavedDataSourceId(rlSavedDataSource.getRlSavedDataSourceId());
        this.setDataSourceId(rlSavedDataSource.getDataSourceId());
        this.setDataSourceName(rlSavedDataSource.getDataSourceName());
        this.setDataSourceType(rlSavedDataSource.getDataSourceType());
        this.setProjectId(rlSavedDataSource.getProjectId());
        this.setUserId(rlSavedDataSource.getUserId());
        this.setInstanceId(rlSavedDataSource.getInstanceId());
        this.setInstanceName(rlSavedDataSource.getInstanceName());
        this.rlModelIdList = new ArrayList<>();
    }

    public void addToRlModelIdList(Long id) {
        if(id != null)
            this.rlModelIdList.add(id);
    }
}
