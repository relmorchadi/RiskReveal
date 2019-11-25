package com.scor.rr.domain;

import com.scor.rr.domain.enums.TrackingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@Table(name = "ProjectImportRun")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectImportRun {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ProjectImportRun")
    private Long projectImportRunId;
    @Column(name = "RREntity")
    private int entity;
    @Column(name = "RunId")
    private int runId;
    @Column(name = "Status")
    private TrackingStatus status;
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

    @ManyToOne
    private ProjectEntity projectEntity;
}
