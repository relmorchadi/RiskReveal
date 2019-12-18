package com.scor.rr.domain.entities.Project;

import lombok.AllArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "vw_NumberOfRegionPerils")
@AllArgsConstructor
public class NumberOfRegionPerils extends NumberOfEntityForProject {
    public NumberOfRegionPerils(Long projectId, int count) {
        super(projectId, count);
    }
}
