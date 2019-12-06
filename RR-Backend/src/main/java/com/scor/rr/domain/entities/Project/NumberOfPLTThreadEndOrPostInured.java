package com.scor.rr.domain.entities.Project;

import lombok.AllArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "NumberOfPLTThreadEndOrPostInured")
@AllArgsConstructor
public class NumberOfPLTThreadEndOrPostInured extends NumberOfEntityForProject {
    public NumberOfPLTThreadEndOrPostInured(Long projectId, int count) {
        super(projectId, count);
    }
}
