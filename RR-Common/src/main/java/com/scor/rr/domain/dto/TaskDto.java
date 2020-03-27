package com.scor.rr.domain.dto;

import com.scor.rr.domain.JobEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {

    private Long taskId;
    private Long jobExecutionId;
    private String status;
    private Integer priority;
    private Date submittedDate;
    private Date startedDate;
    private Date finishedDate;
    private Integer percent;
}
