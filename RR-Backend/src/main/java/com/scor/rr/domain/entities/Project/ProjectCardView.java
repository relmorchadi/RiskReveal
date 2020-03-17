package com.scor.rr.domain.entities.Project;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "vw_ProjectCard")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectCardView {

    @Id
    private Long projectId;

    private Long workspaceId;

    private Integer projectImportRunId;

    private String projectName;

    private String projectDescription;

    private String carRequestId;

    private String carRequestStatus;

    private String uwAnalysis;

    private Boolean masterFlag;

    private Boolean linkFlag;

    private Boolean publishFlag;

    private Boolean clonedFlag;

    private Boolean postInuredFlag;

    private Boolean mgaFlag;

    private String assignedTo;

    private Date creationDate;

    private Date receptionDate;

    private Date dueDate;

    private String createdBy;

    private Integer linkedSourceProjectId;

    private Integer clonedSourceProjectId;


    private Integer regionPerils;

    private Integer plts;

    private Integer publishedForPricingCount;

    private Integer finalPricing;

    private Integer accumulatedPlts;


    private Boolean importedFlag;

    private Boolean publishedForPricingFlag;

    private Boolean accumulatedFlag;

    @Column(name = "subsidiary", length = 25)
    private String subsidiary;

    @Column(name = "ledgerName")
    private String ledgerName;

    @Column(name = "subsidiaryName")
    private String subsidiaryName;

    @Column(name = "expiryDate")
    private String expiryDate;

    @Column(name = "inceptionDate")
    private String inceptionDate;

    @Column(name = "contractDatasource")
    private String contractDatasource;

    private String lastUpdatedBy;

    private Date lastUpdatedOn;

    @Transient
    private List<String> divisions;
}
