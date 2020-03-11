package com.scor.rr.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "BATCH_JOB_EXECUTION", schema = "dbo")
public class JobExecutionEntity {

    @Id
    @Column(name = "Job_Execution_Id")
    private Long jobExecutionId;
    @Column(name = "version")
    private Long version;
    @Column(name = "Job_Instance_Id")
    private Long instanceId;
    @Column(name = "Created_Date")
    private Date createdDate;
    @Column(name = "Start_Date")
    private Date startDate;
    @Column(name = "End_Date")
    private Date endDate;
    @Column(name = "Status")
    private String status;
    @Column(name = "Exit_Code")
    private String exitCode;
    @Column(name = "Exit_Message")
    private String exitMessage;
    @Column(name = "Last_Updated")
    private Date lastUpdated;
    @Column(name = "Job_Configuration_Location")
    private String jobConfigurationLocation;
}
