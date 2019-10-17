package com.scor.rr.domain.entities.plt;

import com.scor.rr.domain.entities.references.omega.Currency;
import com.scor.rr.domain.entities.workspace.Project;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Set;

/**
 * The persistent class for the RRAnalysis database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "RRAnalysis")
@Data
public class RRAnalysis {
    @Id
    @Column(name = "RRAnalysisId")
    private String rrAnalysisId;
    @Column(name = "ImportedDate")
    private Date importedDate;
    @Column(name = "CreationDate")
    private Date creationDate;
    @Column(name = "RunDate")
    private Date runDate;
    @Column(name = "ImportStatus")
    private String importStatus;
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
    @Column(name = "AnalysisId")
    private String analysisId;
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
    @Column(name = "TargetCcyBasis")
    private String targetCcyBasis;
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
    @Column(name = "SourceLossTableType")
    private String sourceLossTableType;
    @Column(name = "EventSet")
    private String eventSet;
    @Column(name = "ModelModule")
    private String modelModule;
    @Column(name = "SourceResultsReference")
    private Long sourceResultsReference;
    @Column(name = "SubPeril")
    private String subPeril;
    @Column(name = "Region")
    private String region;
    @Column(name = "ProfileName")
    private String profileName;
    @Column(name = "OccurrenceBasisOverrideReason")
    private String occurrenceBasisOverrideReason;
    @Column(name = "OccurrenceBasisOverridenBy")
    private String occurrenceBasisOverridenBy;
    @Column(name = "Metadata")
    private String metadata;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProjectId")
    private Project project;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProjectImportRunId")
    private ProjectImportRun projectImportRun;
    //@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RegionPerilId")
    private String regionPeril;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SourceCcyId")
    private Currency sourceCcy;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TargetCcyId")
    private Currency targetCcy;
    //@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ExchangeRateId")
    private Double exchangeRate;

    private String includedTargetRapIds;

    public String getFpDisplayCode() {
        if ("TY".equals(this.financialPerspective)) {
            if (!StringUtils.isEmpty(this.treatyTag)) {
                return this.financialPerspective + "-" + this.treatyTag;
            } else if (!StringUtils.isEmpty(this.treatyLabel)) {
                return this.financialPerspective + "-" + this.treatyLabel.replaceAll(" ", "");
            }
        }
        return this.financialPerspective;
    }

}
