package com.scor.rr.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity(name = "vw_GetForeWriterContractInformation")
@Table
@AllArgsConstructor
@NoArgsConstructor
public class CarContractView {

    @Id
    @Column(name = "Id")
    private Long id;
    @Column(name = "CarId")
    private String carId;
    @Column(name = "ProjectId")
    private Long projectId;
    @Column(name = "CarName")
    private String carName;
    @Column(name = "CarStatus")
    private String carStatus;
    @Column(name = "ContractId")
    private String contractId;
    @Column(name = "ContractName")
    private String contractName;
    @Column(name = "UwYear")
    private Long uwYear;
    @Column(name = "UwAnalysis")
    private String uwAnalysis;
    @Column(name = "BusinessType")
    private String businessType;
    @Column(name = "Lob")
    private String lob;
    @Column(name = "Client")
    private String client;
    @Column(name = "Subsidiary")
    private String subsidiary;
    @Column(name = "Sector")
    private String sector;
}
