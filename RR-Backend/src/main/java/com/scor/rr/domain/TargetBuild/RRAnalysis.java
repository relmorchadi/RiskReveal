package com.scor.rr.domain.TargetBuild;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "RRAnalysis", schema = "dr")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RRAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RRAnalysisId")
    private Integer rrAnalysisId;

    @Column(name = "Entity")
    private Integer entity;

    @Column(name = "ProjectId")
    private Integer projectId;

    @Column(name = "ImportedDate")
    private Date importedDate;

    @Column(name = "CreationDate")
    private Date creationDate;

    @Column(name = "RunDate")
    private Date runDate;

    @Column(name = "ImportStatus")
    private String importStatus;

    @Column(name = "ProjectImportRunId")
    private Integer projectImportRunId;

    @Column(name = "SourceModellingSystemInstance")
    private String sourceModellingSystemInstance;

    @Column(name = "SourceModellingVendor")
    private String sourceModellingVendor;

    @Column(name = "SourceModellingSystem")
    private String sourceModellingSystem;

    @Column(name = "SourceModellingSystemVersion")
    private String sourceModellingSystemVersion;

    @Column(name = "DataSourceId")
    private BigDecimal dataSourceId;

    @Column(name = "DataSourceName")
    private String dataSourceName;

    @Column(name = "FileName")
    private String fileName;

    @Column(name = "AnalysisId")
    private Integer analysisId;

    @Column(name = "AnalysisName")
    private String analysisName;

    @Column(name = "Grain")
    private String grain;

    @Column(name = "FinancialPerspective")
    private String financialPerspective;

    @Column(name = "TreatyLabel")
    private String treatyLabel;

    @Column(name = "TreatyTag")
    private String treatyTag;

    @Column(name = "Peril")
    private String peril;

    @Column(name = "GeoCode")
    private String geoCode;

    @Column(name = "RegionPeril")
    private String regionPeril;

    @Column(name = "SourceCurrency")
    private String sourceCurrency;

    @Column(name = "TargetCurrency")
    private String targetCurrency;

    @Column(name = "TargetCurrencyBasis")
    private String targetCurrencyBasis;

    @Column(name = "ExchangeRate")
    private BigDecimal exchangeRate;

    @Column(name = "DefaultOccurrenceBasis")
    private String defaultOccurrenceBasis;

    @Column(name = "UserOccurrenceBasis")
    private String userOccurrenceBasis;

    @Column(name = "Proportion")
    private BigDecimal proportion;

    @Column(name = "ProxyScalingBasis")
    private String proxyScalingBasis;

    @Column(name = "ProxyScalingNarrative")
    private String proxyScalingNarrative;

    @Column(name = "UnitMultiplier")
    private BigDecimal unitMultiplier;

    @Column(name = "MultiplierBasis")
    private String multiplierBasis;

    @Column(name = "MultiplierNarrative")
    private String multiplierNarrative;

    @Column(name = "ProfileKey")
    private String profileKey;

    @Column(name = "Description")
    private String description;

    @Column(name = "AnalysisLevel")
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

}
