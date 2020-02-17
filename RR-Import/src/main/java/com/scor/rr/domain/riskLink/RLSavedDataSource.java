package com.scor.rr.domain.riskLink;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "ZZ_RLSavedDataSource")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RLSavedDataSource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RlSavedDataSourceId")
    private Long rlSavedDataSourceId;
    @Column(name = "DataSourceName")
    private String dataSourceName;
    @Column(name = "DataSourceId")
    private Long dataSourceId;
    @Column(name = "DataSourceType")
    private String dataSourceType;
    @Column(name = "ProjectId")
    private Long projectId;
    @Column(name = "UserId")
    private Long userId;
    @Column(name = "InstanceId")
    private String instanceId;
    @Column(name = "InstanceName")
    private String instanceName;
}
