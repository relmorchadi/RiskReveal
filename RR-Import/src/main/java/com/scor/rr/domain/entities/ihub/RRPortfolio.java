package com.scor.rr.domain.entities.ihub;

import com.scor.rr.domain.entities.plt.ProjectImportRun;
import com.scor.rr.domain.entities.references.ExchangeRate;
import com.scor.rr.domain.entities.references.omega.Currency;
import com.scor.rr.domain.entities.workspace.Project;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

/**
 * The persistent class for the RRPortfolio database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "RRPortfolio")
@Data
public class RRPortfolio {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "RRPortfolioId")
    private String rrPortfolioId;
    @Column(name = "CreationDate")
    private Date creationDate;
    @Column(name = "ImportedDate")
    private Date importedDate;
    @Column(name = "ImportStatus")
    private String importStatus;
    @Column(name = "SourceModellingSystemInstance")
    private String sourceModellingSystemInstance;
    @Column(name = "SourceModellingVendor")
    private String sourceModellingVendor;
    @Column(name = "SourceModellingSystem")
    private String sourceModellingSystem;
    @Column(name = "SourceModellingSystemVersion")
    private Integer sourceModellingSystemVersion;
    @Column(name = "DataSourceId")
    private Long dataSourceId;
    @Column(name = "DataSourceName")
    private String dataSourceName;
    @Column(name = "PortfolioId")
    private String portfolioId;
    @Column(name = "PortfolioName")
    private String portfolioName;
    @Column(name = "ExposedLocationPerils")
    private String exposedLocationPerils;
    @Column(name = "Proportion")
    private Double proportion;
    @Column(name = "UnitMultiplier")
    private Double unitMultiplier;
    @Column(name = "Description")
    private String description;
    @Column(name = "ExposureLevel")
    private String exposureLevel;
    @Column(name = "Tags")
    private String tags;
    @Column(name = "UserNotes")
    private String userNotes;
    @Column(name = "TargetCurrencyBasis")
    private String targetCurrencyBasis;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProjectId")
    private Project project;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Currency")
    private Currency currency;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ExchangeRateId")
    private ExchangeRate exchangeRate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProjectImportRunId")
    private ProjectImportRun projectImportRun;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "RRPortfolios_RmsPFAnalysisRegion", joinColumns = {
            @JoinColumn(name = "RRPortfolioId")}, inverseJoinColumns = {
            @JoinColumn(name = "RmsPortfolioAnalysisRegionId")})
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<RmsPortfolioAnalysisRegion> tivAnalysisRegions;

}
