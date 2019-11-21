package com.scor.rr.domain.TargetBuild.Project;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ProjectCardView", schema = "tb")
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
}
