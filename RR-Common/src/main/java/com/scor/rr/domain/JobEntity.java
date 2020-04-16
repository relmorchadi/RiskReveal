package com.scor.rr.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.scor.rr.domain.enums.JobType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

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
    @Column(name = "SubmittedDate")
    private Date submittedDate;
    @Column(name = "JobCode")
    private String jobCode;
    @Column(name = "Priority")
    private Integer priority;
    @Column(name = "Status")
    private String status;
    @Column(name = "StartedDate")
    private Date startedDate;
    @Column(name = "FinishedDate")
    private Date finishedDate;
    @Column(name = "JobTypeCode")
    private JobType jobTypeCode;
    @Column(name = "JobTypeDesc")
    private String jobTypeDesc;
    @Column(name = "UserId")
    private Long userId;


    @OneToMany(mappedBy = "job", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<TaskEntity> tasks;

    @OneToMany(mappedBy = "jobId", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<JobParamsEntity> params;
}
