package com.scor.rr.domain.entities.ihub;

import com.scor.rr.domain.entities.rms.AnalysisFinancialPerspective;
import com.scor.rr.domain.entities.rms.AnalysisTreatyStructure;
import com.scor.rr.domain.entities.rms.RmsAnalysis;
import com.scor.rr.domain.entities.rms.RmsMultipleRegionPeril;
import com.scor.rr.domain.entities.workspace.AssociationVersion;
import com.scor.rr.domain.entities.workspace.ImportDecision;
import com.scor.rr.domain.entities.workspace.Project;
import com.scor.rr.domain.enums.FinancialPerspectiveSelectType;
import com.scor.rr.domain.enums.ScanResult;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

/**
 * The persistent class for the SourceResult database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "SourceResult")
@Data
public class SourceResult {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "SourceResultId")
    private String sourceResultId;
    @Column(name = "IncludedTargetRapIds")
    private String includedTargetRapIds;
    @Column(name = "RmsProjectImportConfigId")
    private String rmsProjectImportConfigId;
    @Column(name = "ImportedDoneAtLeastOnce")
    private Boolean importedDoneAtLeastOnce;
    @Column(name = "FinalizedRegionPeril")
    private String finalizedRegionPeril;
    @Column(name = "AnalysisRegionPeril")
    private String analysisRegionPeril;
    @Column(name = "AnalysisSystemRegionPeril")
    private String analysisSystemRegionPeril;
    @Column(name = "AnalysisUserRegionPeril")
    private String analysisUserRegionPeril;
    @Column(name = "OverrideReasonText")
    private String overrideReasonText;
    @Column(name = "OverridedBy")
    private String overridedBy;
    @Column(name = "Tags")
    private String tags;
    @Column(name = "AllTags")
    private String allTags;
    @Column(name = "UserSelectedGrain")
    private String userSelectedGrain;
    @Column(name = "Notes")
    private String notes;
    @Column(name = "Selected")
    private Boolean selected;
    @Column(name = "Enabled")
    private Boolean enabled;
    @Column(name = "ExchangeRate")
    private BigDecimal exchangeRate;
    @Column(name = "TargetCurrencyBasis")
    private String targetCurrencyBasis;
    @Column(name = "TargetCurrency")
    private String targetCurrency;
    @Column(name = "UnitMultiplier")
    private Double unitMultiplier;
    @Column(name = "Proportion")
    private Double proportion;
    @Column(name = "ImportFlag")
    private Boolean importFlag;
    @Column(name = "ProxyScalingBasis")
    private String proxyScalingBasis;
    @Column(name = "ProxyScalingNarrative")
    private String proxyScalingNarrative;
    @Column(name = "MultiplierBasis")
    private String multiplierBasis;
    @Column(name = "MultiplierNarrative")
    private String multiplierNarrative;
    @Column(name = "LastScanned")
    private Date lastScanned;
    @Column(name = "Status")
    private ScanResult status;
    @Column(name = "SelectedSD")
    private Boolean selectedSD;
    @Column(name = "ImportStatus")
    private String importStatus;
    @Column(name = "SelectedRegionPerils")
    private String selectedRegionPerils;
    @Column(name = "AlternativeSourceResult")
    private Boolean alternativeSourceResult;
    @Column(name = "GroupedSourceResultId")
    private String groupedSourceResultId;
    @Column(name = "ProfileKey")
    private String profileKey;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProjectId")
    private Project project;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RmsAnalysisId")
    private RmsAnalysis rmsAnalysis;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AssociationVersionId")
    private AssociationVersion associationVersion;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ModelingResultDataSourceId")
    private ModelingResultDataSource modelingResultDataSource;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ImportDecisionId")
    private ImportDecision importDecision;
    //    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "FinancialPerspectiveId")
//    private AnalysisFinancialPerspective financialPerspective;
    @Column(name = "FinancialPerspectiveSelectType")
    private FinancialPerspectiveSelectType financialPerspectiveSelectType;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LinkedDatasetId")
    private LinkedDataset linkedDataset;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RepresentationDatasetId")
    private RepresentationDataset representationDataset;
    @OneToMany(mappedBy = "sourceResult")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<RmsMultipleRegionPeril> multipleRegionPerils;
    @OneToMany(mappedBy = "sourceResult")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<AnalysisTreatyStructure> analysisTreatyStructures;

    @Transient
    private List<AnalysisFinancialPerspective> analysisFinancialPerspectives;

}
