package com.scor.rr.domain.entities.rms;

import com.scor.rr.domain.entities.workspace.Project;
import com.scor.rr.domain.enums.ModelDataSourceType;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

/**
 * The persistent class for the RmsModelDatasource database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "RmsModelDatasource")
@Data
public class RmsModelDatasource {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "RmsId")
    private Long rmsId;
    @Column(name = "Name")
    private String name;
    @Column(name = "Source")
    private String source;
    @Column(name = "VersionId")
    private Long versionId;
    @Column(name = "Enabled")
    private Boolean enabled;
    @Column(name = "Imported")
    private Boolean imported;
    @Column(name = "DateCreated")
    private Date dateCreated;
    @Column(name = "InstanceId")
    private String instanceId;
    @Column(name = "InstanceName")
    private String instanceName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProjectId")
    private Project project;
    @Column(name = "Type")
    private ModelDataSourceType type;
}
