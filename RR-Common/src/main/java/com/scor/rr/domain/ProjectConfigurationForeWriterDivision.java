package com.scor.rr.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "ProjectConfigurationForeWriterDivision")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ProjectConfigurationForeWriterDivision {

    public ProjectConfigurationForeWriterDivision(Integer entity, Long projectConfigurationForeWriterContractId, String divisionNumber, String principalDivision, String lineOfBusiness, String coverageType, String currency) {
        this.entity = entity;
        this.projectConfigurationForeWriterContractId = projectConfigurationForeWriterContractId;
        this.divisionNumber = divisionNumber;
        this.principalDivision = principalDivision;
        this.lineOfBusiness = lineOfBusiness;
        this.coverageType = coverageType;
        this.currency = currency;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProjectConfigurationForeWriterDivisionId")
    private Long projectConfigurationForeWriterDivisionId;

    @Column(name = "Entity")
    private Integer entity;

    @Column(name = "ProjectConfigurationForeWriterContractId")
    private Long projectConfigurationForeWriterContractId;

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
