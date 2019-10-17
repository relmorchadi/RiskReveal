package com.scor.rr.domain.entities.rms.exposuresummary;

import com.scor.rr.domain.entities.workspace.ModelingExposureDataSource;
import com.scor.rr.domain.entities.workspace.Project;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the ExposureSummary database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "ExposureSummary")
@Data
public class ExposureSummary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ExposureSummaryId")
    private Long exposureSummaryId;
    @Column(name = "RRPortfolioId")
    private String rrPortfolioId;
    @Column(name = "ImportStatus")
    private String importStatus;
    @Column(name = "ExposureSummaryAlias")
    private String exposureSummaryAlias;
    @Column(name = "PortfolioId")
    private Long portfolioId;
    @Column(name = "PortfolioType")
    private String portfolioType;
    @Column(name = "Peril")
    private String peril;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProjectId")
    private Project project;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ExposureSummaryDefinitionId")
    private ExposureSummaryDefinition exposureSummaryDefinition;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ModelingExposureDataSourceId")
    private ModelingExposureDataSource modelingExposureDataSource;
    @OneToMany(mappedBy = "exposureSummary")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<ExposureSummaryItem> exposureSummaryItems;
}
