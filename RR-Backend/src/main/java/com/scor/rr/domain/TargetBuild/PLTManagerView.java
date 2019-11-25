package com.scor.rr.domain.TargetBuild;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PltManagerView", schema = "tb")
public class PLTManagerView {

    @Id
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
    private Integer analysisId;

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
    private Integer projectId;

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


}
