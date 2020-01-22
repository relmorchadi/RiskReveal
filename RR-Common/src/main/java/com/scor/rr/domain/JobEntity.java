package com.scor.rr.domain;

import com.scor.rr.domain.enums.JobStatus;
import com.scor.rr.domain.enums.JobType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "Job")
@AllArgsConstructor
@NoArgsConstructor
public class JobEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "JobId")
    private Long jobId;
    @Column(name = "SubmittedByUser")
    private Long submittedByUser;
    @Column(name = "SubmittedDate")
    private Timestamp submittedDate;
    @Column(name = "JobCode")
    private String jobCode;
    @Column(name = "JobParams")
    private String jobParams;
    @Column(name = "Priority")
    private Integer priority;
    @Column(name = "Status")
    private JobStatus status;
    @Column(name = "StartedDate")
    private Timestamp startedDate;
    @Column(name = "FinishedDate")
    private Timestamp finishedDate;
    @Column(name = "JobTypeCode")
    private JobType jobTypeCode;
    @Column(name = "JobTypeDesc")
    private String jobTypeDesc;}
