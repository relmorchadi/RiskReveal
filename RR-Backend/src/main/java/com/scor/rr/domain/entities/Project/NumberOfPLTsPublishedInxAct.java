package com.scor.rr.domain.entities.Project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "vw_NumberOfPLTsPublishedInxAct")
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class NumberOfPLTsPublishedInxAct extends NumberOfEntityForProject {
    public NumberOfPLTsPublishedInxAct(Long projectId, int count) {
        super(projectId, count);
    }
}
