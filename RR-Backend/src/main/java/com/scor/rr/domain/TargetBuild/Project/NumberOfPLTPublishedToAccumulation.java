package com.scor.rr.domain.TargetBuild.Project;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "NumberOfPLTPublishedToAccumulation")
@AllArgsConstructor
public class NumberOfPLTPublishedToAccumulation extends NumberOfEntityForProject {
    public NumberOfPLTPublishedToAccumulation(Long projectId, int count) {
        super(projectId, count);
    }
}
