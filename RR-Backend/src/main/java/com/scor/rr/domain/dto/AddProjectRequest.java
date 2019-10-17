package com.scor.rr.domain.dto;

import com.scor.rr.domain.Project;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddProjectRequest {

    public Project project;
    public String wsId;
    public String id;
    public Integer uwYear;
}
