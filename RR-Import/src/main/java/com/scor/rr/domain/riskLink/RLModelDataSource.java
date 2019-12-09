package com.scor.rr.domain.riskLink;


import com.scor.rr.domain.DataSource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "RLModelDataSource")
@AllArgsConstructor
@NoArgsConstructor
public class RLModelDataSource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RlModelDataSourceId")
    private Long rlModelDataSourceId;
    @Column(name = "Entity")
    private Integer entity;
    @Column(name = "ProjectId")
    private Long projectId;
    @Column(name = "RlId")
    private Long rlId;
    @Column(name = "InstanceName")
    private String instanceName;
    @Column(name = "InstanceId")
    private String instanceId;
    @Column(name = "Name")
    private String name;
    @Column(name = "Type")
    private String type;
    @Column(name = "VersionId")
    private String versionId;
    @Column(name = "DateCreated")
    private Date dateCreated;

    public RLModelDataSource(DataSource dataSource, Long projectId, String instanceId, String instanceName) {
        this.projectId = projectId;
        this.entity = 1;
        this.instanceId = instanceId;
        this.instanceName = instanceName;
        this.rlId = dataSource.getRmsId();
        this.name = dataSource.getName();
        this.type = dataSource.getType();
        this.versionId = String.valueOf(dataSource.getVersionId());
        this.dateCreated = new Date();
    }

}
