package com.scor.rr.domain.TargetBuild;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ProjectImportRun", schema = "tb")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectImportRun {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProjectImportRunId")
    private Integer projectImportRunId;

    @Column(name = "Entity")
    private Integer entity;

    @Column(name = "RunId")
    private Integer runId;

    @Column(name = "ProjectId")
    private Integer projectId;

    @Column(name = "Status")
    private String status;

    @Column(name = "StartDate")
    private Date startDate;

    @Column(name = "EndDate")
    private Date endDate;

    @Column(name = "LossImportEndDate")
    private Date lossImportEndDate;

    @Column(name = "ImportedBy")
    private String importedBy;

    @Column(name = "SourceConfigVendor")
    private String sourceConfigVendor;

}
