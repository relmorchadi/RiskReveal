package com.scor.rr.domain.TargetBuild.Project;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@MappedSuperclass
@Data
@NoArgsConstructor
public class NumberOfEntityForProject {

    @Id
    @Column(name = "ProjectId")
    private Long projectId;

    @Column(name = "count")
    private Integer count;

    public NumberOfEntityForProject(Long projectId, Integer count) {
        this.projectId = projectId;
        this.count = count;
    }

}
