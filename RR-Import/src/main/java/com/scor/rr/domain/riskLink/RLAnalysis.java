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
@Table(name = "RLAnalysis")
@AllArgsConstructor
@NoArgsConstructor
public class RLAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rlAnalysisId;
    private Integer entity;
    private Long rlModelDataSourceId;
    private Long projectId;
    private Long rdmId;
    private String rdmName;
    @Column(name = "analysisId")
    private Long rlId;
    private String analysisName;
    private String analysisDescription;
    private String defaultGrain;
    private String exposureType;
    private Integer exposureTypeCode;
    private String edmNameSourceLink;
    private Long exposureId;
    private String analysisCurrency;
    private Number rlExchangeRate;
    private Integer typeCode;
    private String analysisType;
    @Temporal(TemporalType.TIMESTAMP)
    private Date runDate;
    private String region;
    private String peril;
    // TODO ; Review with shaun
    private String geoCode;
    private String rpCode;
    private String subPeril;
    private String lossAmplification;
    private Integer analysisMode;
    private Integer engineTypeCode;
    private String engineType;
    private String engineVersion;
    private String engineVersionMajor;
    private String profileName;
    private String profileKey;
    private Double purePremium;
    private Double exposureTIV;
    //TODO : review with shaun
    private String description;
    //TODO : review with shaun
    private String defaultOccurrenceBasis;

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
