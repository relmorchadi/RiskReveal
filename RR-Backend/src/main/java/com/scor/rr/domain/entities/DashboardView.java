package com.scor.rr.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "vw_Dashboard")
public class DashboardView {

    @Id
    private Long id;

    @Column(length = 30)
    private String carRequestId;

    private Date lastUpdateDate;

    private Integer lastUpdatedBy;

    @Column(length = 15)
    private String requestedByFirstName;

    @Column(length = 15)
    private String requestedByLastName;

    @Column(length = 31)
    private String requestedByFullName;

    private Date creationDate;

    @Column(length = 125)
    private String cedantName;

    private String uwAnalysis;

    private String facSource;

    @Column(length = 25)
    private String Lob;

    @Column(length = 25)
    private String businessType;

    private Integer endorsementNumber;

    @Column(length = 25)
    private String sector;

    @Column(length = 25)
    private String subsidiary;

    private Integer uwYear;

    /*uwAnalysisContractDate*/

    @Column(length = 31)
    private String assignedAnalyst;

    private Integer uwOrder;

    @Column(length = 50)
    private String label;

    @Column(length = 25)
    private String facNumber;

    private Long projectId;

    @Column(length = 25)
    private String contractId;

    @Column(length = 50)
    private String contractName;

    /* Insured ??*/

    @Column(length = 10)
    private String carStatus;

    /* PublishedDate ??*/

    /* PublishedBy ??*/
}
