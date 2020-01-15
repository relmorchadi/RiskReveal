package com.scor.rr.domain.dto;

import com.scor.rr.domain.views.RLImportedDataSourcesAndAnalysis;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RLImportedDataSourcesDto {

    private Long rlDataSourceId;
    private String rlDataSourceName;
    private Long rlDatabaseId;
    private String type;
    private Long modelCount;
    private Long projectId;
    private List<Long> rlModelIdList;

    public RLImportedDataSourcesDto(RLImportedDataSourcesAndAnalysis rlImportedDataSourcesAndAnalysis) {
        this.setRlDataSourceId(rlImportedDataSourcesAndAnalysis.getRlDataSourceId());
        this.setRlDatabaseId(rlImportedDataSourcesAndAnalysis.getRlDatabaseId());
        this.setModelCount(rlImportedDataSourcesAndAnalysis.getModelCount());
        this.setProjectId(rlImportedDataSourcesAndAnalysis.getProjectId());
        this.setRlDataSourceName(rlImportedDataSourcesAndAnalysis.getRlDataSourceName());
        this.setType(rlImportedDataSourcesAndAnalysis.getType());
        this.rlModelIdList = new ArrayList<>();
        this.rlModelIdList.add(rlImportedDataSourcesAndAnalysis.getRlModelId());
    }

    public void addToRlModelIdList(Long id) {
        this.rlModelIdList.add(id);
    }
}
