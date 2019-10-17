package com.scor.rr.domain.entities.tracking;


import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the ProjectImportLog database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "ProjectImportLog")
@Data
public class ProjectImportLog {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProjectImportLogId")
    private String projectImportLogId;
    @Column(name = "ProjectId")
    private String projectId;
    @Column(name = "ProjectImportRunId")
    private String projectImportRunId;
    @Column(name = "AssetId")
    private String assetId;
    @Column(name = "AssetType")
    private String assetType;
    @Column(name = "StartDate")
    private Date startDate;
    @Column(name = "EndDate")
    private Date endDate;
    @Column(name = "ImportedBy")
    private String importedBy;
    @Column(name = "Status")
    private String status;
}
