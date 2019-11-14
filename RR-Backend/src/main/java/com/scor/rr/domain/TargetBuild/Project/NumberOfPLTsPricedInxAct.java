package com.scor.rr.domain.TargetBuild.Project;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "NumberOfPLTsPricedInxAct", schema = "tb")
@AllArgsConstructor
public class NumberOfPLTsPricedInxAct extends NumberOfEntityForProject {
}
