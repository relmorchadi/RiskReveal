package com.scor.rr.domain.entities.rms.exposuresummary;

import com.scor.rr.domain.entities.rms.RmsModelDatasource;
import com.scor.rr.domain.entities.workspace.Project;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The persistent class for the RmsExposureSummary database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "RmsExposureSummary")
@Data
public class RmsExposureSummary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RmsExposureSummaryId")
    private Long rmsExposureSummaryId;
    @Column(name = "ImportStatus")
    private String importStatus;
    @Column(name = "PortfolioId")
    private Long portfolioId;
    @Column(name = "PortfolioType")
    private String portfolioType;
    @Column(name = "Peril")
    private String peril;
    @Column(name = "ExposureSummaryName")
    private String exposureSummaryName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProjectId")
    private Project project;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EDMId")
    private RmsModelDatasource edm;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ExposureSummaryDefinitionId")
    private SystemExposureSummaryDefinition exposureSummaryDefinition;
    @OneToMany(mappedBy = "rmsExposureSummary")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<RmsExposureSummaryItem> exposureSummaryList = new ArrayList<>();
}
