package com.scor.rr.domain.TargetBuild;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "ProjectConfigurationForeWriterDivision", schema = "dr")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ProjectConfigurationForeWriterDivision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProjectConfigurationForeWriterDivisionId")
    private Integer projectConfigurationForeWriterDivisionId;

    @Column(name = "Entity")
    private Integer entity;

    @Column(name = "ProjectConfigurationForeWriterContractId")
    private Integer projectConfigurationForeWriterContractId;

    @Column(name = "DivisionNumber", length = 15)
    private String divisionNumber;

    @Column(name = "PrincipalDivision", length = 20)
    private String principalDivision;

    @Column(name = "LineOfBusiness", length = 25)
    private String lineOfBusiness;

    @Column(name = "CoverageType", length = 5)
    private String coverageType;

    @Column(name = "Currency", length = 4)
    private String currency;

}
