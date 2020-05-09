package com.scor.rr.domain.entities.PLTManager;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupedPLTs {

    @Column(name = "pltId")
    private Long pltId;

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

    @Column(name = "minimumGrainRPDescription")
    private String minimumGrainRPDescription;

    @Column(name = "financialPerspective")
    private String financialPerspective;

    @Column(name = "targetRAPCode")
    private String targetRAPCode;

    @Column(name = "targetRAPDesc")
    private String targetRAPDesc;

    @Column(name = "rootRegionPeril")
    private String rootRegionPeril;

    @Column(name = "vendorSystem")
    private String vendorSystem;

    @Column(name = "modellingDataSource")
    private String modellingDataSource;

    @Column(name = "analysisId")
    private Long analysisId;

    @Column(name = "analysisName")
    private String analysisName;

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
    private Long projectId;

    @Column(name = "projectName")
    private String projectName;

    @Column(name = "workspaceContextCode")
    private String workspaceContextCode;

    @Column(name = "client")
    private String client;

    @Column(name = "uwYear")
    private Integer uwYear;

    @Column(name = "clonedPlt")
    private Boolean clonedPlt;

    @Column(name = "clonedSourcePlt")
    private Long clonedSourcePlt;

    @Column(name = "clonedSourceProject")
    private Long clonedSourceProject;

    @Column(name = "clonedSourceWorkspace")
    private Long clonedSourceWorkspace;

    @Column(name = "pltCcy")
    private String pltCcy;

    @Column(name = "aal")
    private Double aal;

    @Column(name = "cov")
    private Double cov;

    @Column(name = "stdDev")
    private Double stdDev;

    @Column(name = "oep10")
    private Double oep10;

    @Column(name = "oep50")
    private Double oep50;

    @Column(name = "oep100")
    private Double oep100;

    @Column(name = "oep250")
    private Double oep250;

    @Column(name = "oep500")
    private Double oep500;

    @Column(name = "oep1000")
    private Double oep1000;

    @Column(name = "aep10")
    private Double aep10;

    @Column(name = "aep50")
    private Double aep50;

    @Column(name = "aep100")
    private Double aep100;

    @Column(name = "aep250")
    private Double aep250;

    @Column(name = "aep500")
    private Double aep500;

    @Column(name = "aep1000")
    private Double aep1000;

    @Column(name = "createdDate")
    private Date createdDate;

    @Column(name = "importedBy")
    private String importedBy;

    @Column(name = "xActPublicationDate")
    private Date xActPublicationDate;

    @Column(name = "publishedBy")
    private String publishedBy;

    @Column(name = "archived")
    private Boolean archived;

    @Column(name = "archivedDate")
    private Date archivedDate;

    @Column(name = "deletedBy")
    private String deletedBy;

    @Column(name = "deletedDue")
    private String deletedDue;

    @Column(name = "deletedOn")
    private Date deletedOn;

    @Transient
    private Set<Tag> tags;

    @Transient
    private List<GroupedPLTs> threads;

    //ADDITIONAL FIELDS

    private Long targetRapId;
    private Long regionPerilId;

    private Long pureId;
    private Long threadId;
}
