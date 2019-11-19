package com.scor.rr.domain.TargetBuild.Project;

import lombok.AllArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "AccumulatedProject", schema = "tb")
@AllArgsConstructor
public class AccumulatedProject extends NumberOfEntityForProject {
}
