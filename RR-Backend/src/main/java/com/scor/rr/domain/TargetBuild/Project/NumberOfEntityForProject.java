package com.scor.rr.domain.TargetBuild.Project;

import lombok.Data;

import javax.persistence.*;

@MappedSuperclass
@Data
public abstract class NumberOfEntityForProject {

    @Id
    @Column(name = "ProjectId")
    private Long projectId;

    @Column(name = "count")
    private Integer count;

}
