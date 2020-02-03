package com.scor.rr.domain.views;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "vw_RLModelDataSource")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RLImportedDataSourcesAndAnalysis {

    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "RlDataSourceId")
    private Long rlDataSourceId;
    @Column(name = "RlDataSourceName")
    private String rlDataSourceName;
    @Column(name = "RlDatabaseId")
    private Long rlDatabaseId;
    @Column(name = "ModelCount")
    private Long modelCount;
    @Column(name = "RLModelId")
    // RLModelAnalysisId Or RLPortfolioId
    private Long rlModelId;
    @Column(name = "Type")
    private String type;
    @Column(name = "ProjectId")
    private Long projectId;
    @Column(name = "InstanceId")
    private String instanceId;
}
