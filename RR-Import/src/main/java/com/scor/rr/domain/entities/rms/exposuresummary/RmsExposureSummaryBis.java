package com.scor.rr.domain.entities.rms.exposuresummary;

import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The persistent class for the RmsExposureSummaryBis database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "RmsExposureSummaryBis")
@Data
public class RmsExposureSummaryBis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RmsExposureSummaryBisId")
    private Long rmsExposureSummaryBisId;
    @Column(name = "ProjectId")
    private String projectId;
    @Column(name = "EDMId")
    private String edmId;
    @Column(name = "PortfolioId")
    private Long portfolioId;
    @Column(name = "PortfolioType")
    private String portfolioType;
    @Column(name = "Peril")
    private String peril;
    @Column(name = "ExposureSummaryName")
    private String exposureSummaryName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ExposureSummaryDefinitionId")
    private SystemExposureSummaryDefinition exposureSummaryDefinition;
    @OneToMany(mappedBy = "rmsExposureSummaryBis")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<RmsExposureSummaryItem> exposureSummaryList = new ArrayList<>();
}
