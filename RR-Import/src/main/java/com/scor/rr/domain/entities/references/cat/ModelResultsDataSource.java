package com.scor.rr.domain.entities.references.cat;

import lombok.Data;

import javax.persistence.*;

/**
 * The persistent class for the ModelResultsDataSource database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "ModelResultsDataSource")
@Data
public class ModelResultsDataSource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ModelResultsDataSourceId")
    private Long modelResultsDataSourceId;
    @Column(name = "Name")
    private String name;
    @Column(name = "ResultsDatabaseName")
    private String resultsDatabaseName;
    @Column(name = "ExposureDatabaseName")
    private String exposureDatabaseName;
    @Column(name = "FacModellingVersion")
    private Integer facModellingVersion;
    @Column(name = "FacPortfolioNumber")
    private String facPortfolioNumber;
    @Column(name = "FacPortfolioNumberOverride")
    private Boolean facPortfolioNumberOverride;
    @Column(name = "FacPortfolioNumberOverrideReason")
    private String facPortfolioNumberOverrideReason;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ModellingSystemInstanceId")
    private ModellingSystemInstance modellingSystemInstance;
}
