package com.scor.rr.domain.entities.workspace;

import com.scor.rr.domain.entities.ihub.LinkedDataset;
import com.scor.rr.domain.entities.ihub.RepresentationDataset;
import com.scor.rr.domain.entities.ihub.RmsPortfolioAnalysisRegion;
import com.scor.rr.domain.entities.rms.RmsPortfolio;
import com.scor.rr.domain.enums.ScanResult;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the Portfolio database table
 *
 * @author HADDINI Zakariyae  && HAMIANI Mohammed
 */
@Entity
@Table(name = "Portfolio")
@Data
public class Portfolio {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "PortfolioId")
    private String portfolioId;
    @Column(name = "Tags")
    private String tags;
    @Column(name = "Alias")
    private String alias;
    @Column(name = "Notes")
    private String notes;
    @Column(name = "Source")
    private String source;
    @Column(name = "Enable")
    private Boolean enable;
    @Column(name = "AllTags")
    private String allTags;
    @Column(name = "LastScanned")
    private Date lastScanned;
    @Column(name = "Selected")
    private Boolean selected;
    @Column(name = "SelectedSD")
    private Boolean selectedSD;
    @Column(name = "ImportFlag")
    private Boolean importFlag;
    @Column(name = "ImportStatus")
    private String importStatus;
    @Column(name = "UnitMultiplier")
    private Double unitMultiplier;
    @Column(name = "TargetCurrency")
    private String targetCurrency;
    @Column(name = "ImportSequence")
    private Integer importSequence;
    @Column(name = "PortfolioRegion")
    private String portfolioRegion;
    @Column(name = "MultiplierBasis")
    private String multiplierBasis;
    @Column(name = "ImportProportion")
    private Double importProportion;
    @Column(name = "ExchangeRate")
    private BigDecimal exchangeRate;
    @Column(name = "ProxyScalingBasis")
    private String proxyScalingBasis;
    @Column(name = "MultiplierNarrative")
    private String multiplierNarrative;
    @Column(name = "PortfolioUserRegion")
    private String portfolioUserRegion;
    @Column(name = "TargetCurrencyBasis")
    private String targetCurrencyBasis;
    @Column(name = "ProxyScalingNarrative")
    private String proxyScalingNarrative;
    @Column(name = "ImportedDoneAtLeastOnce")
    private Boolean importedDoneAtLeastOnce;
    @Column(name = "RmsProjectImportConfigId")
    private String rmsProjectImportConfigId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProjectId")
    private Project project;
    @Column(name = "Status")
    private ScanResult status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RmsPortfolioId")
    private RmsPortfolio rmsPortfolio;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ImportDecisionId")
    private ImportDecision importDecision;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AssociationVersionId")
    private AssociationVersion associationVersion;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ModelingExposureDataSourceId")
    private ModelingExposureDataSource modelingExposureDataSource;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LinkedDatasetId")
    private LinkedDataset linkedDataset;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RepresentationDatasetId")
    private RepresentationDataset representationDataset;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "PortfolioId")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<RmsPortfolioAnalysisRegion> selectedRmsPortfolioAnalysisRegions;
}
