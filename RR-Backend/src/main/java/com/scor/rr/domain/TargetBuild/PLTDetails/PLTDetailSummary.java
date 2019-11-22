package com.scor.rr.domain.TargetBuild.PLTDetails;


import com.scor.rr.domain.TargetBuild.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "PLTDetailSummary", schema = "tb")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PLTDetailSummary {

    @Id
    @Column(name = "pltId")
    private Integer pltId;

    @Column(name = "pltName")
    private String pltName;

    @Column(name = "pltType")
    private String pltType;

    @Column(name = "pltStatus", length = 25)
    private String pltStatus;

    @Column(name = "groupedPlt")
    private Boolean groupedPlt;

    @Column(name = "grain")
    private String grain;

    @Column(name = "xActPublication")
    private Boolean xActPublication;

    @Column(name = "xActPriced")
    private Boolean xActPriced;

    @Column(name = "arcPublication")
    private Boolean arcPublication;

    @Column(name = "perilGroupCode")
    private String perilGroupCode;

    @Column(name = "regionPerilCode")
    private String regionPerilCode;

    @Column(name = "regionPerilDesc")
    private String regionPerilDesc;

    @Column(name = "minimumGrainRPCode")
    private String minimumGrainRPCode;

    @Column(name = "sourceFinancialPerspective")
    private String sourceFinancialPerspective;

    @Column(name = "targetRAPCode")
    private String targetRAPCode;

    @Column(name = "targetRAPDesc")
    private String targetRAPDesc;

    @Column(name = "vendorSystem")
    private String vendorSystem;

    @Column(name = "modellingDataSource")
    private String modellingDataSource;

    @Column(name = "sourceAnalysisId")
    private Integer sourceAnalysisId;

    @Column(name = "sourceAnalysisName")
    private String sourceAnalysisName;

    @Column(name = "sourceAnalysisDescription")
    private String sourceAnalysisDescription;

    @Column(name = "defaultOccurenceBasis")
    private String defaultOccurenceBasis;

    @Column(name = "occurenceBasis")
    private String occurenceBasis;

    @Column(name = "baseAdjustment")
    private Boolean baseAdjustment;

    @Column(name = "defaultAdjustment")
    private Boolean defaultAdjustment;

    @Column(name = "clientAdjustment")
    private Boolean clientAdjustment;

    @Column(name = "projectId")
    private Integer projectId;

    @Column(name = "projectName")
    private String projectName;

    @Column(name = "projectDescription")
    private String projectDescription;

    @Column(name = "projectType", length = 8)
    private String projectType;

    @Column(name = "assignedTo")
    private String assignedTo;

    @Column(name = "projectCreatedDate")
    private Date projectCreatedDate;

    @Column(name = "projectCreatedBy")
    private String projectCreatedBy;

    @Column(name = "masterProjectId")
    private Integer masterProjectId;

    @Column(name = "masterProjectName")
    private String masterProjectName;

    @Column(name = "masterProjectDescription")
    private String masterProjectDescription;

    @Column(name = "masterWorkspace")
    private String masterWorkspace;

    @Column(name = "masterClient")
    private String masterClient;

    @Column(name = "clonedPlt")
    private Boolean clonedPlt;

    @Column(name = "clonedSourcePlt")
    private Integer clonedSourcePlt;

    @Column(name = "clonedSourceProject")
    private Integer clonedSourceProject;

    @Column(name = "clonedSourceWorkspace")
    private String clonedSourceWorkspace;

    @Column(name = "pltCcy")
    private String pltCcy;

    @Column(name = "aal")
    private Integer aal;

    @Column(name = "cov")
    private Integer cov;

    @Column(name = "stdDev")
    private Integer stdDev;

    @Column(name = "oep10")
    private Integer oep10;

    @Column(name = "oep50")
    private Integer oep50;

    @Column(name = "oep100")
    private Integer oep100;

    @Column(name = "oep250")
    private Integer oep250;

    @Column(name = "oep500")
    private Integer oep500;

    @Column(name = "oep1000")
    private Integer oep1000;

    @Column(name = "aep10")
    private Integer aep10;

    @Column(name = "aep50")
    private Integer aep50;

    @Column(name = "aep100")
    private Integer aep100;

    @Column(name = "aep250")
    private Integer aep250;

    @Column(name = "aep500")
    private Integer aep500;

    @Column(name = "aep1000")
    private Integer aep1000;

    @Column(name = "createdDate")
    private Date createdDate;

    @Column(name = "xActPublicationDate")
    private Date xActPublicationDate;

    @Column(name = "publishedBy")
    private String publishedBy;

    @Column(name = "archived")
    private Boolean archived;

    @Column(name = "archivedDate")
    private Date archivedDate;

    @Column(name = "pureId")
    private Integer pureId;

    @Column(name = "pureName")
    private String pureName;

    @Column(name = "lossTableType")
    private String lossTableType;

    @Column(name = "lossTableId")
    private Integer lossTableId;

    @Column(name = "inuringPackageId")
    private Integer inuringPackageId;

    @Column(name = "inuringPackageName")
    private String inuringPackageName;

    @Column(name = "inuringPackageDescription")
    private String inuringPackageDescription;

    @Column(name = "inuringPackageStatus")
    private Integer inuringPackageStatus;

    @Column(name = "inuringCreatedOn")
    private Date inuringCreatedOn;

    @Column(name = "inuringCreatedBy")
    private Integer inuringCreatedBy;

    @Column(name = "inuringLastModifiedOn")
    private Date inuringLastModifiedOn;

    @Column(name = "inuringLastModifiedBy")
    private Integer inuringLastModifiedBy;

    @Transient
    private Set<Tag> tags;
}
