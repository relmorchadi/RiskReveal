package com.scor.rr.domain.entities.Project;

import lombok.AllArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "vw_NumberOfPLTsPricedInForeWriter")
@AllArgsConstructor
public class NumberOfPLTsPricedInForeWriter extends NumberOfEntityForProject {
    public NumberOfPLTsPricedInForeWriter(Long projectId, int count) {
        super(projectId, count);
    }

}
