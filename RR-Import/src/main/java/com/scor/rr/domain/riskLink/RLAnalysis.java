package com.scor.rr.domain.riskLink;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.scor.rr.domain.RdmAnalysisBasic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "ZZ_RLModelAnalysis")
@AllArgsConstructor
@NoArgsConstructor
public class RLAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RLModelAnalysisId")
    private Long rlAnalysisId;
    @Column(name = "Entity")
    private Integer entity;
    @Column(name = "RLDataSourceId")
    private Long rlModelDataSourceId;
    @Column(name = "ProjectId")
    private Long projectId;
    @Column(name = "RDMId")
    private Long rdmId;
    @Column(name = "RDMName")
    private String rdmName;
    @Column(name = "AnalysisId")
    private Long rlId;
    @Column(name = "AnalysisName")
    private String analysisName;
    @Column(name = "AnalysisDescription")
    private String analysisDescription;
    @Column(name = "DefaultGrain")
    private String defaultGrain;
    @Column(name = "ExposureType")
    private String exposureType;
    @Column(name = "ExposureTypeCode")
    private Integer exposureTypeCode;
    @Column(name = "EDMNameSourceLink")
    private String edmNameSourceLink;
    @Column(name = "ExposureId")
    private Long exposureId;
    @Column(name = "AnalysisCurrency")
    private String analysisCurrency;
    @Column(name = "RLExchangeRate")
    private Number rlExchangeRate;
    @Column(name = "TypeCode")
    private Integer typeCode;
    @Column(name = "AnalysisType")
    private String analysisType;
    @Column(name = "RunDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date runDate;
    @Column(name = "Region")
    private String region;
    @Column(name = "Peril")
    private String peril;
    @Column(name = "GeoCode")
    // TODO ; Review with shaun
    private String geoCode;
    @Column(name = "RegionPerilCode")
    private String rpCode;
    @Column(name = "SubPeril")
    private String subPeril;
    @Column(name = "LossAmplification")
    private String lossAmplification;
    @Column(name = "AnalysisMode")
    private Integer analysisMode;
    @Column(name = "EngineTypeCode")
    private Integer engineTypeCode;
    @Column(name = "EngineType")
    private String engineType;
    @Column(name = "EngineVersion")
    private String engineVersion;
    @Column(name = "EngineVersionMajor")
    private String engineVersionMajor;
    @Column(name = "ProfileName")
    private String profileName;
    @Column(name = "ProfileKey")
    private String profileKey;
    @Column(name = "PurePremium")
    private Double purePremium;
    @Column(name = "ExposureTIV")
    private Double exposureTIV;
    @Column(name = "Description")
    //TODO : review with shaun
    private String description;
    @Column(name = "DefaultOccurrenceBasis")
    //TODO : review with shaun
    private String defaultOccurrenceBasis;
    @Column(name = "GroupType")
    private String groupType;
    @Column(name = "Cedant")
    private String cedant;
    @Column(name = "LOB")
    private String lob;
    @Column(name = "User1")
    private String user1;
    @Column(name = "User2")
    private String user2;
    @Column(name = "User3")
    private String user3;
    @Column(name = "User4")
    private String user4;
    @Column(name = "RegionName")
    private String regionName;
    @Column(name = "StatusDescription")
    private String statusDescription;
    @Column(name = "Grouping")
    private String grouping;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "analysisScanStatus")
    @JsonBackReference
    private RLAnalysisScanStatus rlAnalysisScanStatus;

    @OneToMany(mappedBy = "rlAnalysis", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference
    private List<RLImportSelection> RLImportSelection;

    @OneToMany(mappedBy = "rLAnalysisId", orphanRemoval = true, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonBackReference
    private List<RLSourceEpHeader> rlSourceEpHeaders;

    @OneToMany(mappedBy = "rlAnalysis", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonBackReference
    private List<RLAnalysisProfileRegion> rlAnalysisProfileRegions;

    public RLAnalysis(RdmAnalysisBasic rdmAnalysisBasic, RLModelDataSource rdm) {
        this.entity = 1;
        this.rlModelDataSourceId = rdm.getRlModelDataSourceId();
        this.projectId = rdm.getProjectId();
        this.rdmId = Long.valueOf(rdm.getRlId());
        this.rdmName = rdm.getName();
        this.rlId = rdmAnalysisBasic.getAnalysisId();
        this.analysisName = rdmAnalysisBasic.getAnalysisName();
        this.analysisDescription = rdmAnalysisBasic.getDescription();
        this.defaultGrain = null; // TO Check
        this.exposureType = null;
        this.exposureTypeCode = null;
        this.edmNameSourceLink = null;
        this.exposureId = null;
        this.analysisCurrency = rdmAnalysisBasic.getAnalysisCurrency();
        this.rlExchangeRate = null;
        this.typeCode = null;
        this.analysisType = rdmAnalysisBasic.getTypeName(); // Not sure
        this.runDate = new Date();
        this.region = rdmAnalysisBasic.getRegion();
        this.peril = rdmAnalysisBasic.getPeril();
        this.subPeril = rdmAnalysisBasic.getSubPeril();
        this.lossAmplification = rdmAnalysisBasic.getLossAmplification();
//        this.rlAnalysisStatus = rdmAnalysisBasic.getStatusDescription();
        this.analysisMode = null;
        this.engineTypeCode = null;
        this.engineType = rdmAnalysisBasic.getEngineType();
        this.engineVersion = rdmAnalysisBasic.getEngineVersion();
        this.engineVersionMajor = null;
        this.profileName = null;
        this.profileKey = null;
        this.purePremium = null;
        this.exposureTIV = null;

    }
}
