package com.scor.rr.domain.entities.meta;

import com.scor.rr.domain.entities.rms.AnalysisFinancialPerspective;
import com.scor.rr.domain.entities.rms.RmsAnalysis;
import com.scor.rr.domain.entities.rms.RmsAnalysisBasic;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the SourceEpHeader database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "SourceEpHeader")
@Data
public class SourceEpHeader {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SourceEpHeaderId")
    private Long sourceEpHeaderId;
    @Column(name = "AnalysisId")
    private Long analysisId;
    @Column(name = "StatisticsMetrics")
    private Integer statisticsMetrics;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SourceSummaryStatisticId")
    private SourceSummaryStatistic sourceSummaryStatistic;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AnalysisFinancialPerspectiveId")
    private AnalysisFinancialPerspective analysisFinancialPerspective;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RmsAnalysisId")
    private RmsAnalysis rmsAnalysis;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RmsAnalysisBasicId")
    private RmsAnalysisBasic rmsAnalysisBasic;
    @OneToMany(mappedBy = "sourceEpHeader")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<SourceEpCurve> sourceEpCurves;

    public SourceEpHeader() {
    }

    public SourceEpHeader(Long analysisId, Integer statisticsMetrics) {
        this.analysisId = analysisId;
        this.statisticsMetrics = statisticsMetrics;
    }

}
