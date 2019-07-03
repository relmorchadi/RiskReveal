package com.scor.rr.domain.dto;

import com.scor.rr.domain.Project;
import com.scor.rr.domain.UserTag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddProjectRequest {

    public Project project;
    public String workspaceId;
    public String id;
    public Integer uwYear;
}
