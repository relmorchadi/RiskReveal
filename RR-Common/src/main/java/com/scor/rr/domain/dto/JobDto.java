package com.scor.rr.domain.dto;

import com.scor.rr.domain.enums.JobStatus;
import com.scor.rr.domain.enums.JobType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobDto {

    private Long jobId;
    private Long submittedByUser;
    private Date submittedDate;
    private Integer priority;
    private String status;
    private Date startedDate;
    private Date finishedDate;
    private JobType jobTypeCode;

    private List<TaskDto> tasks;

    private Long percent;

    public JobDto(Long jobId, Long submittedByUser, Date submittedDate, Integer priority, String status, Date startedDate, Date finishedDate, JobType jobTypeCode, List<TaskDto> tasks) {
        this.jobId = jobId;
        this.submittedByUser = submittedByUser;
        this.submittedDate = submittedDate;
        this.priority = priority;
        this.status = status;
        this.startedDate = startedDate;
        this.finishedDate = finishedDate;
        this.jobTypeCode = jobTypeCode;
        this.tasks = tasks;
        if (tasks != null)
            this.percent = tasks.stream().filter(t -> t.getStatus().equalsIgnoreCase(JobStatus.SUCCEEDED.getCode())).count() /
                    tasks.size();
    }
}
