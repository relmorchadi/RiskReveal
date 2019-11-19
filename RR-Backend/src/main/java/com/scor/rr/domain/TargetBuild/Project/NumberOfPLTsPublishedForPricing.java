package com.scor.rr.domain.TargetBuild.Project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "NumberOfPLTsPublishedForPricing", schema = "tb")
@Data
public class NumberOfPLTsPublishedForPricing extends NumberOfEntityForProject {
}
