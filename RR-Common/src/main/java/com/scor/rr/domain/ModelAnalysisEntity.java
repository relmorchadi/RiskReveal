package com.scor.rr.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;

@Entity
@Table(name = "ModelAnalysis")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModelAnalysisEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ModelAnalysisId")
    private Long rrAnalysisId;

    @Column(name = "Entity")
    private Integer entity;

    @Column(name = "ProjectId")
    private Long projectId;

    @Column(name = "ImportedDate")
    private Date importedDate;

    @Column(name = "CreationDate")
    private Date creationDate;

    @Column(name = "RunDate")
    private Date runDate;

    @Column(name = "ImportStatus")
    private String importStatus;

    @Column(name = "ProjectImportRunId")
    private Long projectImportRunId;

    @Column(name = "SourceModellingSystemInstance")
    private String sourceModellingSystemInstance;

    @Column(name = "SourceModellingVendor")
    private String sourceModellingVendor;

    @Column(name = "SourceModellingSystem")
    private String sourceModellingSystem;

    @Column(name = "SourceModellingSystemVersion")
    private String sourceModellingSystemVersion;

    @Column(name = "DataSourceId")
    private Long dataSourceId;

    @Column(name = "DataSourceName")
    private String dataSourceName;

    @Column(name = "FileName")
    private String fileName;

    @Column(name = "SourceAnalysisId")
    private Long analysisId;

    @Column(name = "SourceAnalysisName")
    private String analysisName;

    @Column(name = "Grain")
    private String grain;

    @Column(name = "FinancialPerspective")
    private String financialPerspective;

    @Column(name = "FinancialTreatyLabel")
    private String treatyLabel;

    @Column(name = "FinancialTreatyTag")
    private String treatyTag;

    @Column(name = "Peril")
    private String peril;

    @Column(name = "SubPeril")
    private String subPeril;

    @Column(name = "GeoCode")
    private String geoCode;

    @Column(name = "Region")
    private String region;

    @Column(name = "RegionPeril")
    private String regionPeril;

    @Column(name = "SourceCurrency")
    private String sourceCurrency;

    @Column(name = "TargetCurrency")
    private String targetCurrency;

    @Column(name = "TargetCurrencyBasis")
    private String targetCurrencyBasis;

    @Column(name = "ExchangeRate")
    private Double exchangeRate;

    @Column(name = "Division")
    private Integer division;

    @Column(name = "DefaultOccurrenceBasis")
    private String defaultOccurrenceBasis;

    @Column(name = "UserOccurrenceBasis")
    private String userOccurrenceBasis;

    @Column(name = "Proportion")
    private Double proportion;

    @Column(name = "ProxyScalingBasis")
    private String proxyScalingBasis;

    @Column(name = "ProxyScalingNarrative")
    private String proxyScalingNarrative;

    @Column(name = "UnitMultiplier")
    private Double unitMultiplier;

    @Column(name = "MultiplierBasis")
    private String multiplierBasis;

    @Column(name = "MultiplierNarrative")
    private String multiplierNarrative;

    @Column(name = "ProfileKey")
    private String profileKey;

    @Column(name = "ProfileName")
    private String profileName;

    @Column(name = "Description")
    private String description;

    @Column(name = "SourceAnalysisLevel")
    private String analysisLevel;

    @Column(name = "LossAmplification")
    private String lossAmplification;

    @Column(name = "Model")
    private String model;

    @Column(name = "Tags")
    private String tags;

    @Column(name = "UserNotes")
    private String userNotes;

    @Column(name = "OverrideReasonText")
    private String overrideReasonText;

    @Column(name = "ResultName")
    private String resultName;

    @Column(name = "SourceLossModellingBasis")
    private String sourceLossModellingBasis;

    @Column(name = "SourceLossTableType")
    private String sourceLossTableType;

    @Column(name = "EventSet")
    private String eventSet;

    @Column(name = "ModelModule")
    private String modelModule;

    @Column(name = "SourceResultsReference")
    private String sourceResultsReference;

    @Column(name = "OccurrenceBasisOverrideReason")
    private String occurrenceBasisOverrideReason;

    @Column(name = "OccurrenceBasisOverrideBy")
    private String occurrenceBasisOverrideBy;

    @Column(name = "Metadata")
    private String metaData;

    public ModelAnalysisEntity(ModelAnalysisEntity other) {
        this.entity = other.getEntity();
        this.importedDate = other.getImportedDate();
        this.creationDate = other.getCreationDate();
        this.runDate = other.getRunDate();
        this.importStatus = other.getImportStatus();
        this.projectImportRunId = other.getProjectImportRunId();
        this.sourceModellingSystemInstance = other.getSourceModellingSystemInstance();
        this.sourceModellingVendor = other.getSourceModellingVendor();
        this.sourceModellingSystem = other.getSourceModellingSystem();
        this.sourceModellingSystemVersion = other.getSourceModellingSystemVersion();
        this.dataSourceId = other.getDataSourceId();
        this.dataSourceName = other.getDataSourceName();
        this.fileName = other.getFileName();
        this.analysisId = other.getAnalysisId();
        this.analysisName = other.getAnalysisName();
        this.grain = other.getGrain();
        this.financialPerspective = other.getFinancialPerspective();
        this.treatyLabel = other.getTreatyLabel();
        this.treatyTag = other.getTreatyTag();
        this.peril = other.getPeril();
        this.subPeril = other.getSubPeril();
        this.geoCode = other.getGeoCode();
        this.region = other.getRegion();
        this.regionPeril = other.getRegionPeril();
        this.sourceCurrency = other.getSourceCurrency();
        this.targetCurrency = other.getTargetCurrency();
        this.targetCurrencyBasis = other.getTargetCurrencyBasis();
        this.exchangeRate = other.getExchangeRate();
        this.division = other.getDivision();
        this.defaultOccurrenceBasis = other.getDefaultOccurrenceBasis();
        this.userOccurrenceBasis = other.getUserOccurrenceBasis();
        this.proportion = other.getProportion();
        this.proxyScalingBasis = other.getProxyScalingBasis();
        this.proxyScalingNarrative = other.getProxyScalingNarrative();
        this.unitMultiplier = other.getUnitMultiplier();
        this.multiplierBasis = other.multiplierBasis;
        this.multiplierNarrative = other.getMultiplierNarrative();
        this.profileKey = other.profileKey;
        this.profileName = other.getProfileName();
        this.description = other.getDescription();
        this.analysisLevel = other.getAnalysisLevel();
        this.lossAmplification = other.getLossAmplification();
        this.model = other.getModel();
        this.tags = other.getTags();
        this.userNotes = other.getUserNotes();
        this.overrideReasonText = other.getOverrideReasonText();
        this.resultName = other.getResultName();
        this.sourceLossModellingBasis = other.getSourceLossModellingBasis();
        this.sourceLossTableType = other.getSourceLossTableType();
        this.eventSet = other.eventSet;
        this.modelModule = other.getModelModule();
        this.sourceResultsReference = other.getSourceResultsReference();
        this.occurrenceBasisOverrideReason = other.getOccurrenceBasisOverrideReason();
        this.occurrenceBasisOverrideBy = other.getOccurrenceBasisOverrideBy();
        this.metaData = other.getMetaData();
    }

//    public RRAnalysis(RlSourceResult sourceResult){
//    }
}
