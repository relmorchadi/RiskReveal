package com.scor.rr.domain.TargetBuild.Project;

import lombok.AllArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "AccumulatedProject")
@AllArgsConstructor
public class AccumulatedProject extends NumberOfEntityForProject {
    public AccumulatedProject(Long projectId, int count) {
        super(projectId, count);
    }
}
