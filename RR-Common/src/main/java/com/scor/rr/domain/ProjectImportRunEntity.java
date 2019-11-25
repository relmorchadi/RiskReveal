package com.scor.rr.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@Table(name = "ProjectImportRun", schema = "tb")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectImportRunEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProjectImportRunId")
    private Long projectImportRunId;

    @Column(name = "Entity")
    private Integer entity;

    @Column(name = "RunId")
    private Integer runId;

    // @TODO Aymane algin in import Module
    @Column(name = "ProjectId")
    private Long projectId;

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
