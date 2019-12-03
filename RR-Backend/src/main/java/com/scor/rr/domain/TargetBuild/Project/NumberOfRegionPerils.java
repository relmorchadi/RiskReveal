package com.scor.rr.domain.TargetBuild.Project;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "NumberOfRegionPerils")
@AllArgsConstructor
public class NumberOfRegionPerils extends NumberOfEntityForProject {
    public NumberOfRegionPerils(Long projectId, int count) {
        super(projectId, count);
    }
}
