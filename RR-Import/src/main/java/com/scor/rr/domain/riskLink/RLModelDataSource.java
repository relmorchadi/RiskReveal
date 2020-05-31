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
    @Column(name = "RlDataSourceId")
    private Long rlModelDataSourceId;
    @Column(name = "Entity")
    private Integer entity;
    @Column(name = "ProjectId")
    private Long projectId;
    @Column(name = "RLDatabaseId")
    private Long rlId;
    @Column(name = "RLDataSourceName")
    private String name;
    @Column(name = "RLInstanceName")
    private String instanceName;
    @Column(name = "RLInstanceId")
    private String instanceId;
    @Column(name = "DataType")
    private String type;
    @Column(name = "RLVersionId")
    private String versionId;
    @Column(name = "RLDateCreated")
    private Date dateCreated;
    @Column(name = "LastBasicScanStarted")
    private Date lastBasicScanStart;
    @Column(name = "LastBasicScanEnd")
    private Date lastBasicScanEnd;

    /**
     * Case (type = RDM) stores count of Analysis else portfolios
     */
    @Column(name = "ModelCount")
    private Integer count;

    public RLModelDataSource(DataSource dataSource, Long projectId, String instanceId, String instanceName) {
        this.projectId = projectId;
        this.entity = 1;
        this.name = dataSource.getName();
        this.instanceId = instanceId;
        this.instanceName = instanceName;
        this.rlId = dataSource.getRmsId();
        this.type = dataSource.getType();
        this.versionId = String.valueOf(dataSource.getVersionId());
        this.dateCreated = new Date();
    }
}
