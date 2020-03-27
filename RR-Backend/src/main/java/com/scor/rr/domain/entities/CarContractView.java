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
    @Column(name = "id")
    private Long id;
    @Column(name = "carId")
    private String carId;
    @Column(name = "projectId")
    private Long projectId;
    @Column(name = "carName")
    private String carName;
    @Column(name = "contractId")
    private String contractId;
    @Column(name = "contractName")
    private String contractName;
    @Column(name = "uwAnalysis")
    private String uwAnalysis;
    @Column(name = "uwYear")
    private Long uwYear;
    @Column(name = "businessType")
    private String businessType;
    @Column(name = "lob")
    private String lob;
    @Column(name = "client")
    private String client;
    @Column(name = "subsidiary")
    private String subsidiary;
    @Column(name = "sector")
    private String sector;
}
