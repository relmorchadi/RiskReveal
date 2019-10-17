package com.scor.rr.domain.riskLink;


import com.scor.rr.domain.RdmAnalysisBasic;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;

@Data
@Entity
@Table(name = "RLAnalysis")
public class RLAnalysis {

    @Id
    @GeneratedValue
    private Integer rlAnalysisId;
    private Integer entity;
    private Integer rlModelDataSourceId;
    private Integer projectId;
    private BigInteger rdmId;
    private String rdmName;
    private BigInteger analysisId;
    private String analysisName;
    private String analysisDescription;
    private String defaultGrain;
    private String exposureType;
    private Integer exposureTypeCode;
    private String edmNameSourceLink;
    private BigInteger exposureId;
    private String analysisCurrency;
    private Number rlExchangeRate;
    private Integer typeCode;
    private String analysisType;
    @Temporal(TemporalType.TIMESTAMP)
    private Date runDate;
    private String region;
    private String peril;
    private String rpCode;
    private String subPeril;
    private String lossAmplification;
    private BigInteger rlAnalysisStatus;
    private Integer analysisMode;
    private Integer engineTypeCode;
    private String engineType;
    private String engineVersion;
    private String engineVersionMajor;
    private String profileName;
    private String profileKey;
    private String purePremium;
    private String exposureTIV;

    @OneToOne
    private RlAnalysisScanStatus rlAnalysisScanStatus;
    @OneToOne
    private RlSourceResult rlSourceResult;

    public RLAnalysis(RdmAnalysisBasic rdmAnalysisBasic, RlModelDataSource rdm) {
        this.entity=1;
        this.rlModelDataSourceId= rdm.getRlModelDataSourceId();
        this.projectId= rdm.getProjectId();
        this.rdmId= BigInteger.valueOf(rdm.getRlModelDataSourceId());
        this.rdmName=rdm.getName();
        this.analysisId= BigInteger.valueOf(rdmAnalysisBasic.getAnalysisId());
        this.analysisName= rdmAnalysisBasic.getAnalysisName();
        this.analysisDescription= rdmAnalysisBasic.getDescription();
        this.defaultGrain= null; // TO Check
        this.exposureType= null;
        this.exposureTypeCode= null;
        this.edmNameSourceLink= null;
        this.exposureId= null;
        this.analysisCurrency = rdmAnalysisBasic.getAnalysisCurrency();
        this.rlExchangeRate= null;
        this.typeCode=null;
        this.analysisType=rdmAnalysisBasic.getTypeName(); // Not sure
        this.runDate= new Date();
        this.region = rdmAnalysisBasic.getRegion();
        this.peril = rdmAnalysisBasic.getPeril();
        this.subPeril = rdmAnalysisBasic.getSubPeril();
        this.lossAmplification = rdmAnalysisBasic.getLossAmplification();
//        this.rlAnalysisStatus = rdmAnalysisBasic.getStatusDescription();
        this.analysisMode= null;
        this.engineTypeCode=null;
        this.engineType = rdmAnalysisBasic.getEngineType();
        this.engineVersion = rdmAnalysisBasic.getEngineVersion();
        this.engineVersionMajor= null;
        this.profileName= null;
        this.profileKey= null;
        this.purePremium= null;
        this.exposureTIV= null;

    }
}
