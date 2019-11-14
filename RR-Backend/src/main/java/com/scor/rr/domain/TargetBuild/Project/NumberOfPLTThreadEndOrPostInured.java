package com.scor.rr.domain.TargetBuild.Project;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "NumberOfPLTThreadEndOrPostInured", schema = "tb")
@AllArgsConstructor
public class NumberOfPLTThreadEndOrPostInured extends NumberOfEntityForProject {
}
