package com.scor.rr.domain.entities.rms;

import com.scor.rr.domain.entities.ihub.RmsPortfolioAnalysisRegion;
import com.scor.rr.domain.entities.workspace.Project;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the RmsPortfolio database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "RmsPortfolio")
@Data
public class RmsPortfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rmsPortfolioId")
    private Long rmsPortfolioId;
    @Column(name = "EdmId")
    private Long edmId;
    @Column(name = "EdmName")
    private String edmName;
    @Column(name = "PortfolioId")
    private String portfolioId;
    @Column(name = "Number")
    private String number;
    @Column(name = "Name")
    private String name;
    @Column(name = "Created")
    private Date created;
    @Column(name = "Description")
    private String description;
    @Column(name = "Type")
    private String type;
    @Column(name = "Peril")
    private String peril;
    @Column(name = "AgSource")
    private String agSource;
    @Column(name = "AgCedent")
    private String agCedent;
    @Column(name = "AgCurrency")
    private String agCurrency;
    @Column(name = "Tiv")
    private BigDecimal tiv;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProjectId")
    private Project project;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RmsModelDatasourceId")
    private RmsModelDatasource rmsModelDatasource;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "EdmId")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<RmsPortfolioAnalysisRegion> analysisRegions;

}
