package com.scor.rr.domain.TargetBuild.Project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "NumberOfPLTsPricedInForeWriter", schema = "dr")
@AllArgsConstructor
@NoArgsConstructor
public class NumberOfPLTsPricedInForeWriter extends NumberOfEntityForProject {

}
