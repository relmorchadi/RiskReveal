package com.scor.rr.domain.entities.plt;

import com.scor.rr.domain.entities.references.User;
import com.scor.rr.domain.entities.workspace.Project;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

/**
 * The persistent class for the ProjectImportRun database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "ProjectImportRun")
@Data
public class ProjectImportRun {
    @Id
    @Column(name = "ProjectImportRunId")
    private String projectImportRunId;
    @Column(name = "RunId")
    private Integer runId;
    @Column(name = "Status")
    private String status;
    @Column(name = "StartDate")
    private Date startDate;
    @Column(name = "EndDate")
    private Date endDate;
    @Column(name = "LossImportEndDate")
    private Date lossImportEndDate;
    @Column(name = "SourceConfigVendor")
    private String sourceConfigVendor;
    @Column(name = "projectImportSourceConfigId")
    private String projectImportSourceConfigId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProjectId")
    private Project project;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ImportedBy")
    private User importedBy;
}
