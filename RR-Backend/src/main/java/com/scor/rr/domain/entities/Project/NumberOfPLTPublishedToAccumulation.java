package com.scor.rr.domain.entities.Project;

import lombok.AllArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "vw_NumberOfPLTPublishedToAccumulation")
@AllArgsConstructor
public class NumberOfPLTPublishedToAccumulation extends NumberOfEntityForProject {
    public NumberOfPLTPublishedToAccumulation(Long projectId, int count) {
        super(projectId, count);
    }
}
