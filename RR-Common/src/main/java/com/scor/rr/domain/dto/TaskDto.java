package com.scor.rr.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    private String TaskType;
    private String status;
    private String priority;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date submittedDate;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date startedDate;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date finishedDate;
    private Integer percent;

    private Integer division;
    private String name;
    private Long projectId;

    /**
     * Analysis Only Parameters
     **/

    private String financialPerspective;
    private String targetRapCode;
}
