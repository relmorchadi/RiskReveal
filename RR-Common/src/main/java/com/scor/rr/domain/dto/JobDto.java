package com.scor.rr.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.scor.rr.domain.enums.JobStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobDto {

    private Long jobId;
    private String submittedByUser;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date submittedDate;
    private String priority;
    private String status;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date startedDate;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date finishedDate;
    private String jobTypeCode;
    private Long workspaceId;

    private List<TaskDto> tasks;
    private Long percent;

    /**
     * Import Job Parameters
     **/

    private Integer uwYear;
    private Long projectId;
    private String clientName;
    private String contractCode;
    private String workspaceName;

    public void calculatePercentage() {
        if (tasks != null)
            this.percent = (tasks.stream().filter(t -> t.getStatus().equalsIgnoreCase(JobStatus.SUCCEEDED.getCode())).count() /
                    tasks.size()) * 100;
    }

    public void addTask(TaskDto taskdto) {
        if (tasks == null) {
            tasks = new ArrayList<>();
            tasks.add(taskdto);
        } else
            tasks.add(taskdto);
    }
}
