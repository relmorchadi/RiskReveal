package com.scor.rr.domain.TargetBuild.Project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "NumberOfPLTsPublishedInxAct", schema = "tb")
@Data
@AllArgsConstructor
public class NumberOfPLTsPublishedInxAct extends NumberOfEntityForProject {
    public NumberOfPLTsPublishedInxAct(Long projectId, int count) {
        super(projectId, count);
    }
}
