package com.scor.rr.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ModelPortfolio")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModelPortfolioEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ModelPortfolioId")
    private Long modelPortfolioId;
    @Column(name = "Entity")
    private Integer entity;
    @Column(name = "ProjectId")
    private Long projectId;
    @Column(name = "CreationDate")
    private Date creationDate;
    @Column(name = "ImportedDate")
    private Date importedDate;
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
    private Integer sourceModellingSystemVersion;
    @Column(name = "DataSourceId")
    private Long dataSourceId;
    @Column(name = "DataSourceName")
    private String dataSourceName;
    @Column(name = "SourcePortfolioId")
    private Long portfolioId;
    @Column(name = "ModelPortfolioName")
    private String portfolioName;
    // TODO: Review
    @Column(name = "ModelPortfolioType")
    private String portfolioType;
    //
    @Column(name = "ExposedLocationPerils")
    private String exposedLocationPerils;
    @Column(name = "Currency")
    private String currency;
    @Column(name = "DivisionNumber")
    private Integer division;
    @Column(name = "ExchangeRate")
    private Double exchangeRate;
    @Column(name = "Proportion")
    private Double proportion;
    @Column(name = "UnitMultiplier")
    private Double unitMultiplier;
    @Column(name = "ModelPortfolioDescription")
    private String description;
    @Column(name = "ExposureLevel")
    private boolean exposureLevel;
    @Column(name = "ImportLocationLevel")
    private boolean importLocationLevel;

}
