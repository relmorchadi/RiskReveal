package com.scor.rr.domain.TargetBuild.Project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ProjectConfigurationForeWriterContract")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ProjectConfigurationForeWriterContract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProjectConfigurationForeWriterContractId")
    private Long projectConfigurationForeWriterContractId;

    @Column(name = "Entity")
    private Integer entity;

    @Column(name = "ProjectConfigurationForeWriterId")
    private Long projectConfigurationForeWriterId;

    @Column(name = "ContractID", length = 25)
    private String contractId;

    @Column(name = "FacNumber", length = 25)
    private String facNumber;

    @Column(name = "UWYear")
    private Integer uwYear;

    @Column(name = "UWOrder")
    private Integer uwOrder;

    @Column(name = "EndorsementNumber")
    private Integer endorsementNumber;

    @Column(name = "ContractName", length = 50)
    private String contractName;

    @Column(name = "BusinessType", length = 25)
    private String businessType;

    @CreatedDate
    @Column(name = "Client", length = 125)
    private String client;

    @Column(name = "Subsidiary", length = 25)
    private String subsidiary;

    @Column(name = "LineOfBusiness", length = 25)
    private String lineOfBusiness;

    @Column(name = "Sector", length = 25)
    private String sector;


    //TODO: implement AuditorAware to persist createDate, CreatedBy, ...
}
