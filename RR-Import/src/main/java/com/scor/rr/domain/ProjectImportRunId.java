package com.scor.rr.domain;

import com.scor.rr.domain.enums.TrackingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "ProjectImportRun")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectImportRunId {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ProjectImportRunId")
    private int projectImportRunId;
    @Column(name = "Entity")
    private int entity;
    @Column(name = "RunId")
    private int runId;
    @Column(name = "Status")
    private TrackingStatus status;
    @Column(name = "StartDate")
    private LocalDate startDate;
    @Column(name = "EndDate")
    private LocalDate endDate;
    @Column(name = "LossImportEndDate")
    private LocalDate lossImportEndDate;
    @Column(name = "ImportedBy")
    private String importedBy;
    @Column(name = "SourceConfigVendor")
    private String sourceConfigVendor;
}
