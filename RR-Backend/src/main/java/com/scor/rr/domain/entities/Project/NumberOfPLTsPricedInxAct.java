package com.scor.rr.domain.entities.Project;

import lombok.AllArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "NumberOfPLTsPricedInxAct")
@AllArgsConstructor
public class NumberOfPLTsPricedInxAct extends NumberOfEntityForProject {
    public NumberOfPLTsPricedInxAct(Long projectId, int count) {
        super(projectId, count);
    }
}
